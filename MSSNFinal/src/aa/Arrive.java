package aa;

import processing.core.PVector;

public class Arrive extends Behavior{

	public Arrive(float weight) {
		super(weight);
	}

	@Override
	public PVector getDesiredVel(Boid me) {
		PVector vd = PVector.sub(me.eye.target.getPos(), me.getPos());
		float dist = vd.mag();
		float r = me.dna.radiusArrive;
		if(dist<r) {
			vd.mult(dist/r);
		}
		
		return vd;
	}

}
