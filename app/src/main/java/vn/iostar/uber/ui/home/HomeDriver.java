package vn.iostar.uber.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

import vn.iostar.uber.R;
import vn.iostar.uber.activitys.MainActivityDriver;
import vn.iostar.uber.activitys.client.ContactFormActivity;
import vn.iostar.uber.controllers.GeocodingHelper;
import vn.iostar.uber.controllers.YeuCauDatXeController;

public class HomeDriver extends Fragment {

    private HomeDriverViewModel mViewModel;
    private YeuCauDatXeController yeuCauDatXeController=new YeuCauDatXeController();
    GeocodingHelper geocodingHelpe= new GeocodingHelper();

    public static HomeDriver newInstance() {
        return new HomeDriver();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_home_driver, container, false);

        try {
            yeuCauDatXeController.listenClient(getContext(),MainActivityDriver.curPos, FirebaseAuth.getInstance().getUid(), new YeuCauDatXeController.Retriver_Client() {
                @Override
                public void onSuccess(String idClient) {
                    Toast.makeText(getContext(),idClient,Toast.LENGTH_SHORT).show();
                    Log.d("KHANH",idClient);
                    showCustomDialog(idClient);
                }

                @Override
                public void onFail() {

                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeDriverViewModel.class);
        // TODO: Use the ViewModel
    }

    private void showCustomDialog(String idClient) {
        // Tạo dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Inflate layout cho dialog
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.element_driver_wait, null);
        builder.setView(dialogView);

        // Tạo dialog từ builder
        AlertDialog dialog = builder.create();

        // Lấy các view từ layout dialog
        TextView name = dialogView.findViewById(R.id.txtName);
        TextView distance = dialogView.findViewById(R.id.txtkm);
        LinearLayout ok = dialogView.findViewById(R.id.btn_ok);
        LinearLayout nono = dialogView.findViewById(R.id.btn_nono);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    yeuCauDatXeController.acceptThisClient(getContext(), MainActivityDriver.curPos, FirebaseAuth.getInstance().getUid(), idClient, new YeuCauDatXeController.Callback() {
                        @Override
                        public void onSuccess() {
                            Intent intent=new Intent(getContext(), ContactFormActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFail() {

                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                dialog.dismiss();
            }
        });
        nono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yeuCauDatXeController.denyThisClient(getContext(), FirebaseAuth.getInstance().getUid(), idClient, new YeuCauDatXeController.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFail() {

                    }
                });
                dialog.dismiss();
            }
        });

        // Hiển thị dialog
        dialog.show();
    }

}