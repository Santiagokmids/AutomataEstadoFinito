package ui;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	
	public FiniteStateMachineGUI(FiniteStateMachine finiteStateMachine, Stage stage) {
		this.finiteStateMachine = finiteStateMachine;
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
    }

    @FXML
    public void labelToMoore(ActionEvent event) {
    	labelMachine.setText("Autómata de Moore");
    }
    
    @FXML
    public void goToInputs(ActionEvent event) throws IOException {
    	String state = txtStates.getText();
		String input = txtInputs.getText();
		table();
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
	public void createTable(ArrayList<String> states, ArrayList<String> inputs) {

    	GridPane gridPane = new GridPane();
    	int stateIndex = 0;
    	
    	for (int i = 0; i < states.size(); i++) {
    		for (int j = 0; j < inputs.size(); j++) {
    			int row = i;
        		int col = j;
        		int inputIndex = 0;
        		
        		if (col != 0 && row == 0) {
    				Label label = new Label(inputs.get(inputIndex));
    				label.setId("label"+col+row);
    				gridPane.add(label, col, row);
    				inputIndex++;
    			}else if (col == 0 && row != 0) {
    				Label label = new Label(states.get(stateIndex));
    				label.setId("label"+col+row);
    				gridPane.add(label, col, row);
    				stateIndex++;
				} else if(row != 0 && col != 0) {
        			TextField textField = new TextField();
        			textField.setId("field"+col+row);
            		gridPane.add(textField, col, row);
        		}
			}
		}
    	table.getChildren().add(gridPane);
    }


}
