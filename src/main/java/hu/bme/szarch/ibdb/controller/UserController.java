package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.controller.dto.DtoMapper;
import hu.bme.szarch.ibdb.controller.dto.user.CategoriesUpdateRequest;
import hu.bme.szarch.ibdb.controller.dto.user.UpdateUserRequest;
import hu.bme.szarch.ibdb.controller.dto.user.UserInfoResponse;
import hu.bme.szarch.ibdb.service.UserService;
import hu.bme.szarch.ibdb.service.dto.book.BookResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends WebBase {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PutMapping("/profile")
    public UserInfoResponse updateUser(@RequestBody UpdateUserRequest request) {
        return DtoMapper.userInfoResultToResponse(userService.updateUser(DtoMapper.updateUserRequestToMessage(getUserInfo().getUserId(), request)));
    }

    @GetMapping("/profile")
    public UserInfoResponse getUser() {
        return DtoMapper.userInfoResultToResponse(userService.getUserInfo(getUserInfo().getUserId()));
    }

    @DeleteMapping("/profile")
    public void remove() {
        userService.deleteUser(getUserInfo().getUserId());
    }

    @PutMapping("/category")
    public void updateCategories(@RequestBody @Valid CategoriesUpdateRequest request) {
        userService.updateCategories(DtoMapper.categoriesRequestToMessage(getUserInfo().getUserId(), request));
    }

    @PutMapping("/favourite/{id}")
    public void addFavourite(@PathVariable("id") String bookId) {
        userService.addFavourite(DtoMapper.favouriteRequestToMessage(getUserInfo().getUserId(), bookId));
    }

    @DeleteMapping("/favourite/{id}")
    public void removeFavourite(@PathVariable("id") String bookId) {
        userService.removeFavourite(DtoMapper.favouriteRequestToMessage(getUserInfo().getUserId(), bookId));
    }

    @GetMapping("/favourite")
    public List<BookResult> getFavourites() {
        return userService.getFavourites(getUserInfo().getUserId());
    }

}
