package motion.staticobject;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import motion.Utility;
import motion.Vector2D;

/**
 * A rocket launcher, used in law 3 scene
 * @author Nguyen Minh Tu
 *
 */
public class RocketBase extends StaticObject {

	public RocketBase() {
	}

	public RocketBase(Pane parentPane, Vector2D location) {
		super(parentPane, location);
	}

	public RocketBase(Pane parentPane, double x, double y) {
		super(parentPane, x, y);
	}
	
	/**
	 * View of this rocket launcher is a javafx.Path 
	 */
	@Override
	public Node createView() {
		Path path = new Path();
		path.setStroke(Color.BLACK);
		
		//Draw (0,0) => (0,6)
		Utility.drawLine(0, 0, 0, 6, path);
		
		//Draw (0,0) => (2.5, 0)
		Utility.drawLine(0, 0, 2.5 , 0, path);
		
		//Draw (0,5) => (5, 5)
		Utility.drawLine(0, 5, 5 , 5, path);
		
		//Draw (5,5) => (5, 6)
		Utility.drawLine(5, 5, 5 , 6, path);
		
		//relocate path to location
		path.relocate(this.getX(), this.getY());
		
		return path;
	}
		
	@Override
	protected void setInitWidthHeight() {
		//set width, height
		this.setWidth(5 * Utility.SIZE_UNIT);
		this.setHeight(6 * Utility.SIZE_UNIT);			
	}
}
