package cz.fio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvRepository {

    Set<Customer> set = Collections.synchronizedSet(new HashSet<>());

    File tmpFile;

    public CsvRepository(){
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        tmpFile = new File(tempDirectoryPath, "contacts.csv");
        System.out.println("Created CSV File:"+tmpFile.getAbsolutePath());

        this.readData();
    }

    public void addCustomer(Customer c){
        if (!set.contains(c)){
            this.write(c);
        }else{
            System.out.println("Record already exists in File - "+c);
        }
    }

    private void readData(){
        try (Stream<String> stream = Files.lines(tmpFile.toPath(), Charset.forName("Cp1250"))) {

            set.addAll(stream.map(l->{
                String[] fields = l.split(",");
                Customer c = new Customer(fields[0], fields[1], fields[2]);
                return c;
            }).collect(Collectors.toSet()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(Customer c){
        try (
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(tmpFile, true),
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
