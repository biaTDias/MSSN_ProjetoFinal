package aa;


public class DNABoid {
	public float maxSpeed;
	public float maxForce;
	public float visionDistance;
	public float visionSafeDistance;
	public float visionAngle;
	public float deltaTPursuit;
	public float radiusArrive;
	public float deltaTWander;
	public float radiusWander;
	public float deltaPhiWander;
	
	public DNABoid() {
		
		//Speed
		maxSpeed = random(3,5);
		maxForce = random(4,7);
		
		//Vision
		visionDistance = random(2, 4);
		visionSafeDistance = 0.6f * visionDistance;
		visionAngle = (float) Math.PI;
		
		//Behaviors
		deltaTPursuit = random(0.5f, 1f);
		
		radiusArrive = random(3, 5);
		
		deltaTWander = random(1f, 1f);
		radiusWander = random(3f, 3f);
		deltaPhiWander = (float)(Math.PI/8);
		
	}

	public static float random(float min, float max) {
		return (float) (min + (max-min)*Math.random());
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}


}
