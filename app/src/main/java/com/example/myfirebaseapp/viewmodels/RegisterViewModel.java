package com.example.myfirebaseapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myfirebaseapp.models.User;
import com.example.myfirebaseapp.repositories.UserRepository;

public class RegisterViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<Boolean> registrationResult = new MutableLiveData<>();

    public RegisterViewModel() {
        userRepository = new UserRepository();
    }

    public LiveData<Boolean> getRegistrationResult() {
        return registrationResult;
    }

    public void registerUser(String fullName, String email, String password, String confirmPassword, String phone, String address) {
        // Verifica que los campos no estén vacíos
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            registrationResult.setValue(false); // Registro fallido por campos vacíos
            return;
        }

        // Verifica que las contraseñas coincidan
        if (!password.equals(confirmPassword)) {
            registrationResult.setValue(false); // Las contraseñas no coinciden
            return;
        }

        // Verifica que el correo tenga un formato válido
        if (!email.contains("@")) {
            registrationResult.setValue(false); // Formato de correo incorrecto
            return;
        }

        // Verifica que la contraseña tenga al menos 6 caracteres
        if (password.length() < 6) {
            registrationResult.setValue(false); // Contraseña demasiado corta
            return;
        }

        // Crea un objeto User con los datos de registro
        User user = new User(null, fullName, email, phone, address);

        // Llamada al repositorio para registrar al usuario
        userRepository.registerUser(email, password, user).observeForever(success -> {
            registrationResult.setValue(success); // Actualiza el estado del registro con el resultado del repositorio
        });
    }
}
