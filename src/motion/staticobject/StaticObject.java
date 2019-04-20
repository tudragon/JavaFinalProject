package motion.staticobject;

import javafx.scene.layout.Pane;
import motion.DisplayObject;
import motion.Vector2D;

public abstract class StaticObject extends DisplayObject {

	public StaticObject() {
	}

	public StaticObject(Pane parentPane, Vector2D location) {
		super(parentPane, location);
		
	}	
	
	public StaticObject(Pane parentPane, double x, double y) {
		super(parentPane, x, y);		
	}
	
	@Override
	public void update(double elapsedSeconds) {
		//do nothing due to no movement
		this.view.relocate(this.getX(), this.getY());

	}

}
