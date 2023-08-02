package first.app.e_tourisme.tools;

import java.util.List;

import first.app.e_tourisme.model.TouristicSite;

public interface ListeCallBack {
    void onListeResult(List<TouristicSite> sites);
}
