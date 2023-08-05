package first.app.e_tourisme.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.loopj.android.http.RequestParams;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import first.app.e_tourisme.tools.CallWebService;
import first.app.e_tourisme.tools.ResponseCallBack;
import first.app.e_tourisme.tools.SignInCallBack;
import first.app.e_tourisme.tools.WebServiceCallback;

public class User {
    // Propriety

    private String id;
    private String name;
    private String surname;
    private String username;
    private Integer gender;
    private String address;
    private String email;
    private Integer contact;
    private Date birthDate;

    private String password;

    public User(String id, String name, String surname, String username, Integer genre, String address, String email, Integer contact, Date birthDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.gender = genre;
        this.address = address;
        this.email = email;
        this.contact = contact;
        this.birthDate = birthDate;
    }

    public User(String name, String surname, String username, Integer gender, String address, String email, Integer contact, Date birthDate, String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.contact = contact;
        this.birthDate = birthDate;
        this.password = password;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
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

    public void signIn(User user, SignInCallBack callBack) {
        CallWebService webServiceCall = new CallWebService();
        String url = "/user/adduser";

        RequestParams params = new RequestParams();
        params.put("name", user.getName());
        params.put("surname", user.getSurname());
        params.put("gender", user.getGender());
        params.put("birthDate", user.getBirthDate());
        params.put("adress", user.getAddress());
        params.put("email", user.getEmail());
        params.put("contact", user.getContact());
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());

        webServiceCall.responsePost(url, params, new WebServiceCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                JsonElement jsonElement = gson.fromJson(response, JsonElement.class);
                boolean success = false;
                if (jsonElement != null && jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    if (jsonObject.has("user")) {
                        success = true;
                    }
                }
                callBack.onSignInResult(success);
            }

            @Override
            public void onFailure(int statusCode) {
                Log.e("WebService", "Request failed with status code: " + statusCode);
                callBack.onSignInResult(false);
            }
        });
    }

    public void login(String username, String password, Context context, ResponseCallBack callback) {
        CallWebService webServiceCall = new CallWebService();
        String url = "/user/login";

        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);

        webServiceCall.responsePost(url, params, new WebServiceCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                JsonElement jsonElement = gson.fromJson(response, JsonElement.class);
                boolean success = false;
                if (jsonElement != null && jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    if (jsonObject.has("token")) {
                        String token = jsonObject.get("token").getAsString();
                        try {
                            FileOutputStream fileOutputStream = context.openFileOutput("token.txt", Context.MODE_PRIVATE);
                            fileOutputStream.write(token.getBytes());
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d("WebService", "Response: " + token);
                        success = true;
                    }
                }
                callback.onLoginResult(success);
            }

            @Override
            public void onFailure(int statusCode) {
                Log.e("WebService", "Request failed with status code: " + statusCode);
                callback.onLoginResult(false);
            }
        });
    }

}

