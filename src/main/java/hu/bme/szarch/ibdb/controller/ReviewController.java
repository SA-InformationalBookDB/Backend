package hu.bme.szarch.ibdb.controller;


import hu.bme.szarch.ibdb.controller.dto.DtoMapper;
import hu.bme.szarch.ibdb.controller.dto.review.ReviewRequest;
import hu.bme.szarch.ibdb.controller.dto.review.ReviewResponse;
import hu.bme.szarch.ibdb.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController extends WebBase {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping()
    public List<ReviewResponse> getReviews() {
        return DtoMapper.reviewResultsToResponse(reviewService.getReviewsForUser(getUserInfo().getUserId()));
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable String reviewId) {
        reviewService.deleteReview(getUserInfo().getUserId(), reviewId);
    }

    @PutMapping("/{reviewId}")
    public void updateReview(@PathVariable String reviewId,
                             @RequestBody @Valid ReviewRequest reviewRequest) {
        reviewService.updateReview(DtoMapper.updateReviewRequestToMessage(getUserInfo().getUserId(), reviewId, reviewRequest));
    }

    @GetMapping("/book/{id}")
    public List<ReviewResponse> getBookReviews(@PathVariable String id) {
        return DtoMapper.reviewResultsToResponse(reviewService.getReviewsForBook(id));
    }

    @PostMapping("/book/{id}")
    public void addReview(@PathVariable String id,
                          @RequestBody @Valid ReviewRequest request) {
        reviewService.createReview(DtoMapper.createReviewRequestToMessage(getUserInfo().getUserId(), id, request));
    }

}
