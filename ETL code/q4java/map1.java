package q4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class map1{
	public static void main(String args[]) throws IOException, ParseException, Exception {
	//	FileInputStream fis = null;
	//	fis = new FileInputStream("/Users/chosen12/Documents/15619/teamproject/15619f14twitter-parta-aa");
	//	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
	//	File output = new File("output.txt");
	//	FileWriter fw = new FileWriter(output,true);
	//	BufferedWriter bw = new BufferedWriter(fw);
		
		StringBuffer content1 = new StringBuffer();
		String line;
		JSONParser parser = new JSONParser();
		PrintWriter mapout = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"),true);
		while ((line = br.readLine()) != null) {
			if(line.equals(" ") || line.isEmpty() || line.equals("")) {
				continue;
			}
			content1.append(line);
		//	JSONParser parser = new JSONParser();
			Object obj = parser.parse(content1.toString());
			JSONObject layer1 = (JSONObject)obj;
			String time1 = layer1.get("created_at").toString();
		//	String[] split_time1 = time1.split(" ");
			
			JSONObject user = (JSONObject)layer1.get("user");
				
			SimpleDateFormat formate1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
			Date date1 = formate1.parse(time1);
			SimpleDateFormat formate2 = new SimpleDateFormat("yyyy-MM-dd");
			String date2 = formate2.format(date1);
		//	String time2 = date2 + " " + split_time1[3];
			String tweet_id = layer1.get("id").toString();
			long tweet_id_int = Long.parseLong(tweet_id);
			String tweet_id_str = String.format("%020d", tweet_id_int);
			
			String time_zone = "";		
			String place_name = "";
			String location = "";
			
			if(layer1.get("place") != null) {
				JSONObject place = (JSONObject)layer1.get("place");
				if(place.get("name") != null) {
					place_name = place.get("name").toString();
					location = place_name;
				}
				else {
					content1.delete(0, content1.length());
					continue;
				}
			}
			else if (user.get("time_zone") != null) {
				time_zone = user.get("time_zone").toString();
				
				String pattern = "\\btime\\b";
				Pattern r = Pattern.compile(pattern);
				Matcher m = r.matcher(time_zone.toLowerCase());
				if (m.find( )) {
					content1.delete(0, content1.length());
					continue;
				}
				else {
					location = time_zone;
				}
			}			
			else {
				content1.delete(0, content1.length());
				continue;
			}
			
			JSONObject entities = (JSONObject)layer1.get("entities");
		//	String content2 = "";
			
			if (entities.get("hashtags").toString().equals("[]")) {
				content1.delete(0, content1.length());
				continue;
			}
			else {
				JSONArray hashtags = (JSONArray) entities.get("hashtags");
				int hashtags_length = hashtags.size();
				
				Map<String, String> dict = new HashMap<String, String>();
				for (int i = 0; i < hashtags_length; i++) {

					JSONObject hashtag = (JSONObject) hashtags.get(i);
					String hashtag_text = (String) hashtag.get("text");
					String hashtag_indices = hashtag.get("indices").toString();
					String[] split_indices = hashtag_indices.split(",");
					String indices = split_indices[0];
					indices = indices.replace("[","");
				//	int indices_int = Integer.parseInt(indices);
				//	String indices_str = String.format("%03d", indices_int);
					
					if (dict.containsKey(hashtag_text)) {
						continue;
					}
					else {
						dict.put(hashtag_text, indices);
						mapout.println(date2 + ";" + location + ";" + hashtag_text + "\t" + tweet_id_str + ";" + indices);
					//	content2 = hashtag_text + "\t" + indices_1 + "\t" + location + "\t" + tweet_id + "\t" + date2 + "\n";
					//	bw.write(content2);
					}
				}
			}		
			content1.delete(0, content1.length());
		}		
		br.close();
	//	bw.close();
	}   
}
