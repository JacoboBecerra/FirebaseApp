package com.example.myfirebaseapp.repositories;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.myfirebaseapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private static final String TAG = "UserRepository";
    private final FirebaseAuth mAuth;
    private final DatabaseReference mDatabase;
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public UserRepository() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios");
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<Boolean> registerUser(String email, String password, User user) {
        MutableLiveData<Boolean> registrationStatus = new MutableLiveData<>();

        Log.d(TAG, "Iniciando registro de usuario: " + email);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    Log.d(TAG, "Autenticación exitosa");
                    FirebaseUser firebaseUser = authResult.getUser();
                    if (firebaseUser != null) {
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("email", user.getEmail());
                        userData.put("fullName", user.getFullName());
                        userData.put("phone", user.getPhone());

                        String userId = firebaseUser.getUid();
                        Log.d(TAG, "Guardando datos del usuario con ID: " + userId);

                        mDatabase.child(userId)
                                .setValue(userData)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d(TAG, "Datos del usuario guardados exitosamente");
                                    registrationStatus.setValue(true);
                                    errorMessage.setValue(null);
                                })
                                .addOnFailureListener(e -> {
                                    Log.e(TAG, "Error al guardar datos del usuario", e);
                                    registrationStatus.setValue(false);
                                    errorMessage.setValue("Error al guardar datos: " + e.getMessage());
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error en la autenticación", e);
                    registrationStatus.setValue(false);
                    errorMessage.setValue("Error en la autenticación: " + e.getMessage());
                });

        return registrationStatus;
    }

    public MutableLiveData<Boolean> loginUser(String email, String password) {
        MutableLiveData<Boolean> loginStatus = new MutableLiveData<>();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    loginStatus.setValue(true);
                    errorMessage.setValue(null);
                })
                .addOnFailureListener(e -> {
                    loginStatus.setValue(false);
                    errorMessage.setValue("Error en inicio de sesión: " + e.getMessage());
                });

        return loginStatus;
    }
}