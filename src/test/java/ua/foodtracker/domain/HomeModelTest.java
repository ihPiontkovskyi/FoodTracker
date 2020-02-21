package ua.foodtracker.domain;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class HomeModelTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM");
    private static final int WEEKS = 1;
    private static final int PERIOD = 1;
    private static final int COUNT = 8;

    private static final HomeModel model = build();

    @Test
    public void dailyGoalTest() {
        assertEquals(50, model.getDailyGoal().getDailyCarbohydratesGoal());
        assertEquals(60, model.getDailyGoal().getDailyFatGoal());
        assertEquals(70, model.getDailyGoal().getDailyProteinGoal());
        assertEquals(100, model.getDailyGoal().getDailyWaterGoal());
        assertEquals(100, model.getDailyGoal().getDailyEnergyGoal());
    }

    @Test
    public void getLabelsTest() {
        assertThat(model.getLabels(), hasItem(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM"))));
    }

    @Test
    public void getWeeklyStatTest() {
        assertThat(model.getWeeklyWaterStat(), hasItems(notNullValue()));
        assertThat(model.getWeeklyProteinStat(), hasItems(notNullValue()));
        assertThat(model.getWeeklyFatStat(), hasItems(notNullValue()));
        assertThat(model.getWeeklyEnergyStat(), hasItems(notNullValue()));
        assertThat(model.getWeeklyCarbohydrateStat(), hasItems(notNullValue()));
    }

    private static HomeModel build() {
        return HomeModel.builder()
                .withDailySums(getDailySums())
                .withWeeklyStats(getDateToDailySumsMap())
                .withLabels(getLabels())
                .withDailyGoal(HomeModel.calculateDailyGoal(getDailySums(), buildUserGoal()))
                .build();
    }

    private static Map<String, DailySums> getDateToDailySumsMap() {
        return getLabels().stream()
                .collect(Collectors.toMap(Function.identity(), string -> getDailySums()));
    }

    private static DailySums getDailySums() {
        return DailySums.builder()
                .withSumCarbohydrate(50)
                .withSumEnergy(2200)
                .withSumFat(60)
                .withSumProtein(70)
                .withSumWater(2000)
                .build();
    }

    private static List<String> getLabels() {
        return Stream.iterate(LocalDate.now().minusWeeks(WEEKS), day -> day.plusDays(PERIOD))
                .map(DATE_TIME_FORMATTER::format)
                .limit(COUNT)
                .collect(Collectors.toList());
    }

    private static UserGoal buildUserGoal() {
        return UserGoal.builder()
                .withDailyCarbohydrateGoal(100)
                .withDailyWaterGoal(2000)
                .withDailyEnergyGoal(2100)
                .withDailyFatGoal(100)
                .withDailyProteinGoal(100)
                .withId(1)
                .build();
    }
}
