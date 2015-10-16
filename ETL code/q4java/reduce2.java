package q4;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;

public class reduce2 {
	public static void main (String args[]) throws IOException, ParseException, Exception {
		
		//	FileInputStream fis = null;
		//	fis = new FileInputStream("/Users/chosen12/Documents/15619/teamproject/q4/output_q4_1/part-sort");
		//	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
			
			String line;
			String word = "";
			String currentWord = "";
			String value = "";
			String currentValue = "";
			String indices = "";
			String indices_str = "";
			String content = "";
			String currentContent = "";
			String pastValue = "";
			
			
			int currentCount = 0;
			PrintWriter redout = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"),true);
			
			while((line = br.readLine()) != null) {
				if(line.equals(" ") || line.isEmpty() || line.equals("")) {
					continue;
				}
				String[] parts = line.split("[;|\t]");
				word = parts[2];
				content = parts[0]+"\t"+parts[1]+"\t"+parts[2];
				value = parts[3];
				long value_int = Long.parseLong(value);
				String value_str = String.format("%01d", value_int);
				int count = 1;
				
				if (currentWord.equals(word)) {
					if (value_str.equals(pastValue) == false) {
						currentCount++;
						currentValue = currentValue + "," + value_str;
						pastValue = value_str;
					}
					}
				
				else {
					if (currentCount != 0) {
						String[] currentContents = currentContent.split("\t");
						String ScurrentCount = String.valueOf(currentCount);
						Double DcurrentCount = Double.parseDouble(ScurrentCount);
						Double currentCounts = (1 / DcurrentCount);
						redout.println(currentContents[0] + "\t" + currentContents[1] + "\t" + currentCounts + "\t" + currentValue + "\t" + indices_str + "\t" + currentContents[2]);
					}
					indices = parts[4];
					int indices_int = Integer.parseInt(indices);
					indices_str = String.format("%05d", indices_int);
					currentWord = word;
					currentContent = content;
					currentCount = count;
					currentValue = value_str;
					pastValue = value_str;
				}	
			}
			
			if(currentWord.equals(word)) {
				String[] currentContents = currentContent.split("\t");
				String ScurrentCount = String.valueOf(currentCount);
				Double DcurrentCount = Double.parseDouble(ScurrentCount);
				Double currentCounts = (1 / DcurrentCount);
				redout.println(currentContents[0] + "\t" + currentContents[1] + "\t" + currentCounts + "\t" + currentValue + "\t" + indices_str + "\t" + currentContents[2]);
			}
	}

}
