package first.app.e_tourisme.tools;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class CallWebService {
    private String urlServer = "https://eb13-197-149-6-248.ngrok-free.app";
    private Object object;


    public Object responseGet(String urlApi, RequestParams params) {
        final Object[] object = {null};
        String url = urlServer + urlApi;
        Log.d("Url", url);
        new AsyncHttpClient().get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);
                Log.d("Value of", str);
                object[0] = responseBody;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return object;
    }

    public void responsePost(String urlApi, RequestParams params, final WebServiceCallback callback) {
        String url = urlServer + urlApi;
        Log.d("Url", url);
        new AsyncHttpClient().post("https://prod.creatorservice-backend.creatorservice.mg/users/login", params, new AsyncHttpResponseHandler() {
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
