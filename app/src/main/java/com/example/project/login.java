package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class login extends AppCompatActivity {

    EditText username,password;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.loginbtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();
                String pss = password.getText().toString();
                if(name.equalsIgnoreCase("admin") && pss.equalsIgnoreCase("admin")){
                    Intent i = new Intent(com.example.project.login.this,admin.class);
                    startActivity(i);
                }
                else{
                    String url = "http://192.168.2.141/projectApp/login.php?username="+ name +"&password=" + pss;
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest request = new  StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response)
                                { Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(com.example.project.login.this,camera.class);
                                    startActivity(i);

                                } },
                            new Response.ErrorListener(){
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                    Toast.makeText(getApplicationContext(), "Error:" + error.toString(), Toast.LENGTH_SHORT).show();}

                            } );
                    queue.add(request); }
            }

        });

    }

    public void SigninF(View v){
        Intent i = new Intent(this,signin.class);
        startActivity(i);
    }
}