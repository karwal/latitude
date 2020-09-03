package com.data.producer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class InputGenerator {

    List<String> referenceNames;
    List<String> referenceStreetTypes;
    List<String> referenceStreetNames;
    List<String> referenceSuburbs;
    List<String> referenceStates;

    String TEMP_FILE_NAME = "SampleInput.temp";
    String INPUT_FILE_NAME = "SampleInput.txt";

    public InputGenerator() {
        this.referenceNames = readFile("src/main/resources/ReferenceNames.txt");
        this.referenceStreetTypes = readFile("src/main/resources/ReferenceStreetTypes.txt");
        this.referenceStreetNames = readFile("src/main/resources/ReferenceStreetNames.txt");
        this.referenceSuburbs = readFile("src/main/resources/ReferenceSuburbs.txt");
        this.referenceStates = readFile("src/main/resources/ReferenceStates.txt");
    }

    public static void main(String args[]) throws Exception {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Please Enter your choice:");
        System.out.println("Enter the size of the file to be generate in MBs (Example: 1024=1GB)");
        int fileSize = scanner.nextInt();

        InputGenerator generator = new InputGenerator();
        long start = System.nanoTime();
        generator.generateInputFile(fileSize*1000000);
        long end = System.nanoTime();
        System.out.println("Time Taken = " + (end-start)/1000000 + " ms");

    }


    private void generateInputFile(int fileZise) throws Exception {
        deletePreviousFilesIfExist();
        FileWriter file = new FileWriter(TEMP_FILE_NAME);
        BufferedWriter output = new BufferedWriter(file);
        int totalRecords = 0;
        int totalSize = 0;
        while (totalSize < fileZise) {
            try {
                String firstName = referenceNames.get(new Random().nextInt(referenceNames.size() - 1));
                String lastName = referenceNames.get(new Random().nextInt(referenceNames.size() - 1));
                String address = getRandomAddress();
                String dateOfBirth = getRandomDateOfBirth();
                String fileLine = firstName + "," + lastName + "," + address + "," + dateOfBirth;
                output.write(fileLine);
                output.newLine();
                totalSize = totalSize + fileLine.getBytes().length;
                totalRecords++;
            }
            catch (Exception e) {
                e.printStackTrace();
                e.getStackTrace();
            }
        }
        output.close();

        Path fileToMovePath = Paths.get(TEMP_FILE_NAME);
        Path targetPath = Paths.get(INPUT_FILE_NAME);
        Files.move(fileToMovePath, targetPath);
        System.out.println("Total Records = " + totalRecords);
    }

    private void deletePreviousFilesIfExist() throws IOException {
        if(Files.exists(Path.of(TEMP_FILE_NAME))) {
            Files.delete(Path.of(TEMP_FILE_NAME));
        }
        if(Files.exists(Path.of(INPUT_FILE_NAME))) {
            Files.delete(Path.of(INPUT_FILE_NAME));
        }
    }

    private String getRandomDateOfBirth() {
        Random rand = new Random();
        int date = rand.nextInt(27) + 1;
        int month = rand.nextInt(11) + 1;
        int year = rand.nextInt(100) + 1920;
        return date + "/" + month + "/" + year;
    }

    private String getRandomAddress() {
        Random rand = new Random();
        int streetNumber = rand.nextInt(200);
        String streetName = referenceStreetNames.get(rand.nextInt(referenceStreetNames.size()-1));
        String streetType = referenceStreetTypes.get(rand.nextInt(referenceStreetTypes.size()-1));
        String suburb = referenceSuburbs.get(rand.nextInt(referenceSuburbs.size()-1));
        String state = referenceStates.get(rand.nextInt(referenceStates.size()-1));
        int zip = ThreadLocalRandom.current().nextInt(3000, 4000);
        return streetNumber + " " + streetName + " " + streetType + " " + suburb + " " + state + " " + zip;

    }


    public List<String> readFile(String fileName){
        List<String> lines = new ArrayList<>();
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                lines.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return lines;
    }
}
