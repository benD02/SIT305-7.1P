package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lostandfound.R;
public class fragmentCreateAdvert extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, descriptionEditText, dateEditText, locationEditText;
    private RadioGroup statusRadioGroup;
    private RadioButton lostRadioButton, foundRadioButton;
    private Button submitButton;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_advert); // Make sure this is your activity layout

        // Initialize your views and database helper
        db = new DatabaseHelper(this);
        nameEditText = findViewById(R.id.advertName);
        phoneEditText = findViewById(R.id.advertPhone);
        descriptionEditText = findViewById(R.id.advertDescription);
        dateEditText = findViewById(R.id.advertDate);
        locationEditText = findViewById(R.id.advertLocation);
        statusRadioGroup = findViewById(R.id.statusRadioGroup);
        lostRadioButton = findViewById(R.id.lostRadioButton);
        foundRadioButton = findViewById(R.id.foundRadioButton);
        submitButton = findViewById(R.id.submitAdvert);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private void saveData() {
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();
        String status = lostRadioButton.isChecked() ? "Lost" : "Found";

        // Simple validation
        if (name.isEmpty() || phone.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save to database
        db.addAdvert(name, phone, description, date, location, status);
        Toast.makeText(this, "Advert saved successfully", Toast.LENGTH_SHORT).show();

        // Clear the input fields
        nameEditText.setText("");
        phoneEditText.setText("");
        descriptionEditText.setText("");
        dateEditText.setText("");
        locationEditText.setText("");
        statusRadioGroup.clearCheck();

        Intent intent = new Intent(fragmentCreateAdvert.this, MainActivity.class);
        startActivity(intent);
    }
}
