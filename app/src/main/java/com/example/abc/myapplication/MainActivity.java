package com.example.abc.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;                  //two dependencies are added in the gradle file
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

        private Button button;
        public int idnumber;
        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            button = (Button) findViewById(R.id.button);
            final Activity activity = this;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentIntegrator integrator = new IntentIntegrator(activity);
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                    integrator.setPrompt("Scan");
                    integrator.setCameraId(0);
                    integrator.setBeepEnabled(false);
                    integrator.setBarcodeImageEnabled(false);
                    integrator.initiateScan();
                }
            });
        }

        @Override                                                                                       //result of the activity
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "you cancelled the scanning", Toast.LENGTH_LONG).show();
                } else {                                                                           //after a correct scan
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    String temp = (String) result.getContents();
                    char temp1 = temp.charAt(0);
                    idnumber = temp1 - '0';                                                   //id number is the checkpoint no
                    Log.d("my", "no is " + idnumber);
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
}
