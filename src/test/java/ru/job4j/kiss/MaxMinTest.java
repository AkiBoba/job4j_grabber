package ru.job4j.kiss;

import junit.framework.TestCase;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Vladimir Likhachev
 */
public class MaxMinTest extends TestCase {

    @Parameterized.Parameters

    public void testMax() {
        MaxMin maxMin = new MaxMin();
        List<Integer> list = Arrays.asList(5, 3, 8, 9, 4, 2, 10);
        assertThat(maxMin.max(list, Comparator.comparingInt(o -> o)), is(10));
    }

    public void testMin() {
        MaxMin maxMin = new MaxMin();
        List<Integer> list = Arrays.asList(5, 3, 8, 9, 4, 2, 10);
        assertThat(maxMin.min(list, Comparator.comparingInt(o -> o)), is(2));
    }
}