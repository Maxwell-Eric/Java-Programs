import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class KdTree {

	private Node root;
   	private int size;
   	private Point2D closest;
	private double dist;
   
	public KdTree(){
		
	}
	private class Node {
      
         	Point2D point;
         	RectHV rect;
         	Node left;
         	Node right;
         	Node parent;
         
         	private Node(Point2D p){
            		point = p;
         	} 
         
    		private void mdt(int level, Node current){
         
            		if(level%2 == 0){
               			if(this.point.y() < current.point.y()){
                  			if(current.left == null){
                     				current.left = this;
                     				this.parent = current;
                     				RectHV pr = current.rect;
                     				this.rect = new RectHV(pr.xmin(), pr.ymin(), pr.xmax(), current.point.y());    
                  			}
                  
                  			else 
                    				mdt(level+1, current.left);
               			}
               
               			else{
                  			if(current.right == null){
                     			current.right = this;
                     			this.parent = current;
                     			RectHV pr = current.rect;
                     			this.rect = new RectHV(pr.xmin(), current.point.y(), pr.xmax(), pr.ymax());
                  			}
                  
                  			else
                     				mdt(level+1, current.right);   
               			}   
            		}
            
            		else{
               			if(this.point.x() < current.point.x()){
                 			if(current.left == null){
                     				current.left = this;
                     				this.parent = current;
                     				RectHV pr = parent.rect;
                     				this.rect = new RectHV(pr.xmin(), pr.ymin(), current.point.x(), pr.ymax());          
                  			}
                     
                  			else
                     				mdt(level+1, current.left);
               			}
               
               			else{
                  			if(current.right == null){
                     				current.right = this;
                     				this.parent = current;
                     				RectHV pr = parent.rect;
                     				this.rect = new RectHV(current.point.x(), pr.ymin(), pr.xmax(), pr.ymax()); 
                  			}
                  
                  			else
                     				mdt(level+1, current.right);
               			}
            		}
         	}
      	}
    
	public boolean isEmpty(){
		return size == 0;
	}
		
	public int size(){
		return size;
	}
		
	public void insert(Point2D p){
         	if (p == null)
            		throw new IllegalArgumentException();
            
         	if(this.contains(p))
            		return;
            
		if(size == 0){
            		root = new Node(p);
            		root.rect = new RectHV(0,0,1,1);
            		++size;
         	}
         
         	else{
            		Node newNode = new Node(p);
            		newNode.mdt(1, root);
            		++size;
         	}
	}
     
	public boolean contains(Point2D p){
      		if (p == null)
		throw new IllegalArgumentException();
         
      		if (size == 0)
         		return false;
         
	   	return find(p, root, 0);
	}
      
      	private boolean find(Point2D p, Node n, int h){
         	if(n.point.equals(p))
            		return true;
            
         	if(h%2 == 0){
          
            		if(p.x() < n.point.x()){
               			if(n.left == null)
                  			return false;
               			else
                  			return find(p, n.left, h+1);
            		}
            
            		else{
               			if(n.right == null)
                  			return false; 
               			else
                  			return find(p, n.right, h+1);
            		}     
         	}
         
         	else{
            		if(p.y() < n.point.y()){
               			if(n.left == null)
                  			return false;
                 
               			else
                 	 		return find(p, n.left, h+1);
            		}
            
            		else{
               			if(n.right == null)
                  			return false; 
                  
              			else
                  			return find(p, n.right, h+1);
            		} 
         	}
      	}
		
	public void draw(){
         
         	if(size == 0) return;
         		drawNodes(root, 0);
	}
      
      private void drawNodes(Node current, int height){
            
            StdDraw.setPenRadius(0.003);
            if (height%2 == 0){
               StdDraw.setPenColor(StdDraw.BLUE);
               RectHV r = current.rect;
               StdDraw.line(current.point.x(), r.ymin(), current.point.x(), r.ymax());
            }
            
            else{
               StdDraw.setPenColor(StdDraw.RED);
               RectHV r = current.rect;
               StdDraw.line(r.xmin(), current.point.y(), r.xmax(), current.point.y());
               }
               
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            current.point.draw();
            
            if(current.left != null)
               drawNodes(current.left, height+1);
               
            if(current.right != null)
               drawNodes(current.right, height+1);
      }
      
		
	public Iterable<Point2D> range(RectHV rect){
         	if (rect == null)
            		throw new IllegalArgumentException();
          
         	Stack<Point2D> stack = new Stack<>();
         
         	if (size == 0)
            		return stack;
         
		getRange(rect, root, stack);
		return stack;
	}
      
      private void getRange(RectHV search, Node current, Stack<Point2D> s){
         RectHV r = current.rect;
         if(search.xmin()<= r.xmax() && search.xmax() >= r.xmin() && search.ymin() <= r.ymax() && search.ymax() >= r.ymin()){
            double x = current.point.x();
            double y = current.point.y();
            
            if(x >= search.xmin() && x <= search.xmax() && y >= search.ymin() && y <= search.ymax())
               s.push(current.point);
               
            if(current.left != null)
               getRange(search, current.left, s);
            
            if(current.right != null)
               getRange(search, current.right, s);
          }
      }
   
		
	public Point2D nearest(Point2D p){
        	if (p == null)
            		throw new IllegalArgumentException();
            
         	if (size == 0)
            		return null;
 
         	closest = null;
         	getNearest(p, root);
		return closest;	
	}
      
      private void getNearest(Point2D p, Node current){
    
         if(current == root){
            closest = root.point;
            dist = p.distanceSquaredTo(current.point);
         }
         else{
            double d = p.distanceSquaredTo(current.point);
            
            if(d < dist){
               closest = current.point;
               dist = d;
            }
         }
         if(current.left != null && current.left.rect.distanceSquaredTo(p) < dist)
            getNearest(p, current.left);//, closest);
         
         
         if(current.right !=null && current.right.rect.distanceSquaredTo(p) < dist)
            getNearest(p, current.right);//, closest);
      }
		
	public static void main(String[] args){
			
    		String filename = args[0];
         	In in = new In(filename);

         	StdDraw.enableDoubleBuffering();

        	// initialize the data structures with N points from standard input
        	KdTree kdtree = new KdTree();
        	while (!in.isEmpty()) {
            		double x = in.readDouble();
            		double y = in.readDouble();
            		Point2D p = new Point2D(x, y);
            		kdtree.insert(p);
        	}
		kdtree.draw();
         	StdDraw.show();
	}
}
