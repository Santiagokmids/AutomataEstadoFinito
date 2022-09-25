package ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.FiniteStateMachine;

public class FiniteStateMachineGUI {
	
	public FiniteStateMachine finiteStateMachine;
	
	private Stage window;
	
	@FXML
    private Button btnMealy;

    @FXML
    private Button btnMoore;

    @FXML
    private Button btnOutput;

    @FXML
    private Label labelMachine;

    @FXML
    private TextField txtInputs;

    @FXML
    private TextField txtStates;

	@FXML
    private BorderPane mainPane;
	
	public FiniteStateMachineGUI(FiniteStateMachine finiteStateMachine, Stage stage) {
		this.finiteStateMachine = finiteStateMachine;
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
	
	@FXML
    void labelToMealy(ActionEvent event) {
		labelMachine.setText("Autómata de Mealy");
    }

    @FXML
    void labelToMoore(ActionEvent event) {
    	labelMachine.setText("Autómata de Moore");
    }
    
    @FXML
    void goToInputs(ActionEvent event) {
    	boolean checkText = finiteStateMachine.getAtributes(txtStates.getText(), txtInputs.getText());
    	
    	if (!checkText) {
			//cambio de ventana
		} else {
			//alerta
		}
    }


}
