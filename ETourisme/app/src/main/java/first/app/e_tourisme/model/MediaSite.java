package first.app.e_tourisme.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import first.app.e_tourisme.tools.CallWebService;
import first.app.e_tourisme.tools.MediaSiteListener;
import first.app.e_tourisme.tools.WebServiceCallback;

public class MediaSite {
    private String touristicSiteName;
    private String imageName;
    private String typeOfMediaEntitled;
    private String imageData;
    private String dateOfPicture;

    public String getTouristicSiteName() {
        return touristicSiteName;
    }

    public void setTouristicSiteName(String touristicSiteName) {
        this.touristicSiteName = touristicSiteName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getTypeOfMediaEntitled() {
        return typeOfMediaEntitled;
    }

    public void setTypeOfMediaEntitled(String typeOfMediaEntitled) {
        this.typeOfMediaEntitled = typeOfMediaEntitled;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getDateOfPicture() {
        return dateOfPicture;
    }

    public void setDateOfPicture(String dateOfPicture) {
        this.dateOfPicture = dateOfPicture;
    }

    public static Bitmap decodeImageData(String imageData) {
        try {
            byte[] decodedString = Base64.decode(imageData, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void fetchMediaSitesPictures(MediaSiteListener callBack) {
        String urlApi = "/mediaSite/allMediaSite";
        CallWebService callWebService = new CallWebService();
        callWebService.responseGet(urlApi, null, new WebServiceCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<MediaSite> mediaSites = new ArrayList<>();
                    if (response != null) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            MediaSite mediaSite = new MediaSite();
                            mediaSite.setTouristicSiteName(jsonObject.optString("touristicSiteName"));
                            mediaSite.setImageName(jsonObject.optString("imageName"));
                            mediaSite.setTypeOfMediaEntitled(jsonObject.optString("typeOfMediaEntitled"));
                            mediaSite.setImageData(jsonObject.optString("imageData"));
                            mediaSite.setDateOfPicture(jsonObject.optString("dateOfPicture"));
                            mediaSites.add(mediaSite);

                        }
                    }

                    callBack.onMediaSitesLoaded(mediaSites);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode) {
                callBack.onMediaSitesLoaded(null);
            }
        });
    }
}
