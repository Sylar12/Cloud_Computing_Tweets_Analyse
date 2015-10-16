package q4;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;

public class reduce1 {
	public static void main (String args[]) throws IOException, ParseException, Exception {
		
	//	FileInputStream fis = null;
	//	fis = new FileInputStream("/Users/chosen12/Documents/15619/teamproject/q4/2.txt");
	//	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		String line;
		String left;
		String right;
		String tweetID;
		String indices;
		PrintWriter redout = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"),true);
		
		while((line = br.readLine()) != null) {
			if(line.equals(" ") || line.isEmpty() || line.equals("")) {
				continue;
			}
			String[] parts = line.split("\t");
			left = parts[0];
			right = parts[1];
			String[] right_parts = right.split(";");
			tweetID = right_parts[0];
			indices = right_parts[1];
			
			redout.println(left + ";" + tweetID + "\t" + indices);
			
		}	
	}
}
