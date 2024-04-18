package ru.nsu.kgurin;

import java.util.HashSet;

public class Person {
    // person
    String id;
    String firstName;
    String lastName;
    String gender;

    // spouse
    String spouceName;
    String wifeId;
    String husbandId;

    // children
    String childrenNumber;
    HashSet<String> daughterId = new HashSet<>();
    HashSet<String> sonId = new HashSet<>();
    HashSet<String> childrenName = new HashSet<>();

    // parents
    HashSet<String> parentName = new HashSet<>();
    HashSet<String> parentId = new HashSet<>();
    String motherName;
    String fatherName;
    String motherId;
    String fatherId;

    // siblings
    String siblingsNumber;
    HashSet<String> brothersName = new HashSet<>();
    HashSet<String> sistersName = new HashSet<>();
    HashSet<String> siblingsId = new HashSet<>();
    HashSet<String> sisterId = new HashSet<>();
    HashSet<String> brotherId = new HashSet<>();

    public void concat(Person person) {

        if (person == null) return;

        if (id == null) id = person.id;
        if (firstName == null) firstName = person.firstName;
        if (lastName == null) lastName = person.lastName;
        if (gender == null) gender = person.gender;

        if (spouceName == null) spouceName = person.spouceName;
        if (husbandId == null) husbandId = person.husbandId;
        if (wifeId == null) wifeId = person.wifeId;

        if (childrenNumber == null) childrenNumber = person.childrenNumber;
        childrenName.addAll(person.childrenName);
        daughterId.addAll(person.daughterId);
        sonId.addAll(person.sonId);

        if (siblingsNumber == null) siblingsNumber = person.siblingsNumber;
        brothersName.addAll(person.brothersName);
        sistersName.addAll(person.sistersName);
        siblingsId.addAll(person.siblingsId);
        brotherId.addAll(person.brotherId);
        sisterId.addAll(person.sisterId);

        if (motherName == null) motherName = person.motherName;
        if (fatherName == null) fatherName = person.fatherName;
        if (fatherId == null) fatherId = person.fatherId;
        if (motherId == null) motherId = person.motherId;
        parentName.addAll(person.parentName);
        parentId.addAll(person.parentId);
    }
}
