package com.mrz.androidjs;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class JavaScriptInterface {
    protected MainActivity parentActivity;
    protected WebView mWebView;
    
    
    public JavaScriptInterface(MainActivity _activity, WebView _webView)  {
        parentActivity = _activity;
        mWebView = _webView;
        
    }

    @JavascriptInterface
	public void loadURL(String url) {
		final String u = url;

		parentActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mWebView.loadUrl(u);
			}
		});
	}

    @JavascriptInterface
    public void setResult(int val){
        Log.v("mylog","JavaScriptHandler.setResult is called : " + val);

        this.parentActivity.javascriptCallFinished(val);
    }
    
    @JavascriptInterface
    public void calcSomething(int x, int y){
        this.parentActivity.native_to_js("Resultat est : " + (x * y));
    }


    public  void makeToast(){

    }
    
    @JavascriptInterface
    public String testString() {
    	return "test string from java";
    }
}
