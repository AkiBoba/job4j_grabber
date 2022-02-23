package ru.job4j.ood.srp;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Vladimir Likhachev
 */
public class SimpleSequenceGenerator implements SequenceGenerator<Integer> {

    private final NumberGenerator<Integer> numberGenerator;

    public SimpleSequenceGenerator(NumberGenerator<Integer> numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

    @Override
    public List<Integer> generate(int size) {
        return IntStream.range(0, size)
                .map(i -> numberGenerator.generate()).boxed()
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        NumberGenerator ng = new Generator();
        SimpleSequenceGenerator ssg = new SimpleSequenceGenerator(ng);
        System.out.println(ssg.generate(10));
    }
}
