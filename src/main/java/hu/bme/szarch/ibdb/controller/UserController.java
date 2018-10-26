package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.controller.dto.DtoMapper;
import hu.bme.szarch.ibdb.controller.dto.book.ReviewResponse;
import hu.bme.szarch.ibdb.controller.dto.user.CategoriesUpdateRequest;
import hu.bme.szarch.ibdb.controller.dto.user.UserInfoResponse;
import hu.bme.szarch.ibdb.controller.dto.user.UserUpdateRequest;
import hu.bme.szarch.ibdb.domain.Book;
import hu.bme.szarch.ibdb.filter.AuthenticationFilter;
import hu.bme.szarch.ibdb.filter.dto.UserInfo;
import hu.bme.szarch.ibdb.service.BookService;
import hu.bme.szarch.ibdb.service.ReviewService;
import hu.bme.szarch.ibdb.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private ReviewService reviewService;
    private BookService bookService;

    public UserController(UserService userService, ReviewService reviewService, BookService bookService) {
        this.userService = userService;
        this.reviewService = reviewService;
        this.bookService = bookService;
    }



    @PutMapping("/profile")
    public UserInfoResponse updateUser(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                                       @RequestBody UserUpdateRequest request) {
        return DtoMapper.resultToResponse(userService.updateUser(DtoMapper.requestToMessage(userInfo.getUserId(), request)));
    }

    @GetMapping("/profile")
    public UserInfoResponse getUser(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo) {
        return DtoMapper.resultToResponse(userService.getUserInfo(userInfo.getUserId()));
    }

    @DeleteMapping("/profile")
    public void remove(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo) {
        userService.deleteUser(userInfo.getUserId());
    }

    @PutMapping("/category")
    public void updateCategories(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                                 @RequestBody CategoriesUpdateRequest request) {
        userService.updateCategories(DtoMapper.requestToMessage(userInfo.getUserId(), request));
    }

    @PutMapping("/favourite/{id}")
    public void addFavourite(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                             @PathVariable("id") String bookId) {
        userService.addFavourite(DtoMapper.requestToMessage(userInfo.getUserId(), bookId));
    }

    @DeleteMapping("/favourite/{id}")
    public void removeFavourite(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                                @PathVariable("id") String bookId) {
        userService.removeFavourite(DtoMapper.requestToMessage(userInfo.getUserId(), bookId));
    }

    @GetMapping("/favourite")
    public List<Book> getFavourites(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo) {
        return userService.getFavourites(userInfo.getUserId());
    }

    @GetMapping("/review")
    public Page<ReviewResponse> getReviews(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                                           @RequestAttribute int page,
                                           @RequestAttribute int size) {
        return DtoMapper.reviewResultsToResponse(reviewService.getReviewsForUser(PageRequest.of(page, size), userInfo.getUserId()));
    }

    @DeleteMapping("/review/{reviewId}}")
    public void deleteReview(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                             @PathVariable String reviewId) {
        reviewService.deleteReview(userInfo.getUserId(), reviewId);
    }

    @PutMapping("/review/{reviewId}")
    public void updateReview(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                             @PathVariable String reviewId) {
    }

}
