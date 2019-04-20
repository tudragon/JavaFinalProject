package motion.movingobject;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import motion.DisplayObject;
import motion.Vector2D;

/**
 * Parent class of all objects in Panes (Circle, Sprite, Static)
 * @author A
 *
 */
public abstract class MovingObject extends DisplayObject {
	protected Vector2D velocity;
	protected Vector2D acceleration;
	protected double mass;
	protected List<Vector2D> forces = new ArrayList<Vector2D>(); //list of forces acting on this object
	
	public MovingObject() {
		
	}		
	
	/**
	 * Init by vector location and mass
	 * @param location
	 * @param mass
	 */
	public MovingObject(Pane parentPane, Vector2D location, double mass) {
		super(parentPane, location);
		this.mass = mass;
		this.velocity = new Vector2D(0, 0);
		this.acceleration = new Vector2D(0, 0);
	}
	
	/**
	 * Init by location and mass
	 * @param x
	 * @param y
	 * @param mass
	 */
	public MovingObject(Pane parentPane, double x, double y, double mass) {
		this(parentPane, new Vector2D(x, y), mass);		
	}
	
	/**
	 * Full initialization of an object
	 * @param location
	 * @param velocity
	 * @param acceleration
	 * @param mass
	 */
	public MovingObject(Pane parentPane, Vector2D location, Vector2D velocity, Vector2D acceleration, double mass) {
		super(parentPane, location);
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.mass = mass;
	}

	/**
	 * Re-calculate location, velocity and acceleration every frame
	 * @param elapsedSeconds seconds pass by the current frame
	 */
	@Override
	public void update(double elapsedSeconds) {
		// a = F/m - recalculate acceleration every frame
		Vector2D acceleration = new Vector2D(0, 0);
		
		for (Vector2D forceVector : forces) {
			acceleration.add(forceVector);
		}
		
		this.setAcceleration(acceleration);
		
		//v' = v + at
		this.getVelocity().add(this.getAcceleration().multiplyVector(elapsedSeconds));
		//x' = x + vt
		this.getLocation().add(this.getVelocity().multiplyVector(elapsedSeconds));
		
		this.view.relocate(this.getX(), this.getY());
		
		//System.out.println("Location: " + this.getLocation() + "  Velo: " + this.getVelocity()
			//	+ "  Acce: " + this.getAcceleration());
	}
	
	public void addForce(Vector2D forceVector) {
		this.forces.add(forceVector);
	}
	
	public boolean removeForce(Vector2D forceVector) {
		
		//find forceVector among forces
		int index = forces.indexOf(forceVector);
		
		if(index == -1) {
			//not found			
			return false; 
		} else {
			forces.remove(index);			
			return true;
		}
	}
	
	public Vector2D getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}

	public Vector2D getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector2D acceleration) {
		this.acceleration = acceleration;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

}
