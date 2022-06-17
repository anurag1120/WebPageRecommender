package algorithmmanager;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import recommendation.Rule;

public abstract class DescriptionOfAlgorithm {

	public LinkedList<Rule> rules;
	public LinkedHashSet<Integer> recommendations;
	public double accuracy;
	
	/** get the  name of the author of the implementation */
	public abstract String getImplementationAuthorNames();

	/** get the name of the algorithm (e.g. "Rulegrowth") */
	public  abstract String getName();

	/** get the category of this algorithm (e.g. "sequential rule mining" */
	public  abstract String getAlgorithmCategory();

	/** get the URL providing a documentation of how to use this algorithm */
	public abstract String getURLOfDocumentation();


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
	public abstract void runAlgorithm(String[] parameters, String inputFile,
			String outputFile) throws Exception;
	
	/**
	 * Get a description of the algorithm's parameters
	 * @return a list of AlgorithmParameter objects describing the parameters of the algorithm.
	 */
	public abstract DescriptionOfParameter[] getParametersDescription();
	
	/**
	 * Get at list of file types (Strings) representing the input file format taken as input by the algorithm.
	 * @return a list of file types (Strings) or null if the algorithm does not take an input file as input.
	 */
	public abstract String[] getInputFileTypes();
	
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
	public abstract String[] getOutputFileTypes();
	

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

}
