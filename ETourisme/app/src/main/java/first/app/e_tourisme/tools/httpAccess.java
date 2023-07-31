package first.app.e_tourisme.tools;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.ClientProtocolException;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.entity.UrlEncodedFormEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class httpAccess extends AsyncTask<String, Integer, Long> {

    private ArrayList<NameValuePair> parameters;
    private String responseReturn = null;
    private AsyncResponse delegate = null;


    /**
     * Constructor
     */
    public httpAccess() {
        parameters = new ArrayList<NameValuePair>();
    }

    /**
     * Add post params
     *
     * @param name
     * @param value
     */
    public void addParam(String name, String value) {
        parameters.add(new BasicNameValuePair(name, value));
    }

    /**
     * Connect http
     *
     * @param strings
     * @return
     */
    @Override
    protected Long doInBackground(String... strings) {
        HttpClient clientHttp = new DefaultHttpClient();
        HttpPost paramHttp = new HttpPost(strings[0]);

        try {
            // Encode param
            paramHttp.setEntity(new UrlEncodedFormEntity(parameters));
            // Connection and send params, wait response
            HttpResponse response = clientHttp.execute(paramHttp);
            // Transform response
            responseReturn = EntityUtils.toString(response.getEntity());
        } catch (UnsupportedEncodingException e) {
            Log.d("Encodage error", "********" + e.toString());
        } catch (ClientProtocolException e) {
            Log.d("Protocol error", "********" + e.toString());
        } catch (IOException e) {
            Log.d("I/O error", "********" + e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Long result) {
        delegate.processFinish((responseReturn.toString()));
    }

}
