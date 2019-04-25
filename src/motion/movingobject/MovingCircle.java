package motion.movingobject;

import controller.LawSceneController;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import motion.Vector2D;

public class MovingCircle extends MovingObject {
	
	private static double radius = LawSceneController.SIZE_UNIT;

	public MovingCircle() {
		// TODO Auto-generated constructor stub
	}

	public MovingCircle(Pane parentPane, Vector2D location, double mass) {
		super(parentPane, location, mass);		
	}

	public MovingCircle(Pane parentPane, double x, double y, double mass) {
		super(parentPane, x, y, mass);
	}

	public MovingCircle(Pane parentPane, Vector2D location, Vector2D velocity, Vector2D acceleration, double mass) {
		super(parentPane, location, velocity, acceleration, mass);		
	}
	
	/**
	 * View of this class is a javafx.Circle. Radius is given, and color is WHITE, stroke is BLACK
	 */
	@Override
	public Node createView() {
		//create view
        Circle circle = new Circle(radius);

        circle.relocate(this.getX(), this.getY());

        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
        return circle;
	}

	@Override
	protected void setInitWidthHeight() {
		//set width, height
        this.setWidth(radius*2);
        this.setHeight(radius*2);
	}
	

}
