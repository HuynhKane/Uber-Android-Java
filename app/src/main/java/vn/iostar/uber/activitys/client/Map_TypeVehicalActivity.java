package vn.iostar.uber.activitys.client;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.MapsInitializer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iostar.uber.R;
import vn.iostar.uber.adapters.TypeVehicalAdapter;
import vn.iostar.uber.controllers.callAPI.LoaiXeController;
import vn.iostar.uber.models.LoaiXe;
import vn.iostar.uber.retrofit.RetrofitService;
import vn.iostar.uber.ui.map.MapFragment;

public class Map_TypeVehicalActivity extends AppCompatActivity {

    ListView lv_type;
    ArrayList<LoaiXe> listTypeVehical=new ArrayList<>();
    TypeVehicalAdapter typeVehicalAdapter;
    Fragment mapFragment;
    public static LoaiXe loaiXe;
 //   LoaiXeController loaiXeController=new LoaiXeController();
    RetrofitService retrofitService=new RetrofitService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressDialog progressDialog = new ProgressDialog(Map_TypeVehicalActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        setContentView(R.layout.activity_map_type_vehical);
        progressDialog.dismiss();
        MapsInitializer.initialize(Map_TypeVehicalActivity.this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mymap, new MapFragment())
                    .commit();
        }
        getForm();
      //  attackMap();

    }



    private void getForm() {
        lv_type=findViewById(R.id.lv_type);
        listTypeVehical.clear();
        LoaiXeController loaiXeController=retrofitService.getRetrofit().create(LoaiXeController.class);

        loaiXeController.getListLoaiXe().enqueue(new Callback<ArrayList<LoaiXe>>() {
            @Override
            public void onResponse(Call<ArrayList<LoaiXe>> call, Response<ArrayList<LoaiXe>> response) {
                listTypeVehical=  response.body();
                //Log.d("API",listTypeVehical.toString());
                typeVehicalAdapter=new TypeVehicalAdapter(Map_TypeVehicalActivity.this,R.layout.item_type_vehical,listTypeVehical);
                lv_type.setAdapter(typeVehicalAdapter);

            }

            @Override
            public void onFailure(Call<ArrayList<LoaiXe>> call, Throwable t) {

            }
        });


//        loaiXeController.getListLoaiXe(new LoaiXeController.DataRetrievedCallback_LoaiXe() {
//            @Override
//            public void onDataRetrieved(ArrayList<LoaiXe> listLoaiXe) {
//                listTypeVehical=listLoaiXe;
//                typeVehicalAdapter=new TypeVehicalAdapter(Map_TypeVehicalActivity.this,R.layout.item_type_vehical,listTypeVehical);
//                lv_type.setAdapter(typeVehicalAdapter);
//                progressDialog.show();
//            }
//        });
    }
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.mymap);
        if (mapFragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(mapFragment);
            transaction.commit();
        }
        super.onBackPressed();
    }

}


