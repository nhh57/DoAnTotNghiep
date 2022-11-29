package com.example.ecommerce.controller;

import com.example.ecommerce.model.Reviews;
import com.example.ecommerce.response.BaseResponse;
import com.example.ecommerce.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @RequestMapping(value = "/get-all-review-by-product_id/{product_id}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> getAllAccount (
            @PathVariable(name = "product_id", required = false) int productId) throws Exception {
        BaseResponse response = new BaseResponse();
        List<Reviews> listReview = reviewService.getReviewByProduct(productId);
        response.setData(listReview);
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }
}
