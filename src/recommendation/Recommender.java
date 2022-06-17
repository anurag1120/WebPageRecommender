package recommendation;

import java.io.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import tools.MemoryLogger;
public class Recommender {
    	// *** for statistics ***
	long timeStart = 0; // start time of latest execution
	long timeEnd = 0;  // end time of latest execution]
	LinkedHashSet<Integer> recommendations;
	LinkedList<Rule> rules;
    public Recommender() {
	}

/* 	public void evaluatePerformance(String input,String output){
		timeStart = System.currentTimeMillis(); 
		rules = getRulesFromFile(input);
		int j;
		j = (int)Math.round(rules.size()*0.7); // Using 70/30 rules for testing

		LinkedList<Rule> trianSet  = new LinkedList<Rule>();
		LinkedList<Rule> testSet = new LinkedList<Rule>();
		for(int i=0;i<rules.size();i++){
			if(i<j){
				trianSet.add(rules.get(i));
			}
			else{
				testSet.add(rules.get(i));
			}
		}
		int crct = 0, wrong= 0;
		for(int i=0;i<testSet.size();i++){
			 generateRecommendations(trianSet, testSet.get(i).getRuleLeft(), 3, 4);
			 ArrayList<Integer> ruleRight = testSet.get(i).getRuleRight();
			 Boolean isPresent = false;
			 for(int k=0;k<ruleRight.size();k++){
				 if(recommendations.contains(ruleRight.get(k))){
					 isPresent=true;
					 break;
				 }
				System.out.println("RuleRight  "+ruleRight);
				System.out.println("Recomms  "+recommendations);


			 }
			 if(isPresent){
				 crct++;
			 }
			 else{
				 wrong++;
			 }
		}
		System.out.println("Crct are  "+crct);
		System.out.println("Wrong are  "+wrong);
		double accuracy = (crct/(crct+wrong)) ;
		timeEnd = System.currentTimeMillis(); 
		printStats();
		System.out.println("Accuracy is   "+accuracy);
	}
*/
	public void getRulesFromFile(String input){
		 rules = new LinkedList<Rule>();
		// load the input file into memory
		try {
			File file = new File(input);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line=br.readLine())!=null){
				rules.add(parseRule(line));
			}
            
        } catch (Exception e) {
            System.out.println("Exception in opening file"+e);
        }
	}

    public void runAlgorithm(ArrayList<Integer> userSeq,int prediction,String input, String output){
		timeStart = System.currentTimeMillis(); 
		getRulesFromFile(input);
		System.out.println("Rules are loaded into memory");
		Collections.sort(rules,
		new RuleSortingComparator());
		MemoryLogger.getInstance().checkMemory();
		generateRecommendations( userSeq, 3, prediction);
		timeEnd = System.currentTimeMillis(); 
		printStats();
	}

	public void setRules(LinkedList<Rule> rules){
		this.rules = rules;
	}


    private Rule parseRule(String line) {
		
		String[] arr = line.split(" ");
        Boolean isRuleLeftComplete,isRuleRightComplete,isSupComplete;
        isRuleLeftComplete=isRuleRightComplete=isSupComplete=false;
        
		ArrayList<Integer> ruleLeft,ruleRight;
        int sup = 0;
        double conf = 0;
        ruleLeft = new ArrayList<Integer>();
        ruleRight = new ArrayList<Integer>();
        for(String a: arr){
            if(isRuleLeftComplete==false){
                if(a.startsWith("=")){
                    isRuleLeftComplete=true;
                }
                if(a.matches("-?\\d+(\\.\\d+)?")) ruleLeft.add(Integer.parseInt(a));
            }
            else if(isRuleRightComplete==false){
                if(a.startsWith("#")){
                    isRuleRightComplete=true;
                }
                if(a.matches("-?\\d+(\\.\\d+)?")) ruleRight.add(Integer.parseInt(a));
            }else if(isSupComplete==false){
                if(a.startsWith("#")){
                    isSupComplete=true;
                }
                if(a.matches("-?\\d+(\\.\\d+)?")) sup = (Integer.parseInt(a));
            }else{
                if(a.matches("-?\\d+(\\.\\d+)?")) conf = Double.parseDouble(a);
            }  
        }

		Rule rule = new Rule(ruleLeft,ruleRight,sup,conf);

		return rule;
	}

	//prediction:  no of recommendations
	public  void generateRecommendations(ArrayList<Integer> pattern,int threshold,int prediction){
		LinkedHashSet<Integer> recommendedWebpages = new LinkedHashSet<Integer>();
		LinkedHashSet<Integer> popularWebpages = new LinkedHashSet<Integer>();
		ArrayList<Integer> cutoffPattern = new ArrayList<Integer>();
		for(int i=0;i<pattern.size()&&i<threshold;i++) {
			cutoffPattern.add(pattern.get(pattern.size()-i-1));
		}
		ArrayList random = new ArrayList<>();
		//for getting better recommedations
		Collections.reverse(cutoffPattern);
		for(int i=0;i<rules.size();i++) {
			Rule r = rules.get(i);
			ArrayList<Integer> ruleRight =  r.getRuleRight();
			ArrayList<Integer> ruleLeft =  r.getRuleLeft();
			if(cutoffPattern.equals(ruleLeft)) {
				if(recommendedWebpages.size()>prediction) {
					break;
				}else {
					recommendedWebpages.addAll(ruleRight);
					if(random.size()<2) random.addAll(ruleRight);
				}
			}
		}
		for(int i=0;i<Math.min(rules.size(),15);i++){
			popularWebpages.addAll(rules.get(i).getRuleRight());
		}
		
		//in all cases (except where there are no rules) try to recommend some popular pages
		Iterator itr = popularWebpages.iterator();
		
		if(recommendedWebpages.size()<prediction){
			for(int i=1;i<=prediction-recommendedWebpages.size();i++){
				if(itr.hasNext()) recommendedWebpages.add((Integer)itr.next());
			}
		}
		MemoryLogger.getInstance().checkMemory();
		this.recommendations = recommendedWebpages;
		//System.out.println("in generate recommendations"+recommendedWebpages);
		return;
	}

	public void printStats() {
		System.out.println("=============  Recommender - STATS =============");
//		System.out.println("minsup: " + minsuppRelative);
		System.out.println("Total time : " + (timeEnd - timeStart) + " ms");
		System.out.println("Max memory (mb)" + MemoryLogger.getInstance().getMaxMemory());
		System.out.println("=====================================");
	}

    public LinkedHashSet<Integer> getRecomms() {
        return this.recommendations;
    }
}
