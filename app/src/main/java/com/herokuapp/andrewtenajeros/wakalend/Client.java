package com.herokuapp.andrewtenajeros.wakalend;

public class Client {

    public String barangay;
    public String firstname;
    public String lastname;
    public String district;
    public String loan ;


    public Client() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Client(String firstname, String lastname, String barangay, String district, String loan) {
        this.barangay = barangay;
        this.firstname = firstname;
        this.lastname = lastname;
        this.district = district;
        this.loan = loan;
    }

}