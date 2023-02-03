package dodger;

import java.util.ArrayList;
import java.util.List;

import aa.Behavior;
import aa.Boid;
import aa.Eye;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Police extends Boid{
	protected boolean alive;
	private List<Eye> eyes;

	public Police(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt) {
		super(pos, mass, radius, color, p, plt);
		super.dna.maxSpeed = GameSettings.PoliceMaxSpeed;
		super.dna.maxForce = GameSettings.PoliceMaxForce;
		super.dna.visionDistance = 4;
		super.dna.visionSafeDistance = 4;
		eyes = new ArrayList<Eye>();
		alive=true;
	}
	
	@Override
	public void applyBehaviors(float dt) {
		
		PVector vd = new PVector();
		int count = 0;
		for(Behavior behavior : behaviors) {
			if(eyes.get(count) != null) {
				setEye(eyes.get(count));
			}
				eye.look();
			PVector vdd = behavior.getDesiredVel(this);
			vdd.mult(behavior.getWeight()/sumWeights);
			vd.add(vdd);
			count++;
		}
		
		move(dt, vd);
	}
	
	public void setEyes(Eye eye) {
		eyes.add(eye);
	}

	public List<Eye> getEyes() {
		return eyes;
	}
	
	public void checkColision(List<Body> cars) {
		PVector r;
		float d;
		for(Body c : cars) {
			r = PVector.sub(c.getPos(), this.getPos());
			d = r.mag();
			if((d > 0) && (d < this.radius*1.5)){
				System.out.println("CHOQUE");
				alive=false;
				((Police)c).alive=false;
			}
		}
	}

}
