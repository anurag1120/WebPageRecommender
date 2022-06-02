package algorithmmanager;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Arrays;


/**
 * This class is for testing the AlgorithmManager class.
 * 
 * @see AlgorithmManager
 * @author Philippe Fournier-Viger 2016
 */

public class MainTestAlgorithmManager {

	public static void main(String[] args) throws Exception {
		// / Initialize the algorith manager
		AlgorithmManager algoManager = AlgorithmManager.getInstance();
		
		// First example:  Get the list of all algorithms by categories
		System.out.println("========= Printing the list of algorithms sorted by categories ========");
		System.out.println();
		for (String algoName : algoManager.getListOfAlgorithmsAsString(true, true)) {
			System.out.println(algoName);
		} 
		
		// Second example: obtain detailed information about a given algorithm
		System.out.println();
		System.out.println("========= Obtaining information about the BIDE+ algorithm ========");
		String algorithm = "BIDE+";
		DescriptionOfAlgorithm descriptionOfAlgorithm = algoManager.getDescriptionOfAlgorithm(algorithm);
		
		System.out.println("Name : " + descriptionOfAlgorithm.getName());
		System.out.println("Category : " + descriptionOfAlgorithm.getAlgorithmCategory());
		System.out.println("Types of input file : " + Arrays.toString(descriptionOfAlgorithm.getInputFileTypes()));
		System.out.println("Types of output file : " + Arrays.toString(descriptionOfAlgorithm.getOutputFileTypes()));
		System.out.println("Types of parameters : " + Arrays.toString(descriptionOfAlgorithm.getParametersDescription()));
		System.out.println("Implementation author : " + descriptionOfAlgorithm.getImplementationAuthorNames());
		System.out.println("URL:  : " + descriptionOfAlgorithm.getURLOfDocumentation());
		
		// Third example: run the selected algorithm

		System.out.println();
		System.out.println("========= Running the BIDE+ algorithm ========");
		
		String[] parameters = new String[]{"0.4","50","true"};
		String inputFile = fileToPath("contextPrefixSpan.txt");
		String outputFile = "./output.txt";
		descriptionOfAlgorithm.runAlgorithm(parameters, inputFile, outputFile);
	}
	
	public static String fileToPath(String filename) throws UnsupportedEncodingException{
		URL url = MainTestAlgorithmManager.class.getResource(filename);
		 return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
	}
}
