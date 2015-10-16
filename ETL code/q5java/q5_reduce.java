package q5;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;


public class q5_reduce {
	public static void main (String args[]) throws IOException, ParseException, Exception {
		
	//	FileInputStream fis = null;
	//	fis = new FileInputStream("/Users/chosen12/Documents/15619/teamproject/q5/sort");
	//	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		String line;
		String word = "";
		String currentWord = "";
		String value = "";
		String currentValue = "";
		List<String> list1 = null;
		List<String> list2 = null;
		List<String> list3 = null;
		
		int currentCount = 0;
		PrintWriter redout = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"),true);
		
		while((line = br.readLine()) != null) {
			if(line.equals(" ") || line.isEmpty() || line.equals("")) {
				continue;
			}
			String[] lengths = line.split("\t");
			int length = lengths.length;
			
			String[] parts = line.split("\t",2);
			word = parts[0];
			value = parts[1];
			int count = 1;				
			
			if (currentWord.equals(word)) {
				currentCount++;
				if (length == 3) {
					String[] value_split = value.split("\t");
					String value0 = value_split[0];
					String value1 = value_split[1];
					list2.add(value0);
					list3.add(value1);
				}
				else {
					list1.add(value);
				}
				currentValue = value;
				}
			
			else {
				if (currentCount == 0) {
					list1 = new ArrayList<String>();
					list2 = new ArrayList<String>();
					list3 = new ArrayList<String>();
					if (length == 3) {
						String[] value_split = value.split("\t");
						String value0 = value_split[0];
						String value1 = value_split[1];
						list2.add(value0);
						list3.add(value1);
					}
					else {
						list1.add(value);
					}
					}		
				
				
				else {
					HashSet a = new HashSet(list1);
				    list1.clear();
				    list1.addAll(a);
				    int num1 = list1.size();
				    int score1 = (1 * num1);
				    
				    HashSet b = new HashSet(list2);
				    list2.clear();
				    list2.addAll(b);
				    int num2 = list2.size();
				    int score2 = (3 * num2);
				    
				    HashSet c = new HashSet(list3);
				    list3.clear();
				    list3.addAll(c);
				    int num3 = list3.size();
				    int score3 = (10 * num3);
				    
				    int plus = score1 + score2 + score3;
				    
				    redout.println(currentWord + "\t" + score1 + "\t" + score2 + "\t" + score3 + "\t" + plus);
				    
				    list1 = new ArrayList<String>();
					list2 = new ArrayList<String>();
					list3 = new ArrayList<String>();
					
					if (length == 3) {
						String[] value_split = value.split("\t");
						String value0 = value_split[0];
						String value1 = value_split[1];
						list2.add(value0);
						list3.add(value1);
					}
					else {
						list1.add(value);
					}
				}	
					
				currentWord = word;
				currentCount = count;
				currentValue = value;
			}
		
		
		}
		
		if(true) {		
			HashSet a = new HashSet(list1);
		    list1.clear();
		    list1.addAll(a);
		    int num1 = list1.size();
		    int score1 = (1 * num1);
		    
		    HashSet b = new HashSet(list2);
		    list2.clear();
		    list2.addAll(b);
		    int num2 = list2.size();
		    int score2 = (3 * num2);
		    
		    HashSet c = new HashSet(list3);
		    list3.clear();
		    list3.addAll(c);
		    int num3 = list3.size();
		    int score3 = (10 * num3);
		    
		    int plus = score1 + score2 + score3;
		    
		    redout.println(currentWord + "\t" + score1 + "\t" + score2 + "\t" + score3 + "\t" + plus);
		}

}
}
