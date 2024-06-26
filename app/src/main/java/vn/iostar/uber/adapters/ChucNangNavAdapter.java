package vn.iostar.uber.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import vn.iostar.uber.R;
import vn.iostar.uber.adapters.modelAdapters.ChucNangNav;

public class ChucNangNavAdapter extends ArrayAdapter {
    Activity context;
    int resource;
    ArrayList<ChucNangNav> List= new ArrayList<ChucNangNav>();

    public ChucNangNavAdapter(Context context, int resource, ArrayList<ChucNangNav> list) {
        super(context, resource, list);
        List = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView= convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView = inflater.inflate(R.layout.item_nav_header_list, null);
        ImageView imgHinh = (ImageView)  customView.findViewById(R.id.icon);
        TextView txtTen =(TextView) customView.findViewById(R.id.ten);

        txtTen.setText(List.get(position).getTenChucNang());
        String id=List.get(position).getIdChucNang();
        imgHinh.setImageResource(R.drawable.ic_home);
//        switch (id) {
//            case "idhome": {
//                imgHinh.setImageResource(R.drawable.ic_home);
//                break;
//            }
//            case "idcar": {
//                imgHinh.setImageResource(R.drawable.ic_car);
//                break;
//            }
//            case "idlimo": {
//                imgHinh.setImageResource(R.drawable.ic_limo);
//                break;
//            }
//            default: {
//                imgHinh.setImageResource(R.drawable.ic_home);
//                break;
//            }
//
//        }
        return customView;
    }
}
