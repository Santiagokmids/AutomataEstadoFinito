package model;

import java.util.ArrayList;

public class States {
	
	private String state;
	private ArrayList<String> inputs;
	private ArrayList<String> outputs;
	private ArrayList<States> endStates;
	private boolean isVisited; 
	
	public States(String state) {
		this.setState(state);
		inputs = new ArrayList<>();
		outputs = new ArrayList<>();
		endStates = new ArrayList<>();
		setVisited(false);
	}
	
	public void addInputs(String input) {
		inputs.add(input);
	}
	
	public void addOutputs(String output) {
		outputs.add(output);
	}
	
	public void addEndStates(States state) {
		endStates.add(state);
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

	public ArrayList<States> getEndStates() {
		return endStates;
	}

	public void setEndStates(ArrayList<States> endStates) {
		this.endStates = endStates;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
	
}
