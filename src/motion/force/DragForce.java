package motion.force;

import javafx.scene.layout.Pane;
import motion.Vector2D;
import motion.movingobject.MovingObject;

/**
 * This illustrates drag force of atmosphere
 * F = kv^2; k is constant
 * @author Tu
 *
 */
public class DragForce extends Force {
	
	private static final double k_const = 0.015f;

	public DragForce(Pane parentPane, double x, double y, MovingObject object, String name) {
		super(parentPane, x, y, object, name);
	}
	
	/**
	 * Update value of drag force every frame. Fomular F_drag = k * v^2
	 */
	@Override
	protected void updateForceVector(double elapsedSeconds) {
		//F' = k*v^2
		Vector2D velocity = this.getObject().getVelocity();
		Vector2D forceVector = new Vector2D(-k_const* velocity.getX() * velocity.getX(),
				k_const* velocity.getY() * velocity.getY()); //k*v*v
		this.setForceVector(forceVector);
		
		//System.out.println("Drag force: " + forceVector);
	}

}
