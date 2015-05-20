package com.example.android.navigationdrawerexample;

/**
 * Created by prashanth on 4/4/15.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by tahseen0amin on 16/02/2014.
 */
public class ApiConnector {

public static Bitmap item_images[];
    public static Bitmap item_sample;
public static String image_name[];
 public static JSONArray image_object;
 public static int image_count=0;
    public JSONArray GetAllCustomers(String username,String password)
    {
        // URL for getting all customers

    //192.168.0.4
        String url = "http://prashantha.zz.mu/login_check.php?username="+username+"&password="+password;

        // Get HttpResponse Object from url.
        // Get HttpEntity from Http Response Object

        HttpEntity httpEntity = null;

        try
        {

            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
           // HttpGet httpGet = new HttpGet(url);
            HttpPost httpPost=new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);

            httpEntity = httpResponse.getEntity();



        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            e.printStackTrace();

            //Log Errors Here



        }

        catch (IOException e) {
            e.printStackTrace();
        }


        // Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null) {
            try {
                String entityResponse = EntityUtils.toString(httpEntity);

                Log.e("Entity Response  : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonArray;


    }



    public JSONArray Insert_Orders(String arr[][],int position)
    {
        // URL for getting all customers


        String url = "http://prashantha.zz.mu/insert_order.php?vendor_id="+arr[position][4]+"&name="+arr[position][1]+"&order_id="+arr[position][0]+
                "&phone="+arr[position][0]+"&item="+arr[position][2]+"&address="+arr[position][0]+"&price="+arr[position][3];

        // Get HttpResponse Object from url.
        // Get HttpEntity from Http Response Object

        HttpEntity httpEntity = null;

        try
        {

            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
            // HttpGet httpGet = new HttpGet(url);
            HttpPost httpPost=new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);

            httpEntity = httpResponse.getEntity();



        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            e.printStackTrace();

            //Log Errors Here



        }

        catch (IOException e) {
            e.printStackTrace();
        }


        // Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null) {
            try {
                String entityResponse = EntityUtils.toString(httpEntity);

                Log.e("Entity Response  : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonArray;


    }


    public JSONArray Get_Orders()
    {
        // URL for getting all customers


        String url = "http://prashantha.zz.mu/vendor_order.php?vendor_id="+MainActivity.user_id;
        //http://prashantha.zz.mu
        // Get HttpResponse Object from url.
        // Get HttpEntity from Http Response Object

        HttpEntity httpEntity = null;

        try
        {

            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
            // HttpGet httpGet = new HttpGet(url);
            HttpGet httpPost=new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);

            httpEntity = httpResponse.getEntity();



        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            e.printStackTrace();

            //Log Errors Here



        }

        catch (IOException e) {
            e.printStackTrace();
        }


        // Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null) {
            try {
                String entityResponse = EntityUtils.toString(httpEntity);

                Log.e("Entity Response  : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonArray;


    }


    public JSONArray Get_item()
    {
        // URL for getting all customers

        String image="",image_url;
        String url = "http://prashantha.zz.mu/get_item.php?vendor_id="+MainActivity.user_id;

        // Get HttpResponse Object from url.
        // Get HttpEntity from Http Response Object

        HttpEntity httpEntity = null;

        try
        {

            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
            // HttpGet httpGet = new HttpGet(url);
            HttpGet httpPost=new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);

            httpEntity = httpResponse.getEntity();



        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            e.printStackTrace();

            //Log Errors Here



        }

        catch (IOException e) {
            e.printStackTrace();
        }


        // Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null) {
            try {
                String entityResponse = EntityUtils.toString(httpEntity);

                Log.e("Entity Response  : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);
                image_object = new JSONArray(entityResponse);
                //image= jsonArray.getJSONObject(0).getString("image");

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        item_images=new Bitmap[image_object.length()];

   /*        for (int i=0;i<image_object.length();i++) {

            JSONObject json = null;
            image_count=i;
            try {
                json = jsonArray.getJSONObject(i);
                image =json.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }



            image_url = "http://prashantha.zz.mu/get_item_image.php?name="+image;

            new AsyncTask<String, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(String... params) {

                    String url = params[0];
                    Bitmap icon = null;


                    try {
                        InputStream inputStream = new java.net.URL(url).openStream();
                        item_sample = BitmapFactory.decodeStream(inputStream);
                        //item_images[image_count]=item_sample;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return item_sample;

                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    //super.onPostExecute(bitmap);

                   item_images[image_count]=item_sample;
                }
            }.execute(image_url);

        }*/

        return jsonArray;


    }


    public JSONArray Get_Cart(int choice)
    {
        // URL for getting all customers

        String php_file="vendor_cart_price.php";

        switch (choice){
            case 0:
                php_file="vendor_cart.php";
                break;
            case 1:
                php_file="vendor_cart_price.php";
                break;
            case 2:
                php_file="vendor_cart_item.php";
                break;
        }


        String url = "http://prashantha.zz.mu/"+php_file;

        // Get HttpResponse Object from url.
        // Get HttpEntity from Http Response Object

        HttpEntity httpEntity = null;

        try
        {

            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
            // HttpGet httpGet = new HttpGet(url);
            HttpPost httpPost=new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);

            httpEntity = httpResponse.getEntity();



        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            e.printStackTrace();

            //Log Errors Here



        }

        catch (IOException e) {
            e.printStackTrace();
        }


        // Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null) {
            try {
                String entityResponse = EntityUtils.toString(httpEntity);

                Log.e("Entity Response  : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonArray;


    }


    public Bitmap Get_Image(String image)
    {
        // URL for getting all customers
image_count=0;
        String url = "http://prashantha.zz.mu/get_item_image.php?name="+image;
        Bitmap icon = null;


        try {
            InputStream inputStream = new java.net.URL(url).openStream();
            icon = BitmapFactory.decodeStream(inputStream);
            item_images[image_count]=item_sample;
            image_count++;
           // Toast.makeText(getApplicationContext(), "username and password not matching",
             //       Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    return icon;
    }

    public Boolean uploadImageToserver(List<NameValuePair> params) {

        // URL for getting all customers
        String url = "http://prashantha.zz.mu/uploadimage.php";



        HttpEntity httpEntity = null;

        try
        {

            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);

            httpEntity = httpResponse.getEntity();
            String entityResponse = EntityUtils.toString(httpEntity);

            Log.e("Entity Response  : ", entityResponse);
            return true;

        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            e.printStackTrace();

            //Log Errors Her


        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }



    public Boolean updateImageToserver(List<NameValuePair> params) {

        // URL for getting all customers
        String url = "http://prashantha.zz.mu/upload_image.php";



        HttpEntity httpEntity = null;

        try
        {

            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);

            httpEntity = httpResponse.getEntity();
            String entityResponse = EntityUtils.toString(httpEntity);

            Log.e("Entity Response  : ", entityResponse);
            return true;

        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            e.printStackTrace();

            //Log Errors Her


        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Boolean DeleteItem(List<NameValuePair> params) {

        // URL for getting all customers
        String url = "http://prashantha.zz.mu/item_delete.php";



        HttpEntity httpEntity = null;

        try
        {

            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);

            httpEntity = httpResponse.getEntity();
            String entityResponse = EntityUtils.toString(httpEntity);

            Log.e("Entity Response  : ", entityResponse);
            return true;

        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            e.printStackTrace();

            //Log Errors Her


        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public JSONArray Get_Vendor_Details(String username)
    {
        // URL for getting all customers

        //192.168.0.4
        String url = "http://prashantha.zz.mu/vendor_details.php?user_id="+username;

        // Get HttpResponse Object from url.
        // Get HttpEntity from Http Response Object

        HttpEntity httpEntity = null;

        try
        {

            DefaultHttpClient httpClient = new DefaultHttpClient();  // Default HttpClient
            // HttpGet httpGet = new HttpGet(url);
            HttpPost httpPost=new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);

            httpEntity = httpResponse.getEntity();



        } catch (ClientProtocolException e) {

            // Signals error in http protocol
            e.printStackTrace();

            //Log Errors Here



        }

        catch (IOException e) {
            e.printStackTrace();
        }


        // Convert HttpEntity into JSON Array
        JSONArray jsonArray = null;

        if (httpEntity != null) {
            try {
                String entityResponse = EntityUtils.toString(httpEntity);

                Log.e("Entity Response  : ", entityResponse);

                jsonArray = new JSONArray(entityResponse);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonArray;


    }

}
