package ua.foodtracker.service;

import ua.foodtracker.annotation.Transactional;
import ua.foodtracker.dao.entity.User;
import ua.foodtracker.raw.type.entity.RawUser;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface UserService {
    User login(String email, String pass);

    @Transactional
    void register(RawUser user);

    @Transactional
    void modify(RawUser user);

    Optional<User> findById(Integer id);

    void delete(Integer id);

    List<User> getPage(Integer pageNumber);

    long getPageCount();

    void setLocale(Locale locale);
}
