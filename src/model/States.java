package model;

import java.util.ArrayList;

public class States {
	
	private String state;
	private ArrayList<String> inputs;
	private ArrayList<String> outputs;
	private ArrayList<String> endStates;
	
	public States(String state, ArrayList<String> inputs, ArrayList<String> outputs, ArrayList<String> endStates) {
		this.setState(state);
		this.setInputs(inputs);
		this.setOutputs(outputs);
		this.setEndStates(endStates);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ArrayList<String> getInputs() {
		return inputs;
	}

	public void setInputs(ArrayList<String> inputs) {
		this.inputs = inputs;
	}

	public ArrayList<String> getOutputs() {
		return outputs;
	}

	public void setOutputs(ArrayList<String> outputs) {
		this.outputs = outputs;
	}

	public ArrayList<String> getEndStates() {
		return endStates;
	}

	public void setEndStates(ArrayList<String> endStates) {
		this.endStates = endStates;
	}
	
}
