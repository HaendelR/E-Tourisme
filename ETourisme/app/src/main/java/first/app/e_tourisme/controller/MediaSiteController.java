package first.app.e_tourisme.controller;

import android.util.Log;

import first.app.e_tourisme.model.MediaSite;
import first.app.e_tourisme.tools.MediaSiteListener;

public class MediaSiteController {
    private static MediaSiteController instance = null;

    private MediaSiteController() {
        super();
    }

    public static final MediaSiteController getInstance() {
        if (MediaSiteController.instance == null) {
            MediaSiteController.instance = new MediaSiteController();
        }
        return MediaSiteController.instance;
    }

    public void getMediaSites(MediaSiteListener callBack) {
        MediaSite media = new MediaSite();
        media.fetchMediaSitesPictures(callBack);
    }
}
