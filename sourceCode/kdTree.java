// Camila Kin Marquez Sosa 

import java.math.BigDecimal;
import java.util.PriorityQueue;
import java.util.Arrays;
import java.util.Comparator;


public class kdTree {

    private int dimension;  // defines the dimension of the tree
    private Node root;      // root node

    // building block Node of kdTree
    private static class Node{
        // each node is a Point with a node to its left and right
        Point point;
        Node left;
        Node right;
        int coordinate;  // describes what coordinate was compared at that level in kd tree construction

        public Node(Point pt, int x){ // Node constructor
            point = pt;
            coordinate = x;
            left = null;
            right = null;

        }
    } // end of Node declaration


    // kdTree constructor: sets dimension of tree
    public kdTree(Point[] data){
        dimension = data[0].attributes.length; // initialize dimension depending on amount of attributes being used (data)
        root = buildTree(data, 0); // build tree using data
    }

    // build/train tree using array of Points with data
    private Node buildTree(Point[] points, int depth){
        if(points.length == 0) return null;

        // find coordinate for comparing corresponding attributes
        int coordinate = depth%dimension;

        // find midpoint of array of Points
        int midIndex = points.length / 2;

        // sort array of points based on attribute[coordinate] values
        Arrays.sort(points, Comparator.comparingDouble(point -> point.attributes[coordinate]));

        // select median Point to create new node (root of this level)
        Node node = new Node(points[midIndex], coordinate);

        // recursively build left and right subtrees using sorted data
        node.left = buildTree(Arrays.copyOfRange(points, 0, midIndex), depth+1);
        node.right = buildTree(Arrays.copyOfRange(points, midIndex+1, points.length), depth+1);

        // return the median node
        return node;
    }

    // find k nearest neighbours to a target point
    public Point[] kNearestNeighbours(Point targetPt, int k){ // public find kNN
        // store nearest neighbours in a priority queue: size k comparing by distance
        // the negated distance is used so that the largest distance is at the head of the queue (highest priority) - max heap
        PriorityQueue<Node> NN = new PriorityQueue<>(k, Comparator.comparingDouble(node -> -(distance(targetPt, node.point))));

        // find kNN recursively
        kNearestNeighbours(root, targetPt, k, 0, NN);

        // copy nearest neighbour Points to an array of size k
        Point[] nearestNeighbours = new Point[NN.size()];
        for(int i = 0; i < nearestNeighbours.length; i++){
            nearestNeighbours[i] = NN.poll().point;
        }

        return nearestNeighbours;
    }

    private void kNearestNeighbours(Node currentNode, Point targetPt, int k, int depth, PriorityQueue<Node> NN){ // recursive find kNN
        if(currentNode == null){
            return;
        }

        Point currentPoint = currentNode.point;

        // calculate distance between current point and target point
        double distance = distance(targetPt, currentPoint);

        // add/discard current node from priority queue
        if(NN.size() != k) NN.offer(currentNode); // if the queue is not full, add current node to it
        else if(distance < distance(targetPt, NN.peek().point)){  // if the current distance is smaller than the head (furthest distance in queue)
            NN.poll();                  // remove furthest node
            NN.offer(currentNode);      // add current node to queue
        }

        // choose to traverse left or right
        int coordinate = depth%dimension;
        // is the target point's attribute less than the current node's?
        boolean left = targetPt.attributes[coordinate] < currentPoint.attributes[coordinate];
        // if so, next node to visit is the left one
        Node next = left? currentNode.left : currentNode.right;
        // assign the other node the opposite direction
        Node other = next == currentNode.left? currentNode.right : currentNode.left;

        // move recursively through tree
        kNearestNeighbours(next, targetPt, k, depth + 1, NN);

        // determine if other subtree needs to be explored
        // if the difference between the targetPoint and the currentPoint's attributes < the distance between the targetPoint
        // and the farthest point in the queue, then explore the other subtree
        if(NN.size() < k || Math.abs(targetPt.attributes[coordinate] - currentPoint.attributes[coordinate]) < distance(targetPt, NN.peek().point)){
            kNearestNeighbours(other, targetPt, k, depth + 1, NN);
        }
    }


    // calculate Euclidean distance between target point and point2
    private double distance(Point targetPt, Point point2){
        double sum = 0;
        for(int i = 0; i < targetPt.attributes.length; i++){
            double difference = targetPt.attributes[i] - point2.attributes[i];
            sum += difference*difference;
        }
        return Math.sqrt(sum);
    }

    // test accuracy by finding kNN of a list of Points that are not in the tree, and predicting their diagnosis
    public void testTree(Point[] testPoints, int k){
        System.out.print("k = " + k );
        // initialize correct and incorrect counters
        int correct = 0, incorrect = 0;

        // predict diagnosis of test points
        String[] predictions = predictDiagnosis(testPoints, k);

        // count correct and incorrect predictions
        for(int i = 0; i < testPoints.length; i++){
            if(predictions[i].equals(testPoints[i].diagnosis)) correct++;
            else incorrect++;
        }

        if(correct + incorrect != testPoints.length) System.out.println("WRONG TOTAL POINTS");

        // calculate accuracy
        double accuracy = ( (double) correct /(correct + incorrect) ) * 100;

        // System.out.println("Correct: " + correct + " Incorrect: " + incorrect);
        System.out.println("  Accuracy: " + accuracy + "%");
    }

    // predict the diagnosis of an array of test Points
    private String[] predictDiagnosis(Point[] testPoints, int k){
        String[] predictions = new String[testPoints.length];
        Stopwatch stopwatch = new Stopwatch(); // for testing time to find kNN and assert a prediction of diagnosis

        stopwatch.start();
        // iterate through each point in the test points array
        for(int i = 0; i < testPoints.length; i++){
            int Bcount = 0, Mcount = 0; // initialize B and M counters to 0 for majority voting

            // get points' k nearest neighbours

            Point[] nearestNeighbours = kNearestNeighbours(testPoints[i], k);


            // count B and M diagnoses in nearest neighbours
            for(Point pt : nearestNeighbours){
                if(pt.diagnosis.equals("M")) Mcount++;
                else if(pt.diagnosis.equals("B")) Bcount++;
            }

            // assert predicted diagnosis for the point
            predictions[i] = Mcount > Bcount? "M" : "B";
        }
        stopwatch.end();

        System.out.print("  Time: " + BigDecimal.valueOf(stopwatch.elapsed()).toPlainString() + "s");

        // return predictions for each point in test list
        return predictions;
    }

}

