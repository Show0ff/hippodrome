import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Test
    void firstParameterOfHorseNotNull() {
        assertThrows(IllegalArgumentException.class, () ->

                new Horse(null, 10));
    }

    @Test
    void firstParameterOfHorseHasString() {
        try {
            new Horse(null, 10);
        } catch (IllegalArgumentException e) {
            String actual = e.getMessage();
            String expected = "Name cannot be null.";
            assertEquals(expected, actual);
        }
    }


    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void firstParameterOfHorseHasMessageWhenEmpty(String arg) {
        try {
            new Horse(arg, 10);
        } catch (IllegalArgumentException e) {
            String actual = e.getMessage();
            String expected = "Name cannot be blank.";
            assertEquals(expected, actual);
        }

    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void firstParameterOfHorseNotEmpty(String expected) {
        assertThrows(IllegalArgumentException.class, () ->
            new Horse(expected, 10)
        );
    }

    @Test
    void secondParameterOfHorseIsNotNegative() {
        assertThrows(IllegalArgumentException.class, () ->
            new Horse("Perun", -1)
        );
    }

    @Test
    void secondParameterOfHorseHasMessageWhenNegative() {
        try {
            new Horse("Perun", -1);
        } catch (IllegalArgumentException e) {
            String actual = e.getMessage();
            String expected = "Speed cannot be negative.";
            assertEquals(expected, actual);
        }
    }

    @Test
    void thirdParameterOfHorseIsNotNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new Horse("Perun", 1, -1));
    }

    @Test
    void thirdParameterOfHorseHasMessageWhenNegative() {
        try {
            new Horse("Perun", 1, -1);
        } catch (IllegalArgumentException e) {
            String actual = e.getMessage();
            String expected = "Distance cannot be negative.";
            assertEquals(expected, actual);
        }
    }


    @Test
    void getName() {
        Horse horse = new Horse("Perun", 1);
        String name = horse.getName();
        assertEquals("Perun", name);
    }

    @Test
    void getSpeed() {
        Horse horse = new Horse("Perun", 1);
        double speed = horse.getSpeed();
        assertEquals(1, speed);
    }

    @Test
    void getDistance() {
        Horse horse = new Horse("Perun", 1, 10);
        double distance = horse.getDistance();
        assertEquals(10, distance);
    }

    @Test
    void getDistanceWhenWithoutDistance() {
        Horse horse = new Horse("Perun", 1);
        double distance = horse.getDistance();
        assertEquals(0, distance);
    }

    @Test
    void getRandomDoubleFromMove() {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("Perun", 1, 5).move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));

        }
    }

    @Test
    void getRandomDoubleFromMoveExpressionCheck() {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Perun", 1);
            mockedStatic.when(() -> Horse.getRandomDouble(ArgumentMatchers.anyDouble(), ArgumentMatchers.anyDouble())).thenReturn(4.5);
            horse.move();
            double actual = horse.getDistance();
            assertEquals(4.5, actual);

        }
    }


}