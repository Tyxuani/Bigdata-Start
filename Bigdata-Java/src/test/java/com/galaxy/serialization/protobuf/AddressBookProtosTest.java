package com.galaxy.serialization.protobuf;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.galaxy.serialization.protobuf.AddressBookProtos.Person;
import static com.galaxy.serialization.protobuf.AddressBookProtos.Person.PhoneType;
import static com.galaxy.serialization.protobuf.AddressBookProtos.Person.newBuilder;

public class AddressBookProtosTest {

    @Test
    public void serilize_add_with_src() throws IOException {
        Person p = newBuilder()
                .setEmail("4545@qq.com")
                .setName("zjokia")
                .setId(12)
                .addPhone(0, Person.PhoneNumber.newBuilder()
                        .setNumber("152545")
                        .setYtpe(PhoneType.HOME)
                        .build())
                .build();
        FileOutputStream fos = new FileOutputStream("./src/test/java/com/galaxy/serialization/protobuf/addressbook.dat");
        p.writeTo(fos);
        fos.close();
    }

    @Test
    public void resrilize_add() throws IOException {
        FileInputStream fis = new FileInputStream("./src/test/java/com/galaxy/serialization/protobuf/addressbook.dat");
        AddressBookProtos.Person p = AddressBookProtos.Person.parseFrom(fis);
        System.out.println(p.toString());
    }
}