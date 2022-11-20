import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {


    @Test
    void whenFirstArgumentIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));

    }

    @Test
    void whenFirstArgumentIsNullHasMessage() {
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            assertEquals("Horses cannot be null.", message);
        }

    }

    @Test
    void whenFirstArgumentIsEmpty() {
        List<Horse> horses = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
    }

    @Test
    void whenFirstArgumentIsEmptyHasMessage() {
        try {
            List<Horse> horses = new ArrayList<>();
            new Hippodrome(horses);
        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            assertEquals("Horses cannot be empty.", message);
        }
    }

    @Test
    void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i != 30; i++) {
            horses.add(new Horse("Horse " + i, 1 + i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void moveTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }

        new Hippodrome(horses).move();

        for (Horse hors : horses) {
            verify(hors).move();
        }
    }

    @Test
    void getWinner() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            horses.add(new Horse("Horse " + i, i, ThreadLocalRandom.current().nextInt(1,10)));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        Horse horse = horses.stream().max(Comparator.comparing(Horse::getDistance))
                .get();
        assertEquals(horse,hippodrome.getWinner());
    }
}