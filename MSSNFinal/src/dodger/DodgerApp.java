//Main processing app for the game, does the visual methods needed for display of the game

package dodger;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;
import setup.IProcessingApp;
import tools.SubPlot;

public class DodgerApp implements IProcessingApp {
	
	private GameHandler handler = new GameHandler();
	
	private double[] window = {-10, 10, -10, 10};
	private float[] viewport = {0,0, 1,1};
	private SubPlot plt;

	private PFont fontStd;
	private PFont fontMenu;
	
	int buttonY, buttonHeight, buttonWidth;
	int[] buttonXs = new int [3];
	
	private PImage menuBackground;
	
	
	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		handler.setup(p, plt);
		
		
		fontStd = p.createFont(GameSettings.fontStdFamily,GameSettings.fontStdSize,true);
		fontMenu = p.createFont("Facon.otf", 128);
		
		menuBackground = p.loadImage("nfs-1.jpg");
		
		
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
		
		
		p.image(menuBackground, 0, 0);
		
		p.pushStyle();
		p.textFont(fontMenu);
		float x = p.textWidth("Dodger Game");
		float ScreenFontPosX = (p.width - x)/2;
		
		// Título
		p.fill(p.color(196, 13, 0));
		p.text("Dodger Game", ScreenFontPosX, p.height/3);
		p.popStyle();
		
		// Botão Play
		//p.pushStyle();
//		p.textFont(fontStd);
//		x = p.textWidth("START GAME");
//		ScreenFontPosX = (p.width - x)/2;
//		p.noStroke();
//		p.rect(ScreenFontPosX-10, (p.height - GameSettings.fontStdSize)/1.8f, x + 20, GameSettings.fontStdSize +20);
//		p.fill(0);
//		p.text("START GAME", ScreenFontPosX, (p.height - GameSettings.fontStdSize)/1.8f);
//		
		
		p.textFont(fontStd);
		
		p.fill(255);
		x = p.textWidth("START GAME");
		p.noStroke();
		p.rectMode(PConstants.CENTER);
		p.rect(p.width/2, p.height/2 ,x + 30, GameSettings.fontStdSize + 30);
		p.fill(0);
		p.textAlign(PConstants.CENTER, PConstants.CENTER);
		p.text("START GAME", p.width/2-1, p.height/2-4);
		
		if (p.mouseX >= p.width/2 - (x+30)/2 && p.mouseX <= p.width/2 + (x+30)/2 && 
			      p.mouseY >= p.height/2-(GameSettings.fontStdSize + 30)/2 && p.mouseY <= p.height/2+(GameSettings.fontStdSize + 30)/2) 
			    handler.state = State.PLAYING;
			  
		
		
//		for(int i = 0; i < buttonXs.length; i++) {
//			if (p.mouseX >= buttonXs[i] && p.mouseX <= buttonXs[i]+buttonWidth && 
//				      p.mouseY >= buttonY && p.mouseY <= buttonY+buttonHeight) {
//				    handler.state = State.PLAYING;
//				    
//				    break;
//				  }
//		}
		
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
