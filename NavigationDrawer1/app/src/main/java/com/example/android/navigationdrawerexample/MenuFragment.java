package com.example.android.navigationdrawerexample;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by prashanth on 13/3/15.
 */
public class MenuFragment extends Fragment {
    public static int value = 0, total_order = 0,bit_count=0,item_position=0;
    public static ListView menu_list;
    private View inflatedView;
    TextView loginPopup;
    //public static String[] items;


    public static MenuAdapter adapter;
    public static ActionBar at;
    public static String items[][];
    public static boolean flags[];
    public static Bitmap image_item[];
    public static Drawable imagedetail=null;
    public static String imgname = "",delete_user,delete_item;
    public static int image_count = 0;
    public ProgressDialog pDialog,pDialog2;


    public MenuFragment() {
        value = 1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vendor_menu, container, false);
        loginPopup = (TextView) view.findViewById(R.id.menu_title);
        // MenuAdapter ad=new MenuAdapter(getActivity().getApplicationContext());
        menu_list = (ListView) view.findViewById(R.id.menu_list);
        // menu_list.setAdapter(ad);
        new Item_details().execute(new ApiConnector());
        menu_list.setOnItemClickListener(new OrderItemClickListener());

        menu_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView name= (TextView) view.findViewById(R.id.item_name);
                delete_item=items[position][0];
                delete_user=MainActivity.user_id;
                item_position=position;


                AlertDialog diaBox = AskOption();
                diaBox.show();
                Toast.makeText(getActivity().getApplicationContext(),"name: "+name.getText(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return view;

    }

    class MenuAdapter extends BaseAdapter {
LayoutInflater mLayoutInflater;
        private Context context;
        public MenuAdapter(Context context) {
            this.context = context;
            mLayoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return items[0][position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;
            View row = convertView;
            Bitmap img = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                row = inflater.inflate(R.layout.my_item, null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView)row.findViewById(R.id.item_image);
                row.setTag(viewHolder);


                // Log.d("Hey-----------------------------------------------------------","Working------------------");

            } else {
                row = convertView;

            }


            TextView item_name = (TextView) row.findViewById(R.id.item_name);
            TextView item_price = (TextView) row.findViewById(R.id.item_price);
             ImageView item_image = (ImageView) row.findViewById(R.id.item_image);
            item_name.setText("item: " + items[position][0]);
            item_price.setText("price: " + items[position][3]);


            viewHolder = (ViewHolder)row.getTag();
            String url = "http://prashantha.zz.mu/images/" + items[position][0] + ".jpg";
            viewHolder.imageURL = url;
            viewHolder.name=items[position][0];
            item_image.setTag(items[position][0]);
            //Toast.makeText(getActivity().getApplicationContext(),"url: "+items[position][0],Toast.LENGTH_SHORT).show();
            new DownloadAsyncTask(items[position][0]).execute(viewHolder);


            if (items[position][0].equals(items[position][0]) && flags[position]==true ){
                new DownloadAsyncTask(items[position][0]).execute(viewHolder);
                flags[position]=false;
                Toast.makeText(getActivity().getApplicationContext(),"image: "+items[position][0]+items.length,Toast.LENGTH_SHORT).show();
            }

        /*   for (int i=0;i<items.length;i++){
                if (items[i][0].equals(items[position][0]) && flags[i]==true ){
                    new DownloadAsyncTask(items[position][0]).execute(viewHolder);
                    flags[i]=false;
                    Toast.makeText(getActivity().getApplicationContext(),"image: "+items[position][0]+items.length,Toast.LENGTH_SHORT).show();
                }
            }
            */

            //image_item[position]=img;
           // item_image.setImageBitmap(img);


            return row;
        }
    }

    private class Item_details extends AsyncTask<ApiConnector, Long, JSONArray> {
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

            return params[0].Get_item();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            pDialog.dismiss();
            arrange_order(jsonArray);


        }
    }

    private class Get_itemImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public Get_itemImage(ImageView img){
            imageView=img;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Fetching Items..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            //pDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            String url = "http://prashantha.zz.mu/images/" + params[0] + ".jpg";
            Bitmap icon = null;


            try {
                InputStream inputStream = new java.net.URL(url).openStream();
                icon = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return icon;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //
            imageView.setImageBitmap(bitmap);
           // pDialog.dismiss();


        }
    }


