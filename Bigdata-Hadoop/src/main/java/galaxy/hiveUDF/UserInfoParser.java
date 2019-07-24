package galaxy.hiveUDF;

import org.apache.hadoop.hive.ql.exec.UDF;

public class UserInfoParser extends UDF {
    public String evaluate(String info, int index){
        String[] split = info.split("[:|]");
        return split[index - 1];
    }
}

