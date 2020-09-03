package com.data.producer.model;

import com.opencsv.bean.CsvBindByPosition;

import java.util.Date;

public class Customer {

    @CsvBindByPosition(position = 0)
    String firstName;
    @CsvBindByPosition(position = 1)
    String lastName;
    @CsvBindByPosition(position = 2)
    String address;
    @CsvBindByPosition(position = 3)
    String dateOfBirth;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Customer anonymize(){
        Customer anonymizedCustomer = new Customer();
        if(this.getFirstName() != null) {
            anonymizedCustomer.setFirstName(this.getFirstName().replaceAll(".", "*"));
        }
        if(this.getLastName() != null) {
            anonymizedCustomer.setLastName(this.getLastName().replaceAll(".", "*"));
        }
        if(this.getAddress() != null) {
            anonymizedCustomer.setAddress(this.getAddress().replaceAll(".", "*"));
        }
        anonymizedCustomer.setDateOfBirth(this.getDateOfBirth());
        return anonymizedCustomer;
    }

    @Override
    public String toString() {
        return "Customer {" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }
}
