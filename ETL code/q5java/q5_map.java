package q5;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class q5_map{
	public static void main(String args[]) throws IOException, ParseException, Exception {
	//	FileInputStream fis = null;
	//	fis = new FileInputStream("/Users/chosen12/Documents/15619/teamproject/15619f14twitter-parta-aa");
	//	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

		StringBuffer content1 = new StringBuffer();
		PrintWriter mapout = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"),true);
		
			String line;
			List<String> list_id_B = null;
			list_id_B = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				if(line.equals(" ") || line.isEmpty() || line.equals("")) {
					continue;
				}
				content1.append(line);
				JSONParser parser = new JSONParser();
				Object obj;
				obj = parser.parse(content1.toString());
				JSONObject layer1 = (JSONObject)obj;
				
				String tweet_id = layer1.get("id").toString();		//tweet_id
				
				JSONObject user = (JSONObject)layer1.get("user");
				String user_id = user.get("id").toString();			//user_id
				
				String id_A = user_id + "\t" + tweet_id;
				mapout.println(id_A);
				
				if (layer1.containsKey("retweeted_status")) {
					JSONObject retweeted_status = (JSONObject)layer1.get("retweeted_status");
					JSONObject reuser = (JSONObject)retweeted_status.get("user");
					
					String oriuser_id = reuser.get("id").toString();	//oriuser_id	
					String id_B = oriuser_id + "\t" + tweet_id + "\t" + user_id;
					
					list_id_B.add(id_B);
					mapout.println(id_B);
				}			
				content1.delete(0, content1.length());
			}
			br.close();
	}
}	
