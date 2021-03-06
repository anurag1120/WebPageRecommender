package sequence_database_array_integers;

import java.util.ArrayList;
import java.util.List;



/**
 * Implementation of a sequence as a list of itemsets, where itemsets are array of integers.
* 
* @see SequenceDatabase
 * @author Philipe-Fournier-Viger
 **/
public class Sequence{
	
	// A sequence is a list of itemsets, where an itemset is an array of integers
	private final List<Integer[]> itemsets = new ArrayList<Integer[]>();

	/**
	 * Add an itemset to this sequence.
	 * @param itemset An itemset (array of integers)
	 */
	public void addItemset(Object[] itemset) {
		Integer[] itemsetInt = new Integer[itemset.length];
		System.arraycopy(itemset, 0, itemsetInt, 0, itemset.length);
		itemsets.add(itemsetInt);
	}
	
	/**
	 * Print this sequence to System.out.
	 */
	public void print() {
		System.out.print(toString());
	}
	
	/**
	 * Return a string representation of this sequence.
	 */
	public String toString() {
		StringBuilder r = new StringBuilder("");
		// for each itemset
		for(Integer[] itemset : itemsets){
			r.append('(');
			// for each item in the current itemset
			for(int i=0; i< itemset.length; i++){
				String string = itemset[i].toString();
				r.append(string);
				r.append(' ');
			}
			r.append(')');
		}

		return r.append("    ").toString();
	}

	/**
	 * Get the list of itemsets in this sequence.
	 * @return the list of itemsets.
	 */
	public List<Integer[]> getItemsets() {
		return itemsets;
	}
	
	/**
	 * Get the itemset at a given position in this sequence.
	 * @param index the position
	 * @return the itemset as an array of integers.
	 */
	public Integer[] get(int index) {
		return itemsets.get(index);
	}
	
	/**
	 * Get the size of this sequence (number of itemsets).
	 * @return the size (an integer).
	 */
	public int size(){
		return itemsets.size();
	}
}

