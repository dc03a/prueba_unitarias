package org.iesvdm.tddjava.ship;

import org.testng.annotations.*;
import static org.testng.Assert.*;

@Test
public class DirectionSpec {

    public void whenGetFromShortNameNThenReturnDirectionN() {
        assertEquals(Direction.getFromShortName('N'), Direction.NORTH);
    }

    public void whenGetFromShortNameWThenReturnDirectionW() {
        assertEquals(Direction.getFromShortName('W'), Direction.WEST);
    }

    public void whenGetFromShortNameBThenReturnNone() {
        assertEquals(Direction.getFromShortName('B'), Direction.NONE);
    }

    public void givenSWhenLeftThenE() {
        assertEquals(Direction.SOUTH.turnLeft(), Direction.EAST);
    }

    public void givenNWhenLeftThenW() {
        assertEquals(Direction.NORTH.turnLeft(), Direction.WEST);
    }

    public void givenSWhenRightThenW() {
        assertEquals(Direction.SOUTH.turnRight(), Direction.WEST);
    }

    public void givenWWhenRightThenN() {
        assertEquals(Direction.WEST.turnRight(), Direction.NORTH);
    }
}
