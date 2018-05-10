import java.awt.Color;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

//import javax.swing.JButton;
//import javax.swing.JComboBox;
import javax.swing.JFrame;
//import javax.swing.JLabel;

//import edu.princeton.cs.algs4.StdDraw;

public class BBFrame extends JFrame{
	
	public BBFrame(int side){
		
		setSize(side, side);
		setLayout(null);	
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setBackground(Color.CYAN);
		setVisible(true);
	}

	public static void main(String[] args){
		
		
		BBFrame bbf = new BBFrame(500);
		
	}
}
