package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import motion.DisplayObject;
import motion.staticobject.Block;

public class LawTwoController extends LawSceneController {
	/**Canvas with object Pane*/
	@FXML
	private Pane lawTwoPane;
	
	public LawTwoController() {
	}
	
	/** Set up camera in the pane of this scene */
	@Override
	protected void setupCameraPane() {		
		Rectangle clip = new Rectangle(0,0, 600, 200); 
		lawTwoPane.setClip(clip);
	}
	/** Initialize UI elements*/
	@Override
	protected void initPane() {
		//create blocks
        int numBlocks = 1000;
        
        for (int i = 0; i < numBlocks * SIZE_UNIT; i+= SIZE_UNIT) {
			DisplayObject block = new Block(lawTwoPane, i, SIZE_UNIT*3, SIZE_UNIT);
			allDisplayObjects.add(block);			
		}
		
	}

}
