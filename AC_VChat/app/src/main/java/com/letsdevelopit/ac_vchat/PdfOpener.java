package com.letsdevelopit.ac_vchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class PdfOpener extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_opener);

        final Bundle bundle = getIntent().getExtras();
        final String url = bundle.getString("url");

        webView = findViewById(R.id.webview);


        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url="+url);

    }
}
