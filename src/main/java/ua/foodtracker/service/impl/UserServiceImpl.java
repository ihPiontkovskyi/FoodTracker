package ua.foodtracker.service.impl;

import ua.foodtracker.annotation.Autowired;
import ua.foodtracker.annotation.Service;
import ua.foodtracker.dao.UserDao;
import ua.foodtracker.domain.User;
import ua.foodtracker.entity.UserEntity;
import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.service.UserService;
import ua.foodtracker.validator.UserValidator;

import java.util.Optional;

import static org.mindrot.jbcrypt.BCrypt.checkpw;
import static ua.foodtracker.service.utility.EntityMapper.mapUserEntityToUser;
import static ua.foodtracker.service.utility.EntityMapper.mapUserToEntityUser;
import static ua.foodtracker.service.utility.ServiceUtility.addByType;
import static ua.foodtracker.service.utility.ServiceUtility.modifyByType;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserValidator userValidator;

    @Override
    public User login(String email, String pass) {
        Optional<UserEntity> userByEmail = userDao.findByEmail(email);
        if (userByEmail.isPresent() && checkpw(pass, userByEmail.get().getPassword())) {
            return mapUserEntityToUser(userByEmail.get());
        } else {
            throw new IncorrectDataException("login.incorrect.data");
        }
    }

    @Override
    public void register(User user) {
        userValidator.validate(user);
        userValidator.validatePassword(user.getPassword());
        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            throw new IncorrectDataException("user.already.exist");
        }
        addByType(user, userValidator, obj -> userDao.save(mapUserToEntityUser(obj)));
    }

    @Override
    public void modify(User user) {
        modifyByType(user, userValidator, obj -> userDao.update(mapUserToEntityUser(obj)));
    }
}
