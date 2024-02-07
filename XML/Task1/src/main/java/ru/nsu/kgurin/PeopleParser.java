package ru.nsu.kgurin;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PeopleParser {
    private final ArrayList<Person> people = new ArrayList<>();
    HashMap<String, Person> peopleIds = new HashMap<>();
    HashMap<String, Person> peopleNames = new HashMap<>();


    public HashMap<String, Person> Parse(FileInputStream file) throws XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(file);

        Person person = null;
        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                switch (startElement.getName().getLocalPart()) {

                    case "person":
                        person = new Person();
                        Attribute id = startElement.getAttributeByName(new QName("id"));
                        if (id != null) {
                            person.setId(id.getValue());
                            break;
                        }
                        Attribute name = startElement.getAttributeByName(new QName("name"));
                        if (name != null) {
                            String fullName = name.getValue();
                            String[] names = fullName.split("\\s+");
                            String firstName = names[0];
                            String lastName = names[1];
                            person.setFirstName(firstName);
                            person.setLastName(lastName);
                            break;
                        }
                        break;

                    case "gender":
                        Attribute gender = startElement.getAttributeByName(new QName("value"));
                        if (gender != null) {
                            switch (gender.getValue().trim()) {
                                case "male":
                                    person.setGender("M");
                                    break;
                                case "female":
                                    person.setGender("F");
                                    break;
                            }
                            break;
                        }
                        nextEvent = reader.nextEvent();
                        person.setGender(nextEvent.asCharacters().getData().trim());
                        break;

                    case "id":
                        Attribute personId = startElement.getAttributeByName(new QName("value"));
                        person.setId(personId.getValue());
                        break;

                    case "firstname", "first":
                        Attribute firstName = startElement.getAttributeByName(new QName("value"));
                        if (firstName != null && !"".equals(firstName.getValue())) {
                            person.setFirstName(firstName.getValue().trim());
                            break;
                        }
                        nextEvent = reader.nextEvent();
                        if (!"".equals(nextEvent.asCharacters().getData().trim())) {
                            person.setFirstName(nextEvent.asCharacters().getData().trim());
                        }
                        break;

                    case "family", "family-name":
                        nextEvent = reader.nextEvent();
                        if (!"".equals(nextEvent.asCharacters().getData().trim())) {
                            person.setLastName(nextEvent.asCharacters().getData().trim());
                        }
                        break;

                    case "surname":
                        Attribute surname = startElement.getAttributeByName(new QName("value"));
                        if (surname != null && !surname.getValue().isEmpty()) {
                            person.setLastName(surname.getValue().trim());
                        }
                        break;

                    case "fullname":
                        break;

                    //children
                    case "children-number":
                        Attribute childrenNumber = startElement.getAttributeByName(new QName("value"));
                        if (childrenNumber != null) {
                            person.setChildrenNumber(childrenNumber.getValue());
                        }
                        break;
                    case "daughter":
                        Attribute daughterId = startElement.getAttributeByName(new QName("id"));
                        if (daughterId != null) {
                            person.addDaughterId(daughterId.getValue());
                        }
                        break;
                    case "son":
                        Attribute sonId = startElement.getAttributeByName(new QName("id"));
                        if (sonId != null) {
                            person.addSonId(sonId.getValue());
                        }
                        break;
                    case "child":
                        //gender?
                        nextEvent = reader.nextEvent();
                        person.addChildrenName(nextEvent.asCharacters().getData().trim());
                        break;
                    case "children":
                        break;

                    //spouce
                    case "spouce":
                        Attribute spouceName = startElement.getAttributeByName(new QName("value"));
                        if (spouceName != null) {
                            person.setSpouceName(spouceName.getValue().trim());
                        }
                        break;
                    case "wife":
                        Attribute wifeId = startElement.getAttributeByName(new QName("value"));
                        if (wifeId != null) {
                            person.setWifeId(wifeId.getValue().trim());
                        }
                        break;
                    case "husband":
                        Attribute husbandId = startElement.getAttributeByName(new QName("value"));
                        if (husbandId != null) {
                            person.setHusbandId(husbandId.getValue().trim());
                        }
                        break;

                    //parents
                    case "parent":
                        Attribute parentId = startElement.getAttributeByName(new QName("value"));
                        if (parentId != null) {
                            if (!"UNKNOWN".equalsIgnoreCase(parentId.getValue())) {
                                person.addParentId(parentId.getValue().trim());
                            }
                            break;
                        }
                        nextEvent = reader.nextEvent();
                        if (!"UNKNOWN".equalsIgnoreCase(nextEvent.asCharacters().getData().trim())) {
                            person.addParentName(nextEvent.asCharacters().getData().trim());
                        }
                        break;
                    case "father":
                        nextEvent = reader.nextEvent();
                        person.setFatherName(nextEvent.asCharacters().getData().trim());
                        break;
                    case "mother":
                        nextEvent = reader.nextEvent();
                        person.setMotherName(nextEvent.asCharacters().getData().trim());
                        break;

                    //siblings
                    case "siblings":
                        Attribute siblingsId = startElement.getAttributeByName(new QName("val"));
                        if (siblingsId != null) {
                            String siblings = siblingsId.getValue();
                            String[] ids = siblings.split("\\s+");
                            for (var i : ids) {
                                person.addSiblingsId(i);
                            }
                        }
                        break;
                    case "brother":
                        nextEvent = reader.nextEvent();
                        person.addBrotherId(nextEvent.asCharacters().getData().trim());
                        break;
                    case "sister":
                        nextEvent = reader.nextEvent();
                        person.addSisterId(nextEvent.asCharacters().getData().trim());
                        break;
                    case "siblings-number":
                        Attribute siblingsNumber = startElement.getAttributeByName(new QName("value"));
                        if (siblingsNumber != null) {
                            person.setSiblingsNumber(siblingsNumber.getValue());
                        }
                        break;
                }
            }
            if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("person")) {
                    people.add(person);
                }
            }

        }
        merge();
        return peopleIds;
    }

    private void merge() {
        ArrayList<Person> noname = new ArrayList<>();
        for (var i : people) {
            if (i.id != null) {
                if (peopleIds.containsKey(i.id)) {
                    (peopleIds.get(i.id)).concat(i);
                } else {
                    peopleIds.put(i.id, i);
                }
            } else if (!"".equals(i.firstName) && !"".equals(i.lastName)) {
                String name = i.firstName + i.lastName;
                if (peopleNames.containsKey(name)) {
                    (peopleNames.get(name)).concat(i);
                } else {
                    peopleNames.put(name, i);
                }
            } else {
                noname.add(i);
            }
        }

        for (Map.Entry<String, Person> setId : peopleIds.entrySet()) {
            String name = setId.getValue().firstName + setId.getValue().firstName;
            if (peopleNames.containsKey(name)) {
                setId.getValue().concat(peopleNames.get(name));
                peopleNames.remove(name);
            }

            // spouse
            if (setId.getValue().wifeId != null && !setId.getValue().wifeId.isEmpty()) {
                setId.getValue().spouceName = peopleIds.get(setId.getValue().wifeId).firstName +
                        " " + peopleIds.get(setId.getValue().wifeId).lastName;
            } else if (setId.getValue().husbandId != null && !setId.getValue().husbandId.isEmpty()) {
                setId.getValue().spouceName = peopleIds.get(setId.getValue().husbandId).firstName +
                        " " + peopleIds.get(setId.getValue().husbandId).lastName;
            }

            // siblings
            if (!setId.getValue().siblingsId.isEmpty()) {
                for (String siblingId : setId.getValue().siblingsId) {
                    if ("M".equals(peopleIds.get(siblingId).gender)) {
                        setId.getValue().addBrotherId(siblingId);
                    } else {
                        setId.getValue().addSisterId(siblingId);
                    }

                }
            }

            // ты children по parent
            if (!setId.getValue().parentId.isEmpty()) {
                for (String parentId : setId.getValue().parentId) {
                    Person parent = peopleIds.get(parentId);
                    // for child
                    if ("M".equals(parent.gender)) {
                        setId.getValue().fatherName = parent.firstName + " " + parent.lastName;
                    } else {
                        setId.getValue().motherName = parent.firstName + " " + parent.lastName;
                    }
                    // for parent
                    if ("F".equals(setId.getValue().gender)) {
                        parent.daughterId.add(setId.getKey());
                    } else {
                        parent.sonId.add(setId.getKey());
                    }
                }
            }

            if (!setId.getValue().childrenName.isEmpty()) {
                for (String i : setId.getValue().childrenName) {
                    Person child = peopleNames.get(i);
                    if (child != null && child.id != null) {
                        if (child.gender.equals("M")) {
                            setId.getValue().sonId.add(child.id);
                        } else {
                            setId.getValue().daughterId.add(child.id);
                        }
                    }
                }
            }

            //daughter
            for (String i : setId.getValue().daughterId) {
                String daughterName = peopleIds.get(i).firstName + " " + peopleIds.get(i).lastName;
                setId.getValue().childrenName.add(daughterName);
            }

            //son
            for (String i : setId.getValue().sonId) {
                String sonName = peopleIds.get(i).firstName + " " + peopleIds.get(i).lastName;
                setId.getValue().childrenName.add(sonName);
            }
        }
    }
}
