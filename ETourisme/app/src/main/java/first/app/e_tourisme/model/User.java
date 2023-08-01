package first.app.e_tourisme.model;

import android.util.Log;

import com.loopj.android.http.RequestParams;

import java.util.Date;
import java.util.Objects;

import first.app.e_tourisme.tools.CallWebService;
import first.app.e_tourisme.tools.WebServiceCallback;

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

    private String responseRequest;

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

    public void setResponseRequest(String response) {
        Log.d("Ato izy", response);
        this.responseRequest = response;
    }

    public Boolean login(String username, String password) {
        CallWebService webServiceCall = new CallWebService();

        String url = "/user/login";

        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);

        webServiceCall.responsePost(url, params, new WebServiceCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d("WebService", "Response: " + response);
                setResponseRequest(response);
            }

            @Override
            public void onFailure(int statusCode) {
                Log.e("WebService", "Request failed with status code: " + statusCode);
            }
        });


        if (Objects.equals(username, "admin") && Objects.equals(password, "admin")) return true;
        else return false;
    }
}

