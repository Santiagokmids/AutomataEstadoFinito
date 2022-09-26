package ui;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
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
	
	public ArrayList<TextField> textFields = new ArrayList<>();
	
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
			table(finiteStateMachine.getQ(), finiteStateMachine.getS());
			
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
    public void table(ArrayList<String> states, ArrayList<String> inputs) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("table.fxml"));
		
		loader.setController(this);
		Parent load = loader.load();
		mainPane.getChildren().clear();
		mainPane.setTop(load);
		
		createTable(states, inputs);
    }
    
    @FXML
	public void createTable(ArrayList<String> states, ArrayList<String> inputs) {

    	GridPane gridPane = new GridPane();
    	int stateIndex = 0;
    	int output = 1;
    	
    	if (machine == 1) {
			output = 2;
		}
    	
    	for (int i = 0; i < states.size()+1; i++) {
    		int inputIndex = 0;
    		for (int j = 0; j < inputs.size()+output; j++) {
    			int row = i;
        		int col = j;
        		
        		if (col != 0 && row == 0 && j <= inputs.size()) {
    				Label label = new Label(inputs.get(inputIndex));
    				label.setId(col+""+row);
    				gridPane.add(label, col, row);
    				GridPane.setHalignment(label, HPos.CENTER);
    				inputIndex++;
    			}else if(col != 0 && row == 0 && j > inputs.size()) {
    				Label label = new Label("Salidas");
    				label.setId(col+""+row);
    				gridPane.add(label, col, row);
    				GridPane.setHalignment(label, HPos.CENTER);
    			} else if (col == 0 && row != 0) {
    				Label label = new Label(states.get(stateIndex));
    				label.setId(col+""+row);
    				gridPane.add(label, col, row);
    				stateIndex++;
				} else if(row != 0 && col != 0) {
        			TextField textField = new TextField();
        			textField.setId(col+""+row);
            		gridPane.add(textField, col, row);
            		textFields.add(textField);
        		}
			}
		}
    	gridPane.setAlignment(Pos.CENTER);
    	table.getChildren().add(gridPane);
    }
    
    @FXML
    public void getValues(ActionEvent event) {
    	ArrayList<String> valueStrings = new ArrayList<>();
    	for (int i = 0; i < textFields.size(); i++) {
			valueStrings.add(textFields.get(i).getText());
		}
    }
}
