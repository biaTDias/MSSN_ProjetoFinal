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
	private PFont fontMenu;
	
	protected int mainColor;
	
	int buttonY, buttonHeight, buttonWidth;
	int[] buttonXs = new int [3];
	
	
	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		mainColor = p.color(GameSettings.PlayerColors[0], GameSettings.PlayerColors[1], GameSettings.PlayerColors[2]);
		handler.setup(p, plt, mainColor);
		
		
		fontStd = p.createFont(GameSettings.fontStdFamily,GameSettings.fontStdSize,true);
		fontMenu = p.createFont("Facon.otf", 128);
		
		
		
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
			displayGameOver(p, dt);
			break;
		}
	}

	private void displayMenu(PApplet p) {
		p.background(GameSettings.backcolor);
		p.textFont(fontMenu);
		
		// Título
		p.fill(mainColor);
		p.text("Dodger Game", p.width / 2, p.height / 2);
		
		// Botão Play
		for(int i = 0; i < buttonXs.length; i++) {
			if (p.mouseX >= buttonXs[i] && p.mouseX <= buttonXs[i]+buttonWidth && 
				      p.mouseY >= buttonY && p.mouseY <= buttonY+buttonHeight) {
				    displayGame(p, i);
				    System.out.println("HEY");
				    break;
				  }
		}

	}

	private void displayPause(PApplet p) {
		p.background(GameSettings.backcolor, 80);
		p.textFont(fontStd, 22);
		p.fill(0);
		p.text("This is the Pause menu!", p.width / 2, p.height / 2);

	}

	private void displayGame(PApplet p, float dt) {
		p.background(GameSettings.backcolor, 55);
		handler.calculateChanges(p, dt, plt);	
		
		handler.displayActors(p, plt);
		
		p.textFont(fontMenu, 22);
		p.fill(mainColor);
		p.text("Score: "+handler.score, 30, 75);

	}

	private void displayGameOver(PApplet p, float dt) {
		p.background(GameSettings.backcolor, 50);
		p.textFont(fontMenu, 30);
		p.fill(mainColor);
		p.text("GAME OVER", (p.width / 2)-70, p.height / 2);
		p.text("Final Score is "+handler.score, (p.width / 2)-20, 400);

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
