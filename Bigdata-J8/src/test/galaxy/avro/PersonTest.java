package galaxy.avro;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class PersonTest {

    @Test
    public void serilized_compile() throws IOException {
        Person alice = Person.newBuilder()
                .setName("Alice")
                .setFavoriteNumber(10)
                .setFavoriteColor("blue")
                .build();

        DatumWriter<Person> dw = new SpecificDatumWriter<Person>(Person.class);
        DataFileWriter<Person> dfw = new DataFileWriter<Person>(dw);
        dfw.create(alice.getSchema(), new File("./src/main/java/galaxy/avro/person.avrc"));
        dfw.append(alice);
        dfw.close();
    }

    @Test
    public void reserilized() throws IOException {

        DatumReader<Person> dt = new SpecificDatumReader<Person>(Person.class);
        DataFileReader<Person> dtr = new DataFileReader<Person>(new File("./src/main/java/galaxy/avro/person.avrc"), dt);
        DataFileReader<Person> dtr1 = new DataFileReader<Person>(new File("./src/main/java/galaxy/avro/newp.avrc"), dt);
        while (dtr.hasNext()) {
            Person person = dtr.next();
            System.out.println(person.toString());
        }
        while (dtr1.hasNext()) {
            Person person = dtr1.next();
            System.out.println(person.toString());
        }
    }

    @Test
    public void serilized_without_compile() throws IOException {

        Schema schema = new Schema.Parser().parse(new File("./src/main/java/galaxy/avro/person.avro"));

        GenericRecord record = new GenericData.Record(schema);
        record.put("name", "Zhouyi");
        record.put("favorite_number", 15);
        record.put("favorite_color", "yellow");

        DatumWriter<GenericRecord> dw = new GenericDatumWriter<>();
        DataFileWriter<GenericRecord> dfw = new DataFileWriter<>(dw);
        dfw.create(schema, new File("./src/main/java/galaxy/avro/newp.avrc"));
        dfw.append(record);
        dfw.close();

    }

}