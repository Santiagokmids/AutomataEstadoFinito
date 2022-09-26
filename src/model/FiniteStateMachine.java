package model;

import java.util.ArrayList;

public class FiniteStateMachine {

	private ArrayList<String> Q;
	private ArrayList<String> S;
	
	public FiniteStateMachine() {
		Q = new ArrayList<>();
		S = new ArrayList<>();
	}

	public boolean createMealyMachine() {
		return false;
	}

	public boolean createMooreMachine() {
		return false;
	}
	
	public void assignAtributes(String[] states, String[] inputs) {
		for(int i = 0; i < states.length; i++) {
			Q.add(states[i]);
		}
		
		for(int i = 0; i < inputs.length; i++) {
			S.add(inputs[i]);
		}
	}

	public boolean getAtributes(String states, String inputs) {
		String[] newStates = states.split(",");
		String[] newInputs = inputs.split(",");

		boolean verify = verifyStates(newStates, newInputs);

		return verify;
	}

	public boolean verifyStates(String[] states, String[] inputs) {
		boolean verify = false;

		for (int i = 0; i < states.length && !verify; i++) {

			if(Character.isDigit(states[i].charAt(0))) {
				verify = true;
			}
		}
		
		if(!verify) {
			assignAtributes(states, inputs);
		}

		return verify;
	}
	
	public ArrayList<String> getS() {
		return S;
	}

	public void setS(ArrayList<String> S) {
		this.S = S;
	}

	public ArrayList<String> getQ() {
		return Q;
	}

	public void setQ(ArrayList<String> Q) {
		this.Q = Q;
	}
}
