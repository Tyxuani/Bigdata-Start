package galaxy.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class HdfsTyxClinet {

    public static FileSystem fsClient;

    public static void main(String[] args) {

        initHdfsClient();
        String dst = "/kp";
        String src = "./src/main/resources/movie";
//        delete(dst);
        uploadFile(src, dst);
    }

    public HdfsTyxClinet() {
    }

    public static void initHdfsClient() {
        String user = "tyxuan";
        String uesrConf = "./src/main/resources/hdfsClient.xml";
        Configuration conf = new Configuration();
        conf.addResource(uesrConf);
        conf.set("dfs.replication", "2");
        conf.set("dfs.blocksize", "32m");
        try {
            URI hdfsUri = new URI("hdfs://192.8.0.14:9000");
            fsClient = FileSystem.get(hdfsUri, conf, user);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void uploadFile(String src, String dst) {
        try {
            fsClient.copyFromLocalFile(false, true, new Path(src), new Path(dst));
            fsClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadFile(String src, String dst) {
        //拿到远程文件后,使用java原生库操作生成新的文件
        try {
            fsClient.copyToLocalFile(false, new Path(src), new Path(dst), true);
            fsClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mkdir(String dir) {
        //权限会由内部掩码屏蔽部分权限
        try {
            fsClient.mkdirs(new Path(dir), new FsPermission((short) 755));
            fsClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String src) {
        try {
            fsClient.delete(new Path(src), true);
            fsClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void rename(String src, String dst) {
        try {
            fsClient.rename(new Path(src), new Path(dst));
            fsClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void list(String src) {
        try {
            RemoteIterator<LocatedFileStatus> fils = fsClient.listFiles(new Path(src), true);
            while (fils.hasNext()) {
                LocatedFileStatus file = fils.next();
                System.out.println(file.toString());
                System.out.println(Arrays.asList(file.getBlockLocations()));
                System.out.println("--------------------------------");
            }
            fsClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void dirStatus(String dir) {
        try {
            FileStatus[] files = fsClient.listStatus(new Path(dir));
            for (FileStatus file : files) {
                System.out.println(file.toString());
                System.out.println(file.isDirectory() ? "d" : "f");
                System.out.println("--------------------------------");
            }

            fsClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFilePart(String src, String dst) {
        try {
            FSDataInputStream fsin = fsClient.open(new Path(src));
            FileOutputStream fout = new FileOutputStream(dst);

            //默认从0偏移量开始读取文件, 可以调整起始来读取一个block
            fsin.seek(200);

            byte[] by = new byte[25];
            int len = 0;
            int count = 0;
            while ((len = fsin.read(by)) != -1) {
                fout.write(by);
                count += len;
                if (count >= 100) break;
            }
            fout.close();
            fsin.close();
            fsClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String dst) {
        try {
            FSDataOutputStream fsout = fsClient.create(new Path(dst));
            fsout.write("xiao si ni.".getBytes());
            fsout.write("你来啊!".getBytes());
            fsout.flush();
            fsout.close();
            fsClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
