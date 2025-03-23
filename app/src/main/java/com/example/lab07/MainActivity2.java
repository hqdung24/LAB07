package com.example.lab07;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

public class MainActivity2 extends AppCompatActivity {

    GridView gridView;
    List<Publisher> publishers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        gridView = findViewById(R.id.gridView);
        publishers = new ArrayList<>();

        // Sample publishers with images
        publishers.add(new Publisher("VnExpress", R.drawable.logo_vnexpress));
        publishers.add(new Publisher("Thanh Niên", R.drawable.logo_thanhnien));
        publishers.add(new Publisher("Dân Trí", R.drawable.logo_dantri));
        publishers.add(new Publisher("Tuổi Trẻ", R.drawable.logo_tuoitre));
        GridAdapter adapter = new GridAdapter(this, publishers);
        gridView.setAdapter(adapter);

        // Handle click event to open ShowHeadlines
        gridView.setOnItemClickListener((adapterView, view, position, id) -> {
            Publisher selectedPublisher = publishers.get(position);
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            intent.putExtra("publisherName", selectedPublisher.getName());
            intent.putExtra("publisherLogo", selectedPublisher.getImageResId()); // Send the logo
            Log.d("select", "Selected publisher: " + selectedPublisher.getName() + " with logo: " + selectedPublisher.getImageResId());
            startActivity(intent);
        });
    }
}
