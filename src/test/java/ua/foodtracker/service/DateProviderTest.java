package ua.foodtracker.service;

import org.junit.Before;
import org.junit.Test;
import ua.foodtracker.service.utility.DateProvider;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

public class DateProviderTest {
    private DateProvider provider;

    @Before
    public void initProvider() {
        provider = new DateProvider();
    }

    @Test
    public void getLastWeekShouldReturnDatesContains7dayAgo() {
        List<LocalDate> dates = provider.getLastWeek();
        LocalDate now = LocalDate.now();
        assertThat(dates, hasItems(now.minusDays(7), now));
    }

    @Test
    public void parseOrCurrentDateShouldReturnCurrentDate() {
        assertThat(provider.parseOrCurrentDate(null), equalTo(LocalDate.now()));
    }

    @Test
    public void parseOrCurrentDateShouldReturnCurrentDateCase2() {
        assertThat(provider.parseOrCurrentDate("asss"), equalTo(LocalDate.now()));
    }

    @Test
    public void parseOrCurrentDateShouldReturnCurrentDateByParam() {
        assertThat(provider.parseOrCurrentDate("2020-01-01"), equalTo(LocalDate.of(2020, 1, 1)));
    }
}
