package physics;


import processing.core.PVector;

public abstract class Mover {
	protected PVector pos;
	protected PVector vel;
	protected PVector acc;
	protected float mass;
	
	
	private static double G = 6.67e-11;
	
	protected Mover(PVector pos, PVector vel, float mass) {
		this.pos = pos.copy();
		this.vel = vel;
		this.mass = mass;
		acc = new PVector();		
	}
	
	public void applyForce(PVector force) {
		acc.add(PVector.div(force, mass));
	}
	
	public void move(float dt) {
		vel.add(acc.mult(dt));
		pos.add(PVector.mult(vel, dt));
		acc.mult(0);
	}
	
	public PVector attraction(Mover m) {
		PVector r = PVector.sub(pos, m.pos);
		float dist = r.mag();
		float strength =(float)  ((G * mass * m.mass) / Math.pow(dist, 2));
		
		return r.normalize().mult(strength);
		
	}

	public PVector getPos() {
		return pos;
	}

	public void setPos(PVector pos) {
		this.pos = pos;
	}

	public PVector getVel() {
		return vel;
	}

	public void setVel(PVector vel) {
		this.vel = vel;
	}

	public PVector getAcc() {
		return acc;
	}

	public void setAcc(PVector acc) {
		this.acc = acc;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	
	
	
}
