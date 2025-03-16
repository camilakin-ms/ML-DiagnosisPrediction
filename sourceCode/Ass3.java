// Camila Kin Marquez Sosa

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;

public class Ass3 { // assignment 3 class is used for processing input data into training and testing 

    private static int numAttributes;      // both training and testing lists will have the same number of attributes
    private static Point[] trainList;
    private static Point[] testList;


    public Ass3(String pathname,int instancesTotal){
        // for this purpose, the number of attributes is always 10
        // and the ratio is always 4:1
        // but this can be modified to fit other necessities
        numAttributes = 10;
        readData(pathname, 4, 1, instancesTotal); // fill train and test lists
    }

    // use data from a file to create training list and test list at a ratio
    private void readData(String filePath, int ratioTrain, int ratioTest, int totalInstances){

        ArrayList<String> instancesList = new ArrayList<>(); // to store each line (instance) of data

        // every line of the file is a new element in string list
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) { // read and store every line in the file
                instancesList.add(line);
            }
        } catch (IOException e) {   // if file failed to open
            System.out.println("ERROR: File could not be opened. Program will terminate now");
            e.printStackTrace();
            System.exit(0);
        }

        // shuffle list to make sure that the data split into train and test is random
        Collections.shuffle(instancesList);

        // calculate the ratio and split into two Point arrays
        double splitRatio = (double) ratioTrain / (ratioTrain + ratioTest);
        int trainInstances = (int)(totalInstances * splitRatio); // number of instances for training
        int testInstances = totalInstances - trainInstances;     // use remaining instances for testing
        if(trainInstances + testInstances != totalInstances) System.out.println("WRONG DIVISION OF INSTANCES");
        //else System.out.println("train: " + trainInstances + ", test: " + testInstances);
        trainList = new Point[trainInstances];
        testList = new Point[testInstances];

        // fill train and test lists with Points from data
        for(int i = 0, testIndex = 0; i < totalInstances; i++){ // iterate through total instances

            String[] split = instancesList.get(i).split(","); // split string based on ","

            // temporary values to read from each split array
            String id = new String();
            String diagnosis = new String();
            double[] attributes = new double[numAttributes]; // for 10 attributes (can be changed to read more)

            // iterate through each attribute in each instance
            for(int j = 0, k = 0; j < numAttributes+2; j++){
                if(j == 0) id = split[j];               // assign attribute[0] to id
                else if(j == 1) diagnosis = split[j];   // attribute[1] to diagnosis (M/B)
                else attributes[k++] = Double.parseDouble(split[j]); // other attributes to array

            }

            // add Points to training list
            if(i < trainInstances){
                trainList[i] = new Point(id, diagnosis, attributes); // add point to trainList
            }

            // add Points to testing list
            if(i >= trainInstances && testIndex < testInstances){
                testList[testIndex] = new Point(id, diagnosis, attributes); // add point to trainList
                testIndex++;
            }

        }
    }

    // normalize training and testing data based on training range (max-min method)
    public void normalizeData(){
        // get minimum and maximum values of the training list of Points
        double[][] minmaxValues= minmaxValues(trainList);
        double[] minValues = minmaxValues[0];
        double[] maxValues = minmaxValues[1];

        // normalize every attribute for every Point in the TRAINING list
        for (Point point : trainList) {        // iterate through instances
            for (int j = 0; j < numAttributes; j++) {    // iterate through each Point's attributes
                // normalize each attribute value
                double x = point.attributes[j]; // x = un-normalized value
                point.attributes[j] = (x - minValues[j]) / (maxValues[j] - minValues[j]); // min-max normalization
            }
        }

        // normalize every attribute for every Point in the TESTING list (using min and max from training list)
        for (Point point : testList) {        // iterate through instances
            for (int j = 0; j < numAttributes; j++) {    // iterate through each Point's attributes
                // normalize each attribute value
                double x = point.attributes[j]; // x = un-normalized value
                point.attributes[j] = (x - minValues[j]) / (maxValues[j] - minValues[j]); // min-max normalization
            }
        }
    }

    // find min and max values of data
    private static double[][] minmaxValues(Point[] data){
        int attributes = data[0].attributes.length; // number of attributes

        // to hold each attribute's min and max values
        double[] minValues = new double[attributes];
        double[] maxValues = new double[attributes];

        for(int i = 0; i < attributes; i++) {   // iterate through attributes
            double min = data[0].attributes[i]; // initialize min and max to first value in attributes array
            double max = data[0].attributes[i];

            for (Point point : data) { // iterate through instances
                if(point.attributes[i] < min) min = point.attributes[i]; // find min and max
                if (point.attributes[i] > max) max = point.attributes[i];
            }
            minValues[i] = min; // place min and max values for attribute in array
            maxValues[i] = max;
        }

        return new double[][] {minValues, maxValues};
    }

    // getter for training list
    public Point[] getTrainList(){
        return trainList;
    }

    // getter for testing list
    public Point[] getTestList(){
        return testList;
    }

    // for debugging purposes
    public static void printData(){
        System.out.println("TRAIN:");
        for(Point point: trainList){
            point.printPoint();

        }
        System.out.println();
        System.out.println("TEST:");
        for(Point point: testList){
            point.printPoint();

        }
    }
}
