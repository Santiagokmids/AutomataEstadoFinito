package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.AutomataEstadoFinito;

public class Main extends Application {
	
	private AutomataEstadoFinito automataEstadoFinito;
	private AutomataEstadoFinitoGUI automataEstadoFinitoGUI;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		automataEstadoFinitoGUI = new AutomataEstadoFinitoGUI(automataEstadoFinito, primaryStage);
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-pane.fxml"));
		
		fxmlLoader.setController(automataEstadoFinitoGUI);
		
		Parent root = fxmlLoader.load();
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Autómatas de Estados Finitos");
		primaryStage.setResizable(false);
		primaryStage.show();
		automataEstadoFinitoGUI.loadBanner();
	}

}
