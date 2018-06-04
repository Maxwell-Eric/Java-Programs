import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.util.List;
import java.util.LinkedList;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Toolkit;


public class RocketSim extends JFrame{

   private List<JLabel> labels = new LinkedList<>();
   private List<JTextField> textBoxes = new LinkedList<>();
   
   public RocketSim(){
   
      super("Rocket Information");
      
      JPanel panel = new JPanel();
      panel.setBackground(Color.CYAN);
      panel.setLayout(null);
      
      String[] labelText = {"Average Thrust (N)", "Initial Mass (kg)", "Final Mass (kg)", "Drag Coeffient", "Burn Time (s)", "Air Density (kg/m^3)", "Reference Area (m^2)"};
      
      for(int i=0; i<labelText.length; ++i){
         
      
         JTextField current = new JTextField();
         textBoxes.add(current);
         current.setSize(125, 25);
         current.setLocation(20+(i%4)*150, 50+(i/4)*100);
         panel.add(current);
         
         JLabel cl = new JLabel(labelText[i]);
         labels.add(cl);
         cl.setSize(125, 25);
         cl.setLocation(20+(i%4)*150, 25+(i/4)*100);
         panel.add(cl);
      }
      
      JButton start = new JButton("Start Simulation");
      start.setSize(150,50);
      start.setLocation(400,200);
      panel.add(start);
      start.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent event){
            double[] inputs = new double[textBoxes.size()];
            int i=0;
            for(JTextField tf : textBoxes){
               
               try{
                  inputs[i] = Double.parseDouble(tf.getText());
               }catch(NumberFormatException ex){JOptionPane.showMessageDialog(RocketSim.this, "You must enter numbers", "Error", JOptionPane.ERROR_MESSAGE); return;};
               ++i;
            }
            new RocketPerformance(inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6]);
         }
      });
      
      add(panel);
      setSize(650, 300);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      setLocation((int)(screenSize.getWidth()/2 ),(int)(screenSize.getHeight()/2 - getHeight()/2));
      setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);  
   }

   public static void main(String[] args){
      new RocketSim();
   }
}