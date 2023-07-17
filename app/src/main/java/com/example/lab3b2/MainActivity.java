package com.example.lab3b2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button btnOject,btnArray;
    TextView result;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOject = findViewById(R.id.btnObj);
        btnArray = findViewById(R.id.btnArray);
        result = findViewById(R.id.txtResult);
        //
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        btnOject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       getJsonObject();
                   }
               },3000);
            }
        });

        btnArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getJsonArray();
                    }
                },3000);
            }
        });


    }
    public void getJsonArray(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String strURL = ("http://192.168.1.79:3000/getJsonArray");
        JsonArrayRequest  jsonArrayRequest = new JsonArrayRequest(strURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("TAG", "onResponse: "+ response.toString());
                try {
                    String JsonRespone = "";
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");
                        JSONObject phone = jsonObject.getJSONObject("phone");
                        String home = phone.getString("home");
                        String mobile = phone.getString("mobile");

                        JsonRespone += "NAME:" + name + "\n\n";
                        JsonRespone += "Email:" + email + "\n\n";
                        JsonRespone += "Home:" + home + "\n\n";
                        JsonRespone += "Mobile:" + mobile + "\n\n";

                        result.setText(JsonRespone);
                    }
                    progressDialog.hide();

                }catch (Exception e){
                    progressDialog.hide();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);

    }
    public void getJsonObject(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String strURL = ("http://192.168.1.79:3000/getJsonObject");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, strURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String JsonRespone = "";

                    String name = response.getString("name");
                    String email = response.getString("email");
                    JSONObject phone = response.getJSONObject("phone");
                    String home = phone.getString("home");
                    String mobile = phone.getString("mobile");

                    JsonRespone += "NAME:" + name + "\n\n";
                    JsonRespone += "Email:" + email + "\n\n";
                    JsonRespone += "Home:" + home + "\n\n";
                    JsonRespone += "Mobile:" + mobile + "\n\n";

                    result.setText(JsonRespone);


                }catch (Exception e){
                    progressDialog.hide();
                }
                progressDialog.hide();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

    }

}