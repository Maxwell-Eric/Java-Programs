/********************
 * Eric Maxwell 
 *******************/

import javax.swing.JFrame;
import java.util.LinkedList;
import java.util.List;

/**********************************************************************************************************
 * RocketPerformance class
 *    -This class uses Newton's second law and the kinematic equations to predict rocket model performance.
 *    -Time is incremented at intervals of 0.01 seconds and all rocket parameters are updated.
 *    -This class is designed to predict rocket performance for a high school physics class.
 *    -It assumes constant thrust, but drag and weight are not constant.
 **********************************************************************************************************/

public class RocketPerformance{

   //Linked list of time, acceleration, velocity and height of the rocket
   private List<Integer> time = new LinkedList<>();
   private List<Double> a = new LinkedList<>();
   private List<Double> v = new LinkedList<>();
   private List<Double> h = new LinkedList<>();

   //Rocket parameters
   private double thrust;
   private double burnTime;
   private double initMass;
   private double finalMass;
   private double dragCoeff;
   private double airDensity;
   private double velocity;
   private double height;
   private double refArea;
   private double maxVelocity;
   
   //Graph the shows the rockets performance characteristics.
   private RocketGraph graph;
   
   /**********************
    * Consturctor
    *   
    *    @params   time, initial mass, final mass, drag coeffiecient, burn time, air density, reference area
    **********************/
   public RocketPerformance(double t, double im, double fm, double dc, double bt, double ad, double ra){
      
      //Assign parameters to class attributes
      thrust = t;
      burnTime = bt;
      initMass = im;
      finalMass = fm;
      dragCoeff = dc;
      airDensity = ad;
      refArea = ra;
      velocity = 0;
      height = 0;
      
      //Call the launch function, which predicts the performance based on the parameters.
      launch();
   }

   /*************************************
    * launch method
    *    -Creates lists of time, acceleration, velocity and height for the rocket's performance
    ************************************/
   private void launch(){
   
      //Acceleration is zero at time = 0
      double acc = 0.0;
      //dt is the incremental change in time, which is used to update rocket performance characteristics
      double dt = .01;
      //time
      double t;
      //Change in mass is initial mass - final mass. This is the amount of fuel that was burned.
      double dmass = initMass - finalMass;
      //The mass is the initial mass at time = 0.
      double mass = initMass;
      
      //From time = 0 until the motor burns out (Boost Phase)
      for(t = 0.0; t<=burnTime; t=t+dt){
         
         //add current time, velocity, height and acceleration to respective lists.
         time.add((int)(t*100));
         v.add(velocity);
         h.add(height);
         a.add(acc);
         
         //Update acceleration using net Force divded by current mass. Drag changes with velocity
         acc = (thrust - mass*9.81 - .5*airDensity*dragCoeff*refArea*velocity*velocity)/mass;
         
         //Acceleration will never be negative during boost.
         if(acc < 0)
            acc = 0;
         
         //Update velocity, height and mass for the time increment
         velocity += acc*dt;
         height += velocity*dt + .5*acc*dt*dt;
         mass = mass - dmass/burnTime*dt;
      }
     
      //Max velocity is the velocity at burn out
      maxVelocity = velocity;
      
      //While the velocity is positive, the model rocket will remain in coast phase moving upward. Mass will remain constant.
      while(velocity >0){
         //Increment time, update acceleration, velocity and height... then add them to their respective lists
         t += dt;
         acc = ( - mass*9.81 - .5*airDensity*dragCoeff*refArea*velocity*velocity)/mass;
         velocity += acc*dt;
         height +=velocity*dt + .5*acc*dt*dt;
         time.add((int)(t*100));
         h.add(height);
         v.add(velocity);
         a.add(acc);
      }
      //Create a graph of the performance based on the lists
      graph = new RocketGraph(a, v, h, time, burnTime, t, formatDouble(maxVelocity), formatDouble(h.get(h.size()-1)));
   }
   
   /*************
    * formatDouble method
    *    -This method takes a double as a parameter and returns a double with two decimals.
    *    @param   double to be truncated
    *    @return  double that has been truncated
    ****************/
   private double formatDouble(double number){
      return Double.parseDouble(String.format("%.2f", number));
   }
}