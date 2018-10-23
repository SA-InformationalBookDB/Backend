package hu.bme.szarch.ibdb.controller.dto;

import hu.bme.szarch.ibdb.controller.dto.book.BookResponse;
import hu.bme.szarch.ibdb.controller.dto.book.ReviewRequest;
import hu.bme.szarch.ibdb.controller.dto.book.ReviewResponse;
import hu.bme.szarch.ibdb.controller.dto.oauth.*;
import hu.bme.szarch.ibdb.service.dto.authentication.LoginMessage;
import hu.bme.szarch.ibdb.service.dto.authentication.LoginResult;
import hu.bme.szarch.ibdb.service.dto.authentication.RegistrationMessage;
import hu.bme.szarch.ibdb.service.dto.authentication.RegistrationResult;
import hu.bme.szarch.ibdb.service.dto.book.BookResult;
import hu.bme.szarch.ibdb.service.dto.review.ReviewMessage;
import hu.bme.szarch.ibdb.service.dto.review.ReviewResult;
import hu.bme.szarch.ibdb.service.dto.token.AccessTokenResult;
import org.springframework.data.domain.Page;

public class DtoMapper {

    public static RegistrationMessage requestToMessage(RegistrationRequest request) {
        return RegistrationMessage.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

    public static RegistrationResponse resultToResponse(RegistrationResult result) {
        return RegistrationResponse.builder()
                .userId(result.getUserId())
                .build();
    }

    public static LoginMessage requestToMessage(LoginRequest request) {
        return LoginMessage.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .clientId(request.getClientId())
                .redirectUri(request.getRedirectUri())
                .build();
    }

    public static LoginResponse resultToResponse(LoginResult result) {
        return LoginResponse.builder()
                .code(result.getCode())
                .redirectUri(result.getRedirectUri())
                .build();
    }

    public static AccessTokenResponse resultToResponse(AccessTokenResult result) {
        return AccessTokenResponse.builder()
                .accessToken(result.getAccessToken())
                .accessTokenExpiration(result.getAccessTokenExpiration())
                .refreshToken(result.getRefreshToken())
                .build();
    }

    public static Page<BookResponse> bookResultsToResponse(Page<BookResult> results) {
        return results.map(DtoMapper::resultToResponse);
    }

    public static BookResponse resultToResponse(BookResult result) {
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
                .build();
    }

    public static ReviewResponse resultToResponse(ReviewResult result) {
        return ReviewResponse.builder()
                .id(result.getId())
                .comment(result.getComment())
                .date(result.getDate())
                .points(result.getPoints())
                .build();
    }

    public static Page<ReviewResponse> reviewResultsToResponse(Page<ReviewResult> result) {
        return result.map(DtoMapper::resultToResponse);
    }

    public static ReviewMessage requestToMessage(String userId, String bookId, ReviewRequest request) {
        return ReviewMessage.builder()
                .bookId(bookId)
                .userId(userId)
                .comment(request.getComment())
                .points(request.getPoints())
                .build();
    }

}
