package galaxy.mapreduce.outputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class EnhanceOutputFormat extends FileOutputFormat<Text, NullWritable> {

    @Override
    public RecordWriter<Text, NullWritable> getRecordWriter(TaskAttemptContext jobContext) throws IOException, InterruptedException {
        Configuration conf = jobContext.getConfiguration();
        FileSystem fs = FileSystem.get(conf);

        FSDataOutputStream noout = fs.create(new Path(conf.get("out.nomal.path", "")));
        FSDataOutputStream enout = fs.create(new Path(conf.get("out.enhance.path", "")));

        return new EnhanceRecordWriter(noout, enout);
    }

    static class EnhanceRecordWriter extends RecordWriter<Text, NullWritable>
    {
        FSDataOutputStream noout = null;
        FSDataOutputStream enout = null;

        public EnhanceRecordWriter(FSDataOutputStream noout, FSDataOutputStream enout) throws IOException {
            this.noout = noout;
            this.enout = enout;
        }


        @Override
        public void write(Text key, NullWritable value) throws IOException, InterruptedException {


            if(key.toString().contains("\001tocrawl")){

                noout.write(key.getBytes());
                noout.write("\n".getBytes());
                noout.flush();
            }
            else {

                enout.write(key.getBytes());
                enout.write("\n".getBytes());
                enout.flush();
            }
        }

        @Override
        public void close(TaskAttemptContext context) throws IOException, InterruptedException {
            noout.close();
            enout.close();
        }
    }


}
