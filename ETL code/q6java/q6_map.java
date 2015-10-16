package q6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class q6_map{
	public static void main(String args[]) throws IOException, ParseException, Exception {
	//	FileInputStream fis = null;
	//	fis = new FileInputStream("/Users/chosen12/Documents/15619/teamproject/15619f14twitter-parta-aa");
	//	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		
		StringBuffer content1 = new StringBuffer();
		String line;
		JSONParser parser = new JSONParser();
		PrintWriter mapout = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"),true);
		while ((line = br.readLine()) != null) {
			if(line.equals(" ") || line.isEmpty() || line.equals("")) {
				continue;
			}
			content1.append(line);
			Object obj;
			obj = parser.parse(content1.toString());
			JSONObject layer1 = (JSONObject)obj;
			
			String tweet_id = layer1.get("id").toString();		//tweet_id
			
			JSONObject user = (JSONObject)layer1.get("user");
			String user_id = user.get("id").toString();			//user_id
			
			JSONObject entities = (JSONObject)layer1.get("entities");
			if (entities.get("media") != null) {
			JSONArray media = (JSONArray) entities.get("media");
			
			int num = 0;
			for (int i = 0; i < media.size(); i++) {
				JSONObject media_i = (JSONObject) media.get(i);
				String type = media_i.get("type").toString();
				if (type.equals("photo")) {
					num++;
					}
				}
			mapout.println(user_id + "\t" + tweet_id + "\t" + num);
			}
			
			content1.delete(0, content1.length());
		}		
		br.close();
	}   
}
