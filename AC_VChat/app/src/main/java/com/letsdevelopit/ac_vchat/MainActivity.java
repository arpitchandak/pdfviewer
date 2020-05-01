package com.letsdevelopit.ac_vchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "msg" ;
    Button getdata;
    TextView data;
    WebView webview;

    PDFView pdfView;
    JsonObjectRequest request;

    RecyclerView recyclerView;
    AdapterCentralList adapter;
    List<ModelcentralClass> modelcentralClassList = new ArrayList<>();

    private RequestQueue mRequestQueue;
    private String url = "https://c63d494c.ngrok.io/upload_pdf/get_pdf.php";
    private SearchView searchView;

    ProgressBar progressBar;

    @Override
    protected void onStart() {
        super.onStart();
        sendAndRequestResponse();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRequestQueue = Volley.newRequestQueue(this);
        recyclerView = findViewById(R.id.recycler);

        progressBar = findViewById(R.id.progress);
        searchView = findViewById(R.id.searchview);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

    }

    private void sendAndRequestResponse() {

        modelcentralClassList.clear();

        request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetJavaScriptEnabled")
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("msg:", "h2");
                        try {
                            JSONArray jsonArray = response.getJSONArray("pdf_files");
                            for(int i=0;i<jsonArray.length();i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String pdf_url = jsonObject1.getString("pdf_url");
                                String img_url = jsonObject1.getString("pdf_img_url");
                                String name = jsonObject1.getString("pdf_name");

                                modelcentralClassList.add(new ModelcentralClass(pdf_url,img_url,name));


                                adapter = new AdapterCentralList(MainActivity.this, modelcentralClassList);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                                recyclerView.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                            }


                        } catch (JSONException e) {
                            Log.d("msg:", "error");
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });
        Log.d("msg:", "h2main");
        mRequestQueue.add(request);


    }
    @SuppressLint("StaticFieldLeak")
    class RetrivePdfStream extends AsyncTask<String, Void, InputStream> {

    @Override
    protected InputStream doInBackground(String... strings) {
        InputStream inputStream = null;
        try {
            URL uri = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            }
        } catch (IOException e) {
            return null;
        }
        return inputStream;
    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        super.onPostExecute(inputStream);
        Toast.makeText(MainActivity.this, "hey", Toast.LENGTH_SHORT).show();
        pdfView.fromStream(inputStream).load();
    }

        @Override
        protected void onCancelled(InputStream inputStream) {
            super.onCancelled(inputStream);
            Toast.makeText(MainActivity.this, "Bye", Toast.LENGTH_SHORT).show();
        pdfView.fromStream(inputStream).load();

        }
    }
}

