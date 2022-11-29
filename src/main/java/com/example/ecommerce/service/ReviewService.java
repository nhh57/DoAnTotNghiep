package com.example.ecommerce.service;

import com.example.ecommerce.model.Reviews;

import java.util.List;

public interface ReviewService {
    /**
     * <p>getReviewByProduct</p>
     * @param productId
     * @return list
     */
    List<Reviews> getReviewByProduct(int productId);
}
