import edu.princeton.cs.algs4.*;

public class PercolationStats {
	
	private double[] results;
	private int trials;
	
	public PercolationStats(int n, int trials){
		if(n < 1 || trials < 1)
			throw new IllegalArgumentException();
		this.trials = trials;
		results = new double[trials];
		
		for(int i=0; i<trials; ++i){
			Percolation perc = new Percolation(n);
			while(!perc.percolates()){
				perc.open(StdRandom.uniform(1, n+1), StdRandom.uniform(1, n+1));
			}
			results[i] = ((double)perc.numberOfOpenSites()/(n*n));
		}
	}
	
	public double mean(){
		return StdStats.mean(results);
	}
	
	public double stddev(){
		return StdStats.stddev(results);
	}
	
	public double confidenceLo(){
		return mean() - 1.96*stddev()/Math.sqrt(trials);
	}
	
	public double confidenceHi(){
		return mean() + 1.96*stddev()/Math.sqrt(trials);
	}
	
	public static void main(String[] args){
		PercolationStats ps = new PercolationStats(250,500);
		System.out.println("Mean: "+ps.mean()+"; Standard Deviation: "+ps.stddev()+"\n\nConfidence Interval: "+ps.confidenceLo()+" to "+ps.confidenceHi());
	}
}
