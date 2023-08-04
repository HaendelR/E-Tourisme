package first.app.e_tourisme.tools;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class CallWebService {

    private String urlServer = "https://0669-41-77-19-16.ngrok-free.app";
    private String authorizationHeader;

    public void setAuthorizationHeader(String token) {
        this.authorizationHeader = "Bearer " + token;
    }

    public void clearAuthorizationHeader() {
        this.authorizationHeader = null;
    }

    public void responseGet(String urlApi, RequestParams params, final WebServiceCallback callback) {
        String url = urlServer + urlApi;
        Log.d("Url", url);

        AsyncHttpClient client = new AsyncHttpClient();

        if (authorizationHeader != null) {
            client.addHeader("Authorization", authorizationHeader);
        }
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                callback.onSuccess(str);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                callback.onFailure(statusCode);
            }
        });
    }

    public void responsePost(String urlApi, RequestParams params, final WebServiceCallback callback) {
        String url = urlServer + urlApi;
        Log.d("Url", url);

        AsyncHttpClient client = new AsyncHttpClient();
        if (authorizationHeader != null) {
            client.addHeader("Authorization", authorizationHeader);
        }
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                callback.onFailure(statusCode);
            }
        });

    }
}
