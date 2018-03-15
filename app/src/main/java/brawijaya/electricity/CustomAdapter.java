package brawijaya.electricity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView logo;
    public TextView namaos;

    public CustomViewHolder(View itemView) {
        super(itemView);
        logo = (ImageView) itemView.findViewById(R.id.logo);
        namaos = (TextView) itemView.findViewById(R.id.namaos);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>{
    ArrayList<List> listData = new ArrayList<List>();
    Context c;
    String daya, kota;

    public CustomAdapter(ArrayList<List> listAlat) {
        this.listData = listAlat;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.rowview, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        holder.namaos.setText(listData.get(position).nama);
        holder.logo.setImageResource(listData.get(position).logo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), listData.get(position).nama, Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(view.getContext(), Main2Activity.class);
                myIntent.putExtra("alat", listData.get(position).nama);
                view.getContext().startActivity(myIntent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }

}