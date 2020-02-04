package ua.foodtracker.service.impl;

import ua.foodtracker.annotation.Autowired;
import ua.foodtracker.annotation.Service;
import ua.foodtracker.dao.Page;
import ua.foodtracker.dao.UserDao;
import ua.foodtracker.entity.User;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.service.UserService;
import ua.foodtracker.service.utility.ServiceUtility;
import ua.foodtracker.validator.impl.UserValidator;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.mindrot.jbcrypt.BCrypt.checkpw;
import static ua.foodtracker.service.utility.ServiceUtility.getErrorMessageByIssues;
import static ua.foodtracker.service.utility.ServiceUtility.getPageNumberByString;

@Service
public class UserServiceImpl implements UserService {
    public static final Long ITEMS_PER_PAGE = 20L;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserValidator userValidator;

    @Override
    public User login(String email, String pass) {
        Optional<User> userByEmail = userDao.findByEmail(email);
        return userByEmail.isPresent() && checkpw(pass, userByEmail.get().getPassword()) ?
                userByEmail.get() : null;
    }

    @Override
    public boolean register(User user) {
        userValidator.validate(user);
        if (userValidator.hasErrors()) {
            Integer id = userDao.save(user);
            return id != null && id != 0;
        } else {
            throw new ValidationException(getErrorMessageByIssues(userValidator.getMessages(),userValidator.getLocale()));
        }
    }

    @Override
    public boolean modify(User user) {
        userValidator.validate(user);
        if (userValidator.hasErrors()) {
            return userDao.update(user);
        } else {
            throw new ValidationException(getErrorMessageByIssues(userValidator.getMessages(),userValidator.getLocale()));
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public boolean delete(Integer id) {
        return userDao.deleteById(id);
    }

    @Override
    public List<User> getPage(String pageNumber) {
        return userDao.findAll(new Page(getPageNumberByString(pageNumber, getPageCount()), ITEMS_PER_PAGE));
    }

    @Override
    public long getPageCount() {
        return ServiceUtility.getNumberOfPage(userDao.count());
    }

    @Override
    public void setLocale(Locale locale) {
        userValidator.setLocale(locale);
    }
}
