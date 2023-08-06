package first.app.e_tourisme.controller;

import first.app.e_tourisme.model.Commentaire;
import first.app.e_tourisme.tools.CommentCallBack;

public class CommentaireController {

    private static CommentaireController instance = null;

    private CommentaireController() {super();}

    public static final CommentaireController getInstance() {
        if(CommentaireController.instance == null) {
            CommentaireController.instance = new CommentaireController();
        }

        return CommentaireController.instance;
    }

    public void addComment(Commentaire comment, CommentCallBack callBack) {
        comment.addComment(comment, callBack);
    }
}
