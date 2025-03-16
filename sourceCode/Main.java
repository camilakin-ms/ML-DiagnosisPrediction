// Camila Kin Marquez Sosa

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the path for the data file: ");
        String pathname = scanner.nextLine();
        // /Users/camilakinmarquezsosa/Desktop/classes/Summer2024/coen352/Assignment3/breast+cancer+wisconsin+diagnostic/wdbc.data

        // test ML algorithm for N = 100, 200, 300, 400, 500 instances
        int N = 100;
        while(N <= 500){
            System.out.println("Testing w/ N = " + N);
            // test each N instances with k = 1, 5, 7 nearest neighbours
            test(pathname, N, 1);
            test(pathname, N, 5);
            test(pathname, N, 7);
            N += 100;
        }

    }

    public static void test(String source, int N, int k){
        // read data from file, make training and testing arrays
        Ass3 obj = new Ass3(source, N);
        // normalize data
        obj.normalizeData();
        // create tree with training data
        kdTree tree = new kdTree(obj.getTrainList());
        // test tree with testing data
        tree.testTree(obj.getTestList(), k);
    }
}
