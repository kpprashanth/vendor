package com.example.android.navigationdrawerexample;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.navigationdrawerexample.R;

/**
 * Created by parashu on 18/4/15.
 */
public class vendor_deatils extends Fragment{
    TextView name,user_id,phone,address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vendor_details, container, false);
        name = (TextView) view.findViewById(R.id.name);
        phone = (TextView) view.findViewById(R.id.phone);
        user_id = (TextView) view.findViewById(R.id.user_id);
        address = (TextView) view.findViewById(R.id.address);

        return view;
    }
}
