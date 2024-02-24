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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

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


        HashMap<String, HashSet<String>> daughterIds = new HashMap<>();
        HashMap<String, HashSet<String>> sonIds = new HashMap<>();
        Iterator<Map.Entry<String, Person>> iterator = peopleIds.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Person> setId = iterator.next();
            String name = setId.getValue().firstName + setId.getValue().lastName;
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

            for (var siblingId : setId.getValue().siblingsId) {
                Person sibling = peopleIds.get(siblingId);
                if (!sibling.parentId.isEmpty()) {
                    setId.getValue().parentId.addAll(sibling.parentId);
                    sibling.parentId.addAll(setId.getValue().parentId);
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
                String daughterName = peopleIds.get(i).firstName + " " + peopleIds.get(i).lastName;
                setId.getValue().childrenName.add(daughterName);
            }

            //son
            for (String i : setId.getValue().sonId) {
                String sonName = peopleIds.get(i).firstName + " " + peopleIds.get(i).lastName;
                setId.getValue().childrenName.add(sonName);
            }
        }


        for (var daughterSet : daughterIds.entrySet()) {
            Person parent = peopleIds.get(daughterSet.getKey());
            HashSet<String> ids = new HashSet<>(daughterSet.getValue());
            for (var daughterId : ids) {
                Person daughter = peopleIds.get(daughterId);
                for (var siblingId : daughter.siblingsId) {
                    Person sibling = peopleIds.get(siblingId);
                    if ("M".equals(sibling.gender)) {
                        parent.sonId.add(sibling.id);
                    } else {
                        parent.daughterId.add(sibling.id);
                    }
                }
            }
        }


        for (var sonSet : sonIds.entrySet()) {
            Person parent = peopleIds.get(sonSet.getKey());
            HashSet<String> ids = new HashSet<>(sonSet.getValue());
            for (var sonId : ids) {
                Person son = peopleIds.get(sonId);
                for (var siblingId : son.siblingsId) {
                    Person sibling = peopleIds.get(siblingId);
                    if ("M".equals(sibling.gender)) {
                        parent.sonId.add(sibling.id);
                    } else {
                        parent.daughterId.add(sibling.id);
                    }
                }
            }
        }

        ArrayList<Person> nonameProcessed = new ArrayList<>();
        for (var nPerson : noname) {
            if (!nPerson.sonId.isEmpty()) {
                for (var sonId : nPerson.sonId) {
                    Person son = peopleIds.get(sonId);
                    if (son != null && !son.parentId.isEmpty()) {
                        for (var parentId : son.parentId) {
                            Person parent = peopleIds.get(parentId);
                            if (parent.gender != null && (parent.gender).equals(nPerson.gender)) {
                                mergePerson(parent, nPerson);
                            } else {
                                if (parent.wifeId != null) {
                                    Person parentSpouse = peopleIds.get(parent.wifeId);
                                    mergePerson(parentSpouse, nPerson);
                                } else if (parent.husbandId != null) {
                                    Person parentSpouse = peopleIds.get(parent.husbandId);
                                    mergePerson(parentSpouse, nPerson);
                                }
                            }
                        }
                    }
                }
                nonameProcessed.add(nPerson);
            }
            if (!nPerson.daughterId.isEmpty()) {
                for (var daughterId : nPerson.daughterId) {
                    Person daughter = peopleIds.get(daughterId);
                    if (daughter != null && !daughter.parentId.isEmpty()) {
                        for (var parentId : daughter.parentId) {
                            Person parent = peopleIds.get(parentId);
                            if (parent.gender != null && (parent.gender).equals(nPerson.gender)) {
                                mergePerson(parent, nPerson);
                            } else {
                                if (parent.wifeId != null) {
                                    Person parentSpouse = peopleIds.get(parent.wifeId);
                                    mergePerson(parentSpouse, nPerson);
                                } else if (parent.husbandId != null) {
                                    Person parentSpouse = peopleIds.get(parent.husbandId);
                                    mergePerson(parentSpouse, nPerson);
                                }
                            }
                        }
                    }
                }
                nonameProcessed.add(nPerson);
            }
            if (nPerson.wifeId != null) {
                Person wife = peopleIds.get(nPerson.wifeId);
                if (peopleIds.get(wife.husbandId) != null) {
                    mergePerson(peopleIds.get(wife.husbandId), nPerson);
                }
                nonameProcessed.add(nPerson);
            }
            if (nPerson.husbandId != null) {
                Person husband = peopleIds.get(nPerson.husbandId);
                if (peopleIds.get(husband.wifeId) != null) {
                    mergePerson(peopleIds.get(husband.wifeId), nPerson);
                }
                nonameProcessed.add(nPerson);
            }
            if (!nPerson.parentId.isEmpty() && nPerson.firstName != null && !nPerson.firstName.isEmpty()) {
                for (var parentID : nPerson.parentId) {
                    Person parent = peopleIds.get(parentID);
                    if (parent != null) {
                        if ("M".equals(nPerson.gender) && !parent.sonId.isEmpty()) {
                            Optional<Person> currentSon = parent.sonId.stream()
                                    .map(peopleIds::get)
                                    .filter(son -> son != null && son.firstName.equals(nPerson.firstName))
                                    .findFirst();
                            currentSon.ifPresent(person -> mergePerson(person, nPerson));
                        }
                        if ("F".equals(nPerson.gender) && !parent.daughterId.isEmpty()) {
                            Optional<Person> currentDaughter = parent.daughterId.stream()
                                    .map(peopleIds::get)
                                    .filter(daughter -> daughter != null && daughter.firstName.equals(nPerson.firstName))
                                    .findFirst();
                            currentDaughter.ifPresent(person -> mergePerson(person, nPerson));
                        }
                    }
                }
                nonameProcessed.add(nPerson);
            }
            if (!nPerson.siblingsId.isEmpty()) {
                continue;
            }
            if (!nPerson.sisterId.isEmpty() && nPerson.firstName != null && !nPerson.firstName.isEmpty()) {
                for (var sisterID : nPerson.sisterId) {
                    Person sister = peopleIds.get(sisterID);
                    if (sister != null) {
                        if ("M".equals(nPerson.gender) && sister.brotherId != null) {
                            Optional<Person> currentBrother = sister.brotherId.stream()
                                    .map(peopleIds::get)
                                    .filter(brother -> brother != null && brother.firstName.equals(nPerson.firstName))
                                    .findFirst();
                            currentBrother.ifPresent(person -> mergePerson(person, nPerson));
                        }
                        if ("F".equals(nPerson.gender) && sister.sisterId != null) {
                            Optional<Person> currentSister = sister.sisterId.stream()
                                    .map(peopleIds::get)
                                    .filter(sisterTwo -> sisterTwo != null && sisterTwo.firstName.equals(nPerson.firstName))
                                    .findFirst();
                            currentSister.ifPresent(person -> mergePerson(person, nPerson));
                        }
                    }
                }
                nonameProcessed.add(nPerson);
            }
            if (!nPerson.brotherId.isEmpty() && nPerson.firstName != null && !nPerson.firstName.isEmpty()) {
                for (var brotherID : nPerson.brotherId) {
                    Person brother = peopleIds.get(brotherID);
                    if (brother != null) {
                        if ("M".equals(nPerson.gender) && brother.brotherId != null) {
                            Optional<Person> currentBrother = brother.brotherId.stream()
                                    .map(peopleIds::get)
                                    .filter(brotherTwo -> brotherTwo != null && brotherTwo.firstName.equals(nPerson.firstName))
                                    .findFirst();
                            currentBrother.ifPresent(person -> mergePerson(person, nPerson));
                        }
                        if ("F".equals(nPerson.gender) && brother.sisterId != null) {
                            Optional<Person> currentSister = brother.sisterId.stream()
                                    .map(peopleIds::get)
                                    .filter(sister -> sister != null && sister.firstName.equals(nPerson.firstName))
                                    .findFirst();
                            currentSister.ifPresent(person -> mergePerson(person, nPerson));
                        }
                    }
                }
                nonameProcessed.add(nPerson);
            }
        }
        System.out.println("");
        noname.removeAll(nonameProcessed);
    }

    private void mergePerson(Person personToAdd, Person personFromAdd) {
        // person
        if (personToAdd.id == null) {
            personToAdd.id = personFromAdd.id;
        }
        if (personToAdd.firstName == null) {
            personToAdd.firstName = personFromAdd.firstName;
        }
        if (personToAdd.lastName == null) {
            personToAdd.lastName = personFromAdd.lastName;
        }
        if (personToAdd.gender == null) {
            personToAdd.gender = personFromAdd.gender;
        }

        // spouse
        if (personToAdd.spouceName == null) {
            personToAdd.spouceName = personFromAdd.spouceName;
        }
        if (personToAdd.wifeId == null) {
            personToAdd.wifeId = personFromAdd.wifeId;
        }
        if (personToAdd.husbandId == null) {
            personToAdd.husbandId = personFromAdd.husbandId;
        }

        // children
        if (personToAdd.childrenNumber == null) {
            personToAdd.childrenNumber = personFromAdd.childrenNumber;
        }
        personToAdd.daughterId.addAll(personFromAdd.daughterId);
        personToAdd.sonId.addAll(personFromAdd.sonId);
        if (personToAdd.childrenName == null) {
            personToAdd.childrenName = personFromAdd.childrenName;
        }

        // parents
        if (personToAdd.parentName == null) {
            personToAdd.parentName = personFromAdd.parentName;
        }
        personToAdd.parentId.addAll(personFromAdd.parentId);
        if (personToAdd.motherName == null) {
            personToAdd.motherName = personFromAdd.motherName;
        }
        if (personToAdd.fatherName == null) {
            personToAdd.fatherName = personFromAdd.fatherName;
        }

        // siblings
        if (personToAdd.siblingsNumber == null) {
            personToAdd.siblingsNumber = personFromAdd.siblingsNumber;
        }
        personToAdd.siblingsId.addAll(personFromAdd.siblingsId);
        personToAdd.sisterId.addAll(personFromAdd.sisterId);
        personToAdd.brotherId.addAll(personFromAdd.brotherId);
    }
}