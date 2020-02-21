package ua.foodtracker.command.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.foodtracker.command.impl.user.ProfilePageCommand;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;

@RunWith(MockitoJUnitRunner.class)
public class ProfilePageCommandTest {
    @Mock
    private HttpServletRequest request;
    @InjectMocks
    private ProfilePageCommand profilePageCommand;

    @Test
    public void testProfilePageCommand() {
        assertThat(profilePageCommand.execute(request), equalTo("/WEB-INF/pages/user/profile.jsp"));
        verifyNoInteractions(request);
    }
}