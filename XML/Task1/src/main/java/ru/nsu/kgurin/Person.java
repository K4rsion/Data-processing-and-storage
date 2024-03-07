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

    // siblings
    String siblingsNumber;
    HashSet<String> siblingsId = new HashSet<>();
    HashSet<String> sisterId = new HashSet<>();
    HashSet<String> brotherId = new HashSet<>();

    //spouse
    public void setSpouceName(String spouceName) {
        this.spouceName = spouceName;
    }

    public void setHusbandId(String husbandId) {
        this.husbandId = husbandId;
    }

    public void setWifeId(String wifeId) {
        this.wifeId = wifeId;
    }

    //children
    public void setChildrenNumber(String childrenNumber) {
        this.childrenNumber = childrenNumber;
    }

    public void addChildrenName(String currentChildName) {
        childrenName.add(currentChildName);
    }

    public void addSonId(String currentSonId) {
        sonId.add(currentSonId);
    }

    public void addDaughterId(String currentDaughterId) {
        daughterId.add(currentDaughterId);
    }

    // siblings
    public void setSiblingsNumber(String siblingsNumber) {
        this.siblingsNumber = siblingsNumber;
    }

    public void addSiblingsId(String currentSiblingId) {
        siblingsId.add(currentSiblingId);
    }

    public void addSisterId(String currentSisterId) {
        sisterId.add(currentSisterId);
    }

    public void addBrotherId(String currentBrotherId) {
        brotherId.add(currentBrotherId);
    }

    // person
    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // parents
    public void addParentId(String currentParentId) {
        parentId.add(currentParentId);
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void addParentName(String currentParentName) {
        parentName.add(currentParentName);
    }

    /////////////////////////////////////

    public void concat(Person person) {

        if (person == null) return;

        if (id == null) id = person.id;
        if (firstName == null) firstName = person.firstName;
        if (lastName == null) lastName = person.lastName;
        if (gender == null) gender = person.gender;

        if (spouceName == null) spouceName = person.spouceName;
        if (wifeId == null) wifeId = person.wifeId;
        if (husbandId == null) husbandId = person.husbandId;

        if (childrenNumber == null) childrenNumber = person.childrenNumber;
        daughterId.addAll(person.daughterId);
        sonId.addAll(person.sonId);
        childrenName.addAll(person.childrenName);

        if (siblingsNumber == null) siblingsNumber = person.siblingsNumber;
        sisterId.addAll(person.sisterId);
        brotherId.addAll(person.brotherId);
        siblingsId.addAll(person.siblingsId);

        if (motherName == null) motherName = person.motherName;
        if (fatherName == null) fatherName = person.fatherName;
        parentId.addAll(person.parentId);
        parentName.addAll(person.parentName);
    }

    ////////////////////////////////////
}
