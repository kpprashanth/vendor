package com.example.android.navigationdrawerexample;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by prashanth on 13/3/15.
 */
public class MyFragment extends Fragment {
public static int value=0;
    public static ListView menu_list;
    public View inflatedView;
    public static int total_order;
    public static String orders[][];

   public static MenuAdapter adapter2;
   TextView loginPopup;
   public static ProgressDialog pDialog;


//nihal42harish@gmail.com
public static ActionBar at;
    public MyFragment(){
            value=1;
    }



    public void setvalue(int val){
        value=val;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.vendor_menu,container,false);
        loginPopup= (TextView) view.findViewById(R.id.menu_title);
        loginPopup.setText("My Cart");
        MenuAdapter ad=new MenuAdapter(getActivity().getApplicationContext());
        menu_list= (ListView) view.findViewById(R.id.menu_list);
        new GetCart().execute(new ApiConnector());
        //menu_list.setAdapter(ad);

        return view;

    }


    class MenuAdapter extends BaseAdapter {

        private Context context;
        String[] mPlanetTitles;
        int []images={R.drawable.myorder1,R.drawable.food1,R.drawable.detail1,R.drawable.logout1,R.drawable.aboutus};

        public MenuAdapter(Context context){
            this.context=context;
            mPlanetTitles=context.getResources().getStringArray(R.array.orders);
        }

        @Override
        public int getCount() {
            return orders.length;
        }

        @Override
        public Object getItem(int position) {
            return orders[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row=null;
            if (convertView==null){
                LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                row = inflater.inflate(R.layout.show_order, parent, false);

                // Log.d("Hey-----------------------------------------------------------","Working------------------");

            }
            else{
                row=convertView;

            }

            TextView order_id = (TextView) row.findViewById(R.id.order_id_title);
            TextView order_user = (TextView) row.findViewById(R.id.order_user);
            TextView order_item = (TextView) row.findViewById(R.id.item_name);
            order_id.setText("Order ID: " + orders[position][1]);
            order_user.setText("customer: " + orders[position][0]);
            order_item.setText("Item: " + orders[position][3]);
            return row;
        }
    }


    private class GetCart extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Fetching...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread

            return params[0].Get_Cart(FragmentPageAdapter.choice);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            pDialog.dismiss();
            arrange_order(jsonArray);


        }
    }


    public void arrange_order(JSONArray jsonArray){

        total_order=jsonArray.length();
        orders=new String[total_order][6];

        for(int i=0; i<jsonArray.length();i++){

            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
                orders[i][0] =json.getString("name");
                orders[i][1] =json.getString("order_id");
                orders[i][2] =json.getString("phone");
                orders[i][3] =json.getString("item");
                orders[i][4] =json.getString("address");
                orders[i][5] =json.getString("price");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter2=new MenuAdapter(getActivity());
            menu_list.setAdapter(adapter2);
        }

    }
}
