package com.geniusnine.android.marathikavita;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class UserFeedback extends AppCompatActivity {

    ////Azure connections
    private Feedback feedback;

    ///Azure Database connection for contact uploading
    private MobileServiceClient mobileServiceClientFeedbackUploading;
    private MobileServiceTable<Feedback> mobileServiceTableFeedback;
    private FirebaseAuth firebaseAuth;
    private EditText editTextSubject;
    private EditText editTextDescription;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        editTextSubject = (EditText)findViewById(R.id.editTextSubject);
        editTextDescription = (EditText)findViewById(R.id.editTextDescription);
        buttonSubmit = (Button)findViewById(R.id.buttonSubmitFeedBack);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeAzureTable();
                uploadFeedback();
            }
        });

    }
    private void initializeAzureTable() {
        try {
            mobileServiceClientFeedbackUploading = new MobileServiceClient(
                    getString(R.string.web_address),
                    this);
            mobileServiceClientFeedbackUploading.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(20, TimeUnit.SECONDS);
                    client.setWriteTimeout(20, TimeUnit.SECONDS);
                    return client;
                }
            });
            mobileServiceTableFeedback = mobileServiceClientFeedbackUploading.getTable(Feedback.class);


        } catch (MalformedURLException e) {

        } catch (Exception e) {

        }
    }
    private void uploadFeedback() {
        firebaseAuth = FirebaseAuth.getInstance();
        feedback = new Feedback();
        feedback.setFirebaseid(firebaseAuth.getCurrentUser().getUid());
        feedback.setAppid(getString(R.string.app_id));
        feedback.setSubject(editTextSubject.getText().toString());
        feedback.setDescription(editTextDescription.getText().toString());

        try {
            mobileServiceTableFeedback.insert(feedback);
            editTextSubject.setText("");
            editTextDescription.setText("");
            Toast.makeText(UserFeedback.this, "Submitted", Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            Log.e("feedback ", e.toString());
        }

    }

}
