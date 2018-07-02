import java.util.LinkedList;
import java.util.List;

public class Board {
	private int[][] blocks;
	private int manh = 0;
	private int hamm = 0;
	
	public Board(int[][] blocks){
		this.blocks = blocks;
		calcManhattan();
		calcHamming();
	}
	
	public int dimension(){
		return blocks.length;
	}
	
	public int hamming(){
		return hamm;
	}
		
	private void calcHamming(){
		
		int count = 1;
		for(int i=0; i<blocks.length; ++i)
			for(int j=0; j<blocks.length; ++j){
				if( i == blocks.length - 1 && j == blocks.length - 1)
					break;
				if(blocks[i][j] != count)
					++hamm;
				++count;
			}	
	}
	
	private void calcManhattan(){
		
		for(int i=0; i<blocks.length; ++i)
			for(int j=0; j<blocks.length; ++j){
				int num = blocks[i][j] - 1;
				if(num > -1){
					int row = num/blocks.length;
					int column = num%blocks.length;
					manh += Math.abs(row - i) + Math.abs(column - j);
				}
			}
	}
	
	public int manhattan(){
		return manh;
	}
	
	public boolean isGoal(){
		int count = 1;
		for(int i=0; i<blocks.length; ++i)
			for(int j=0; j<blocks.length; ++j){
				if(i== blocks.length -1 && j == blocks.length -1)
					break;
				if(blocks[i][j] != count){
					return false;
			}
			++count;
		}
		return true;
	}
	
	public Board twin(){
		
		int[][] twinblocks = copyBlocks();
		int or=0, oc=0;
		int tr=0, tc=0;
		boolean oneFound = false;
		boolean twoFound = false;
		for(int i=0; i<twinblocks.length; ++i){
			for(int j=0; j<twinblocks.length; ++j){
				if(twinblocks[i][j] == 1){
					oneFound = true;
					or = i;
					oc = j;
					
				}
						
				if(twinblocks[i][j] == 2){
					twoFound = true;
					tr = i;
					tc = j;
				}
			}
			if(oneFound && twoFound)
				break;
		}
		twinblocks[or][oc] = 2;
		twinblocks[tr][tc] = 1;
			
		return new Board(twinblocks);
	}
	
	public boolean equals(Object y){
		if(y == null)
			return false;
		if(this == y)
			return true;
		if(y.getClass() != this.getClass())
			return false;
		Board that = (Board) y;
		
		if(this.blocks.length != that.blocks.length)
			return false;
		for(int i=0; i<blocks.length; ++i)
			for(int j=0; j<blocks.length; ++j){
				if(this.blocks[i][j] != that.blocks[i][j])
					return false;
					
			}
		return true;
	}
	
	public Iterable<Board> neighbors(){
		List<Board> neighborBoards = new LinkedList<>();
	
		for(int i=0; i<blocks.length; ++i)
			for(int j=0; j<blocks.length; ++j)
				if(blocks[i][j] == 0){
					if(i - 1 >= 0){
						int[][] nBlocks = copyBlocks();
						int temp = nBlocks[i][j];
						nBlocks [i][j] = nBlocks[i-1][j];
						nBlocks [i-1][j] = temp;
						
						neighborBoards.add(new Board(nBlocks));
					}
					
					if(i+1 <= blocks.length - 1){
						int[][] nBlocks = copyBlocks();
						int temp = nBlocks[i][j];
						nBlocks [i][j] = nBlocks[i+1][j];
						nBlocks [i+1][j] = temp;
						neighborBoards.add(new Board(nBlocks));
					}
					
					if(j-1 >= 0){
						int[][] nBlocks = copyBlocks();
						int temp = nBlocks[i][j];
						nBlocks [i][j] = nBlocks[i][j-1];
						nBlocks[i][j-1] = temp;
						neighborBoards.add(new Board(nBlocks));
					}
					
					if(j+1 <= blocks.length -1){
						int[][] nBlocks = copyBlocks();
						int temp = nBlocks[i][j];
						nBlocks [i][j] = nBlocks[i][j+1];
						nBlocks[i][j+1] = temp;
						neighborBoards.add(new Board(nBlocks));
					}
				}
		return neighborBoards;
	}
	
	public int[][] getInts(){
		return  blocks;
	}
	
	public String toString() {
		int n = blocks.length;
	    StringBuilder s = new StringBuilder();
	    s.append(n + "\n");
	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	            s.append(String.format("%2d ", blocks[i][j]));
	        }
	        s.append("\n");
	    }
	    return s.toString();
	}
	
	private void display(){
		for(int[] ia : blocks){
			for(int i : ia)
				System.out.print(i+"    ");
			System.out.println();
		}
	}
	
	private int[][] copyBlocks(){
		int[][] copyblocks =new int[blocks.length][blocks.length];
		for(int i=0; i<blocks.length; ++i)
			for(int j=0; j<blocks.length; ++j)
				copyblocks[i][j] = blocks[i][j];
		return copyblocks;
	}
	
	public static void main(String[] args){
		
		int[][] blocks = new int[3][3];
		blocks[0][0] = 5;
		blocks[0][1] = 0;
		blocks[0][2] = 3;
		blocks[1][0] = 4;
		blocks[1][1] = 1;
		blocks[1][2] = 6;
		blocks[2][0] = 7;
		blocks[2][1] = 8;
		blocks[2][2] = 2;
		Board b = new Board(blocks);
		b.display();

		System.out.println(b.toString());
		System.out.println(b.hamming());
		System.out.println(b.manhattan()+ " "+ b.isGoal());
		Board t = b.twin();
		t.display();
		for(Board board : b.neighbors()){
			
			System.out.println();
			board.display();
		}
		
		System.out.println();
		
	}
}
