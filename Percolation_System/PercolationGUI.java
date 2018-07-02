import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class PercolationGUI extends JFrame{

   private JPanel panel = new JPanel();
   private JComboBox<String> choices;
   private JButton startPercolation;
   private JLabel selectFile;

   public PercolationGUI(){
      
      super("Percolation Visualization");
   
      setSize(600,400);
      add(panel);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      
      selectFile = new JLabel("Select a file: ");
      panel.add(selectFile);
      
      getInputFiles();
      panel.add(choices);
      
      startPercolation = new JButton("Start Percolation");
      panel.add(startPercolation);
      setVisible(true);
      
      startPercolation.addActionListener(new ActionListener(){
        
         @Override
         public void actionPerformed(ActionEvent event){
            String[] selection = new String[] {choices.getSelectedItem().toString()};
            
            Runnable runPerc = new Runnable(){
               @Override
               public void run(){
                  PercolationVisualizer.main(selection);
               }
            };
            
            Thread perc = new Thread(runPerc);
            perc.start();
         }
      });
   }
   
   private void getInputFiles(){
   
      String path = "percolation-testing/percolation/";
   
      File folder = new File(path);
      File[] listOfFiles = folder.listFiles(new FilenameFilter() {
         public boolean accept(File directory, String fileName) {
            return fileName.endsWith(".txt");
         }
      });
      
      Vector<String> choiceNames = new Vector<>();
      for (File file : listOfFiles) {
         if (file.isFile()) {
            choiceNames.add(path+file.getName());
         }
      }
      choices = new JComboBox<>(choiceNames);
   }
   
   public static void main(String[] args){
      PercolationGUI p = new PercolationGUI();
   }
}