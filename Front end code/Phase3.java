package undertowhbase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
//import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Result;
//import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.client.HTableInterface;
//import org.apache.hadoop.hbase.filter.PageFilter;


public class Phase3{
	//static HTable table2;
	//static HTable table3;
	//static HTable table4;
	//static HTable table5;

	private static HTablePool pool;
	private static int MAX=1000;
	private static void prepareHBaseConnection() throws IOException {
		Configuration conf = HBaseConfiguration.create();
		pool = new HTablePool(conf, MAX);
		HTableInterface[] tables = new HTableInterface[1000];
		for (int n = 0; n < MAX; n++) {
			tables[n] = pool.getTable("q2table");
			//System.out.println("pool");
		}
		for (HTableInterface table : tables) {
			table.close();
		}
	}
	//private HTablePool datasource;
	/*
	public XYZDAO(final HTablePool pool)
	{
	    this.datasource = pool;
	}

	public void doSomething(...)
	{
	    HTableInterface aTable = datasource.getTable(...);
	    aTable.get(...);
	    aTable.close();
	}
	 */
	//static HTable table6;
	/*
	private static void prepareHBaseConnection() {
		Configuration config = HBaseConfiguration.create();
		try {
			table2 = new HTable(config, Bytes.toBytes("q2table"));
			table3 = new HTable(config, Bytes.toBytes("q3table"));
			table4 = new HTable(config, Bytes.toBytes("q4table"));
			table5 = new HTable(config, Bytes.toBytes("q5table"));
			//table6 = new HTable(config, Bytes.toBytes("q6table"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 */


	public static String getq2entry(String rowkey) throws IOException {
		HTableInterface table = pool.getTable("q2table");
		Get get = new Get(Bytes.toBytes(rowkey));
		Result results = table.get(get);
		byte[] columns = results.getValue(Bytes.toBytes("family"), Bytes.toBytes("qualifier"));
		String ans = new String(columns, "utf-8");
		table.close();
		return ans;
	}
	public static String getq3entry(String rowkey) throws IOException {
		HTableInterface table = pool.getTable("q3table");
		Get get = new Get(Bytes.toBytes(rowkey));
		Result results = table.get(get);
		byte[] columns = results.getValue(Bytes.toBytes("family"), Bytes.toBytes("qualifier"));
		String ans = Bytes.toString(columns);
		table.close();
		return ans;
	}
	public static String getq5entry(String rowkey) throws IOException {
		HTableInterface table = pool.getTable("q5table");
		Get get = new Get(Bytes.toBytes(rowkey));
		Result results = table.get(get);
		byte[] columns = results.getValue(Bytes.toBytes("family"), Bytes.toBytes("qualifier"));
		String ans = Bytes.toString(columns);
		table.close();
		return ans;
	}
	/*
	public static String getq6entry(String rowkey) throws IOException {
		HTableInterface table = pool.getTable("q6table");
		Get get = new Get(Bytes.toBytes(rowkey));
		Result results = table.get(get);
		byte[] columns = results.getValue(Bytes.toBytes("family"), Bytes.toBytes("qualifier"));
		String ans = Bytes.toString(columns);
		table.close();
		return ans;
	}
	*/
	public static String scanq6entry(String rowkey) throws IOException {
		HTableInterface table = pool.getTable("q6table");
		Scan scan = new Scan();
		scan.setFilter(new PageFilter(1));
		scan.setStartRow(rowkey.getBytes());
		ResultScanner scanner = table.getScanner(scan);
		String ans ="";
		//scanner.toString();
		//scan.setFilter(filter);
		for(Result result : scanner) {
			byte[] column = result.getValue(Bytes.toBytes("family"), Bytes.toBytes("qualifier"));
			ans =Bytes.toString(column);
			//System.out.println(ans);
		}
		table.close();
		return ans;
	}

	private static String twoquery(String uid, String tweettime) throws Exception {
		String rowkey = uid + tweettime;
		String ans = getq2entry(rowkey);
		ans = ans.replace("omg", "\n");
		return ans;
	}
	private static String threequery(String userid) throws Exception {
		String rowkey = userid;
		String ans = getq3entry(rowkey);
		ans = ans.replace(";", "\n");
		return ans;
	}
	
