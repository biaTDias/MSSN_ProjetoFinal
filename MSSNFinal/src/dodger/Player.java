package dodger;

import java.util.ArrayList;

import aa.Boid;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Player extends Boid{
	protected boolean alive;

	public Player(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt) {
		super(pos, mass, radius, color, p, plt);
		super.dna.maxSpeed = GameSettings.PlayerMaxSpeed;
		super.dna.maxForce = GameSettings.PlayerMaxForce;
		alive = true;
		
	}
	
	public boolean choque(ArrayList<Body> cars) {
		PVector r;
		float d;
		for(Body c : cars) {
			r = PVector.sub(c.getPos(), this.getPos());
			d = r.mag();
			if((d > 0) && (d < this.radius*1.5)){
				System.out.println("Game OVER");
				alive=false;
				return true;
			}
		}
		return false;
	}
	


}
