package setup;

import processing.core.PApplet;

public interface IProcessingApp {
	public void setup(PApplet p);
	public void draw(PApplet p, float dt);
	public void mousePressed(PApplet p);
	void mouseMoved(PApplet p);
	void keyPressed(PApplet p);
	void mouseReleased(PApplet p);
	void mouseDragged(PApplet p);
}
