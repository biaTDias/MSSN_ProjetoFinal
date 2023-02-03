package aa;

import processing.core.PVector;

public class Seek extends Behavior{

	public Seek(float weight) {
		super(weight);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public PVector getDesiredVel(Boid me) {
		
		return PVector.sub(me.eye.target.getPos(), me.getPos());
	}
	
}
