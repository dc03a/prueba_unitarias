package org.iesvdm.tddjava.ship;

import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

@Test
public class ShipSpec {

    private Ship ship;
    private Location location;
    private Planet planet;

    @BeforeMethod
    public void beforeTest() {
        Point max = new Point(50, 50);
        location = new Location(new Point(21, 13), Direction.NORTH);
        List<Point> obstacles = new ArrayList<>();
        obstacles.add(new Point(44, 44));
        obstacles.add(new Point(45, 46));
        planet = new Planet(max, obstacles);
//        ship = new Ship(location);
        ship = new Ship(location, planet);
    }

    public void whenInstantiatedThenLocationIsSet() {
        Location location = new Location(new Point(21, 13), Direction.NORTH);
        Ship ship = new Ship(location, planet);
        assertEquals(ship.getLocation(), location);
    }

    public void givenNorthWhenMoveForwardThenYDecreases() {
        ship.moveForward();
        assertEquals(ship.getLocation().getPoint().getY(), 12);
    }

   public void givenEastWhenMoveForwardThenXIncreases() {
        ship.getLocation().setDirection(Direction.EAST);
        ship.moveForward();
        assertEquals(ship.getLocation().getPoint().getX(), 22);
    }

    public void whenMoveForwardThenForward() {
        ship.moveForward();
        assertEquals(ship.getLocation().getPoint().getY(), 14);
    }

    public void whenMoveBackwardThenBackward() {
        ship.moveBackward();
        assertEquals(ship.getLocation().getPoint().getY(),12 );
    }

    public void whenTurnLeftThenLeft() {
        ship.turnLeft();
        assertEquals(ship.getLocation().getDirection(), Direction.WEST);
    }

    public void whenTurnRightThenRight() {
        ship.turnRight();
        assertEquals(ship.getLocation().getDirection(), Direction.EAST);
    }

    public void whenReceiveCommandsFThenForward() {
        ship.receiveCommands("f");
        assertEquals(ship.getLocation().getPoint().getY(), 14);
    }

    public void whenReceiveCommandsBThenBackward() {
        ship.receiveCommands("b");
        assertEquals(ship.getLocation().getPoint().getY(), 12);
    }

    public void whenReceiveCommandsLThenTurnLeft() {
        ship.receiveCommands("l");
        assertEquals(ship.getLocation().getDirection(), Direction.WEST);
    }

    public void whenReceiveCommandsRThenTurnRight() {
        ship.receiveCommands("r");
        assertEquals(ship.getLocation().getDirection(), Direction.EAST);
    }

    public void whenReceiveCommandsThenAllAreExecuted() {
        ship.receiveCommands("frbl");
        assertEquals(ship.getLocation().getPoint(), new Point(21, 13));
        assertEquals(ship.getLocation().getDirection(), Direction.WEST);
    }

    public void whenInstantiatedThenPlanetIsStored() {
       Point max = new Point(50, 50);
        Planet planet = new Planet(max);
        ship = new Ship(location, planet);
    }

    public void givenDirectionEAndXEqualsMaxXWhenReceiveCommandsFThenWrap() {
        assertEquals(ship.getPlanet(), planet);
    }

    public void givenDirectionEAndXEquals1WhenReceiveCommandsBThenWrap() {

    }

    public void whenReceiveCommandsThenStopOnObstacle() {
    }

    public void whenReceiveCommandsThenOForOkAndXForObstacle() {

    }

}
