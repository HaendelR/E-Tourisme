package first.app.e_tourisme.tools;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class CallWebService {
    private String urlServer = "http://localhost:3000";
    private Object object;


    public Object responseWb(String urlApi) {
        final Object[] object = {null};
        String url = urlServer + urlApi;
        Log.d("Url", url);
        new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
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
}
