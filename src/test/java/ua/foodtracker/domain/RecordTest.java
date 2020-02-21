package ua.foodtracker.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class RecordTest {

    private static final User USER = initUser();

    @Parameter
    public Meal meal;
    @Parameter(1)
    public Integer weight;
    @Parameter(2)
    public Integer energyExpected;
    @Parameter(3)
    public Integer fatExpected;
    @Parameter(4)
    public Integer waterExpected;
    @Parameter(5)
    public Integer proteinExpected;
    @Parameter(6)
    public Integer carbohydrateExpected;

    private Record record;

    @Before
    public void initRecord() {
        record = Record.builder()
                .withId(1)
                .withMeal(meal)
                .withWeight(weight)
                .withDate(LocalDate.now())
                .build();
    }

    @Parameters(name = "Meal={0}, weight={1}")
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                {Meal.builder()
                        .withCarbohydrates(10)
                        .withFat(10)
                        .withProtein(10)
                        .withWeight(100)
                        .withWater(10)
                        .withName("name")
                        .build(), 200, 340, 20, 20, 20, 20},
                {Meal.builder()
                        .withCarbohydrates(0)
                        .withFat(0)
                        .withProtein(0)
                        .withWeight(120)
                        .withWater(0)
                        .withName("name")
                        .build(), 200, 0, 0, 0, 0, 0},
                {Meal.builder()
                        .withCarbohydrates(10)
                        .withFat(12)
                        .withProtein(14)
                        .withWeight(80)
                        .withWater(10)
                        .withName("name")
                        .build(), 80, 204, 12, 10, 14, 10},
        });
    }

    @Test
    public void testRecordCalculates() {
        assertThat(record.calculateEnergy(), equalTo(energyExpected));
        assertThat(record.calculateCarbohydrate(), equalTo(carbohydrateExpected));
        assertThat(record.calculateFat(), equalTo(fatExpected));
        assertThat(record.calculateProtein(), equalTo(proteinExpected));
        assertThat(record.calculateWater(), equalTo(waterExpected));
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
                .withBirthday(LocalDate.now().minusDays(80))
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
}
