package gui;

import java.nio.charset.Charset;


/**
 * The main method of this class reset the input/output file paths
 * that are stored in the registry by the GUI version
 * (This class is intended for developers only).
 * 
 * @author Philippe Fournier-Viger
 */
 class ResetPreferences {

	public static void main(String[] args) {
		PreferencesManager.getInstance().setInputFilePath("");
		PreferencesManager.getInstance().setOutputFilePath("");
		PreferencesManager.getInstance().setPreferedCharset(Charset.defaultCharset().name());
	}

}
