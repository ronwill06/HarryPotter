package com.rondalewilliams.harrypotter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HarryPotterItemsManager {

    private static HarryPotterItemsManager instance = null;

    public static final String ENDPOINT = "http://de-coding-test.s3.amazonaws.com/books.json";

    OkHttpClient client = new OkHttpClient();

    public static HarryPotterItemsManager sharedInstance() {

        if (instance == null) {
            instance = new HarryPotterItemsManager();
        }

        return instance;
    }

    protected HarryPotterItemsManager() {

    }

    public String getJSON() {

        try {
            Request request = new Request.Builder()
                    .url(ENDPOINT)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return null;
    }



    public List<HarryPotterItem> getHarryPotterItems() throws JSONException {
        List<HarryPotterItem> items = new ArrayList<>();

        try {
            String json = getJSON();
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                HarryPotterItem item = new HarryPotterItem();
                item.setTitle(jsonObject.getString("title"));

                if (!jsonObject.has("author")) {
                    continue;
                }

                item.setAuthor(jsonObject.getString("author"));

                if (!jsonObject.has("imageURL")) {
                    continue;
                }

                item.setImageUrl(jsonObject.getString("imageURL"));
                items.add(item);
            }

            return items;


        } catch (JSONException je) {
            je.printStackTrace();
        }

        return null;
    }


}
