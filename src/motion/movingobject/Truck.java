package motion.movingobject;


import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import motion.Utility;
import motion.Vector2D;

public class Truck extends MovingObject {	
	
	public Truck() {
	}

	public Truck(Pane parentPane, Vector2D location, double mass) {
		super(parentPane, location, mass);		
	}

	public Truck(Pane parentPane, double x, double y, double mass) {
		super(parentPane, x, y, mass);		
	}

	public Truck(Pane parentPane, Vector2D location, Vector2D velocity, Vector2D acceleration, double mass) {
		super(parentPane, location, velocity, acceleration, mass);
		
	}
	
	/**
	 * View of this class is truck: consists of many polygons 
	 * */
	@Override
	public Node createView() {
		Pane container = new Pane();
		//fix the coordinate of container
		Text original = new Text(0,0,"");
		container.getChildren().add(original);
		
		//1. the container of the truck
		Polygon hori_rec = new Polygon();
		double hori_rec_raw[] = {
				0.0, 1.0,
				0.0, 2.0,
				2.0, 2.0,
				2.0, 1.0
		};
		//multiple by SIZE_UNIT and add to Polygon
		hori_rec.getPoints().addAll(multiplyDoubelArray(hori_rec_raw));
		
		hori_rec.setFill(Color.WHITE);
		hori_rec.setStroke(Color.BLACK);
		container.getChildren().add(hori_rec);
		
		//2. the head of the truck
		Polygon hori_ver = new Polygon();
		double hori_ver_raw[] = {
				2.0, 0.0,
				3.0, 0.0,
				3.0, 2.0,
				2.0, 2.0
		};
		//multiple by SIZE_UNIT and add to Polygon
		hori_ver.getPoints().addAll(multiplyDoubelArray(hori_ver_raw));
		
		hori_ver.setFill(Color.BLUE);
		container.getChildren().add(hori_ver);
		
		//3. front wheel
		Circle front_wheel = new Circle(2 * Utility.SIZE_UNIT
				, 2.5 * Utility.SIZE_UNIT
				, Utility.SIZE_UNIT/2);
		front_wheel.setFill(Color.WHITE);
		front_wheel.setStroke(Color.BLUE);
		container.getChildren().add(front_wheel);
		
		//4. back wheel
		Circle back_wheel = new Circle(0.5 * Utility.SIZE_UNIT
				, 2.5 * Utility.SIZE_UNIT
				, Utility.SIZE_UNIT/2);
		back_wheel.setFill(Color.WHITE);
		back_wheel.setStroke(Color.BLUE);
		container.getChildren().add(back_wheel);
		
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
		this.width = 3 * Utility.SIZE_UNIT;
		this.height = 3 * Utility.SIZE_UNIT;
	}

}
