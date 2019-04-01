package org.coding.exercise.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ParameterRangeTest {

    @Test
    public void constructor() {
        ParameterRange range =
                new ParameterRange(1D, 10D);
        Assert.assertEquals(1D, range.getMinimum(), 0D);
        Assert.assertEquals(10D, range.getMaximum(), 0D);
    }
}