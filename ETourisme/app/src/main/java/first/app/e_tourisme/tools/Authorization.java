package first.app.e_tourisme.tools;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.loopj.android.http.RequestParams;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import first.app.e_tourisme.model.User;

public class Authorization {
    public boolean verifyToken(Context context) {
        boolean tokenExists = false;
        try {
            FileInputStream fileInputStream = context.openFileInput("token.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String token = bufferedReader.readLine();
            fileInputStream.close();

            // Vérifiez si le token est non nul et non vide
            if (token != null && !token.isEmpty()) {
                tokenExists = true;
                this.getuserConnecte(token, context);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tokenExists;
    }

    public void getuserConnecte(String token, Context context) {
        CallWebService webServiceCall = new CallWebService();
        String url = "/user/me";

        RequestParams params = new RequestParams();
        params.put("token", token);

        webServiceCall.responsePost(url, params, new WebServiceCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                User userconnecte = new User();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);

                JsonElement jsonElement = gson.fromJson(response, JsonElement.class);
                if(jsonElement != null && jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    if(jsonObject.has("email") && !jsonObject.get("email").isJsonNull()) userconnecte.setEmail(jsonObject.get("email").getAsString());
                    if(jsonObject.has("name") && !jsonObject.get("name").isJsonNull()) userconnecte.setName(jsonObject.get("name").getAsString());
                    if(jsonObject.has("surname") && !jsonObject.get("surname").isJsonNull()) userconnecte.setSurname(jsonObject.get("surname").getAsString());
                    if(jsonObject.has("username") && !jsonObject.get("username").isJsonNull()) userconnecte.setUsername(jsonObject.get("username").getAsString());
                    if(jsonObject.has("gender") && !jsonObject.get("gender").isJsonNull()) userconnecte.setGender(jsonObject.get("gender").getAsInt());
                    if(jsonObject.has("adress") && !jsonObject.get("adress").isJsonNull()) userconnecte.setAddress(jsonObject.get("adress").getAsString());
                    if(jsonObject.has("contact") && !jsonObject.get("contact").isJsonNull()) userconnecte.setContact(jsonObject.get("contact").getAsInt());
                    if(jsonObject.has("birthDate") && !jsonObject.get("birthDate").isJsonNull()) {
                        try {
                            userconnecte.setBirthDate(dateFormat.parse(jsonObject.get("birthDate").getAsString()));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }

                // mampiditra anle user connecte anaty fichier
                saveUserToFile(userconnecte, context);
            }

            @Override
            public void onFailure(int statusCode) {

            }
        });
    }

    private void saveUserToFile(User user, Context context) {
        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        try {
            FileOutputStream fileOutputStream = context.openFileOutput("user_data.json", Context.MODE_PRIVATE);
            fileOutputStream.write(userJson.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
