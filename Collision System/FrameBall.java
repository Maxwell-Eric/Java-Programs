import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
//import javax.swing.JPanel;

//import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
//import edu.princeton.cs.algs4.*;
public class FrameBall extends JComponent {

	private double sx, sy;
	private double vx, vy;
	private int mass;// = 5;
	public int count = 0;
	private int radius;// = 10;
	private Color color = Color.BLACK;
	
	public FrameBall(int radius){
		this.radius = radius;
		mass = 1;
		initializeParticle();
	}
	
	public FrameBall(int radius, Color c){
		this.radius = radius;
		mass = 1;
		color = c;
		initializeParticle();
	}
	
	public FrameBall(int radius, int mass){
		this.radius = radius;
		this.mass = mass;
		initializeParticle();
	}
	
	public FrameBall(int radius, int mass, Color c){
		this.radius = radius;
		this.mass = mass;
		color = c;
		initializeParticle();
		vx = 0;
		vy = 0;
	}
	
	private void initializeParticle(){
		
		sx = StdRandom.uniform(radius, 250);
		sy = StdRandom.uniform(radius, 250);
		vx = StdRandom.uniform(-5.0,5.0);
		vy = StdRandom.uniform(-5.0, 5.0);
		setSize(2*radius,2*radius);
		setLocation((int) sx - radius, (int) sy - radius);
	}
	
	public void move(double dt){
		sx = sx + vx*dt;
		sy = sy + vy*dt;
		setLocation((int) sx - radius,(int) sy - radius);
	}
	
	public double timeToHit(FrameBall fb){
		if(this == fb)
			return Double.MAX_VALUE;
		
		double dx = fb.sx - this.sx, dy = fb.sy - this.sy;
		double dvx = fb.vx - this.vx, dvy = fb.vy - this.vy;
		double dvds = dx*dvx + dy*dvy; 
		if(dvds > 0)
			return Double.MAX_VALUE;
		
		double dvdv = dvx*dvx + dvy*dvy;
		double dsds = dx*dx + dy*dy;
		double sigma = this.radius + fb.radius;
		double d = (dvds*dvds) - dvdv*(dsds - sigma*sigma);
		
		if(d < 0)
			return Double.MAX_VALUE;
		
		return -(dvds + Math.sqrt(d))/dvdv;	
	}
	
	public double timeToVWall(){
		if(vx > 0)
			return (getParent().getWidth() - sx - radius)/((double)vx);
			
		if(vx < 0)
			return -((sx-radius)/vx );
		
		else 
			return Double.MAX_VALUE;
	}
	
	public double timeToHWall(){
		if(vy > 0)
			return (getParent().getHeight() - sy - radius)/((double)vy);
			
		if(vy < 0)
			return -((sy-radius)/vy);
		
		else 
			return Double.MAX_VALUE;
	}
	
	public void bop(FrameBall fb){
		double dx = fb.sx - this.sx, dy = fb.sy - this.sy;
		double dvx = fb.vx - this.vx, dvy = fb.vy - this.vy;
		double dvds = dx*dvx + dy*dvy; 
		double dist = fb.radius + this.radius;
		double J = 2*this.mass*fb.mass*dvds/((this.mass + fb.mass)*dist);
		
		double Jx = J*dx/dist, Jy = J*dy/dist;
		//this.vx += (Jx/this.mass)*.5; this.vy += (Jy/this.mass)*.5;
		this.vx += Jx/this.mass; this.vy += Jy/this.mass;
		fb.vx -= (Jx/fb.mass); fb.vy -= (Jy/fb.mass);
		this.count++;
		fb.count++;
	}
	
	public void bovw(){
		
		vx = -vx;
		++count;
	}
	
	public void bohw(){
		vy = -vy;
		++count;
	}
	
	public void paintComponent(Graphics gx)
	   {
			gx.setColor(color);
	      //Call the super method
	      super.paintComponent(gx);
	      
	      //Create 2D graphics 
	      Graphics2D gx2D = (Graphics2D) gx;
	      
	      gx2D.fillOval(0, 0, 2*radius, 2*radius);
	   }
}
