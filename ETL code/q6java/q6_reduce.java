package q6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class q6_reduce {
	public static void main (String args[]) throws IOException, ParseException, Exception {
		
		//	FileInputStream fis = null;
		//	fis = new FileInputStream("/Users/chosen12/Documents/15619/teamproject/q6/sort");
		//	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			
			String line;
			String word = "";
			String currentWord = "";
			String value = "";
			String currentValue = "";				
			int currentCount = 0;
			int count = 0;
			Map<String, String> dict = new HashMap<String, String>();
			
			PrintWriter redout = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"),true);
			
			while((line = br.readLine()) != null) {
				if(line.equals(" ") || line.isEmpty() || line.equals("")) {
					continue;
				}
				String[] parts = line.split("\t");
				word = parts[0];
				value = parts[1];
				count = Integer.parseInt(parts[2]);
				
				if (currentWord.equals(word)) {
					if (dict.containsKey(value)) {
						continue;
					}
					currentCount += count;
					dict.put(value, "only");
					}
				
				else {
					if (currentCount != 0) {
						redout.println( currentWord + "\t" + currentCount);
					}
					currentWord = word;
					currentValue = value;
					currentCount = count;
					
					dict = new HashMap<String, String>();
					dict.put(currentValue, "only");
				}	
			}
			
			if(currentWord.equals(word)) {
				redout.println(currentWord + "\t" + currentCount);
			}
	}

}
