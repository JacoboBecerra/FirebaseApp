package com.example.myfirebaseapp.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirebaseapp.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView imageView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Inicialización de las vistas
        titleTextView = findViewById(R.id.detailTitleTextView);
        descriptionTextView = findViewById(R.id.detailDescriptionTextView);
        imageView = findViewById(R.id.detailImageView);
        backButton = findViewById(R.id.backButton);

        // Obtener los datos del Intent que se pasó desde DashboardActivity
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String imageUrl = getIntent().getStringExtra("imageUrl");

        // Configurar los datos en las vistas
        titleTextView.setText(title);
        descriptionTextView.setText(description);

        // Usar Picasso para cargar la imagen desde la URL (si la URL es válida)
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(imageView);
        } else {
            // Si no hay imagen, puedes usar una imagen por defecto
            imageView.setImageResource(R.drawable.ic_launcher_foreground);
        }

        // Configurar el comportamiento del botón de retroceso
        backButton.setOnClickListener(v -> onBackPressed());
    }

    // Método para manejar el clic del botón "Volver"
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();  // Finaliza esta actividad y regresa a la anterior (DashboardActivity)
    }
}
