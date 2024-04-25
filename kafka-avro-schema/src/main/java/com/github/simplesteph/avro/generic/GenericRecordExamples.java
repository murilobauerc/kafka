package com.github.simplesteph.avro.generic;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecordBuilder;

public class GenericRecordExamples {

    public static void main(String[] args) {
    // step 0: define schema
    Schema.Parser parser = new Schema.Parser();
    Schema schema = parser.parse("{\n" +
            "    \"type\": \"record\",\n" +
            "    \"namespace\": \"com.example\",\n" +
            "    \"name\": \"Customer\",\n" +
            "    \"doc\": \"Avro Schema for our Customer\",\n" +
            "    \"fields\": [\n" +
            "        {\"name\": \"first_name\", \"type\": \"string\", \"doc\": \"First Name of the customer\"},\n" +
            "        {\"name\": \"last_name\", \"type\": \"string\", \"doc\": \"Last name of the costumer\"},\n" +
            "        {\"name\": \"age\", \"type\": \"int\", \"doc\": \"Age of the customer\"},\n" +
            "        {\"name\": \"height\", \"type\": \"float\", \"doc\": \"Height in cms\"},\n" +
            "        {\"name\": \"weight\", \"type\": \"float\", \"doc\": \"Weight in kgs\"},\n" +
            "        {\"name\": \"automated_email\", \"type\": \"boolean\", \"default\": true, \"doc\": \"true if the user wants a marketing emails\"}\n" +
            "    ]\n" +
            "}");

    // step 1: create a generic record
    GenericRecordBuilder genericRecordBuilder = new GenericRecordBuilder(schema)
            .set("first_name", "Jhon")
            .set("last_name", "Doe")
            .set("age", 25)
            .set("height", 170f)
            .set("weight", 80.5f)
            .set("automated_email", false);
    GenericData.Record customer = genericRecordBuilder.build();
    System.out.println(customer);

    GenericRecordBuilder genericRecordBuilderWithDefault = new GenericRecordBuilder(schema)
            .set("first_name", "Jhon")
            .set("last_name", "Doe")
            .set("age", 25)
            .set("height", 170f)
            .set("weight", 80.5f);
    GenericData.Record customerWithDefault = genericRecordBuilderWithDefault.build();
    System.out.println(customerWithDefault);



        // step 2: write that generic record to a file


    // step 3: read a generic record from a file

    // step 4: interpret as generic record
    }
}
