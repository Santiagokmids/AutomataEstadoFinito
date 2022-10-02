package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.FiniteStateMachine;
import model.States;

public class FiniteStateMachineGUI {

	public FiniteStateMachine finiteStateMachine;

	public ArrayList<TextField> textFields = new ArrayList<>();
	public Hashtable<String, String> contenedorHashtable = new Hashtable<>();

	@FXML	
	private VBox table;

	@FXML
	private ImageView imgBackground;

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

	public FiniteStateMachineGUI(FiniteStateMachine finiteStateMachine) {
		this.finiteStateMachine = finiteStateMachine;
	}

	@FXML
	public void loadBanner() throws IOException {
		
		this.finiteStateMachine = new FiniteStateMachine();
		this.textFields = new ArrayList<>();
		this.contenedorHashtable = new Hashtable<>();
		machine = -1;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("startProgram.fxml"));

		loader.setController(this);
		Parent load = loader.load();

		Image img = new Image("/img/background.png");
		imgBackground.setImage(img);

		mainPane.getChildren().clear();
		mainPane.setTop(load);
	}

	@FXML
	public void labelToMealy(ActionEvent event) {
		labelMachine.setText("Autómata de Mealy");
		machine = FiniteStateMachine.MEALY;
	}

	@FXML
	public void labelToMoore(ActionEvent event) {
		labelMachine.setText("Autómata de Moore");
		machine = FiniteStateMachine.MOORE;
	}

	@FXML
	public void goToInputs(ActionEvent event) throws IOException {
		String state = txtStates.getText();
		String input = txtInputs.getText();

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("No se puede continuar");

		boolean verify = true;

		if (state.isEmpty() || input.isEmpty()) {
			alert.setContentText("Debe ingresar los valores antes de continuar");
			alert.showAndWait();

		}else if(!state.isEmpty() && !input.isEmpty() && machine >= 0) {
			verify = finiteStateMachine.getAttributes(state, input);

			if (!verify) {
				table(finiteStateMachine.getQ(), finiteStateMachine.getS());

			}else {
				alert.setContentText("Los estados no pueden empezar con un valor numérico");
				alert.showAndWait();
			}


		} else if(machine < 0) {
			alert.setContentText("Debe seleccionar un autómata antes de continuar");
			alert.showAndWait();
		}

		txtStates.setText("");
		txtInputs.setText("");
	}

	@FXML
	public void table(ArrayList<String> states, ArrayList<String> inputs) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("table.fxml"));

		loader.setController(this);
		Parent load = loader.load();

		Image img = new Image("/img/background.png");
		imgBackground.setImage(img);

		mainPane.getChildren().clear();
		mainPane.setTop(load);

		createTable(states, inputs);
	}

	@FXML
	public void createTable(ArrayList<String> states, ArrayList<String> inputs) {

		GridPane gridPane = new GridPane();
		int stateIndex = 0;
		int output = 1;

		if (machine == FiniteStateMachine.MOORE) {
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
					textField.setOpacity(0.6);
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
	public void createNewMachine(ArrayList<Hashtable<String, States>> partitioning) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("finalMachine.fxml"));

		loader.setController(this);
		Parent load = loader.load();

		Image img = new Image("/img/background.png");
		imgBackground.setImage(img);

		mainPane.getChildren().clear();
		mainPane.setTop(load);

		newPartitioning(partitioning);
	}

	@FXML
	public void getValues(ActionEvent event) throws IOException {

		if (machine == FiniteStateMachine.MEALY) {

			for (int i = 0; i < textFields.size(); i++) {
				if (!textFields.get(i).getText().isEmpty()) {
					contenedorHashtable.put(textFields.get(i).getId(), textFields.get(i).getText());

				} else {
					contenedorHashtable.put(textFields.get(i).getId(), "vacio,vacio");
				}
			}
			finiteStateMachine.nodeMealy(contenedorHashtable);

		}else if (machine == FiniteStateMachine.MOORE) {

			for (int i = 0; i < textFields.size(); i++) {
				if (!textFields.get(i).getText().isEmpty()) {
					contenedorHashtable.put(textFields.get(i).getId(), textFields.get(i).getText());		
				}else {
					contenedorHashtable.put(textFields.get(i).getId(), "vacio");
				}
			}
			finiteStateMachine.nodeMoore(contenedorHashtable);
		}

		ArrayList<States> states = finiteStateMachine.searchConnected();

		ArrayList<Hashtable<String, States>> partitioning = finiteStateMachine.partitioning(states);

		partitioning = assignNewNames(partitioning);

		createNewMachine(partitioning);

	}

	public ArrayList<Hashtable<String,States>> assignNewNames(ArrayList<Hashtable<String,States>> partitioning){

		for (int i = 0; i < partitioning.size(); i++) {
			String name = "{";

			Enumeration<States> contenedor = partitioning.get(i).elements();
			States statesPartitioning = (States) contenedor.nextElement();

			name += statesPartitioning.getState();

			while (contenedor.hasMoreElements()) {
				statesPartitioning = (States) contenedor.nextElement();
				name += ", "+statesPartitioning.getState();
			}
			name += "}";

			Enumeration<States> assignNames = partitioning.get(i).elements();

			while (assignNames.hasMoreElements()) {
				States stateForChange = (States) assignNames.nextElement();
				stateForChange.setState(name);
			}
		}
		return partitioning;
	}

	public void newPartitioning(ArrayList<Hashtable<String,States>> partitioning) {
		GridPane gridPane = new GridPane();
		int output = 1;
		int cont = 0;
		char newBlocks= 'A';

		ArrayList<String> inputs = finiteStateMachine.getS();

		if (machine == FiniteStateMachine.MOORE) {
			output = 2;
		}

		for (int i = 0; i < partitioning.size()+1; i++) {

			Enumeration<States> states = partitioning.get(cont).elements();
			Enumeration<States> endStates = partitioning.get(cont).elements();
			States outputState = null;
			
			int inputIndex = 0;
			for (int j = 0; j < inputs.size()+output+1; j++) {
				int row = i;
				int col = j;

				if (col == 1 && row == 0) {
					Label label = new Label("Q");
					gridPane.add(label, col, row);

					GridPane.setHalignment(label, HPos.CENTER);

				} else if(col == 0 && row == 0) {
					Label label = new Label("Q'");
					gridPane.add(label, col, row);

					GridPane.setHalignment(label, HPos.CENTER);
					
				}else if(col != 0 && row == 0 && j <= inputs.size()+1) {
					Label label = new Label(inputs.get(inputIndex));
					gridPane.add(label, col, row);

					GridPane.setHalignment(label, HPos.CENTER);
					inputIndex++;
				}
				else if(col != 0 && row == 0 && j > inputs.size()+1) {
					Label label = new Label("Salidas");

					gridPane.add(label, col, row);
					GridPane.setHalignment(label, HPos.CENTER);

				} else if (col == 1 && row != 0 && states.hasMoreElements() && endStates.hasMoreElements()) {
					States newStates = (States) states.nextElement();
					outputState = endStates.nextElement();
					
					TextField newInfo = new TextField(newStates.getState());
					newInfo.setEditable(false);
					newInfo.setOpacity(0.6);
					gridPane.add(newInfo, col, row);
					cont++;

				} else if(col == 0 && row != 0) {
					Label label = new Label(newBlocks+"'");
					gridPane.add(label, col, row);
					newBlocks++;
				}
				else if(row != 0 && col != 0 && col < inputs.size()+2) {
					String text;
					
					if(machine == FiniteStateMachine.MOORE) {
						text = outputState.getEndStates().get(col-2).getState();
						
					}else {
						text = outputState.getEndStates().get(col-2).getState()+", "+outputState.getOutputs().get(col-2);
					}
					
					TextField newInfo = new TextField(text);
					newInfo.setEditable(false);
					newInfo.setOpacity(0.6);
					gridPane.add(newInfo, col, row);
					
				}else if(row != 0 && col != 0 && col >= inputs.size()+2) {
					TextField newInfo = new TextField(outputState.getOutputs().get(0));
					newInfo.setEditable(false);
					newInfo.setOpacity(0.6);
					gridPane.add(newInfo, col, row);
				}
			}
		}
		gridPane.setAlignment(Pos.CENTER);
		table.getChildren().add(gridPane);
	}
}
