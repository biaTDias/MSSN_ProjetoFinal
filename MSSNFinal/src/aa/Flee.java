package aa;

import processing.core.PVector;

public class Flee extends Behavior{

	public Flee(float weight) {
		super(weight);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public PVector getDesiredVel(Boid me) {
		
		PVector vd = PVector.sub(me.eye.target.getPos(), me.getPos());
		return vd.mult(-1);
	}
	
}
