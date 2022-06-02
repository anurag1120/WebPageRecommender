package algorithmmanager;

import java.io.Serializable;


/**
 * This class is used to describe an algorithm's parameter.
 * 
 * @see DescriptionOfAlgorithm
 * @author Philippe Fournier-Viger, 2016
 */
public class DescriptionOfParameter implements Serializable{
	
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 6680232387395745034L;
	
	/** name of this parameter */
	public final String name;
	/** example value for this parameter */
	public final String example;
	/** type of parameter value */
	public final Class parameterType;
	/** this parameter is optional or not? */
	public final boolean isOptional;
	
	/**
	 * Constructor for this parameter
	 * @param name  the name of the parameter (a string)
	 * @param example a string providing an example value that this parameter could take
	 * @param parameterType the type of this parameter (e.g. Integer.class, Double.class, String.class...)
	 */
	public DescriptionOfParameter(String name, String example, Class parameterType, boolean isOptional){
		this.name = name;
		this.example = example;
		this.parameterType = parameterType;
		this.isOptional = isOptional;
	}
	
	@Override
	/**
	 * Obtain a String representation of this parameter description
	 * @return a String
	 */
	public String toString() {
		return "[" + name + ", " + example + ", " + parameterType + ", isOptional = " + isOptional + " ]";
	}

}
