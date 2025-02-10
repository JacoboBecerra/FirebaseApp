package com.example.myfirebaseapp.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfirebaseapp.R;
import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {

    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView imageView;
    private Button backButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        titleTextView = view.findViewById(R.id.detailTitleTextView);
        descriptionTextView = view.findViewById(R.id.detailDescriptionTextView);
        imageView = view.findViewById(R.id.detailImageView);
        backButton = view.findViewById(R.id.backButton);

        // Get arguments from bundle
        Bundle args = getArguments();
        if (args != null) {
            String title = args.getString("title");
            String description = args.getString("description");
            String imageUrl = args.getString("imageUrl");

            titleTextView.setText(title);
            descriptionTextView.setText(description);

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(imageView);
            } else {
                imageView.setImageResource(R.drawable.ic_launcher_foreground);
            }
        }

        backButton.setOnClickListener(v -> {
            // Navigate back to previous fragment
            getParentFragmentManager().popBackStack();
        });

        return view;
    }
}