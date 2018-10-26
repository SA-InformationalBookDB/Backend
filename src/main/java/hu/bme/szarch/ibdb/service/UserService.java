package hu.bme.szarch.ibdb.service;

import hu.bme.szarch.ibdb.domain.Book;
import hu.bme.szarch.ibdb.domain.Category;
import hu.bme.szarch.ibdb.domain.User;
import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.repository.BookRepository;
import hu.bme.szarch.ibdb.repository.CategoryRepository;
import hu.bme.szarch.ibdb.repository.UserRepository;
import hu.bme.szarch.ibdb.service.dto.user.FavouriteMessage;
import hu.bme.szarch.ibdb.service.dto.user.CategoriesUpdateMessage;
import hu.bme.szarch.ibdb.service.dto.user.UserInfoResult;
import hu.bme.szarch.ibdb.service.dto.user.UserUpdateMessage;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

    public UserInfoResult getUserInfo(String id) {
        User user = findUserById(id);

        return UserInfoResult.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public void deleteUser(String userId) {
        User user = findUserById(userId);

        userRepository.delete(user);
    }

    public UserInfoResult updateUser(UserUpdateMessage message) {
        User user = findUserById(message.getId());

        user.setEmail(message.getEmail());
        user.setDateOfBirth(message.getDateOfBirth());

        userRepository.save(user);

        return UserInfoResult.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .dateOfBirth(user.getDateOfBirth())
                .build();
    }

    public void enableOrDisableUser(String userId, boolean isEnabled) {
        User user = findUserById(userId);

        user.setEnabled(isEnabled);

        this.userRepository.save(user);
    }

    public void updateCategories(CategoriesUpdateMessage message) {
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

    public List<Book> getFavourites(String userId) {
        return findUserById(userId).getFavourites();
    }


    public User findUserById(String userId) {
        Optional<User> user = this.userRepository.findById(userId);

        if(!user.isPresent()) {
            throw new ServerException(Errors.NOT_FOUND);
        }

        return user.get();
    }

    public Book findBookById(String bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        if(!book.isPresent()) {
            throw new ServerException(Errors.NOT_FOUND);
        }

        return book.get();
    }

}
