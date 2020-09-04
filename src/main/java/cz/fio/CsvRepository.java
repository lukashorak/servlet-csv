package cz.fio;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CsvRepository {

    Set<Customer> set = Collections.synchronizedSet(new HashSet<>());

    Path tmpNoPrefix;

    public CsvRepository(){
        //Read data to set
        try {
            tmpNoPrefix = Files.createTempFile("contacts", ".csv");
            System.out.println("Created CSV File:"+tmpNoPrefix.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCustomer(Customer c){
        if (!set.contains(c)){
            this.write(c);
        }
    }

    private void write(Customer c){
        try (
            FileWriter pw = new FileWriter(tmpNoPrefix.toFile(),true);
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(tmpNoPrefix.toFile(), true),
                            Charset.forName("Cp1250")));
        ){
            out.write(c.getFirstName()+","+c.getLastName()+","+c.getEmail());
            out.write(System.lineSeparator());
            out.flush();
            System.out.println("Write to CSV File:"+c.getFirstName()+","+c.getLastName()+","+c.getEmail());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
