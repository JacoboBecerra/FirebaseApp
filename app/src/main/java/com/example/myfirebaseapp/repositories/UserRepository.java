package com.example.myfirebaseapp.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.myfirebaseapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRepository {
    private final FirebaseAuth mAuth;
    private final DatabaseReference mDatabase;

    public UserRepository() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
    }

    public MutableLiveData<Boolean> registerUser(String email, String password, User user) {
        MutableLiveData<Boolean> registrationStatus = new MutableLiveData<>();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            user.setId(firebaseUser.getUid());
                            mDatabase.child(firebaseUser.getUid()).setValue(user)
                                    .addOnCompleteListener(dbTask -> registrationStatus.setValue(dbTask.isSuccessful()));
                        }
                    } else {
                        registrationStatus.setValue(false);
                    }
                });

        return registrationStatus;
    }
}
