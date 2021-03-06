package ua.foodtracker.command.record;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.record.DiaryPageCommand;
import ua.foodtracker.domain.DailySums;
import ua.foodtracker.domain.Gender;
import ua.foodtracker.domain.Lifestyle;
import ua.foodtracker.domain.Role;
import ua.foodtracker.domain.User;
import ua.foodtracker.domain.UserGoal;
import ua.foodtracker.service.RecordService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiaryPageCommandTest {

    private static final User USER = initUser();

    private static final DailySums DAILY_SUMS = initDailySums();

    @Mock
    private HttpServletRequest request;

    @Mock
    private ServletContext context;
    @Mock
    private HttpSession session;
    @Mock
    private RecordService service;
    @InjectMocks
    private DiaryPageCommand diaryPageCommand;

    @Test
    public void executeShouldReturnUrlSuccessfully() {
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(eq("ua.foodtracker.service.RecordService"))).thenReturn(service);
        when(request.getSession(false)).thenReturn(session);
        when(request.getParameter("date")).thenReturn(LocalDate.now().toString());
        when(session.getAttribute("user")).thenReturn(USER);
        when(service.calculateDailySums(USER, LocalDate.now().toString())).thenReturn(DAILY_SUMS);
        when(service.getRecordsByDate(USER, LocalDate.now().toString())).thenReturn(Collections.emptyList());
        doNothing().when(request).setAttribute(eq("records"), any());
        doNothing().when(request).setAttribute(eq("dateSums"), any());

        String url = diaryPageCommand.execute(request);

        verify(request, times(2)).getServletContext();
        verify(context, times(2)).getAttribute(anyString());
        verify(request, times(2)).getSession(false);
        verify(session).getAttribute(anyString());
        verify(service).getRecordsByDate(USER, LocalDate.now().toString());
        verify(service).calculateDailySums(USER, LocalDate.now().toString());
        verify(request, times(4)).setAttribute(anyString(), any());
    }

    private static User initUser() {
        return User.builder()
                .withId(1)
                .withGender(Gender.MALE)
                .withLifestyle(Lifestyle.NOT_SELECTED)
                .withRole(Role.USER)
                .withPassword("$2a$10$NxW3cyRxP33QWbEeAUu2b.QSShHLyYHKtUHrkG5vyISuZzLXksMTa")
                .withWeight(90)
                .withHeight(190)
                .withBirthday(LocalDate.now().minusYears(30))
                .withLastName("lastName")
                .withFirstName("firstName")
                .withEmail("email@mail.com")
                .withUserGoal(UserGoal.builder()
                        .withId(1)
                        .withDailyCarbohydrateGoal(2)
                        .withDailyEnergyGoal(3)
                        .withDailyFatGoal(1)
                        .withDailyProteinGoal(1)
                        .withDailyWaterGoal(2)
                        .build())
                .build();
    }

    private static DailySums initDailySums() {
        return DailySums.builder()
                .withSumWater(10)
                .withSumFat(10)
                .withSumEnergy(10)
                .withSumProtein(19)
                .withSumCarbohydrate(19)
                .build();
    }
}