	private static String fourquery(String udate,String location,int m,int n) throws Exception {
		String rowkey = udate + location;
		String ans = getHBaseColumns(rowkey, m, n);
		return ans;
	}

	public static String getHBaseColumns(String rowkey, int m, int n) throws IOException {
		HTableInterface table = pool.getTable("q4table");
		Get get = new Get(Bytes.toBytes(rowkey));
		byte[] bytefamily = Bytes.toBytes("family");
		for(int ii_=m; ii_<=n; ii_++) {
			get.addColumn(bytefamily, Bytes.toBytes(Integer.toString(ii_)));
		}
		Result results = table.get(get);
		String ans = "";
		for(int ii_=m; ii_<=n; ii_++) {
			byte[] columns = results.getValue(bytefamily, Bytes.toBytes(Integer.toString(ii_)));
			String columnStr = new String(columns, "utf-8");
			if(columnStr!=null&&columnStr!="") {
				ans += (columnStr + "\n");
			}
		}
		table.close();
		return ans;
	}
	/*
	private static String fourquery(String udate,String location,int m,int n) throws Exception {
		String rowkey = udate + location;
		String ans = getHBaseColumns(rowkey, m, n);
		return ans;
	}
	*/
	public static String getq6entry(String rowkey) throws IOException {
		HTableInterface table = pool.getTable("q6table");
		Get get = new Get(Bytes.toBytes(rowkey));
		Result results = table.get(get);
		byte[] columns = results.getValue(Bytes.toBytes("family"), Bytes.toBytes("qualifier"));
		String ans = Bytes.toString(columns);
		table.close();
		return ans;
	}
	private static String fivequery(String uidA,String uidB) throws Exception {
		String[] resultA = getq5entry(uidA).split("\\t",4);
		String[] resultB = getq5entry(uidB).split("\\t",4);
		String ans = String.format("%s\t%s\tWINNER\n", uidA, uidB);
		String t1WINNER="";
		if(Integer.parseInt(resultA[0])>Integer.parseInt(resultB[0])) {
			t1WINNER=uidA;
		}
		else if(Integer.parseInt(resultA[0])<Integer.parseInt(resultB[0])) {
			t1WINNER=uidB;
		}
		else {
			t1WINNER="X";
		}
		String t2WINNER="";
		if(Integer.parseInt(resultA[1])>Integer.parseInt(resultB[1])) {
			t2WINNER=uidA;
		}
		else if(Integer.parseInt(resultA[1])<Integer.parseInt(resultB[1])) {
			t2WINNER=uidB;
		}
		else {
			t2WINNER="X";
		}
		String t3WINNER="";
		if(Integer.parseInt(resultA[2])>Integer.parseInt(resultB[2])) {
			t3WINNER=uidA;
		}
		else if(Integer.parseInt(resultA[2])<Integer.parseInt(resultB[2])) {
			t3WINNER=uidB;
		}
		else {
			t3WINNER="X";
		}
		String t4WINNER="";
		if(Integer.parseInt(resultA[3])>Integer.parseInt(resultB[3])) {
			t4WINNER=uidA;
		}
		else if(Integer.parseInt(resultA[3])<Integer.parseInt(resultB[3])) {
			t4WINNER=uidB;
		}
		else {
			t4WINNER="X";
		}
		ans += String.format("%s\t%s\t%s\n", resultA[0], resultB[0], t1WINNER);
		ans += String.format("%s\t%s\t%s\n", resultA[1], resultB[1], t2WINNER);
		ans += String.format("%s\t%s\t%s\n", resultA[2], resultB[2], t3WINNER);
		ans += String.format("%s\t%s\t%s\n", resultA[3], resultB[3], t4WINNER);
		return ans;
	}

