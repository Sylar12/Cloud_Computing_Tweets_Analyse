package q3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class map{
	public static void main(String args[]) throws IOException, ParseException, Exception {
	//	FileInputStream fis = null;
	//	fis = new FileInputStream("/Users/chosen12/Documents/15619/teamproject/15619f14twitter-parta-aa");
	//	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringBuffer content1 = new StringBuffer();
		PrintWriter mapout = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"),true);
		
			String line;
			while ((line = br.readLine()) != null) {
				if(line.equals(" ") || line.isEmpty() || line.equals("")) {
					continue;
				}
				content1.append(line);
				JSONParser parser = new JSONParser();
				Object obj;
				obj = parser.parse(content1.toString());
				JSONObject layer1 = (JSONObject)obj;
				
				JSONObject user = (JSONObject)layer1.get("user");
				String user_id = user.get("id").toString();
				String reuser_id;
				
				if (layer1.containsKey("retweeted_status")) {
					JSONObject retweeted_status = (JSONObject)layer1.get("retweeted_status");
					JSONObject reuser = (JSONObject)retweeted_status.get("user");
					reuser_id = reuser.get("id").toString();
					
					String id_A = user_id + "\t" + reuser_id;
					String id_B = reuser_id + "\t" + "Z" + "\t" + user_id;
					
					mapout.println(id_A);
					mapout.println(id_B);
				}											
				
				content1.delete(0, content1.length());
			}			
			br.close();
	}
}	
	
	 
