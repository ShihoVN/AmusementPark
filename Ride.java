import java.util.LinkedList;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;
/**
 * Write a description of class Ride here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Ride
{
    //declare variables
    PrintWriter outFile;
    String name;
    String[] group;
    int space;
    int length;
    int capacity;
    int queue;
    
    //place where groups are in the ride
    ArrayList<VisitorGroup> line;
    ArrayList<VisitorGroup> leaving;
    ArrayList<VisitorGroup> onRide;
    //ArrayList<VisitorGroup> leavingPark;
    
    //keep in track of statistics
    int groupRode;
    int peopleRode;
    int groupSadAge;
    int groupSadLine;
    int peopleSadTooLong;
    int groupSadTooLong;
    int waitTotal;
    
    //ride state
    String state;
    int running;
    
    int peopleInRide;
    int check;
    
    
    //constructor
    public Ride(PrintWriter outFile, String[] info)
    {
        //assign parameter to variable
        this.outFile = outFile;
        //initialize all arraylist
        line = new ArrayList<VisitorGroup>();
        leaving = new ArrayList<VisitorGroup>();
        onRide = new ArrayList<VisitorGroup>();
        //leavingPark = new ArrayList<VisitorGroup>();
        
        //assign characteristics to the right variable
        name = info[0];
        group = info[1].split(" ");
        space = Integer.parseInt(info[2]);
        length = Integer.parseInt(info[3]);
        capacity = Integer.parseInt(info[4]);
        queue = Integer.parseInt(info[5]);
        
        //ready to start up ride
        state = "finished";
        running = 0;
        check = 0;
        peopleInRide =0;
    }
    
    //update one time step for the ride
    public void updateTimeStep(){
        //increment and decrement time
        for(int i = 0; i< line.size(); i++){
            line.get(i).timeLeft --;
            line.get(i).waiting ++;
            if(line.get(i).timeLeft <=0){
                //leavingPark.add(group);
                peopleSadTooLong += line.get(i).people;
                groupSadTooLong ++;
                line.remove(i);
            }    
        }
        //if ride is finished, ready to load epople at that time step
        if(state.equals("finished")){
            load();
        }
        //ride is loaded, ready to run
        else if (state.equals("loaded")){
            state = "running";
        }
        //if ride ran the length of the ride, unload all people
        else if((state.equals("running") && (running == length))){
            unload();
        }
        //incrmenet time of ride running 
        else{
            
            running++;
        }
        
    }
    //puts people from line on the ride
    public void load(){
        //until all the room on the ride is full
        while(capacity >= peopleInRide){
            //if no one is on line
            if(line.size() ==0 ||check ==line.size()){
                break;
            }
            //if group can fit on the ride 
            else if(
            line.get(check).people < (capacity - peopleInRide)){
                //adjust statistics
                waitTotal += line.get(check).waiting;
                peopleRode += line.get(check).people;
                groupRode ++;
                //remove the ride from the groups wishlist
                line.get(check).wishList.remove(name);
                //add to the arraylist for people on the ride
                peopleInRide += line.get(check).people;
                onRide.add(line.get(check));
                //remove group from the line arraylist
                line.remove(check);
                }
            else{
                check++;
            }
        }
        state = "loaded";
    }
    //unload people on the ride 
    public void unload(){
        peopleInRide = 0;
        check = 0;
        //puts arraylist of grups ready to leavr the ride
        leaving.addAll(onRide);
        //no one on the ride
        onRide.clear();
        state = "finished";
    }
    
    //check if the group can get on line for the ride
    public boolean checkAvailable(VisitorGroup theGroup){
        //check for age restriction
        if(!checkAge(theGroup)){
            //incrmenet stats 
            groupSadAge ++;
            //off wishlist because they csnt ride
            theGroup.wishList.remove(name);
            return false;
        }
        //get the number of people on line already
        int peopleOnLine=0;
        for(int i = 0; i< line.size(); i++){
            peopleOnLine += line.get(i).people;
        }
        //compare to the limit of the max line if the group can get online
        if((queue - peopleOnLine) < theGroup.people){
            groupSadLine ++;
            return false;
        }
        
        return true;
    }
    
    //check if group satisfy age restriction
    public boolean checkAge(VisitorGroup theGroup){
        //goes through the restriction and the people in the group 
        for(int i=0; i< theGroup.types.size(); i++){
            for(int j = 0; j<group.length; j++){
                //if they have the permission to ride the ride
                if(theGroup.types.get(i).equals(group[j]))
                    return true;
            }
        }
        return false;
    }
    //puts a group on their line
    public void joinLine(VisitorGroup theGroup){
            line.add(theGroup);
            theGroup.waiting =0;
        
    }
    //print data stats taken from this ride 
    public void printData(){
        try{
            outFile.println(name);
            outFile.println("Total groups: " + groupRode);
            outFile.println("Total people: " + peopleRode);
            outFile.println("groups unable to ride due to age restriction: " + groupSadAge);
            outFile.println("groups unable to get on line: " + groupSadLine);
            //checks for illegal math operations
            if(groupRode >0){
                outFile.println("Average waiting time per group: "
                + (double)(waitTotal/groupRode));
            }
        }catch(Exception e)
        {System.out.println(e);}
    }

    
    

}
