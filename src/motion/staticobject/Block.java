package motion.staticobject;

import controller.LawSceneController;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import motion.Vector2D;

/**
 * A 1x1 block on screen
 * @author Nguyen Minh Tu
 *
 */
public class Block extends StaticObject {	
	private static double size = LawSceneController.SIZE_UNIT;

	public Block() {
		// TODO Auto-generated constructor stub
	}

	public Block(Pane parentPane, Vector2D location) {
		super(parentPane, location);
	}
	/**
	 * Create a block
	 * @param parentPane the frame that contains this block
	 * @param x x-position of the block
	 * @param y y-position of the block
	 * @param size
	 */
	public Block(Pane parentPane, double x, double y) {
		super(parentPane, x, y);		
	}
	
	/**
	 * View of this Block would be a rectangle
	 */
	@Override
	public Node createView() {
		//create rectangle view
		Rectangle rect = new Rectangle(this.getX(), this.getY(), size, size);
		rect.setFill(Color.WHITE);
		rect.setStroke(Color.BLACK);
		//set width, height
		
		return rect;
	}

	@Override
	protected void setInitWidthHeight() {
		this.setWidth(size);
		this.setHeight(size);
	}

}
