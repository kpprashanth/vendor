package com.example.android.navigationdrawerexample;

/**
 * Created by parashu on 18/4/15.
 */
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by parashu on 18/4/15.
 */
public class VendorDetails extends Fragment{
    public TextView name,user_id,phone,address,station;
    public Button edit;
    public ProgressDialog pDialog;
    JSONArray js;
    public static String vendor_arr[]=new String[5];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vendor_details, container, false);
        name = (TextView) view.findViewById(R.id.name);
        phone = (TextView) view.findViewById(R.id.phone);
        user_id = (TextView) view.findViewById(R.id.user_id);
        address = (TextView) view.findViewById(R.id.address);
        station= (TextView) view.findViewById(R.id.station);

        try {
            js= new vendor_details().execute(new ApiConnector()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        edit= (Button) view.findViewById(R.id.edit);

        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

               Intent i = new Intent(getActivity(), UpdateVendorDetails.class);
               startActivity(i);
            }
        });

        return view;
    }

    private class vendor_details extends AsyncTask<ApiConnector, Long, JSONArray> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Fetching Orders..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread

            return params[0].Get_Vendor_Details(MainActivity.user_id);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            pDialog.dismiss();
            arrange_order(jsonArray);


        }
    }


    public void arrange_order(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
                name.setText(json.getString("name"));
                user_id.setText(json.getString("user_id"));
                phone.setText(json.getString("phone"));
                address.setText(json.getString("address"));
                station.setText(json.getString("station"));

                vendor_arr[0]=json.getString("name");
                vendor_arr[1]=json.getString("user_id");
                vendor_arr[2]=json.getString("phone");
                vendor_arr[3]=json.getString("address");
                vendor_arr[4]=json.getString("station");

                // new Item_Image().execute(new ApiConnector());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //order_count=0;

        }
    }
}