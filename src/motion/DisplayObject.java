	package motion;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * Parent class of all objects in Panes (Circle, Static, Force)
 * @author Nguyen Minh Tu
 *
 */
public abstract class DisplayObject {
	protected Vector2D location;	
	protected Node view; //how this region is going to be rendered
	protected Pane parentPane; //the parent frame of this object
	
	protected double width, height = 0; //width and height of this object
	
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
	 * This function is called in this.addToPane(). In order for the object to be created
	 * Step 1: Create view of the object
	 * Step 2: Add object's view to the parentPane
	 * => This function is step 1 of the process
	 */
	public abstract Node createView();
	
	/**
	 * Add this.view to this.parentPane. If this function is not called, then the parentPane will not display this.view.
	 * This function is Step 2 of the process
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

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	
	
}
