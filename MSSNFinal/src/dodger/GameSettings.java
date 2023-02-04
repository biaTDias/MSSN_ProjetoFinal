//This class is to group all game settings so they can be easily altered 


//TODO usar Java annotations para ter uma breve explicação in-IDE do que cada variável é


package dodger;

import processing.core.PApplet;


public class GameSettings extends PApplet{
	//width e weight da janela usados em ProcessingSetup
	public final static int width = 1200;
	public final static int height = 700;
	public final static int backcolor = 25;
	
	//Font Standard
	public final static String fontStdFamily = "Verdana";
	public final static int fontStdSize = 22;
	
	//Player settings
	public final static int PlayerMaxSpeed = 10;
	public final static int PlayerMaxForce = 20;
	public final static int[] PlayerColors = {145, 29, 20};
	
	//Police settings
	public final static int PoliceMaxSpeed = 15;
	public final static int PoliceMaxForce = 7;
	public final static int numbStarterCars = 7;
	public final static int carSpawnValue = 80;
	
	//Explosion settings
	public final static float[] velParams = {PApplet.radians(3600), PApplet.radians(360), 2f, 6f};
	public final static float[] lifetimeParams = {0.1f, 0.7f};
	public final static float[] radiusParams = {0.1f, 0.2f};
	public final static float flow = 90f;
}
