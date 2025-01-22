package com.example.myfirebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.loginButton).setOnClickListener(v -> loginUser());
        findViewById(R.id.registerButton).setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();

        // Validar si los campos están vacíos
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Por favor, ingrese un correo y una contraseña.", Toast.LENGTH_SHORT).show();
            return; // No continuar si los campos están vacíos
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show();

                        // Redirige a DashboardActivity
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Finaliza la LoginActivity para evitar que el usuario regrese aquí al presionar "Atrás"
                    } else {
                        Toast.makeText(LoginActivity.this, "Error en autenticación.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}



