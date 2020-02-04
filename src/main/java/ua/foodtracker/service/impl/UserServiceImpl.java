package ua.foodtracker.service.impl;

import ua.foodtracker.annotation.Autowired;
import ua.foodtracker.annotation.Service;
import ua.foodtracker.dao.Page;
import ua.foodtracker.dao.UserDao;
import ua.foodtracker.dao.entity.User;
import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.raw.type.entity.RawUser;
import ua.foodtracker.service.UserService;
import ua.foodtracker.service.utility.ServiceUtility;
import ua.foodtracker.validator.impl.UserValidator;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.mindrot.jbcrypt.BCrypt.checkpw;
import static ua.foodtracker.dao.utility.EntityMapper.mapRawUserToEntityUser;
import static ua.foodtracker.service.utility.ServiceUtility.getErrorMessageByIssues;

@Service
public class UserServiceImpl implements UserService {
    private static final Long ITEMS_PER_PAGE = 20L;
    private static final String INCORRECT_DATA = "incorrect.data";

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserValidator userValidator;

    @Override
    public User login(String email, String pass) {
        Optional<User> userByEmail = userDao.findByEmail(email);
        if (userByEmail.isPresent() && checkpw(pass, userByEmail.get().getPassword())) {
            return userByEmail.get();
        } else {
            userValidator.putIssue("user", "login.incorrect.data");
            throw new IncorrectDataException(getErrorMessageByIssues(userValidator.getMessages(), userValidator.getLocale()));
        }
    }

    @Override
    public void register(RawUser user) {
        userValidator.validate(user);
        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            userValidator.putIssue("email", "user.already.exist");
            throw new IncorrectDataException(getErrorMessageByIssues(userValidator.getMessages(), userValidator.getLocale()));
        }
        if (!userValidator.hasErrors()) {
            Integer id = userDao.save(mapRawUserToEntityUser(user));
            if (id == null || id == 0) {
                userValidator.putIssue("data", INCORRECT_DATA);
                throw new IncorrectDataException(getErrorMessageByIssues(userValidator.getMessages(), userValidator.getLocale()));
            }
        } else {
            throw new ValidationException(getErrorMessageByIssues(userValidator.getMessages(), userValidator.getLocale()));
        }
    }

    @Override
    public void modify(RawUser user) {
        userValidator.validate(user);
        if (!userValidator.hasErrors()) {
            if (!userDao.update(mapRawUserToEntityUser(user))) {
                userValidator.putIssue("data", INCORRECT_DATA);
                throw new IncorrectDataException(getErrorMessageByIssues(userValidator.getMessages(), userValidator.getLocale()));
            }
        } else {
            throw new ValidationException(getErrorMessageByIssues(userValidator.getMessages(), userValidator.getLocale()));
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public void delete(Integer id) {
        if (!userDao.deleteById(id)) {
            userValidator.putIssue("data", INCORRECT_DATA);
            throw new IncorrectDataException(getErrorMessageByIssues(userValidator.getMessages(), userValidator.getLocale()));
        }
    }

    @Override
    public List<User> getPage(Integer pageNumber) {
        return userDao.findAll(new Page(pageNumber, ITEMS_PER_PAGE));
    }

    @Override
    public long getPageCount() {
        return ServiceUtility.getNumberOfPage(userDao.count(),ITEMS_PER_PAGE);
    }

    @Override
    public void setLocale(Locale locale) {
        userValidator.setLocale(locale);
    }
}
