package win.winwin.oneapp;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {

    private WebView webview;
    MainActivity mainActivity = new MainActivity();
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        webview.saveState(outState);
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onBackPressed(){
        if(webview.canGoBack()){
            webview.goBack();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webview.loadUrl(mainActivity.getNewUrl());
        //ВЕБВЬЮ
        webview = (WebView) findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (savedInstanceState != null)
            webview.restoreState(savedInstanceState);
        else
            webview.loadUrl(mainActivity.getNewUrl());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        //КОНЕЦ ВЕБВЬЮ
    }
}
