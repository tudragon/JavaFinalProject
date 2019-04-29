package motion.movingobject;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import motion.Utility;
import motion.force.Force;

/**
 * Gas that follows the rocket
 * @author Nguyen Minh Tu
 *
 */
public class RocketGas extends MovingObject {
	private Rocket rocket;
	private Path paths;
	
	public RocketGas() {
	}

	public RocketGas(Pane parentPane, Rocket rocket) {
		super(parentPane, rocket.getX() ,rocket.getY() + rocket.getHeight(), 100);
		this.rocket = rocket;
	}

	@Override
	public Node createView() {
		paths = new Path();
		paths.relocate(this.getX(), this.getY());
		return paths;
	}

	@Override
	protected void setInitWidthHeight() {
		//no width, height
	}
	
	@Override
	public void update(double elapsedSeconds) {
		//bind rocket's gas to base of rocket
		this.setX(this.rocket.getX());
		this.setY(this.rocket.getY() + this.rocket.getHeight());
		this.paths.relocate(this.getX(), this.getY());
		
		//redraw lines
		//1. get first force acting on RocketGas (=f_gas)
		Force f_gas;
		
		try {
			f_gas = this.forces.get(0);
			//found f_gas
			double forceGasYVector = f_gas.getForceVector().getY();
			
			//2. clear path
			this.paths.getElements().clear();
			
			//check if vector is 0
			if(forceGasYVector == 0) return;
			
			//3. random
			int random;
			
			//4. redraw
			for (int i = 0; i < this.rocket.getWidth(); i+=5) {
				random = (int)(Math.random() * 10 + 1);
				Utility.drawSmallLine(i, 0, i, forceGasYVector + random, paths);				
			}
		} catch(IndexOutOfBoundsException e) {
			//no f_gas yet
		}
	}

}
