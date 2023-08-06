package first.app.e_tourisme.model;

import android.telecom.Call;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.loopj.android.http.RequestParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import first.app.e_tourisme.tools.CallWebService;
import first.app.e_tourisme.tools.CommentCallBack;
import first.app.e_tourisme.tools.ListCommentCallBack;
import first.app.e_tourisme.tools.WebServiceCallback;

public class Commentaire {
    private String userName;

    private String userSurname;

    private String touristicSiteName;

    private String content;

    private Date dateOfComment;

    public Commentaire() {
    }

    public Commentaire(String userName, String userSurname, String touristicSiteName, String content, Date dateOfComment) {
        this.userName = userName;
        this.userSurname = userSurname;
        this.touristicSiteName = touristicSiteName;
        this.content = content;
        this.dateOfComment = dateOfComment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getTouristicSiteName() {
        return touristicSiteName;
    }

    public void setTouristicSiteName(String touristicSiteName) {
        this.touristicSiteName = touristicSiteName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateOfComment() {
        return dateOfComment;
    }

    public void setDateOfComment(Date dateOfComment) {
        this.dateOfComment = dateOfComment;
    }

    public void addComment(Commentaire comment, CommentCallBack callBack) {

        CallWebService webServiceCall = new CallWebService();
        String url = "/comment/addComment";

        RequestParams params = new RequestParams();
        params.put("userName", comment.getUserName());
        params.put("userSurname", comment.getUserSurname());
        params.put("touristicSiteName", comment.getTouristicSiteName());
        params.put("content", comment.getContent());

        webServiceCall.responsePost(url, params, new WebServiceCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                JsonElement jsonElement = gson.fromJson(response, JsonElement.class);
                boolean success = false;
                if (jsonElement != null && jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    if (jsonObject.has("comment")) {
                        success = true;
                    }
                }
                callBack.addCommentResult(success);
            }

            @Override
            public void onFailure(int statusCode) {
                callBack.addCommentResult(false);
            }
        });
    }

    public void getCommentSiteTouristiques(String siteName, ListCommentCallBack callBack) {
        CallWebService webServiceCall = new CallWebService();
        String url = "/comment/touristicSiteName";

        RequestParams params = new RequestParams();
        params.put("touristicSiteName", siteName);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

        webServiceCall.responseGet(url, params, new WebServiceCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                List<Commentaire> listes = new ArrayList<>();

                JsonArray responses = gson.fromJson(response, JsonArray.class);
                if(responses != null) {
                    for(JsonElement jsonElement : responses) {
                        if(jsonElement.isJsonObject()) {
                            try {
                                JsonObject jsonObject = jsonElement.getAsJsonObject();
                                String username = jsonObject.get("userName").getAsString();
                                String userSurname = jsonObject.get("userSurname").getAsString();
                                String touristicSiteName = jsonObject.get("touristicSiteName").getAsString();
                                String content = jsonObject.get("content").getAsString();
                                Date dateComment = dateFormat.parse(jsonObject.get("dateOfComment").getAsString());

                                Commentaire coms = new Commentaire(username, userSurname, touristicSiteName, content, dateComment);
                                listes.add(coms);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
                callBack.onListCommentResult(listes);
            }

            @Override
            public void onFailure(int statusCode) {
                callBack.onListCommentResult(null);
            }
        });
    }

    @Override
    public String toString() {
        return this.userName +" ("+ this.userSurname+")";
    }
}
