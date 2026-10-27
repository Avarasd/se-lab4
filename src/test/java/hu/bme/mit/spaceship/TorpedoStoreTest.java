package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TorpedoStoreTest {

    @Test
    void fire_Success() {
        // Arrange
        TorpedoStore store = new TorpedoStore(1);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void fire_Failure_WithMaxFailureRate() {
        // Arrange: failure rate of 1.0 means firing always fails
        TorpedoStore store = new TorpedoStore(10, 1.0);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertEquals(false, result);
    }

    @Test
    void fire_ZeroCount_ThrowsException() {
        // Arrange
        TorpedoStore store = new TorpedoStore(10);

        // Act + Assert: numberOfTorpedos < 1 triggers the guard clause
        assertThrows(IllegalArgumentException.class, () -> store.fire(0));
    }

    @Test
    void fire_MoreThanAvailable_ThrowsException() {
        // Arrange
        TorpedoStore store = new TorpedoStore(5);

        // Act + Assert: numberOfTorpedos > torpedoCount triggers the guard clause
        assertThrows(IllegalArgumentException.class, () -> store.fire(6));
    }

    @Test
    void isEmpty_WhenEmpty_ReturnsTrue() {
        // Arrange
        TorpedoStore store = new TorpedoStore(0);

        // Act + Assert
        assertEquals(true, store.isEmpty());
    }

    @Test
    void getTorpedoCount_ReturnsRemainingCount() {
        // Arrange
        TorpedoStore store = new TorpedoStore(7);

        // Act
        store.fire(2);

        // Assert
        assertEquals(5, store.getTorpedoCount());
    }
}
