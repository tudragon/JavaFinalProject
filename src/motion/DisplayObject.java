	package motion;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * Parent class of all objects in Panes (Circle, Sprite, Static)
 * @author A
 *
 */
public abstract class DisplayObject {
	protected Vector2D location;	
	protected Node view; //how this region is going to be rendered
	protected Pane parentPane;
	
	public DisplayObject() {
		
	}		
		
	public DisplayObject(Pane parentPane, Vector2D location) {
		super();
		this.location = location;
		this.parentPane = parentPane;		
	}	
	
	public DisplayObject(Pane parentPane, double x, double y) {
		this(parentPane, new Vector2D(x, y));	
	}
	
	
	/**
	 * Re-calculate every frame
	 */
	public abstract void update(double elapsedSeconds);
	
	/**
	 * How to render this node
	 */
	public abstract Node createView();
	
	/**
	 * Add this.view to parent pane
	 */
	public void addToPane() {
		//add view to this node
		this.view = createView();				
		//add this node to pane
		this.parentPane.getChildren().add(this.view);
	}

	public Vector2D getLocation() {
		return location;
	}

	public void setLocation(Vector2D location) {
		this.location = location;
	}
	
	public void setX(double x) {
		this.getLocation().setX(x);
	}
	
	public void setY(double y) {
		this.getLocation().setY(y);
	}
	
	public double getX() {
		return this.getLocation().getX();
	}
	
	public double getY() {
		return this.getLocation().getY();
	}

	public Node getView() {
		return view;
	}

	public void setView(Node view) {
		this.view = view;
	}
	
	
	
}
