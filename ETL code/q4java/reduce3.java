package q4;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;

public class reduce3 {
	public static void main (String args[]) throws IOException, ParseException, Exception {
		
		//	FileInputStream fis = null;
		//	fis = new FileInputStream("/Users/chosen12/Documents/15619/teamproject/q4/output_q4_1/part-q4");
		//	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			
			String line;
			String word = "";
			String currentWord = "";
			int currentCount = 0;
			int count = 1;
			
			PrintWriter redout = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"),true);
			
			while((line = br.readLine()) != null) {
				if(line.equals(" ") || line.isEmpty() || line.equals("")) {
					continue;
				}
				String[] parts = line.split("\t");
				word = parts[0] + parts[1];
				
				if (currentWord.equals(word)) {
					currentCount++;
					redout.println(parts[0] + parts[1] + "\t" + parts[5] + ":" + parts[3]+ "\t" + currentCount);
					}
				
				else {
					currentWord = word;
					currentCount = count;
					redout.println(parts[0] + parts[1] + "\t" + parts[5] + ":" + parts[3]+ "\t" + currentCount);
				}	
			}
			
			if(currentWord.equals(word) == false) {
				String[] parts = line.split("\t");
				currentWord = word;
				currentCount = count;
				redout.println(parts[0] + parts[1] + "\t" + parts[5] + ":" + parts[3]+ "\t" + currentCount);
			}
	}

}
