package com.example.pdflayerapiapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.webkit.WebViewClient;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView clickableTextView;
    private TextView loadingText;
    private EditText editText;
    private String editValue;
    private ProgressBar progressBar;
    private PDFView pdfView;
    private Button downloadButton;
    private String pdfUrl;
    private boolean isResponseSuccessful;

    private static final String API_KEY = "9d0303e2c2ad311d3ba9e8120478da6e";
    private static String URL_TO_CONVERT = "https://survey.stackoverflow.co/2023/#most-popular-technologies-misc-tech-learn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "onCreate MainActivity", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate MainActivity");
        button = findViewById(R.id.searchButton);
        editText = findViewById(R.id.searchEditText);
        clickableTextView = findViewById(R.id.clickableTextView);
        progressBar = findViewById(R.id.loadingProgressBar);
        loadingText = findViewById(R.id.loadingText);
        pdfView = findViewById(R.id.pdfView);
        downloadButton = findViewById(R.id.downloadButton);

        loadPDF();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                progressBar.setVisibility(View.VISIBLE);
                loadingText.setVisibility(View.VISIBLE);

                URL_TO_CONVERT = String.valueOf(editText.getText());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        loadingText.setVisibility(View.GONE);

                        clickableTextView.setText(editValue);

                        if(isResponseSuccessful){
                            downloadButton.setVisibility(View.VISIBLE);
                            downloadButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    openLinkInBrowser(pdfUrl);
                                }
                            });
                        }

                        loadPDF();
                        showClickableText();
                    }
                }, 2000);
            }
        });

        clickableTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity2(v);
            }
        });

    }

    @Override
    protected void onStart() {
        Toast.makeText(this, "onStart MainActivity", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStart MainActivity");
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume MainActivity", Toast.LENGTH_SHORT).show();

        showClickableText();
        clickableTextView.setText(editValue);

        loadPDF();

        if(!(pdfUrl.isEmpty()) && isResponseSuccessful){
            downloadButton.setVisibility(View.VISIBLE);
            downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openLinkInBrowser(pdfUrl);
                }
            });
        }

        Log.d(TAG, "onResume MainActivity");
    }

    @Override
    protected void onPause() {
        Toast.makeText(this, "onPause MainActivity", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onPause MainActivity");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Toast.makeText(this, "onStop MainActivity", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStop MainActivity");
        super.onStop();
    }

    public void gotoActivity2(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    private void showLoading() {
        // Lógica para mostrar um indicador de carregamento, por exemplo, uma ProgressBar
        button.setEnabled(false); // Desativar o botão durante o carregamento
    }

    private void hideLoading() {
        // Lógica para ocultar o indicador de carregamento
        clickableTextView.setVisibility(View.VISIBLE); // Tornar o texto clicável visível
        button.setEnabled(true); // Reativar o botão após o carregamento
    }

    private void showClickableText() {
        clickableTextView.setVisibility(View.VISIBLE); // Tornar o texto clicável visível
    }

    private void loadPDF() {
        OkHttpClient client = new OkHttpClient();

        pdfUrl = "http://api.pdflayer.com/api/convert?access_key="+ API_KEY + "&document_url="+URL_TO_CONVERT+"&test=1";

        Request request = new Request.Builder()
                .url(pdfUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    byte[] pdfContent = response.body().bytes();

                    runOnUiThread(() -> {
                        pdfView.fromBytes(pdfContent)
                                .load();
                    });

                  isResponseSuccessful = true;

                } else {

                }
            }
        });
    }

    private void openLinkInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

}