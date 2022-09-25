package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.AutomataEstadoFinito;

public class AutomataEstadoFinitoGUI {
	
	public AutomataEstadoFinito automataEstadoFinito;
	
	private Stage window;
	
	@FXML
    private BorderPane mainPane;
	
	public AutomataEstadoFinitoGUI(AutomataEstadoFinito automataEstadoFinito, Stage stage) {
		this.automataEstadoFinito = automataEstadoFinito;
		this.window = stage;
	}
	
	@FXML
	public void loadBanner() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("startProgram.fxml"));
		
		loader.setController(this);
		Parent load = loader.load();
		mainPane.getChildren().clear();
		mainPane.setTop(load);
	}
}
