package hu.bme.szarch.ibdb.service;

import hu.bme.szarch.ibdb.domain.Book;
import hu.bme.szarch.ibdb.domain.Review;
import hu.bme.szarch.ibdb.domain.User;
import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.repository.BookRepository;
import hu.bme.szarch.ibdb.repository.ReviewRepository;
import hu.bme.szarch.ibdb.repository.UserRepository;
import hu.bme.szarch.ibdb.service.dto.review.CreateReviewMessage;
import hu.bme.szarch.ibdb.service.dto.review.ReviewResult;
import hu.bme.szarch.ibdb.service.dto.review.UpdateReviewMessage;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         BookRepository bookRepository,
                         UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<ReviewResult> getReviewsForBook(String bookId) {
        return reviewRepository.findAllByBook_Id(bookId).stream().map(this::reviewToResult).collect(Collectors.toList());
    }

    public List<ReviewResult> getReviewsForUser(String userId) {
        return reviewRepository.findAllByUser_Id(userId).stream().map(this::reviewToResult).collect(Collectors.toList());
    }

    public void createReview(CreateReviewMessage message) {
        Book book = bookRepository.findById(message.getBookId()).orElseThrow(() -> new ServerException(Errors.NOT_FOUND));
        User user = userRepository.findById(message.getUserId()).orElseThrow(() -> new ServerException(Errors.NOT_FOUND));

        Review review = new Review();

        review.setUser(user);
        review.setBook(book);
        review.setComment(message.getComment());
        review.setDate(OffsetDateTime.now());
        review.setPoints(message.getPoints());

        reviewRepository.save(review);

        List<Review> reviews = reviewRepository.findAllByBook_Id(message.getBookId());

        setAverageResultForBook(book, reviews);

        bookRepository.save(book);
    }

    public void updateReview(UpdateReviewMessage message) {
        Review review = reviewRepository.findById(message.getReviewId()).orElseThrow(() -> new ServerException(Errors.NOT_FOUND));
        Book book = bookRepository.findById(review.getBook().getId()).orElseThrow(() -> new ServerException(Errors.NOT_FOUND));

        if (!review.getUser().getId().equals(message.getUserId())) {
            throw new ServerException(Errors.BAD_REQUEST);
        }

        review.setComment(message.getComment());
        review.setPoints(message.getPoints());

        reviewRepository.save(review);

        List<Review> reviews = reviewRepository.findAllByBook_Id(book.getId());

        setAverageResultForBook(book, reviews);

        bookRepository.save(book);
    }

    public void deleteReview(String userId, String reviewId) {
        Review review = reviewRepository.findByIdAndUser_Id(reviewId, userId).orElseThrow(() -> new ServerException(Errors.NOT_FOUND));
        Book book = review.getBook();

        reviewRepository.delete(review);

        List<Review> reviews = reviewRepository.findAllByBook_Id(book.getId());

        setAverageResultForBook(book, reviews);

        bookRepository.save(book);
    }

    private void setAverageResultForBook(Book book, List<Review> reviews) {
        if(reviews.size() == 0) {
            book.setAverageRating(0.0f);
            return;
        }

        float ratingSum = (float)reviews.stream().mapToDouble(Review::getPoints).sum();

        book.setAverageRating(ratingSum / reviews.size());
    }

    private ReviewResult reviewToResult(Review review) {
        return ReviewResult.builder()
                .id(review.getId())
                .comment(review.getComment())
                .date(review.getDate())
                .points(review.getPoints())
                .bookId(review.getBook().getId())
                .bookTitle(review.getBook().getTitle())
                .userNickname(review.getUser().getNickname())
                .build();
    }

}
