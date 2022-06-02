package algorithmmanager.descriptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import algorithmmanager.DescriptionOfAlgorithm;
import algorithmmanager.DescriptionOfParameter;
import recommendation.Recommender;


public  class DescrpitionAlgoRecommender extends DescriptionOfAlgorithm{
	

	/** get the name of the algorithm (e.g. "Rulegrowth") */
	public String getName() {
		return "Recommender";
	}

	/** get the category of this algorithm (e.g. "sequential rule mining" */
	public String getAlgorithmCategory() {
		return "SEQUENTIAL RULE MINING";
	}


	/**
	 * Run the algorithm
	 * 
	 * @param algorithmName
	 *            the name of the algorithm
	 * @param inputFile
	 *            the input file for the algorithm
	 * @param outputFile
	 *            the output file for the algorithm
	 * @param parameters
	 *            the parameters of the algorithm
	 * @throws IOException
	 *             exception if an error occurs
	 */
	public  void runAlgorithm(String[] parameters, String inputFile,
			String outputFile) throws Exception{
                ArrayList<Integer> userSeq = getParamAsArrayList(parameters[0]);
                int p = getParamAsInteger(parameters[1]);
                Recommender algo = new Recommender();
				algo.runAlgorithm(userSeq, p, inputFile, outputFile);
				algo.printStats();
				this.recommendations= algo.getRecomms();
				System.out.println("recomms in Descripto"+this.recommendations);

        }
	
	public ArrayList<Integer> getParamAsArrayList(String line) {
		String[] arr = line.split(" ");
		ArrayList<Integer> userSeq = new ArrayList<Integer>();
		for(String a: arr){
			if(a.matches("-?\\d+(\\.\\d+)?")) userSeq.add(Integer.parseInt(a));
		}
		return userSeq;
	}

	/**
	 * Get a description of the algorithm's parameters
	 * @return a list of AlgorithmParameter objects describing the parameters of the algorithm.
	 */
	public  DescriptionOfParameter[] getParametersDescription(){
        DescriptionOfParameter[] parameters = new DescriptionOfParameter[2];
		parameters[0] = new DescriptionOfParameter("User Sequence","(e.g. '2 3 4 5')", String.class, false);
		parameters[1] = new DescriptionOfParameter("Max no of Predictions ", "(e.g. 4 items)", Integer.class, false);
		return parameters;
    }
	
	/**
	 * Get at list of file types (Strings) representing the input file format taken as input by the algorithm.
	 * @return a list of file types (Strings) or null if the algorithm does not take an input file as input.
	 */
	public String[] getInputFileTypes() {
		return new String[]{"Rules in textfiles"};
	}
	
//	/**
//	 * Get at list of special file types supported by this algorithm such as the ARFF file format.
//	 * @return a list of file types (Strings) or null if the algorithm does not accept any special input file as input.
//	 */
//	abstract String[] getSpecialInputFileTypes();
//	
	/**
	 * Get at list of file types (Strings) representing the  output file format taken as input by the algorithm.
	 * @return a list of file types (Strings) or null if the algorithm does not output a file.
	 */
	public  String[] getOutputFileTypes(){
		return new String[]{"Recommendations"};
	}
	

	/**
	 * Method to convert a parameter given as a string to a double. For example,
	 * convert something like "50%" to 0.5.
	 * 
	 * @param value
	 *            a string
	 * @return a double
	 */
	protected static double getParamAsDouble(String value)  {
		if (value.contains("%")) {
			value = value.substring(0, value.length() - 1);
			return Double.parseDouble(value) / 100d;
		}
		return Double.parseDouble(value);
	}
	
	/**
	 * Method to convert a parameter given as a string to a float. For example,
	 * convert something like "50%" to 0.5.
	 * 
	 * @param value
	 *            a string
	 * @return a float
	 */
	protected static float getParamAsFloat(String value)  {
		if (value.contains("%")) {
			value = value.substring(0, value.length() - 1);
			return Float.parseFloat(value) / 100f;
		}
		return Float.parseFloat(value);
	}

	/**
	 * Method to transform a string to an integer
	 * 
	 * @param value
	 *            a string
	 * @return an integer
	 */
	protected static int getParamAsInteger(String value) {
		return Integer.parseInt(value);
	}
	
	/**
	 * Method to transform a string to an boolean
	 * 
	 * @param value         a string
	 * @return a boolean
	 */
	protected static boolean getParamAsBoolean(String value) {
		if("true".equals(value) || "True".equals(value) || "1".equals(value)){
			return true;
		}
		if("false".equals(value) || "False".equals(value) || "0".equals(value)){
			return false;
		}
		throw new NumberFormatException("Illegal value");
	}

	/**
	 * Method to get a parameter as a string. Note: this method just return the
	 * string taken as parameter.
	 * 
	 * @param value
	 *            a string
	 * @return a string
	 */
	protected static String getParamAsString(String value) {
		return value;
	}

	/**
	 * Check if a given value would be of the correct type as the i-th parameter value of this
	 * algorithm.
	 * @param string the value as a string
	 * @param i the parameter number
	 * @return true if it is of the correct type. Otherwise, false
	 */
	public boolean isParameterOfCorrectType(String value, int i) {
		// Get the class that is expected for the i-th parameter
		Class expectedClass = getParametersDescription()[i].parameterType;
		
		// Try to cast the value to the given parameter type
		try{
			if(expectedClass == Double.class){
				Double convertedValue = getParamAsDouble(value);
			}else if(expectedClass == Integer.class){
				Integer convertedValue = getParamAsInteger(value);
			}else if(expectedClass == Boolean.class){
				Boolean convertedValue = getParamAsBoolean(value);
			}else if(expectedClass == String.class){
				String convertedValue = getParamAsString(value);
			}
		}catch(Exception e){
			// If it does not work, then the value is not of the correct type
			return false;
		}
		
		// Otherwise, the value is of the correct type
		return true;
	}

	@Override
	public String getImplementationAuthorNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getURLOfDocumentation() {
		// TODO Auto-generated method stub
		return null;
	}

}
