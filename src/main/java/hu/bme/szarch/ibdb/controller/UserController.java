package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.controller.dto.review.ReviewResponse;
import hu.bme.szarch.ibdb.controller.dto.user.CategoriesRequest;
import hu.bme.szarch.ibdb.controller.dto.user.CategoryResponse;
import hu.bme.szarch.ibdb.controller.dto.user.ProfileRequest;
import hu.bme.szarch.ibdb.controller.dto.user.ProfileResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

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
    public Page<ReviewResponse> getReviews(@RequestAttribute int page, @RequestAttribute int size) {
        return Page.empty();
    }

    @DeleteMapping("/review/{id}")
    public void deleteReview(@PathVariable String id) {

    }

    @PutMapping("/review/{id}")
    public void updateReview(@PathVariable String id) {

    }

}
