package com.example.myfirebaseapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myfirebaseapp.repositories.UserRepository;

public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<Boolean> loginStatus;

    public LoginViewModel() {
        userRepository = new UserRepository();
        loginStatus = new MutableLiveData<>();
    }

    public LiveData<Boolean> getLoginStatus() {
        return loginStatus;
    }

    public void loginUser(String email, String password) {
        userRepository.loginUser(email, password).observeForever(success -> {
            loginStatus.setValue(success); // Actualiza el estado de inicio de sesión
        });
    }
}
