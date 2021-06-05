package com.thesis.gamamicroservices.reviewservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.thesis.gamamicroservices.reviewservice.dto.ReviewSetDTO;
import com.thesis.gamamicroservices.reviewservice.dto.messages.ReviewCreatedMessage;
import com.thesis.gamamicroservices.reviewservice.dto.messages.ReviewDeletedMessage;
import com.thesis.gamamicroservices.reviewservice.model.Review;
import com.thesis.gamamicroservices.reviewservice.repository.ReviewRepository;
import com.thesis.gamamicroservices.reviewservice.security.JwtTokenUtil;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ReviewService {

    private final RabbitTemplate rabbitTemplate;
    private final Exchange exchange;
    private final ReviewRepository reviewRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public ReviewService(RabbitTemplate rabbitTemplate, @Qualifier("reviewExchange") Exchange exchange, ReviewRepository reviewRepository, JwtTokenUtil jwtTokenUtil) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.reviewRepository = reviewRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public void createReview(String authorizationToken, int productID, ReviewSetDTO reviewSetDTO) {
        //String email = jwtTokenUtil.getEmailFromAuthorizationString(authorizationToken);
        int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromAuthorizationString(authorizationToken));
        Review r = new Review(reviewSetDTO, userId, productID);
        this.reviewRepository.save(r);
        rabbitTemplate.convertAndSend(exchange.getName(), "review", new ReviewCreatedMessage(r));
    }


    public void deleteReview(String authorizationToken, int reviewID) throws NoDataFoundException {
        int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromAuthorizationString(authorizationToken));
        Optional<Review> review = this.reviewRepository.findById(reviewID);
        if(review.isPresent() && review.get().getUserId() == userId) {
            reviewRepository.delete(review.get());
            rabbitTemplate.convertAndSend(exchange.getName(), "review", new ReviewDeletedMessage(reviewID));
        }
        else {
            throw new NoDataFoundException("You cannot delete review of id " + reviewID);
        }

    }

    @Transactional
    public void deleteReviewByProductId(int productId) {
        reviewRepository.deleteAllByProductId(productId);
    }

    @Transactional
    public void deleteReviewByUserId(int userId) {
        reviewRepository.deleteAllByUserId(userId);
        //evento de multiple review delete, ReviewsDeletedUserDeletedMessage
    }

}
