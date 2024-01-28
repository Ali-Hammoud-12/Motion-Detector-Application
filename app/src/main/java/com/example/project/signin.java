package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class signin extends AppCompatActivity {

    EditText username,Email,password,pss2;
    Button Sin;
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Sin = findViewById(R.id.signbtn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        pss2 = findViewById(R.id.password2);
        Email = findViewById(R.id.Email);
        error = findViewById(R.id.error);
        error.setVisibility(View.INVISIBLE);

        Sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = username.getText().toString();
                String email = Email.getText().toString();
                String pass1 = password.getText().toString();
                String pass2 = pss2.getText().toString();

                if (name.equals("") || pass1.equals("") || email.equals("") || pass2.equals("")) {
                    error.setVisibility(View.VISIBLE);
                } else {
                    error.setVisibility(View.INVISIBLE);
                    if (pass1.equals(pass2)) {
                        String url = "http://192.168.2.141/projectApp/add.php?username=" + name + "&Email=" + email +
                                "&password=" + pass1;
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest request = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Error:" + error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                        queue.add(request);
                    } else {

                        error.setVisibility(View.VISIBLE);

                    }


                }


            }
        });


    }
}