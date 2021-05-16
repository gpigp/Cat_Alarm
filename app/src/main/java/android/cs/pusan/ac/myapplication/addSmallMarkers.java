package android.cs.pusan.ac.myapplication;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class addSmallMarkers extends Activity {

    private FirebaseFirestore mDatabase;

    Spinner spinner;
    Button btn_cancel;
    Button btn_submit;

    ArrayList<String> catNames;
    public static Map<String, String> namesAndTypes;
    String selected = "??";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_small_markers);

        catNames = new ArrayList<>();
        catNames.addAll(MainActivity.catNames);
        if( !(catNames.get(0).equals("??")) ) {
            catNames.add(0,"??");
        }
        namesAndTypes = MainActivity.namesAndTypes;

        mDatabase = FirebaseFirestore.getInstance();

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, catNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = catNames.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected = catNames.get(0);
            }
        });

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_submit = findViewById(R.id.btn_submit);

        btn_cancel.setOnClickListener(v -> onBackPressed() );
        btn_submit.setOnClickListener(v -> {
            Map<String, Object> data = new HashMap<>();
            data.put("name", selected);
            if( selected.equals("??") ){
                data.put("type", "white");
            }
            else {
                data.put("type", namesAndTypes.get(selected));
            }
            int pm = 1; int pm2 = 1;
            if( Math.random() < 0.5 ) pm = -1;
            if( Math.random() < 0.5 ) pm2 = -1;
            data.put("latitude", 35.233 + pm * Math.random()*0.005);
            data.put("longitude", 129.08 + pm2 * Math.random()*0.005);
            Date currentTime = Calendar.getInstance().getTime();
            String yyyyMM = new SimpleDateFormat("yyyyMM", Locale.getDefault()).format(currentTime);
            String dd = new SimpleDateFormat("dd", Locale.getDefault()).format(currentTime);
            String detectedTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(currentTime);
            data.put("detectedTime", detectedTime);

            Map<String, Object> newDoc = new HashMap<>();
            newDoc.put("date", yyyyMM);
            mDatabase.document("catSmallMarkers/" + yyyyMM)
                    .get()
                    .addOnCompleteListener(task -> {
                        if( task.isSuccessful() ){
                            Map<String, Object> getDB = task.getResult().getData();
                            if( getDB == null ){
                                Log.d("DB Error", "Error get DB no data", task.getException());
                                mDatabase.document("catSmallMarkers/" + yyyyMM)
                                        .set(newDoc)
                                        .addOnSuccessListener(documentReference -> Log.d("ADD","new Doc"))
                                        .addOnFailureListener(e -> Log.d("ADD","Error adding: ",e));
                            }
                        }
                        else{
                            Log.d("SHOW", "Error show DB", task.getException());
                        }
                    });
            mDatabase.collection("catSmallMarkers/" + yyyyMM + "/" + dd)
                    .add(data)
                    .addOnSuccessListener(documentReference -> Log.d("ADD","Document added ID: "+yyyyMM))
                    .addOnFailureListener(e -> Log.d("ADD","Error adding: ",e));
            onBackPressed();
        });


    }



}
