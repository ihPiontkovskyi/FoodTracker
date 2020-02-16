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
import ua.foodtracker.domain.Role;
import ua.foodtracker.entity.GenderEntity;
import ua.foodtracker.entity.LifestyleEntity;
import ua.foodtracker.entity.RecordEntity;
import ua.foodtracker.entity.RoleEntity;
import ua.foodtracker.exception.IncorrectDataException;
import ua.foodtracker.exception.ValidationException;
import ua.foodtracker.domain.Meal;
import ua.foodtracker.domain.Record;
import ua.foodtracker.domain.User;
import ua.foodtracker.service.impl.RecordServiceImpl;
import ua.foodtracker.service.utility.EntityMapper;
import ua.foodtracker.validator.impl.RecordValidator;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Locale;
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
    public static final User RAW_USER = User.builder()
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
    public static final Meal RAW_MEAL = Meal.builder()
            .withId(1)
            .withWeight(100)
            .withWater(100)
            .withProtein(100)
            .withUser(RAW_USER)
            .withFat(100)
            .withCarbohydrates(100)
            .withName("name")
            .build();
    public static final Record RAW_RECORD = Record.builder()
            .withId(1)
            .withUserId(1)
            .withDate(LocalDate.now())
            .withMeal(RAW_MEAL)
            .build();

    public static final RecordEntity RECORD_ENTITY = EntityMapper.mapRecordToEntityRecord(RAW_RECORD);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private RecordDao recordDao;
    @Mock
    private RecordValidator recordValidator;

    @InjectMocks
    private RecordServiceImpl recordService;

    @After
    public void resetMock() {
        reset(recordDao);
    }

    @Test
    public void modifyShouldntThrowException() {
        doNothing().when(recordValidator).validate(RAW_RECORD);
        when(recordValidator.hasErrors()).thenReturn(false);
        when(recordDao.update(any())).thenReturn(true);

        recordService.modify(RAW_RECORD);

        verify(recordValidator).validate(RAW_RECORD);
        verify(recordValidator).hasErrors();
        verify(recordDao).update(any());
    }

    @Test
    public void modifyShouldThrowValidationException() {
        doNothing().when(recordValidator).validate(RAW_RECORD);
        when(recordValidator.hasErrors()).thenReturn(true);

        exception.expect(ValidationException.class);
        recordService.modify(RAW_RECORD);

        verify(recordValidator).validate(RAW_RECORD);
        verify(recordValidator).hasErrors();
    }

    @Test
    public void modifyShouldThrowIncorrectDataException() {
        doNothing().when(recordValidator).validate(RAW_RECORD);
        when(recordValidator.hasErrors()).thenReturn(false);
        when(recordDao.update(any())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        recordService.modify(RAW_RECORD);

        verify(recordValidator).validate(RAW_RECORD);
        verify(recordValidator).hasErrors();
    }

    @Test
    public void findByIdShouldntThrowException() {
        when(recordDao.findById(RAW_USER.getId())).thenReturn(Optional.of(RECORD_ENTITY));

        recordService.findById(RAW_USER.getId().toString());

        verify(recordDao).findById(RAW_USER.getId());
    }

    @Test
    public void findByIdShouldIncorrectDataExceptionCase1() {
        when(recordDao.findById(RAW_USER.getId())).thenReturn(Optional.empty());

        exception.expect(IncorrectDataException.class);
        recordService.findById(null);

        verify(recordDao).findById(RAW_USER.getId());
    }

    @Test
    public void findByIdShouldIncorrectDataExceptionCase2() {
        when(recordDao.findById(RAW_USER.getId())).thenReturn(Optional.empty());

        exception.expect(IncorrectDataException.class);
        recordService.findById("ass");

        verify(recordDao).findById(RAW_USER.getId());
    }

    @Test
    public void deleteShouldntThrowException() {
        when(recordDao.deleteById(RAW_RECORD.getId())).thenReturn(true);

        recordService.delete(RAW_RECORD.getId().toString());

        verify(recordDao).deleteById(RAW_RECORD.getId());
    }

    @Test
    public void deleteShouldIncorrectDataException() {
        when(recordDao.deleteById(RAW_RECORD.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        recordService.delete(RAW_RECORD.getId().toString());

        verify(recordDao).deleteById(RAW_RECORD.getId());
    }

    @Test
    public void deleteShouldIncorrectDataExceptionCase2() {
        when(recordDao.deleteById(RAW_RECORD.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        recordService.delete(null);

        verify(recordDao).deleteById(RAW_RECORD.getId());
    }

    @Test
    public void deleteShouldIncorrectDataExceptionCase3() {
        when(recordDao.deleteById(RAW_RECORD.getId())).thenReturn(false);

        exception.expect(IncorrectDataException.class);
        recordService.delete("ass");

        verify(recordDao).deleteById(RAW_RECORD.getId());
    }

    @Test
    public void setLocaleTest() {
        Locale current = Locale.getDefault();
        recordService.setLocale(current);

        verify(recordValidator).setLocale(current);
    }

    @Test
    public void addShouldThrowValidationException() {
        doNothing().when(recordValidator).validate(RAW_RECORD);
        when(recordValidator.hasErrors()).thenReturn(true);

        exception.expect(ValidationException.class);
        recordService.add(RAW_RECORD);

        verify(recordValidator).validate(RAW_RECORD);
        verify(recordValidator).hasErrors();
    }

    @Test
    public void addShouldThrowIncorrectDataException() {
        doNothing().when(recordValidator).validate(RAW_RECORD);
        when(recordValidator.hasErrors()).thenReturn(false);
        when(recordDao.save(any())).thenReturn(0);

        exception.expect(IncorrectDataException.class);
        recordService.add(RAW_RECORD);

        verify(recordValidator).validate(RAW_RECORD);
        verify(recordValidator).hasErrors();
        verify(recordDao).save(any());
    }

    @Test
    public void addShouldntThrowException() {
        doNothing().when(recordValidator).validate(RAW_RECORD);
        when(recordValidator.hasErrors()).thenReturn(false);
        when(recordDao.save(any())).thenReturn(RAW_RECORD.getId());

        recordService.add(RAW_RECORD);

        verify(recordValidator).validate(RAW_RECORD);
        verify(recordValidator).hasErrors();
        verify(recordDao).save(any());
    }

    @Test
    public void getRecordsByDateShouldReturnList() {
        when(recordDao.findByUserIdAndDate(eq(1), any())).thenReturn(Collections.emptyList());

        assertEquals(Collections.EMPTY_LIST, recordService.getRecordsByDate(1, LocalDate.now().toString()));

        verify(recordDao).findByUserIdAndDate(eq(1), any());
    }
}
