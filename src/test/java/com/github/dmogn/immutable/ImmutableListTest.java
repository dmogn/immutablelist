package com.github.dmogn.immutable;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImmutableListTest {

    @Test
    public void testReverse() {
        final ImmutableList<String> stringArray = new ImmutableList<>(Arrays.asList("bond", "equity", "gold", "real estate"));

        final ImmutableList<String> reversedArray = stringArray.reverse();

        Assertions.assertTrue(Arrays.equals(new String[]{"real estate", "gold", "equity", "bond"}, reversedArray.toArray()));
    }

    @Test
    public void testFoldLeftInteger() {
        final ImmutableList<Integer> intArray = new ImmutableList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        final Integer foldLeftResult = intArray.foldLeft((x, y) -> x + y);

        Assertions.assertEquals(55, foldLeftResult);

        final ImmutableList<Integer> intArrayReversed = intArray.reverse();

        final Integer foldLeftResultReversed = intArrayReversed.foldLeft((x, y) -> x + y);

        Assertions.assertEquals(55, foldLeftResultReversed);
    }

    @Test
    public void testFoldLeftString() {
        final ImmutableList<String> stringArray = new ImmutableList<>(Arrays.asList("sub", "way", " station"));

        final String foldLeftResult = stringArray.foldLeft((x, y) -> x + y);

        Assertions.assertEquals("subway station", foldLeftResult);
    }

    @Test
    public void testFilter() {
        final ImmutableList<Integer> array = new ImmutableList<>(Arrays.asList(97, 44, 67, 3, 22, 90, 1, 77, 98, 1078, 6, 64, 6, 79, 42));
        // filter even
        final ImmutableList<Integer> filteredArray = array.filter(i -> i % 2 == 0);

        Assertions.assertTrue(Arrays.equals(new Integer[]{44, 22, 90, 98, 1078, 6, 64, 6, 42}, filteredArray.toArray()));
    }

    @Test
    public void testMap() {
        final ImmutableList<Integer> array = new ImmutableList<>(Arrays.asList(97, 44, 67, 3, 22, 90, 1, 77, 98, 1078, 6, 64, 6, 79, 42));

        final ImmutableList<Double> arrayDouble = array.map(value -> value.doubleValue());

        Assertions.assertTrue(Arrays.equals(new Double[]{97., 44., 67., 3., 22., 90., 1., 77., 98., 1078., 6., 64., 6., 79., 42.},
                arrayDouble.toArray()));
    }

}
