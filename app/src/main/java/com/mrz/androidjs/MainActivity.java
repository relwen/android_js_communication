package com.mrz.androidjs;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView myWebView;
    TextView myResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myResultView = (TextView)this.findViewById(R.id.myResult);

        myWebView = (WebView)this.findViewById(R.id.myWebView);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl("file:///android_asset/index.html");

        myWebView.addJavascriptInterface(new JavaScriptInterface(this, myWebView), "MyHandler");


        Button btnSet = (Button)this.findViewById(R.id.btnCalc);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callJavaScriptFunctionAndGetResultBack(333, 444);
            }
        });

        Button btnSimple = (Button)this.findViewById(R.id.btnSimple);
        btnSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                native_to_js("NATIF EN WEBVIEW");
            }
        });
    }

    public void native_to_js(String someText){
        Log.v("mylog","changeText is called");
        myWebView.loadUrl("javascript: document.getElementById('test1').innerHTML = '<strong>"+someText+"</strong>'");
    }

    public void callJavaScriptFunctionAndGetResultBack(int val1, int val2){

        myWebView.loadUrl("javascript:window.MyHandler.setResult( addSomething("+val1+","+val2+") )");
    }

    @SuppressLint("WrongConstant")
    public void javascriptCallFinished(final int val){
        Log.v("mylog","MyActivity.javascriptCallFinished is called : " + val);
        Toast.makeText(this, "Callback got val: " + val, 5).show();

        // I need to run set operation of UI on the main thread.
        // therefore, the above parameter "val" must be final
        runOnUiThread(new Runnable() {
            public void run() {
                myResultView.setText("Callback got val: " + val);
            }
        });
    }
}