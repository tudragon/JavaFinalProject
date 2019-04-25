package motion.movingobject;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import motion.Utility;
import motion.Vector2D;

public class Rocket extends MovingObject {

	public Rocket() {
	}

	public Rocket(Pane parentPane, Vector2D location, double mass) {
		super(parentPane, location, mass);
	}

	public Rocket(Pane parentPane, double x, double y, double mass) {
		super(parentPane, x, y, mass);
	}

	public Rocket(Pane parentPane, Vector2D location, Vector2D velocity, Vector2D acceleration, double mass) {
		super(parentPane, location, velocity, acceleration, mass);
	}
	
	/**
	 * View of this class is a rocket: consists of many polygons 
	 * */
	@Override
	public Node createView() {
		Pane container = new Pane();
		//fix the coordinate of container
		Text original = new Text(0,0,"");
		container.getChildren().add(original);
		
		//1. head triangular of the rocket
		Polygon head_tria = new Polygon();
		double head_tria_raw[] = {
				1.5, 0,
				2.0, 1.0,
				1.0, 1.0
		};
		//multiple by SIZE_UNIT and add to Polygon
		head_tria.getPoints().addAll(multiplyDoubelArray(head_tria_raw));
		
		head_tria.setFill(Color.RED);
		container.getChildren().add(head_tria);
		
		//2.body of the rocket
		Polygon body_rec = new Polygon();
		double body_rec_raw[] = {
				1.0, 1.0,
				2.0, 1.0,
				2.0, 4.0,
				1.0, 4.0
		};
		//multiple by SIZE_UNIT and add to Polygon
		body_rec.getPoints().addAll(multiplyDoubelArray(body_rec_raw));
		
		body_rec.setFill(Color.WHITE);
		body_rec.setStroke(Color.BLACK);
		container.getChildren().add(body_rec);
		
		//3. the trapezoid base of the rocket
		Polygon trap_base = new Polygon();
		double trap_base_raw[] = {
				1.0, 4.0,
				2.0, 4.0,
				3.0, 5.0,
				0.0, 5.0
		};
		//multiple by SIZE_UNIT and add to Polygon
		trap_base.getPoints().addAll(multiplyDoubelArray(trap_base_raw));
		
		trap_base.setFill(Color.RED);
		container.getChildren().add(trap_base);
		
		//relocate the container
		container.relocate(this.getX(), this.getY());				
		return container;
	}
	
	
	/**
	 * Ultility function: Take a double array, multiply it with SIZE_UNIT
	 * @param multiplier the array to be multiplied
	 * @return multiplier * SIZE_UNIT
	 */
	private Double[] multiplyDoubelArray(double ...multiplier) {
		int len = multiplier.length;
		Double output[] = new Double[len];
		for (int i = 0; i < len; i++) {
			output[i] = multiplier[i] * Utility.SIZE_UNIT;
		}
		return output;
	}

	@Override
	protected void setInitWidthHeight() {
		//set width, height - depend on how the object is drawn
		this.setWidth(3 * Utility.SIZE_UNIT);
		this.setHeight(5 * Utility.SIZE_UNIT);				
	}
}
