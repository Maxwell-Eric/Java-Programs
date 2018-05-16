import edu.princeton.cs.algs4.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




/**********************************************************
 * Percolation Stats class
 *    -This class determines the percentage sites must be opened before
 *       a system percolates.
 *    -The process is repeated several times and produces the statistics
 *       of the experiment.
 *
 *********************************************************/

public class PercolationStats extends JFrame {
	
	private double[] results;
	private int trials;
   private int sites;
   private int n;
   private JPanel panel = new JPanel();
	
	public PercolationStats(){
   
      super("Percolation Statistics");
      
      panel.setLayout(null);
      
      JLabel sideLength = new JLabel("Enter the number of sites on each side of the system:");
      sideLength.setSize(350,25);
      sideLength.setLocation(20,20);
      panel.add(sideLength);
      
      JTextField sl = new JTextField();
      sl.setSize(100, 25);
      sl.setLocation(325, 20);
      panel.add(sl);
      
      
      JLabel numTrials = new JLabel("Enter the number of trials:");
      numTrials.setSize(350,25);
      numTrials.setLocation(20,100);
      panel.add(numTrials);
      
      JTextField nt = new JTextField();
      nt.setSize(100, 25);
      nt.setLocation(175, 100);
      panel.add(nt);
      
      JButton start = new JButton("Start");
      start.setSize(75,50);
      start.setLocation(500, 300);
      panel.add(start);
      
      start.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent event){
            
            try{
               n = Integer.parseInt(sl.getText());
               trials = Integer.parseInt(nt.getText());
            }catch(NumberFormatException ex){System.out.println("You must enter integers!");return;}
         
         
            if(n < 1 || trials < 1)
			      throw new IllegalArgumentException();
            sites = n*n;
		      results = new double[trials];
		
		      for(int i=0; i<trials; ++i){
			      Percolation perc = new Percolation(n);
			      while(!perc.percolates()){
				      perc.open(StdRandom.uniform(1, n+1), StdRandom.uniform(1, n+1));
			      }
			      results[i] = ((double)perc.numberOfOpenSites()/(n*n));
		      }
            
            
            JLabel meanL = new JLabel("<html>For a system with "+sites+" and "+trials+" trials:<br>Mean % of sites needed to be opened for percolation: "+mean()+"<br>Standard Deviation: "+stddev()+"<br>Confidence Interval: "+confidenceLo()+" to "+confidenceHi());
            meanL.setSize(500, 75);
            meanL.setLocation(20, 225);
            panel.add(meanL);
            
            repaint();
            System.out.println("For a system with "+sites+" sites and "+trials+" tests:\n");
		      System.out.println("Mean percentage of sites needed to be opened for percolation: "+mean()+
                         "\nStandard Deviation: "+stddev()+"\nConfidence Interval: "+confidenceLo()+" to "+confidenceHi());
         }
      });
      
      
      add(panel);
      setSize(600,400);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setVisible(true);
		
	}

	public double mean(){
		return formatDouble(StdStats.mean(results));
	}
	
	public double stddev(){
		return formatDouble(StdStats.stddev(results));
	}
	
	public double confidenceLo(){
		return formatDouble(mean() - 1.96*stddev()/Math.sqrt(trials));
	}
	
	public double confidenceHi(){
		return formatDouble(mean() + 1.96*stddev()/Math.sqrt(trials));
	}
   
   private double formatDouble(double number){
      return Double.parseDouble(String.format("%.6f", number));
   }
	
	public static void main(String[] args){
		PercolationStats ps = new PercolationStats();
      
	}
}
