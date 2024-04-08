package com.example.lostandfound;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class fragmentShowAdvert extends AppCompatActivity {

    private RecyclerView rvLostItems, rvFoundItems;
    private AdvertAdapter lostAdapter, foundAdapter;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_show_advert);

        db = new DatabaseHelper(this);
        rvLostItems = findViewById(R.id.rv_lost_items);
        rvFoundItems = findViewById(R.id.rv_found_items);

        // Setting LayoutManagers
        rvLostItems.setLayoutManager(new LinearLayoutManager(this));
        rvFoundItems.setLayoutManager(new LinearLayoutManager(this));

        List<AdvertItem> lostItems = db.getLostItems();
        List<AdvertItem> foundItems = db.getFoundItems();

        // Debugging: Log the size of the lists to check if data is being retrieved
        Log.d("ShowAdvert", "Lost items size: " + lostItems.size());
        Log.d("ShowAdvert", "Found items size: " + foundItems.size());

        lostAdapter = new AdvertAdapter(lostItems, item -> {
            // Handle lost item click here
            showItemDetails(item);
        });
        foundAdapter = new AdvertAdapter(foundItems, item -> {
            // Handle found item click here
            showItemDetails(item);
        });

        rvLostItems.setAdapter(lostAdapter);
        rvFoundItems.setAdapter(foundAdapter);
    }

    private void showItemDetails(AdvertItem item) {
        Intent intent = new Intent(this, ItemDetailsActivityFragment.class);
        intent.putExtra("id", item.getId()); // Use 'item', not 'selectedItem'
        intent.putExtra("name", item.getName());
        intent.putExtra("description", item.getDescription());
        intent.putExtra("phone", item.getPhone()); // Ensure this data exists in AdvertItem
        intent.putExtra("date", item.getDate()); // Ensure this data exists in AdvertItem
        intent.putExtra("location", item.getLocation()); // Ensure this data exists in AdvertItem
        intent.putExtra("status", item.getStatus()); // Ensure this data exists in AdvertItem
        startActivity(intent);
    }



}
