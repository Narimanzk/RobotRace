package ca.mcgill.ecse211.project;

import static ca.mcgill.ecse211.project.Resources.*;
import static ca.mcgill.ecse211.project.Movement.*;

import java.lang.Thread;
import simlejos.ExecutionController;
import simlejos.hardware.ev3.LocalEV3;

/**
 * Main class of the program.
 *
 * TODO Describe your project overview in detail here (in this Javadoc comment).
 */
public class Main {

  /** Main entry point. */
  public static void main(String[] args) {
    ExecutionController.performPhysicsSteps(INITIAL_NUMBER_OF_PHYSICS_STEPS);
    ExecutionController.performPhysicsStepsInBackground(PHYSICS_STEP_PERIOD);

    // Start the odometer thread and update the number of threads
    new Thread(odometer).start();
    ExecutionController.setNumberOfParties(NUMBER_OF_THREADS);
    odometer.printPosition();

    // TODO Replace these method calls with your own logic
    beep(3);
    int startingCorner = getStartingPoint();
    initialLocalize(startingCorner);
        
    System.exit(0);
  }
  

  
  // TODO : Put this in a localization class
  public static void initialLocalize(int startingCorner) {
    UltrasonicLocalizer.localize();
    LightLocalizer.localize_start();
    
    // TODO : localize based on starting corner.
    switch(startingCorner) {
      case(0):
        println("Bottom left");
        break;
      case(1):
        println("Bottom right");
        break;
      case(2):
        println("Top right");
        break;
      case(3):
        println("Top left");
        break;
      default:
        println("Error getting starting corner");
    }

    
  }
  

  public static int getStartingPoint() {
    return (redTeam==3) ? redCorner : greenCorner;
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  /**
   * Example using WifiConnection to communicate with a server and receive data concerning the
   * competition such as the starting corner the robot is placed in.<br>
   * 
   * <p>Keep in mind that this class is an <b>example</b> of how to use the Wi-Fi code; you must use
   * the WifiConnection class yourself in your own code as appropriate. In this example, we simply
   * show how to get and process different types of data.<br>
   * 
   * <p>There are two variables you MUST set manually (in Resources.java) before using this code:
   * 
   * <ol>
   * <li>SERVER_IP: The IP address of the computer running the server application. This will be your
   * own laptop, until the beta beta demo or competition where this is the TA or professor's laptop.
   * In that case, set the IP to the default (indicated in Resources).</li>
   * <li>TEAM_NUMBER: your project team number.</li>
   * </ol>
   * 
   * <p>Note: You can disable printing from the Wi-Fi code via ENABLE_DEBUG_WIFI_PRINT.
   * 
   */
  public static void wifiExample() {
    // Note that we are using the Resources.println() method, not System.out.println(), to ensure
    // the team number is always printed
    println("Running...");

    // Example 1: Print out all received data
    println("Map:\n" + wifiParameters);

    // Example 2: Print out specific values
    println("Red Team: " + redTeam);
    println("Green Zone: " + green);
    println("Island Zone, upper right: " + island.ur);
    println("Red tunnel footprint, lower left y value: " + tnr.ll.y);
    println("All waypoints: " + waypoints);

    // Example 3: Compare value (simplified example)
    if (overpass.endpointA.x >= island.ll.x && overpass.endpointA.y >= island.ll.y) {
      println("Overpass endpoint A is on the island.");
    } else {
      errPrintln("Overpass endpoint A is in the water!"); // prints to stderr (shown in red)
    }
    
    // Example 4: Calculate the distance between two waypoints
    println("Distance between waypoints 3 and 5:",
        Navigation.distanceBetween(waypoint(3), waypoint(5)));

    // Example 5: Calculate the area of a region
    println("The island area is " + island.getWidth() * island.getHeight() + ".");
  }
  
  
  


}
