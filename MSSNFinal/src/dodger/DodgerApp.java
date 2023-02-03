//Main processing app for the game, does the visual methods needed for display of the game

package dodger;

import processing.core.PApplet;
import processing.core.PFont;
import setup.IProcessingApp;
import tools.SubPlot;

public class DodgerApp implements IProcessingApp {
	private GameHandler handler = new GameHandler();
	
	private double[] window = {-10, 10, -10, 10};
	private float[] viewport = {0,0, 1,1};
	private SubPlot plt;

	private PFont fontStd;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		handler.setup(p, plt);
		
		
		fontStd = p.createFont(GameSettings.fontStdFamily,GameSettings.fontStdSize,true);
		
//		//Para ver as fonts disponiveis
//		for (String var : PFont.list()) 
//		{ 
//		    System.out.println(var);
//		}
	}

	// ###########################################################################################################
	// Display methods
	// ###########################################################################################################

	@Override
	public void draw(PApplet p, float dt) {

		
		display(p, dt);
	}

	protected void display(PApplet p, float dt) {

		switch (handler.state) {
		case MENU:
			// display o MainMenu
			displayMenu(p);
			break;
		case PAUSED:
			// pause screen
			displayPause(p);
			break;
		case PLAYING:
			// in-game screen
			displayGame(p, dt);
			break;
		case DEAD:
			// game over screen
			displayGameOver(p);
			break;
		}
	}

	private void displayMenu(PApplet p) {
		p.background(255);
		p.textFont(fontStd, 22);
		p.fill(0);
		p.text("This is the Main menu!", p.width / 2, p.height / 2);

	}

	private void displayPause(PApplet p) {
		p.background(255);
		p.textFont(fontStd, 22);
		p.fill(0);
		p.text("This is the Pause menu!", p.width / 2, p.height / 2);

	}

	private void displayGame(PApplet p, float dt) {
		p.background(255);
		handler.calculateChanges(p, dt, plt);
		
		
		
		p.textFont(fontStd, 22);
		p.fill(0);
		p.text("This is the game screen!", p.width / 2, p.height / 2);
		
		
		handler.player.display(p, plt);

	}

	private void displayGameOver(PApplet p) {
		p.background(255);
		p.textFont(fontStd, 22);
		p.fill(0);
		p.text("This is the Game Over screen!", p.width / 2, p.height / 2);

	}

	// ###########################################################################################################
	// Interaction handling methods
	// ###########################################################################################################

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(PApplet p) {
		// TODO Auto-generated method stub

	}

}
