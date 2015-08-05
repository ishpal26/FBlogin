package ishpal.hungrystillmustwait;

/**
 * Created by Ishpal on 1/8/2015.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ishpal on 24/7/2015.
 */
public class ServerRequests {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://hungrystillmustwait.netne.net/";

    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait");

    }

    public void storeUserDataInBackground(Restaurant restaurant, GetCallback callBack) {
        progressDialog.show();
        new StoreDataAsyncTask(restaurant, callBack).execute();
    }

    public void fetchUserDataInBackground(Restaurant restaurant, GetCallback callBack) {
        progressDialog.show();
        new FetchDataAsyncTask(restaurant, callBack).execute();
    }

    public void updateUserDataInBackground(Restaurant restaurant, GetCallback callback) {
        new UpdateDataAsyncTask(restaurant, callback).execute();
    }

    public class StoreDataAsyncTask extends AsyncTask<Void, Void, Void> {
        Restaurant restaurant;
        GetCallback restCallBack;

        public StoreDataAsyncTask(Restaurant restaurant, GetCallback restCallBack) {
            this.restaurant = restaurant;
            this.restCallBack = restCallBack;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("name", restaurant.getName()));
            dataToSend.add(new BasicNameValuePair("total", restaurant.getTotalWaitingTime() + ""));
            dataToSend.add(new BasicNameValuePair("numUsers", restaurant.getNumUsers() + ""));
            dataToSend.add(new BasicNameValuePair("average", restaurant.getAverageWaitingTime() + ""));
            dataToSend.add(new BasicNameValuePair("current", restaurant.getCurrentWaitingTime() + ""));
            dataToSend.add(new BasicNameValuePair("address", restaurant.getAddress()));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");


            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            restCallBack.done(null);
        }
    }


    public class FetchDataAsyncTask extends AsyncTask<Void, Void, Restaurant> {
        Restaurant restaurant;
        GetCallback restCallBack;

        public FetchDataAsyncTask(Restaurant restaurant, GetCallback restCallBack) {
            this.restaurant = restaurant;
            this.restCallBack = restCallBack;
        }

        @Override
        protected Restaurant doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("name", restaurant.getName()));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchUserData.php");

            Restaurant returnedRestaurant = null;

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                if (jObject.length() == 0) {
                    returnedRestaurant = null;
                } else {
                    Log.v("happened", "2");
                    String name = jObject.getString("name");
                    int total = jObject.getInt("total");
                    int numUsers = jObject.getInt("numUsers");
                    int average = jObject.getInt("average");
                    int current = jObject.getInt("current");
                    String address = jObject.getString("address");

                    returnedRestaurant = new Restaurant(name, total, numUsers, average, current, address);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnedRestaurant;
        }

        @Override
        protected void onPostExecute(Restaurant returnedRestaurant) {
            super.onPostExecute(returnedRestaurant);
            progressDialog.dismiss();
            restCallBack.done(returnedRestaurant);
        }
    }


    public class UpdateDataAsyncTask extends AsyncTask<Void, Void, Void> {
        Restaurant restaurant;
        GetCallback restCallBack;

        public UpdateDataAsyncTask(Restaurant restaurant, GetCallback restCallBack) {
            this.restaurant = restaurant;
            this.restCallBack = restCallBack;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("name", restaurant.getName()));
            dataToSend.add(new BasicNameValuePair("total", restaurant.getTotalWaitingTime() + ""));
            dataToSend.add(new BasicNameValuePair("numUsers", restaurant.getNumUsers() + ""));
            dataToSend.add(new BasicNameValuePair("average", restaurant.getAverageWaitingTime() + ""));
            dataToSend.add(new BasicNameValuePair("current", restaurant.getCurrentWaitingTime() + ""));
            dataToSend.add(new BasicNameValuePair("address", restaurant.getAddress()));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Update.php");


            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            restCallBack.done(null);
        }
    }
}
