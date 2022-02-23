package ru.job4j.ood.srp;

import java.util.Random;

/**
 * @author Vladimir Likhachev
 */
public class Generator implements NumberGenerator<Integer> {

    @Override
    public Integer generate() {
        Random random = new Random();
        return random.nextInt();
    }
}
