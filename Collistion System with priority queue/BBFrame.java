import java.awt.Color;
import javax.swing.JFrame;

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
