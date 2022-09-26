package ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.FiniteStateMachine;

public class FiniteStateMachineGUI {
	
	public FiniteStateMachine finiteStateMachine;
	
	@FXML	
	private VBox table;
	
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
	
	public int machine; 
	
	public FiniteStateMachineGUI(FiniteStateMachine finiteStateMachine, Stage stage) {
		this.finiteStateMachine = finiteStateMachine;
		machine = -1;
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
   public void labelToMealy(ActionEvent event) {
		labelMachine.setText("Autómata de Mealy");
		machine = 0;
    }

    @FXML
    public void labelToMoore(ActionEvent event) {
    	labelMachine.setText("Autómata de Moore");
    	machine = 1;
    }
    
    @FXML
    public void goToInputs(ActionEvent event) throws IOException {
    	String state = txtStates.getText();
		String input = txtInputs.getText();
		
		boolean verify = finiteStateMachine.getAtributes(state, input);
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("No se puede continuar");
		
		if(!verify && machine >= 0) {
			table();
			
		} else if(machine < 0) {
			alert.setContentText("Debe seleccionar un autómata antes de continuar");
			alert.showAndWait();
		}
		else {
			alert.setContentText("Los estados no pueden empezar con un valor numérico");
			alert.showAndWait();
		}
    }
    
    @FXML
    public void table() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("table.fxml"));
		
		loader.setController(this);
		Parent load = loader.load();
		mainPane.getChildren().clear();
		mainPane.setTop(load);
		
		createTable();
    }
    
    @FXML
	public void createTable() {

    	GridPane gridPane = new GridPane();
    	gridPane.add(new TextField(), 0, 0);
    	gridPane.add(new TextField(), 0, 1);
    	gridPane.add(new TextField(), 0, 2);
    	gridPane.add(new TextField(), 0, 3);
    	gridPane.add(new TextField(), 0, 4);
    	gridPane.add(new TextField(), 0, 5);
    	gridPane.add(new TextField(), 0, 6);
    	gridPane.add(new TextField(), 0, 7);
    	gridPane.add(new TextField(), 0, 8);
    	gridPane.add(new TextField(), 1, 0);
    	gridPane.add(new TextField(), 1, 1);
    	gridPane.add(new TextField(), 1, 2);
    	gridPane.add(new TextField(), 1, 3);
    	gridPane.add(new TextField(), 1, 4);
    	gridPane.add(new TextField(), 2, 0);
    	gridPane.add(new TextField(), 3, 0);
    	gridPane.add(new TextField(), 4, 0);
    	gridPane.add(new TextField(), 5, 0);
    	gridPane.add(new TextField(), 6, 0);
    	gridPane.add(new TextField(), 7, 0);
    	gridPane.add(new TextField(), 8, 0);
    	gridPane.add(new TextField(), 9, 0);

    	
    	table.getChildren().add(gridPane);
    }
}
