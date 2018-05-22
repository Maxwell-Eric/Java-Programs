## This project creates a square system with sites that can be opened. 
## The system percolates when there is a path from the top of the system to the bottom.

A union find data structure is used to determine whether sites are connected.

There are two executable programs in this project. 
  - The first project calculates the statistics to cause a system to percolate. The user inputs the number of sites on each side and the number of trials. The mean percentage of sites needed to be removed to cause a system to percolate is calculated. The standard deviation and the confidence interval are also calculated and included. There is a jar file for this program call Percolation_Stats.jar.
  - The second project displays a system and removes sites based on an input file.   
