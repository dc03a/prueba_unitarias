package org.iesvdm.tddjava.ship;

import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

@Test
public class LocationSpec {

    private final int x = 12;
    private final int y = 32;
    private final Direction direction = Direction.NORTH;
    private Point max;
    private Location location;
    private List<Point> obstacles;

    @BeforeMethod
    public void beforeTest() {
        max = new Point(50, 50);
        location = new Location(new Point(x, y), direction);
        obstacles = new ArrayList<Point>();
    }

    public void whenInstantiatedThenXIsStored() {
        assertEquals(location.getX(), x);
    }

    public void whenInstantiatedThenYIsStored() {
        assertEquals(location.getY(), y);
    }

    public void whenInstantiatedThenDirectionIsStored() {
        assertEquals(location.getDirection(), direction);
    }

    public void givenDirectionNWhenForwardThenYDecreases() {
        location.forward(max, obstacles);
        assertEquals(location.getY(), y - 1);
    }

    public void givenDirectionSWhenForwardThenYIncreases() {
        location.setDirection(Direction.SOUTH);
        location.forward(max, obstacles);
        assertEquals(location.getY(), y + 1);
    }

    public void givenDirectionEWhenForwardThenXIncreases() {
        location.setDirection(Direction.EAST);
        location.forward(max, obstacles);
        assertEquals(location.getX(), x + 1);
    }

    public void givenDirectionWWhenForwardThenXDecreases() {
        location.setDirection(Direction.WEST);
        location.forward(max, obstacles);
        assertEquals(location.getX(), x - 1);
    }

    public void givenDirectionNWhenBackwardThenYIncreases() {
        location.backward(max, obstacles);
        assertEquals(location.getY(), y + 1);
    }

    public void givenDirectionSWhenBackwardThenYDecreases() {
        location.setDirection(Direction.SOUTH);
        location.backward(max, obstacles);
        assertEquals(location.getY(), y - 1);
    }

    public void givenDirectionEWhenBackwardThenXDecreases() {
        location.setDirection(Direction.EAST);
        location.backward(max, obstacles);
        assertEquals(location.getX(), x - 1);
    }

    public void givenDirectionWWhenBackwardThenXIncreases() {
        location.setDirection(Direction.WEST);
        location.backward(max, obstacles);
        assertEquals(location.getX(), x + 1);
    }

    public void whenTurnLeftThenDirectionIsSet() {
        location.turnLeft();
        assertEquals(location.getDirection(), Direction.WEST);
    }

    public void whenTurnRightThenDirectionIsSet() {
        location.turnRight();
        assertEquals(location.getDirection(), Direction.EAST);
    }

    public void givenSameObjectsWhenEqualsThenTrue() {
        Location sameLocation = new Location(new Point(x, y), direction);
        assertEquals(location, sameLocation);
    }

    public void givenDifferentObjectWhenEqualsThenFalse() {
        Object differentObject = new Object();
        assertNotEquals(location, differentObject);
    }

    public void givenDifferentXWhenEqualsThenFalse() {
        Location differentLocation = new Location(new Point(x + 1, y), direction);
        assertNotEquals(location, differentLocation);
    }

    public void givenDifferentYWhenEqualsThenFalse() {
        Location differentLocation = new Location(new Point(x, y + 1), direction);
        assertNotEquals(location, differentLocation);
    }

    public void givenDifferentDirectionWhenEqualsThenFalse() {
        Location differentLocation = new Location(new Point(x, y), Direction.SOUTH);
        assertNotEquals(location, differentLocation);
    }

    public void givenSameXYDirectionWhenEqualsThenTrue() {
        Location sameLocation = new Location(new Point(x, y), direction);
        assertEquals(location, sameLocation);
    }

    public void whenCopyThenDifferentObject() {
        Location copyLocation = location.copy();
        assertNotSame(location, copyLocation);
    }

    public void whenCopyThenEquals() {
        Location copyLocation = location.copy();
        assertEquals(location, copyLocation);
    }

    public void givenDirectionEAndXEqualsMaxXWhenForwardThen1() {
        location = new Location(new Point(max.getX(), y), Direction.EAST);
        location.forward(max, obstacles);
        assertEquals(location.getX(), 1);
    }

    public void givenDirectionWAndXEquals1WhenForwardThenMaxX() {
        location = new Location(new Point(1, y), Direction.WEST);
        location.forward(max, obstacles);
        assertEquals(location.getX(), max.getX());
    }

    public void givenDirectionNAndYEquals1WhenForwardThenMaxY() {
        location = new Location(new Point(x, 1), Direction.NORTH);
        location.forward(max, obstacles);
        assertEquals(location.getY(), max.getY());
    }

    public void givenDirectionSAndYEqualsMaxYWhenForwardThen1() {
        location = new Location(new Point(x, max.getY()), Direction.SOUTH);
        location.forward(max, obstacles);
        assertEquals(location.getY(), 1);
    }

    public void givenObstacleWhenForwardThenReturnFalse() {
        obstacles.add(new Point(x, y - 1));
        assertFalse(location.forward(max, obstacles));
    }

    public void givenObstacleWhenBackwardThenReturnFalse() {
        obstacles.add(new Point(x, y + 1));
        assertFalse(location.backward(max, obstacles));
    }

}
