## Model Rocket Performance Estimator for High School Physics
This program is designed to use with a highschool physics class studing Newton's Laws of Motion and the kinematic equations. A model rocket is launched with an altimiter. The parameters of the rocket are put into the GUI and a performance graph is produced. The program takes into account change in mass and velocity to calculate instantaneous acceleration, velocity and height using the equation acceleration = Net Force/mass and the kinematic equations of motion. The system is losing mass due to propellent being ejected and the drag force (D = .5 * drag coeffecienct * air density * velocity squared * reference area) changes with the square of the velocity. These changes in force have a major impact on the rockets performance. Time is incremented and performance parameters are updated every 0.01 seconds. An altimiter is placed in the rocket and the results are compared to the graph produced.  

### GUI
![rocket information 6_30_2018 10_41_14 pm](https://user-images.githubusercontent.com/24630618/42131092-c59fb84c-7cb6-11e8-8858-56bef9be2eb6.png)

<br><br><br>
### Output
![model rocket performance 6_30_2018 10_41_21 pm](https://user-images.githubusercontent.com/24630618/42131093-c804651a-7cb6-11e8-9047-5f5d434ef254.png)

This graph shows max velocity and height. The program takes into consideration the change in mass during burn phase and the change in drag with the change in velocity.  This graph gives a good estimate of performance along with what the delay charge of the motor should be. 
The class launches a rocket with an altimeter and compares the results to the performance estimated by the graph, then reports on the findings. 
