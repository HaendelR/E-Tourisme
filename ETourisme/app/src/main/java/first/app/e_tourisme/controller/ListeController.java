package first.app.e_tourisme.controller;

import first.app.e_tourisme.model.TouristicSite;
import first.app.e_tourisme.tools.ListeCallBack;
import first.app.e_tourisme.tools.ResponseCallBack;

public final class ListeController {
    private static ListeController instance = null;

    private ListeController() {super();}

    public static final ListeController getInstance() {
        if(ListeController.instance == null) {
            ListeController.instance = new ListeController();
        }
        return ListeController.instance;
    }

    public void getAllSites(ListeCallBack callBack) {
        TouristicSite site = new TouristicSite();
        site.getListeSiteTouristique(callBack);
    }
}
