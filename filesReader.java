//import libraries
import java.util.Scanner;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.*;
import java.util.List;

/**
 * Write a description of class ConfigFile here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class filesReader
{
    String configFile;
    

    
    public filesReader(String configFile)
    {
        //assign parameter to a variable 
        this.configFile = configFile;

    }
    
    //finds and returns the size of the park
    public int getParkSpace(){
        try{
            //scanner open 
            Scanner config = new Scanner(new FileReader(configFile));
            //stores the line the Scanner is currently at
            String line = "";
                
            //checks for the line that has the value for park space 
            //moves on the line until it does
            while (config.hasNextLine() 
            && !(line.split(" ")[0].equals("space"))){
                    //moves on to the next line
                    line = config.nextLine();
            }
            //exits while loop when it finds the line
            
            //Scanner cloes
            config.close();
            
            return Integer.parseInt(line.split(" ")[1]);
        }
        catch(Exception e){
            System.out.println(e);
            return 0;}
        
    }
    
    
    //get the specified characteristic of the ride
    //FINDS DATA CLOSEST TO THE SCANNER TAKEN THROUGH THE PARAMETER
    public String getrDetailwithScanner(Scanner configP, String type){
        try{
            //stores the line the Scanner is currently at
            String line = "";
            //checks the line if it has the specified data
            while(configP.hasNextLine() 
            && !(line.split(" ")[0].equals(type))){
                line = configP.nextLine();
            }
            //finds the line 
            
            return line.substring(type.length()+1, line.length());
        }catch(Exception e){
            System.out.println(e);
            return null;}
    }
    
    //returns an ArrayList with just the name of all the rides in the park
    public ArrayList<String> getRidesList(){
        //stores all the name 
        ArrayList<String> ridesList = new ArrayList<String>();
        //stores the line the Scanner is currently at
        String line;
        try{
            //Scanner open 
            Scanner config = new Scanner(new FileReader(configFile));
            
            //moves on until it goes through all the line in file
            while(config.hasNextLine()){
                line = config.nextLine();
                //when it finds the word type, it takes string left in the line
                if(line.split(" ")[0].equals("type"))
                    ridesList.add(line.substring(5));
                    
            }
            //Scanner closes
            config.close();
            return ridesList;
        }catch(Exception e){
            System.out.println(e);
            return null;}
    }
    
    
    //returns array within an array with all the ride characteristicd
    public String[][] getRides(){
        //number of rides 
        int n = 0;
        //keeps in track how many rides characteristics were taken 
        int N = 0;
        //characteristics listed for each ride 
        String[] rideDetails = 
        {"type", "group", "space", "length", "capacity", "queue"};
        //stores the line the Scanner is currently at
        String line = "";
        try{
            //Scnner open
            Scanner config = new Scanner(new FileReader(configFile));
            //goes through every line until the end of file 
            while(config.hasNextLine()){
                line = config.nextLine();
                //every word ride, it will count how many rides there are 
                if(line.split(" ")[0].equals("ride"))
                    n++;
            }
            //initialize the array within an array to return 
            String[][] rides = new String[n][rideDetails.length];
            //Scanner open
            config = new Scanner(new FileReader(configFile));
            //takes the data until it reaches the end of the file
            while(config.hasNextLine()){
                line = config.nextLine();
                //once the characteristics for all the rides are taken
                if(N == n)
                    break;
                //finds line where the ride chsracteristics start
                if(line.split(" ")[0].equals("ride") && N<n){
                    for(int i = 0; i< rideDetails.length; i++){
                        line = config.nextLine();
                        //store variables in correct index
                        rides[N][i] = line.substring(line.split(" ")[0].length() + 1);
                    }
                    N++;
                }
            }
            //Scanner close
            config.close();
            
            
            return rides;
                
        }catch(Exception e){
            System.out.println(e);
            return null;}
    }
    
    
    public int[] getpDetail(String type){
        try{
            //Scanner open
            Scanner config = new Scanner(new FileReader(configFile));
            //array to return 
            int[] data = new int[2];
            //stores the line the Scanner is currently at
            String line = "";
            
            //loops till the end of file 
            while(config.hasNextLine()){
                //makes an array for each line
                String[] lines = config.nextLine().split(" ");
                //if the line has more than two words
                if(lines.length > 2){
                    //finds the locstion of specified data
                    if (lines[0].equals(type)){
                        //store variable
                        data[0] = Integer.parseInt(lines[1]);
                        data[1] = Integer.parseInt(lines[2]);
                        //takes out loop
                        break;
                    }
                }
                
            }
            //Scanner closes
            config.close();
      
            return data;
        }catch(Exception e){
            System.out.println(e);
            return null;}
    }
    
    
    //gets distribution by percentage of people
    public int[] getpDistribution(String distribution){
        try{
            //Scanner open 
            Scanner config = new Scanner(new FileReader(configFile));
            //return array
            int[] data = new int[3];
            //stores the line the Scanner is currently at
            String line = "";
            
            //goes through until the end of file 
            //until it finds the line of specified data
            while(config.hasNextLine() 
            && !(line.split(" ")[0].equals(distribution))){
                line = config.nextLine();
            }

            //store in array to return 
            for(int i = 0; i<3; i++){
                data[i] = Integer.parseInt(line.split(" ")[i+1]);
            }
            //Scanner closes
            config.close();
            return data;  
        }catch(Exception e){
            System.out.println(e);
            return null;}
    }
    
    
}
