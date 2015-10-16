package q2etl.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.HashMap;
import java.util.HashSet;


public class Mapper {
	public static void main(String args[]) throws IOException, ParseException, Exception {
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final Set<String> CensorSet = CensorInit("resources/censor.txt");
		final Map<String, Integer> SentiMap = SentiInit("resources/sentiment.txt");
		final JSONParser parser = new JSONParser();
		final SimpleDateFormat formate1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
		final SimpleDateFormat formate2 = new SimpleDateFormat("yyyy-MM-dd");
		String line;
		while ((line = br.readLine()) != null) {
			if(line.equals(" ") || line.isEmpty() || line.equals("")) {
				continue;
			}
			JSONObject layer1 = (JSONObject)parser.parse(line);
			String time1 = layer1.get("created_at").toString();
			String[] split_time1 = time1.split(" ");
			JSONObject user = (JSONObject)layer1.get("user");
			String user_id = user.get("id").toString();
			Date date1 = formate1.parse(time1);
			String date2 = formate2.format(date1);
			String time2 = date2 + " " + split_time1[3];
			String tweet_id = layer1.get("id").toString();
			String text = layer1.get("text").toString();
			int score = calcSentScore(text, SentiMap);
			String censoredText = censorText(text, CensorSet);
			//System.out.println(tweet_id + ":"+ censoredText+"\n");
			censoredText = censoredText.replace("\r\n", "omg").replace("\n", "omg").replace("\r", "omg");
			//System.out.println(tweet_id + ":"+ censoredText+"\n");
			PrintWriter mapout = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"),true);
			mapout.println(user_id + time2 + "\t" + tweet_id + ":" + score + ":" + censoredText + "omg");
		}
		br.close();
	}

	private static int calcSentScore(String text, Map<String, Integer> afinnDict) {

		int score = 0;
		String lowertext = text.toLowerCase();
		StringBuilder word = new StringBuilder();
		for(int i = 0; i < lowertext.length(); i++) {
			char c = lowertext.charAt(i);
			if(Character.isAlphabetic(c) || Character.isDigit(c)) {
				word.append(c);
			} 
			else {
				if(word.length() != 0) {
					if(afinnDict.containsKey(word.toString())) {
						score += afinnDict.get(word.toString());
					}
					word.delete(0, word.length());
				}
			}
		}

		if(word.length() != 0) {
			if(afinnDict.containsKey(word)) {
				score += afinnDict.get(word.toString());
			}
			word.delete(0, word.length());
		}
		return score;
	}
	private static String censorText(String text, Set<String> bannedDict) {
		StringBuilder censoredText = new StringBuilder();
		StringBuilder word = new StringBuilder();
		for(int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if(Character.isAlphabetic(c) || Character.isDigit(c)) {
				word.append(c);
				continue;
			}
			if(word.length() != 0) {
				if(bannedDict.contains(word.toString().toLowerCase())) {
					StringBuilder str = new StringBuilder();
					for(int j = 1; j < word.length() - 1; j++) {
						str.append("*");
					}
					censoredText.append(word.replace(1, word.length() - 1, str.toString()));
				} 
				else {
					censoredText.append(word);
				}
				word.delete(0, word.length());
			}
			censoredText.append(c);
		}
		if(word.length() != 0) {
			if(bannedDict.contains(word.toString().toLowerCase())) {
				StringBuilder str = new StringBuilder();
				for(int j = 1; j < word.length() - 1; j++) {
					str.append("*");
				}
				censoredText.append(word.replace(1, word.length() - 1, str.toString()));
			} 
			else {
				censoredText.append(word);
			}
			word.delete(0, word.length());
		}
		return censoredText.toString();
	}

	private static Set<String> CensorInit(String ifile) throws IOException {
		Set<String> dict = new HashSet<String>();
		//System.out.println(ifile);
		InputStream InStream= Mapper.class.getResourceAsStream(ifile);
		BufferedReader br = new BufferedReader(new InputStreamReader(InStream));    	

		String line;
		while((line = br.readLine()) != null) {
			if(line.isEmpty()) {
				continue;
			}
			//System.out.println(line);
			String word = decodeROT13(line);
			dict.add(word);
		}
		InStream.close();
		br.close();
		return dict;
	}

	private static Map<String, Integer> SentiInit(String ifile) throws IOException {
		Map<String, Integer> dict = new HashMap<String, Integer>();
		//System.out.println(ifile);
		InputStream InStream = Mapper.class.getResourceAsStream(ifile);
		BufferedReader br = new BufferedReader(new InputStreamReader(InStream));    	

		String line;
		while((line = br.readLine()) != null) {
			if(line.isEmpty()) {
				continue;
			}
			//System.out.println(line);
			String[] Array = line.split("\t");
			dict.put(Array[0], Integer.parseInt(Array[1]));
		}
		InStream.close();
		br.close();
		return dict;
	} 

	private static String decodeROT13(String word) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (c == 'a') c = 'n';
			else if (c == 'b') c = 'o';
			else if (c == 'c') c = 'p';
			else if (c == 'd') c = 'q';
			else if (c == 'e') c = 'r';
			else if (c == 'f') c = 's';
			else if (c == 'g') c = 't';
			else if (c == 'h') c = 'u';
			else if (c == 'i') c = 'v';
			else if (c == 'j') c = 'w';
			else if (c == 'k') c = 'x';
			else if (c == 'l') c = 'y';
			else if (c == 'm') c = 'z';
			else if (c == 'n') c = 'a';
			else if (c == 'o') c = 'b';
			else if (c == 'p') c = 'c';
			else if (c == 'q') c = 'd';
			else if (c == 'r') c = 'e';
			else if (c == 's') c = 'f';
			else if (c == 't') c = 'g';
			else if (c == 'u') c = 'h';
			else if (c == 'v') c = 'i';
			else if (c == 'w') c = 'j';
			else if (c == 'x') c = 'k';
			else if (c == 'y') c = 'l';
			else if (c == 'z') c = 'm';
			else if (c == 'A') c = 'N';
			else if (c == 'B') c = 'O';
			else if (c == 'C') c = 'P';
			else if (c == 'D') c = 'Q';
			else if (c == 'E') c = 'R';
			else if (c == 'F') c = 'S';
			else if (c == 'G') c = 'T';
			else if (c == 'H') c = 'U';
			else if (c == 'I') c = 'V';
			else if (c == 'J') c = 'W';
			else if (c == 'K') c = 'X';
			else if (c == 'L') c = 'Y';
			else if (c == 'M') c = 'Z';
			else if (c == 'N') c = 'A';
			else if (c == 'O') c = 'B';
			else if (c == 'P') c = 'C';
			else if (c == 'Q') c = 'D';
			else if (c == 'R') c = 'E';
			else if (c == 'S') c = 'F';
			else if (c == 'T') c = 'G';
			else if (c == 'U') c = 'H';
			else if (c == 'V') c = 'I';
			else if (c == 'W') c = 'J';
			else if (c == 'X') c = 'K';
			else if (c == 'Y') c = 'L';
			else if (c == 'Z') c = 'M';
			str.append(c);
		}
		return str.toString();
	}
}