	private static String sixquery(String uidA,String uidB) throws Exception {
		long uidAi = Long.parseLong(uidA);
		long uidBi = Long.parseLong(uidB);
		if(uidAi > uidBi) {
			return "0\n";
		}
		String rowkeyS1 = StringUtils.leftPad(uidA, 20, "+");
		//System.out.println(rowkeyS1);
		if(uidAi == uidBi) {
			String ttget = getq6entry(rowkeyS1);
			if(ttget==null||ttget=="") {
				return "0\n";
			}
			return (ttget+"\n");
		}
		String Ascore = scanq6entry(rowkeyS1);
		String rowkeyS2 = StringUtils.leftPad(String.valueOf(uidBi+1), 20, "+");
		//System.out.println(rowkeyS2);
		String Bscore = scanq6entry(rowkeyS2);
		int ansi = Integer.parseInt(Bscore)-Integer.parseInt(Ascore);
		return String.valueOf(ansi)+"\n";
	}

	//private static String sixquery(String uidA,String uidB) throws Exception {
	public static void main(final String[] args) throws IOException {
		final SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final String teaminfo = "BestExcavator,9391-7050-0806,8376-9634-6431,2137-4008-5615\n";
		final String pub_key = "6876766832351765396496377534476050002970857483815262918450355869850085167053394672634315391224052153";
		final BigDecimal public_key = new BigDecimal(pub_key);
		final ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<String, String>();
		//pool();
		prepareHBaseConnection();
		Undertow server = Undertow.builder()
				.setWorkerThreads(4096)
				.setIoThreads(Runtime.getRuntime().availableProcessors() * 2)
				.setServerOption(UndertowOptions.ALWAYS_SET_KEEP_ALIVE, true)
				.setBufferSize(1024*128)
				.addHttpListener(80, "0.0.0.0")
				.setHandler(new HttpHandler() {
					public void handleRequest(final HttpServerExchange exchange) throws Exception {
						String path = exchange.getRequestPath();
						String num = "";

						exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
						if (path.startsWith("/q1")){
							Map<String, Deque<String>> queryInPar = exchange.getQueryParameters();
							String key = queryInPar.get("key").getFirst();
							if (hashMap.containsKey(key)){
								num = hashMap.get(key);
							}
							else{
								BigDecimal key_value = new BigDecimal(key);
								num = (key_value.divide(public_key)).toString();
								hashMap.put(key, num);
							}

							String info = num.toString() + "\n" + teaminfo;
							exchange.getResponseSender().send(info + date_format.format(new Date()) + "\n");
						}
						else if (path.startsWith("/q2")){
							Map<String, Deque<String>> queryInPar = exchange.getQueryParameters();
							String userid = queryInPar.get("userid").getFirst();
							String tweet_time = queryInPar.get("tweet_time").getFirst();
							String[] time_all = tweet_time.split(" ");
							String time_final = time_all[0] + " " + time_all[1];
							String results = twoquery(userid, time_final);
							String info = teaminfo + results;
							exchange.getResponseSender().send(info);
						}
						else if (path.startsWith("/q3")){
							Map<String, Deque<String>> queryInPar = exchange.getQueryParameters();
							String userid = queryInPar.get("userid").getFirst();
							String results = threequery(userid);
							String info = teaminfo + results;
							exchange.getResponseSender().send(info);
						}
						else if (path.startsWith("/q4")){
							Map<String, Deque<String>> queryInPar = exchange.getQueryParameters();
							String udate = queryInPar.get("date").getFirst();
							String location = queryInPar.get("location").getFirst();
							int m = Integer.parseInt(queryInPar.get("m").getFirst());
							int n = Integer.parseInt(queryInPar.get("n").getFirst());
							String results = fourquery(udate, location, m, n);
							String info = teaminfo + results;
							exchange.getResponseSender().send(info);
						}
						else if (path.startsWith("/q5")){
							Map<String, Deque<String>> queryInPar = exchange.getQueryParameters();
							String uidA = queryInPar.get("m").getFirst();
							String uidB = queryInPar.get("n").getFirst();
							String results = fivequery(uidA, uidB);
							String info = teaminfo + results;
							exchange.getResponseSender().send(info);
						}
						else if (path.startsWith("/q6")){
							Map<String, Deque<String>> queryInPar = exchange.getQueryParameters();
							String uidA = queryInPar.get("m").getFirst();
							String uidB = queryInPar.get("n").getFirst();
							String results = sixquery(uidA, uidB);
							String info = teaminfo + results;
							exchange.getResponseSender().send(info);
						}
					}
				}).build();
		server.start();
	}
}

