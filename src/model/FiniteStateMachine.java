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
	private ArrayList<States> statesConnecteds;

	public FiniteStateMachine() {
		Q = new ArrayList<>();
		S = new ArrayList<>();
		node = new Hashtable<>();
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
		for (int i = 1; i < S.size()+1; i++) {
			Enumeration<String> enumeration = hasdNode.elements();
			Enumeration<String> keysEnumeration = hasdNode.keys();

			while (enumeration.hasMoreElements()) {
				States nodeState = new States(null);
				String input = "";

				String[] outputs = ((String) enumeration.nextElement()).split(",");
				String[] keys = ((String) keysEnumeration.nextElement()).split("");
				int key = -1;

				if (keys.length >= 3 && Q.size() > 9) {
					nodeState = node.get(Q.get(Integer.parseInt(keys[1]+keys[2])-1));
					input = S.get(Integer.parseInt(keys[0])-1);
					key = Integer.parseInt(keys[0]);
				}else if (keys.length >= 3 && S.size() > 9) {
					nodeState = node.get(Q.get(Integer.parseInt(keys[2])-1));
					input = S.get(Integer.parseInt(keys[0]+keys[1])-1);
					key = Integer.parseInt(keys[0]+keys[1]);
				}else {
					nodeState = node.get(Q.get(Integer.parseInt(keys[1])-1));
					key = Integer.parseInt(keys[0]);
					input = S.get(Integer.parseInt(keys[0])-1);
				}

				if(key == i && !outputs[1].equals("vacio") && !outputs[0].equals("vacio")) {
					nodeState.addInputs(input);
					nodeState.addOutputs(outputs[1]);
					nodeState.addEndStates(node.get(outputs[0]));

				}else if(key == i && outputs[1].equals("vacio") && outputs[0].equals("vacio")) {
					nodeState.addOutputs("vacio");
					nodeState.addEndStates(new States("vacio"));
				}
			}
		}


	}

	public void nodeMoore(Hashtable<String, String> hasdNode) {
		for (int i = 1; i < S.size()+1; i++) {
			Enumeration<String> enumeration = hasdNode.elements();
			Enumeration<String> keysEnumeration = hasdNode.keys();

			while (enumeration.hasMoreElements()) {

				States nodeState = new States(null);
				String input = "";

				String output = (String) enumeration.nextElement();
				String[] keys = ((String) keysEnumeration.nextElement()).split("");
				int key = -1;
				int key1 = -1;

				if(!Character.isDigit(output.charAt(0)) && ((S.size() <= 9 && Integer.parseInt(keys[0]) <= S.size()) || (S.size() > 9 && Integer.parseInt(keys[0]+keys[1]) <= S.size()))) {
					if (keys.length >= 3 && Q.size() > 9) {
						nodeState = node.get(Q.get(Integer.parseInt(keys[1]+keys[2])-1));
						input = S.get(Integer.parseInt(keys[0])-1);
						key = Integer.parseInt(keys[0]);
						key1 =  Integer.parseInt(keys[1]+keys[2]);
					}else if (keys.length >= 3 && S.size() > 9) {
						nodeState = node.get(Q.get(Integer.parseInt(keys[2])-1));
						input = S.get(Integer.parseInt(keys[0]+keys[1])-1);
						key = Integer.parseInt(keys[0]+keys[1]);
						key1 =  Integer.parseInt(keys[2]);
					}else {
						nodeState = node.get(Q.get(Integer.parseInt(keys[1])-1));
						key = Integer.parseInt(keys[0]);
						key1 =  Integer.parseInt(keys[1]);
						input = S.get(key-1);
					}

					if(key == i && !output.equals("vacio")) {
						String indexNode = hasdNode.get(Integer.toString(S.size()+1)+key1);
						nodeState.addInputs(input);
						nodeState.addOutputs(indexNode);
						nodeState.addEndStates(node.get(output));
					}else if(key == i && output.equals("vacio")){
						nodeState.addOutputs("vacio");
						nodeState.addEndStates(new States("vacio"));
					}
				}
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

	@SuppressWarnings("unchecked")
	public ArrayList<States> searchConnected(){
		ArrayList<States> statesConnected = new ArrayList<>();

		statesConnected.add(node.get(Q.get(0)));
		statesConnected.get(0).setVisited(true);

		searchConnected(statesConnected, node.get(Q.get(0)));

		statesConnecteds = (ArrayList<States>) statesConnected.clone();

		return statesConnected;
	}

	public void searchConnected(ArrayList<States> statesConnected, States state) {
		ArrayList<States> endStates = state.getEndStates();

		for(int i = 0; i < endStates.size(); i++) {

			if (!endStates.get(i).getState().equals("vacio") && endStates.get(i).isVisited() == false) {
				statesConnected.add(endStates.get(i));
				endStates.get(i).setVisited(true);
				searchConnected(statesConnected, endStates.get(i));
			}
		}
	}

	public ArrayList<Hashtable<String, States>> partitioning(ArrayList<States> states){

		ArrayList<Hashtable<String, States>> partitioning = new ArrayList<>();

		partitioning = searchFirstBlock(partitioning, states);

		ArrayList<Hashtable<String, States>> newPartitioning = new ArrayList<>();

		partitioning = partitionings(partitioning, newPartitioning);

		return partitioning;
	}

	public ArrayList<Hashtable<String, States>> partitionings(ArrayList<Hashtable<String, States>> partitioning, ArrayList<Hashtable<String, States>> newPartitioning){

		for (int i = 0; i < partitioning.size(); i++) {
			Enumeration<States> state = partitioning.get(i).elements();
			States nextElement = state.nextElement();
			newPartitioning = secondPartitioning(partitioning, newPartitioning, partitioning.get(i), nextElement);
		}

		if (!partitioning.equals(newPartitioning)) {
			ArrayList<Hashtable<String, States>> newCurrent = new ArrayList<>();
			newPartitioning = partitionings(newPartitioning, newCurrent);
		}

		return newPartitioning;
	}

	public ArrayList<Hashtable<String, States>> secondPartitioning(ArrayList<Hashtable<String, States>> current, ArrayList<Hashtable<String, States>> newPartitioning,  Hashtable<String, States> block, States first) {

		Hashtable<String, States> currentBlock = new Hashtable<>();
		Hashtable<String, States> differentBlock = new Hashtable<>();

		if (block.size() == 1) {
			currentBlock.put(first.getState(), first);
			newPartitioning.add(currentBlock);
		}else if (block.size() > 1) {
			currentBlock.put(first.getState(), first);

			Enumeration<States> sectionBLock = block.elements();

			while (sectionBLock.hasMoreElements()) {
				
				boolean founded = true;
				States states = (States) sectionBLock.nextElement();
				if (!first.getState().equalsIgnoreCase(states.getState())) {
					for (int i = 0; i < first.getEndStates().size() && founded; i++) {
						founded = searchStateInBlock(current, first.getEndStates().get(i).getState(), states.getEndStates().get(i).getState());
					}
					if (founded) {
						currentBlock.put(states.getState(), states);
					} else {
						differentBlock.put(states.getState(), states);
					}
				}
			}

			newPartitioning.add(currentBlock);
			Enumeration<States> newFirst = differentBlock.elements();

			if (newFirst.hasMoreElements()) {
				States different = newFirst.nextElement();
				secondPartitioning(current, newPartitioning, differentBlock, different);
			}
		}

		return newPartitioning;
	}

	private boolean searchStateInBlock(ArrayList<Hashtable<String, States>> partitioning, String first, String second) {

		boolean founded = false;

		for (int i = 0; i < partitioning.size() && !founded; i++) {
			
			if (partitioning.get(i).get(first) != null && partitioning.get(i).get(second) != null) {
				founded = true;
				
			}else if (partitioning.get(i).get(first) == null && partitioning.get(i).get(second) != null) {
				i = partitioning.size();
				
			}else if (partitioning.get(i).get(first) != null && partitioning.get(i).get(second) == null) {
				i = partitioning.size();
				
			}else if (partitioning.get(i).get(first) == null && partitioning.get(i).get(second) == null && first.equals("vacio") && second.equals("vacio")) {
				founded = true;
			}
		}
		return founded;
	}

	public ArrayList<Hashtable<String, States>> searchFirstBlock(ArrayList<Hashtable<String, States>> partitioning, ArrayList<States> states){

		Hashtable<String, States> firstBlock = new Hashtable<>();

		if(states.size() == 1) {
			firstBlock.put(states.get(0).getState(), states.get(0));
			partitioning.add(firstBlock);

		}else if(states.size() > 1) {
			States first = states.get(0);
			firstBlock.put(first.getState(), first);

			for(int i = 1; i < states.size(); i++) {

				if(first.getOutputs().equals(states.get(i).getOutputs())) {
					firstBlock.put(states.get(i).getState(), states.get(i));
					states.remove(states.get(i));
					i--;
				}
			}

			partitioning.add(firstBlock);
			states.remove(0);
			searchFirstBlock(partitioning, states);
		}

		return partitioning;
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

	public ArrayList<States> getStatesConnecteds() {
		return statesConnecteds;
	}

	public void setStatesConnecteds(ArrayList<States> statesConnecteds) {
		this.statesConnecteds = statesConnecteds;
	}
}
