package vn.iostar.uber.activities.client;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import vn.iostar.uber.R;
import vn.iostar.uber.adapters.VoucherAdapter;
import vn.iostar.uber.controllers.UuDaiController;
import vn.iostar.uber.models.UuDai;

public class VoucherActivity  extends AppCompatActivity {

    ListView lv_voucher;
    ArrayList<UuDai> listVoucher =new ArrayList<>();
    VoucherAdapter voucherAdapter;
    UuDaiController uuDaiController=new UuDaiController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);
        getForm();

    }

    private void getForm() {
        lv_voucher=findViewById(R.id.lv_voucher);
        listVoucher.clear();
        uuDaiController.getListUuDai(new UuDaiController.DataRetrievedCallback_UuDai() {
            @Override
            public void onDataRetrieved(ArrayList<UuDai> listUuDai) {
                listVoucher = listUuDai;
                voucherAdapter = new VoucherAdapter(VoucherActivity.this,R.layout.item_voucher, listVoucher);
                lv_voucher.setAdapter(voucherAdapter);

            }
        } );





    }
}