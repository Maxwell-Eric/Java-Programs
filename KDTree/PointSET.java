import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
	
	private SET<Point2D> points;

	
	public PointSET(){
		points = new SET<>();
	}

	public boolean isEmpty(){
		return points.isEmpty();
	}
	
	public int size(){
		return points.size();
	}
	
	public void insert(Point2D p){
      if(p == null)
         throw new IllegalArgumentException();
   
     
      
      if(points.contains(p))
         return;
         
		points.add(p);
	}
	
	public boolean contains(Point2D p){
      if(p == null)
         throw new IllegalArgumentException();
		return points.contains(p);
	}
	
	public void draw(){
		for(Point2D p : points)
			p.draw();
	}
	
	public Iterable<Point2D> range(RectHV rect){
		if(rect == null)
         throw new IllegalArgumentException();
         
		SET<Point2D> inside = new SET<>();
		
		for(Point2D p : points){
         double x = p.x();
         double y = p.y();
			if (x >= rect.xmin() && x <= rect.xmax() && y >= rect.ymin() && y <= rect.ymax())
				inside.add(p);
      }
		return inside;
	}
	
	public Point2D nearest(Point2D p){
      if(p == null)
         throw new IllegalArgumentException();
         
       if(size() == 0)
         return null;
       
         
		double smallDist = 2;
		Point2D closest = null;
		for(Point2D p1 : points){
			if(p != p1){
				double distance = p.distanceSquaredTo(p1);
				if(distance < smallDist){
					smallDist = distance;
					closest = p1;
				}
			}
			
		}
		return closest;	
	}
	
	public static void main(String[] args){
		PointSET ps = new PointSET();
		
		display(ps);
		
		ps.insert(new Point2D(.3,.5));
		
		display(ps);
		
		ps.draw();
	}
	
	private static void display(PointSET ps){
		System.out.println(ps.isEmpty()+", "+ps.size());
	}
	
}