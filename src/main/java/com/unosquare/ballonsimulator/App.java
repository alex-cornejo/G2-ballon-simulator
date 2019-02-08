package com.unosquare.ballonsimulator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    private static void generateDataset() {

    }

    private static void processDataset() {

    }

    public static void main(String[] args) {

        boolean repeat;
        do {
            System.out.print("\033[H\033[2J");
            Scanner scanner = new Scanner(System.in);
            repeat = false;
            System.out.println("\n-------------------------------------");
            System.out.println("Please choose one of the next options");
            System.out.println("-------------------------------------");
            System.out.println("\r[1] Generate dataset randomly");
            System.out.println("\r[2] Process an existing dataset");
            System.out.println("\r[9] Exit");
            System.out.print("\nOption: ");

            int option = 0;
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                repeat = true;
            }

            switch (option) {
            case 1:
                generateDataset();
                break;

            case 2:
                processDataset();
                break;

            case 9:
                repeat = false;
                System.out.println("Bye bye!!!");
                break;
            default:
                System.out.println("You must choose a valid option!!!");
                repeat = true;
                break;
            }
            if (!repeat) {
                scanner.close();
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (repeat);
    }
}
