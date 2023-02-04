package dodger;

import java.util.ArrayList;

import aa.Boid;
import physics.Body;
import processing.core.PApplet;
import processing.core.PConstants;
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
			if((d > 0) && (d < this.radius*2)){
				System.out.println("Game OVER");
				alive=false;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void setShape(PApplet p, SubPlot plt) {
		float[] rr = plt.getDimInPixel(radius, radius);
		
		p.pushStyle();
		
		shape = p.createShape();
		shape.beginShape();
		shape.noStroke();
		shape.fill(color);
		shape.endShape(PConstants.CLOSE);
		shape.vertex(-rr[0], rr[0]/2);
		shape.vertex(rr[0], 0);
		shape.vertex(-rr[0], -rr[0]/2);
		
		p.popStyle();
	}
	


}
