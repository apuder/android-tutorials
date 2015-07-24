

package org.androidtutorials.storage.sql;

/**
 * Very simple class that encapsulates the details of an employee: first name,
 * last name, and age.
 */
public class Employee {

    private String firstName;
    private String lastName;
    private int    age;

    public Employee(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }
}
