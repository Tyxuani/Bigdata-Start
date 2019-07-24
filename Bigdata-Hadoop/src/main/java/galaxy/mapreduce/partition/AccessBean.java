package galaxy.mapreduce.partition;

import lombok.Data;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@Data
public class AccessBean implements Writable {

    private String poneNum;
    private int upLoad;
    private int downLoad;
    private int sum;

    public AccessBean() {
    }

    public AccessBean(String poneNum, int upLoad, int downLoad) {
        this.poneNum = poneNum;
        this.upLoad = upLoad;
        this.downLoad = downLoad;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(poneNum);
        out.writeInt(upLoad);
        out.writeInt(downLoad);
        out.writeInt(sum);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.poneNum = in.readUTF();
        this.upLoad = in.readInt();
        this.downLoad = in.readInt();
        this.sum = in.readInt();
    }
}
