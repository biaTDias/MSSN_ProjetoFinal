package aa;

import processing.core.PVector;

public class Brake extends Behavior{

	public Brake(float weight) {
		super(weight);
	}

	@Override
	public PVector getDesiredVel(Boid me) {
		return new PVector();
	}

}
