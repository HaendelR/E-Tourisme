package first.app.e_tourisme.model;

import java.util.Date;
import java.util.Objects;

public class User {
    // Propriety
    private String name;
    private String surname;
    private String username;
    private String genre;
    private String address;
    private String email;
    private Integer contact;
    private Date birthDate;

    public User(String name, String surname, String username, String genre, String address, String email, Integer contact, Date birthDate) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.genre = genre;
        this.address = address;
        this.email = email;
        this.contact = contact;
        this.birthDate = birthDate;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getContact() {
        return contact;
    }

    public void setContact(Integer contact) {
        this.contact = contact;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean login(String username, String password) {
        if (Objects.equals(username, "admin") && Objects.equals(password, "admin")) return true;
        else return false;
    }
}

