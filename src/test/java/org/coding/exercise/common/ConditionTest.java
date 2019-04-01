package org.coding.exercise.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Random;

@RunWith(JUnit4.class)
public class ConditionTest {

    @Test
    public void randomAccess() {
        Random random = new Random();
        int index = random
                .nextInt(Condition.values().length - 1);
        Condition next = Condition.values()[index];

        switch (index) {
            case 0:
                Assert.assertEquals(next, Condition.SUNNY);
                break;
            case 1:
                Assert.assertEquals(next, Condition.RAIN);
                break;
            case 2:
                Assert.assertEquals(next, Condition.SNOW);
                break;
        }
    }
}