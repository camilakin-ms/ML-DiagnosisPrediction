// Camila Kin Marquez Sosa 40234852

public class Stopwatch { // to measure runtime of algorithms
    private long startTime;
    private long endTime;
    private boolean active; // determines if stopwatch is running or not

    public Stopwatch(){ // constructor
        startTime = 0;
        endTime = 0;
        active = false;
    }

    public void start(){ // start stopwatch
        startTime = System.nanoTime();  // sets start time
        active = true;                  // status: active
    }

    public void end(){ // stop stopwatch
        endTime =  System.nanoTime();   // sets end time
        active = false;                 // status: inactive
    }


    public double elapsed(){ // returns time from start to end
        if(active){ // if stopwatch still running
            return (double)(System.nanoTime() - startTime) / 1000000000; // return difference between start and current time
        }                                                                // divide by 1,000,000,000 to convert to seconds
        else{ // if stopwatch ended
            return (double)(endTime - startTime) / 1000000000; // return difference between start and end time
        }
    }
}
