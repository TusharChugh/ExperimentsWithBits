package edu.cmu.cs.cs214.hw4.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tushar on 22-Oct-16.
 */
public class LocationTest {
    //CHECKSTYLE:OFF
    Location location;
    @Before
    public void initLoc() {
        location = new Location(8,8);
        Assert.assertEquals(location.row(), 8);
        Assert.assertEquals(location.col(), 8);
        Assert.assertEquals(location.toString(), "8, 8");
        Assert.assertEquals(location.equals(new Location(8,8)), true);
        Assert.assertEquals(location.equals(new Location(location)), true);
    }

    @Test
    public void neighbourTest() {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location(8,7));
        Assert.assertEquals(locations.contains(location.left()), true);
        locations.add(new Location(8,9));
        Assert.assertEquals(locations.contains(location.right()), true);
        locations.add(new Location(7,8));
        Assert.assertEquals(locations.contains(location.up()), true);
        locations.add(new Location(9,8));
        Assert.assertEquals(locations.contains(location.down()), true);
        for(Location loc: location.neighbours())
            Assert.assertEquals(locations.contains(loc), true);

        Assert.assertNotEquals(locations.get(0).hashCode(), locations.get(1).hashCode());
        Assert.assertEquals(location.hashCode(), new Location(8,8).hashCode());
    }
}
