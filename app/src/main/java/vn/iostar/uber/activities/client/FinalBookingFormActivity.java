package vn.iostar.uber.activities.client;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.iostar.uber.R;
import vn.iostar.uber.controllers.FinalBookingController;
import vn.iostar.uber.controllers.GeocodingHelper;
import vn.iostar.uber.models.LoaiXe;
import vn.iostar.uber.models.TaiXe;
import vn.iostar.uber.models.UuDai;
import vn.iostar.uber.ui.home.home;

public class FinalBookingFormActivity extends AppCompatActivity {
    GeocodingHelper geocodingHelper=new GeocodingHelper();
    TextView diemDon,diemDen,tenXe,giaTien,txt_uuDai,tongTien,thoiGian;
    ImageView icon ,typePayment;
    LinearLayout btn_confirm_booking, btn_x;
    TableLayout table;

    FinalBookingController finalBookingController=new FinalBookingController();


    private String posFrom;
    private String posTo;
    public static TaiXe taiXe=new TaiXe();
    private String typePay= ChooseTypePaymentActivity.typePayment;
    private LoaiXe typeCar= Map_TypeVehicalActivity.loaiXe;
    private UuDai voucher= VoucherActivity.uuDai;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_booking_form);
        getForm();
        getInforBoooking(posFrom,posTo,typePay,typeCar,voucher);
    }

    private void getForm() {
        diemDon = findViewById(R.id.txt_pick_up);
        diemDen = findViewById(R.id.txt_destination);
        icon = findViewById(R.id.icon);
        tenXe = findViewById(R.id.ten);
        giaTien = findViewById(R.id.gia);
        txt_uuDai = findViewById(R.id.txt_voucher);
        tongTien = findViewById(R.id.total_price);
        thoiGian = findViewById(R.id.txt_estimate_time);
        btn_confirm_booking = findViewById(R.id.btn_confirm_booking);
        typePayment = findViewById(R.id.typePay);
        table=findViewById(R.id.table);
        btn_x=findViewById(R.id.x);

        table.setVisibility(View.GONE);


        posFrom=geocodingHelper.getAddressFromLatLng(FinalBookingFormActivity.this,home.from);
        posTo=geocodingHelper.getAddressFromLatLng(FinalBookingFormActivity.this,home.to);

        diemDon.setText(posFrom);
        diemDen.setText(posTo);
        String id= typeCar.getIdLoaiXe();
        switch (id) {
            case "idbike": {
                icon.setImageResource(R.drawable.ic_bike);
                break;
            }
            case "idcar": {
                icon.setImageResource(R.drawable.ic_car);
                break;
            }
            case "idlimo": {
                icon.setImageResource(R.drawable.ic_limo);
                break;
            }

        }
        String type = typePay;
        switch (type){
            case "cash":{
                typePayment.setImageResource(R.drawable.ic_cash);
                break;
            }
            case "visa":{
                typePayment.setImageResource(R.drawable.ic_visa);
                break;
            }
            case "master_card":{
                typePayment.setImageResource(R.drawable.ic_mastercard);
                break;
            }
            case "paypal":{
                typePayment.setImageResource(R.drawable.ic_paypal);
                break;
            }


        }
        tenXe.setText(typeCar.getTenLoaiXe());
        thoiGian.setText("15 min");
        txt_uuDai.setText(voucher.getUuDai());

        btn_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FinalBookingFormActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        btn_confirm_booking.setOnClickListener(new View.OnClickListener() {   //*****
            @Override
            public void onClick(View v) {
                getInforBoooking(posFrom,posTo,typePay,typeCar,voucher);
                table.setVisibility(View.VISIBLE);
                Toast.makeText(FinalBookingFormActivity.this,"Looking for your driver...",Toast.LENGTH_SHORT).show();
                new CountDownTimer(5000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        // Có thể cập nhật giao diện người dùng nếu cần mỗi giây
                    }

                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(FinalBookingFormActivity.this, FoundDriverActivity.class);
                        startActivity(intent);
                        finish(); // Kết thúc Activity hiện tại nếu cần thiết
                    }
                }.start();
            }
        });

    }
    private void getInforBoooking(String posFrom, String posTo, String typePay, LoaiXe typeCar, UuDai voucher) {

        Float initalPrice =finalBookingController.getPriceOfTypeCar(typeCar,home.from,home.to);
        giaTien.setText(initalPrice.toString());


        ////////////caculate final price///////////////
        Double finalPrice =finalBookingController.getPriceUseVoucher(voucher,initalPrice);
        tongTien.setText(finalPrice.toString());

        finalBookingController.paymentInfor(typePay,finalPrice);

        taiXe= finalBookingController.chooseDriver(home.from,home.to,typePay);


    }






}