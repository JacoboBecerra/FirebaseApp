package com.example.myfirebaseapp;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirebaseapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;  // Biblioteca para cargar imágenes desde URL

public class DashboardActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView itemImageView;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Vincular las vistas
        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        itemImageView = findViewById(R.id.itemImageView);
        logoutButton = findViewById(R.id.logoutButton);

        // Obtener los datos de Firebase (este es un ejemplo con datos ficticios)
        loadDashboardData();

        // Configurar el botón de Logout
        logoutButton.setOnClickListener(v -> logout());
    }

    private void loadDashboardData() {
        // Referencia al nodo "recetas/0" en la base de datos
        DatabaseReference gameRef = mDatabase.child("recetas").child("0");

        gameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Obtener los datos del nodo
                    String itemTitle = dataSnapshot.child("titulo").getValue(String.class);
                    String itemDescription = dataSnapshot.child("descripcion").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imagen").getValue(String.class);

                    // Asignar los datos a las vistas
                    titleTextView.setText(itemTitle);
                    descriptionTextView.setText(itemDescription);

                    // Cargar la imagen usando Picasso
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Picasso.get().load(imageUrl).into(itemImageView);
                    } else {
                        Toast.makeText(DashboardActivity.this, "URL de imagen no válida.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DashboardActivity.this, "No se encontraron datos.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DashboardActivity.this, "Error al cargar datos: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void logout() {
        // Cerrar sesión
        mAuth.signOut();
        // Redirigir al LoginActivity
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Cerrar DashboardActivity
    }
}
