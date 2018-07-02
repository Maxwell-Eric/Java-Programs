import edu.princeton.cs.algs4.MinPQ;

public class CollisionSystem{
	
	private MinPQ<Event> pq;
	private double t = 0.0;
	private FrameBall[] particles;

	public CollisionSystem(FrameBall[] particles){
		this.particles = particles;
	}
	
	
	private void predict(FrameBall a){
		if(a==null) return;
		for(int i=0; i<particles.length; ++i){
			double dt = a.timeToHit(particles[i]);
			pq.insert(new Event(t+dt, a, particles[i]));
		}
		pq.insert(new Event(t+a.timeToVWall(), a, null));
		pq.insert(new Event(t+a.timeToHWall(), null, a));
	}
	
	public void redraw(){ 
		
		particles[0].getParent().repaint();
		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pq.insert(new Event(t + 1, null, null));
	}
	
	public void simulate(){
		pq = new MinPQ<Event>();
		for(FrameBall particle : particles)
			predict(particle);
		pq.insert(new Event(0, null, null));
		
		while(!pq.isEmpty()){
			Event event = pq.delMin();
			if(!event.isValid()) continue;
			FrameBall a = event.a;
			FrameBall b = event.b;
			
			for(int i=0; i<particles.length; ++i){
				particles[i].move(event.time - t);
			}
			t = event.time;
			if(a != null && b != null) a.bop(b);
			else if(a != null && b == null) a.bovw();
			else if(a == null && b != null) b.bohw();
			else if(a == null && b == null) redraw();
			predict(a); predict(b);	
		}
	}
	
	
	private class Event implements Comparable<Event>{
		
		private double time;
		private FrameBall a, b;
		private int countA, countB;
		
		public Event(double t, FrameBall a, FrameBall b){
			time = t;
			this.a = a; this.b = b;
			if(a != null)countA = a.count; else countA = -1; 
			if(b != null) countB = b.count; else countB = -1;
		}
		
		public int compareTo(Event e){
			if(this.time - e.time > 0)
				return 1;
			else
				return -1;
		}
		
		public boolean isValid(){
			if(a != null && countA != a.count)
				return false;
			
			if(b != null && countB != b.count)
				return false;
			
			return true;
		}
	}
}
