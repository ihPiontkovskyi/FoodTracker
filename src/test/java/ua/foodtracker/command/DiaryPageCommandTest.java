package ua.foodtracker.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.user.DiaryPageCommand;
import ua.foodtracker.dto.DailySumsTransferObject;
import ua.foodtracker.entity.Gender;
import ua.foodtracker.entity.Lifestyle;
import ua.foodtracker.entity.Role;
import ua.foodtracker.service.RecordService;
import ua.foodtracker.service.domain.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Locale;

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
        when(session.getAttribute("locale")).thenReturn(Locale.getDefault());
        when(session.getAttribute("currentDate")).thenReturn(LocalDate.now());
        when(session.getAttribute("user")).thenReturn(USER);
        when(service.getRecordsByDate(USER.getId(), LocalDate.now())).thenReturn(Collections.emptyList());
        doNothing().when(request).setAttribute(eq("records"), any());
        doNothing().when(request).setAttribute(eq("dateSums"), any());

        String url = diaryPageCommand.execute(request);

        verify(request).getServletContext();
        verify(context).getAttribute(anyString());
        verify(request,times(4)).getSession(false);
        verify(session,times(4)).getAttribute(anyString());
        verify(service).getRecordsByDate(USER.getId(),LocalDate.now());
        verify(request, times(2)).setAttribute(anyString(),any());
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
                .build();
    }
}
