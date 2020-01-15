import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.PrintWriter;
/**
 * Write a description of class Park here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Park
{
    //instance variable
    filesReader reader;
    PrintWriter outFile;
    Rides rides;
    VisitorGroups visitors;
    //stats
    int arrivals;
    int totalVisitors;
    int totalGroups;
    int close;
    
    //one whole amusement park
    public Park(String configFile, String outputName)
    {
        try{
            outFile = new PrintWriter(new FileWriter(outputName));
            Random random = new Random();
            reader = new filesReader(configFile);
            
            //container of groups and rides for this park
            rides = new Rides(outFile, reader.getRides());
            visitors = new VisitorGroups(outFile);
            
            
            //operates asd mamy time steps of amusement park
            //for how many time loops run 
            close = 1000;
            for(int currentTime = 0; currentTime <close; currentTime++){
                //random number of arrival every time step
                arrivals = (int)Math.round(random.nextGaussian()*(reader.getpDetail("arrival")[1]) 
                + (reader.getpDetail("arrival")[0]));
                //make random groups 
                for(int i = 0; i< arrivals; i++){
                    VisitorGroup newGroup = 
                    new VisitorGroup(configFile, currentTime, close);
                    //count stats
                    totalVisitors += newGroup.people;
                    totalGroups ++;;
                    //add to container
                    visitors.addGroup(newGroup);
                }
                //find ride for groups 
                visitors.findRide(rides);
                //update time step for all rides
                rides.updateTimeStep();
                //adds to gorup container who are leaving a ride 
                visitors.addGroups(rides.leaving);
                rides.leaving.clear();
                //rides.leavingPark.clear();
                
            }
            //print stats
            printData();
            visitors.printData();
            for(Ride aRide : rides.getRidesList()){
                aRide.printData();
            }
            outFile.close();
        }catch(Exception e)
        {System.out.println(e);}
    }
    
    //print stats
    public void printData(){
        try{
            outFile.println("");
            int groupSad = 0;
            int peopleSad = 0;
            //stats for each ride 
            for(Ride aRide : rides.getRidesList()){
                groupSad += aRide.groupSadTooLong;
                peopleSad += aRide.peopleSadTooLong;
                
            }
            outFile.println("total number of visitors: " + totalVisitors);
            outFile.println("total number of groups: " + totalGroups);
            outFile.println("total groups left due to their set schedule: " + groupSad);
            outFile.println("total people left due to their set schedule: " + peopleSad);
        }catch(Exception e)
        {System.out.println(e);}
           
    }

    public static void main(String[] args){
        Park myPark1 = new Park(args[0], "originalOutput1.txt");
        //Park myPark2 = new Park(args[0], "originalOutput2.txt");
        //Park myPark3 = new Park(args[0], "originalOutput3.txt");
        //Park myPark4 = new Park(args[0], "originalOutput4.txt");
        //Park myPark5 = new Park(args[0], "originalOutput5.txt");
        
        
        

        
    }
}
