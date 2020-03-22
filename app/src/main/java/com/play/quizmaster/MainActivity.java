package com.play.quizmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.play.quizmaster.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {

    private WebView webview;
    private ProgressDialog progressDialog;

    private InterstitialAd mInterstitialAd;

    private boolean error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Make sure this is before calling super.onCreate
        //SM- Splash-Did not work - Ref - https://android.jlelse.eu/the-complete-android-splash-screen-guide-c7db82bce565
//        setTheme(R.style.AppTheme);
        final Activity activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        // SM - Unit Id for Quiz Master########UNCOMMENT##############
        mInterstitialAd.setAdUnitId("ca-app-pub-5628484688168119/4138885941");

        //SM - Unit Id provided by google for testing
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        webview =(WebView)findViewById(R.id.webView);

        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);

        webview.addJavascriptInterface(new Object()
        {
            @JavascriptInterface
            public void performClick()
            {
                //Log.d("TAG", "at performClick");
                //***************NORMAL APP *** WITH AD*************
                //ShowAd();

                //***************PREMIUM APP *** NO AD*************
                LoadHomeURL();

            }
        }, "ad");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                LoadHomeURL();
            }
        });

        webview.getSettings().setDomStorageEnabled(true);
        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
//        webview.loadUrl("http://quizmaster.sytes.net");
//        webview.loadUrl("http://192.168.2.87:8080/quizmaster");

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

 //           public void onLoadResource(WebView view, String url) {
                // Check to see if there is a progress dialog
//                if (progressDialog == null) {
//                    // If no progress dialog, make one and set message
//                    progressDialog = new ProgressDialog(activity);
//                    progressDialog.setMessage("Loading please wait...");
//                    progressDialog.show();
//
//                    // Hide the webview while loading
//                    webview.setEnabled(false);
//                }
//            }

//            public void onPageFinished(WebView view, String url) {
//                // Page is done loading;
//                // hide the progress dialog and show the webview
//                if (progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                    progressDialog = null;
//                    webview.setEnabled(true);
//                }
//            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
                //Your code to do
                Toast.makeText(MainActivity.this, "Failed to connect to the server. Please try again after some time" , Toast.LENGTH_LONG).show();
            }

        });

        //webview.loadUrl("http://192.168.2.95:8080/quizmaster");
        LoadHomeURL();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    private void LoadHomeURL(){
        webview.loadUrl("http://quizmaster.sytes.net");
        //webview.loadUrl("http://192.168.2.87:8080/quizmaster");
        //webview.loadUrl("http://192.168.2.95:8080/quizmaster");
    }
    private void ShowAd(){

        runOnUiThread(new Runnable() {
            @Override public void run() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });

//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        } else {
//            Log.d("TAG", "The interstitial wasn't loaded yet.");
//        }
    }
}