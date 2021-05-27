package com.thesis.gamamicroservices.userservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.thesis.gamamicroservices.userservice.dto.UserCreatedDTO;
import com.thesis.gamamicroservices.userservice.dto.UserGetDTO;
import com.thesis.gamamicroservices.userservice.dto.UserSetDTO;
import com.thesis.gamamicroservices.userservice.messaging.RoutingKeys;
import com.thesis.gamamicroservices.userservice.model.Account;
import com.thesis.gamamicroservices.userservice.model.Address;
import com.thesis.gamamicroservices.userservice.model.Role;
import com.thesis.gamamicroservices.userservice.model.User;
import com.thesis.gamamicroservices.userservice.repository.UserRepository;
import com.thesis.gamamicroservices.userservice.security.JwtTokenUtil;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder bcryptEncoder;
    private final ObjectWriter objectWriter;
    private final RabbitTemplate rabbitTemplate;
    private final Exchange exchange;

    @Autowired
    public UserService(UserRepository userRepository, JwtTokenUtil jwtTokenUtil, PasswordEncoder bcryptEncoder, ObjectWriter objectWriter, RabbitTemplate rabbitTemplate, Exchange exchange) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.bcryptEncoder = bcryptEncoder;
        this.objectWriter = objectWriter;
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
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
            Address address = new Address(userSetDto.getStreet(), userSetDto.getZipCode(), userSetDto.getCountry(), userSetDto.getCity());
            User createdUser = new User(userSetDto, account);
            createdUser.addAddressToUser(address);


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
            try {
                String userJson = objectWriter.writeValueAsString(new UserCreatedDTO(createdUser));
                rabbitTemplate.convertAndSend(exchange.getName(), RoutingKeys.CREATED.getNotation(), userJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }


        }
    }

    public void deleteUser(int id) throws NoDataFoundException {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            rabbitTemplate.convertAndSend(exchange.getName(), RoutingKeys.DELETED.getNotation(), id);
        }
        else {
            throw new NoDataFoundException("There's no user with id " + id);
        }
    }


    public void createAdmin(UserSetDTO userSetDto) throws AlreadyExistsException {

        Optional<User> userOptional = findByEmail(userSetDto.getEmail());
        if (userOptional.isPresent()){
            throw new AlreadyExistsException ("There's a user with that email");
        }
        else {
            Account account = new Account(userSetDto.getEmail(), bcryptEncoder.encode(userSetDto.getPassword()),Role.ADMIN);
            Address address = new Address(userSetDto.getStreet(), userSetDto.getZipCode(), userSetDto.getCountry(), userSetDto.getCity());
            User createdUser = new User(userSetDto, account);
            createdUser.addAddressToUser(address);

            //builder serve para evitar ter multiplos constructors com parametros diferentes cada
            //neste caso do user como apenas contruo de uma forma nem faz muito sentido mas whatever
            userRepository.save(createdUser);

            try {
                String userJson = objectWriter.writeValueAsString(new UserCreatedDTO(createdUser));
                rabbitTemplate.convertAndSend(exchange.getName(), RoutingKeys.CREATED.getNotation(), userJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
