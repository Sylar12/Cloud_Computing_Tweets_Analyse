package q6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;

public class q6_sum0 {
	public static void main (String args[]) throws IOException, ParseException, Exception {
	//	FileInputStream fis = null;
	//	fis = new FileInputStream("/Users/chosen12/Documents/15619/teamproject/q6/sort0");
	//	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		PrintWriter redout = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"),true);
		String line;
		String left = null;
		String right = "0";
		while((line = br.readLine()) != null) {
			if(line.equals(" ") || line.isEmpty() || line.equals("")) {
				continue;
			}
			String[] parts = line.split("\t");
			left = parts[0];
			redout.println(left + "\t" + right);
			right = parts[1];
		}
		long left_long = Long.parseLong(left) + 1;
		redout.println(left_long + "\t" + right);
	}
}
