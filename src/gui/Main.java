package gui;


import java.lang.reflect.Method;

import algorithmmanager.AlgorithmManager;
import algorithmmanager.DescriptionOfAlgorithm;


public class Main {


	/**
	 * Method to launch the software. If there are command line arguments, it means
	 * that the software is launched from the command line. Otherwise, this method
	 * launches the graphical user interface.
	 *
	 * @param args command line arguments.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// If there are command line arguments, we don't launch
		// the user interface. It means that the user is using
		// the command line.
		long heapSize = Runtime.getRuntime().totalMemory();

        //Print the jvm heap size.
        System.out.println("Heap Size = " + heapSize);

		if (args.length != 0) {
			// process command line arguments.
			processCommandLineArguments(args);
		} else {
			// Else, we launch the graphical user interface.
			MainWindow mainWindow = new MainWindow(true, true);
			mainWindow.setVisible(true);
		}
	}

	/**
	 *
	 * @param args command line arguments.
	 */
	public static void processCommandLineArguments(String[] args) {
		// "version" --> show the current version
	 if ("run".equals(args[0])) {

			try {

				// We get the parameters :
				String algoName = null;

				if (args.length > 1) {
					algoName = args[1]; // algorithm name
				}

				// Get the description of the algorithm
				DescriptionOfAlgorithm description = AlgorithmManager.getInstance().getDescriptionOfAlgorithm(algoName);

				// the next argument is 2
				int i = 2;

				String input = null;
				if (description.getInputFileTypes() != null) {
					if (args.length > i) {
						input = args[i]; // input file
					}
					i++;
				}

				String output = null;

				if (description.getOutputFileTypes() != null) {
					if (args.length > i) {
						output = args[i]; // output file
					}
					i++;
				}

				// create an array to store the parameters of the algorithm
				String[] parameters;
				// copy the arguments in the array of parameters:
				if (args.length > i) {
					parameters = new String[args.length - i];
					System.arraycopy(args, i, parameters, 0, args.length - i);
				} else {
					// This happens because the authors has provided no parameter in the command
					// line interface
					parameters = new String[0];
				}

				// run the algorithm:
				CommandProcessor.runAlgorithm(algoName, input, output, parameters);
			} catch (NumberFormatException e) {
				System.out.println(
						"Error. Please check the parameters of the algorithm.  The format for numbers is incorrect. \n"
								+ "\n ERROR MESSAGE = " + e.toString());
			} catch (Exception e) {
				System.out.println("An error while trying to run the algorithm. \n ERROR MESSAGE = " + e.toString());
				e.printStackTrace();
			}
		} // "test" --> this is to run a test file (for developers only).
		else if ("test".equals(args[0])) {
			String testName = args[1];
			try {
				@SuppressWarnings("rawtypes")
				Class testClass = Class.forName("tests." + testName);
				@SuppressWarnings("unchecked")
				Method mainMethod = testClass.getMethod("main", String[].class);
				String[] params = null;
				mainMethod.invoke(null, (Object) params);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			// if any other commands that we don't recognize, we show this:
			System.out.println(
					"\n\n Command not recognized.\n For help, please check again \n \n");
		}
	}
} 
