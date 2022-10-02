package model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * This class contains methods, attributes,  and relations related with a finite state machine.
 * @version 1
 * @author Santiago Trochez Velasco, https://github.com/Santiagokmids <br>
 * @author Luis Miguel Ossa Arias, https://github.com/Itsumohitoride <br>
 */

public class FiniteStateMachine {

	public static final int MEALY = 0;
	public static final int MOORE = 1;

	private ArrayList<String> Q;
	private ArrayList<String> S;
	private Hashtable<String, States> node;
	private ArrayList<States> statesConnecteds;
	
	/**
	 * <b>name:</b> FiniteStateMachine <br>
	 * Create an object finite state machine. <br>
	 * <b>post:</b> An object finite state machine has created. <br>
	 */

	public FiniteStateMachine() {
		Q = new ArrayList<>();
		S = new ArrayList<>();
		node = new Hashtable<>();
	}
	
	/**
	 * <b>name:</b> assignAtributes <br>
	 * Assign states and inputs in arraylist. <br>
	 * <b>post:</b> The states and inputs from the ui information has been assign into the system. <br>
	 * @param states is the array with all the states of the finite state machine.
	 * @param inputs is the array with all the inputs of the finite state machine.
	 */

	public void assignAtributes(String[] states, String[] inputs) {
		for(int i = 0; i < states.length; i++) {
			Q.add(states[i]);
		}

		for(int i = 0; i < inputs.length; i++) {
			S.add(inputs[i]);
		}

		createStates();
	}
	
	/**
	 * <b>name:</b> getAtributes <br>
	 * Get the attributes states and inputs. Furthermore, the information is split by a comma. <br>
	 * <b>post:</b> The states and inputs was split by a comma. <br>
	 * @param states is the string with all the states of the finite state machine.
	 * @param inputs is the string with all the inputs of the finite state machine.
	 * @return <code>verify</code> is the result of verify if the states name don't start with a number.
	 */

	public boolean getAttributes(String states, String inputs) {
		String[] newStates = states.split(",");
		String[] newInputs = inputs.split(",");

		boolean verify = verifyStates(newStates, newInputs);

		return verify;
	}
	
	/**
	 * <b>name:</b> createStates <br>
	 * Add the states into the system, only a name in this case. <br>
	 * <b>post:</b> The states of the finite state machine were aggregated. <br> 
	 */
	
	public void createStates() {
		for (int i = 0; i < Q.size(); i++) {
			States state = new States(Q.get(i));
			node.put(Q.get(i), state);
		}
	}
	
	/**
	 * <b>name:</b> verifyStates <br>
	 * Verify is the name's states start without a number. <br>
	 * <b>post:</br> The name of all the states was verified. <br>
	 * @param states is the array with the name of all states for the finite state machine.
	 * @param inputs is the array with the name of all inputs for the finite state machine.
	 * @return <code>verify</code> is the verification of all the state's name.
	 */

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
	
	/**
	 * <b>name:</b> nodeMealy
	 * Create a node of mealy machine. <br>
	 * <b>post:</b> A node of a mealy machine was created. <br>
	 * @param hasdNode is the information of the machine recollected from the user's information.
	 */

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
	
	/**
	 * <b>name:</b> nodeMoore <br>
	 * Create a node of moore machine. <br>
	 * <b>post:</b> A node of a moore machine was created. <br>
	 * @param hasdNode is the information of the machine recollected from the user's information.
	 */

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

	/**
	 * <b>name:</br> searchConnected <br>
	 * Search all the states that are accessible from the initial state. <br>
	 * <b>post:</br> The search of all the states that are accessible from the initial state was made. <br>
	 */

	@SuppressWarnings("unchecked")
	public ArrayList<States> searchConnected(){
		ArrayList<States> statesConnected = new ArrayList<>();

		statesConnected.add(node.get(Q.get(0)));
		statesConnected.get(0).setVisited(true);

		searchConnected(statesConnected, node.get(Q.get(0)));

		statesConnecteds = (ArrayList<States>) statesConnected.clone();

		return statesConnected;
	}
	
	/**
	 * <b>name:</b> searchConnected <br>
	 * Search all the states that are accessible from the initial state. This method is executed recursively. <br>
	 * <b>post:</b> All the states that are accessible from the initial state was searched recursively. <br>
	 * @param statesConnected are the states accessible from the initial state.
	 * @param state is the actual node that play the role of the initial state.
	 */

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
	
	/**
	 * <b>name:</b> partitioning <br>
	 * In this method the partitioning of the finite state machine into a minimum equivalent machine is developed. <br>
	 * <b>post:</b> the partitioning of the finite state machine into a minimum equivalent machines was developed. <br>
	 * @param states is the arraylist of all the state's machine.
	 * @return <code>partitioning</code> is the current partitioning of the finite state machine.
	 */

