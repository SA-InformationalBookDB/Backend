package hu.bme.szarch.ibdb.controller;

import hu.bme.szarch.ibdb.controller.dto.DtoMapper;
import hu.bme.szarch.ibdb.controller.dto.user.CategoriesUpdateRequest;
import hu.bme.szarch.ibdb.controller.dto.user.UserInfoResponse;
import hu.bme.szarch.ibdb.controller.dto.user.UpdateUserRequest;
import hu.bme.szarch.ibdb.domain.Book;
import hu.bme.szarch.ibdb.filter.AuthenticationFilter;
import hu.bme.szarch.ibdb.filter.dto.UserInfo;
import hu.bme.szarch.ibdb.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PutMapping("/profile")
    public UserInfoResponse updateUser(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                                       @RequestBody UpdateUserRequest request) {
        return DtoMapper.userInfoResultToResponse(userService.updateUser(DtoMapper.updateUserRequestToMessage(userInfo.getUserId(), request)));
    }

    @GetMapping("/profile")
    public UserInfoResponse getUser(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo) {
        return DtoMapper.userInfoResultToResponse(userService.getUserInfo(userInfo.getUserId()));
    }

    @DeleteMapping("/profile")
    public void remove(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo) {
        userService.deleteUser(userInfo.getUserId());
    }

    @PutMapping("/category")
    public void updateCategories(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                                 @RequestBody CategoriesUpdateRequest request) {
        userService.updateCategories(DtoMapper.categoriesRequestToMessage(userInfo.getUserId(), request));
    }

    @PutMapping("/favourite/{id}")
    public void addFavourite(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                             @PathVariable("id") String bookId) {
        userService.addFavourite(DtoMapper.favouriteRequestToMessage(userInfo.getUserId(), bookId));
    }

    @DeleteMapping("/favourite/{id}")
    public void removeFavourite(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo,
                                @PathVariable("id") String bookId) {
        userService.removeFavourite(DtoMapper.favouriteRequestToMessage(userInfo.getUserId(), bookId));
    }

    @GetMapping("/favourite")
    public List<Book> getFavourites(@SessionAttribute(AuthenticationFilter.userInfoAttribute) UserInfo userInfo) {
        return userService.getFavourites(userInfo.getUserId());
    }

}
