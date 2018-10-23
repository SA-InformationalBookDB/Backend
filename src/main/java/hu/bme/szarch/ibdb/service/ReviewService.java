package hu.bme.szarch.ibdb.service;

import hu.bme.szarch.ibdb.domain.Book;
import hu.bme.szarch.ibdb.domain.Review;
import hu.bme.szarch.ibdb.domain.User;
import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.repository.BookRepository;
import hu.bme.szarch.ibdb.repository.ReviewRepository;
import hu.bme.szarch.ibdb.repository.UserRepository;
import hu.bme.szarch.ibdb.service.dto.review.ReviewMessage;
import hu.bme.szarch.ibdb.service.dto.review.ReviewResult;
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

    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Page<ReviewResult> getReviewsForBook(Pageable pageable, String bookId) {
        return reviewRepository.findAllByBook_Id(pageable, bookId).map(this::reviewToResult);
    }

    public void createReview(ReviewMessage message) {
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

        reviewRepository.save(review);
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
