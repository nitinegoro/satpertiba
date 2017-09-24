package id.ac.stiepertiba.pertiba.models;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import id.ac.stiepertiba.pertiba.NilaiActivity;
import id.ac.stiepertiba.pertiba.R;

/**
 * Created by nitinegoro on 9/24/2017.
 */

public class NilaiAdapter extends RecyclerView.Adapter<NilaiAdapter.ViewHolder>{
    Context context;
    ArrayList<HashMap<String, String>> list_data;

    public NilaiAdapter(NilaiActivity nilaiActivity, ArrayList<HashMap<String, String>> list_data) {
        this.context = nilaiActivity;
        this.list_data = list_data;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tkdmakul.setText(list_data.get(position).get("course_code"));
        holder.tmakul.setText(list_data.get(position).get("course_name"));
        holder.tsks.setText(list_data.get(position).get("sks") + " SKS");
        holder.tnilai.setText(list_data.get(position).get("point"));
        holder.tgrade.setText(list_data.get(position).get("grade"));

        switch ( list_data.get(position).get("grade") )
        {
            case "A" :
                holder.tgrade.setTextColor(ContextCompat.getColor(context, R.color.success));
                break;
            case "B" :
                holder.tgrade.setTextColor(ContextCompat.getColor(context, R.color.info));
                break;
            case "C" :
                holder.tgrade.setTextColor(ContextCompat.getColor(context, R.color.warning));
                break;
            default:
                holder.tgrade.setTextColor(ContextCompat.getColor(context, R.color.error));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_nilai, null);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tmakul;
        TextView tkdmakul;
        TextView tsks;
        TextView tnilai;
        TextView tgrade;

        public ViewHolder(View itemView) {
            super(itemView);

            tmakul = (TextView) itemView.findViewById(R.id.tmakul);
            tkdmakul = (TextView) itemView.findViewById(R.id.tkdmakul);
            tsks = (TextView) itemView.findViewById(R.id.tsks);
            tnilai = (TextView) itemView.findViewById(R.id.tnilai);
            tgrade = (TextView) itemView.findViewById(R.id.tgrade);
        }
    }
}
