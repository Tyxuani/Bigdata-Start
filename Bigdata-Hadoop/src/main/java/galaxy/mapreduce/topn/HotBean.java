package galaxy.mapreduce.topn;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotBean implements WritableComparable<HotBean>{
    String movie;
    int hot;

    @Override
    public int compareTo(HotBean o) {
        return this.hot - o.hot;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(movie);
        out.writeInt(hot);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.movie = in.readUTF();
        this.hot = in.readInt();
    }
}
