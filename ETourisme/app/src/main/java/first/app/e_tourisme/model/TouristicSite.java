package first.app.e_tourisme.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import first.app.e_tourisme.tools.CallWebService;
import first.app.e_tourisme.tools.ListeCallBack;
import first.app.e_tourisme.tools.WebServiceCallback;

public class TouristicSite implements Parcelable {

    private String id;
    private String name;
    private String description;
    private Place place;
    private String imageData;

    public TouristicSite() {
    }

    public TouristicSite(String id, String name, String description, Place place, String imageData) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.place = place;
        this.imageData = imageData;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public TouristicSite(String name, String description, Place place) {
        this.name = name;
        this.description = description;
        this.place = place;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return this.name + " " + this.place.getEntitled();
    }

    protected TouristicSite(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        place = in.readParcelable(Place.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeParcelable(place, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TouristicSite> CREATOR = new Creator<TouristicSite>() {
        @Override
        public TouristicSite createFromParcel(Parcel in) {
            return new TouristicSite(in);
        }

        @Override
        public TouristicSite[] newArray(int size) {
            return new TouristicSite[size];
        }
    };

    public void getListeSiteTouristique(ListeCallBack callback) {
        CallWebService webServiceCall = new CallWebService();
        String url = "/touristicSite/allTouristicSite";

        webServiceCall.responseGet(url, null, new WebServiceCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                List<TouristicSite> listes = new ArrayList<>();

                JsonArray reponses = gson.fromJson(response, JsonArray.class);
                if (reponses != null) {
                    for (JsonElement jsonElement : reponses) {
                        if (jsonElement.isJsonObject()) {
                            JsonObject jsonObject = jsonElement.getAsJsonObject();
                            String id = jsonObject.get("_id").getAsString();
                            String name = jsonObject.get("touristicSiteName").getAsString();
                            String description = jsonObject.get("description").getAsString();
                            JsonObject place = jsonObject.getAsJsonObject("placeName");
                            String placeName = place.get("name").getAsString();
                            String imageData = jsonObject.get("imageData").getAsString();

                            Place placeSite = new Place(placeName);

                            TouristicSite site = new TouristicSite(id, name, description, placeSite, imageData);
                            listes.add(site);

                        }
                    }
                }

                callback.onListeResult(listes);
            }

            @Override
            public void onFailure(int statusCode) {
                callback.onListeResult(null);
            }
        });
    }
}
