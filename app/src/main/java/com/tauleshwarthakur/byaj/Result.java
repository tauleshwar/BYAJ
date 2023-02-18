package com.tauleshwarthakur.byaj;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

public class Result extends AppCompatActivity {

    FloatingActionButton ShareButton;
    ConstraintLayout invoice;

    private TextView cretedOn;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;


    private TextView reciept_type_display;
    private TextView start_date_display;
    private TextView end_date_display;
    private TextView runtime_display;
    private TextView principle_display;
    private TextView interest_rate_display;
    private TextView interest_display;
    private TextView total_amount_display;




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle intentExtras = getIntent().getExtras();

        calendar = Calendar.getInstance();
        cretedOn = findViewById(R.id.createdOn);

        reciept_type_display = findViewById(R.id.recieptTypeDisplay);
        start_date_display = findViewById(R.id.startDateDisplay);
        end_date_display = findViewById(R.id.endDateDisplay);
        runtime_display = findViewById(R.id.runTimeDisplay);
        principle_display = findViewById(R.id.principleDisplay);
        interest_rate_display = findViewById(R.id.interestRateDisplay);
        interest_display = findViewById(R.id.totalInterestDisplay);
        total_amount_display = findViewById(R.id.totalAmoutDisplay2);


        dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        date = dateFormat.format(calendar.getTime());
        cretedOn.setText(date);

        String byaj_type = intentExtras.getString("byaj_type");
        reciept_type_display.setText(byaj_type);

        CalculateDate clcdate = new CalculateDate();

        String sdate = intentExtras.getString("start_date");
        start_date_display.setText(sdate);

        String endate = intentExtras.getString("end_date");
        end_date_display.setText(endate);

        String runTime = intentExtras.getString("run_time");
        runtime_display.setText(runTime);

        String principle_S = intentExtras.getString("principle");
        principle_display.setText(principle_S);

        String interestTypeRate = intentExtras.getString("interest_rate_type");
        interest_rate_display.setText(interestTypeRate);

        String interestAmount_S = intentExtras.getString("interest_amount");
        interest_display.setText(interestAmount_S);

        String totalAmount_S = intentExtras.getString("total_amount");
        total_amount_display.setText(totalAmount_S);








        checkPermission();



        invoice = findViewById(R.id.invoiceView);
        ShareButton = findViewById(R.id.shareButton);

        ShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    share_invoice();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetWorldReadable")
    private void share_invoice() throws IOException {
        Bitmap bitmap = createBitmapImage(invoice);


        // Assume block needs to be inside a Try/Catch block.
        try {
            OutputStream fOut = null;
            String state = Environment.getExternalStorageState();



            String dsPath = Environment.getExternalStorageDirectory() + File.separator+"Pictures"+File.separator+"BYAJ" ; // the File to save
            File directory = new File(dsPath);


            if (! directory.exists()){
                directory.mkdir();
                // If you require it to make the entire directory path including parents /dir/dir2/,
                // use directory.mkdirs(); here instead.
            }

            dateFormat = new SimpleDateFormat("MM_dd");
            date = dateFormat.format(calendar.getTime());

            String ImageName = "BYAJ"+date+calendar.get(Calendar.MILLISECOND)+".png";
            File file = new File(directory+ File.separator + ImageName);

            file.createNewFile();
            fOut = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fOut.flush(); // Not really required
            fOut.close(); // do not forget to close the stream
            file.setReadable(true,false);
//            MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
            new SingleMediaScanner(this,file);

            try {
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setType("image/png");

                        try{
                            intent1.putExtra(Intent.EXTRA_STREAM,FileProvider.getUriForFile(this,
                            BuildConfig.APPLICATION_ID+".provider",file));

                    Toast.makeText(getApplicationContext(),"got path",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"not getting path"+ e.toString(),Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }

                startActivity(Intent.createChooser(intent1, "Share Image"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        }

//Checking storage permission
    private void checkPermission() {
        if(Build.VERSION.SDK_INT>=23){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }

            if(checkSelfPermission(Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{ Manifest.permission.READ_CALENDAR},1);
            }
        }
    }

//Overriding onRequest Function
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }






    @SuppressLint("ResourceAsColor")
    private  Bitmap createBitmapImage(View view){
        Bitmap returnBitmapImage = Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas newCanvas = new Canvas(returnBitmapImage);
        newCanvas.drawColor(R.color.white);
        view.draw(newCanvas);



        return returnBitmapImage;
    }

}