package galaxy.mapreduce.topn;

import org.apache.hadoop.io.WritableComparator;

public class UidGroupingComparator extends WritableComparator {

    public UidGroupingComparator() {
        super(RateBean.class, true);
    }

    @Override
    public int compare(Object a, Object b) {
        return super.compare(((RateBean) a).getUid(), ((RateBean)b).getUid());
    }
}
