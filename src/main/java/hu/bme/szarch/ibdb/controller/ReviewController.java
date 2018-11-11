package hu.bme.szarch.ibdb.controller;


import hu.bme.szarch.ibdb.controller.dto.DtoMapper;
import hu.bme.szarch.ibdb.controller.dto.review.ReviewRequest;
import hu.bme.szarch.ibdb.controller.dto.review.ReviewResponse;
import hu.bme.szarch.ibdb.filter.AuthenticationFilter;
import hu.bme.szarch.ibdb.filter.dto.UserInfo;
import hu.bme.szarch.ibdb.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping()
    public Page<ReviewResponse> getReviews(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                                           @RequestParam(required = false, defaultValue = "0") int page,
                                           @RequestParam(required = false, defaultValue = "10") int size) {
        return DtoMapper.reviewResultsToResponse(reviewService.getReviewsForUser(PageRequest.of(page, size), userInfo.getUserId()));
    }

    @DeleteMapping("/{reviewId}}")
    public void deleteReview(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                             @PathVariable String reviewId) {
        reviewService.deleteReview(userInfo.getUserId(), reviewId);
    }

    @PutMapping("/{reviewId}")
    public void updateReview(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                             @PathVariable String reviewId,
                             @RequestBody @Valid ReviewRequest reviewRequest) {
        reviewService.updateReview(DtoMapper.updateReviewRequestToMessage(userInfo.getUserId(), reviewId, reviewRequest));
    }

    @GetMapping("/book/{id}")
    public Page<ReviewResponse> getBookReviews(@PathVariable String id,
                                               @RequestParam(required = false, defaultValue = "0") int page,
                                               @RequestParam(required = false, defaultValue = "10") int size) {
        return DtoMapper.reviewResultsToResponse(reviewService.getReviewsForBook(PageRequest.of(page, size), id));
    }

    @PostMapping("/book/{id}")
    public void addReview(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                          @PathVariable String id,
                          @RequestBody @Valid ReviewRequest request) {
        reviewService.createReview(DtoMapper.createReviewRequestToMessage(userInfo.getUserId(), id, request));
    }

}
