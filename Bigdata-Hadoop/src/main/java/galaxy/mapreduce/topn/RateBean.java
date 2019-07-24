package galaxy.mapreduce.topn;

import lombok.Data;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@Data
public class RateBean implements WritableComparable<RateBean> {
    private String movie;
    private String rate;
    private String timeStamp;
    private String uid;

    public void set(String movie, String rate, String timeStamp, String uid){
        this.movie = movie;
        this.rate = rate;
        this.timeStamp = timeStamp;
        this.uid = uid;
    }

    @Override
    public int compareTo(RateBean o) {
        if(this.uid.equals(o.getUid())){
            return -this.rate.compareTo(o.getRate());
        }
        return this.uid.compareTo(o.getUid());
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(movie);
        out.writeUTF(rate);
        out.writeUTF(timeStamp);
        out.writeUTF(uid);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.movie = in.readUTF();
        this.rate = in.readUTF();
        this.timeStamp = in.readUTF();
        this.uid = in.readUTF();
    }
}
