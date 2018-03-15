package brawijaya.electricity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class TestFragment extends Fragment {

    public TestFragment() {
        // Required empty public constructor
    }
    TextView biayaJam,biayaHari,biayaBulan;
    Double aDaya=0.0, pajakKota=0.0, tdl=0.0, ppn=0.0;
    String jamnya,nKota,nDaya;
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_test, container, false);

        Bundle bundle = getArguments();
        jamnya = bundle.getString("jam");
        nKota = bundle.getString("nKota");
        nDaya = bundle.getString("nDaya");
        aDaya = bundle.getDouble("aDaya");

        biayaJam = (TextView) rootview.findViewById(R.id.biayaJam);
        biayaHari = (TextView) rootview.findViewById(R.id.biayaHari);
        biayaBulan = (TextView) rootview.findViewById(R.id.biayaBulan);

            switch(nKota){
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
            switch (nDaya){
                case "R-1 / 450 VA":
                    tdl=415.0;
                    ppn=0.0;
                    break;
                case "R-1 / 900 VA":
                    tdl=586.0;
                    ppn=0.0;
                    break;
                case "R-1 / 900 VA RTM":
                    tdl=1352.0;
                    ppn=0.0;
                    break;
                case "R-2 / 3500 VA, 4400 VA, 5500 VA":
                    tdl=1467.28;
                    ppn=0.1;
                    break;
                case "R-3 / 6600 VA ke Atas":
                    tdl=1467.28;
                    ppn=0.1;
                    break;
                default:
                    tdl=1467.28;
                    ppn=0.0;
                    break;
            }
                int aJam = Integer.parseInt(jamnya);

                    Double hargaJam = aDaya*tdl;
                    Double hargaHari = aDaya*aJam*tdl;

                    Double hargaBulan = aDaya*aJam*tdl*30;
                    Double hargaPajak = hargaBulan*(ppn+pajakKota);
                    Double hargaBulanFix = hargaBulan + hargaPajak;

                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

                    int as = 2;
                    double temp = Math.pow(10,as);
                    double bJam = (double) Math.round((hargaJam*temp)/temp);
                    double bHari = (double) Math.round((hargaHari*temp)/temp);
                    double bBulan = (double) Math.round((hargaBulanFix*temp)/temp);

                    biayaJam.setText(formatRupiah.format(bJam)+" ,-");
                    biayaHari.setText(formatRupiah.format(bHari)+" ,-");
                    biayaBulan.setText(formatRupiah.format(bBulan)+" ,-");
        return rootview;
                }

            }
