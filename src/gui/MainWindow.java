package gui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument.Content;

import algorithmmanager.AlgorithmManager;
import algorithmmanager.DescriptionOfAlgorithm;
import algorithmmanager.DescriptionOfParameter;
import javax.swing.*;
import java.awt.*;
import input.TestInput;
import recommendation.Rule;

/**
 * This class is the user interface  (the main Window).
 * It allows the user to launch single algorithms.
 */
public class MainWindow extends JFrame implements ThreadCompleteListener, UncaughtExceptionHandler {

    // current input file
    private String inputFile = null;
    // current output file
    private String outputFile = null;
    private static final long serialVersionUID = 1L;
    /**
     * The following fields are components of the user interface. They are
     * generated automatically by the Visual Editor plugin of Eclipse.
     */
    private JPanel contentPane;
    private JTextField textFieldParam1;
    private JTextField textFieldParam2;
    private JTextField textFieldParam3;
    private JTextField textFieldParam4;
    private JTextField textFieldParam5;
    private JTextField textFieldParam6;
    private JTextField textFieldParam7;
    private JLabel labelParam1;
    private JLabel labelParam2;
    private JLabel labelParam3;
    private JLabel labelParam4;
    private JLabel labelParam5;
    private JLabel labelParam6;
    private JLabel labelParam7;
    private JLabel lbHelp1;
    private JLabel lbHelp2;
    private JLabel lbHelp3;
    private JLabel lbHelp4;
    private JLabel lbHelp5;
    private JLabel lbHelp6;
    private JLabel lbHelp7;
    private JTextField textFieldInput;
    private JTextField textFieldOutput;
    private JComboBox<String> comboBox;
    private JTextArea textArea;
    private JButton buttonRun;
    private JCheckBox checkboxOpenOutputText;
    private JLabel lblSetOutputFile;
    private JButton buttonOutput;
    private JButton buttonInput;
    private JLabel lblChooseInputFile;
    private JProgressBar progressBar;
    
    //  VARIABLES USED TO RUN AN ALGORITHM IN A SEPARATED THREAD
    // The current data mining task
    private static NotifyingThread currentRunningAlgorithmThread = null;
	private JLabel lblOpenOutputFile;
	
	/**  show the tools */
	private boolean showTools;
	
	/**  show the algorithms*/
	private boolean showAlgorithms;

