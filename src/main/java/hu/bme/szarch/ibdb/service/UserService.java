package hu.bme.szarch.ibdb.service;

import hu.bme.szarch.ibdb.domain.Category;
import hu.bme.szarch.ibdb.domain.User;
import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.repository.UserRepository;
import hu.bme.szarch.ibdb.service.dto.user.UserResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResult getUserInfo(String id) {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()) {
            throw new ServerException(Errors.NOT_FOUND);
        }

        return UserResult.builder()
                .id(user.get().getId())
                .email(user.get().getEmail())
                .role(user.get().getRole())
                .build();
    }

    public List<Category> getCategoriesForUser(String id) {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()) {
            throw new ServerException(Errors.NOT_FOUND);
        }

        return user.get().getCategories();
    }

    public void deleteUser(String userId) {
        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()) {
            throw new ServerException(Errors.NOT_FOUND);
        }

        userRepository.delete(user.get());
    }


    public void enableOrDisableUser(String userId, boolean isEnabled) {
        Optional<User> user = this.userRepository.findById(userId);

        if(!user.isPresent()) {
            throw new ServerException(Errors.NOT_FOUND);
        }
        user.get().setEnabled(isEnabled);

        this.userRepository.save(user.get());
    }

}
