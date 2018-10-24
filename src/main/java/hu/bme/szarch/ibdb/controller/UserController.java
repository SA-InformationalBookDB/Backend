package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.controller.dto.DtoMapper;
import hu.bme.szarch.ibdb.controller.dto.book.ReviewResponse;
import hu.bme.szarch.ibdb.controller.dto.user.CategoriesRequest;
import hu.bme.szarch.ibdb.controller.dto.user.CategoryResponse;
import hu.bme.szarch.ibdb.controller.dto.user.ProfileRequest;
import hu.bme.szarch.ibdb.controller.dto.user.ProfileResponse;
import hu.bme.szarch.ibdb.filter.AuthenticationFilter;
import hu.bme.szarch.ibdb.filter.dto.UserInfo;
import hu.bme.szarch.ibdb.service.ReviewService;
import hu.bme.szarch.ibdb.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private ReviewService reviewService;

    public UserController(UserService userService, ReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @PutMapping("/profile")
    public void updateUser(@RequestBody ProfileRequest request) {

    }

    @GetMapping("/profile")
    public ProfileResponse getUser() {
        return ProfileResponse.builder().build();
    }

    @DeleteMapping("/profile/remove")
    public void remove() {

    }

    @PutMapping("/category")
    public void updateCategories(@RequestBody CategoriesRequest request) {

    }

    @GetMapping("/category")
    public Page<CategoryResponse> getCategories(@RequestAttribute int page, @RequestAttribute int size) {
        return Page.empty();
    }

    @PutMapping("/favourite")
    public void updateFavourites(@RequestBody CategoriesRequest request) {

    }

    @GetMapping("/favourite")
    public Page<CategoryResponse> getFavourites(@RequestAttribute int page, @RequestAttribute int size) {
        return Page.empty();
    }

    @GetMapping("/review")
    public Page<ReviewResponse> getReviews(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                                           @RequestAttribute int page,
                                           @RequestAttribute int size) {
        return DtoMapper.reviewResultsToResponse(reviewService.getReviewsForUser(PageRequest.of(page, size), userInfo.getUserId()));
    }

    @DeleteMapping("/review/{id}")
    public void deleteReview(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                             @PathVariable String id) {
        reviewService.deleteReview(userInfo.getUserId(), id);
    }

    @PutMapping("/review/{id}")
    public void updateReview(@PathVariable String id) {

    }

}
