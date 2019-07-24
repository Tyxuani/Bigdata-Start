package galaxy;

import galaxy.mapreduce.topn.RateBean;
import io.netty.util.internal.ThreadLocalRandom;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class JavaUtil {
    public static void main(String[] args) throws IOException {

        createMovieSql();

    }

    private static void createMovieSql() throws IOException {
        RateBean rateBean = new RateBean();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2000; i++) {

            StringBuilder movie = new StringBuilder();
            for (int j = 0; j < 4; j++) {
                movie.append(ThreadLocalRandom.current().nextInt(9));
            }

            String rate = ThreadLocalRandom.current().nextInt(1,11) + "";
            String timeStamp = System.currentTimeMillis() + "";
            String uid = ThreadLocalRandom.current().nextInt(8) + "";

            rateBean.set(movie.toString(), rate, timeStamp, uid);
            sb.append("INSERT INTO output_format VALUES (\"" + movie.toString() +"\", \"" + rate
                    + "\", \"" + timeStamp + "\", \"" + uid + "\");\n");

        }

        String path = "./src/main/resources/movie/movieRate.sql";
        File file = new File(path);
        file.createNewFile();
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(sb.toString().getBytes());
        fout.close();

    }

    private static void createMovieFile() throws IOException {
        RateBean rateBean = new RateBean();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2000; i++) {

            StringBuilder movie = new StringBuilder();
            for (int j = 0; j < 4; j++) {
                movie.append(ThreadLocalRandom.current().nextInt(9));
            }

            String rate = ThreadLocalRandom.current().nextInt(1,11) + "";
            String timeStamp = System.currentTimeMillis() + "";
            String uid = ThreadLocalRandom.current().nextInt(8) + "";

            rateBean.set(movie.toString(), rate, timeStamp, uid);
            sb.append(new ObjectMapper().writeValueAsString(rateBean) + "\n");

        }

        String path = "./src/main/resources/movie/movieRate.txt";
        File file = new File(path);
        file.createNewFile();
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(sb.toString().getBytes());
        fout.close();

    }

    private static void createSource() throws IOException {
        for (int k = 0; k < 1; k++) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 200; i++) {

                sb.append(1);
                for (int j = 0; j < 4; j++) {
                    sb.append(ThreadLocalRandom.current().nextInt(9));
                }

                sb.append(" upload ");
                for (int j = 0; j < 3; j++) {
                    sb.append(ThreadLocalRandom.current().nextInt(9));
                }

                sb.append(" download ");
                for (int j = 0; j < 3; j++) {
                    sb.append(ThreadLocalRandom.current().nextInt(9));
                }
                sb.append("\n");
            }
            String path = "./src/main/resources/apifile/poneaddr" + k + ".txt";
            File file = new File(path);
            file.createNewFile();
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(sb.toString().getBytes());
            fout.close();
        }
    }
}
