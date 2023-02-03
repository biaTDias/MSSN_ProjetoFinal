//All backend game tasks are handled in this class

package dodger;

import java.util.ArrayList;
import java.util.List;

import aa.*;
import physics.*;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class GameHandler {
	
	private int score = 0;
	// se calhar mudar para private e fazer um getter? more game state protection
	protected State state = State.MENU;

	protected Player player;
	private Body target;
	private List<Body> allTrackingBodies;
	private int index = 0;
	// cars (Uma lista de police)

	protected void setup(PApplet p, SubPlot plt) {
		// player
		double[] ww = plt.getWorldCoord(p.width/2, p.height/2);
		player = new Player(new PVector((float) ww[0], (float) ww[1]), 1, 0.5f, p.color(252, 186, 3), p, plt);
		player.addBehavior(new Seek(1f));
		target = new Body(new PVector(), new PVector(), 1f, 3f, p.color(255, 0, 0));
		allTrackingBodies = new ArrayList<Body>();
		allTrackingBodies.add(target);
		Eye eye = new Eye(player, allTrackingBodies);
		player.setEye(eye);

	}

	protected void calculateChanges(PApplet p, float dt, SubPlot plt) {
		double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);

		target.setPos(new PVector((float) ww[0], (float) ww[1]));
		player.applyBehavior(index, dt);

	}

}
