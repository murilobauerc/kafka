package com.github.simplesteph.avro.generic;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.*;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

import java.io.File;
import java.io.IOException;

public class GenericRecordExamples {

  public static void main(String[] args) {
    // step 0: define schema
    Schema.Parser parser = new Schema.Parser();
    Schema schema =
        parser.parse(
            "{\n"
                + "    \"type\": \"record\",\n"
                + "    \"namespace\": \"com.example\",\n"
                + "    \"name\": \"Customer\",\n"
                + "    \"doc\": \"Avro Schema for our Customer\",\n"
                + "    \"fields\": [\n"
                + "        {\"name\": \"first_name\", \"type\": \"string\", \"doc\": \"First Name of the customer\"},\n"
                + "        {\"name\": \"last_name\", \"type\": \"string\", \"doc\": \"Last name of the costumer\"},\n"
                + "        {\"name\": \"age\", \"type\": \"int\", \"doc\": \"Age of the customer\"},\n"
                + "        {\"name\": \"height\", \"type\": \"float\", \"doc\": \"Height in cms\"},\n"
                + "        {\"name\": \"weight\", \"type\": \"float\", \"doc\": \"Weight in kgs\"},\n"
                + "        {\"name\": \"automated_email\", \"type\": \"boolean\", \"default\": true, \"doc\": \"true if the user wants a marketing emails\"}\n"
                + "    ]\n"
                + "}");

    // step 1: create a generic record
    GenericRecordBuilder genericRecordBuilder =
        new GenericRecordBuilder(schema)
            .set("first_name", "Jhon")
            .set("last_name", "Doe")
            .set("age", 25)
            .set("height", 170f)
            .set("weight", 80.5f)
            .set("automated_email", false);
    GenericData.Record customer = genericRecordBuilder.build();
    System.out.println(customer);

    // step 2: write that generic record to a file
    final DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
    try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter)) {
      dataFileWriter.create(customer.getSchema(), new File("customer-generic.avro"));
      dataFileWriter.append(customer);
      System.out.println("Written customer-generic.avro");
    } catch (IOException e) {
      System.out.println("Couldn't write file");
      throw new RuntimeException(e);
    }

    // step 3: reading from file
    final File file = new File("customer-generic.avro");
    final DatumReader<GenericRecord> datumReader = new GenericDatumReader<>();
    try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, datumReader)) {
      GenericRecord customerRead = dataFileReader.next();
      System.out.println("Successfully read avro file");
      System.out.println(customerRead.toString());

      // get the data from a generic records
      System.out.println("First name: " + customerRead.get("first_name"));

      // read a non-existent field
      System.out.println("Non existent field: " + customerRead.get("not_here"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // step 4: interpret as generic record

  }
}
