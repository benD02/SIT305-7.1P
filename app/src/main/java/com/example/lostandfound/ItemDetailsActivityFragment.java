package com.example.lostandfound;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemDetailsActivityFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_item_details_activity);

        DatabaseHelper db = new DatabaseHelper(this);

        // Get the passed item details
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int itemId = extras.getInt("id");
            ((TextView)findViewById(R.id.itemName)).setText(extras.getString("name"));
            ((TextView)findViewById(R.id.itemDescription)).setText(extras.getString("description"));
            ((TextView)findViewById(R.id.itemPhone)).setText(extras.getString("phone"));
            ((TextView)findViewById(R.id.itemDate)).setText(extras.getString("date"));
            ((TextView)findViewById(R.id.itemLocation)).setText(extras.getString("location"));
            ((TextView)findViewById(R.id.itemStatus)).setText(extras.getString("status"));

            findViewById(R.id.btnRemoveItem).setOnClickListener(view -> {

                Log.d("id", String.valueOf(itemId));
                db.deleteAdvert(itemId);
                finish();
            });
        }
    }
}