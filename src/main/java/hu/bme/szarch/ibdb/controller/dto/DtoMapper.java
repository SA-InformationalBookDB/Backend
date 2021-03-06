package hu.bme.szarch.ibdb.controller.dto;

import hu.bme.szarch.ibdb.controller.dto.book.BookRequest;
import hu.bme.szarch.ibdb.controller.dto.book.BookResponse;
import hu.bme.szarch.ibdb.controller.dto.oauth.*;
import hu.bme.szarch.ibdb.controller.dto.review.ReviewRequest;
import hu.bme.szarch.ibdb.controller.dto.review.ReviewResponse;
import hu.bme.szarch.ibdb.controller.dto.user.CategoriesUpdateRequest;
import hu.bme.szarch.ibdb.controller.dto.user.CategoryResponse;
import hu.bme.szarch.ibdb.controller.dto.user.UserInfoResponse;
import hu.bme.szarch.ibdb.service.dto.authentication.LoginMessage;
import hu.bme.szarch.ibdb.service.dto.authentication.LoginResult;
import hu.bme.szarch.ibdb.service.dto.authentication.RegistrationMessage;
import hu.bme.szarch.ibdb.service.dto.authentication.RegistrationResult;
import hu.bme.szarch.ibdb.service.dto.book.BookResult;
import hu.bme.szarch.ibdb.service.dto.book.CreateBookMessage;
import hu.bme.szarch.ibdb.service.dto.book.UpdateBookMessage;
import hu.bme.szarch.ibdb.service.dto.category.CategoryResult;
import hu.bme.szarch.ibdb.service.dto.review.CreateReviewMessage;
import hu.bme.szarch.ibdb.service.dto.review.ReviewResult;
import hu.bme.szarch.ibdb.service.dto.review.UpdateReviewMessage;
import hu.bme.szarch.ibdb.service.dto.token.AccessTokenResult;
import hu.bme.szarch.ibdb.service.dto.user.FavouriteMessage;
import hu.bme.szarch.ibdb.service.dto.user.UpdateUserCategoriesMessage;
import hu.bme.szarch.ibdb.service.dto.user.UpdateUserMessage;
import hu.bme.szarch.ibdb.service.dto.user.UserInfoResult;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DtoMapper {

    public static RegistrationMessage registrationRequestToMessage(RegistrationRequest request) {
        return RegistrationMessage.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .confirmPassword(request.getConfirmPassword())
                .build();
    }

    public static RegistrationResponse registrationResultToResponse(RegistrationResult result) {
        return RegistrationResponse.builder()
                .userId(result.getUserId())
                .build();
    }

    public static LoginMessage loginRequestToMessage(LoginRequest request) {
        return LoginMessage.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .clientId(request.getClientId())
                .redirectUri(request.getRedirectUri())
                .build();
    }

    public static LoginResponse loginResultToResponse(LoginResult result) {
        return LoginResponse.builder()
                .code(result.getCode())
                .redirectUri(result.getRedirectUri())
                .build();
    }

    public static AccessTokenResponse accessTokenResultToResponse(AccessTokenResult result) {
        return AccessTokenResponse.builder()
                .accessToken(result.getAccessToken())
                .accessTokenExpiration(result.getAccessTokenExpiration())
                .refreshToken(result.getRefreshToken())
                .build();
    }

    public static List<BookResponse> bookResultsToResponse(List<BookResult> results) {
        return results.stream().map(DtoMapper::bookResultToResponse).collect(Collectors.toList());
    }

    public static BookResponse bookResultToResponse(BookResult result) {
        return BookResponse.builder()
                .id(result.getId())
                .author(result.getAuthor())
                .imageUrl(result.getImageUrl())
                .pageNumber(result.getPageNumber())
                .published(result.getPublished())
                .publisher(result.getPublisher())
                .sold(result.getSold())
                .summary(result.getSummary())
                .title(result.getTitle())
                .views(result.getViews())
                .averageRating(result.getAverageRating())
                .categories(result.getCategories().stream().map(DtoMapper::categoryResultToResponse).collect(Collectors.toList()))
                .isFavourite(result.isFavourite())
                .build();
    }

    public static ReviewResponse reviewResultToResponse(ReviewResult result) {
        return ReviewResponse.builder()
                .id(result.getId())
                .comment(result.getComment())
                .date(result.getDate())
                .points(result.getPoints())
                .bookId(result.getBookId())
                .userNickname(result.getUserNickname())
                .build();
    }

    public static List<ReviewResponse> reviewResultsToResponse(List<ReviewResult> result) {
        return result.stream().map(DtoMapper::reviewResultToResponse).collect(Collectors.toList());
    }

    public static CreateReviewMessage createReviewRequestToMessage(String userId, String bookId, ReviewRequest request) {
        return CreateReviewMessage.builder()
                .bookId(bookId)
                .userId(userId)
                .comment(request.getComment())
                .points(request.getPoints())
                .build();
    }

    public static UpdateReviewMessage updateReviewRequestToMessage(String userId, String reviewId, ReviewRequest request) {
        return UpdateReviewMessage.builder()
                .reviewId(reviewId)
                .userId(userId)
                .comment(request.getComment())
                .points(request.getPoints())
                .build();
    }

    public static CreateBookMessage createBookRequestToMessage(BookRequest request) {
        return CreateBookMessage.builder()
                .author(request.getAuthor())
                .imageUrl(request.getImageUrl())
                .pageNumber(request.getPageNumber())
                .published(request.getPublished())
                .publisher(request.getPublisher())
                .sold(request.getSold())
                .summary(request.getSummary())
                .title(request.getTitle())
                .categoryIds(request.getCategoryIds())
                .build();
    }

    public static UpdateBookMessage updateBookRequestToMessage(String bookId, BookRequest request) {
        return UpdateBookMessage.builder()
                .id(bookId)
                .author(request.getAuthor())
                .imageUrl(request.getImageUrl())
                .pageNumber(request.getPageNumber())
                .published(request.getPublished())
                .publisher(request.getPublisher())
                .sold(request.getSold())
                .summary(request.getSummary())
                .title(request.getTitle())
                .categoryIds(request.getCategoryIds())
                .build();
    }

    public static UpdateUserMessage updateUserRequestToMessage(String userId, String nickname, OffsetDateTime birthDate) {
        return UpdateUserMessage.builder()
                .id(userId)
                .nickname(nickname)
                .dateOfBirth(birthDate)
                .build();
    }

    public static List<UserInfoResponse> userInfoResultsToResponses(List<UserInfoResult> results) {
        return results.stream().map(DtoMapper::userInfoResultToResponse).collect(Collectors.toList());
    }

    public static UserInfoResponse userInfoResultToResponse(UserInfoResult result) {
        return UserInfoResponse.builder()
                .id(result.getId())
                .birthDate(result.getDateOfBirth())
                .nickname(result.getNickname())
                .email(result.getEmail())
                .role(result.getRole())
                .enabled(result.isEnabled())
                .build();
    }

    public static UpdateUserCategoriesMessage categoriesRequestToMessage(String userId, CategoriesUpdateRequest request) {
        return UpdateUserCategoriesMessage.builder()
                .userId(userId)
                .categoryIds(request.getCategoryIds())
                .build();
    }

    public static CategoryResponse categoryResultToResponse(CategoryResult result) {
        return CategoryResponse.builder()
                .id(result.getId())
                .name(result.getName())
                .build();
    }

    public static FavouriteMessage favouriteRequestToMessage(String userId, String bookId) {
        return FavouriteMessage.builder()
                .userId(userId)
                .bookId(bookId)
                .build();
    }

}
