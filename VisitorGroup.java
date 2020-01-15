import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Random;
import java.util.*;
/**
 * Write a description of class VisitorGroup here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class VisitorGroup
{
    //instance variables
    filesReader reader;
    Random random = new Random();
    
    //characteristics of ride
    String type;
    int people;
    LinkedList<String> types;
    int timeLeft;
    ArrayList<String> wishList;
    
    int waiting;
    int currentTime;
    int closeTime;
    ArrayList<String> rides;
    int ridel;
    
    //default constructor
    public VisitorGroup()
    {
        
    }
    
    public VisitorGroup(String configFile, int currentTime, 
    int closeTime){
        reader = new filesReader(configFile);
        
        //assigns value from parater to a variable
        this.currentTime = currentTime;
        this.closeTime = closeTime;
        rides = reader.getRidesList();
        
        //generate and set characteristics 
        generateGroupType();
        generateNumber();
        generatePeopleTypes();
        generateStaytime();
        generateRidesToRide(configFile);
        
        
    }
    
    //randomly chooses group type
    public void generateGroupType(){
        //number generate randomly to choose the group type
        int roulette = random.nextInt(100);
        //percentage of distribution
        int[] numbers = reader.getpDistribution("groupDistribution");
        //checks where the roulette lied 
        if(roulette < numbers[0])
            type =  "individual";
        else if(roulette >= numbers[0] && roulette < numbers[1])
            type =  "family";
        else{
            type = "friends";}
    }
    
    //randomly chooses the number of people in the group 
    public void generateNumber(){
        //get config data
        int[] numbersC = reader.getpDetail("numberChildren");
        int[] numbersF = reader.getpDetail("numberFriend");
        
        //generate appropriately to the group type 
        if(type.equals("individual"))
            people = 1;
        else if(type.equals("family")){
            people = 2 + (int)Math.round(random.nextGaussian()*numbersC[1]+numbersC[0]);
        }
        else{
            people = (int)Math.round(random.nextGaussian()*numbersF[1]+numbersC[0]);
        }
    }
    
    //generate the specific type of age group in the group
    public void generatePeopleTypes(){
        //store everyone's age group 
        types = new LinkedList<String>();
        int[] numbers;
        
        //accordingly to the group type
        if(type.equals("individual")){
            //distribution for an individual 
            numbers = reader.getpDistribution("individual");
            //roulette
            int roulette = random.nextInt(100);  
            //compare to roulette and distribution 
            if(roulette < numbers[0]){
                types.add("adult");
            }
            else if(roulette < numbers[1]){
                types.add("youngAdult");
            }
            else {
                types.add("teenager");
            }
            return;
        }
        else if(type.equals("family")){
            //family has teo adults aka parents 
            types.add("adult");
            types.add("adult");
            //get data from config
            numbers = reader.getpDetail("ageChild");
            //generate random age for the number of kids the group has 
            for (int child = 0; child< (people-2); child++){
                int childAge = (int)Math.round(random.nextGaussian()
                *numbers[1] + numbers[0]);
                if(childAge >= 22)
                    types.add("adult");
                if(childAge >= 18 && childAge < 22)
                    types.add("young_adult");
                if(childAge >= 13 && childAge <18)
                    types.add("teenager");
                if(childAge >= 12 && childAge < 5)
                    types.add("adolescent");
                if(childAge<= 5)
                    types.add("toddler");
            }
            return;
        }
        else{
            //when group is friends
            //get data form config file 
            numbers = reader.getpDistribution("friendAge");
            int roulette = random.nextInt(100);
            //generate comparing the roulette to the distribution 
            if(roulette < numbers[0]){
                for(int i = 0; i< people ; i++){
                    types.add("adult");}
            }
            else if(roulette < numbers[1]){
                for(int i = 0; i< people ; i++){
                    types.add("youngAdult");}
            }
            else {
                for(int i = 0; i< people ; i++){
                    types.add("teenager");}
    
            }
            return;
        }
    }
    
    //generate the time each group csn spend in the park 
    public void generateStaytime(){
        //likeliness for each group to have a restriction on time 
        int possibility;
        //accordingly to each group type
        if(type.equals("individual"))
            possibility = 7;
        else if(type.equals("family")){
            //more possibilty of age restricton with a toddler 
            if((types.contains("toddler") && (types.contains("adolescent"))))
                possibility = 9;
            else if((types.contains("teenager") && (types.contains("young_adult"))))
                possibility = 8; 
            else{
                possibility = 5;}
        }else{
            possibility = 6;}  
            
        //randomly generates the length of time 
        
        //no time restriction
        if(random.nextInt(10)>possibility)
            timeLeft = closeTime - currentTime;
        else{
            //random time restriction 
            timeLeft = (closeTime/4) + 
            ((int)Math.round((closeTime-currentTime)*random.nextInt(5))/10);
        } 
        
    }
    
    //makes ride wishlist 
    public void generateRidesToRide(String configFile){
        
        reader = new filesReader(configFile);
        //get data from config file 
        String[][] allRides = reader.getRides();
        //get all name of rides
        for(int i = 0; i< allRides.length; i++){
            rides.add(allRides[i][0]);
        }
        
        //their wish list 
        wishList = new ArrayList<String>();
        //random length of their wish list 
        ridel = random.nextInt(rides.size()+1);
        //they will all have more than 3 rides they want to ride
        while(ridel <4){
            ridel = random.nextInt(rides.size()+1);}  
            
        //randomly pick the rides they want to ride 
        for(int i = 0; i<ridel; i++){
            int ride = random.nextInt(rides.size());
            wishList.add(rides.get(ride));
            rides.remove(ride);
        }
    }
    
    public boolean findRide(Rides allRides){
        //compare the ride in the park to the ride from their wishlist
        for(int j = 0;j< allRides.getRidesList().size(); j++){
            for(int i = 0; i<wishList.size(); i++){
                //check if the rides match, then check for availability
                if((allRides.getRidesList().get(j).name.equals(wishList.get(i))) 
                && allRides.getRidesList().get(j).checkAvailable(this)){
                    //if available, add the group to the line for the ride
                    allRides.getRidesList().get(j).joinLine(this);
                    return true;
                }
            }
        }
        return false;
    }
}
