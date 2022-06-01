package input;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class TestInput {

	public static void main(String [] arg) throws IOException{
		String input ;
		String output = ".//output.txt";  // the path for saving the frequent itemsets found
	}
	
	public static String fileToPath(String filename) throws UnsupportedEncodingException{
		URL url = TestInput.class.getResource(filename);
		 return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
	}
}
