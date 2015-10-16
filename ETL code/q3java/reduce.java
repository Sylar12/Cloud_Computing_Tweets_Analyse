package newq3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class reduce {
	public static void main (String args[]) throws IOException, ParseException, Exception {
		
			FileInputStream fis = null;
			fis = new FileInputStream("/Users/chosen12/Documents/15619/teamproject/q3/part-00000 (12)");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		//	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			String line;
			String word = "";
			String currentWord = "";
			String value = "";
			String currentValue = "";
			List<String> list1 = null;
			List<String> list2 = null;
			int length = 0;
			
			int currentCount = 0;
		//	CharSequence Z = "Z";
		//	PrintWriter redout = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"),true);
			
			while((line = br.readLine()) != null) {
				if(line.equals(" ") || line.isEmpty() || line.equals("")) {
					continue;
				}
				String[] lengths = line.split("\t");
				length = lengths.length; 
				String[] parts = line.split("\t",2);
				word = parts[0];
				value = parts[1];
				int count = 1;				
				
				if (currentWord.equals(word)) {
					currentCount++;
					if (length == 3) {
						String[] value_split = value.split("\t");
						String value0 = value_split[1];
						list2.add(value0);
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
						if (length == 3) {
							String[] value_split = value.split("\t");
							String value0 = value_split[1];
							list2.add(value0);
						}
						else {
							list1.add(value);
						}		
					}
					
					if (length == 3) {
						
						Long[] list2_long = new Long[list2.size()];
						List<String> list2_sort_array = null;
						list2_sort_array = new ArrayList<String>();
						List<String> list2_final = null;
						list2_final = new ArrayList<String>();
						
						for (int i = 0; i < list2.size(); i++) {
							Long str_long = Long.parseLong(list2.get(i));
							list2_long[i] = str_long;
						}
						Arrays.sort(list2_long);
						
						for (int i =0; i < list2_long.length; i++) {
							String str = String.valueOf(list2_long[i]);
							list2_sort_array.add(str);
						}
						
				/*		String[] list2_sort_string = new String[list2_sort_array.size()];
						for (int i =0; i < list2_sort_array.size(); i++) {
							list2_sort_string[i] = list2_sort_array.get(i);
						}
						
						Map<String, String> dict = new HashMap<String, String>();
						for (int i = 0; i < list2_sort_string.length; i++) {
							if (dict.containsKey(list2_sort_string[i])) {
								continue;
							}
							else {
								dict.put(list2_sort_string[i], "get");
								String str = list2_sort_string[i];
								list2_final.add(str);
							}
						}*/
						
						Set set  =   new  HashSet(); 
						List newList  =   new  ArrayList(); 
						for  (Iterator iter  =  list2_sort_array.iterator(); iter.hasNext();)   { 
							Object element  =  iter.next(); 
							if  (set.add(element)) 
								newList.add(element); 
							} 
						list2_sort_array.clear(); 
						list2_sort_array.addAll(newList); 
						if (list2_sort_array != null) {
							System.out.print(currentWord + "\t");
						}
						for (String str: list2_sort_array) {
							if (list1.contains(str)) {
								str = "(" + str + ")";
							} 
							else {
							}
							System.out.print(str + ";");
						}
						System.out.println();
						
						list1 = new ArrayList<String>();
						list2 = new ArrayList<String>();
						if (length == 3) {
							String[] value_split = value.split("\t");
							String value0 = value_split[1];
							list2.add(value0);
						}
						else {
							list1.add(value);
						}		
					}
					else {
						list1 = new ArrayList<String>();
						list2 = new ArrayList<String>();
						if (length == 3) {
							String[] value_split = value.split("\t");
							String value0 = value_split[1];
							list2.add(value0);
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
			
			if(currentWord.equals(word) && length == 3) {
				Long[] list2_long = new Long[list2.size()];
				List<String> list2_sort_array = null;
				list2_sort_array = new ArrayList<String>();
				List<String> list2_final = null;
				list2_final = new ArrayList<String>();
				
				for (int i = 0; i < list2.size(); i++) {
					Long str_long = Long.parseLong(list2.get(i));
					list2_long[i] = str_long;
				}
				Arrays.sort(list2_long);
				
				for (int i =0; i < list2_long.length; i++) {
					String str = String.valueOf(list2_long[i]);
					list2_sort_array.add(str);
				}
				
				Set set  =   new  HashSet(); 
				List newList  =   new  ArrayList(); 
			    for  (Iterator iter  =  list2_sort_array.iterator(); iter.hasNext();)   {
			    	Object element  =  iter.next(); 
			    	if  (set.add(element)) 
			    		newList.add(element); 
			    	} 
			    list2_sort_array.clear(); 
			    list2_sort_array.addAll(newList); 
				
			    System.out.print(currentWord + "\t");
				for (String str: list2_sort_array) {
					if (list1.contains(str)) {
						str = "(" + str + ")";
					} 
					else {
					}
					System.out.print(str + ";");
				}
				System.out.println();

			}
	}

}
