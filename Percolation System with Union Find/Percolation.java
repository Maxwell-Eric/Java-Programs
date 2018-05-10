import edu.princeton.cs.algs4.*;


/*****************************************************************************************************
 * Percolation Class
 *    -This class creates a square system which contains nxn sites.
 *    -Sites can be opened and connected through a union find data structure.
 *    -A system percolates if fluid can travel from the top of the system to the bottom through connected sites
 *
 *
 * Used with the PercolationStats class or with the PercolationVisualizer class.
 * 
 ****************************************************************************************************/


public class Percolation {


   /*****************
    * Attributes
    ****************/
	
   //Tells whether a site at the given location is open
	private boolean[][] isOpen;
   
   //Determines where the top of the grid is connected to the bottom
	private boolean[] connectedBottom;
   
   //The index of the top row
	private int top = 0;
   
   //Tells whether the system has percolated
	private boolean hasPercolated = false;
   
   //The size of the system; the system will have sizexsize sites that may be opened
	private int size;
   
   //Number of sites which have been opened
	private int numberOfOpenSites = 0;
   
   //Weighted quick union find data structure used to determine if sites are connected
	private WeightedQuickUnionUF uf;
   
	
   /*********************************************************************************************************************************
    * Constructor
    *    -Creates a square system with nxn sites. 
    *    -Initializes the isOpen 2D array.
    *    -Initializes the connectedBottom array.
    *    -Initializes the union find data structure.
    *
    * @param   n - number of sites for each side of the system. If n = 10, the system will be a 10x10 system with 100 total sites.
    * @throws IlleagleArgumentException if n<1
    **********************************************************************************************************************************/ 
	public Percolation(int n){
      //Check to make sure n is a positive number
		if(n<1)
			throw new IllegalArgumentException();
      
      //Assign the attribute size to the parameter n
		size = n;
      
      //Initialize the 2D array of booleans which tells if each site is open or closed
		isOpen = new boolean[n][n];
      
      //Initialize the array of boolenas which tells if the site is connected to the bootom of the system through the union find 
		connectedBottom = new boolean[n*n+1];
      
      //Initialize the union find data structure
		uf = new WeightedQuickUnionUF(n*n + 1);
	}
   
   
   /***************
    * Methods
    **************/
     
   
   /***********************************************************************
    * toIndex method
    *    - Takes the row and column of a site and returns an index number
    *
    * @param   row - The row of the site
    * @param   column - The column of the site
    * @return  int index - an index for the site
    ***********************************************************************/
	private int toIndex(int row, int column){
		return (row-1)*size + column;
	}
   
   
   
   /*******************************************************
    * open method
    *    - Opens the site at the row and column parameters
    *    - Upates the unions of the site which just opened
    *
    * @parameter  row - The row of the site
    * @parameter  column - The column of the site
    ******************************************************/
	public void open(int row, int column){
		if(!isOpen[row - 1] [column - 1]){
         //Open site in the array by setting the boolean to true
			isOpen[row - 1][column - 1] = true;
         //Increment the number of open sites
			++numberOfOpenSites;
         //Create an index for the site using the toIndex method.
			int index = toIndex(row,column);
			
         //If the row is 1, the site is connected to the top of the system
			if(row == 1){
				uf.union(index, top);
			}
         
         //If the site is on the bottom row, connect the site to the bottom of the system
			if(row == size){
				connectedBottom[index] = true;
				connectedBottom[uf.find(index)] = true;
			}
         
         //Update whether the site is connected to the bottom through the union find data structure
         //Connect the site to any open sites to the right, left, below or above the current site.
			if(row<size && isOpen(row+1, column)){
				updateConnectedBottom(row, column, row+1, column);
				uf.union(index, toIndex(row+1, column));
			}

			if(row>1 && isOpen(row - 1, column)){
				updateConnectedBottom(row, column, row-1, column);
				uf.union(index, toIndex(row-1, column));
			}
			if(column<size && isOpen(row, column+1)){
				updateConnectedBottom(row, column, row, column+1);
				uf.union(index, toIndex(row, column+1));
				
			}
			if(column>1 && isOpen(row, column-1)){
				updateConnectedBottom(row, column, row, column-1);
				uf.union(index, toIndex(row, column-1));
			}
         
         //If the site is full (which meeans there is a path to the top of the system), and it is connected to the bottom
         //then the system has percolated.
			if(isFull(row,column) && connectedBottom[uf.find(toIndex(row,column))])
				hasPercolated = true;
		}
	}
   
	
   /**************************************************************************
    * isOpen method
    *    -Returns boolean telling if the site at the row and column is open
    *
    * @param   row - The row of the site
    * @param   column - The column of the site
    * @return  boolean - tells if the site is open 
    **************************************************************************/
	public boolean isOpen(int row, int column){
		return isOpen[row - 1][column - 1];
	}
   
   
	/******************************************************************************************************************
    * isFull method
    *    - tells if the site is full once opened. 
    *    - full indicates that it is connected to the top and fluid can flow into the site from the top of the system.
    *    - throws index out of bounds exception
    *
    * @param   row - The row of the site
    * @param   column - The column of the site
    * @return  boolean - indicates if the site is full or connected to the top of the system
    * @throws  IndexOutOfBoundsException if the row or column is not possible
    ********************************************************************************************************************/
	public boolean isFull (int row, int column) throws IndexOutOfBoundsException{
		//Check to see if exception needs to be thrown
      if(row > size || row < 1 || column > size || column < 1)
			throw new IndexOutOfBoundsException();
		
      //Return boolean indicating if the site is connected to the top of the system
      return uf.connected(toIndex(row, column), top);
	}
	
   
   /****************************************************************************
    * numberOfOpenSites method
    *    - returns the number of open sites in the system
    *
    * @return int numberOfOpenSites - the number of sites that have been opened
    ***************************************************************************/
	public int numberOfOpenSites(){
		return numberOfOpenSites;
	}
   
	
   /*******************************************************
    * percolates method
    *    - tell whether the system has percolated, which means there is a path from the top to the bottom of the system.
    *
    * @return boolean hasPercolated - true if the system has percolated
    *******************************/
	public boolean percolates(){
		return hasPercolated;
	}
	
   
   /************************************************************************************************************************************************
    * updateConnectedBottom method
    *    - Sets the boolean in the connectedBottom array at the index to true if it connected to the bottom through the union find data structure.
    *
    * @param row1 - The row of the newly opened site
    * @param column1 - The column of the newly opened site
    * @param row2 - The row of an open site next to the newly opened site
    * @param column2 - The column of an open site next to the newly opensite (corresponds to row2 site)
    ************************************************************************************************************************************************/
	private void updateConnectedBottom(int row1, int column1, int row2, int column2){
      //get index of newly opened site.
		int index = uf.find(toIndex(row1, column1));
      //get index of open site next to newly opened site
		int root = uf.find(toIndex(row2,column2));
      //If either site is connected to the bottom, then both are connected
		if(connectedBottom[index] || connectedBottom[root]){
			connectedBottom[root] = true;
			connectedBottom[index] = true;
		}
	}
}
