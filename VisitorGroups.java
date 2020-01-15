import java.util.LinkedList;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;
/**
 * Write a description of class VisitorGroups here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class VisitorGroups
{
    //groups looking for a ride to get on line
    ArrayList<VisitorGroup> groups;
    //number of groups left satisfied
    int satisfiedGroups;
    int groupSadNoLine = 0;
    
    PrintWriter outFile;
    
    public VisitorGroups(PrintWriter outFile)
    {
        //assign parameter to variable
        this.outFile = outFile;
        //initialize groups
        groups = new ArrayList<VisitorGroup>();
        
        satisfiedGroups = 0;
    }
    
    //add groups to the container
    public void addGroup(VisitorGroup newGroup){
        groups.add(newGroup);
    }
    
    //add arraylist groups to the container
    public void addGroups(ArrayList<VisitorGroup> newGroups){
        groups.addAll(newGroups);
    }
    
    //looks for a ride for all the groups in the container
    public void findRide(Rides allRides){
            for(int i = 0; i< groups.size(); i++){
                //if wish list is all complete
                if(groups.get(i).wishList.isEmpty()){
                    //increment number of satisfied groups
                    satisfiedGroups ++;
                    //leave park
                    groups.remove(i);
                }
                //looks for a ride to ride
                groups.get(i).findRide(allRides);
                
            
                    
            }
        }
    
    
    //prints data 
    public void printData(){
        try{
            //print the number of satisfied groups
            outFile.println("groups who satisfied their wishList: "
            + satisfiedGroups);
            
        }catch(Exception e)
        {System.out.println(e);}
    }
}
