package com.letsdevelopit.ac_vchat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AdapterCentralList extends RecyclerView.Adapter<AdapterCentralList.Viewholder> {

    Context mcontext;
    Activity activity;

    private List<ModelcentralClass> exampleListFull;
    private List<ModelcentralClass> modelcentralClassList;




    public AdapterCentralList(Context context, List<ModelcentralClass> modelcentralClassList) {
        this.mcontext = context;
        activity = (Activity) context;
        this.modelcentralClassList = modelcentralClassList;
        exampleListFull = new ArrayList<>(modelcentralClassList);

    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rowdepart, viewGroup, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder viewholder, final int i) {
        final String name = modelcentralClassList.get(i).getName();
        final String imgUrl = modelcentralClassList.get(i).getImgUrl();
        final String url = modelcentralClassList.get(i).getURL();


        viewholder.set_data(name,imgUrl,url);


        viewholder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, PdfOpener.class);
                intent.putExtra("url",url);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelcentralClassList.size();
    }

    public Filter getFilter() {
        return exampleFilter;
    }



    class Viewholder extends RecyclerView.ViewHolder {

        private ImageView img;
        CardView card;
        private TextView name;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.image);
            card = itemView.findViewById(R.id.card);
        }

        private void set_data(String nm, String iurl, String url) {

            name.setText(nm);
            Glide.with(itemView)
                    .load("https://cdn4.iconfinder.com/data/icons/file-extension-names-vol-8/512/24-512.png")
                    .into(img);
        }


    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelcentralClass> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(modelcentralClassList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ModelcentralClass item : exampleListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            modelcentralClassList.clear();
            modelcentralClassList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}
