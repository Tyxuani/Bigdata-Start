package galaxy.mapreduce.partition;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PartitionSubmit {

    private static List<String> part = Arrays.asList("重庆", "上海", "北京", "杭州", "其他地方");

    public static class ParMapper extends Mapper<LongWritable, Text, Text, AccessBean> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] lines = value.toString().split(" ");
            String poneNum = lines[0];
            int up = Integer.parseInt(lines[2]);
            int down = Integer.parseInt(lines[4]);
            context.write(new Text(poneNum), new AccessBean(poneNum, up, down));
        }
    }

    public static class ParReducer extends Reducer<Text, AccessBean, Text, LongWritable> {
        @Override
        protected void reduce(Text key, Iterable<AccessBean> values, Context context) throws IOException, InterruptedException {
            long count = 0L;
            for (AccessBean value : values) {
                int sum = value.getUpLoad() + value.getDownLoad();
                value.setSum(sum);
                count += sum;
            }
            int addr = Integer.parseInt(key.toString().substring(0, 2));

            String provence = null;
            switch (addr) {
                case 13:
                    provence = part.get(0);
                    break;
                case 15:
                    provence = part.get(1);
                    break;
                case 18:
                    provence = part.get(2);
                    break;
                case 17:
                    provence = part.get(3);
                    break;
                default:
                    provence = part.get(4);
                    break;
            }
            context.write(new Text(provence), new LongWritable(count));
        }
    }

    public static class UDPartitioner extends Partitioner<Text, AccessBean> {

        @Override
        public int getPartition(Text key, AccessBean value, int numPartitions) {
            int addr = Integer.parseInt(key.toString().substring(0, 2));
            switch (addr) {
                case 13:
                    return 0;
                case 15:
                    return 1;
                case 18:
                    return 2;
                case 17:
                    return 3;
                default:
                    return 4;
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        String parNum = args[2];
        conf.set("mapreduce.task.partition", parNum);
        Job job = Job.getInstance(conf);

        String in = args[0];
        String out = args[1];

        job.setPartitionerClass(UDPartitioner.class);

        job.setJarByClass(PartitionSubmit.class);

        job.setMapperClass(ParMapper.class);
        job.setReducerClass(ParReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(AccessBean.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        //指定文件读取的组件(普通文本)
        job.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.setInputPaths(job, new Path(in));

        //指定mr结果输出
        job.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job, new Path(out));

        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);
    }
}
