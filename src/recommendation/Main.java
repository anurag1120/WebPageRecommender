package recommendation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;


import trulegrowth.TruleGrowthAlgo;


public class Main {

	static LinkedList<Rule> rules;
	public static void main(String [] arg) throws IOException{
		String input = fileToPath("input.txt");  // the database
		String output = "./output.txt";  // the path for saving the frequent itemsets found

		//  Applying RuleGROWTH algorithm with minsup = 3 sequences and minconf = 0.5
		double minsup = 0.007;
		double minconf = 0.;
		int windowSize = 3;
		

		TruleGrowthAlgo algo = new TruleGrowthAlgo();
		
		// This optional parameter allows to specify the maximum number of items in the 
		// left side (antecedent) of rules found:
//		algo.setMaxAntecedentSize(1);  // optional

		// This optional parameter allows to specify the maximum number of items in the 
		// right side (consequent) of rules found:
//		algo.setMaxConsequentSize(2);  // optional

		rules = algo.runAlgorithm(minsup, minconf, input, output, windowSize);

		
		ArrayList<Integer> sample = new ArrayList<Integer>();
		sample.add(1);
		//sample.add(4);
		//sorting with support and conf
		Collections.sort(rules,
		new RuleSortingComparator());
		System.out.println(rules);
		System.out.println(getRecommendations(sample,8,3));
		// print statistics
		algo.printStats();
	}
	public static String fileToPath(String filename) throws UnsupportedEncodingException{
		//System.out.println(filename);
		URL url = Main.class.getResource(filename);
		System.out.println(url);
		return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
	}
	
	//for single pattern
	//thershold : no of pages already browsed by user
	//prediction:  no of recommendations
	public static Set<Integer> getRecommendations(ArrayList<Integer> pattern,int threshold,int prediction){
		LinkedHashSet<Integer> recommendedWebpages = new LinkedHashSet<Integer>();
		LinkedHashSet<Integer> popularWebpages = new LinkedHashSet<Integer>();
		ArrayList<Integer> cutoffPattern = new ArrayList<Integer>();
		for(int i=0;i<pattern.size()&&i<threshold;i++) {
			cutoffPattern.add(pattern.get(i));
		}
		for(int i=0;i<rules.size();i++) {
			Rule r = rules.get(i);
			ArrayList<Integer> ruleRight =  r.getRuleRight();
			ArrayList<Integer> ruleLeft =  r.getRuleLeft();
			if(cutoffPattern.equals(ruleLeft)) {
				if(recommendedWebpages.size()>prediction) {
					break;
				}else {
					recommendedWebpages.addAll(ruleRight);
				}
			}else{
				if(popularWebpages.size()<prediction) popularWebpages.addAll(ruleRight);
			}
		}
		//in all cases (except where there are no rules) try to recommend atleast 3 pages
		Iterator itr = popularWebpages.iterator();
		if(recommendedWebpages.size()<prediction){
			for(int i=1;i<=prediction-recommendedWebpages.size();i++){
				if(itr.hasNext()) recommendedWebpages.add((Integer)itr.next());
			}
		}
		return recommendedWebpages;
	}
	
}
