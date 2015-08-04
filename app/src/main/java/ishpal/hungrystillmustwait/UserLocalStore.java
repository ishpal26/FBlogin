package ishpal.hungrystillmustwait;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ishpal on 1/8/2015.
 */
public class UserLocalStore {
    String SP_NAME = "searchDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME,0);
    }

    public void storeUserData(Restaurant restaurant){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name", restaurant.getName());
        spEditor.putInt("total", restaurant.getTotalWaitingTime());
        spEditor.putInt("numUsers", restaurant.getNumUsers());
        spEditor.putInt("average", restaurant.getAverageWaitingTime());
        spEditor.putInt("current", restaurant.getCurrentWaitingTime());
        spEditor.putString("address", restaurant.getAddress());
        spEditor.commit();
    }

    public Restaurant getCurrentRest(){
        String name = userLocalDatabase.getString("name", "");
        int total = userLocalDatabase.getInt("total", -1);
        int numUsers = userLocalDatabase.getInt("numUsers", -1);
        int average = userLocalDatabase.getInt("average", -1);
        int current = userLocalDatabase.getInt("current", -1);
        String address = userLocalDatabase.getString("address","");

        Restaurant storedRestaurant = new Restaurant(name, total, numUsers, average, current, address);
        return storedRestaurant;
    }

    public void setRestLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getRestLoggedIn(){
        if(userLocalDatabase.getBoolean("loggedIn", false)){
            return true;
        }else{
            return false;
        }
    }


    public void clearCurrentRest(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
