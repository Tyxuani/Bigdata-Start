package galaxy.mapreduce.outputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

public class EnhanceMuti {
    static class EnhaceMapper extends Mapper<LongWritable, Text, Text, NullWritable> {


        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] words = line.split(" +");
            for (String word : words) {
                context.write(new Text(word), NullWritable.get());
            }
        }
    }

    static class EnhanceReducer extends Reducer<Text, NullWritable, Text, NullWritable> {
        MultipleOutputs mof = null;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            mof = new MultipleOutputs<>(context);
        }

        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            String word = key.toString();
            if (word.startsWith("a")) {
                mof.write("A", new Text(key), NullWritable.get(), "a/");
            } else if (word.startsWith("b")) {
                mof.write("B", new Text(word), NullWritable.get(), "b/");
            } else {
                mof.write("O", new Text(word), NullWritable.get(), "o/");
            }

        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            mof.close();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();

        String in = args[0];
        String out = args[1];

        Job job = Job.getInstance(conf);

        job.setJarByClass(EnhanceMuti.class);
        job.setMapperClass(EnhaceMapper.class);
        job.setReducerClass(EnhanceReducer.class);


        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(out));

        MultipleOutputs.addNamedOutput(job, "A", TextOutputFormat.class, Text.class, NullWritable.class);
        MultipleOutputs.addNamedOutput(job, "B", TextOutputFormat.class, Text.class, NullWritable.class);
        MultipleOutputs.addNamedOutput(job, "O", TextOutputFormat.class, Text.class, NullWritable.class);


        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);
    }
}
