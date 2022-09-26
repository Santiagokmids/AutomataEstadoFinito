package model;

import java.util.ArrayList;

public class FiniteStateMachine {

	private ArrayList<String> Q;
	private ArrayList<String> S;
	
	public FiniteStateMachine() {
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
			S.add(states[i]);
		}
	}

	public boolean getAtributes(String states, String inputs) {
		String[] newStates = states.split(",");
		String[] newInputs = states.split(",");

		boolean verify = verifyStates(newStates);

		return verify;
	}

	public boolean verifyStates(String[] states) {
		boolean verify = false;

		for (int i = 0; i < states.length && !verify; i++) {

			if(Character.isDigit(states[i].charAt(0))) {
				verify = true;
			}
		}

		return verify;
	}
}
