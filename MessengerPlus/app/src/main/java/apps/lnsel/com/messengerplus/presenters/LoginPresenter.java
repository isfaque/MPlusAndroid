package apps.lnsel.com.messengerplus.presenters;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import apps.lnsel.com.messengerplus.helpers.VolleyLibrary.AppController;
import apps.lnsel.com.messengerplus.views.LoginScreen.LoginActivityView;

/**
 * Created by apps2 on 6/14/2017.
 */
public class LoginPresenter {
    private LoginActivityView view;

    private static final String TAG = "REQ";

    public LoginPresenter(LoginActivityView view) {
        this.view = view;
    }

    public void loginService(String url, final String usrUserName, final String usrPassword){

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        String str_response = response;

                        try {
                            JSONObject jsonObj = new JSONObject(str_response);
                            String status = jsonObj.getString("status");
                            String message = jsonObj.getString("message");
                            if(status.equals("failed")){
                                view.errorInfo(message);
                            }else{
                                String usrId = jsonObj.getString("usrId");
                                String usrUserName = jsonObj.getString("usrUserName");
                                String usrPassword = jsonObj.getString("usrPassword");
                                view.updateSession(usrId, usrUserName, usrPassword);
                                view.successInfo(message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        view.errorInfo("Server not Responding, Please check your Internet Connection");
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("usrUserName",usrUserName);
                params.put("usrPassword",usrPassword);
                return params;
            }
        };

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }
}
