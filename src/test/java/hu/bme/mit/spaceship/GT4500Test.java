package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GT4500Test {

    private GT4500 ship;

    @BeforeEach
    public void init() {
        this.ship = new GT4500();
    }

    @Test
    void fireTorpedo_Single_Success() {
        // Arrange

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void fireTorpedo_All_Success() {
        // Arrange

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void fireTorpedo_Single_Alternates_PrimaryThenSecondary() {
        // Arrange: default ship has 10 torpedoes in each store, no failures

        // Act: two consecutive SINGLE shots should both succeed (primary, then secondary)
        boolean first = ship.fireTorpedo(FiringMode.SINGLE);
        boolean second = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        assertEquals(true, first);
        assertEquals(true, second);
    }

    @Test
    void fireTorpedo_Single_PrimaryEmpty_FallsBackToSecondary() {
        // Arrange: empty primary store, stocked secondary store
        // Covers the branch where primary is empty on the first SINGLE shot
        GT4500 ship = new GT4500(0, 0.0, 10, 0.0);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert: fires secondary instead
        assertEquals(true, result);
    }

    @Test
    void fireTorpedo_Single_SecondaryEmpty_FallsBackToPrimary() {
        // Arrange: stocked primary, empty secondary
        // First SINGLE fires primary; second SINGLE would target secondary (empty)
        // so it falls back to primary again.
        GT4500 ship = new GT4500(10, 0.0, 0, 0.0);

        // Act
        boolean first = ship.fireTorpedo(FiringMode.SINGLE);  // fires primary
        boolean second = ship.fireTorpedo(FiringMode.SINGLE); // secondary empty -> primary again

        // Assert
        assertEquals(true, first);
        assertEquals(true, second);
    }

    @Test
    void fireTorpedo_Single_BothEmpty_Fails() {
        // Arrange: both stores empty
        // Covers the branches where the fallback store is also empty.
        GT4500 ship = new GT4500(0, 0.0, 0, 0.0);

        // Act
        boolean first = ship.fireTorpedo(FiringMode.SINGLE);  // primary empty, secondary empty
        boolean second = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        assertEquals(false, first);
        assertEquals(false, second);
    }

    @Test
    void fireTorpedo_All_BothEmpty_Fails() {
        // Arrange: both stores empty -> ALL cannot fire anything
        GT4500 ship = new GT4500(0, 0.0, 0, 0.0);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        assertEquals(false, result);
    }
}
