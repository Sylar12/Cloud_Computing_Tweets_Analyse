package q3.load;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

public class q3loaddata {
	public static Configuration conf = HBaseConfiguration.create();
	public static class Map extends Mapper<LongWritable,Text,ImmutableBytesWritable,Put>{
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String chunk = value.toString();
			StringTokenizer tokenizer = new StringTokenizer(chunk, "\n");
			String line = "";
			while (tokenizer.hasMoreTokens()) {
				line = tokenizer.nextToken();
				String[] attribute = line.split("\\t",2);
				if(attribute[0] == "" || attribute[0] == " "){
					continue;
				}
				else{
					try {
						String rowkeyS = StringUtils.leftPad(attribute[0], 20, "+");
						byte[] bRowKey = Bytes.toBytes(rowkeyS);
//						byte[] bRowKey = Bytes.toBytes(attribute[0]);
						//byte[] bRowKey = Bytes.toBytes(Integer.parseInt(attribute[0]));
						ImmutableBytesWritable rowkey = new ImmutableBytesWritable(bRowKey);
						Put put = new Put(bRowKey);
						
						put.add("family".getBytes(), "qualifier".getBytes(), attribute[1].getBytes());
						context.write(rowkey, put);
					}
					catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		//		Job job = new Job(conf, "q3loaddata");
		//		Job job = new Job(conf, "q5loaddata");
//		Job job = new Job(conf, "q2loaddata");
				Job job = new Job(conf, "q6loaddata");
		String tableName = args[0];
		Path inputDir = new Path(args[1]);
		job.setJarByClass(q3loaddata.class);
		FileInputFormat.setInputPaths(job,inputDir);
		job.setInputFormatClass(TextInputFormat.class);
		job.setMapperClass(Map.class);
		TableMapReduceUtil.initTableReducerJob(tableName,null, job);
		job.setNumReduceTasks(0);
		TableMapReduceUtil.addDependencyJars(job);
		System.exit(job.waitForCompletion(true)?0:1);
	}

}
