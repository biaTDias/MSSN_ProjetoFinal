//Main processing app for the game, does the visual methods needed for display of the game

package dodger;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;
import setup.IProcessingApp;
import tools.Button;
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
	
	private PImage menuBackground;
	private PImage deadBackground;
	
	ArrayList <Button> btnsMenu;
	ArrayList <Button> btnsPaused;
	ArrayList <Button> btnsPlaying;
	ArrayList <Button> btnsDead;
	
	@Override
	public void setup(PApplet p) {
		
		plt = new SubPlot(window, viewport, p.width, p.height);
		mainColor = p.color(GameSettings.PlayerColors[0], GameSettings.PlayerColors[1], GameSettings.PlayerColors[2]);
		handler.setup(p, plt, mainColor);
		
		p.rectMode(PConstants.CENTER);
		p.textAlign(PConstants.CENTER, PConstants.CENTER);
		fontStd = p.createFont(GameSettings.fontStdFamily,GameSettings.fontStdSize,true);
		fontMenu = p.createFont("Facon.otf", 128);
		
		setupMenu(p);
		setupPaused(p);
		//setupPlaying(p);
		setupDead(p);
		
	}

	private void setupMenu(PApplet p) {
		
		menuBackground = p.loadImage("nfs-1.jpg");
		
		//Botoes Menu
		btnsMenu = new ArrayList <Button>();
		btnsMenu.add(new Button(GameSettings.middleX, GameSettings.middleY, "Start Game", fontStd, State.PLAYING, p));
		
	}
	
	private void setupPaused(PApplet p) {
		
		//Botoes Paused
		btnsPaused = new ArrayList <Button>();
		btnsPaused.add(new Button(GameSettings.middleX, GameSettings.middleY, "RESUME", fontStd, State.PLAYING, p));
		btnsPaused.add(new Button(GameSettings.middleX + 40, GameSettings.middleY + 40, "QUIT", fontStd, State.MENU, p));
		
	}
	
//	private void setupPlaying(PApplet p) {
//		
//		p.background(GameSettings.backcolor);
//		
//		// Botoes Niveis
//		btnsPaused = new ArrayList <Button>();
//		btnsPaused.add(new Button(GameSettings.middleX, GameSettings.middleY, "EASY", fontStd, State.PLAYING, p, 0));
//		btnsPaused.add(new Button(GameSettings.middleX, GameSettings.middleY + 40, "HARD", fontStd, State.PLAYING, p, 1));
//		
//	}
	
	private void setupDead(PApplet p) {
		
		deadBackground = p.loadImage("detention.jpg");

		
		//Botoes dead
		btnsDead = new ArrayList <Button>();
		btnsDead.add(new Button(GameSettings.middleX + 90, GameSettings.middleY + 150, "MENU", fontStd, State.MENU, p));
		
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
		
		
		p.image(menuBackground, 0, 0);
		
		p.textFont(fontMenu);
		float ScreenFontPosX = (p.width)/2;
		
		// Título
		p.fill(mainColor);
		p.text("Dodger Game", ScreenFontPosX, p.height/3);
		
		// Botão Play
		for(Button  b: btnsMenu)
			b.display(p);
		
	}

	private void displayPause(PApplet p) {
		p.background(GameSettings.backcolor, 80);
		p.textFont(fontStd, 22);
		p.fill(0);
		p.text("This is the Pause menu!", p.width / 2, p.height / 2);
		
		//Botao Pausa
		for(Button  b: btnsPaused)
			b.display(p);
		

	}

	private void displayGame(PApplet p, float dt) {
		
		p.background(GameSettings.backcolor, 55);
		handler.calculateChanges(p, dt, plt);	
		
		handler.displayActors(p, plt);
		
		p.textFont(fontMenu, 22);
		p.fill(mainColor);
		p.text("Score: " + handler.score, 30, 75);

	}

	private void displayGameOver(PApplet p, float dt) {
		
		p.image(deadBackground,0,0);

		int scorePos = 50;
		p.fill(p.color(255,255,255,150));
		p.rectMode(PConstants.CENTER);
		p.rect(90, p.height/2, 180, p.height);
		p.textSize(20);
		p.fill(mainColor);
		p.text("High Scores", 90, scorePos);
		
		
		for(String s : handler.getHighscores()) {
			scorePos+=40;
			p.text(s, 90, scorePos);
		}
		
		float gap = (p.width - 180)/2 + 180 ;
		p.textFont(fontMenu);
		p.fill(mainColor);
		
		p.text("GAME OVER", gap, GameSettings.middleY/ 2);
		p.textFont(fontMenu, 50);
		p.text("Final Score is " + handler.score, gap, 400);
		
		
		//Botao Pausa
		for(Button  b: btnsDead)
			b.display(p);

	}

	// ###########################################################################################################
	// Interaction handling methods
	// ###########################################################################################################

	@Override
	public void mousePressed(PApplet p) {
		State s = handler.state;
		switch (s) {
		case MENU:
			for(Button b: btnsMenu)
				handler.state = b.isInside(s, p);
			if(handler.state == State.PLAYING)
				handler.setup(p, plt, mainColor);
			break;
		case PAUSED:
			// pause screen
			for(Button b: btnsPaused)
				handler.state = b.isInside(s, p);
			break;
		case PLAYING:
			break;
		case DEAD:
			// game over screen
			for(Button b: btnsDead)
				handler.state = b.isInside(s, p);
			break;
		}
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
