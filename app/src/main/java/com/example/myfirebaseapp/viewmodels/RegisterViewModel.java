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
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            registrationResult.setValue(false);
            return;
        }

        if (!password.equals(confirmPassword)) {
            registrationResult.setValue(false);
            return;
        }

        if (!email.contains("@")) {
            registrationResult.setValue(false);
            return;
        }

        if (password.length() < 6) {
            registrationResult.setValue(false);
            return;
        }

        User user = new User(null, fullName, email, phone, address);
        registrationResult.setValue(userRepository.registerUser(email, password, user).getValue());
    }
}
