package com.example.turucaller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText input;
    private TextView output;
    private final int LOADER_ID=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data=input.getText().toString();
                if(data.isEmpty()){
                    Toast.makeText(MainActivity.this, "No input given", Toast.LENGTH_SHORT).show();
                    return ;
                }
                LoaderManager manager=LoaderManager.getInstance(MainActivity.this);

                LoaderManager.LoaderCallbacks<String> callbacks=new LoaderManager.LoaderCallbacks<String>() {
                    @NonNull
                    @Override
                    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
                        DataLoader loader=new DataLoader(getApplicationContext(),input.getText().toString());
                        return loader;
                    }

                    @Override
                    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
                        output.setVisibility(View.VISIBLE);
                        output.setText(data);
                    }

                    @Override
                    public void onLoaderReset(@NonNull Loader<String> loader) {

                    }
                };
                manager.initLoader(LOADER_ID,null,callbacks);
            }
        });

    }
    private void init(){
        submitButton= (Button) findViewById(R.id.submit);
        input= (EditText) findViewById(R.id.number);
        output= (TextView) findViewById(R.id.output);
    }

}