package com.example.prosoft.taxiloy.api;

import android.content.Context;
import android.os.AsyncTask;

import com.example.prosoft.taxiloy.ui.enums.API_Method;
import com.example.prosoft.taxiloy.ui.objects.ModelError;
import com.example.prosoft.taxiloy.ui.objects.ModelObject;
import com.example.prosoft.taxiloy.ui.utils.TaxiLoyDebug;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by prosoft on 12/24/15.
 */
public class CallBackAPI extends AsyncTask<List<Object>, String, JSONObject> {

    private OnTaskComplete onTaskComplete;
    private String method;

    public interface OnTaskComplete {
        public void setMyTaskComplete(JSONObject result);

        public void onStart();

        public void onSuccess(ModelObject modelObject);

        public void onFail(ModelError error);

        public void onComplete();
    }

    public void setMyTaskCompleteListener(OnTaskComplete onTaskComplete) {
        this.onTaskComplete = onTaskComplete;
    }

    public CallBackAPI() {

    }

    public CallBackAPI(Context context) {

    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected JSONObject doInBackground(List<Object>... params) {

        //Generating random number between:
        //params[0] is url
        //params[1] is method
        JsonParser json = new JsonParser();

        String url = params[0].get(0).toString();
        String method = params[0].get(1).toString();
        Object param = (Object) params[0].get(2);

        this.method = method;
        JSONObject result = null;
        if (this.method == API_Method.POST) {
            result = json.makeHttpRequest(url, method, param);
        } else if(this.method == API_Method.GET) {
            result = json.getHttpRequest(url, method);
        } else {
            result = json.putHttpRequest(url, method, param);
        }
        //message
        return result;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        try {
            if (result.has("error") || result.has("code")) {
                //onTaskComplete.setMyTaskComplete(result);
                onTaskComplete.onFail(processErrorResponse(result));
            } else {
                /**
                 Object modelJson = result.get(modelObj.getKeyPath());

                 modelObj.setValuesWithJSON(modelJson);

                 boolean clean = modelObj.isClean();

                 if (clean) {
                 modelObject = modelObj;
                 }
                 onTaskComplete.onSuccess(modelObject);**/

                onTaskComplete.setMyTaskComplete(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public Class dataClassForPostResponse() {
        TaxiLoyDebug.d(this.getClass().toString() + " Need to implement dataClassForPostResponse");
        return null;
    }

    public Class dataClassForGetResponse() {
        TaxiLoyDebug.d(this.getClass().toString() + " Need to implement dataClassForGetResponse");
        return null;
    }


    private ModelError processErrorResponse(Object modelJSON) {
        ModelError error = new ModelError();
        error.setValuesWithJSON(modelJSON);

        if (!error.isClean()) {
            error = new ModelError(ModelError.UNSPECIFIED_ERROR, "");
        }
        return error;
        //processErrorResponse(error);
    }
}