package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class admin extends AppCompatActivity {

    ListView lv;
    JSONArray data;
    CustomAdapter cs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        lv = findViewById(R.id.lv);
        getdatafromdb();

    }


    @Override
    protected void onResume() {
        getdatafromdb();
        super.onResume();
    }

    public void getdatafromdb(){
        String url = "http://192.168.2.141/projectApp/viewall.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        data = response;
                        cs = new CustomAdapter(admin.this,data);
                        lv.setAdapter(cs);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    { // TODO: Handle error
                        Toast.makeText(getApplicationContext(),"Error:"+error.toString(), Toast.LENGTH_SHORT).show();
                    } });
        queue.add(jsonArrayRequest);
    }
}