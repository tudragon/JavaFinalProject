package motion;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class Utility {
	
	/**size unit of objects in panel. Default is 20 pixels*/
	public static final double SIZE_UNIT = 20;
	
	/** Default force is 10 Newton*/
	public static final int DEFAULT_FORCE = 10;
	
	/**
	 * add a line from (startX * SIZE_UNIT,startY * SIZE_UNIT) -> (endX * SIZE_UNIT ,endY * SIZE_UNIT) to path
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param path
	 */
	public static void drawLine(double startX, double startY, double endX, double endY, Path path) {
		drawSmallLine(startX * Utility.SIZE_UNIT, startY * Utility.SIZE_UNIT,
				endX * Utility.SIZE_UNIT, endY * Utility.SIZE_UNIT,
				path);
	}
	
	/**
	 * add a line from (startX ,startY) -> (endX ,endY) to path
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param path
	 */
	public static void drawSmallLine(double startX, double startY, double endX, double endY, Path path) {
		MoveTo moveTo = new MoveTo(startX, startY);
		LineTo lineTo = new LineTo(endX, endY );
		
		path.getElements().add(moveTo); 
		path.getElements().add(lineTo);
	}

}
