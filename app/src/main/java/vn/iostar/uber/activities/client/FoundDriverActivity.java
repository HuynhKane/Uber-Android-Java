package vn.iostar.uber.activities.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import vn.iostar.uber.databinding.ActivityFoundDriverBinding;

public class FoundDriverActivity extends AppCompatActivity {
    private ActivityFoundDriverBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoundDriverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        finishTrip();

    }

    private void finishTrip() {

        binding.btnConfirmDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoundDriverActivity.this, FinishTripActivity.class));


            }
        });
        binding.x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FoundDriverActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        binding.txtTen.setText(FinalBookingFormActivity.taiXe.getTen());
        binding.txtSdt.setText(FinalBookingFormActivity.taiXe.getSdt());



    }
}