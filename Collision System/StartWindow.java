import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StartWindow extends JFrame implements ActionListener {
	
	JComboBox<Integer> numParts;
	JLabel numPartsLabel;
	JComboBox<Integer> partRadius;
	JLabel partRadiusLabel;
	JComboBox<Integer> sz;
	JLabel szLabel;
	JButton startB;
	Runnable doCollisions;
	
	boolean buttonClicked = false;
	int numberOfParts;
	int radius;
	int side;
	
	public StartWindow(){
		super("Collision System");
	
		Integer[] array = {10, 20, 50, 75, 100};
		numParts = new JComboBox<>(array);
		numParts.setSize(75, 25);
		numParts.setLocation(250, 25);
		add(numParts);
		
		numPartsLabel = new JLabel("Select Number of Particles: ");
		numPartsLabel.setSize(200, 25);
		numPartsLabel.setLocation(50, 25);
		add(numPartsLabel);
		
		
		Integer[] array2 = {1, 2, 5, 10, 15};
		partRadius = new JComboBox<>(array2);
		partRadius.setSize(75, 25);
		partRadius.setLocation(250, 100);
		add(partRadius);
		
		partRadiusLabel = new JLabel("Select Particle Radius: ");
		partRadiusLabel.setSize(200, 25);
		partRadiusLabel.setLocation(50, 100);
		add(partRadiusLabel);
		
		Integer[] array3 = {300, 400, 600, 800};
		sz = new JComboBox<>(array3);
		sz.setSize(75, 25);
		sz.setLocation(250, 175);
		add(sz);
		
		szLabel = new JLabel("Select Window Size: ");
		szLabel.setSize(200, 25);
		szLabel.setLocation(50, 175);
		add(szLabel);
		
		startB = new JButton("Start");
		startB.setSize(150, 50);
		startB.setLocation(400, 50);
		startB.addActionListener(this);
		add(startB);
		
		
		setSize(600,300);
		getContentPane().setBackground(new Color(240,120,240));
		setLayout(null);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		numberOfParts = (int) numParts.getSelectedItem();
		radius = (int) partRadius.getSelectedItem();
		side = (int) sz.getSelectedItem();
		buttonClicked = true;
		
		doCollisions = new Runnable(){
			public void run(){
				BBFrame bbf = new BBFrame(side);
				FrameBall[] particles = new FrameBall[numberOfParts];
			
				for(int i=0; i<numberOfParts -1; ++i){
					particles[i] =  new FrameBall(radius);
					bbf.add(particles[i]);
				}
				particles[numberOfParts-1] = new FrameBall(20, 100, Color.RED);
				bbf.add(particles[numberOfParts-1]);
				CollisionSystem col = new CollisionSystem(particles);
				col.simulate();
			}
		};
		Thread thread = new Thread(doCollisions);
		thread.start();
	}
	
	public static void main(String[] args){
		
		new StartWindow();
	}
}
