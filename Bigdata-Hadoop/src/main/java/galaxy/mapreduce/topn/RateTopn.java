package galaxy.mapreduce.topn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
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

public class RateTopn {


    static class RateTopnMapper extends Mapper<LongWritable, Text, RateBean, NullWritable> {
        ObjectMapper objectMapper = new ObjectMapper();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            RateBean rateBean = objectMapper.readValue(value.toString(), RateBean.class);
            context.write(rateBean, NullWritable.get());
        }
    }

    static class RateTopnReducer extends Reducer<RateBean, NullWritable, RateBean, NullWritable> {
        @Override
        protected void reduce(RateBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            int topn = context.getConfiguration().getInt("rate.topn", 3);
            int count = 0;
            for (NullWritable value : values) {
                context.write(key, NullWritable.get());
                count++;
                if (count == topn) return;
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        String topn = args[2];
        conf.set("rate.topn", topn);
        Job job = Job.getInstance(conf);

        String in = args[0];
        String out = args[1];

        job.setPartitionerClass(UidPartitioner.class);
        job.setGroupingComparatorClass(UidGroupingComparator.class);

        job.setJarByClass(RateTopn.class);

        job.setMapperClass(RateTopnMapper.class);
        job.setReducerClass(RateTopnReducer.class);

        job.setOutputKeyClass(RateBean.class);
        job.setOutputValueClass(NullWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.setInputPaths(job, new Path(in));

        job.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job, new Path(out));

        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);
    }
}
