package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Reviews;
import com.example.ecommerce.repository.ReviewRepo;
import com.example.ecommerce.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;

    /**
     * <p>getReviewByProduct</p>
     * @param productId
     * @return list
     */
    @Transactional(readOnly = true)
    @Override
    public List<Reviews> getReviewByProduct(int productId) {
        return reviewRepo.findByProductId(productId);
    }
}
