package dk.vonfrank.ou44locationservice.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import dk.vonfrank.ou44locationservice.Models.BeaconItem;

/**
 * Created by Von Frank on 29-03-2017.
 */

public class JSONReader {

    public static String loadJSONFromFile(InputStream inputStream){
        InputStream is;
        String json = null;
        try {
            is = inputStream;
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return json;
    }

    public static ArrayList<BeaconItem> ConvertJSONToBeaconItems(String json){
        ArrayList<BeaconItem> items = new ArrayList<BeaconItem>();

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray jsonArray = obj.getJSONArray("beacons");

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject tempObject = jsonArray.getJSONObject(i);
                BeaconItem tempBeaconItem = new BeaconItem(tempObject.getString("instanceId"), tempObject.getString("alias"), tempObject.getString("UUID"), tempObject.getString("room"), tempObject.getString("roomName"), tempObject.getString("level"));
                items.add(tempBeaconItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return items;
    }
}
