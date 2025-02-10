package com.example.myfirebaseapp.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.myfirebaseapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    private EditText currentPasswordEditText;
    private EditText newPasswordEditText;
    private EditText confirmNewPasswordEditText;
    private Button changePasswordButton;
    private Button themeToggleButton;
    private Button logoutButton;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
        setupListeners();
        displayUserInfo();
    }

    private void initializeViews(View view) {
        currentPasswordEditText = view.findViewById(R.id.currentPasswordEditText);
        newPasswordEditText = view.findViewById(R.id.newPasswordEditText);
        confirmNewPasswordEditText = view.findViewById(R.id.confirmNewPasswordEditText);
        changePasswordButton = view.findViewById(R.id.changePasswordButton);
        themeToggleButton = view.findViewById(R.id.themeToggleButton);
        logoutButton = view.findViewById(R.id.logoutButton);
        mAuth = FirebaseAuth.getInstance();
    }

    private void setupListeners() {
        changePasswordButton.setOnClickListener(v -> changePassword());
        themeToggleButton.setOnClickListener(v -> toggleTheme());
        logoutButton.setOnClickListener(v -> logout());
    }

    private void displayUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Aquí puedes mostrar información del usuario si añades más campos
        }
    }

    private void changePassword() {
        String currentPassword = currentPasswordEditText.getText().toString();
        String newPassword = newPasswordEditText.getText().toString();
        String confirmPassword = confirmNewPasswordEditText.getText().toString();

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showToast("Por favor, complete todos los campos");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showToast("Las nuevas contraseñas no coinciden");
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.updatePassword(newPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            showToast("Contraseña actualizada correctamente");
                            clearPasswordFields();
                        } else {
                            showToast("Error al actualizar la contraseña");
                        }
                    });
        }
    }

    private void toggleTheme() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("isDarkMode", true);

        AppCompatDelegate.setDefaultNightMode(
                isDarkMode ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES
        );

        sharedPreferences.edit().putBoolean("isDarkMode", !isDarkMode).apply();
        requireActivity().recreate();
    }

    private void logout() {
        mAuth.signOut();
        requireActivity().finish();
        startActivity(new android.content.Intent(requireActivity(), LoginActivity.class));
    }

    private void clearPasswordFields() {
        currentPasswordEditText.setText("");
        newPasswordEditText.setText("");
        confirmNewPasswordEditText.setText("");
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
