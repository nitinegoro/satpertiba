package id.ac.stiepertiba.pertiba.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.ac.stiepertiba.pertiba.AccountActivity;
import id.ac.stiepertiba.pertiba.HasilActivity;
import id.ac.stiepertiba.pertiba.NilaiActivity;
import id.ac.stiepertiba.pertiba.R;
import id.ac.stiepertiba.pertiba.models.IndexIcon;

/**
 * Created by nitinegoro on 9/20/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<IndexIcon> icons;
    private Context context;

    public DataAdapter(Context context, ArrayList<IndexIcon> icons) {
        this.context = context;
        this.icons = icons;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataAdapter.ViewHolder holder, final int position) {
        holder.nama_icon.setText(icons.get(position).getNama_icon());
        Picasso.with(context).load(icons.get(position).getUrl_icon()).resize(80, 80).into(holder.img_icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent hasil = new Intent(v.getContext(), HasilActivity.class),
                        nilai = new Intent(v.getContext(), NilaiActivity.class),
                        account = new Intent(v.getContext(), AccountActivity.class);
                /* JUDUL HALAMAN */
                hasil.putExtra( "NamaIcon", icons.get(position).getNama_icon() );
                nilai.putExtra( "NamaIcon", icons.get(position).getNama_icon() );
                account.putExtra( "NamaIcon", icons.get(position).getNama_icon() );
                /* CALLING ACTIVITY */
               switch ( position ) {
                   case 8:
                       v.getContext().startActivity(account);
                       break;
                   case 2:
                       v.getContext().startActivity(hasil);
                       break;
                   case 1:
                       v.getContext().startActivity(nilai);
                       break;
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return icons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_icon;
        public TextView nama_icon;

        public ViewHolder(View itemView) {
            super(itemView);

            nama_icon = (TextView)itemView.findViewById(R.id.nm_icon);
            img_icon = (ImageView)itemView.findViewById(R.id.img_icon);
        }
    }
}
