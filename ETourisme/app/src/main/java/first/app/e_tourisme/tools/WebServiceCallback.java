package first.app.e_tourisme.tools;

public interface WebServiceCallback {
    void onSuccess(String response);
    void onFailure(int statusCode);
}

