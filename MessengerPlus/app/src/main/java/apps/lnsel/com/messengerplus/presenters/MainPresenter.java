package apps.lnsel.com.messengerplus.presenters;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import apps.lnsel.com.messengerplus.helpers.VolleyLibrary.AppController;
import apps.lnsel.com.messengerplus.views.Dashboard.MainScreen.MainActivityView;

/**
 * Created by apps2 on 6/14/2017.
 */
public class MainPresenter {
    private MainActivityView view;

    private static final String TAG = "MAIN_REQ";
    private static final String TAG_DATA = "data";
    JSONObject e;
    JSONArray data;

    public MainPresenter(MainActivityView view) {
        this.view = view;
    }

    public void getUsersService(String url) {

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());

                        String str_response = response;

                        try {
                            JSONObject jsonObj = new JSONObject(str_response);

                            String status = jsonObj.getString("status");
                            String message = jsonObj.getString("message");

                            if(status.equals("failed")){
                                view.errorInfo(message);
                            }else{
                                data = jsonObj.getJSONArray(TAG_DATA);
                                for (int i = 0; i < data.length(); i++) {
                                    e = data.getJSONObject(i);

                                    String usrId = e.getString("usrId");
                                    String usrUserName = e.getString("usrUserName");
                                    String usrStatus = e.getString("usrStatus");

                                    view.addUser(usrUserName);

                                }

                                view.updateList();

                            }



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                view.errorInfo("Server not Responding, Please check your Internet Connection");
            }
        });

        AppController.getInstance().addToRequestQueue(req);

    }
}
