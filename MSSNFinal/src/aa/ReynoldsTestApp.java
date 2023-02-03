package aa;

import java.util.ArrayList;
import java.util.List;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class ReynoldsTestApp implements IProcessingApp{
	
	private Boid wander, seeker, pursuiter;
	
	private Flock flock;
	private float[] sacWeight = {3f,1f,2f};
	private double[] window = {-10, 10, -10, 10};
	private float[] view1 = {0.02f, 0.51f, 0.96f, 0.47f};
	private float[] view2 = {0.02f, 0.02f, 0.47f, 0.47f};
	private float[] view3 = {0.51f, 0.02f, 0.47f, 0.47f};
	private SubPlot plt1, plt2, plt3;
	
	private Body target;
	
	private int ix =0;

	@Override
	public void setup(PApplet p) {
		
		plt1 = new SubPlot(window, view1, p.width, p.height);
		plt2 = new SubPlot(window, view2, p.width, p.height);
		plt3 = new SubPlot(window, view3, p.width, p.height);
		flock = new Flock(100, 0.1f, 0.3f, p.color(0, 100, 200), sacWeight, p, plt1);
		
		wander = new Boid(new PVector(p.random((float) (window[0]),(float) (window[1])), (float) (window[2]),(float) (window[3])), 0.5f, 0.5f, p.color(25, 255, 50), p, plt2);
		wander.addBehavior(new Wander(1f));
		
		pursuiter = new Boid(new PVector(p.random((float) (window[0]),(float) (window[1])), (float) (window[2]),(float) (window[3])), 0.5f, 0.5f, p.color(255, 0, 0), p, plt2);
		pursuiter.addBehavior(new Pursuit(1f));
		List<Body> allTrackingBodies = new ArrayList<Body>();
		allTrackingBodies.add(wander);
		pursuiter.setEye(new Eye(pursuiter, allTrackingBodies));
		
		target = new Body(new PVector(), new PVector(), 1f, .3f, p.color(0));
		seeker = new Boid(new PVector(p.random((float) (window[0]),(float) (window[1])), (float) (window[2]),(float) (window[3])), 0.5f, 0.5f, p.color(10, 255, 255), p, plt3);
		seeker.addBehavior(new Seek(1f));
		seeker.addBehavior(new Wander(1f));
		//seeker.addBehavior(new Flee(1f));
		allTrackingBodies = new ArrayList<Body>();
		allTrackingBodies.add(target);
		seeker.setEye(new Eye(pursuiter, allTrackingBodies));
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(255);
		
		float[] bb = plt1.getBoundingBox();
		p.fill(255, 60);
		p.rect(bb[0], bb[1], bb[2], bb[3]);
		
		bb = plt2.getBoundingBox();
		p.fill(100, 150, 200, 60);
		p.rect(bb[0], bb[1], bb[2], bb[3]);
		
		bb = plt3.getBoundingBox();
		p.fill(190, 170, 45, 60);
		p.rect(bb[0], bb[1], bb[2], bb[3]);
		
		wander.applyBehaviors(dt);
		pursuiter.applyBehaviors(dt);
		seeker.applyBehaviors(dt);
		flock.applyBehavior(dt);
		
		wander.display(p, plt2);
		pursuiter.display(p, plt2);
		seeker.display(p, plt3);
		flock.display(p, plt1);
		target.display(p, plt3);
		
	}

	@Override
	public void mousePressed(PApplet p) {
		if(plt3.isInside(p.mouseX, p.mouseY)) {
			double[] w = plt3.getWorldCoord(p.mouseX, p.mouseY);
			target.setPos(new PVector((float)(w[0]),(float) (w[1])));
		}
		
		
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(PApplet p) {
		if(p.key == 't') {
			ix = (ix + 1)%2;
		}
		
	}



	@Override
	public void mouseMoved(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(PApplet p) {
		// TODO Auto-generated method stub
		
	}

}
