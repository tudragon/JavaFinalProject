package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import motion.DisplayObject;
import motion.Vector2D;
import motion.force.Force;
import motion.movingobject.MovingObject;
import motion.movingobject.Rocket;
import motion.staticobject.Block;
import motion.staticobject.RocketBase;
import motion.staticobject.StaticObject;

public class LawThreeController extends LawSceneController {
	/**Canvas with object Pane*/
	@FXML
	private Pane lawThreePane;
	Rectangle clip;
	
	/** The main object */
	private MovingObject rocket;
	
	/** Display rocket's movement*/
	@FXML
	private Label P1,P2,f_gas,f_engine,a,v;
	
	/** F_engine buttons*/
	@FXML
	private Button lawThreeBurnMoreGas, lawThreeBurnLessGas;
	
	/** F_engine*/
	private Force F_gas;

	
	public LawThreeController() {
	}

	@Override
	protected void setupCameraPane() {
		clip = new Rectangle(0,0, 600, 300);
		lawThreePane.setClip(clip);
		
	}

	@Override
	protected void initPane() {
		//create blocks: 
        int numHorizontalBlocks = 50 ;
        int numVerticalBlocks = 1000;
        int offset = 12; //block on bottom left has (x,y) = (12*SIZE_UNIT,0)
        
        for (int i = 0; i < numHorizontalBlocks * SIZE_UNIT; i+= SIZE_UNIT) {
			DisplayObject block = new Block(lawThreePane, i, SIZE_UNIT*offset, SIZE_UNIT);
			allDisplayObjects.add(block);			
		}
        
        for (int i = 0; i < numVerticalBlocks * SIZE_UNIT; i+= SIZE_UNIT) {
			DisplayObject block = new Block(lawThreePane, 0, -i + offset * SIZE_UNIT, SIZE_UNIT);
			allDisplayObjects.add(block);			
		}
		
        //rocket launcher
        StaticObject rocket_base = new RocketBase(lawThreePane, 13 * SIZE_UNIT, 
        		offset * SIZE_UNIT - 6 * SIZE_UNIT);
        //rocket
        MovingObject rocket = new Rocket(lawThreePane, 14 * SIZE_UNIT,
        		offset * SIZE_UNIT - 6 * SIZE_UNIT, 10);
        
        allDisplayObjects.add(rocket_base);
        allDisplayObjects.add(rocket);
	}

}
