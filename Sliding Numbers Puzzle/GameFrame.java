import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class GameFrame extends JFrame implements ActionListener{
	
	private JPanel gamePanel = new JPanel();
	private List<JButton> buttons;
	private List<JPanel> panels;
	private JButton minMoves = new JButton("Click to Get Minimum Number of Moves");
	private JButton solveB = new JButton("Click to Solve");
	private JLabel text = new JLabel();
	private JLabel numOfMovesL = new JLabel();
	private Runnable solvePuzzle;
	private int edgeLength = 3;
	private int size = edgeLength * edgeLength;
	private int openPanelIndex;
	private int numberOfMoves = 0;
	
	public GameFrame(){
		super("Number Puzzle");
		gamePanel.setSize(500, 500);
		gamePanel.setLayout(new GridLayout(edgeLength, edgeLength, 5 , 5));
		gamePanel.setBackground(Color.MAGENTA);
		Border b = BorderFactory.createBevelBorder(BevelBorder.RAISED);
		gamePanel.setBorder(b);
		add(gamePanel);
		
		numOfMovesL.setText("Number Of Moves: "+numberOfMoves);
		numOfMovesL.setBounds(525, 450, 150, 30);
		add(numOfMovesL);
		
		text.setBounds(575, 25, 50, 30);
		add(text);
		
		minMoves.setBounds(525, 100, 150, 100);
		minMoves.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Board b = new Board(getBoard());
				Solver s = new Solver(b);
				text.setText(s.moves()+"");
				//System.out.println(s.moves());
			}
		});
		
		solveB.setBounds(525, 300, 150, 100);
		solveB.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				Board current = new Board(getBoard());
				Solver s = new Solver(current);
				Iterable<Board> boards = s.solution();
				
				solvePuzzle = new Runnable(){
					
					public void run(){
						for(Board b : boards){
							int[][] bi = b.getInts();
							int iterator = 0;
							for(int i=0; i<edgeLength; ++i){
								for(int j=0; j<edgeLength; ++j){
									
									JButton currentB = buttons.get(iterator);

									if(currentB != null && Integer.parseInt(currentB.getText()) != bi[i][j]){
										buttons.set(openPanelIndex, currentB);
										panels.get(openPanelIndex).add(currentB);
										buttons.set(iterator, null);
										openPanelIndex = iterator;
										text.setText(new Solver(b).moves()+"");
										GameFrame.this.revalidate();
										GameFrame.this.repaint();
										try {
											Thread.sleep(300);
										} catch (InterruptedException e1) {
											e1.printStackTrace();
										}
									}
									++iterator;
								}
							}
						}
					}
				};
				Thread thread = new Thread(solvePuzzle);
				thread.start();
			}
		});
		add(solveB);
		add(minMoves);
		
		setSize(700, 547);
		buttons = new ArrayList<>(size);
		panels = new ArrayList<>(size)	;
		setLayout(null);//new GridLayout(edgeLength, edgeLength, 5, 5));
		
		for(int i=0; i<size; ++i){
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBackground(Color.MAGENTA);
			panels.add(panel);
			gamePanel.add(panel);
			if(i< size -1){
				JButton button = new JButton((i+1)+"");
				button.addActionListener(this);
				buttons.add(button);
			}
			else
				buttons.add(null);
		}
		Solver s;
		do{
			Collections.shuffle(buttons);
			s = new Solver(new Board(getBoard()));
		}
		while(!s.isSolvable());
		
		for(int i=0; i<size; ++i){
			if(buttons.get(i) != null)
				panels.get(i).add(buttons.get(i));
			else
				openPanelIndex = i;
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public int[][] getBoard(){
		int [][] board = new int[edgeLength][edgeLength];
		int iterator = 0;
		for(int i=0; i<edgeLength; ++i)
			for(int j=0; j<edgeLength; ++j){
				JButton current = buttons.get(iterator);
				if(current != null)
					board[i][j]	= Integer.parseInt(current.getText());
				else
					board[i][j] = 0;
				++iterator;
			}
		return board;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		text.setText(null);
		JButton source = (JButton) e.getSource();
		int clickedIndex = buttons.indexOf(source);
		int diff = (clickedIndex - openPanelIndex);
		int column = openPanelIndex%edgeLength;
		if((Math.abs(diff) == edgeLength) || (column == 0 && diff == 1) || (column == 2 && diff == -1) || (column == 1 && Math.abs(diff) == 1)){
			panels.get(openPanelIndex).add(buttons.get(clickedIndex));
			buttons.set(openPanelIndex, source);
			buttons.set(clickedIndex, null);
			openPanelIndex = clickedIndex;
         ++numberOfMoves;
         numOfMovesL.setText("Number Of Moves: "+numberOfMoves);
			revalidate();
			repaint();
			if(new Board(getBoard()).isGoal())
				JOptionPane.showMessageDialog(this, "You Solved the Puzzle!", "Numbers Puzzle", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	public static void main(String[] args){
		GameFrame gf = new GameFrame();
	}
}
