package motion.movingobject;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import motion.DisplayObject;
import motion.Force;
import motion.Vector2D;

/**
 * Parent class of all objects in Panes (Circle, Sprite)
 * @author A
 *
 */
public abstract class MovingObject extends DisplayObject {
	protected Vector2D velocity;
	protected Vector2D acceleration;
	
	/** Every moving object must have a mass (so that forces can act on it)*/
	protected double mass;
	
	/** The list of forces acting on this object in real-time. Used to calculate acceleration every frame. */
	protected List<Vector2D> forces = new ArrayList<Vector2D>();
	
	public MovingObject() {
		
	}		
	
	/**
	 * Init by vector location and mass, zero velocity and acceleration
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
	 * 1. a = sum(F)/m
	 * 2. v' = v + at
	 * 3. x' = x + at
	 */
	@Override
	public void update(double elapsedSeconds) {
		//1. a = F/m - recalculate acceleration every frame
		Vector2D acceleration = new Vector2D(0, 0);
		
		for (Vector2D forceVector : forces) {
			acceleration.add(forceVector.divideVector(this.mass));
		}
		
		this.setAcceleration(acceleration);
		
		//2. v' = v + at
		this.getVelocity().add(this.getAcceleration().multiplyVector(elapsedSeconds));
		//3. x' = x + vt
		this.getLocation().add(this.getVelocity().multiplyVector(elapsedSeconds));
		
		this.view.relocate(this.getX(), this.getY());
		
//		System.out.println("Location: " + this.getLocation() + "  Velo: " + this.getVelocity()
//				+ "  Acce: " + this.getAcceleration());
	}
	
	/**
	 * Add a forceVector to this object's force. Only need the forceVector, don't need to track the whole {@link Force} class object
	 * @param forceVector a {@link Vector2D}. Found in {@link Force}.forceVector
	 * This function is called only in Force(Pane parentPane, double x, double y, MovingObject object, String name)
	 */
	public void addForce(Vector2D forceVector) {
		this.forces.add(forceVector);
	}
	
	/**
	 * Remove force from this object's force
	 * @param forceVector a {@link Vector2D}. Found in {@link Force}.forceVector
	 * @return true if successfully. false if not
	 */
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
	
	/** Get the net Force on the moving object*/
	public Vector2D getSumForceVector() {
		Vector2D result = new Vector2D(0, 0);
		
		for (Vector2D forceVector : forces) {
			result.add(forceVector);
		}
		
		return result;
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
