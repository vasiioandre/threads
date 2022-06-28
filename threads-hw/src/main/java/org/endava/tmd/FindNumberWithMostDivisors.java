package org.endava.tmd;

public class FindNumberWithMostDivisors {
    public static void main(String[] args) {
        int maxDivisors = 0, number = 0;
        long startTime = System.nanoTime();

        for (int i = 1; i <= 100000; i++) {
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
        }

        System.out.println("The integer that has the largest number of divisors is: " + number + " with " + maxDivisors +
                " divisors.");
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("The running time of the program: " + totalTime);
    }
}