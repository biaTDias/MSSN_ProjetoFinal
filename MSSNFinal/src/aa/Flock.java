package aa;

import java.util.ArrayList;
import java.util.List;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Flock {
	public List<Boid> boids;
	
	public Flock(int nBoids, float mass, float radius, int color, float[] sacWeights, PApplet p, SubPlot plt) {
		boids = new ArrayList<Boid>();
		double[] w = plt.getWindow();
		for(int i = 0; i<nBoids; i++) {
			
			float x = p.random((float)w[0],(float)w[1]);
			float y = p.random((float)w[2],(float)w[3]);
			Boid b = new Boid(new PVector(x, y), mass, radius, color, p, plt);
			b.addBehavior(new Separate(sacWeights[0]));
			b.addBehavior(new Align(sacWeights[1]));
			b.addBehavior(new Cohesion(sacWeights[2]));
			
			boids.add(b);
		}
		
		List<Body> bodies = boidList2BodyList(boids);
		for (Boid b : boids) {
			b.setEye(new Eye(b, bodies));
			
		}
	}
	
	private List<Body> boidList2BodyList(List<Boid> boids){
		List<Body> bodies = new ArrayList<Body>();
		for(Boid b : boids) {
			bodies.add(b);
		}
		return bodies;
	}
	
	public void applyBehavior(float dt) {
		for(Boid b : boids) {
			b.applyBehaviors(dt);
		}
	}
	
	public Boid getBoid(int i){
		return boids.get(i);
	}
	
	public void display(PApplet p, SubPlot plt) {
		for(Boid b : boids) {
			b.display(p, plt);
		}
	}

	public List<Boid> getBoids() {
		return boids;
	}

	public void setBoids(List<Boid> boids) {
		this.boids = boids;
	}
	
	public void addBoid(Boid b) {
		boids.add(b);
	}
	
	public void removeBoid(int i) {
		boids.remove(i);
	}

}
