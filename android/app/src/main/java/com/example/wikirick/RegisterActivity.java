package com.example.wikirick;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    static String host ="http://10.0.2.2:8000/";

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private Button registerButton;
    private RequestQueue queue;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.queue = Volley.newRequestQueue(this);
        // Establece el diseño de la actividad
        setContentView(R.layout.activity_register);
        editTextUsername = findViewById(R.id.editTextNombre);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        registerButton = findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Llamada a la función para enviar la solicitud de registro al servidor
                // Toast.makeText(RegisterActivity.this, "Nombre: " + editTextUsername.getText().toString(), Toast.LENGTH_SHORT).show();
                sendPostRegister();
            }
        });

    }
    // Método para enviar una solicitud de registro al servidor
    private void sendPostRegister()  {

        // Construye el cuerpo de la solicitud con los datos del usuario
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username", editTextUsername.getText().toString());
            requestBody.put("password", editTextPassword.getText().toString());
            requestBody.put("email", editTextEmail.getText().toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                host + "/users",
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(RegisterActivity.this, "Usuario creado", Toast.LENGTH_LONG).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse == null) {
                            Toast.makeText(RegisterActivity.this, "No se ha establecido la conexión", Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                int serverCode = error.networkResponse.statusCode;
                                if (serverCode == 400) {
                                    Toast.makeText(RegisterActivity.this, "Alguno de los campos es incorrecto", Toast.LENGTH_SHORT).show();
                                }

                                if (serverCode == 409) {
                                    Toast.makeText(RegisterActivity.this, "Ya existe una cuenta con este correo ", Toast.LENGTH_SHORT).show();
                                }
                            }catch (NullPointerException e){}
                        }
                    }
                }
        );
        this.queue.add(request);
    }
}

