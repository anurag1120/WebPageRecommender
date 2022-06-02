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
    public Recommender() {
	}

    public void runAlgorithm(ArrayList<Integer> userSeq,int prediction,String input, String output){
		LinkedList<Rule> rules = new LinkedList<Rule>();

		//parse text
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
		printStats();
		 generateRecommendations(rules, userSeq, 3, prediction);
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
	public  void generateRecommendations(LinkedList<Rule> rules,ArrayList<Integer> pattern,int threshold,int prediction){
		LinkedHashSet<Integer> recommendedWebpages = new LinkedHashSet<Integer>();
		LinkedHashSet<Integer> popularWebpages = new LinkedHashSet<Integer>();
		ArrayList<Integer> cutoffPattern = new ArrayList<Integer>();
		for(int i=0;i<pattern.size()&&i<threshold;i++) {
			cutoffPattern.add(pattern.get(pattern.size()-i-1));
		}
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
		this.recommendations = recommendedWebpages;
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
