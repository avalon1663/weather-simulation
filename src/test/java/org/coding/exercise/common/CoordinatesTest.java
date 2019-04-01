package org.coding.exercise.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CoordinatesTest {

    @Test
    public void constructor() {
        Coordinates coordinates =
                new Coordinates("name", 1D, 2D, 3D);
        Assert.assertEquals("name", coordinates.getName());
        Assert.assertEquals(1D, coordinates.getLatitude(), 0D);
        Assert.assertEquals(2D, coordinates.getLongitude(), 0D);
        Assert.assertEquals(3D, coordinates.getElevation(), 0D);
    }
}