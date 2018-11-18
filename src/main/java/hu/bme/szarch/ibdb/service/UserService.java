package hu.bme.szarch.ibdb.service;

import hu.bme.szarch.ibdb.domain.Book;
import hu.bme.szarch.ibdb.domain.Category;
import hu.bme.szarch.ibdb.domain.Role;
import hu.bme.szarch.ibdb.domain.User;
import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.repository.BookRepository;
import hu.bme.szarch.ibdb.repository.CategoryRepository;
import hu.bme.szarch.ibdb.repository.UserRepository;
import hu.bme.szarch.ibdb.service.dto.book.BookResult;
import hu.bme.szarch.ibdb.service.dto.category.CategoryResult;
import hu.bme.szarch.ibdb.service.dto.user.FavouriteMessage;
import hu.bme.szarch.ibdb.service.dto.user.UpdateUserCategoriesMessage;
import hu.bme.szarch.ibdb.service.dto.user.UpdateUserMessage;
import hu.bme.szarch.ibdb.service.dto.user.UserInfoResult;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private BookRepository bookRepository;

    public UserService(UserRepository userRepository,
                       CategoryRepository categoryRepository,
                       BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    public List<UserInfoResult> getUsers() {
        return userRepository.findAll().stream().filter(user -> !user.getRole().equals(Role.ADMIN)).map(this::userToUserInfoResult).collect(Collectors.toList());
    }

    public UserInfoResult getUserInfo(String id) {
        return userToUserInfoResult(findUserById(id));
    }

    public void deleteUser(String userId) {
        User user = findUserById(userId);

        userRepository.delete(user);
    }

    public UserInfoResult updateUser(UpdateUserMessage message) {
        User user = findUserById(message.getId());

        if(message.getDateOfBirth() != null) user.setDateOfBirth(message.getDateOfBirth());
        if(message.getNickname() != null) user.setNickname(message.getNickname());

        userRepository.save(user);

        return userToUserInfoResult(user);
    }

    public void enableOrDisableUser(String userId, boolean isEnabled) {
        User user = findUserById(userId);

        if(user.getRole().equals(Role.ADMIN)) return;

        user.setEnabled(isEnabled);

        this.userRepository.save(user);
    }

    public void updateCategories(UpdateUserCategoriesMessage message) {
        List<Category> categories = categoryRepository.findAllByIdIn(new HashSet<>(message.getCategoryIds()));

        User user = findUserById(message.getUserId());

        user.setCategories(categories);

        userRepository.save(user);
    }

    public List<Category> getCategoriesForUser(String id) {
        return findUserById(id).getCategories();
    }

    public void addFavourite(FavouriteMessage message) {
        User user = findUserById(message.getUserId());

        user.getFavourites().add(findBookById(message.getBookId()));

        userRepository.save(user);
    }

    public void removeFavourite(FavouriteMessage message) {
        User user = findUserById(message.getUserId());
        Book book = findBookById(message.getBookId());

        user.setFavourites(
                user.getFavourites()
                        .stream()
                        .filter(_book -> !_book.getId().equals(book.getId()))
                        .collect(Collectors.toList()
                        )
        );

        userRepository.save(user);
    }

    public List<BookResult> getFavourites(String userId) {
        return findUserById(userId).getFavourites().stream().map(this::bookToResult).collect(Collectors.toList());
    }


    private User findUserById(String userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new ServerException(Errors.NOT_FOUND));
    }

    private Book findBookById(String bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new ServerException(Errors.NOT_FOUND));
    }

    private UserInfoResult userToUserInfoResult(User user) {
        return UserInfoResult.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .role(user.getRole())
                .dateOfBirth(user.getDateOfBirth())
                .enabled(user.isEnabled())
                .build();
    }

    private BookResult bookToResult(Book book) {
        return BookResult.builder()
                .id(book.getId())
                .averageRating(book.getAverageRating())
                .views(book.getViews())
                .title(book.getTitle())
                .summary(book.getSummary())
                .sold(book.getSold())
                .publisher(book.getPublisher())
                .published(book.getPublished())
                .pageNumber(book.getPageNumber())
                .imageUrl(book.getImageUrl())
                .author(book.getAuthor())
                .categories(book.getCategories().stream().map(this::categoryToResult).collect(Collectors.toList()))
                .build();
    }

    private CategoryResult categoryToResult(Category category) {
        return CategoryResult.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
