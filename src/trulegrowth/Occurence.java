package trulegrowth;
import java.util.ArrayList;
import java.util.List;

import sequence_database_list_integers.Sequence;
import sequence_database_list_integers.SequenceDatabase;

/**
 * This class represent a set of occurences in a sequence, as defined 
 * in the TRuleGrowth algorithm.
 * 
 * Note that unlike the RuleGrowth algorithms, all occurences in a sequence are kept instead of just
 * the first and last one. This is the main difference between the two implementations of "Occurence".
 * 
 * @see AlgoTRuleGrowth
 * @see Sequence
 * @see SequenceDatabase
 * @author Philippe Fournier-Viger
 */
public class Occurence {
	/** the sequenceID (a.k.a transaction id) */
	public int sequenceID =-1;
	/** a list of occurences (position in this sequence) */
	public List<Short> occurences = new ArrayList<Short>();
	
	/**
	 * Contructor
	 * @param sequenceID a sequence ID
	 */
	public Occurence(int sequenceID){
		this.sequenceID = sequenceID;
	}
	
	/**
	 * Add an occurence.
	 * @param occurence the position of an itemset
	 */
	public void add(short occurence){
		occurences.add(occurence);
	}
	
	/**
	 * Get the first occurence in this sequence.
	 * @return the position of an itemset
	 */
	public short getFirst(){
		return occurences.get(0);
	}
	
	/**
	 * Get the last occurence in this sequence.
	 * @return the position of an itemset
	 */
	public short getLast(){
		return occurences.get(occurences.size()-1);
	}
	
	/**
	 * Check if a given Occurence is the same as this one (used to store occurence in Collections).
	 * @param obj another Occurence
	 * @return true if they both have the same sequence ID.
	 */
	public boolean equals(Object obj) {
		return ((Occurence)obj).sequenceID == sequenceID;
	}

	/**
	 * Get the hashcode.
	 * @return an hashcode
	 */
	public int hashCode() {
		return sequenceID;
	}
}
