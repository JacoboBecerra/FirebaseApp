package com.example.myfirebaseapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfirebaseapp.R;
import com.example.myfirebaseapp.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Obtén el ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Obtén las vistas del correo y la contraseña
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);

        // Configura el botón de inicio de sesión
        findViewById(R.id.loginButton).setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Validar los campos de entrada
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Por favor, ingrese un correo y una contraseña.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Intentar iniciar sesión con los datos proporcionados
            loginViewModel.loginUser(email, password);
        });

        // Observar el estado de inicio de sesión
        loginViewModel.getLoginStatus().observe(this, isSuccess -> {
            if (isSuccess) {
                // Si el inicio de sesión fue exitoso, mostrar mensaje y redirigir a MainActivity
                Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show();

                // Redirigir a MainActivity en lugar de un fragmento
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();  // Finalizar la actividad de inicio de sesión para que no se pueda volver atrás
            } else {
                // Si el inicio de sesión falló, mostrar un mensaje de error
                Toast.makeText(LoginActivity.this, "Error en el inicio de sesión.", Toast.LENGTH_SHORT).show();
            }
        });

        // Acción del botón de registro
        findViewById(R.id.registerButton).setOnClickListener(v -> {
            // Redirigir a la actividad de registro
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