    class OrderItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            select_item(position,view);

        }


    }

    public void select_item(int position,View view){

        Intent service=new Intent(getActivity(),ShowItemDetails.class);
        service.putExtra("user_id",items[position][0]);
       // View view=menu_list.getChildAt(position);
        ImageView imageView= (ImageView) view.findViewById(R.id.item_image);
        imagedetail=imageView.getDrawable();

        service.putExtra("position",position);
        startActivity(service);
    }


    public void arrange_order(JSONArray jsonArray) {

        total_order = jsonArray.length();
        items = new String[total_order][4];
        flags=new boolean[total_order];
        bit_count=0;
        image_item = new Bitmap[total_order];
        image_count = 0;

        for (int i = 0; i < jsonArray.length(); i++) {
                //JSONObject js=new JSONObject();
            //js[0];
            flags[i]=true;
            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
                items[i][0] = json.getString("name");
                imgname = json.getString("name");
                items[i][1] = json.getString("discription");
                items[i][2] = json.getString("image");
                items[i][3] = json.getString("price");

                // new Item_Image().execute(new ApiConnector());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //order_count=0;

        }
        adapter = new MenuAdapter(getActivity().getApplicationContext());
        menu_list.setAdapter(adapter);
        //new ImageDownloaderTask().execute();
        //getimages();
    }

    private AlertDialog AskOption()
    {

        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(getActivity())
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        Delete_item();
                        dialog.dismiss();
                    }

                })



                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    private static class ViewHolder {

        ImageView imageView;
        String imageURL,name;
        Bitmap bitmap;

    }
    private class DownloadAsyncTask extends AsyncTask<ViewHolder, Void, ViewHolder> {
        String name;
        DownloadAsyncTask(String name){
            this.name=name;
        }

        @Override
        protected ViewHolder doInBackground(ViewHolder... params) {
            // TODO Auto-generated method stub
            //load image directly
            ViewHolder viewHolder = params[0];
            try {
                URL imageURL = new URL(viewHolder.imageURL);
                viewHolder.bitmap = BitmapFactory.decodeStream(imageURL.openStream());
            } catch (IOException e) {
                // TODO: handle exception
                Log.e("error", "Downloading Image Failed");
                viewHolder.bitmap = null;
            }

            return viewHolder;
        }

        @Override
        protected void onPostExecute(ViewHolder result) {
            // TODO Auto-generated method stub
            if (result.bitmap == null) {
                result.imageView.setImageResource(R.drawable.smileysad);
            } else {
                if (name==result.name) {
                    result.imageView.setImageBitmap(result.bitmap);
                    if (bit_count<image_item.length){
                        image_item[bit_count]=result.bitmap;
                        bit_count++;
                    }
                  //  Toast.makeText(getActivity().getApplicationContext(),"image: "+name+items.length,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    class ItemDelete extends AsyncTask<String,String,String> {
      //  private final WeakReference<ImageView> imageViewReference;
        ImageView img;
        int count=0;
        String name;


        @Override
        protected String doInBackground(String... params) {

           // Toast.makeText(getActivity().getApplicationContext(),"url: "+params[0],Toast.LENGTH_SHORT).show();


            Bitmap icon = null;
            for (int i=0;i<items.length;i++) {
                String url = "http://prashantha.zz.mu/images/" + items[i][0] + ".jpg";
                try {
                    InputStream inputStream = new java.net.URL(url).openStream();

                    icon = BitmapFactory.decodeStream(inputStream);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "";
        }


        @Override
        protected void onPostExecute(String bitmap) {

        }
    }

    private class Delete extends AsyncTask<ApiConnector,Long,JSONArray>
    {
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

            return params[0].Get_Orders();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            pDialog.dismiss();
            if(jsonArray!=null) {
                arrange_order(jsonArray);
                Toast.makeText(getActivity().getApplicationContext(), "Deleted",
                        Toast.LENGTH_LONG).show();
            }


        }
    }

    private void Delete_item() {
        //this.itemimage.setImageBitmap(image);

        boolean flag=false;
        // upload
        final List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("name", delete_item));
        params1.add(new BasicNameValuePair("vendor_id",MainActivity.user_id));
        try {
            new AsyncTask<ApiConnector,Long, Boolean >() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    pDialog2 = new ProgressDialog(getActivity());
                    pDialog2.setMessage("Deleting..");
                    pDialog2.setIndeterminate(false);
                    pDialog2.setCancelable(true);
                    pDialog2.show();

                }

                @Override
                protected Boolean doInBackground(ApiConnector... apiConnectors) {
                    return apiConnectors[0].DeleteItem(params1);
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute(aBoolean);
                    pDialog2.dismiss();
                }
            }.execute(new ApiConnector());
        }
        catch (Exception e){

        }

       // menu_list.removeView(menu_list.getChildAt(item_position));
        Toast.makeText(getActivity().getApplicationContext(), "Updated... "+flag,
                Toast.LENGTH_LONG).show();

    }



}