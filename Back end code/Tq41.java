package data.q4;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
//import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

public class Tq41 {
	public static Configuration conf = HBaseConfiguration.create();
	public static class Map extends Mapper<LongWritable,Text,ImmutableBytesWritable,Text>{
	    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            Text valueT =new Text();
            String chunk = new String (value.getBytes(), "UTF-8");
	    	StringTokenizer tokenizer = new StringTokenizer(chunk, ";\n");
	    	String line = "";
	    	while (tokenizer.hasMoreTokens()) {
	    		line = tokenizer.nextToken();
	    		String[] attribute = line.split("\\t",5);
                String rowkeyS = attribute[0]+attribute[1];
                valueT.set(attribute[4]+":"+attribute[3]+":"+attribute[2]);
    			byte[] bRowKey = Bytes.toBytes(rowkeyS);
    			ImmutableBytesWritable rowkey = new ImmutableBytesWritable(bRowKey);
                context.write(rowkey, valueT);
	    	}
	    }
	}
	public static class Reduce extends TableReducer<ImmutableBytesWritable,Text,Put> {
        public void reduce(ImmutableBytesWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            //int ii_ = 1;
            Put put = new Put(key.get());
            for(Text val : values) {
            	String tempval = new String(val.getBytes(), "UTF-8");
            	String[] col = tempval.split(":",2); 
           // 	String col = Integer.toString(ii_);
                put.add("family".getBytes(), col[0].getBytes(), col[1].getBytes());
            }
            context.write(null, put);
        }
    }
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "q4loaddata");
		String tableName = args[0];
		Path inputDir = new Path(args[1]);
		job.setJarByClass(Tq41.class);
		FileInputFormat.setInputPaths(job,inputDir);
		job.setInputFormatClass(TextInputFormat.class);
		//job.setOutputValueClass(Text.class);
		//job.setOutputKeyClass(ImmutableBytesWritable.class);
		job.setMapperClass(Map.class);
		job.setMapOutputKeyClass(ImmutableBytesWritable.class);
		job.setMapOutputValueClass(Text.class);

	//	job.setReducerClass(Reduce.class);
		TableMapReduceUtil.initTableReducerJob(tableName, Reduce.class, job);
	//	job.setNumReduceTasks(0);
		TableMapReduceUtil.addDependencyJars(job);
		System.exit(job.waitForCompletion(true)?0:1);
	}

}
