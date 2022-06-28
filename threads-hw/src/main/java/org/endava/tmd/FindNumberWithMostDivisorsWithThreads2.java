package org.endava.tmd;

import java.util.concurrent.*;

public class FindNumberWithMostDivisorsWithThreads2 {
    static int numberWithMostDivisors = 0, maxNumberOfDivisors = 0;

    synchronized private static void updateNumberWithMaxDivisors(int numberWithMostDivisorsFromThread,
                                                                 int numberOfDivisorsFromThread) {
        if (numberOfDivisorsFromThread > maxNumberOfDivisors) {
            numberWithMostDivisors = numberWithMostDivisorsFromThread;
            maxNumberOfDivisors = numberOfDivisorsFromThread;
        }
    }

    private static class CountDivisorsThread extends Thread {
        int min, max;

        public CountDivisorsThread(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public void run() {
            int maxDivisors = 0, number = 0;

            for (int i = min; i <= max; i++) {
                int count = 0;
                for (int j = 1; j <= i; j++) {
                    if (i % j == 0) {
                        count++;
                    }
                }
                if (count > maxDivisors) {

                    maxDivisors = count;
                    number = i;
                }

                updateNumberWithMaxDivisors(number, maxDivisors);
            }
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CountDivisorsThread[] worker = new CountDivisorsThread[10];
        long startTime = System.nanoTime();

        for (int i = 0; i < 10; i++) {
            worker[i] = new CountDivisorsThread(i * 10000, ((i + 1) * 10000 - 1));
        }

        maxNumberOfDivisors = 0;
        for (int i = 0; i < 10; i++)
            worker[i].start();

        for (int i = 0; i < 10; i++) {
            while (worker[i].isAlive()) {
                try {
                    worker[i].join();
                } catch (InterruptedException e) {
                }
            }
        }

        System.out.println("The integer that has the largest number of divisors is: " + numberWithMostDivisors +
                " with " + maxNumberOfDivisors + " divisors.");
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("The running time of the program: " + totalTime);
    }
}
