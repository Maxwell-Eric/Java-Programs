## This project is associated with Princeton's Algorithms Part I class from coursera.
### It creates a kdTree data structure with points, which are shown on a graph. When a point is added to the kdTree data structure, a verticle blue or horizontal red line is created through the point on the graph. The colored line will stop at the line which contains the parent point, or any point higher in the binary tree (both of which will be the other color of the line containing the new point). The root node's line will be a blue line which extends across the entire window.  A jar file was created and file can be supplied upon request.



### GUI
![kdtree 6_30_2018 8_50_47 pm](https://user-images.githubusercontent.com/24630618/42130630-b7341f1e-7ca7-11e8-9b87-fd6e306c615b.png)
 
 ### There are two programs contained in this project. 
 
  - The first program imports points from text files. A JComboBox reads files for selection. When the start button is pressed, the file path and name are passed to the KdTree class. The list of points in the text file are added to a KdTree and a window is displayed that shows the points with the blue and red lines. The KDTreeGUI.java file was created as a front end GUI for the user and made into a jar file. The program can be executed by downloading the repository and running the KDTreeGUI.java file with the kdtree-testing folder in the appropriate location.
  
 ![standard draw 6_30_2018 8_56_59 pm](https://user-images.githubusercontent.com/24630618/42130663-7209ac8c-7ca8-11e8-94fd-4aa6aad438df.png)
 
<br><br><br>
 
 - The second program opens a blank window. The user clicks on the window to add a point to that spot. The graph will add blue or red line depending on where in the tree the point is added. The point's location in the balanced binary tree can be determined by where the line starts and stops. There is jar file included to execute this program named KDTree_Interactive.jar.

![standard-draw-6_30_2018-9_01_59-pm](https://user-images.githubusercontent.com/24630618/42130697-50bc0736-7ca9-11e8-909f-890de0c160ce.gif)
