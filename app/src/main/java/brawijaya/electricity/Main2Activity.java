package brawijaya.electricity;

import android.app.Activity;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView biayaJam, biayaHari, biayaBulan;
    EditText jam;
    String alat, nKota, nDaya;
    Double aDaya = 0.0, pajakKota = 0.0, tdl = 0.0, ppn = 0.0;

    Spinner spDayaAlat, spKota, spDaya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main2);

        LinearLayout bar = (LinearLayout) findViewById(R.id.bar);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        TextView lbBar = (TextView) findViewById(R.id.lbBar);
        TextView ket = (TextView) findViewById(R.id.keterangan);
        jam = (EditText) findViewById(R.id.jam);

        biayaJam = (TextView) findViewById(R.id.biayaJam);
        biayaHari = (TextView) findViewById(R.id.biayaHari);
        biayaBulan = (TextView) findViewById(R.id.biayaBulan);

        spDayaAlat = (Spinner) findViewById(R.id.spDayaAlat);
        spDaya = (Spinner) findViewById(R.id.spDaya);
        spKota = (Spinner) findViewById(R.id.spKota);

        Bundle bundle = getIntent().getExtras();
        alat = bundle.getString("alat");

        lbBar.setText(alat);
        ket.setText("Daya " + alat);

        ArrayAdapter<CharSequence> adapterKota = ArrayAdapter.createFromResource(this, R.array.kota, android.R.layout.simple_spinner_item);
        adapterKota.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKota.setAdapter(adapterKota);
        spKota.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterDaya = ArrayAdapter.createFromResource(this, R.array.daya, android.R.layout.simple_spinner_item);
        adapterDaya.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDaya.setAdapter(adapterDaya);
        spDaya.setOnItemSelectedListener(this);

        switch (alat) {
            case "Televisi":
                ArrayAdapter<CharSequence> adapterTv = ArrayAdapter.createFromResource(this, R.array.televisi, android.R.layout.simple_spinner_item);
                adapterTv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDayaAlat.setAdapter(adapterTv);
                //dayaAlat.setOnItemSelectedListener(this);

                logo.setBackgroundResource(R.drawable.itv);
                bar.setBackgroundColor(Color.parseColor("#cf000f"));
                break;
            case "AC":
                ArrayAdapter<CharSequence> adapterAC = ArrayAdapter.createFromResource(this, R.array.ac, android.R.layout.simple_spinner_item);
                adapterAC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDayaAlat.setAdapter(adapterAC);
                //dayaAlat.setOnItemSelectedListener(this);

                logo.setBackgroundResource(R.drawable.iac);
                bar.setBackgroundColor(Color.parseColor("#1e8bc3"));
                break;
            case "Kipas Angin":
                ArrayAdapter<CharSequence> adapterKipas = ArrayAdapter.createFromResource(this, R.array.kipas, android.R.layout.simple_spinner_item);
                adapterKipas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDayaAlat.setAdapter(adapterKipas);
                //dayaAlat.setOnItemSelectedListener(this);

                logo.setBackgroundResource(R.drawable.ikipas);
                bar.setBackgroundColor(Color.parseColor("#16a085"));
                break;
            case "Mesin Cuci":
                ArrayAdapter<CharSequence> adapterMcuci = ArrayAdapter.createFromResource(this, R.array.mcuci, android.R.layout.simple_spinner_item);
                adapterMcuci.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDayaAlat.setAdapter(adapterMcuci);
                //dayaAlat.setOnItemSelectedListener(this);

                logo.setBackgroundResource(R.drawable.imcuci);
                bar.setBackgroundColor(Color.parseColor("#8e44ad"));
                break;
            case "Kulkas":
                ArrayAdapter<CharSequence> adapterKulkas = ArrayAdapter.createFromResource(this, R.array.kulkas, android.R.layout.simple_spinner_item);
                adapterKulkas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDayaAlat.setAdapter(adapterKulkas);
                //dayaAlat.setOnItemSelectedListener(this);

                logo.setBackgroundResource(R.drawable.ikulkas);
                bar.setBackgroundColor(Color.parseColor("#f39c12"));
                break;
            case "Rice Cooker":
                ArrayAdapter<CharSequence> adapterRcooker = ArrayAdapter.createFromResource(this, R.array.rcooker, android.R.layout.simple_spinner_item);
                adapterRcooker.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDayaAlat.setAdapter(adapterRcooker);
                //dayaAlat.setOnItemSelectedListener(this);

                logo.setBackgroundResource(R.drawable.ircooker);
                bar.setBackgroundColor(Color.parseColor("#f7ca18"));
                break;
        }
        spDayaAlat.setOnItemSelectedListener(this);

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
            case R.id.spDayaAlat:
                String sSelected = parent.getItemAtPosition(position).toString();
                aDaya = Double.valueOf(sSelected).doubleValue() / 1000;
        }

        //Toast.makeText(this, sSelected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void hitung(View view) {
        if (nKota.equals("None") || nDaya.equals("None")) {
            Toast.makeText(this, "Masukkan kota & daya rumah", Toast.LENGTH_SHORT).show();
        } else {
            if (jam.getText().toString().equals("")) {
                Toast.makeText(this, "Inputkan Durasi Pemakaian", Toast.LENGTH_SHORT).show();
            } else {
                int seleksiJam = Integer.parseInt(jam.getText().toString());
                if (seleksiJam<=0 || seleksiJam>24) {
                    Toast.makeText(this, "Durasi jam tidak boleh kurang dari 0 dan lebih dari 24 jam", Toast.LENGTH_SHORT).show();
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("jam", jam.getText().toString());
                    bundle.putString("nKota", nKota);
                    bundle.putString("nDaya", nDaya);
                    bundle.putDouble("aDaya", aDaya);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    TestFragment testFragment = new TestFragment();
                    testFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_layout, testFragment);
                    fragmentTransaction.commit();
                }
            }
        }
    }
}