	public ArrayList<Hashtable<String, States>> partitioning(ArrayList<States> states){

		ArrayList<Hashtable<String, States>> partitioning = new ArrayList<>();

		partitioning = searchFirstBlock(partitioning, states);

		ArrayList<Hashtable<String, States>> newPartitioning = new ArrayList<>();

		partitioning = partitionings(partitioning, newPartitioning);

		return partitioning;
	}
	
	/** 
	 *<b>name:</b>  partitionings.
	 *Do the partitioning after doing the first partitioning of recursive way.<br>
	 *@param partitioning. This is the partitioning made in searchFirstBlock method.
	 *@param newPartitioning. This is the newPartitioning that will be assigned.
	 *<b> post: </b> The new partitioning assigned.
	 *@return newPartitioning. This is a ArrayList of Hashtable where we have the last step of partitioning.
	 */
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
	
	/** 
	 *<b>name:</b>  secondPartitioning.
	 *Do the partitioning after doing the first partitioning.<br>
	 *@param current. That is the ArrayList of Hashtable that have the last step of partitioing. 
	 *@param newPartitioning. That is the new ArrayList of Hashtable after of doing the partitioning.
	 *@param block. That is a block where we have a states that havn't been partitioned yet. 
	 *@param first. That is the first state of the block.
	 *<b> post: </b> The new partitioning.
	 *@return newPartitioning. This is a ArrayList of Hashtable where we have the last step of partitioning.
	 */
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
	
	/** 
	 *<b>name:</b>  searchStateInBlock.
	 *Search one state in a block of partitioning.<br>
	 *@param partitioning. This is the ArrayList of Hastable where for we have the blocks of partitioning.
	 *@param first. This is a first state of the ArrayList endStates.
	 *@param second. This is a second state of the ArrayList endStates.
	 *<b> post: </b> The boolean value of search a state in the endStates of another states.
	 *@return founded. This is a boolean value that determine if first is in the same block of second.
	 */
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
	
	/** 
	 *<b>name:</b>  searchFirstBlock.
	 *Get the first block of the partitioning algorithm.<br>
	 *@param partitioning. This is the ArrayList of Hastable where for each index, we have a block of states with same inputs.
	 *@param states. This is a ArrayList with all states of the finite state machine. 
	 *<b> post: </b> The first block of partitioning has been created.
	 *@return partitioning. This is a ArrayList of Hastable with the blocks of states with the same inputs.
	 */
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
	
	/** 
	 *<b>name:</b>  getS.
	 *Set the set S of inputs of the finite state machine.<br> 
	 *<b> post: </b> The set of inputs of the finite state machine.
	 *@return S. This is a ArrayList of inputs of the finite state machine.
	 */
	public ArrayList<String> getS() {
		return S;
	}
	
	/**
	 * <b>name:</b> setS
	 * Set the set S of inputs of the finite state machine.<br> 
	 * <b>post:</b> The new ArrayList of inputs of the finite state machine. 
	 * @param S
	 */
	public void setS(ArrayList<String> S) {
		this.S = S;
	}
	
	/** 
	 *<b>name:</b>  getQ.
	 *Set the set Q of states of the finite state machine.<br> 
	 *<b> post: </b> The set of states of the finite state machine.
	 *@return Q. This is a ArrayList of States of the finite state machine.
	 */
	public ArrayList<String> getQ() {
		return Q;
	}
	
	/**
	 * <b>name:</b> setQ
	 * Set the set Q of states of the finite state machine.<br> 
	 * <b>post:</b> The new ArrayList of states of the finite state machine. 
	 * @param Q
	 */
	public void setQ(ArrayList<String> Q) {
		this.Q = Q;
	}
	
	/** 
	 *<b>name:</b>  getStatesConnecteds.
	 *Get the statesConnecteds arraylist.<br> 
	 *<b> post: </b> The arraylist of states connecteds.
	 *@return statesConnecteds. This is a ArrayList of States with the states connecteds.
	 */
	public ArrayList<States> getStatesConnecteds() {
		return statesConnecteds;
	}
	
	/**
	 * <b>name:</b> setStatesConnecteds. <br>
	 * Set the ArrayList of statesConnecteds. <br>
	 * <b>post:</b> The new ArrayList of statesConnecteds. 
	 * @param statesConnecteds. ArrayList that will be the new ArrayList of states connecteds.
	 */
	public void setStatesConnecteds(ArrayList<States> statesConnecteds) {
		this.statesConnecteds = statesConnecteds;
	}
}
