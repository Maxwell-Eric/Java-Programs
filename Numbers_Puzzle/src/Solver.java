import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	
	private boolean isSolvable;
	private int moves;
	private Stack<Board> solution = new Stack<>();
	
	public Solver(Board initial){
		if(initial == null)
			throw new IllegalArgumentException();
		boolean found = false;
		Node first = new Node(initial, 0, null);
		Node second = new Node(initial.twin(), 0, null);
		MinPQ<Node> pq = new MinPQ<>();
		pq.insert(first);
		pq.insert(second);
		
		while(!found){
		
			Node current = pq.delMin();
			if(current.board.isGoal()){
				solution.push(current.board);
				moves = current.moves;
				
				while(current.previous != null){
					solution.push(current.previous.board);
					current = current.previous;
				}
				
				if(solution.peek().equals(first.board)){
					isSolvable = true;
					found = true;
				}
				
				if(solution.peek().equals(second.board)){
					isSolvable = false;
					moves = -1;
					found = true;
				}
			}
		
			else{
				Iterable<Board> nBoards = current.board.neighbors();
				for(Board b : nBoards){
					if(current.previous == null || !b.equals(current.previous.board)){
						pq.insert(new Node(b, current.moves + 1, current));
					}
				}
			}
		}
	}
	
	private class Node implements Comparable<Node>{
		Board board;
		int moves;
		Node previous;
		int manh;
		
		public Node(Board board, int moves, Node previous){
			this.board = board;
			this.moves = moves;
			this.previous = previous;
			manh = board.manhattan();
		}
		
		public int compareTo(Node that){
			
			if(this.manh + this.moves == that.manh + that.moves){
				if(this.manh > that.manh)
					return 1;
				if(this.manh < that.manh)
					return - 1;
				else
					return 0;
			}
			
			if(this.manh + this.moves > that.manh + that.moves)
				return 1;
			
			else 
				return -1;
		}
	}
	
	public Iterable<Board> solution(){
		if(isSolvable)
			return solution;
		else
			return null;
	}
	
	public boolean isSolvable(){
		return isSolvable;
	}
	
	public int moves(){
		return moves;
	}
	
	public static void main(String[] args){
		
		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	
	/*
		int[][] blocks = new int[3][3];
		blocks[0][0] = 3;
		blocks[0][1] = 7;
		blocks[0][2] = 5;
		blocks[1][0] = 4;
		blocks[1][1] = 8;
		blocks[1][2] = 1;
		blocks[2][0] = 6;
		blocks[2][1] = 2;
		blocks[2][2] = 0;
		Board b = new Board(blocks);
		//System.out.println(b.toString());
	
		Solver s = new Solver(b);
		//System.out.println(s.moves());
         
		//for(Board board : s.solution){
			//System.out.println(board.toString());
		//}*/
		
	}
}
