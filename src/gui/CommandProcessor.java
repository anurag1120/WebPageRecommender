package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

import algorithmmanager.AlgorithmManager;
import algorithmmanager.DescriptionOfAlgorithm;
import algorithmmanager.DescriptionOfParameter;
import recommendation.Rule;

/**
 * This class executes commands from the command line interface or
 * graphical interface to run the algorithms.
 */
public class CommandProcessor {

	private CommandProcessor() {

	}

	/**
	 * This method run an algorithm. It is called from the GUI interface or when
	 * the user run the jar file from the command line.
	 * 
	 * @param algorithmName
	 *            the name of the algorithm
	 * @param inputFile
	 *            the input file for the algorithm
	 * @param outputFile
	 *            the output file for the algorithm
	 * @param parameters
	 *            the parameters of the algorithm
	 * @throws Exception if sometimes bad occurs
	 */
	public static void runAlgorithm(String algorithmName,
			String inputFile, String outputFile, String[] parameters) throws Exception {
	
		Map<Integer, String> mapItemToString = null;
		// This variable store the path of the original output file
		String originalOutputFile = null;
		// This variable store the path of the original input file
		String originalInputFile = null;
		
		// Get information about the chosen algorithm
		AlgorithmManager manager = AlgorithmManager.getInstance();
		DescriptionOfAlgorithm algorithm = manager.getDescriptionOfAlgorithm(algorithmName);
		
		// If the algorithm does not exist, throw an exception
		if(algorithm == null){
			throw new IllegalArgumentException("\n\n There is no algorithm with the name '" + algorithmName);
		}
		
		// If there is no imput file we remember it
		if(algorithm.getInputFileTypes() == null){
			inputFile = null;
		}
		// If there is no output file we remember it
		if(algorithm.getOutputFileTypes() == null){
			outputFile = null;
		}
		
		// Check that the parameters provided by the user are OK
		// in terms of data type (String, Integer...). 
		// Otherwise, we will throw an exception to the user
		
		// If we expect an input file
		if(algorithm.getInputFileTypes() != null){
			if(inputFile == null){
				throw new IllegalArgumentException(System.lineSeparator()+ System.lineSeparator() + "No input file has been provided.");
			}
			
			File input = new File(inputFile);
			if(input.exists() == false){
				throw new IllegalArgumentException(System.lineSeparator()+ System.lineSeparator() +" The input file '" + inputFile + "' does not exist.");
			}
		}
		
		// If we expect an output file
		if(algorithm.getOutputFileTypes() != null && outputFile == null){
				throw new IllegalArgumentException(System.lineSeparator()+ System.lineSeparator() + " No output file path has been provided.");
		}
		
		int numberOfParameter = algorithm.getParametersDescription().length;
		
		for(int i = 0; i < numberOfParameter; i++){
			DescriptionOfParameter parameterI =  algorithm.getParametersDescription()[i];

			// Get the ordinal of this parameter
			String ordinal = ordinal(i+1);
			
			// If it is the command line interface and the user has not provided the parameter i
			if(i == parameters.length){
				// and if the parameter is not optional
				if(parameterI.isOptional == false){
					// we throw an exception
					throw new IllegalArgumentException(System.lineSeparator()+ System.lineSeparator() +" The " + ordinal + " parameter of this algorithm '" + parameterI.name + "' is mandatory. Please provide a value of type: " + parameterI.parameterType.getSimpleName() + ".");
				}
				break;
			}else{
				// Otherwise, the user has provided the parameter
				String valueI = parameters[i];
				
				// If the parameter is null but it is not optional, then we need to inform the user
				// that this parameter is not optional
				if("".equals(valueI)){
					if(parameterI.isOptional == false){
						throw new IllegalArgumentException(System.lineSeparator()+ System.lineSeparator() +" The " + ordinal + " parameter of this algorithm '" + parameterI.name + "' is mandatory. Please provide a value of type: " + parameterI.parameterType.getSimpleName() + ".");
					}
				}
				else{
					// Check if the parameter is of the correct type
					boolean isCorrectType = algorithm.isParameterOfCorrectType(parameters[i], i);
					
					// if not of the correct type, we throw an exception
					if(isCorrectType == false){		
						
						throw new IllegalArgumentException(System.lineSeparator()+ System.lineSeparator() + " The " + ordinal + " parameter value of this algorithm '" + parameterI.name + "' is of an incorrect type. The provided value is '" + parameters[i] + "' but it should be of type: " + parameterI.parameterType.getSimpleName() + ".");
					}
				}
			}
		}
					// ******  WE  APPLY THE DESIRED ALGORITHM ******
				 algorithm.runAlgorithm(parameters, inputFile, outputFile);
				 if(algorithmName=="TRuleGrowth"){
					System.out.println(
						"The Generated Rules are"
					);
					for(int i=0;i<algorithm.rules.size();i++){
						System.out.println(algorithm.rules.get(i));
					}
				 }
				 else if(algorithmName=="Recommender"){
					System.out.println(
						"The Generated Recommendations are"
					);
					Iterator itr = algorithm.recommendations.iterator();
					while(itr.hasNext()){
						System.out.println(itr.next());
					}
				 }
				 				 
	}

	public static void processGetRecommendationsCommandFromGUI() {

	}

	/**
	 * Convert an integer number to its ordinal
	 * 
	 * @param i the number
	 * @return its ordinal as a String
	 */
	public static String ordinal(int i) {
		// Code from :
		// http://stackoverflow.com/questions/6810336/is-there-a-library-or-utility-in-java-to-convert-an-integer-to-its-ordinal
		String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
		switch (i % 100) {
			case 11:
			case 12:
			case 13:
				return i + "th";
			default:
				return i + sufixes[i % 10];

		}
	}

}
