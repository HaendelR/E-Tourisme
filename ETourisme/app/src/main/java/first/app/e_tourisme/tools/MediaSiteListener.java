package first.app.e_tourisme.tools;

import java.util.List;

import first.app.e_tourisme.model.MediaSite;

public interface MediaSiteListener {
    void onMediaSitesLoaded(List<MediaSite> mediaSites);
}
