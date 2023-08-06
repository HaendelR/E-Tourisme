package first.app.e_tourisme.tools;

import java.util.List;

import first.app.e_tourisme.model.Commentaire;

public interface ListCommentCallBack {
    void onListCommentResult(List<Commentaire> comments);
}
