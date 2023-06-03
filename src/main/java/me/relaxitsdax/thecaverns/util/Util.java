package me.relaxitsdax.thecaverns.util;

import java.util.Random;

public class Util {

    public static int randIntInclusive(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public static int randIntExclusive(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min) + min;
    }

    public static int randNegative() { //Returns either -1 or 1
        return randIntInclusive(1, 2) == 1 ? 1 : -1;
    }
}
