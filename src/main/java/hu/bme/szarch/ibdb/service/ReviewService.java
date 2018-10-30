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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

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

    public Page<ReviewResult> getReviewsForBook(Pageable pageable, String bookId) {
        return reviewRepository.findAllByBook_Id(pageable, bookId).map(this::reviewToResult);
    }

    public Page<ReviewResult> getReviewsForUser(Pageable pageable, String userId) {
        return reviewRepository.findAllByUser_Id(pageable, userId).map(this::reviewToResult);
    }

    public void createReview(CreateReviewMessage message) {
        Optional<Book> book = bookRepository.findById(message.getBookId());
        Optional<User> user = userRepository.findById(message.getUserId());

        if(!book.isPresent() || !user.isPresent()) {
            throw new ServerException(Errors.NOT_FOUND);
        }

        Review review = new Review();

        review.setUser(user.get());
        review.setBook(book.get());
        review.setComment(message.getComment());
        review.setDate(OffsetDateTime.now());
        review.setPoints(message.getPoints());

        countAverageResultForBook(book.get(), message.getPoints());

        reviewRepository.save(review);
    }

    public void updateReview(UpdateReviewMessage message) {
        Optional<Review> review = reviewRepository.findById(message.getReviewId());

        if(!review.isPresent()) {
            throw new ServerException(Errors.NOT_FOUND);
        }

        if(!review.get().getUser().getId().equals(message.getUserId())) {
            throw new ServerException(Errors.BAD_REQUEST);
        }

        review.get().setComment(message.getComment());
        review.get().setPoints(message.getPoints());

        reviewRepository.save(review.get());
    }

    public void deleteReview(String userId, String reviewId) {
        Optional<Review> review = reviewRepository.findByIdAndUser_Id(reviewId, userId);

        if(!review.isPresent()) {
            throw new ServerException(Errors.NOT_FOUND);
        }

        reviewRepository.delete(review.get());
    }

    private void countAverageResultForBook(Book book, int newRating) {
        double ratingSum = book.getReviews().stream().mapToInt(Review::getPoints).sum();
        ratingSum += newRating;

        book.setAverageRating(ratingSum/book.getReviews().size());

        bookRepository.save(book);
    }

    private ReviewResult reviewToResult(Review review) {
        return ReviewResult.builder()
                .id(review.getId())
                .comment(review.getComment())
                .date(review.getDate())
                .points(review.getPoints())
                .build();
    }

}
