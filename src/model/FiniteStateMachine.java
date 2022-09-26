package model;

public class FiniteStateMachine {
	
	public FiniteStateMachine() {
	}
	
	public boolean createMealyMachine() {
		return false;
	}
	
	public boolean createMooreMachine() {
		return false;
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
