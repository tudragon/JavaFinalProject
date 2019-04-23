package motion.movingobject;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import motion.Vector2D;

public class MovingCircle extends MovingObject {
	
	private double radius;

	public MovingCircle() {
		// TODO Auto-generated constructor stub
	}

	public MovingCircle(Pane parentPane, Vector2D location, double mass,double radius) {
		super(parentPane, location, mass);		
		this.radius = radius;
	}

	public MovingCircle(Pane parentPane, double x, double y, double mass,double radius) {
		super(parentPane, x, y, mass);
		this.radius = radius;
	}

	public MovingCircle(Pane parentPane, Vector2D location, Vector2D velocity, Vector2D acceleration, double mass,double radius) {
		super(parentPane, location, velocity, acceleration, mass);		
		this.radius = radius;
	}
	
	/**
	 * View of this class is a javafx.Circle. Radius is given, and color is WHITE, stroke is BLACK
	 */
	@Override
	public Node createView() {
		//create view
        Circle circle = new Circle(this.radius);

        circle.relocate(this.getX(), this.getY());

        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
        
        //set width, height
        this.setWidth(radius*2);
        this.setHeight(radius*2);
        
        return circle;
	}
	

}
