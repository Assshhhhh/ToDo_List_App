package com.example.to_dolistapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.to_dolistapp.R;
import com.example.to_dolistapp.databinding.ActivityDataInsertBinding;

public class DataInsertActivity extends AppCompatActivity {

    private ActivityDataInsertBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataInsertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        String type = getIntent().getStringExtra("type");
        if (type.equals("updateMode")){

            int id = getIntent().getIntExtra("id", 0);
            binding.editTitle.setText(getIntent().getStringExtra("title"));
            binding.editDisp.setText(getIntent().getStringExtra("disp"));

            binding.addButton.setText("Update");
            binding.addButton.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.putExtra("title", binding.editTitle.getText().toString().trim());
                intent.putExtra("desc", binding.editDisp.getText().toString().trim());
                intent.putExtra("id", id);
                setResult(RESULT_OK, intent);
                finish();
            });

        } else {

            binding.addButton.setOnClickListener(v -> {

                Intent intent = new Intent();
                intent.putExtra("title", binding.editTitle.getText().toString().trim());
                intent.putExtra("desc", binding.editDisp.getText().toString().trim());
                setResult(RESULT_OK, intent);
                finish();

            });

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DataInsertActivity.this, MainActivity.class));
    }
}