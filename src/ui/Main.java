package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.FiniteStateMachine;

public class Main extends Application {
	
	private FiniteStateMachine finiteStateMachine;
	private FiniteStateMachineGUI finiteStateMachineGUI;
	
	public Main(){
		finiteStateMachine = new FiniteStateMachine();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		finiteStateMachineGUI = new FiniteStateMachineGUI(finiteStateMachine, primaryStage);
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-pane.fxml"));
		
		fxmlLoader.setController(finiteStateMachineGUI);
		
		Parent root = fxmlLoader.load();
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Autómatas de Estados Finitos");
		primaryStage.setResizable(false);
		primaryStage.show();
		finiteStateMachineGUI.loadBanner();
	}

}
