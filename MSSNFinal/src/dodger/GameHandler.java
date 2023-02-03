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
	protected int score;
	// se calhar mudar para private e fazer um getter? more game state protection
	protected State state = State.PLAYING;

	protected Player player;
	private Body playerTarget;
	private List<Body> playerTrackingBodies;
	private int index = 0;

	private List<Body> cars;
	private List<Body> policeTrackingBodies;
	private int noNewCarCounter;

	// ###########################################################################################################
	// New Game setup
	// ###########################################################################################################
	protected void setup(PApplet p, SubPlot plt) {
		score = 0;
		noNewCarCounter = 0;

		// create player
		double[] ww = plt.getWorldCoord(p.width / 2, p.height / 2);
		player = new Player(new PVector((float) ww[0], (float) ww[1]), 1, 0.5f, p.color(252, 186, 3), p, plt);
		player.addBehavior(new Seek(1f));
		playerTarget = new Body(new PVector(), new PVector(), 1f, 3f, p.color(255, 0, 0));
		playerTrackingBodies = new ArrayList<Body>();
		playerTrackingBodies.add(playerTarget);
		Eye eye = new Eye(player, playerTrackingBodies);
		player.setEye(eye);

		// create first police cars
		cars = new ArrayList<Body>();
		for (int i = 0; i < GameSettings.numbStarterCars; i++) {
			ww = randomSpawnLocation(p, plt);
			newPolice(ww, p, plt);
		}

	}

	private void newPolice(double[] ww, PApplet p, SubPlot plt) {
		Police c = new Police(new PVector((float) ww[0], (float) ww[1]), 1, 0.5f, p.color(18, 14, 140), p, plt);
		c.addBehavior(new Seek(1f));
		c.addBehavior(new Separate(10f));
		policeTrackingBodies = new ArrayList<Body>();
		policeTrackingBodies.add(player);
		Eye eyeP = new Eye(c, policeTrackingBodies);
		c.setEyes(eyeP);
		cars.add(c);

		for (Body c1 : cars) {
			eyeP = new Eye((Boid) c1, cars);
			((Police) c1).setEyes(eyeP);
		}
	}

	private double[] randomSpawnLocation(PApplet p, SubPlot plt) {
		double[] ww = plt.getWorldCoord(0, 0);
		int side = (int) (Math.random() * 4);

		switch (side) {
		// Left
		case 0:
			ww = plt.getWorldCoord(0, (float) (Math.random() * p.height));
			break;
		// right
		case 1:
			ww = plt.getWorldCoord(p.width, (float) (Math.random() * p.height));
			break;
		// top
		case 2:
			ww = plt.getWorldCoord((float) (Math.random() * p.width), 0);
			break;
		// bottom
		default:
			ww = plt.getWorldCoord((float) (Math.random() * p.width), p.height);
			break;
		}


		return ww;

	}

	// ###########################################################################################################
	// Ongoing game
	// ###########################################################################################################

	protected void calculateChanges(PApplet p, float dt, SubPlot plt) {
		double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);

		playerTarget.setPos(new PVector((float) ww[0], (float) ww[1]));
		player.applyBehaviors(dt);
		if (player.choque((ArrayList<Body>) cars)) {
			state = State.DEAD;
		}
		for (int carCount = cars.size() - 1; carCount >= 0; carCount--) {
			Police c = (Police) cars.get(carCount);
			c.checkColision(cars);
			if (!((Police) c).alive) {
				//TODO por aqui a origem do Particle system da explosão
				
				
				// TODO enves de aumentar a score linearmente, dar a possibilidade de multiplyer
				// quando se destroem vários carros ao mesmo tempo
				cars.remove(carCount);
				score += 50;
			}
			c.applyBehaviors(dt);
		}

		noNewCarCounter += 1 + Math.random() * 2;
		if (noNewCarCounter >= GameSettings.carSpawnValue) {
			newPolice(randomSpawnLocation(p, plt), p, plt);
			noNewCarCounter = 0;
		}

	}

	protected void displayActors(PApplet p, SubPlot plt) {
		player.display(p, plt);
		for (Body c : cars) {
			c.display(p, plt);
		}
	}

}
