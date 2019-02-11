package com.unosquare.ballonsimulator;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.unosquare.ballonsimulator.model.Observatory.ObservatoryEnum;
import com.unosquare.ballonsimulator.util.DatasetGenerator;
import com.unosquare.ballonsimulator.util.DatasetProcessor;
import com.unosquare.ballonsimulator.util.ExternalMergeSort;

public class App {

    private static void generateDataset() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("\nPlease enter the amount of records for your dataset: ");
            int amount = scanner.nextInt();
            System.out.print("Please enter your dataset output file name: ");
            String outputFile = scanner.next();
            DatasetGenerator dsg = new DatasetGenerator(outputFile, amount);

            dsg.execute();
            System.out.println("\n'" + outputFile + "' generated in the root of this program");
        } catch (InputMismatchException e) {
            System.out.println("Invalid data...");
        } catch (IOException e) {
            System.out.println("An error ocurred while genereting your dataset...");
        }

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void processDataset() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("\nPlease choose the desired output for the dataset processing: ");
            System.out.println("[1] AU");
            System.out.println("[2] US");
            System.out.println("[3] FR");
            System.out.println("\r[9] Exit");
            System.out.print("\nOption: ");
            int option = scanner.nextInt();
            ObservatoryEnum obs = null;
            switch (option) {
            case 1:
                obs = ObservatoryEnum.AU;
                break;

            case 2:
                obs = ObservatoryEnum.US;
                break;
            case 3:
                obs = ObservatoryEnum.FR;
                break;

            case 9:
                System.out.println("Bye bye!!!");
                break;
            default:
                System.out.println("You must choose a valid option!!!");
                break;
            }
            if (option != 9) {
                System.out.print("\nPlease enter your input dataset file name (it must exist): ");
                String inputFile = scanner.next();
                System.out.print("Please enter your output dataset file name: ");
                String outputFile = scanner.next();
                ExternalMergeSort ems = new ExternalMergeSort();
                ems.sortDataset(inputFile);

                DatasetProcessor dsp = new DatasetProcessor();
                dsp.processDataset("tmp/datasetsorted.txt", outputFile, obs);

                System.out.println("\n'" + outputFile + "' generated in the root of this program");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid data...");
        } catch (Exception e) {
            System.out.println("An error ocurred while processing your dataset...");
        }

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
                repeat = true;
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
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (repeat);
    }
}
