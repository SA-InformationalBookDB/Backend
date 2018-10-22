package hu.bme.szarch.ibdb.service;

import hu.bme.szarch.ibdb.domain.User;
import hu.bme.szarch.ibdb.error.Errors;
import hu.bme.szarch.ibdb.error.ServerException;
import hu.bme.szarch.ibdb.repository.UserRepository;
import hu.bme.szarch.ibdb.service.dto.user.UserInfoResult;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInfoResult findById(String id) {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()) {
            throw new ServerException(Errors.NOT_FOUND);
        }

        return UserInfoResult.builder()
                .id(user.get().getId())
                .email(user.get().getEmail())
                .role(user.get().getRole())
                .build();
    }

}
