package com.example.myfirebaseapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.myfirebaseapp.R;
import com.example.myfirebaseapp.viewmodels.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        EditText nombreCompleto = findViewById(R.id.nombreCompleto);
        EditText editEmail = findViewById(R.id.editEmail);
        EditText editPassword = findViewById(R.id.editPassword);
        EditText confirmPassword = findViewById(R.id.confirmPassword);
        EditText telefono = findViewById(R.id.telefono);
        EditText direccion = findViewById(R.id.direccion);
        Button registerButton = findViewById(R.id.registerButton);
        Button loginButton = findViewById(R.id.loginButton);

        registerButton.setOnClickListener(v -> {
            String fullName = nombreCompleto.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            String confirmPass = confirmPassword.getText().toString().trim();
            String phone = telefono.getText().toString().trim();
            String address = direccion.getText().toString().trim();

            Log.d(TAG, "Intento de registro con email: " + email);
            registerViewModel.registerUser(fullName, email, password, confirmPass, phone, address);
        });

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        observeViewModel();
    }

    private void observeViewModel() {
        registerViewModel.getRegistrationResult().observe(this, isSuccess -> {
            if (isSuccess) {
                Log.d(TAG, "Registro exitoso");
                Toast.makeText(RegisterActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Log.d(TAG, "Registro fallido");
                // No mostrar Toast aquí, se manejará en los otros observers
            }
        });

        registerViewModel.getValidationError().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Log.d(TAG, "Error de validación: " + error);
                Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_LONG).show();
            }
        });

        registerViewModel.getErrorMessage().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Log.e(TAG, "Error de Firebase: " + error);
                Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_LONG).show();
            }
        });
    }
}