    /**
     * Create the frame.
     * @throws Exception 
     */
    public MainWindow(boolean showTools, boolean showAlgorithms) {
    	this.showTools = showTools;
    	this.showAlgorithms = showAlgorithms;
        setResizable(true);
        addWindowListener(new WindowAdapter() {

        	@Override
            public void windowClosed(WindowEvent arg0) {
                System.exit(0);
            }
        });
        
        // We want to distinguish if the windows is used to run a dataset tool or another algorithm.
        if(showTools && !showAlgorithms){
        	setTitle("Prepare data (run a dataset tool)");
        }
        else if(!showTools && showAlgorithms){
        	setTitle("Run an algorithm");
        }
        else{
        	// set the title of the window
        	setTitle("WEB PAGE RECOMMENDER version 1.0");
        }
        // When the user clicks the "x" the software will close.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setUndecorated(false);
        setVisible(true);
        contentPane = new JPanel();
        contentPane.setLayout(null);



       // contentPane.setBorder(new EmptyBorder(3, 3, 3, 3));
        setContentPane(contentPane);
       

        // Combo box to store the list of algorithms.
        comboBox = new JComboBox<String>(new Vector<String>());
        comboBox.setMaximumRowCount(20);

        //************************************************************************
        //********* Use the algorithm manager to populate the list of algorithms ******* //
        comboBox.addItem("");
        
        AlgorithmManager manager = null;
		try {
			manager = AlgorithmManager.getInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		List<String> algorithmList = manager.getListOfAlgorithmsAsString(showTools, showAlgorithms);
		for(String algorithmOrCategoryName : algorithmList){
			comboBox.addItem(algorithmOrCategoryName);
		}

        //************************************************************************
        //************************************************************************

        // What to do when the user choose an algorithm : 
        comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent evt) {
				// We need to update the user interface:
				try {
					updateUserInterfaceAfterAlgorithmSelection(evt.getItem().toString(),
							evt.getStateChange() == ItemEvent.SELECTED);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
        	
        });
        comboBox.setBounds(360, 200, 367, 20);
        contentPane.add(comboBox);

        // The button "Run algorithm"
        buttonRun = new JButton("Run algorithm");
        buttonRun.setEnabled(false);
        buttonRun.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                // When the user clicks "run":
                 processRunAlgorithmCommandFromGUI();

            }
        });
        buttonRun.setBounds(297, 485, 170, 23);
        contentPane.add(buttonRun);


        JLabel lblChooseAnAlgorithm = new JLabel("Choose an algorithm:");
        lblChooseAnAlgorithm.setBounds(119, 197, 204, 20);
        contentPane.add(lblChooseAnAlgorithm);

        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent arg0) {
            	// Show the webpage of the  project
                openWebPage("https://github.com/anurag1120/WebPageRecommender");
            }
        });
        lblNewLabel.setIcon(new ImageIcon(MainWindow.class.getResource("logo.jpg")));
        lblNewLabel.setBounds(500, 0, 320,200 );
        
        contentPane.add(lblNewLabel);
       // MyCanvas m = new MyCanvas();
       // m.setBounds(12, 13, 50, 50);
       // contentPane.add(m);

        textFieldParam1 = new JTextField();
        textFieldParam1.setBounds(360, 290, 157, 20);
        contentPane.add(textFieldParam1);
        textFieldParam1.setColumns(10);
        
      //  {String buffer = new String(new byte[]{83,80,77,70});
       // if(getTitle().startsWith(buffer) != true){setTitle(buffer);}}

        buttonInput = new JButton("...");
        buttonInput.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                askUserToChooseInputFile();
            }
        });
        
        buttonInput.setBounds(527, 231, 32, 23);
        contentPane.add(buttonInput);

        buttonOutput = new JButton("...");
        buttonOutput.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                askUserToChooseOutputFile();
            }
        });
        buttonOutput.setBounds(527, 260, 32, 23);
        contentPane.add(buttonOutput);

        labelParam1 = new JLabel("Parameter 1:");
        labelParam1.setBounds(119, 291, 204, 14);
        contentPane.add(labelParam1);

        labelParam2 = new JLabel("Parameter 2:");
        labelParam2.setBounds(119, 312, 204, 14);
        contentPane.add(labelParam2);

        labelParam3 = new JLabel("Parameter 3:");
        labelParam3.setBounds(119, 341, 204, 14);
        contentPane.add(labelParam3);

        labelParam4 = new JLabel("Parameter 4:");
        labelParam4.setBounds(119, 363, 231, 14);
        contentPane.add(labelParam4);

        labelParam5 = new JLabel("Parameter 5:");
        labelParam5.setBounds(119, 388, 231, 14);
        contentPane.add(labelParam5);

        labelParam6 = new JLabel("Parameter 6:");
        labelParam6.setBounds(119, 413, 231, 14);
        contentPane.add(labelParam6);
        
        labelParam7 = new JLabel("Parameter 7:");
        labelParam7.setBounds(119, 434, 231, 14);
        contentPane.add(labelParam7);

        textFieldParam2 = new JTextField();
        textFieldParam2.setColumns(10);
        textFieldParam2.setBounds(360, 315, 157, 20);
        contentPane.add(textFieldParam2);

        textFieldParam3 = new JTextField();
        textFieldParam3.setColumns(10);
        textFieldParam3.setBounds(360, 340, 157, 20);
        contentPane.add(textFieldParam3);

        textFieldParam4 = new JTextField();
        textFieldParam4.setColumns(10);
        textFieldParam4.setBounds(360, 365, 157, 20);
        contentPane.add(textFieldParam4);

        textFieldParam5 = new JTextField();
        textFieldParam5.setColumns(10);
        textFieldParam5.setBounds(360, 390, 157, 20);
        contentPane.add(textFieldParam5);

        textFieldParam6 = new JTextField();
        textFieldParam6.setColumns(10);
        textFieldParam6.setBounds(360, 415, 157, 20);
        contentPane.add(textFieldParam6);
        
        textFieldParam7 = new JTextField();
        textFieldParam7.setColumns(10);
        textFieldParam7.setBounds(360, 440, 157, 20);
        contentPane.add(textFieldParam7);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(750, 200, 581, 500);
        contentPane.add(scrollPane);

        textArea = new JTextArea();
        Font font = textArea.getFont();
        float size = font.getSize() + 4.0f;
        textArea.setFont( font.deriveFont(size) );
        scrollPane.setViewportView(textArea);

        System.setOut(new PrintStream(new TextAreaOutputStream(textArea)));

        textFieldInput = new JTextField();
        textFieldInput.setEditable(false);
        textFieldInput.setBounds(360, 231, 157, 20);
        contentPane.add(textFieldInput);
        textFieldInput.setColumns(10);

        textFieldOutput = new JTextField();
        textFieldOutput.setEditable(false);
        textFieldOutput.setColumns(10);
        textFieldOutput.setBounds(360, 260, 157, 20);
        contentPane.add(textFieldOutput);
        
        checkboxOpenOutputText = new JCheckBox("text editor");
        checkboxOpenOutputText.setSelected(true);
        checkboxOpenOutputText.setBounds(122, 540, 85, 23);
        contentPane.add(checkboxOpenOutputText);

        lblChooseInputFile = new JLabel("Choose input file");
        lblChooseInputFile.setBounds(119, 234, 97, 14);
        contentPane.add(lblChooseInputFile);

        lblSetOutputFile = new JLabel("Set output file");
        lblSetOutputFile.setBounds(119, 261, 97, 14);
        contentPane.add(lblSetOutputFile);

        lbHelp1 = new JLabel("help1");
        lbHelp1.setBounds(527, 290, 211, 14);
        contentPane.add(lbHelp1);

        lbHelp2 = new JLabel("help2");
        lbHelp2.setBounds(527, 315, 211, 14);
        contentPane.add(lbHelp2);

        lbHelp3 = new JLabel("help3");
        lbHelp3.setBounds(527, 340, 200, 14);
        contentPane.add(lbHelp3);

        lbHelp4 = new JLabel("help4");
        lbHelp4.setBounds(527, 365, 200, 14);
        contentPane.add(lbHelp4);

        lbHelp5 = new JLabel("help5");
        lbHelp5.setBounds(527, 390, 200, 14);
        contentPane.add(lbHelp5);

        lbHelp6 = new JLabel("help6");
        lbHelp6.setBounds(527, 415, 200, 14);
        contentPane.add(lbHelp6);
        
        lbHelp7 = new JLabel("help7");
        lbHelp7.setBounds(527, 440, 200, 14);
        contentPane.add(lbHelp7);
        
        progressBar = new JProgressBar();
        progressBar.setBounds(360, 600, 163, 16);
        contentPane.add(progressBar);
        
        lblOpenOutputFile = new JLabel("Open output file using: ");
        lblOpenOutputFile.setBounds(119, 520, 191, 20);
        contentPane.add(lblOpenOutputFile);
        hideAllParams();
    }



    /**
     * This method updates the user interface according to what the user has selected or unselected
     * in the list of algorithms. For example, if the user choose the "PrefixSpan" algorithm
     * the parameters of the PrefixSpan algorithm will be shown in the user interface.
     * @param algorithmName  the algorithm name. 
     * @throws Exception 
     * @boolean isSelected indicate if the algorithm has been selected or unselected
     */
	private void updateUserInterfaceAfterAlgorithmSelection(String algorithmName, boolean isSelected) throws Exception {
        // COMBOBOX ITEM SELECTION - ITEM STATE CHANGED
        if (isSelected) {
            if(algorithmName=="Recommender"){
                buttonRun.setText("Get Recommendations");

            }else{
                buttonRun.setText("Run Algorithm");
            }
            buttonRun.setEnabled(true);
            
            
            //************************************************************************
            //********* Prepare the user interface for this algorithm  ******* //
            hideAllParams();
            
            JTextField[] textFieldsParams  = new JTextField[]{textFieldParam1, textFieldParam2, textFieldParam3, textFieldParam4,
            		textFieldParam5, textFieldParam6, textFieldParam7};
            
            JLabel[] labelsParams  = new JLabel[]{labelParam1, labelParam2, labelParam3, labelParam4,
            		labelParam5, labelParam6, labelParam7};
            
            AlgorithmManager manager = AlgorithmManager.getInstance();
    		DescriptionOfAlgorithm algorithm = manager.getDescriptionOfAlgorithm(algorithmName);
    		if(algorithm != null){
    			DescriptionOfParameter[] parameters = algorithm.getParametersDescription();
    			for(int i=0; i< parameters.length; i++){
    				DescriptionOfParameter parameter = parameters[i];
                    //System.out.println(parameter.name);
    				String optional = parameter.isOptional ? " (optional)" : "";
    				setParam(textFieldsParams[i], parameter.name + optional, labelsParams[i], parameter.example);
    			}
    			
				if(algorithm.getInputFileTypes() != null){
    				lblChooseInputFile.setVisible(true);
                    buttonInput.setVisible(true);
                    textFieldInput.setVisible(true);
				}
				
				if(algorithm.getOutputFileTypes() != null){
                    if(algorithm.getName()!="Recommender"){
                    lblSetOutputFile.setVisible(true);
                    buttonOutput.setVisible(true);
                    textFieldOutput.setVisible(true);
                    checkboxOpenOutputText.setVisible(true);
                    
                    lblOpenOutputFile.setVisible(true);
                    checkboxOpenOutputText.setVisible(true);
                    }
				}
				//************************************************************************
    		}else {
                // This is for the command line version
                // If the name of the algorithm is not recognized:
                if (isVisible() == false) {
                    System.out.println("There is no algorithm with this name. ");
                }

                hideAllParams();
                buttonRun.setEnabled(false);
            }
        } else {
            // if no algorithm is chosen, we hide all parameters.
            hideAllParams();
            buttonRun.setEnabled(false);
        }
	}

    private  void setParam(JTextField textfield, String name, JLabel label, String helpText) {
        label.setText(name);
        textfield.setEnabled(true);
        textfield.setVisible(true);
        label.setVisible(true);
        if (textfield == textFieldParam1) {
            lbHelp1.setText(helpText);
            lbHelp1.setVisible(true);
        } else if (textfield == textFieldParam2) {
            lbHelp2.setText(helpText);
            lbHelp2.setVisible(true);
        } else if (textfield == textFieldParam3) {
            lbHelp3.setText(helpText);
            lbHelp3.setVisible(true);
        } else if (textfield == textFieldParam4) {
            lbHelp4.setText(helpText);
            lbHelp4.setVisible(true);
        } else if (textfield == textFieldParam5) {
            lbHelp5.setText(helpText);
            lbHelp5.setVisible(true);
        } else if (textfield == textFieldParam6) {
            lbHelp6.setText(helpText);
            lbHelp6.setVisible(true);
        }else if (textfield == textFieldParam7) {
            lbHelp7.setText(helpText);
            lbHelp7.setVisible(true);
        }
    }

