package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.service.ReviewService;
import hu.bme.szarch.ibdb.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    private UserService userService;
    private ReviewService reviewService;

    public AdminUserController(UserService userService, ReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @DeleteMapping("/{userId}/remove")
    public void removeUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    @PostMapping("/{userId}/enable")
    public void enableUser(@PathVariable String userId) {
        userService.enableOrDisableUser(userId, true);
    }

    @PostMapping("/{userId}/disable")
    public void disableUser(@PathVariable String userId) {
        userService.enableOrDisableUser(userId, false);
    }

    @DeleteMapping("/{userId}/review/{reviewId}")
    public void removeReview(@PathVariable String userId,
                             @PathVariable String reviewId) {
        reviewService.deleteReview(userId, reviewId);
    }

}
