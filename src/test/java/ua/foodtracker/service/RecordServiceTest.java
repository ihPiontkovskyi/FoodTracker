package ua.foodtracker.service;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.dao.RecordDao;
import ua.foodtracker.domain.Gender;
import ua.foodtracker.domain.Lifestyle;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.domain.Record;
import ua.foodtracker.domain.Role;
import ua.foodtracker.domain.User;
import ua.foodtracker.entity.RecordEntity;
import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.service.impl.RecordServiceImpl;
import ua.foodtracker.service.utility.EntityMapper;
import ua.foodtracker.validator.impl.RecordValidatorImpl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecordServiceTest {
    private static final User USER = User.builder()
            .withId(1)
            .withGender(Gender.MALE)
            .withLifestyle(Lifestyle.NOT_SELECTED)
            .withRole(Role.USER)
            .withPassword("$2a$10$NxW3cyRxP33QWbEeAUu2b.QSShHLyYHKtUHrkG5vyISuZzLXksMTa")
            .withWeight(90)
            .withHeight(190)
            .withBirthday(LocalDate.now().minusDays(80))
            .withLastName("lastName")
            .withFirstName("firstName")
            .withEmail("email@mail.com")
            .build();
    private static final Meal MEAL = Meal.builder()
            .withId(1)
            .withWeight(100)
            .withWater(100)
            .withProtein(100)
            .withUser(USER)
            .withFat(100)
            .withCarbohydrates(100)
            .withName("name")
            .build();
    private static final Record RECORD = Record.builder()
            .withId(1)
            .withUserId(1)
            .withDate(LocalDate.now())
            .withMeal(MEAL)
            .build();

    private static final RecordEntity RECORD_ENTITY = EntityMapper.mapRecordToEntityRecord(RECORD);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private RecordDao recordDao;
    @Mock
    private RecordValidatorImpl recordValidator;

    @InjectMocks
    private RecordServiceImpl recordService;

    @After
    public void resetMock() {
        reset(recordDao);
    }

    @Test
    public void findByIdShouldntThrowException() {
        when(recordDao.findById(USER.getId())).thenReturn(Optional.of(RECORD_ENTITY));

        recordService.findById(USER.getId().toString());

        verify(recordDao).findById(USER.getId());
    }

    @Test
    public void findByIdShouldIncorrectDataExceptionCase1() {
        when(recordDao.findById(USER.getId())).thenReturn(Optional.empty());

        exception.expect(IncorrectDataException.class);
        recordService.findById(null);

        verify(recordDao).findById(USER.getId());
    }

    @Test
    public void findByIdShouldIncorrectDataExceptionCase2() {
        when(recordDao.findById(USER.getId())).thenReturn(Optional.empty());

        exception.expect(IncorrectDataException.class);
        recordService.findById("ass");

        verify(recordDao).findById(USER.getId());
    }

    @Test
    public void deleteShouldntThrowException() {
        when(recordDao.deleteById(RECORD.getId())).thenReturn(true);

        recordService.delete(RECORD.getId().toString());

        verify(recordDao).deleteById(RECORD.getId());
    }

    @Test
    public void deleteShouldIncorrectDataException() {
        when(recordDao.deleteById(RECORD.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        recordService.delete(RECORD.getId().toString());

        verify(recordDao).deleteById(RECORD.getId());
    }

    @Test
    public void deleteShouldIncorrectDataExceptionCase2() {
        when(recordDao.deleteById(RECORD.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        recordService.delete(null);

        verify(recordDao).deleteById(RECORD.getId());
    }

    @Test
    public void deleteShouldIncorrectDataExceptionCase3() {
        when(recordDao.deleteById(RECORD.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        recordService.delete("ass");

        verify(recordDao).deleteById(RECORD.getId());
    }

    @Test
    public void addShouldThrowIncorrectDataExceptionCase() {
        doNothing().when(recordValidator).validate(RECORD);

        exception.expect(IncorrectDataException.class);
        recordService.add(RECORD);

        verify(recordValidator).validate(RECORD);
    }

    @Test
    public void addShouldThrowIncorrectDataException() {
        doNothing().when(recordValidator).validate(RECORD);
        when(recordDao.save(any())).thenReturn(0);

        exception.expect(IncorrectDataException.class);
        recordService.add(RECORD);

        verify(recordValidator).validate(RECORD);
        verify(recordDao).save(any());
    }

    @Test
    public void addShouldntThrowException() {
        doNothing().when(recordValidator).validate(RECORD);
        when(recordDao.save(any())).thenReturn(RECORD.getId());

        recordService.add(RECORD);

        verify(recordValidator).validate(RECORD);
        verify(recordDao).save(any());
    }

    @Test
    public void getRecordsByDateShouldReturnList() {
        when(recordDao.findByUserIdAndDate(eq(1), any())).thenReturn(Collections.emptyList());

        assertEquals(Collections.EMPTY_LIST, recordService.getRecordsByDate(USER, LocalDate.now().toString()));

        verify(recordDao).findByUserIdAndDate(eq(1), any());
    }
}
