package com.example.utils;

import java.util.Random;

/**
 * The type Draw cards utils.
 */
public class DrawCardsUtils {
    private static final double COMMON_PROBABILITY = 0.5069;
    private static final double ELITE_PROBABILITY = 0.436;
    private static final double LIMITED_PROBABILITY = 0.047;
    private static final double SP_PROBABILITY = 0.001;
    private final Random random;

    /**
     * Instantiates a new Draw cards utils.
     */
    public DrawCardsUtils() {
        this.random = new Random();
    }

    /**
     * Draw card int.
     *
     * @return the int
     */
    public int drawCard() {
        double randomValue = random.nextDouble();
        if (randomValue < COMMON_PROBABILITY) {
            return 1;
        } else if (randomValue < COMMON_PROBABILITY + ELITE_PROBABILITY) {
            return 2;
        } else if (randomValue < COMMON_PROBABILITY + ELITE_PROBABILITY + LIMITED_PROBABILITY) {
            return 3;
        } else if (randomValue < COMMON_PROBABILITY + ELITE_PROBABILITY + LIMITED_PROBABILITY + SP_PROBABILITY) {
            return 4;
        }
        return 0;
    }
}
