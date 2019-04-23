package motion;


import controller.LawOneController;
import controller.LawSceneController;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import motion.movingobject.MovingObject;

/**
 * Force is a simple Vector2D
 * @author Nguyen Minh Tu
 * x,y is its magnitude in Newton
 * MovingObject is the object to be exerted on
 */
public class Force extends DisplayObject {
	/** Name of the force to be displayed on screen. Ex: "F", "G", "N", ... */
	private String name;
	private Text nameLabel; //javafx.Text object
	private double relativeXofTextToForce = 0.5; //how the force's name is being displayed
	
	/** The object this force acts on*/
	private MovingObject object;
	
	/** Force magnitude in Newton (N). Ex (10,5) is force with 10N x-axis and 5N y-axis */
	private Vector2D forceVector;
	
	/** Display arrow of the force*/
	private Path arrow;
	
	/** Default force is 10 Newton*/
	private final int DEFAULT_FORCE = 10;
	
	/**
	 * Init the force, but not start yet
	 * @param x magnitude in x-axis (N)
	 * @param y magnitude in y-axis (N)
	 * @param object the object to be exerted on
	 * @param name name of force display on screen
	 */
	public Force(Pane parentPane, double x, double y, MovingObject object, String name) {
		super();
		this.parentPane = parentPane;
		this.forceVector = new Vector2D(x, y);
		this.object = object;				
		this.name = name;
		this.object.addForce(forceVector);
	}
	
	/**
	 * Add addition force to force vector
	 */
	public void speedUp() {
		this.getForceVector().add(new Vector2D(DEFAULT_FORCE, 0));
	}
	
	/**
	 * Subtract force from force vector
	 */
	public void slowDown() {
		this.getForceVector().subtract(new Vector2D(DEFAULT_FORCE, 0));
	}

	public MovingObject getObject() {
		return object;
	}

	public void setObject(MovingObject object) {
		this.object = object;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Vector2D getForceVector() {
		return forceVector;
	}

	public void setForceVector(Vector2D forceVector) {
		this.forceVector = forceVector;
	}

	public double getRelativeXofTextToForce() {
		return relativeXofTextToForce;
	}

	public void setRelativeXofTextToForce(double relativeXofTextToForce) {
		this.relativeXofTextToForce = relativeXofTextToForce;
	}

	@Override
	public void update(double elapsedSeconds) {
		//bound this.location to object.location
		this.view.relocate(this.object.getX(), this.object.getY());
		
		//clear arrow
		arrow.getElements().clear();
		
		//redraw arrow
		double forceVectorX = this.forceVector.getX();
		double forceVectorY = this.forceVector.getY();
		
		//only draw if force != (0,0)
		if((forceVectorX != 0) || (forceVectorY != 0)) {
			//update arrow
			double startX = object.getWidth()/2; //start at the center of object
			double startY = object.getHeight()/2;
			double endX = startX + forceVectorX*LawSceneController.SIZE_UNIT/2; //end at arrow tip
			double endY = startY + forceVectorY*LawSceneController.SIZE_UNIT/2;
			
			drawArrow(startX, startY, endX, endY);
			
			//update label's position: 0,5 to the left or right of the force's arrow
			nameLabel.setX(endX + this.getRelativeXofTextToForce() * LawSceneController.SIZE_UNIT);
			nameLabel.setY(endY);			
			nameLabel.setVisible(true);
		} else {
			//if force if (0,0), then hide the nameLabel from scene
			nameLabel.setVisible(false);
		}	
	}
	
	/**
	 * Draw an arrow once. This function is called every frame.
	 * The update() function would delete the current arrow this.arrow, then re-draw it using this function
	 * @return add elements to this.arrow from scratch
	 */
	private void drawArrow(double startX, double startY, double endX, double endY) {
		this.arrow.strokeProperty().bind(this.arrow.fillProperty());
		this.arrow.setFill(Color.BLACK);
        
        //Line
		this.arrow.getElements().add(new MoveTo(startX, startY));
		this.arrow.getElements().add(new LineTo(endX, endY));
        
        //ArrowHead
        double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        //point1
        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * LawSceneController.SIZE_UNIT/2 + endX;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * LawSceneController.SIZE_UNIT/2 + endY;
        //point2
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * LawSceneController.SIZE_UNIT/2 + endX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * LawSceneController.SIZE_UNIT/2 + endY;
        
        this.arrow.getElements().add(new LineTo(x1, y1));
        this.arrow.getElements().add(new LineTo(x2, y2));
        this.arrow.getElements().add(new LineTo(endX, endY));
	}
	
	/**
	 * This function overrides this abstract method createView() of displayObject.
	 * The function initializes this.view. A force's this.view will contain
	 * 1. A name label of force (F,G,N,P,...) displayed on screen. The label is stored in this.nameLabel
	 * 2. An arrow displayed on screen.
	 */
	@Override
	public Node createView() {
		Pane container = new Pane();
		//fix the coordinate of container
		Text original = new Text(0,0,"");
		container.getChildren().add(original);
		
		//1. add name label
		this.nameLabel = new Text();				
		this.nameLabel.setText(this.getName());
		this.nameLabel.setVisible(false);
		container.getChildren().add(this.nameLabel);
		
		//2. add arrow		
		arrow = new Path();
		container.getChildren().add(arrow);
		
		//3. relocate the object
		container.relocate(this.object.getX(), this.object.getY());
		return container;
	}	

}
