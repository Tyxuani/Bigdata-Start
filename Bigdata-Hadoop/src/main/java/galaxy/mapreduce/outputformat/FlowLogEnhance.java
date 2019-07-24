package galaxy.mapreduce.outputformat;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FlowLogEnhance {

    static class FlowLogMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        Map<String, String> ruleMap = new HashMap<>();
        Text text = null;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            DBUtil.load(ruleMap);
            text = new Text();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] fields = line.split(" +");
            String url = fields[1];

            String content = ruleMap.get(url);
            if (StringUtils.isNotBlank(content)) {
                text.set(line + "\t");
                context.write(text, NullWritable.get());
            } else {
                text.set(url + "\001tocrawl");
                context.write(text, NullWritable.get());
            }

        }
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();

        String in = args[0];
        String nomal = args[1];
        String enhance = args[2];

        conf.set("out.nomal.path", nomal);
        conf.set("out.enhance.path", enhance);
        Job job = Job.getInstance(conf);

        job.setMapperClass(FlowLogMapper.class);


        job.setJarByClass(FlowLogEnhance.class);


        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.setInputPaths(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(nomal));

        job.setOutputFormatClass(EnhanceOutputFormat.class);

        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);
    }
}
