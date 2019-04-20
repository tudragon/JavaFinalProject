package motion;


import controller.LawOneController;
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
	private String name; // name of force	
	private MovingObject object;
	private Vector2D forceVector; //force magnitude
	
	//display
	private Text nameLabel;
	private Path arrow;
	
	private final int DEFAULT_FORCE = 10; //default force is 10 Newton
	
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

	@Override
	public void update(double elapsedSeconds) {
		//bound this.location to object.location
		this.view.relocate(this.object.getX(), this.object.getY());
		
		
		
		//clear arrow
		arrow.getElements().clear();
		
		double forceVectorX = this.forceVector.getX();
		double forceVectorY = this.forceVector.getY();
		//only draw if force != 0
		if((forceVectorX != 0) || (forceVectorY != 0)) {
			//update arrow
			drawArrow(LawOneController.SIZE_UNIT,LawOneController.SIZE_UNIT, 
					forceVectorX*LawOneController.SIZE_UNIT + LawOneController.SIZE_UNIT,
					forceVectorY*LawOneController.SIZE_UNIT + LawOneController.SIZE_UNIT);
			
			//update label
			nameLabel.setX(forceVectorX*LawOneController.SIZE_UNIT + LawOneController.SIZE_UNIT);
			nameLabel.setY(forceVectorY*LawOneController.SIZE_UNIT + LawOneController.SIZE_UNIT);			
			nameLabel.setVisible(true);
		} else {
			nameLabel.setVisible(false);
		}	
	}
	
	/**
	 * Re-add those elements to arrow
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
        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * LawOneController.SIZE_UNIT/2 + endX;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * LawOneController.SIZE_UNIT/2 + endY;
        //point2
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * LawOneController.SIZE_UNIT/2 + endX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * LawOneController.SIZE_UNIT/2 + endY;
        
        this.arrow.getElements().add(new LineTo(x1, y1));
        this.arrow.getElements().add(new LineTo(x2, y2));
        this.arrow.getElements().add(new LineTo(endX, endY));
	}

	@Override
	public Node createView() {
		// TODO create arrow & name
		Pane container = new Pane();
		//fix the coordinate of container
		Text original = new Text(0,0,"");
		container.getChildren().add(original);
		
		//add text
		nameLabel = new Text();				
		nameLabel.setText(this.getName());
		nameLabel.setVisible(false);
		container.getChildren().add(nameLabel);
		
		//add arrow		
		arrow = new Path();
		container.getChildren().add(arrow);
		
		container.relocate(this.object.getX(), this.object.getY());
		return container;
	}	

}
