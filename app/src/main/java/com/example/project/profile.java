package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class profile extends AppCompatActivity {

    EditText username,email,Oldpss,Newpss;
    Button change;
    TextView Error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username =findViewById(R.id.Pusername);
        email =findViewById(R.id.PEmail);
        Oldpss = findViewById(R.id.oldpassword);
        Newpss = findViewById(R.id.NewPassword);
        change = findViewById(R.id.Pchange);
        Error = findViewById(R.id.Perror);

        Error.setVisibility(View.INVISIBLE);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = username.getText().toString();
                String Email = email.getText().toString();
                String oldpss = Oldpss.getText().toString();
                String newpss = Newpss.getText().toString();

                if (name.equals("") || Email.equals("") || oldpss.equals("") || newpss.equals("")) {
                    Error.setVisibility(View.VISIBLE);
                } else {
                    Error.setVisibility(View.INVISIBLE);
                    String url = "http://192.168.2.141/projectApp/change.php?username=" + name + "&Email=" + Email +
                            "&oldpassword=" + oldpss + "&newpassword=" + newpss ;
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest request = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                username.setText("");
                                email.setText("");
                                Oldpss.setText("");
                                Newpss.setText("");


                            }
                            },
                            new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error:" + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(request);

                }


            }
        });



    }
}