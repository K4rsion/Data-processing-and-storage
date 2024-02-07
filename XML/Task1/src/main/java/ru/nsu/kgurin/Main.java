package ru.nsu.kgurin;

import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        FileInputStream file = new FileInputStream("src/main/resources/people.xml");
        PeopleParser parser = new PeopleParser();
        HashMap<String, Person> peopleId = parser.Parse(file);
        System.out.println("");
    }
}
