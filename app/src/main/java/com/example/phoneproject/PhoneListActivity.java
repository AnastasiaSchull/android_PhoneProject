package com.example.phoneproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phoneproject.adapters.PhoneAdapter;
import java.util.Arrays;
import java.util.List;

public class PhoneListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PhoneAdapter adapter;
    private List<String> phoneNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phone_list);
        Log.d("t", "Activity Phone started");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // настройка RecyclerView
        phoneNumbers = Arrays.asList("1234567890", "0987654321", "1112223334");
        recyclerView = findViewById(R.id.phoneRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PhoneAdapter(this, phoneNumbers);

        adapter.setOnItemClickListener(phoneNumber -> {
            // возвращаем выбранный номер в MainActivity
            Intent returnIntent = new Intent();
            returnIntent.putExtra("selectedPhoneNumber", phoneNumber);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });

        recyclerView.setAdapter(adapter);
    }
}