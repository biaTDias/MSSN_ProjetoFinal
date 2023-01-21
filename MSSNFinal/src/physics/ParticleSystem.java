package physics;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class ParticleSystem extends Body{
	
	private List<Particle> particles;
	private PSControl psc;
	

	public ParticleSystem(PVector pos, PVector vel, float mass, float radius, PSControl psc) {
		super(pos, vel, mass, radius, 0);
		this.particles = new ArrayList<Particle>();
		this.psc = psc;
	}
	
	public PSControl getPSControl() {
		return psc;
	}
	
	@Override
	public void display(PApplet p, SubPlot plt) {
		for(Particle particle : particles) {
			particle.display(p, plt);
		}
	}
	
	@Override
	public void move(float dt) {
		super.move(dt);
		
		addParticles(dt);
		
		for(int i =particles.size()-1; i>=0;i--) {
			Particle p = particles.get(i);
			p.move(dt);
			if(p.isDead()) {
				particles.remove(i);
			}
		}
	}
	
	private void addParticles(float dt) {
		float particlesPerFrame = psc.getFlow() * dt;
		
		int n = (int) particlesPerFrame;
		float f = particlesPerFrame -n;
		for(int i=0; i<n; i++) {
			addOneParticle();
		}
		if(Math.random() < f) {
			addOneParticle();
		}
	}

	private void addOneParticle() {

		Particle particle = new Particle(pos, psc.getRndVel(), psc.getRndRadius(), psc.getColor(), psc.getRndLifetime());
		
		particles.add(particle);
	}

}
