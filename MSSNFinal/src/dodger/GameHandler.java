//All backend game tasks are handled in this class

package dodger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import aa.*;
import physics.*;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;
//import ddf.minim.*;

public class GameHandler {
	protected int score;
	protected int mColor;
	// se calhar mudar para private e fazer um getter? more game state protection
	protected State state = State.PLAYING;

	protected Player player;
	private Body playerTarget;
	private List<Body> playerTrackingBodies;

	private List<Body> cars;
	private List<Body> policeTrackingBodies;
	private int noNewCarCounter;

	// Explosions, temporary solution because this implementation will sink a lot of
	// memory without need
	private List<ParticleSystem> explosions;
	private List<Integer> explosionsLife;

	// sounds
//	Minim minim;
//	AudioPlayer soundPlayer;

	// High Scores
	private String scoresFile;
	private String delimiter;
	private Scanner scanner;
	private ArrayList<String> scores;

	// ###########################################################################################################
	// New Game setup
	// ###########################################################################################################
	protected void setup(PApplet p, SubPlot plt, int mainColor) {
		score = 0;
		noNewCarCounter = 0;
		this.mColor = mainColor;

		// create player
		double[] ww = plt.getWorldCoord(p.width / 2, p.height / 2);
		player = new Player(new PVector((float) ww[0], (float) ww[1]), 1, 0.4f, mColor, p, plt);
		player.addBehavior(new Seek(1f));
		playerTarget = new Body(new PVector(), new PVector(), 1f, 3f, p.color(255, 0, 0));
		playerTrackingBodies = new ArrayList<Body>();
		playerTrackingBodies.add(playerTarget);
		Eye eye = new Eye(player, playerTrackingBodies);
		player.setEye(eye);

		// create first police cars
		cars = new ArrayList<Body>();
		for (int i = 0; i < GameSettings.numbStarterCars; i++) {
			newPolice(randomSpawnLocation(p, plt), p, plt);
		}

		// Explosions set up
		explosions = new ArrayList<ParticleSystem>();
		explosionsLife = new ArrayList<Integer>();

//		//sound
//		minim = new Minim(this);
//		soundPlayer = minim.loadFile("explosionSound.wav");

		// High Scores set up
		scoresFile = GameSettings.scoresFile;
		delimiter = GameSettings.scoresDelim;
		scores = new ArrayList<String>();

		System.out.println("High Scores on set-up:");
		try {
			File file = new File(scoresFile);
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] tempScores = line.split(delimiter);

				for (String s : tempScores) {
					scores.add(s);
					System.out.println(s);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + scoresFile);
		}

	}

	private void newPolice(double[] ww, PApplet p, SubPlot plt) {
		Police c = new Police(new PVector((float) ww[0], (float) ww[1]), 1, 0.5f, p.color(46, 94, 199), p, plt);
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
		// Player changes
		double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
		playerTarget.setPos(new PVector((float) ww[0], (float) ww[1]));
		player.applyBehaviors(dt);
		if (player.choque((ArrayList<Body>) cars)) {
			state = State.DEAD;
			if (score > 0)
				addScore(score);
		}

		// Police changes
		for (int carCount = cars.size() - 1; carCount >= 0; carCount--) {
			Police c = (Police) cars.get(carCount);
			c.checkColision(cars);
			if (!((Police) c).alive) {
				// explosion
				PSControl psc = new PSControl(GameSettings.velParams, GameSettings.lifetimeParams,
						GameSettings.radiusParams, GameSettings.flow, p.color(220));
				ParticleSystem ps = new ParticleSystem(c.getPos(), new PVector(), 2f, 0.2f, psc);
				explosions.add(ps);
				int eL = 0;
				explosionsLife.add(eL);
//				soundPlayer.rewind();
//				soundPlayer.play();

				// TODO enves de aumentar a score linearmente, dar a possibilidade de multiplyer
				// quando se destroem v�rios carros ao mesmo tempo
				cars.remove(carCount);
				score += 50;
			}
			c.applyBehaviors(dt);
		}

		// Police Spawn
		noNewCarCounter += 1 + Math.random() * 2;
		if (noNewCarCounter >= GameSettings.carSpawnValue) {
			newPolice(randomSpawnLocation(p, plt), p, plt);
			noNewCarCounter = 0;
		}

		// Explosion changes
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).move(dt);
			explosionsLife.set(i, explosionsLife.get(i) + 1);
			if (explosionsLife.get(i) >= 40) {
				explosions.get(i).getPSControl().setFlow(0f);
			}
		}

	}

	// Display all elements of the game
	protected void displayActors(PApplet p, SubPlot plt) {
		player.display(p, plt);
		for (Body c : cars) {
			c.display(p, plt);
		}
		for (ParticleSystem ps : explosions) {
			ps.display(p, plt);
		}
	}

	// return the list of the high scores
	protected ArrayList<String> getHighscores() {
		if (scores.size() <= 0) {
			System.out.println("empty scores");
		}
		return scores;
	}

	// calculate and add new highscore if it matches criteria
	private void addScore(int sc) {
		int i = 0;
		for (String s : scores) {
			// if score is same then it keeps the old one
			try {
				int value = Integer.valueOf(s.trim());
				if (value < sc && i <= GameSettings.maxNumbSavedScores - 1) {
					scores.add(i, String.valueOf(sc));
					alterScoreFile();
				}
			} catch (NumberFormatException e) {
				System.out.println("Error: " + s + " is not a valid integer");
			}
			i++;
		}
		// if there's still space in highscores
		if (i < GameSettings.maxNumbSavedScores - 1) {
			scores.add(String.valueOf(sc));
			alterScoreFile();
		}
	}

	private void alterScoreFile() {
		try {
			FileWriter writer = new FileWriter(scoresFile);
			for (String s : scores) {
				writer.write("/n" + s);
			}
			writer.close();
			System.out.println("Scores file updated");
		} catch (IOException e) {
			System.out.println("Error writing to file: " + scoresFile);
			e.printStackTrace();
		}
	}

}
