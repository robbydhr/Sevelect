package brawijaya.electricity;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.text.NumberFormat;
import java.util.Locale;

public class Main3Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    TextView biayaJam, biayaHari, biayaBulan;
    EditText jam, dayaInput;
    String nKota="null", nDaya="null";
    Double pajakKota = 0.0, tdl = 0.0, ppn = 0.0;
    Spinner spKota, spDaya;
    String bHarinotif;

    LinearLayout hasil;

    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID=0;
    private static final String NOTIFICATION_GUIDE_URL =
            "https://developer.android.com/design/patterns/notifications.html";
    private Button mNotifyButton;
    private static final String ACTION_CANCEL_NOTIFICATION =
            "com.example.android.notifyme.ACTION_CANCEL_NOTIFICATION";

    private NotificationReceiver mReceiver = new NotificationReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyButton = (Button) findViewById(R.id.hitung);
        mNotifyButton.setEnabled(true);

        //inisialisasi dan registrer penerima notifikasi
        IntentFilter intenFilter = new IntentFilter();
        intenFilter.addAction(ACTION_CANCEL_NOTIFICATION);
        registerReceiver(mReceiver,intenFilter);

        jam = (EditText) findViewById(R.id.jam);
        dayaInput = (EditText) findViewById(R.id.dayaInput);

        biayaJam = (TextView) findViewById(R.id.biayaJam);
        biayaHari = (TextView) findViewById(R.id.biayaHari);
        biayaBulan = (TextView) findViewById(R.id.biayaBulan);

        hasil = (LinearLayout) findViewById(R.id.hasil);

        spDaya = (Spinner) findViewById(R.id.spDaya);
        spKota = (Spinner) findViewById(R.id.spKota);

        ArrayAdapter<CharSequence> adapterKota = ArrayAdapter.createFromResource(this, R.array.kota, android.R.layout.simple_spinner_item);
        adapterKota.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKota.setAdapter(adapterKota);
        spKota.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterDaya = ArrayAdapter.createFromResource(this, R.array.daya, android.R.layout.simple_spinner_item);
        adapterDaya.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDaya.setAdapter(adapterDaya);
        spDaya.setOnItemSelectedListener(this);

        //operasi yg terjadi saat button Hitung di click
        mNotifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nKota.equals("None") || nDaya.equals("None")){
                    Toast.makeText(getApplicationContext(), "Masukkan kota & daya rumah", Toast.LENGTH_SHORT).show();
                }
                else{
                    switch (nKota) {
                        case "DKI Jakarta":
                            pajakKota = 0.03;
                            break;
                        case "Surabaya":
                            pajakKota = 0.08;
                            break;
                        case "Denpasar":
                            pajakKota = 0.05;
                            break;
                        case "Bandung":
                            pajakKota = 0.06;
                            break;
                        case "Medan":
                            pajakKota = 0.075;
                            break;
                    }
                    switch (nDaya) {
                        case "R-1 / 450 VA":
                            tdl = 415.0;
                            ppn = 0.0;
                            break;
                        case "R-1 / 900 VA":
                            tdl = 586.0;
                            ppn = 0.0;
                            break;
                        case "R-1 / 900 VA RTM":
                            tdl = 1352.0;
                            ppn = 0.0;
                            break;
                        case "R-2 / 3500 VA, 4400 VA, 5500 VA":
                            tdl = 1467.28;
                            ppn = 0.1;
                            break;
                        case "R-3 / 6600 VA ke Atas":
                            tdl = 1467.28;
                            ppn = 0.1;
                            break;
                        default:
                            tdl = 1467.28;
                            ppn = 0.0;
                            break;
                    }
                }
                if (jam.getText().toString().equals("") && dayaInput.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Inputkan Daya dan Jam", Toast.LENGTH_SHORT).show();
                }
                if (jam.getText().toString().equals("") || dayaInput.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Inputkan Daya atau Jam", Toast.LENGTH_SHORT).show();
                } else {
                    int aJam = Integer.parseInt(jam.getText().toString());
                    int aDayaInput = Integer.parseInt(dayaInput.getText().toString());
                    if (aJam == 0 || aDayaInput == 0) {
                        Toast.makeText(getApplicationContext(), "Daya atau Jam harus diatas 0", Toast.LENGTH_SHORT).show();
                    }
                    if (aJam == 0 && aDayaInput == 0) {
                        Toast.makeText(getApplicationContext(), "Daya dan Jam harus diatas 0", Toast.LENGTH_SHORT).show();
                    } else if (aJam > 24) {
                        Toast.makeText(getApplicationContext(), "Durasi Pemakaian tidak boleh melebihi 24 jam", Toast.LENGTH_SHORT).show();
                    } else {
                        double abDayaInput = (double) aDayaInput/1000;
                        Double hargaJam = abDayaInput * tdl;
                        Double hargaHari = abDayaInput * aJam * tdl;

                        Double hargaBulan = abDayaInput * aJam * tdl * 30;
                        Double hargaPajak = hargaBulan * (ppn + pajakKota);
                        Double hargaBulanFix = hargaBulan + hargaPajak;

                        Locale localeID = new Locale("in", "ID");
                        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

                        int as = 2;
                        double temp = Math.pow(10, as);
                        double bJam = (double) Math.round((hargaJam * temp) / temp);
                         double bHari = (double) Math.round((hargaHari * temp) / temp);
                        double bBulan = (double) Math.round((hargaBulanFix * temp) / temp);

                        biayaJam.setText(formatRupiah.format(bJam) + " ,-");
                        biayaHari.setText(formatRupiah.format(bHari) + " ,-");
                        biayaBulan.setText(formatRupiah.format(bBulan) + " ,-");

                        bHarinotif = formatRupiah.format(bHari).toString();
                        hasil.setVisibility(view.VISIBLE);
                        sendNotification();


                    }
                }
            }
        });
    }

    //saat notif di destroy
    protected void onDestroy(){
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
    //methof utk mengirim notifikasi

    private void cancelNotification() {
        //Cancel the notification
        mNotifyManager.cancel(NOTIFICATION_ID);

        //Resets the buttons
        mNotifyButton.setEnabled(true);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spKota:
                nKota = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spDaya:
                nDaya = parent.getItemAtPosition(position).toString();
                break;
        }
    }


    private class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case ACTION_CANCEL_NOTIFICATION:
                    cancelNotification();
                    break;
            }
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void sendNotification(){
        Intent notificationIntent = new Intent(this, Main3Activity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity
                (this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent cancelIntent = new Intent(ACTION_CANCEL_NOTIFICATION);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, cancelIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText("Biaya Perhari "+bHarinotif+" ,-")
                //.setContentText(getString(R.string.notification_text))
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setDeleteIntent(cancelPendingIntent);

        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
        mNotifyButton.setEnabled(false);
    }
    public void hide(View view) {
        hasil.setVisibility(view.INVISIBLE);
    }
}