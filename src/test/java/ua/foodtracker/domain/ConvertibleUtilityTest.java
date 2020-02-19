package ua.foodtracker.domain;

import org.junit.Test;
import ua.foodtracker.domain.utility.ConvertibleUtility;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConvertibleUtilityTest {

    @Test
    public void convertToJsonArrayShouldReturnEmptyArray() {
        String arrayJson = ConvertibleUtility.convertToJsonArray(Collections.emptyList());
        assertThat(arrayJson, equalTo("[]"));
    }

    @Test
    public void convertToJsonArrayShouldReturnArrayWithOneElem() {
        String arrayJson = ConvertibleUtility.convertToJsonArray(Collections.singletonList(new MealInfo(1, "label")));
        assertThat(arrayJson, equalTo("[{\"id\":1,\"label\":\"label\"}]"));
    }
}
