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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

public class PeopleParser {
    private final ArrayList<Person> people = new ArrayList<>();
    HashMap<String, Person> extractedPeople = new HashMap<>();
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
                            person.id = id.getValue();
                            break;
                        }
                        Attribute name = startElement.getAttributeByName(new QName("name"));
                        if (name != null) {
                            String fullName = name.getValue();
                            String[] names = fullName.trim().split("\\s+");
                            String firstName = names[0];
                            String lastName = names[1];
                            person.firstName = firstName;
                            person.lastName = lastName;
                            break;
                        }
                        break;

                    case "gender":
                        Attribute gender = startElement.getAttributeByName(new QName("value"));
                        if (gender != null) {
                            switch (gender.getValue().trim()) {
                                case "male":
                                    person.gender = "M";
                                    break;
                                case "female":
                                    person.gender = "F";
                                    break;
                            }
                            break;
                        }
                        nextEvent = reader.nextEvent();
                        person.gender = nextEvent.asCharacters().getData().trim();
                        break;

                    case "id":
                        Attribute personId = startElement.getAttributeByName(new QName("value"));
                        person.id = personId.getValue();
                        break;

                    case "firstname", "first":
                        Attribute firstName = startElement.getAttributeByName(new QName("value"));
                        if (firstName != null && !"".equals(firstName.getValue())) {
                            person.firstName = firstName.getValue().trim();
                            break;
                        }
                        nextEvent = reader.nextEvent();
                        if (!"".equals(nextEvent.asCharacters().getData().trim())) {
                            person.firstName = nextEvent.asCharacters().getData().trim();
                        }
                        break;

                    case "family", "family-name":
                        nextEvent = reader.nextEvent();
                        if (!"".equals(nextEvent.asCharacters().getData().trim())) {
                            person.lastName = nextEvent.asCharacters().getData().trim();
                        }
                        break;

                    case "surname":
                        Attribute surname = startElement.getAttributeByName(new QName("value"));
                        if (surname != null && !surname.getValue().isEmpty()) {
                            person.lastName = surname.getValue().trim();
                        }
                        break;

                    case "fullname":
                        break;

                    //children
                    case "children-number":
                        Attribute childrenNumber = startElement.getAttributeByName(new QName("value"));
                        if (childrenNumber != null) {
                            person.childrenNumber = childrenNumber.getValue();
                        }
                        break;
                    case "daughter":
                        Attribute daughterId = startElement.getAttributeByName(new QName("id"));
                        if (daughterId != null) {
                            person.daughterId.add(daughterId.getValue());
                        }
                        break;
                    case "son":
                        Attribute sonId = startElement.getAttributeByName(new QName("id"));
                        if (sonId != null) {
                            person.sonId.add(sonId.getValue());
                        }
                        break;
                    case "child":
                        //gender?
                        nextEvent = reader.nextEvent();
                        person.childrenName.add(nextEvent.asCharacters().getData().trim());
                        break;
                    case "children":
                        break;

                    //spouse
                    case "spouce":
                        Attribute spouceName = startElement.getAttributeByName(new QName("value"));
                        if (spouceName != null) {
                            person.spouceName = spouceName.getValue().trim();
                        }
                        break;
                    case "wife":
                        Attribute wifeId = startElement.getAttributeByName(new QName("value"));
                        if (wifeId != null) {
                            person.wifeId = wifeId.getValue().trim();
                        }
                        break;
                    case "husband":
                        Attribute husbandId = startElement.getAttributeByName(new QName("value"));
                        if (husbandId != null) {
                            person.husbandId = husbandId.getValue().trim();
                        }
                        break;

                    //parents
                    case "parent":
                        Attribute parentId = startElement.getAttributeByName(new QName("value"));
                        if (parentId != null) {
                            if (!"UNKNOWN".equalsIgnoreCase(parentId.getValue())) {
                                person.parentId.add(parentId.getValue().trim());
                            }
                            break;
                        }
                        nextEvent = reader.nextEvent();
                        if (!"UNKNOWN".equalsIgnoreCase(nextEvent.asCharacters().getData().trim())) {
                            person.parentName.add(nextEvent.asCharacters().getData().trim());
                        }
                        break;
                    case "father":
                        nextEvent = reader.nextEvent();
                        person.fatherName = nextEvent.asCharacters().getData().trim();
                        break;
                    case "mother":
                        nextEvent = reader.nextEvent();
                        person.motherName = nextEvent.asCharacters().getData().trim();
                        break;

                    //siblings
                    case "siblings":
                        Attribute siblingsId = startElement.getAttributeByName(new QName("val"));
                        if (siblingsId != null) {
                            String siblings = siblingsId.getValue();
                            String[] ids = siblings.trim().split("\\s+");
                            person.siblingsId.addAll(Arrays.asList(ids));
                        }
                        break;
                    case "brother":
                        nextEvent = reader.nextEvent();
                        person.brothersName.add(nextEvent.asCharacters().getData().trim());
                        break;
                    case "sister":
                        nextEvent = reader.nextEvent();
                        person.sistersName.add(nextEvent.asCharacters().getData().trim());
                        break;
                    case "siblings-number":
                        Attribute siblingsNumber = startElement.getAttributeByName(new QName("value"));
                        if (siblingsNumber != null) {
                            person.siblingsNumber = siblingsNumber.getValue();
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
        return extractedPeople;
    }

    private void merge() {
        ArrayList<Person> noname = new ArrayList<>();
        for (var i : people) {
            if (i.id != null) {
                if (extractedPeople.containsKey(i.id)) {
                    (extractedPeople.get(i.id)).concat(i);
                } else {
                    extractedPeople.put(i.id, i);
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


        HashMap<String, HashSet<String>> daughterIds = new HashMap<>();
        HashMap<String, HashSet<String>> sonIds = new HashMap<>();
        for (Map.Entry<String, Person> setId : extractedPeople.entrySet()) {
            String name = setId.getValue().firstName + setId.getValue().lastName;
            if (peopleNames.containsKey(name)) {
                setId.getValue().concat(peopleNames.get(name));
                peopleNames.remove(name);
            }

            // spouse
            if (setId.getValue().wifeId != null && !setId.getValue().wifeId.isEmpty()) {
                setId.getValue().spouceName = extractedPeople.get(setId.getValue().wifeId).firstName +
                        " " + extractedPeople.get(setId.getValue().wifeId).lastName;
            } else if (setId.getValue().husbandId != null && !setId.getValue().husbandId.isEmpty()) {
                setId.getValue().spouceName = extractedPeople.get(setId.getValue().husbandId).firstName +
                        " " + extractedPeople.get(setId.getValue().husbandId).lastName;
            }

            // siblings
            if (!setId.getValue().siblingsId.isEmpty()) {
                for (String siblingId : setId.getValue().siblingsId) {
                    if ("M".equals(extractedPeople.get(siblingId).gender)) {
                        setId.getValue().brotherId.add(siblingId);
                    } else {
                        setId.getValue().sisterId.add(siblingId);
                    }

                }
            }

            for (var siblingId : setId.getValue().siblingsId) {
                Person sibling = extractedPeople.get(siblingId);
                if (!sibling.parentId.isEmpty()) {
                    setId.getValue().parentId.addAll(sibling.parentId);
                    sibling.parentId.addAll(setId.getValue().parentId);
                }
            }

            // ты children по parent
            if (!setId.getValue().parentId.isEmpty()) {
                for (String parentId : setId.getValue().parentId) {
                    Person parent = extractedPeople.get(parentId);
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
                for (var childrenName : setId.getValue().childrenName) {
                    childrenName = childrenName.replaceAll("\\s", "");
                    Person child = peopleNames.get(childrenName);
                    if (child != null && child.id != null) {
                        if (child.gender.equals("M")) {
                            setId.getValue().sonId.add(child.id);
                        } else {
                            setId.getValue().daughterId.add(child.id);
                        }
                    }
                }
            }
            daughterIds.put(setId.getKey(), setId.getValue().daughterId);
            sonIds.put(setId.getKey(), setId.getValue().sonId);

            //daughter
            for (String i : setId.getValue().daughterId) {
                String daughterName = extractedPeople.get(i).firstName + " " + extractedPeople.get(i).lastName;
                setId.getValue().childrenName.add(daughterName);
            }

            //son
            for (String i : setId.getValue().sonId) {
                String sonName = extractedPeople.get(i).firstName + " " + extractedPeople.get(i).lastName;
                setId.getValue().childrenName.add(sonName);
            }
        }

        setChildren(daughterIds);
        setChildren(sonIds);

        for (var nPerson : noname) {
            setSpouseBySon(nPerson);
            setSpouseByDaughter(nPerson);

            if (nPerson.wifeId != null) {
                Person wife = extractedPeople.get(nPerson.wifeId);
                if (extractedPeople.get(wife.husbandId) != null) {
                    extractedPeople.get(wife.husbandId).concat(nPerson);
                }
            }
            if (nPerson.husbandId != null) {
                Person husband = extractedPeople.get(nPerson.husbandId);
                if (extractedPeople.get(husband.wifeId) != null) {
                    extractedPeople.get(husband.wifeId).concat(nPerson);
                }
            }
            if (!nPerson.parentId.isEmpty() && nPerson.firstName != null && !nPerson.firstName.isEmpty()) {
                for (var parentID : nPerson.parentId) {
                    Person parent = extractedPeople.get(parentID);
                    if (parent != null) {
                        if ("M".equals(nPerson.gender) && !parent.sonId.isEmpty()) {
                            Optional<Person> currentSon = parent.sonId.stream()
                                    .map(extractedPeople::get)
                                    .filter(son -> son != null && son.firstName.equals(nPerson.firstName))
                                    .findFirst();
                            currentSon.ifPresent(person -> person.concat(nPerson));
                        }
                        if ("F".equals(nPerson.gender) && !parent.daughterId.isEmpty()) {
                            Optional<Person> currentDaughter = parent.daughterId.stream()
                                    .map(extractedPeople::get)
                                    .filter(daughter -> daughter != null && daughter.firstName.equals(nPerson.firstName))
                                    .findFirst();
                            currentDaughter.ifPresent(person -> person.concat(nPerson));
                        }
                    }
                }
            }
            if (!nPerson.sisterId.isEmpty() && nPerson.firstName != null && !nPerson.firstName.isEmpty()) {
                for (var sisterID : nPerson.sisterId) {
                    completePersonBySibling(nPerson, sisterID);
                }
            }
            if (!nPerson.brotherId.isEmpty() && nPerson.firstName != null && !nPerson.firstName.isEmpty()) {
                for (var brotherID : nPerson.brotherId) {
                    completePersonBySibling(nPerson, brotherID);
                }
            }
        }

        //splitParentId
        for (var person : extractedPeople.values()) {
            for (var sister : person.sistersName) {
                if (peopleNames.get(sister) != null
                        && peopleNames.get(sister).id != null
                        && !peopleNames.get(sister).id.isEmpty()) {
                    person.sisterId.add(peopleNames.get(sister).id);
                }
            }

            for (var brother : person.brothersName) {
                if (peopleNames.get(brother) != null
                        && peopleNames.get(brother).id != null
                        && !peopleNames.get(brother).id.isEmpty()) {
                    person.brotherId.add(peopleNames.get(brother).id);
                }
            }

            if (!person.parentId.isEmpty()) {
                for (var parentId : person.parentId) {
                    String gender = extractedPeople.get(parentId).gender;
                    if (gender != null) {
                        if (gender.equals("M")) {
                            person.fatherId = parentId;
                        } else {
                            person.motherId = parentId;
                        }
                    }
                }
            }
            person.gender = "F";
        }
    }

    private void completePersonBySibling(Person currentPerson, String siblingId) {
        Person sibling = extractedPeople.get(siblingId);
        if (sibling != null) {
            if ("M".equals(currentPerson.gender) && sibling.brotherId != null) {
                Optional<Person> currentBrother = sibling.brotherId.stream()
                        .map(extractedPeople::get)
                        .filter(brotherTwo -> brotherTwo != null && brotherTwo.firstName.equals(currentPerson.firstName))
                        .findFirst();
                currentBrother.ifPresent(person -> person.concat(currentPerson));
            }
            if ("F".equals(currentPerson.gender) && sibling.sisterId != null) {
                Optional<Person> currentSister = sibling.sisterId.stream()
                        .map(extractedPeople::get)
                        .filter(sister -> sister != null && sister.firstName.equals(currentPerson.firstName))
                        .findFirst();
                currentSister.ifPresent(person -> person.concat(currentPerson));
            }
        }
    }

    private void setChildren(HashMap<String, HashSet<String>> childrenIds) {
        for (var child : childrenIds.entrySet()) {
            Person parent = extractedPeople.get(child.getKey());
            HashSet<String> ids = new HashSet<>(child.getValue());
            for (var childId : ids) {
                Person son = extractedPeople.get(childId);
                for (var siblingId : son.siblingsId) {
                    Person sibling = extractedPeople.get(siblingId);
                    if ("M".equals(sibling.gender)) {
                        parent.sonId.add(sibling.id);
                    } else {
                        parent.daughterId.add(sibling.id);
                    }
                }
            }
        }
    }

    private void setSpouseBySon(Person person) {
        if (!person.sonId.isEmpty()) {
            for (var sonId : person.sonId) {
                setSpouseByChild(person, sonId);
            }
        }
    }

    private void setSpouseByDaughter(Person person) {
        if (!person.daughterId.isEmpty()) {
            for (var daughterId : person.daughterId) {
                setSpouseByChild(person, daughterId);
            }
        }
    }

    private void setSpouseByChild(Person person, String childrenId) {
        Person daughter = extractedPeople.get(childrenId);
        if (daughter != null && !daughter.parentId.isEmpty()) {
            for (var parentId : daughter.parentId) {
                Person parent = extractedPeople.get(parentId);
                if (parent.gender != null && (parent.gender).equals(person.gender)) {
                    parent.concat(person);
                } else {
                    if (parent.wifeId != null) {
                        Person parentSpouse = extractedPeople.get(parent.wifeId);
                        parentSpouse.concat(person);
                    } else if (parent.husbandId != null) {
                        Person parentSpouse = extractedPeople.get(parent.husbandId);
                        parentSpouse.concat(person);
                    }
                }
            }
        }
    }
}