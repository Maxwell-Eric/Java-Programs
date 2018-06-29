/*************************************
 * Eric Maxwell
 * CSC 479
 * Module 3: Programming Assignment 1
 *************************************/

import java.awt.Point;
import java.util.Stack;

/*
 * This class does a depth level search for a 2x2 floor which needs to be vacuumed.
 */
public class DLS {

	/*
	 * Attributes
	 */
	// Array of classes which implement Action interface which can perform actions on nodes.
	private final Action[] ACTIONS = { new Suck(), new North(), new East(), new South(), new West() };
	private final int NUMBER_OF_CHILDREN = ACTIONS.length;
	private final int MAX_DEPTH = 10;
	private Stack<Node> tree = new Stack<>();
	// Stack of nodes from the root to the goal node
	private Stack<Node> solution;

	/*
	 * DLS Constructor. Calls the depthLS method with a new node(which is the root
	 * node).
	 */
	private DLS() {
		depthLS(new Node());
	}
	
	/*
	 * Begins the depth level search by adding the root node to the tree, checking
	 * to see if the root node is in the goal state, then recursive DLS method is then called.
	 * 
	 * @param root The root node of the tree
	 */
	private void depthLS(Node root) {
		tree.add(root);
		recursiveDLS(root);
	}

	/*
	 * Checks to see if the top on the stack node is a goal node. Recursively adds children to
	 * the top node on the stack until max depth is reached. Once max depth is
	 * reached, all leaf nodes and nodes which have been full expanded are removed. This keeps 
    * the tree size to O(bm) nodes where b is the branch size and m is the depth. 
	 * 
	 * @param n The current node at the top of the stack
	 */
	private void recursiveDLS(Node n) {
		
      //Check for goal node
		if(n.isGoal())
			return;
		
      //Prune tree
		Node current = n;
		while (current.DEPTH == MAX_DEPTH || current.expanded) {
			tree.pop();
			if(tree.empty()) {
				System.out.println("No Goal Node Found!!");
				return;
			}	
			current = tree.peek();
		}
      
      //Expand node at top of the stack
		getChildren(current);
		current.expanded = true;
		
      //Recursive method call
		recursiveDLS(tree.peek());
	}

	/*
	 * Creates child nodes for the node at the top of the stack. The node is copied, then an action is
	 * performed and the node is added to the stack.
	 * 
	 * @param n the node which is being expanded
	 */
	private void getChildren(Node n) {
		int initialIndex = NUMBER_OF_CHILDREN - 1;
		for (int i = initialIndex; i >= 0; --i) {
			Node newNode = new Node(copyFloor(n), copyVacLoc(n), n.DEPTH+ 1, n);
			ACTIONS[i].action(newNode);
			newNode.actionTaken = ACTIONS[i].toString();
			tree.add(newNode);
		}
	}
	
	/*
	 * Creates and returns a stack of nodes from the root to the goal node.
	 * 
	 * @param goalNode the node which is in the goal state
	 * @return Stack<Node>  a stack of nodes from the root to the goal node
	 */
	private static Stack<Node> solution(Node goalNode) {
		Stack<Node> solution = new Stack<>();
		Node current = goalNode;
		while (current != null) {
			solution.push(current);
			current = current.PARENT;
		}
		return solution;
	}

	/*
	 * Copies the vacuum location. Ensures each node has a unique location.
	 * 
	 * @param n       Node from which the vacuum location is being copied
	 * @return Point  a copy of the location of the vacuum from the parent node
	 */
	private static Point copyVacLoc(Node n) {
		Point p = new Point();
		p.x = n.vacLoc.x;
		p.y = n.vacLoc.y;
		return p;
	}

	/*
	 * Copies the state of the floor. Ensures each node has a unique floor state.
	 * 
	 * @param n             Node from which the floor state is being copied.
	 * @return boolean[][]  a copy of the floor state from the parent node
	 */
	private static boolean[][] copyFloor(Node n) {
		boolean[][] f = new boolean[2][2];
		for (int i = 0; i < 2; ++i) {
			for (int j = 0; j < 2; ++j)
				f[i][j] = n.floor[i][j];
		}
		return f;
	}

	/*
	 * Action interface, used to create classes, which can perform actions to the
	 * space, that can be kept in an array.
	 */
	private interface Action {
   
