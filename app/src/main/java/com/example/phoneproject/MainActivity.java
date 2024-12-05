package com.example.phoneproject;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.phoneproject.databinding.ActivityMainBinding;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding  binding;

//Multiple Permission
    //String тут это название разрешений
    final ActivityResultLauncher<String[]> multiplePermission = registerForActivityResult(
        new ActivityResultContracts.RequestMultiplePermissions(),
        (Map<String,Boolean> map) -> {
            //выбирать Manifest andriod!!!
         if(Boolean.TRUE.equals(map.get(Manifest.permission.CALL_PHONE))){
             Toast.makeText(this, "Permission for calls has been granted", Toast.LENGTH_SHORT).show();
         }
         if(Boolean.TRUE.equals(map.get(Manifest.permission.SEND_SMS))){
             Toast.makeText(this, "Permission for sms granted", Toast.LENGTH_SHORT).show();
         }
    }

);
    //Single Permission
    //Phone calls
    final ActivityResultLauncher<String> phonePermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            (Boolean permitted) -> {
                if(permitted) {
                    String phone = "tel: " + binding.phoneText.getText().toString();
                    //1 вариант лучше использовать! та 2й deprecated
                    // startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(phone)));

                    //2 вариант
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(phone)));
                }
                else {
                    Toast.makeText(this, "Grant permission for ringers",
                            Toast.LENGTH_SHORT).show();
                }
            }
    );

    final ActivityResultLauncher<String> smsPermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            permitted -> {
                if(permitted){
                    //1
                   /* String phone = "smsto:" + binding.phoneText.getText();
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(phone));
                    intent.putExtra("sms_body", binding.smsText.getText().toString());
                    startActivity(intent);*/

                    //2
                    SmsManager manager = SmsManager.getDefault();
                    manager.sendTextMessage(
                            binding.phoneText.getText().toString(),
                            null,
                            binding.smsText.getText().toString(),
                            null,
                            null
                    );
                }
                else{
                    Toast.makeText(this, R.string.grant_permission_to_send_sms,
                            Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       // setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Maltiple Permission
        /*multiplePermission.launch(new String[]{
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS
        });*/
        //Settings
        binding.settingsButton.setOnClickListener(v -> {
            Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package: " + getPackageName()));
            startActivity(intent);
        });
        binding.callButton.setOnClickListener(v -> {
            phonePermission.launch((Manifest.permission.CALL_PHONE));
        });

        binding.sendButton.setOnClickListener( v -> {
            smsPermission.launch(Manifest.permission.SEND_SMS);
        });
    }
}