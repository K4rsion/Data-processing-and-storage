package ru.nsu.gurin;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private static final Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/person",
                    "postgres", "admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException, SQLException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream("src/main/resources/people.xml"));
        String currentPersonId = null;

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();

                switch (startElement.getName().getLocalPart()) {
                    case "person":
                        Attribute personIdAttr = startElement.getAttributeByName(new QName("id"));
                        Attribute name = startElement.getAttributeByName(new QName("name"));
                        Attribute gender = startElement.getAttributeByName(new QName("gender"));

                        String fullName = name.getValue();
                        String[] names = fullName.trim().split("\\s+");
                        String firstName = names[0];
                        String lastName = names.length > 1 ? names[1] : "";

                        if (personIdAttr != null) {
                            currentPersonId = personIdAttr.getValue();
                            insertPerson(currentPersonId, firstName, lastName, gender.getValue());
                        }
                        break;

                    case "wife", "husband":
                        Attribute spouseIdAttr = startElement.getAttributeByName(new QName("id"));
                        if (spouseIdAttr != null && currentPersonId != null) {
                            setSpouse(spouseIdAttr.getValue(), currentPersonId);
                        }
                        break;

                    case "mother", "father":
                        Attribute parentIdAttr = startElement.getAttributeByName(new QName("id"));
                        if (parentIdAttr != null && currentPersonId != null) {
                            setParent(parentIdAttr.getValue(), currentPersonId);
                        }
                        break;

                    case "brother", "sister":
                        Attribute siblingIdAttr = startElement.getAttributeByName(new QName("id"));
                        if (siblingIdAttr != null && currentPersonId != null) {
                            setSibling(siblingIdAttr.getValue(), currentPersonId);
                        }
                        break;

                    case "daughter", "son":
                        Attribute childIdAttr = startElement.getAttributeByName(new QName("id"));
                        if (childIdAttr != null && currentPersonId != null) {
                            setChild(childIdAttr.getValue(), currentPersonId);
                        }
                        break;
                }
            }
        }
    }

    public static void insertPerson(String id, String firstName, String lastName, String gender) throws SQLException {
        String sql = "INSERT INTO \"PERSON\" (\"ID\", \"FIRST_NAME\", \"LAST_NAME\", \"GENDER\") " +
                "VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (\"ID\") DO UPDATE SET " +
                "\"FIRST_NAME\" = EXCLUDED.\"FIRST_NAME\", " +
                "\"LAST_NAME\" = EXCLUDED.\"LAST_NAME\", " +
                "\"GENDER\" = EXCLUDED.\"GENDER\"";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, gender);
            preparedStatement.executeUpdate();
        }
    }

    public static void setSpouse(String primaryId, String spouseId) throws SQLException {
        String sql = "INSERT INTO \"SPOUSE\" (\"ID\", \"SPOUSE_ID\") VALUES (?, ?)" +
                "ON CONFLICT (\"ID\") DO UPDATE SET \"SPOUSE_ID\" = EXCLUDED.\"SPOUSE_ID\"";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, primaryId);
        preparedStatement.setString(2, spouseId);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static void setSibling(String primaryId, String siblingId) throws SQLException {
        String sql = "INSERT INTO \"SIBLING\" (\"ID\", \"SIBLING_ID\") VALUES (?, ?)" +
                "ON CONFLICT (\"ID\") DO UPDATE SET \"SIBLING_ID\" = EXCLUDED.\"SIBLING_ID\"";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, primaryId);
        preparedStatement.setString(2, siblingId);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static void setChild(String primaryId, String parentId) throws SQLException {
        String sql = "INSERT INTO \"CHILD\" (\"ID\", \"PARENT_ID\") VALUES (?, ?)" +
                "ON CONFLICT (\"ID\") DO UPDATE SET \"PARENT_ID\" = EXCLUDED.\"PARENT_ID\"";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, primaryId);
        preparedStatement.setString(2, parentId);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public static void setParent(String primaryId, String childId) throws SQLException {
        String sql = "INSERT INTO \"PARENT\" (\"ID\", \"CHILD_ID\") VALUES (?, ?)" +
                "ON CONFLICT (\"ID\") DO UPDATE SET \"CHILD_ID\" = EXCLUDED.\"CHILD_ID\"";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, primaryId);
        preparedStatement.setString(2, childId);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}