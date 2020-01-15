import java.util.LinkedList;
import java.util.ArrayList;
import java.util.*;
import java.io.FileWriter;
import java.io.PrintWriter;
/**
 * Write a description of class Rides here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Rides
{
    PrintWriter outFile;
    ArrayList<Ride> rides;
    
    ArrayList<VisitorGroup> leaving;
    //ArrayList<VisitorGroup> leavingPark;
    
    
    public Rides(PrintWriter outFile, String[][] ridesInfo)
    {
        //assign parameter to variable
        this.outFile = outFile;
        //all the groups leaving their ride 
        leaving = new ArrayList<VisitorGroup>();
        //container for all ride in park
        rides = new ArrayList<Ride>();
        
        //creates ride object for all ride in config file 
        //and adds to the container
        for(int i = 0; i<ridesInfo.length; i++){
                    Ride newRide = new Ride(outFile, ridesInfo[i]);
                    rides.add(newRide);
        }
    }
    
    //get the list of rides int he park
    public ArrayList<Ride> getRidesList(){
        return rides;
    }
    
    //update time step for all ride in the container
    public void updateTimeStep(){
        for(int i =0; i<rides.size(); i++){
            rides.get(i).updateTimeStep();
            //combine all groups leaving the ride
            leaving.addAll(rides.get(i).leaving);
            //clear each of their ride because they left already 
            rides.get(i).leaving.clear();
        }
    }
    
    
}
