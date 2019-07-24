package galaxy.mapreduce.topn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class RateHot {

    static class RateHotMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        ObjectMapper objectMapper = new ObjectMapper();
        IntWritable tag = new IntWritable(1);
        Text movie = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            RateBean rateBean = objectMapper.readValue(value.toString(), RateBean.class);
            movie.set(rateBean.getMovie());
            context.write(movie, tag);
        }
    }

    static class RateHotReducer extends Reducer<Text, IntWritable, HotBean, NullWritable> {
        List<HotBean>  hotBeans = new ArrayList<>();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable value: values){
                sum += value.get();
            }
            hotBeans.add(new HotBean(key.toString(), sum));
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            int topn = context.getConfiguration().getInt("rate.hot", 5);
            int count = 0;
            for (HotBean value : hotBeans) {
                context.write(value, NullWritable.get());
                if (count ++ == topn) return;
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        String topn = args[2];
        conf.set("rate.hot", topn);
        Job job = Job.getInstance(conf);

        String in = args[0];
        String out = args[1];

        job.setJarByClass(RateHot.class);

        job.setMapperClass(RateHotMapper.class);
        job.setReducerClass(RateHotReducer.class);

        job.setOutputKeyClass(HotBean.class);
        job.setOutputValueClass(NullWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.setInputPaths(job, new Path(in));

        job.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job, new Path(out));

        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);
    }
}