     /*
      * Performs an action on a node to create a child node.
      *
      * @param n  Node on which action is being performed.
      */
		public void action(Node n);
      
     /*
      *Overrides the toString() method
      *
      * @return String  A string representation of the action taken
      */
      @Override
		public String toString();
	}

	/*
	 * Action classes which implement the vacuum actions. The action() methods
	 * change the space state. The toString() describes what action was taken.
	 */
	private class Suck implements Action {
      @Override
		public void action(Node n) {
			n.floor[n.vacLoc.x][n.vacLoc.y] = true;
		}
      @Override
		public String toString() {
			return "Suck";
		}
	}

	private class North implements Action {
      @Override
		public void action(Node n) {
			if (n.vacLoc.y == 0)
				n.vacLoc.y = 1;
		}
      @Override
		public String toString() {
			return "Move North";
		}
	}

	private class East implements Action {
      @Override
		public void action(Node n) {
			if (n.vacLoc.x == 0)
				n.vacLoc.x = 1;
		}
      @Override
		public String toString() {
			return "Move East";
		}
	}

	private class South implements Action {
      @Override
		public void action(Node n) {
			if (n.vacLoc.y == 1)
				n.vacLoc.y = 0;
		}
      @Override
		public String toString() {
			return "Move South";
		}
	}

	private class West implements Action {
      @Override
		public void action(Node n) {
			if (n.vacLoc.x == 1)
				n.vacLoc.x = 0;
		}
      @Override
		public String toString() {
			return "Move West";
		}
	}

	/*
	 * Private inner class node contains the space state, and node information. 
	 */
	private final class Node {

		/*
		 * Attributes
		 */
		private boolean[][] floor;
		private  Point vacLoc;
		private final int DEPTH;
		private final Node PARENT;
		private boolean expanded = false;
		private String actionTaken;
		
		/*
		 * Node constructor for the root node.
		 */
		private Node() {
			floor = new boolean[2][2];
			vacLoc = new Point(0, 0);
			DEPTH = 0;
			PARENT = null;
		}

		/*
		 * Node constructor for children nodes.
		 * 
		 * @param f  The current state of the floor in the parent node.
		 * @param p  The location of the vacuum on the floor in the parent node.
		 * @param d  The depth of the node in the tree
		 * @param pa The parent node.
		 */
		private Node(boolean[][] f, Point p, int d, Node pa) {
			floor = f;
			vacLoc = p;
			DEPTH = d;
			PARENT = pa;
		}

		/*
		 * Displays the state of the node, which includes action taken to get to node from parent, 
		 * the total number of actions to get to the state, the total cost, the vacuum location and
		 * the floor state.
		 */
		private void displayNode() {
			System.out.println("\nAction: " + actionTaken + "\nTotal Actions: " + DEPTH + "\nTotal Cost: " + DEPTH);
			System.out.println("Vaccuumm Location: (" + vacLoc.x + "," + vacLoc.y + ")");
			for (int i = 0; i < 2; ++i)
				for (int j = 0; j < 2; ++j)
					System.out.println("(" + i + "," + j + ")" + " is clean: " + floor[i][j]);
		}

		/*
		 * Checks to see if a node is in the goal state. If so, it crates a stack with
		 * with all nodes from the root to the goal node. A message indicating the goal
		 * has been found and the tree size when then goal was found.
		 * The nodes are then displayed starting at the root node and ending with the goal node.
       *
       * @return boolean  Indicates whether the current node is a goal node.
		 */
		private boolean isGoal() {
			for (boolean[] ba : floor)
				for (boolean b : ba)
					if (b == false)
						return false;

			System.out.print("***************************Found*************************\nTree size: " + tree.size() 
					+ "\n\nInitial State: ");
         //Create solution stack
			solution = solution(this);

         //Display solution stack
			while (!solution.empty()) {
				solution.pop().displayNode();
			}
			return true;
		};
	}

	/*
	 * A timer has been added to measure the time to find the first solution with the DLS
	 */
	public static void main(String[] args) {
		final long startTime = System.currentTimeMillis();
		
		new DLS();
		
		final long endTime = System.currentTimeMillis();
		System.out.println("\nTotal execution time: " + (endTime - startTime)+"ms");
	}
}