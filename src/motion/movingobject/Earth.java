package motion.movingobject;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import motion.Vector2D;

/**
 * Earth can be exerted force it. It has no view and INFINITY mass
 * @author Nguyen Minh Tu
 *
 */
public class Earth extends MovingObject {


	public Earth(Pane parentPane, Vector2D location) {
		super(parentPane, location, Double.MAX_VALUE);
	}

	public Earth(Pane parentPane, double x, double y) {
		super(parentPane, x, y, Double.MAX_VALUE);
	}

	@Override
	public Node createView() {
		Rectangle rect = new Rectangle(this.getX(), this.getY(), 0, 0);
		return rect;
	}

	@Override
	protected void setInitWidthHeight() {
		//no width, height
	}

}
