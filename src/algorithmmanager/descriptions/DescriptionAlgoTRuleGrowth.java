package algorithmmanager.descriptions;

import java.io.IOException;
import java.util.LinkedList;

import algorithmmanager.DescriptionOfAlgorithm;
import algorithmmanager.DescriptionOfParameter;
import recommendation.Rule;
import trulegrowth.TruleGrowthAlgo;


/**
 * This class describes the TRuleGrowth algorithm parameters. 
 * It is designed to be used by the graphical and command line interface.
 */
public class DescriptionAlgoTRuleGrowth extends DescriptionOfAlgorithm {

	/**
	 * Default constructor
	 */
	public DescriptionAlgoTRuleGrowth(){
	}

	@Override
	public String getName() {
		return "TRuleGrowth";
	}

	@Override
	public String getAlgorithmCategory() {
		return "SEQUENTIAL RULE MINING";
	}

	@Override
	public String getURLOfDocumentation() {
		return "https://github.com/anurag1120/WebPageRecommender";
	}

	@Override
	public void runAlgorithm(String[] parameters, String inputFile, String outputFile) throws IOException {
		double minsup = getParamAsDouble(parameters[0]);
		double minconf = getParamAsDouble(parameters[1]);
		int window = getParamAsInteger(parameters[2]);
		LinkedList<Rule> rules;
		TruleGrowthAlgo algo = new TruleGrowthAlgo();
		if (parameters.length >=4 && "".equals(parameters[3]) == false) {
			algo.setMaxAntecedentSize(getParamAsInteger(parameters[3]));
		}
		if (parameters.length >=5 && "".equals(parameters[4]) == false) {
			algo.setMaxConsequentSize(getParamAsInteger(parameters[4]));
		}
		 algo.runAlgorithm(minsup, minconf, inputFile, outputFile, window);
		algo.printStats();
		this.rules = algo.getRules();
		System.out.println("rules in Descripto"+this.rules);
		return;
	}

	public LinkedList<Rule> getRules(){
		return this.rules;
	} 

	@Override
	public DescriptionOfParameter[] getParametersDescription() {
        
		DescriptionOfParameter[] parameters = new DescriptionOfParameter[5];
		parameters[0] = new DescriptionOfParameter("Minsup (%)", "(e.g. 0.7 or 70%)", Double.class, false);
		parameters[1] = new DescriptionOfParameter("Minconf (%)", "(e.g. 0.8 or 80%)", Double.class, false);
		parameters[2] = new DescriptionOfParameter("Window size", "(e.g. 3)", Integer.class, false);
		parameters[3] = new DescriptionOfParameter("User sequence", "(e.g. '2 3 4 5')", String.class, true);
		parameters[4] = new DescriptionOfParameter("Max no of Predictions ", "(e.g. 2 items)", Integer.class, true);
		return parameters;
	}

	@Override
	public String getImplementationAuthorNames() {
		return "Anurag";
	}

	@Override
	public String[] getInputFileTypes() {
		return new String[]{"Database of instances","Sequence database", "Simple sequence database"};
	}

	@Override
	public String[] getOutputFileTypes() {
		return new String[]{"Patterns", "Sequential rules", "Frequent sequential rules"};
	}
	
}
