package model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class FiniteStateMachine {

	public static final int MEALY = 0;
	public static final int MOORE = 1;

	private ArrayList<String> Q;
	private ArrayList<String> S;
	private Hashtable<String, States> node;

	public FiniteStateMachine() {
		Q = new ArrayList<>();
		S = new ArrayList<>();
		node = new Hashtable<>();
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

		createStates();
	}

	public boolean getAtributes(String states, String inputs) {
		String[] newStates = states.split(",");
		String[] newInputs = inputs.split(",");

		boolean verify = verifyStates(newStates, newInputs);

		return verify;
	}

	public void nodeMealy(Hashtable<String, String> hasdNode) {
		Enumeration<String> enumeration = hasdNode.elements();
		Enumeration<String> keysEnumeration = hasdNode.keys();

		while (enumeration.hasMoreElements()) {
			String[] outputs = ((String) enumeration.nextElement()).split(",");
			String[] keys = ((String) keysEnumeration.nextElement()).split("");

			States nodeState = node.get(Q.get(Integer.parseInt(keys[1])-1));

			nodeState.addInputs(S.get(Integer.parseInt(keys[0])-1));
			nodeState.addOutputs(outputs[1]);
			nodeState.addEndStates(node.get(outputs[0]));
		}
	}

	public void nodeMoore(Hashtable<String, String> hasdNode) {
		Enumeration<String> enumeration = hasdNode.elements();
		Enumeration<String> keysEnumeration = hasdNode.keys();
		
		while (enumeration.hasMoreElements()) {
			
			String output = (String) enumeration.nextElement();
			String[] keys = ((String) keysEnumeration.nextElement()).split("");
			
			int key = Integer.parseInt(keys[0]);
			int key1 = Integer.parseInt(keys[1]);
			
			if(!Character.isDigit(output.charAt(0)) && key <= S.size()) {
				
				States nodeStates = node.get(Q.get(key1-1));
				String indexNode = hasdNode.get(Integer.toString(S.size()+1)+key1);
				
				nodeStates.addInputs(S.get(key-1));
				nodeStates.addOutputs(indexNode);
				nodeStates.addEndStates(node.get(output));
			}
		}
	}

	public void createStates() {
		for (int i = 0; i < Q.size(); i++) {
			States state = new States(Q.get(i));
			node.put(Q.get(i), state);
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

	public ArrayList<States> searchConnected(){
		ArrayList<States> statesConnected = new ArrayList<>();
		
		statesConnected.add(node.get(Q.get(0)));
		statesConnected.get(0).setVisited(true);
		
		searchConnected(statesConnected, node.get(Q.get(0)));
		return statesConnected;
	}

	public void searchConnected(ArrayList<States> statesConnected, States state) {
		ArrayList<States> endStates = state.getEndStates();
		
		for(int i = 0; i < endStates.size(); i++) {

			if (endStates.get(i).isVisited() == false) {
				statesConnected.add(endStates.get(i));
				endStates.get(i).setVisited(true);
				searchConnected(statesConnected, endStates.get(i));
			}
		}
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
