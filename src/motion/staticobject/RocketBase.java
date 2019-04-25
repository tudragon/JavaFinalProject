package motion.staticobject;

import controller.LawSceneController;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
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
		this.drawLine(0, 0, 0, 6, path);
		
		//Draw (0,0) => (2.5, 0)
		this.drawLine(0, 0, 2.5 , 0, path);
		
		//Draw (0,5) => (5, 5)
		this.drawLine(0, 5, 5 , 5, path);
		
		//Draw (5,5) => (5, 6)
		this.drawLine(5, 5, 5 , 6, path);
		
		//relocate path to location
		path.relocate(this.getX(), this.getY());
		
		return path;
	}
	
	/**
	 * add a line from (startX * SIZE_UNIT,startY * SIZE_UNIT) -> (endX * SIZE_UNIT ,endY * SIZE_UNIT) to path
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param path
	 */
	private void drawLine(double startX, double startY, double endX, double endY, Path path) {
		MoveTo moveTo = new MoveTo(startX* LawSceneController.SIZE_UNIT
				, startY* LawSceneController.SIZE_UNIT);
		LineTo lineTo = new LineTo(endX* LawSceneController.SIZE_UNIT
				, endY * LawSceneController.SIZE_UNIT);
		
		path.getElements().add(moveTo); 
		path.getElements().add(lineTo);
	}
	
	@Override
	protected void setInitWidthHeight() {
		//set width, height
		this.setWidth(5 * LawSceneController.SIZE_UNIT);
		this.setHeight(6 * LawSceneController.SIZE_UNIT);			
	}
}
