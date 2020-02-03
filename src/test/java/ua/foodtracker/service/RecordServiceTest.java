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
import ua.foodtracker.entity.Meal;
import ua.foodtracker.entity.Record;
import ua.foodtracker.service.impl.RecordServiceImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecordServiceTest {

    public static final Record RECORD = Record.builder()
            .withId(1)
            .withDate(new Date(System.currentTimeMillis()))
            .withMeal(Meal.builder().build())
            .withUserId(1)
            .build();

    @Mock
    private RecordDao dao;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @InjectMocks
    private RecordServiceImpl recordService;

    @After
    public void resetMock() {
        reset(dao);
    }

    @Test
    public void addShouldReturnTrue() {
        when(dao.save(RECORD)).thenReturn(RECORD.getId());
        assertTrue(recordService.add(RECORD));
        verify(dao).save(RECORD);
    }

    @Test
    public void deleteShouldReturnTrue() {
        when(dao.deleteById(RECORD.getId())).thenReturn(true);
        assertTrue(recordService.delete(RECORD.getId()));
        verify(dao).deleteById(RECORD.getId());
    }

    @Test
    public void addShouldReturnFalse() {
        when(dao.save(RECORD)).thenReturn(null);
        assertFalse(recordService.add(RECORD));
        verify(dao).save(RECORD);
    }

    @Test
    public void addShouldReturnFalse0() {
        when(dao.save(RECORD)).thenReturn(0);
        assertFalse(recordService.add(RECORD));
        verify(dao).save(RECORD);
    }

    @Test
    public void deleteShouldReturnFalse() {
        when(dao.deleteById(RECORD.getId())).thenReturn(false);
        assertFalse(recordService.delete(RECORD.getId()));
        verify(dao).deleteById(RECORD.getId());
    }

    @Test
    public void findByIdShouldReturnMeal() {
        when(dao.findById(RECORD.getId())).thenReturn(Optional.of(RECORD));
        assertTrue(recordService.findById(RECORD.getId()).isPresent());
        verify(dao).findById(RECORD.getId());
    }

    @Test
    public void findByIdShouldReturnOptionalEmpty() {
        when(dao.findById(RECORD.getId())).thenReturn(Optional.empty());
        assertFalse(recordService.findById(RECORD.getId()).isPresent());
        verify(dao).findById(RECORD.getId());
    }

    @Test
    public void modifyShouldReturnTrue() {
        when(dao.update(RECORD)).thenReturn(true);
        assertTrue(recordService.modify(RECORD));
        verify(dao).update(RECORD);
    }

    @Test
    public void modifyShouldReturnFalse() {
        when(dao.update(RECORD)).thenReturn(false);
        assertFalse(recordService.modify(RECORD));
        verify(dao).update(RECORD);
    }

    @Test
    public void getPageShouldReturnPage() {
        List<Record> res = new ArrayList<>();
        res.add(RECORD);
        when(dao.findByUserIdAndDate(RECORD.getUserId(), RECORD.getDate())).thenReturn(res);
        assertFalse(recordService.getPage(RECORD.getUserId(), RECORD.getDate()).isEmpty());
        verify(dao).findByUserIdAndDate(RECORD.getUserId(), RECORD.getDate());
    }
}
