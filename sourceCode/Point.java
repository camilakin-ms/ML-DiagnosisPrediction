// Camila Kin Marquez Sosa 

// a Point has an id, a diagnosis (B/M) and an array of attributes (doubles)
// they are separate so only the attributes contribute to building a tree
public class Point{
    public String id;
    public String diagnosis;
    public double[] attributes;

    public Point(String ID, String DIAGNOSIS, double[] ATTRIBUTES){
        id = ID;
        diagnosis = DIAGNOSIS;
        attributes = ATTRIBUTES;
    }

    // for debugging purposes
    public void printPoint(){
        System.out.print(id + " | " + diagnosis + " | ");
        for(double attribute: attributes){
            System.out.print(attribute);
            System.out.print(" | ");
        }
        System.out.println();
    }
} // end of Point declaration