//    private  static void setHelpTextForParam(JLabel label, String name) {
//        label.setText(name);
//        label.setVisible(true);
//    }


    /**
     * Hide all parameters from the user interface. This is used to hide fields
     * when the user change algorithms or when the JFrame is first created.
     */
    private  void hideAllParams() {
        labelParam1.setVisible(false);
        labelParam2.setVisible(false);
        labelParam3.setVisible(false);
        labelParam4.setVisible(false);
        labelParam5.setVisible(false);
        labelParam6.setVisible(false);
        labelParam7.setVisible(false);
//		.setVisible(false);
        lbHelp1.setVisible(false);
        lbHelp2.setVisible(false);
        lbHelp3.setVisible(false);
        lbHelp4.setVisible(false);
        lbHelp5.setVisible(false);
        lbHelp6.setVisible(false);
        lbHelp7.setVisible(false);
        textFieldParam1.setVisible(false);
        textFieldParam2.setVisible(false);
        textFieldParam3.setVisible(false);
        textFieldParam4.setVisible(false);
        textFieldParam5.setVisible(false);
        textFieldParam6.setVisible(false);
        textFieldParam7.setVisible(false);

        lblSetOutputFile.setVisible(false);
        buttonOutput.setVisible(false);
        textFieldOutput.setVisible(false);
        lblChooseInputFile.setVisible(false);
        buttonInput.setVisible(false);
        textFieldInput.setVisible(false);
        

        lblOpenOutputFile.setVisible(false);
        checkboxOpenOutputText.setVisible(false);
    }

    static class TextAreaOutputStream extends OutputStream {

        JTextArea textArea;

        public TextAreaOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void flush() {
            textArea.repaint();
        }

        public void write(int b) {
            textArea.append(new String(new byte[]{(byte) b}));
        }
    }


    /**
     * This method open a URL in the default web browser.
     *
     * @param url : URL of the webpage
     */
    private void openWebPage(String url) {
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method show the help webpage for a given algorithm in the default browser of the user.
     * @param choice the algorithm name (e.g. "PrefixSpan")
     * @throws Exception 
     */
    private void openHelpWebPageForAlgorithm(String choice) throws Exception {
    	//************************************************************************
    	AlgorithmManager manager = AlgorithmManager.getInstance();
		DescriptionOfAlgorithm algorithm = manager.getDescriptionOfAlgorithm(choice);
		if(algorithm != null){
			openWebPage(algorithm.getURLOfDocumentation());
		}
		//************************************************************************
	}

    /**
     * This method ask the user to choose the input file. This method is
     * called when the user click on the button to choose the input file.
     */
	private void askUserToChooseInputFile() {
		try {
		    // WHEN THE USER CLICK TO CHOOSE THE INPUT FILE

		    File path;
		    // Get the last path used by the user, if there is one
		    String previousPath = PreferencesManager.getInstance().getInputFilePath();
		    if (previousPath == null) {
		        // If there is no previous path (first time user), 
		        // show the files in the "examples" package of

		        URL main = TestInput.class.getResource("TestInput.class");
		        if (!"file".equalsIgnoreCase(main.getProtocol())) {
		            path = null;
		        } else {
		            path = new File(main.getPath());
		        }
		    } else {
		        // Otherwise, the user used  before, so
		        // we show the last path that he used.
		        path = new File(previousPath);
		    }

		    // Create a file chooser to let the user
		    // select the file.
		    final JFileChooser fc = new JFileChooser(path);
		    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    int returnVal = fc.showOpenDialog(MainWindow.this);

		    // if he chose a file
		    if (returnVal == JFileChooser.APPROVE_OPTION) {
		        File file = fc.getSelectedFile();
		        textFieldInput.setText(file.getName());
		        inputFile = file.getPath(); // remember the file he chose
		    }
		    // remember this folder for next time.
		    if (fc.getSelectedFile() != null) {
		        PreferencesManager.getInstance().setInputFilePath(fc.getSelectedFile().getParent());
		    }
		} catch (Exception e) {
            SwingUtilities.invokeLater(new Runnable(){ public void run() { JOptionPane.showMessageDialog(null,
                "An error occured while opening the input file dialog. ERROR MESSAGE = " + e.toString(), "Error",
                JOptionPane.ERROR_MESSAGE); }});
		  
		}
	}

    /**
     * This method ask the user to choose the output file. This method is
     * called when the user click on the button to choose the input file of the S P M F interface.
     */
	private void askUserToChooseOutputFile() {
		try {
		    // WHEN THE USER CLICK TO CHOOSE THE OUTPUT FILE

		    File path;
		    // Get the last path used by the user, if there is one
		    String previousPath = PreferencesManager.getInstance().getOutputFilePath();
		    // If there is no previous path (first time user), 
		    // show the files in the "examples" package of
		    if (previousPath == null) {
		        URL main = TestInput.class.getResource("TestInput.class");
		        if (!"file".equalsIgnoreCase(main.getProtocol())) {
		            path = null;
		        } else {
		            path = new File(main.getPath());
		        }
		    } else {
		        // Otherwise, use the last path used by the user.
		        path = new File(previousPath);
		    }

		    // ASK THE USER TO CHOOSE A FILE
		    final JFileChooser fc;
		    if (path != null) {
		        fc = new JFileChooser(path.getAbsolutePath());
		    } else {
		        fc = new JFileChooser();
		    }
		    int returnVal = fc.showSaveDialog(MainWindow.this);

		    // If the user chose a file
		    if (returnVal == JFileChooser.APPROVE_OPTION) {
		        File file = fc.getSelectedFile();
		        textFieldOutput.setText(file.getName());
		        outputFile = file.getPath(); // save the file path
		        // save the path of this folder for next time.
		        if (fc.getSelectedFile() != null) {
		            PreferencesManager.getInstance().setOutputFilePath(fc.getSelectedFile().getParent());
		        }
		    }

		} catch (Exception e) {
            SwingUtilities.invokeLater(new Runnable(){ public void run() { JOptionPane.showMessageDialog(null,
                "An error occured while opening the output file dialog. ERROR MESSAGE = " + e.toString(), "Error",
                JOptionPane.ERROR_MESSAGE); }});
		    
		}
		

        String buffer = new String(new byte[]{83,80,77,70});
        if(getTitle().startsWith(buffer) != true){setTitle(buffer);}
	}
	
	
	/**
	 * This method receives a notifications when an algorithm terminates that
	 * was launched by the user by clicking "Run algorithm..."
	 */
	@Override
	public void notifyOfThreadComplete(Thread thread, boolean succeed) {
		
		// IF - the algorithm terminates... and there is an output file
		if (succeed){
            if(lblSetOutputFile.isVisible()){
			// if using wants to use the text editor to open the file
			if(checkboxOpenOutputText.isSelected()) {
			    // open the output file if the checkbox is checked 
			    Desktop desktop = Desktop.getDesktop();
			    // check first if we can open it on this operating system:
			    if (desktop.isSupported(Desktop.Action.OPEN)) {
			        try {
			            // if yes, open it
			            desktop.open(new File(outputFile));
			        } catch (IOException e) {
			            JOptionPane.showMessageDialog(null,
			                    "The output file failed to open with the default application. "
			                    + "\n This error occurs if there is no default application on your system "
			                    + "for opening the output file or the application failed to start. "
			                    + "\n\n"
			                    + "To fix the problem, consider changing the extension of the output file to .txt."
			                    + "\n\n ERROR MESSAGE = " + e.toString(), "Error",
			                    JOptionPane.ERROR_MESSAGE);
			        } catch (SecurityException e) {
			            JOptionPane.showMessageDialog(null,
			                    "A security error occured while trying to open the output file. ERROR MESSAGE = " + e.toString(), "Error",
			                    JOptionPane.ERROR_MESSAGE);
			        } catch (Exception e) {
			            JOptionPane.showMessageDialog(null,
			                    "An error occured while opening the output file. ERROR MESSAGE = " + e.toString(), "Error",
			                    JOptionPane.ERROR_MESSAGE);
			        }
			    }
			}
        }
		buttonRun.setText("Run algorithm");
        progressBar.setIndeterminate(false);
        progressBar.setEnabled(false);
        comboBox.setEnabled(true);
        }
	}
	


	/**
	 * This method receives the notifications when an algorithm launched by the
	 * user throw an exception
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable e) {
		// If the thread just die because the user click on the "Stop algorithm" button
	/*	if(e instanceof ThreadDeath) {
			// we just let the thread die.
		}
		else if(e instanceof NumberFormatException) {
			// if it is a number format exception, meaning that the user enter a string as a parameter instead
			// of an integer or double value.
			JOptionPane.showMessageDialog(null,
                    "Error. Please check the parameters of the algorithm.  The format for numbers is incorrect. \n"
                    + "\n ERROR MESSAGE = " + e.toString(), "Error",
                    JOptionPane.ERROR_MESSAGE);
		}else{
			// If another kind of error occurred while running the algorithm, show the error.
            JOptionPane.showMessageDialog(null,
                    "An error occurred while trying to run the algorithm. \n ERROR MESSAGE = " + e.toString(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } */
		textArea.setText("Sorry Something Went Wrong..\n"+e.getMessage());
        comboBox.setEnabled(true);
        progressBar.setIndeterminate(false);
        progressBar.setVisible(false);
        
	}

	/**
	 * This method is called when the user click the "Run" or "Stop" button of the user interface,
	 * to launch the chosen algorithm and thereafter catch exception if one occurs.
	 */
	private void processRunAlgorithmCommandFromGUI() {
        LinkedList<Rule> rules = new LinkedList<Rule>();
		// If a thread is already running (the user click on the stop Button
		if(currentRunningAlgorithmThread != null &&
				currentRunningAlgorithmThread.isAlive()) {
			// stop that thread
			currentRunningAlgorithmThread.stop();
			
			textArea.setText("Algorithm stopped. \n");
			buttonRun.setText("Run algorithm");
	        progressBar.setIndeterminate(false);
            progressBar.setVisible(false);
	        comboBox.setEnabled(true);
		}
		
		
		// Get the parameters
		final String choice = (String) comboBox.getSelectedItem();
		final String[] parameters = new String[7];
		parameters[0] = textFieldParam1.getText();
		parameters[1] = textFieldParam2.getText();
		parameters[2] = textFieldParam3.getText();
		parameters[3] = textFieldParam4.getText();
		parameters[4] = textFieldParam5.getText();
		parameters[5] = textFieldParam6.getText();
		parameters[6] = textFieldParam7.getText();
		
		// Get the current time
		SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("hh:mm:ss aa");
		String time = dateTimeInGMT.format(new Date());
		
		textArea.setText("Algorithm is running... (" + time + ")  \n");
		
        progressBar.setIndeterminate(true);
        buttonRun.setText("Stop algorithm");
        comboBox.setEnabled(false);
        
		// RUN THE SELECTED ALGORITHM in a new thread
		// create a thread to execute the algorithm
		currentRunningAlgorithmThread = new NotifyingThread() {
            
            @Override
			public void doRun() throws Exception {
                CommandProcessor.runAlgorithm(choice, inputFile, outputFile, parameters);
			}
		};
		// The main thread will listen for the completion of the algorithm
		currentRunningAlgorithmThread.addListener(this);
		// The main thread will also listen for exception generated by the
		// algorithm.
		currentRunningAlgorithmThread.setUncaughtExceptionHandler(this);
		// Run the thread
		currentRunningAlgorithmThread.start();

        try {
            currentRunningAlgorithmThread.join();
        } catch (InterruptedException e) {
            System.out.println("Error in joining threads"+ e.getMessage());
            e.printStackTrace();
        }

        progressBar.setIndeterminate(false);
	}


    
} 

