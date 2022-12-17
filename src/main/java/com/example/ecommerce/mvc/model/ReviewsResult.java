package com.example.ecommerce.mvc.model;

import com.example.ecommerce.model.Reviews;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewsResult {
    private Reviews reviews;
    private String time;
}
