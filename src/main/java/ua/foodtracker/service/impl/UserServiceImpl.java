package ua.foodtracker.service.impl;

import ua.foodtracker.annotation.Autowired;
import ua.foodtracker.annotation.Service;
import ua.foodtracker.dao.Page;
import ua.foodtracker.dao.UserDao;
import ua.foodtracker.entity.UserEntity;
import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.service.UserService;
import ua.foodtracker.service.domain.User;
import ua.foodtracker.service.utility.EntityMapper;
import ua.foodtracker.validator.impl.UserValidator;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mindrot.jbcrypt.BCrypt.checkpw;
import static ua.foodtracker.service.utility.EntityMapper.mapUserEntityToUser;
import static ua.foodtracker.service.utility.ServiceUtility.getNumberOfPage;

@Service
public class UserServiceImpl implements UserService {
    private static final Long ITEMS_PER_PAGE = 3L;
    private static final String INCORRECT_DATA = "incorrect.data";
    public static final String DATA_KEY = "data";

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
            userValidator.putIssue("user", "login.incorrect.data");
            throw new IncorrectDataException(userValidator.getErrorMessageByIssues());
        }
    }

    @Override
    public void register(User user) {
        userValidator.validate(user);
        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            userValidator.putIssue("email", "user.already.exist");
            throw new IncorrectDataException(userValidator.getErrorMessageByIssues());
        }
        if (!userValidator.hasErrors()) {
            Integer id = userDao.save(EntityMapper.mapUserToEntityUser(user));
            if (id == null || id == 0) {
                userValidator.putIssue(DATA_KEY, INCORRECT_DATA);
                throw new IncorrectDataException(userValidator.getErrorMessageByIssues());
            }
        } else {
            throw new ValidationException(userValidator.getErrorMessageByIssues());
        }
    }

    @Override
    public void modify(User user) {
        userValidator.validate(user);
        if (!userValidator.hasErrors()) {
            if (!userDao.update(EntityMapper.mapUserToEntityUser(user))) {
                userValidator.putIssue(DATA_KEY, INCORRECT_DATA);
                throw new IncorrectDataException(userValidator.getErrorMessageByIssues());
            }
        } else {
            throw new ValidationException(userValidator.getErrorMessageByIssues());
        }
    }

    @Override
    public Optional<User> findById(String id) {
        if (id == null) {
            userValidator.putIssue(DATA_KEY, INCORRECT_DATA);
            throw new IncorrectDataException(userValidator.getErrorMessageByIssues());
        }
        try {
            return userDao.findById(Integer.parseInt(id)).map(EntityMapper::mapUserEntityToUser);
        } catch (NumberFormatException ex) {
            userValidator.putIssue(DATA_KEY, INCORRECT_DATA);
            throw new IncorrectDataException(userValidator.getErrorMessageByIssues());
        }
    }

    @Override
    public void delete(String id) {
        if (id == null) {
            userValidator.putIssue(DATA_KEY, INCORRECT_DATA);
            throw new IncorrectDataException(userValidator.getErrorMessageByIssues());
        }
        try {
            if (!userDao.deleteById(Integer.parseInt(id))) {
                userValidator.putIssue(DATA_KEY, INCORRECT_DATA);
                throw new IncorrectDataException(userValidator.getErrorMessageByIssues());
            }
        } catch (NumberFormatException ex) {
            userValidator.putIssue(DATA_KEY, INCORRECT_DATA);
            throw new IncorrectDataException(userValidator.getErrorMessageByIssues());
        }
    }

    @Override
    public List<User> getPage(Integer pageNumber) {
        return userDao.findAll(new Page(pageNumber, ITEMS_PER_PAGE)).stream().map(EntityMapper::mapUserEntityToUser).collect(Collectors.toList());
    }

    @Override
    public long getPageCount() {
        return getNumberOfPage(userDao.count(), ITEMS_PER_PAGE);
    }

    @Override
    public void setLocale(Locale locale) {
        userValidator.setLocale(locale);
    }
}
