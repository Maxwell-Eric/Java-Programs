import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;
import java.util.LinkedList;
import java.util.List;

public class RocketGraph extends JFrame{

   private Graph graph;
   private List<Double> h;
   private List<Integer> t;
   private List<Double> v;
   private List<Double> a;
   private double burnTime;
   private double totalTime;
   private double maxVelocity;
   private double maxHeight;
   
   
   
   public RocketGraph(List<Double> acc, List<Double> velocity, List<Double> height, List<Integer> time, double bt, double tt, double mv, double mh){
      
      super("Model Rocket Performance");
      
      h = height;
      t = time;
      v = velocity;
      a = acc;
      burnTime = bt;
      totalTime = tt;
      maxVelocity = mv;
      maxHeight = mh;
      
      graph = new Graph();
      add(graph);
      setSize(1300, 700);
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
   }
   
   
   private class Graph extends JPanel{
   
      private JLabel[] labels = new JLabel[4];
      private JLabel[] vertAxisNums = new JLabel[6];
      private JLabel[] horiAxisNums = new JLabel[11];
      private JLabel maxVelocityL;
      private JLabel maxHeightL;
      private JLabel timeAxisL;
      int zeroLineY = 500;
      int zeroLineX = 75;
      private Color dg = new Color(0,150,0);
      private Font font = new Font(Font.SERIF, Font.BOLD, 22);
      

      public Graph(){
         setLayout(null);
         String[] slabels = new String[] {"Height (m)", "Velocity (m/s)", "Acceleration (m/s^2)", "Burn Out (s)"};
         labels = new JLabel[slabels.length];
         for(int i=0; i<slabels.length; ++i){
            labels[i] = new JLabel(slabels[i]);
            JLabel current = labels[i];
            current.setFont(font);
            current.setSize(200,25);
            current.setLocation(1075, 35+50*i);
            add(current);
         }
         
         for(int i=0; i<6; ++i){
            
            vertAxisNums[i] = new JLabel(((i-1)*100)+"");
            JLabel current = vertAxisNums[i];
            current.setFont(font);
            current.setSize(50,25);
            current.setLocation(10,590-i*100);
            add(current);
         
         }
         
         for(int i=1; i<11; ++i){
         
            horiAxisNums[i] = new JLabel(i+"");
            JLabel current = horiAxisNums[i];
            current.setFont(font);
            current.setSize(25,25);
            current.setLocation(zeroLineX+i*100-10, zeroLineY + 50);
            add(current); 
         }
         
         
         maxVelocityL = new JLabel("Max Velocity: "+maxVelocity+"m/s");
         maxVelocityL.setFont(font);
         maxVelocityL.setForeground(Color.BLUE);
         maxVelocityL.setSize(250, 25);
         maxVelocityL.setLocation(400, 25);
         add(maxVelocityL);
         
         
         maxHeightL = new JLabel("Max Height: "+maxHeight+"m");
         maxHeightL.setFont(font);
         maxHeightL.setForeground(dg);
         maxHeightL.setSize(250, 25);
         maxHeightL.setLocation(650, 25);
         add(maxHeightL);
         
         timeAxisL = new JLabel("Time (s)");
         timeAxisL.setFont(font);
         timeAxisL.setSize(200,25);
         timeAxisL.setLocation(500, 600);
         add(timeAxisL);

      }
   
      
      protected void paintComponent(Graphics g){
         
         super.paintComponent(g);
         
         Graphics2D g2 = (Graphics2D) g;
         g2.setStroke(new BasicStroke(2f));
         
         
         g2.setColor(dg);
         g2.drawLine(1000, 50, 1050, 50);
         g2.setColor(Color.BLUE);
         g2.drawLine(1000, 100, 1050, 100);
         g2.setColor(Color.RED);
         g2.drawLine(1000, 150, 1050, 150);
         g2.setColor(Color.ORANGE);
         g2.drawLine(1000, 200, 1050, 200);
         g2.drawLine((int)(burnTime*100)+zeroLineX, 600, (int)(burnTime*100)+zeroLineX, 50);
         
         int length = t.size();
         for(int i=0; i<length-1; ++i){
            
            g2.setColor(dg);
            g2.drawLine(t.get(i)+zeroLineX,(int) (zeroLineY - h.get(i)), t.get(i+1)+zeroLineX, (int)(zeroLineY-h.get(i+1)));
            g2.setColor(Color.BLUE);
            g2.drawLine(t.get(i)+zeroLineX,(int) (zeroLineY - v.get(i)), t.get(i+1)+zeroLineX, (int)(zeroLineY-v.get(i+1)));
            g2.setColor(Color.RED);
            g2.drawLine(t.get(i)+zeroLineX,(int) (zeroLineY - a.get(i)), t.get(i+1)+zeroLineX, (int)(zeroLineY-a.get(i+1)));
         }
         
         g2.setColor(Color.BLACK);
         g2.drawLine(zeroLineX, zeroLineY + 100, zeroLineX, 50);
         g2.drawLine(zeroLineX, zeroLineY, zeroLineX+1000, zeroLineY);
         for(int i=0; i<=1000; i+=10){
            if(i%100 == 0)
               g2.drawLine(zeroLineX+i, zeroLineY+10, zeroLineX+i, zeroLineY-10);
            else
               g2.drawLine(zeroLineX+i, zeroLineY+5, zeroLineX+i, zeroLineY-5);
            
            if(i<560)
               if(i%100 ==0)
                  g2.drawLine(zeroLineX-10, zeroLineY+100-i, zeroLineX+10, zeroLineY+100-i);
               else
                  g2.drawLine(zeroLineX-5, zeroLineY+100-i, zeroLineX+5, zeroLineY+100-i);
         }
      } 
   }
}