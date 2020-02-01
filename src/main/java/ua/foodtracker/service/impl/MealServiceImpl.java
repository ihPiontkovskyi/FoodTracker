package ua.foodtracker.service.impl;

import ua.foodtracker.annotation.Autowired;
import ua.foodtracker.annotation.Service;
import ua.foodtracker.dao.MealDao;
import ua.foodtracker.dao.Page;
import ua.foodtracker.entity.Meal;
import ua.foodtracker.service.MealService;

import java.util.List;
import java.util.Optional;

@Service
public class MealServiceImpl implements MealService {
    public static final Long ITEMS_PER_PAGE = 20L;

    @Autowired
    MealDao dao;

    @Override
    public List<Meal> getPage(Integer pageNumber, Integer userId) {
        return dao.findAll(new Page(pageNumber,ITEMS_PER_PAGE));
    }

    @Override
    public Long getPageCount() {
        Long count = dao.count();
        return count % ITEMS_PER_PAGE == 0 ? count : count + 1;
    }

    @Override
    public boolean add(Meal meal) {
        return dao.save(meal) != 0;
    }

    @Override
    public boolean delete(Meal meal) {
        return dao.deleteById(meal.getId());
    }

    @Override
    public boolean modify(Meal meal) {
        return dao.update(meal);
    }

    @Override
    public Optional<Meal> findById(Integer id) {
        return dao.findById(id);
    }
}
