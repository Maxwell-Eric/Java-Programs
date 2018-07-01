import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class KDTreeGUI extends JFrame{

   private JPanel panel;
   private JComboBox args;
   private JLabel argsLabel;
   private JButton startInputB;
   private JButton startInteractiveB;
   
   
   public KDTreeGUI(){
   
      super("KDTree");
      setSize(600,400);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      
   
      panel = new JPanel();
      add(panel);
      
      argsLabel = new JLabel("Choose an input file");
      getArgs();
      
      startInputB = new JButton("Start kdTree with input file");
      startInputB.addActionListener(new ActionListener(){
      
         @Override
         public void actionPerformed(ActionEvent event){
            
            String[] selection = new String[] {args.getSelectedItem().toString()};
            
            Runnable startInput = new Runnable(){
               @Override
               public void run(){
                  KdTree.main(selection);
               }
            };
            Thread si = new Thread(startInput);
            si.start();
         }
      });
      
      startInteractiveB = new JButton("Start Interactive Window");
      startInteractiveB.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent event){
            Runnable startInteractive = new Runnable(){
               @Override
               public void run(){
                  KdTreeVisualizer.main(new String[] {});
               }
            };
            Thread interactive = new Thread(startInteractive);
            interactive.start();
         }
      });
      
      panel.add(argsLabel);
      panel.add(args);
      panel.add(startInputB);
      panel.add(startInteractiveB);
     
      
      setVisible(true);
   }
   
   private void getArgs(){
   
      String path = "kdtree-testing/kdtree/";
   
      File folder = new File(path);
      File[] listOfFiles = folder.listFiles(new FilenameFilter() {
         public boolean accept(File directory, String fileName) {
            return fileName.endsWith(".txt");
         }
      });
      
      Vector<String> fileNames = new Vector<>();
      for (File file : listOfFiles) {
         if (file.isFile()) {
            fileNames.add(path+file.getName());
         }
      }
      args = new JComboBox<>(fileNames);
   }

   public static void main(String[] args){
      new KDTreeGUI();
   }
}