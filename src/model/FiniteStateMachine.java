package model;

import java.util.ArrayList;

public class FiniteStateMachine {
	
	public static final int MEALY = 0;
	public static final int MOORE = 1;

	private ArrayList<String> Q;
	private ArrayList<String> S;
	private ArrayList<States> mealyMachine;
	private ArrayList<States> mooreMachine;
	
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
	
	public void nodeData(ArrayList<String> data, int typeMachine) {
		if (typeMachine == MEALY) {
			
		}else if (typeMachine == MOORE) {
			
		}
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
	
	public ArrayList<States> searchConnected(int machine){
		ArrayList<States> statesConnected = new ArrayList<>();
		
		if (machine == 0) {
			searchConnected(statesConnected, mealyMachine, mealyMachine.get(0));
			
		} else if(machine == 1) {
			searchConnected(statesConnected, mooreMachine, mooreMachine.get(0));
		}
		
		return statesConnected;
	}
	
	public void searchConnected(ArrayList<States> statesConnected, ArrayList<States> machine, States state) {
		
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
