package physics;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class SolarSystemApp implements IProcessingApp{
	
	private float sunMass = 1.989e30f;
	private float earthMass = 5.97e24f;
	private float distEarthSun = 1.496e11f;
	private float earthSpeed = 3e4f;
	
	private float[] viewport = {0.2f,0.2f,0.6f,0.6f};
	private double[] window = {-1.2*distEarthSun, 1.2*distEarthSun, -1.2*distEarthSun, 1.2*distEarthSun};
	
	private SubPlot plt;
	private Body sun, earth;
	
	private float speedUp = 60 * 60 * 24 *30;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		sun = new Body(new PVector(), new PVector(), sunMass, distEarthSun/10, p.color(255, 128, 0));
		earth = new Body(new PVector(0, distEarthSun), new PVector(earthSpeed, 0), earthMass, distEarthSun/20, p.color(0, 180, 120));
		
		
		
	}

	@Override
	public void draw(PApplet p, float dt) {
		float[] pp = plt.getBoundingBox();
		
		p.fill(255,25); // ao fazer este retangulo preenchido tranparente cria um efeito de arrasto nos celestial bodies que se mexem
		p.rect(pp[0], pp[1], pp[2], pp[3]);
		
		sun.display(p, plt);
		
		PVector f = sun.attraction(earth);
		earth.applyForce(f);
		earth.move(dt*speedUp);
		
		earth.display(p, plt);
		
		
		
	}

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
