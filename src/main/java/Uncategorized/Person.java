package main.java.Uncategorized;

public abstract class Person {
    private String name;
    private String surname;
    private String contactInfo;
    public Person(String name,String surname,String contactInfo){
        this.name = name;
        this.contactInfo = contactInfo;
        this.surname = surname;
    }
}
