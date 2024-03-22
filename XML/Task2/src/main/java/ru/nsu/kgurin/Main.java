package ru.nsu.kgurin;

import org.xml.sax.SAXException;
import ru.nsu.kgurin.generated.*;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Map<String, Person> peopleIds = new HashMap<>();
    private static final Map<String, PersonType> collectedData = new HashMap<>();

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
        FileInputStream file = new FileInputStream("src/main/resources/people.xml");
        PeopleParser parser = new PeopleParser();
        peopleIds.putAll(parser.Parse(file));
        People people = new People();
        for (var info : peopleIds.values()) {
            PersonType person = new PersonType();
            setPersonInfo(person, info);
            collectedData.put(info.id, person);
        }

        for (var person : collectedData.values()) {
            setSpouse(person);
            setChildren(person);
            setParents(person);
            setSiblings(person);
        }

        people.getPerson().addAll(collectedData.values());
        try {
            JAXBContext jc = null;
            ClassLoader classLoader = People.class.getClassLoader();
            jc = JAXBContext.newInstance("ru.nsu.kgurin.generated", classLoader);
            Marshaller writer = jc.createMarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            File schemaFile = new File("src/main/resources/schema.xsd");
            writer.setSchema(schemaFactory.newSchema(schemaFile));
            writer.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            writer.marshal(people, new File("src/main/resources/structured_people.xml"));
        } catch (JAXBException | SAXException e) {
            e.printStackTrace();
        }
    }

    private static void setPersonInfo(PersonType person, Person info) {
        person.setId(info.id);
        person.setName(info.firstName + " " + info.lastName);
        person.setGender(GenderType.fromValue(info.gender));
    }

    private static void setSpouse(PersonType person) {
        var info = peopleIds.get(person.getId());

        if (info.wifeId != null) {
            PersonRef personRef = new PersonRef();
            personRef.setId(collectedData.get(info.wifeId));
            person.setWife(personRef);

        } else if (info.husbandId != null) {
            PersonRef personRef = new PersonRef();
            personRef.setId(collectedData.get(info.husbandId));
            person.setHusband(personRef);
        }
    }

    private static void setChildren(PersonType person) {
        var info = peopleIds.get(person.getId());
        ChildrenType childrenType = new ChildrenType();

        for (var daughter : info.daughterId) {
            PersonRef personRef = new PersonRef();
            personRef.setId(collectedData.get(daughter));
            childrenType.getDaughter().add(personRef);
        }
        for (var son : info.sonId) {
            PersonRef personRef = new PersonRef();
            personRef.setId(collectedData.get(son));
            childrenType.getSon().add(personRef);
        }
        person.getChildren().add(childrenType);
    }

    private static void setParents(PersonType person) {
        Person info = peopleIds.get(person.getId());
        ParentsType parentsType = new ParentsType();

        if (info.motherName != null && collectedData.get(info.motherId) != null) {
            PersonRef personRef = new PersonRef();
            personRef.setId(collectedData.get(info.motherId));
            parentsType.setMother(personRef);
        }
        if (info.fatherName != null && collectedData.get(info.fatherId) != null) {
            PersonRef personRef = new PersonRef();
            personRef.setId(collectedData.get(info.fatherId));
            parentsType.setFather(personRef);
        }

        person.getParents().add(parentsType);
    }

    private static void setSiblings(PersonType person) {
        var info = peopleIds.get(person.getId());
        SiblingsType siblingsType = new SiblingsType();

        for (var sister : info.sisterId) {
            PersonRef personRef = new PersonRef();
            personRef.setId(collectedData.get(sister));
            siblingsType.getSister().add(personRef);
        }
        for (var brother : info.brotherId) {
            PersonRef personRef = new PersonRef();
            personRef.setId(collectedData.get(brother));
            siblingsType.getBrother().add(personRef);
        }
        person.getSiblings().add(siblingsType);
    }
}
