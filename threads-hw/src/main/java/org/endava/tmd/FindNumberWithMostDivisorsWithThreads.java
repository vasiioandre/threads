package org.endava.tmd;

import java.io.IOException;
import java.util.concurrent.*;

class Divisors implements Callable {
    private int number;

    public Divisors(int number) {
        this.number = number;
    }

    public int findNumberOfDivisors() {
        int count = 0;
        for (int j = 1; j <= number; j++) {
            if (number % j == 0) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Object call() throws Exception {
        return findNumberOfDivisors();
    }
}

public class FindNumberWithMostDivisorsWithThreads {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int maxDivisors = 0, numberWithMostDivisors = 0, numberOfDivisors = 0;
        long startTime = System.nanoTime();

        ExecutorService es = Executors.newFixedThreadPool(10);
        Future<Integer>[] results = new Future[100];

        for (int i = 1; i <= 100; i++) {
            Divisors d = new Divisors(i);
            results[i - 1] = es.submit(d);
        }

        for (Future<Integer> result : results) {
            try {
                numberOfDivisors = result.get();

                if (numberOfDivisors > maxDivisors) {
                    maxDivisors = numberOfDivisors;
                }
            } catch (ExecutionException e) {
                System.out.println(e.getCause());
            }
        }

        try {
            es.shutdown();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("The integer that has the largest number of divisors is: " + numberWithMostDivisors +
                " with " + maxDivisors + " divisors.");
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("The running time of the program: " + totalTime);
    }
}
