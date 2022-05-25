package recommendation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

import trulegrowth.TruleGrowthAlgo;


public class Main {

	static LinkedList<LinkedList<Object>> rules;
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

		//System.out.println(rules);
		LinkedList<Integer> sample = new LinkedList<Integer>();
		sample.add(55267);
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
	//prediction: max no of recommendations
	public static Set<Integer> getRecommendations(LinkedList<Integer> pattern,int threshold,int prediction){
		Set<Integer> recommendedWebpages = new HashSet<Integer>();
		LinkedList<Integer> cutoffPattern = new LinkedList();
		int currentPredictions = 0;
		for(int i=0;i<pattern.size()&&i<threshold;i++) {
			cutoffPattern.add(pattern.get(i));
		}
		for(int i=0;i<rules.size();i++) {
			LinkedList<Object> l = rules.get(i);
			ArrayList<Object> ruleRight =  (ArrayList<Object>) l.get(1);
			int predictedWebPage = 0;
			if(ruleRight.size()>0) predictedWebPage =  (int) ruleRight.get(0);
			if(cutoffPattern.equals(l.get(0))) {
				currentPredictions++;
				if(currentPredictions>prediction) {
					break;
				}else {
					recommendedWebpages.add(predictedWebPage);
				}
			}
		}
		return recommendedWebpages;
	}
	
}
