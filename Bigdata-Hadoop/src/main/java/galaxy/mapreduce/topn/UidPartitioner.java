package galaxy.mapreduce.topn;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class UidPartitioner extends Partitioner<RateBean, NullWritable> {

    @Override
    public int getPartition(RateBean rateBean, NullWritable nullWritable, int numPartitions) {
        return (rateBean.getRate().hashCode() & Integer.MAX_VALUE ) / numPartitions;
    }
}
