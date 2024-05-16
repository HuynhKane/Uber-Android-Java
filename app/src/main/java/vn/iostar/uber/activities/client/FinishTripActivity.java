package vn.iostar.uber.activities.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import vn.iostar.uber.R;

public class FinishTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_trip);
        getForm();
    }

    private void getForm() {
        LinearLayout btn_x=findViewById(R.id.x);

        btn_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FinishTripActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}