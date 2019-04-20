package motion.staticobject;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import motion.Vector2D;

/**
 * A 1x1 block on screen
 * @author A
 *
 */
public class Block extends StaticObject {	
	private double size;

	public Block() {
		// TODO Auto-generated constructor stub
	}

	public Block(Pane parentPane, Vector2D location, double size) {
		super(parentPane, location);
		this.size = size;
	}
	
	public Block(Pane parentPane, double x, double y, double size) {
		super(parentPane, x, y);		
		this.size = size;
	}
	
	@Override
	public Node createView() {
		Rectangle rect = new Rectangle(this.getX(), this.getY(), size, size);
		rect.setFill(Color.WHITE);
		rect.setStroke(Color.BLACK);
		return rect;
	}

}
