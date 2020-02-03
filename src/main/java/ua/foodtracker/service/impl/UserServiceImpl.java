package ua.foodtracker.service.impl;

import ua.foodtracker.annotation.Autowired;
import ua.foodtracker.annotation.Service;
import ua.foodtracker.dao.Page;
import ua.foodtracker.dao.UserDao;
import ua.foodtracker.entity.User;
import ua.foodtracker.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.mindrot.jbcrypt.BCrypt.checkpw;

@Service
public class UserServiceImpl implements UserService {
    public static final Long ITEMS_PER_PAGE = 20L;

    @Autowired
    private UserDao dao;

    @Override
    public User login(String email, String pass) {
        Optional<User> userByEmail = dao.findByEmail(email);
        return userByEmail.isPresent() && checkpw(pass, userByEmail.get().getPassword()) ?
                userByEmail.get() : null;
    }

    @Override
    public boolean register(User user) {
        Integer id = dao.save(user);
        return id != null && id != 0;
    }

    @Override
    public boolean modify(User user) {
        return dao.update(user);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public boolean delete(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public List<User> getPage(Integer pageNumber) {
        return dao.findAll(new Page(pageNumber, ITEMS_PER_PAGE));
    }

    @Override
    public Long getPageCount() {
        Long count = dao.count();
        return count % ITEMS_PER_PAGE == 0 ? count / ITEMS_PER_PAGE : count / ITEMS_PER_PAGE + 1;
    }
}
