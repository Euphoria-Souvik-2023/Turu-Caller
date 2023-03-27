package com.example.turucaller;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DataLoader extends AsyncTaskLoader<String> {
    private String data;

    public DataLoader(Context context, String data){
        super(context);
        this.data=data;
    }
    @Nullable
    @Override
    public String loadInBackground() {
        return callAPI();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }
    private String callAPI(){
        String url="https://api.api-ninjas.com/v1/validatephone?number="+data;
        final String API_KEY="6nkoL3XobJaZOQdIp//jbQ==TKsjqggAbqwukLog";
        URL link=createURL(url);
        String response="";
        response=connect(link,API_KEY);
        return response;
    }
    private String connect(URL link,final String API_KEY){
        HttpURLConnection httpURLConnection=null;
        InputStream inputStream=null;
        try{
            httpURLConnection=(HttpURLConnection) link.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("x-api-key",API_KEY);
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode()==200){
                inputStream=httpURLConnection.getInputStream();
                String result=readInputStream(inputStream);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return "Client/Server Error";
    }
    private  String readInputStream(InputStream  inputStream) throws  IOException{
        StringBuilder builder=new StringBuilder();
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line=reader.readLine();
        while(line!=null){
            builder.append(line).append("\n");
            line=reader.readLine();
        }
        reader.close();
        return builder.toString();
    }
    private URL createURL(String url){
        URL link=null;
        try{
            link=new URL(url);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return link;
    }
}
