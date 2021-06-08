package com.thesis.gamamicroservices.userservice.service;

import com.thesis.gamamicroservices.userservice.dto.AddressSetDTO;
import com.thesis.gamamicroservices.userservice.dto.messages.*;
import com.thesis.gamamicroservices.userservice.dto.UserGetDTO;
import com.thesis.gamamicroservices.userservice.dto.UserSetDTO;
import com.thesis.gamamicroservices.userservice.messaging.RoutingKeys;
import com.thesis.gamamicroservices.userservice.model.Account;
import com.thesis.gamamicroservices.userservice.model.Address;
import com.thesis.gamamicroservices.userservice.model.Role;
import com.thesis.gamamicroservices.userservice.model.User;
import com.thesis.gamamicroservices.userservice.repository.AddressRepository;
import com.thesis.gamamicroservices.userservice.repository.UserRepository;
import com.thesis.gamamicroservices.userservice.security.JwtTokenUtil;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder bcryptEncoder;
    private final RabbitTemplate rabbitTemplate;
    private final Exchange userExchange;
    private final Exchange userUExchange;

    @Autowired
    public UserService(UserRepository userRepository, AddressRepository addressRepository, JwtTokenUtil jwtTokenUtil, PasswordEncoder bcryptEncoder, RabbitTemplate rabbitTemplate, @Qualifier("userExchange") Exchange userExchange, @Qualifier("userUExchange") Exchange userUExchange) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.bcryptEncoder = bcryptEncoder;
        this.rabbitTemplate = rabbitTemplate;
        this.userExchange = userExchange;
        this.userUExchange = userUExchange;
    }

    public UserGetDTO getMyUserDetails(String authorizationToken) {
        String email = jwtTokenUtil.getEmailFromAuthorizationString(authorizationToken);
        return new UserGetDTO(findByEmail(email).get());
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByAccount_Email(email);
    }

    public boolean emailAlreadyExists(String email) {
        return findByEmail(email).isPresent();
    }


    public User getMyUser(String authorizationToken) {
        String email = jwtTokenUtil.getEmailFromAuthorizationString(authorizationToken);
        return findByEmail(email).get();
    }


    public void createUser(UserSetDTO userSetDto) throws AlreadyExistsException {

        Optional<User> userOptional = findByEmail(userSetDto.getEmail());
        if (userOptional.isPresent()){
            throw new AlreadyExistsException ("There's a user with that email");
        }
        else {
            Account account = new Account(userSetDto.getEmail(), bcryptEncoder.encode(userSetDto.getPassword()), Role.USER);
            //Address address = new Address(userSetDto.getStreet(), userSetDto.getZipCode(), userSetDto.getCountry(), userSetDto.getCity());
            User createdUser = new User(userSetDto, account);
            //createdUser.addAddressToUser(address);


/**
            User createdUser = User.builder()
                    .account(Account.builder().email(userSetDto.getEmail()).password(bcryptEncoder.encode(userSetDto.getPassword())).role(Role.USER).build())
                    .firstName(userSetDto.getFirstName())
                    .lastName(userSetDto.getFirstName())
                    .birthDate(userSetDto.getBirthDate())
                    .phoneNumber(userSetDto.getPhoneNumber())
                    .sex(userSetDto.getSex()) //tenho que ver se tenho que incializar vazio as reviews ou se o builder ja trata disso
                    .build();
 **/
            //builder serve para evitar ter multiplos constructors com parametros diferentes cada
            //neste caso do user como apenas contruo de uma forma nem faz muito sentido mas whatever
            userRepository.save(createdUser);
            rabbitTemplate.convertAndSend(userExchange.getName(), RoutingKeys.CREATED.getNotation(), new UserCreatedMessage(createdUser));

        }
    }

    public void deleteUser(String authorizationToken) throws NoDataFoundException {
        int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromAuthorizationString(authorizationToken));
        if(userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            rabbitTemplate.convertAndSend(userExchange.getName(), RoutingKeys.DELETED.getNotation(), new UserDeletedMessage(userId));
        }
        else {
            throw new NoDataFoundException("There's no user with id " + userId);
        }
    }

    public void updateUserDetails(String authorizationToken, Map<String, Object> updates) {
        User user = this.getMyUser(authorizationToken);
        try {
            // Map key is field name, v is value
            updates.forEach((k, v) -> {
                // use reflection to get field k on manager and set it to value v
                try {
                    Field field = ReflectionUtils.findField(User.class, k);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, user, v);
                } catch (NullPointerException e) {
                    throw new NullPointerException();
                }
            });
            userRepository.save(user);

            rabbitTemplate.convertAndSend(userUExchange.getName(), "users", new UserUpdatedMessage(user.getId(), updates));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAddress(String authorizationToken, AddressSetDTO addressSetDTO) {
        Address address = new Address(addressSetDTO);
        //int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromAuthorizationString(authorizationToken));
        User user = this.getMyUser(authorizationToken);
        address.setUser(user);
        addressRepository.save(address);
        //user.addAddressToUser(address);
        //userRepository.save(user);

        rabbitTemplate.convertAndSend(userUExchange.getName(), "users", new AddressCreatedMessage(address));
    }

    public void removeAddress(String authorizationToken, int addressId) throws NoDataFoundException {
        //int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromAuthorizationString(authorizationToken));
        User user = this.getMyUser(authorizationToken);
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new NoDataFoundException("Address not found"));

        if(address.getUser().getId() == user.getId()) {
            addressRepository.deleteById(addressId);
            rabbitTemplate.convertAndSend(userUExchange.getName(), "users", new AddressDeletedMessage(addressId, user.getId()));
        } else {
            throw new NoDataFoundException("User does not own that address");
        }


    }

    //address update


    public void createAdmin(UserSetDTO userSetDto) throws AlreadyExistsException {

        Optional<User> userOptional = findByEmail(userSetDto.getEmail());
        if (userOptional.isPresent()){
            throw new AlreadyExistsException ("There's a user with that email");
        }
        else {
            Account account = new Account(userSetDto.getEmail(), bcryptEncoder.encode(userSetDto.getPassword()),Role.ADMIN);
            //Address address = new Address(userSetDto.getStreet(), userSetDto.getZipCode(), userSetDto.getCountry(), userSetDto.getCity());
            User createdUser = new User(userSetDto, account);
            //createdUser.addAddressToUser(address);

            //builder serve para evitar ter multiplos constructors com parametros diferentes cada
            //neste caso do user como apenas contruo de uma forma nem faz muito sentido mas whatever
            userRepository.save(createdUser);

            userRepository.save(createdUser);
            rabbitTemplate.convertAndSend(userExchange.getName(), RoutingKeys.CREATED.getNotation(), new UserCreatedMessage(createdUser));
        }
    }
}
