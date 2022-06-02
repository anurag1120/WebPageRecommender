package gui;
import java.nio.charset.Charset;

import java.util.prefs.Preferences;

/**
 * This class is used to manage registry keys for
 * storing user preferences for the  GUI.
 * 
 * @see MainWindow
 * @author Philippe Fournier-Viger
 */
public class PreferencesManager {   
	
	// We use two registry key to store
	// the paths of the last folders used by the user
	// for input and output files.
    public static final String REGKEY_SPMF_INPUT_FILE = "ca.pfv.spmf.gui.input";
    public static final String REGKEY_SPMF_OUTPUT_FILE = "ca.pfv.spmf.gui.output";
//    public static final String REGKEY_SPMF_PLUGIN_FOLDER_PATH = "ca.pfv.spmf.plugin.folderpath";
    public static final String REGKEY_SPMF_PLUGIN_REPOSITORY_URL = "ca.pfv.spmf.plugin.repositoryurl";
    public static final String REGKEY_SPMF_PREFERED_CHARSET = "ca.pfv.spmf.gui.charset";

    // Implemented as a singleton
    private static PreferencesManager instance;

    /**
     * Default constructor
     */
    private PreferencesManager(){

    }
    
    /**
     * Get the only instance of this class (a singleton)
     * @return the instance
     */
    public static PreferencesManager getInstance(){
        if(instance == null){
            instance = new PreferencesManager();
        }
        return instance;
    }
    
    /**
     * Get the input file path stored in the registry
     * @return a path as a string
     */
    public String getInputFilePath() {
        //      read back from registry HKCurrentUser
        Preferences p = Preferences.userRoot();
        return p.get(REGKEY_SPMF_INPUT_FILE, null);
    }
    
    /**
     * Store an input file path in the registry
     * @param filepath a path as a string
     */
    public void setInputFilePath(String filepath) {
        // write into HKCurrentUser
        Preferences p = Preferences.userRoot();
        p.put(REGKEY_SPMF_INPUT_FILE, filepath);
    }
    
    /**
     * Get the output file path stored in the registry
     * @return a path as a string
     */
    public String getOutputFilePath() {
        //      read back from registry HKCurrentUser
        Preferences p = Preferences.userRoot();
        return p.get(REGKEY_SPMF_OUTPUT_FILE, null);
    }

    /**
     * Store an output file path in the registry
     * @param filepath a path as a string
     */
    public void setOutputFilePath(String filepath) {
        // write into HKCurrentUser
        Preferences p = Preferences.userRoot();
        p.put(REGKEY_SPMF_OUTPUT_FILE, filepath);
    }
    
//    /**
//     * Get the output file path stored in the registry
//     * @return a path as a string
//     */
//    public String getPluginFolderFilePath() {
//        //      read back from registry HKCurrentUser
//        Preferences p = Preferences.userRoot();
//        return p.get(REGKEY_SPMF_PLUGIN_FOLDER_PATH, null);
//    }
//
//    /**
//     * Store an output file path in the registry
//     * @param filepath a path as a string
//     */
//    public void setPluginFolderFilePath(String filepath) {
//        // write into HKCurrentUser
//        Preferences p = Preferences.userRoot();
//        p.put(REGKEY_SPMF_PLUGIN_FOLDER_PATH, filepath);
//    }
//    
//    /**
//     * Delete the plugin file path from the registry
//     * @param filepath a path as a string
//     */
//    public void deletePluginFolderFilePath() {
//        // write into HKCurrentUser
//        Preferences p = Preferences.userRoot();
//        p.remove(REGKEY_SPMF_PLUGIN_FOLDER_PATH);
//    }
//    
//    
    //---
    /**
     * Store a repository URL in the registry
     * @param filepath a repository URL as a string
     */
    public void setRepositoryURL(String filepath) {
        // write into HKCurrentUser
        Preferences p = Preferences.userRoot();
        p.put(REGKEY_SPMF_PLUGIN_REPOSITORY_URL, filepath);
    }
    
    /**
     * Get the repository URL  path stored in the registry
     * @return a path as a string
     */
    public String getRepositoryURL() {
        //      read back from registry HKCurrentUser
        Preferences p = Preferences.userRoot();
        String url = p.get(REGKEY_SPMF_PLUGIN_REPOSITORY_URL, null);
        return (url == null) ? "http://www.philippe-fournier-viger.com/spmf/plugins/" : url;
    }
    

    //---
    
    /**
     * Get the prefered charset stored in the registry
     * @return Charset the prefered charset
     */
    public Charset getPreferedCharset() {
        //      read back from registry HKCurrentUser
        Preferences p = Preferences.userRoot();
        String charsetName = p.get(REGKEY_SPMF_PREFERED_CHARSET, null);

        return (charsetName == null) ? Charset.defaultCharset() : Charset.forName(charsetName);
    }

    /**
     * Store the prefered charset  in the registry
     * @param charsetName the prefered charset 
     */
    public void setPreferedCharset(String charsetName) {
        // write into HKCurrentUser
        Preferences p = Preferences.userRoot();
        p.put(REGKEY_SPMF_PREFERED_CHARSET, charsetName);
    }
}
