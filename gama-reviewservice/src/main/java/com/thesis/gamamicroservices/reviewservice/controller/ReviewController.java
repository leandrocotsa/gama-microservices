package com.thesis.gamamicroservices.reviewservice.controller;

import com.thesis.gamamicroservices.reviewservice.dto.ReviewSetDTO;
import com.thesis.gamamicroservices.reviewservice.service.NoDataFoundException;
import com.thesis.gamamicroservices.reviewservice.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private static final String REVIEW_CREATED_LOG = "A Review was created";
    private static final String REVIEW_DELETED_LOG = "Review: {} was deleted";

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReview(@RequestHeader("Authorization") String authorizationToken, @RequestParam int productID, @RequestBody ReviewSetDTO reviewSetDTO) {
        this.reviewService.createReview(authorizationToken, productID, reviewSetDTO);
        logger.info(REVIEW_CREATED_LOG);
    }

    @DeleteMapping(path="/{reviewID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity deleteReview(@RequestHeader("Authorization") String authorizationToken, @PathVariable int reviewID) throws NoDataFoundException {
        this.reviewService.deleteReview(authorizationToken, reviewID);
        logger.info(REVIEW_DELETED_LOG, reviewID);
        return new ResponseEntity<>("Review successfully deleted", null, HttpStatus.OK);
    }

}
