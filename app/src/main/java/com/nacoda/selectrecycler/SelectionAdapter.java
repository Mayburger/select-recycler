package com.nacoda.selectrecycler;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mayburger on 1/11/18.
 */

public class SelectionAdapter extends RecyclerView.Adapter<SelectionAdapter.ViewHolder> {

    private String[] data;
    private Context context;
    private RecyclerView recycler;

    public SelectionAdapter(String[] data, Context context, RecyclerView recycler) {
        this.data = data;
        this.context = context;
        this.recycler = recycler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selection_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {
            SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.selection), MODE_PRIVATE);
            int selectedPosition = prefs.getInt(context.getString(R.string.selection),0);
            if (position == selectedPosition){
                holder.check.setVisibility(View.VISIBLE);
                holder.itemParent.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.selection), MODE_PRIVATE).edit();
        editor.apply();

        holder.text.setText(data[position]);
        holder.itemParent.setOnClickListener(view -> {
            int color = Color.TRANSPARENT;
            Drawable background = holder.itemParent.getBackground();
            if (background instanceof ColorDrawable)
                color = ((ColorDrawable) background).getColor();

            if (color == context.getResources().getColor(R.color.colorPrimary)){
                editor.putInt(context.getString(R.string.selection),position);
                editor.apply();
                recycler.setAdapter(this);
            } else {
                holder.check.setVisibility(View.INVISIBLE);
                holder.itemParent.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                recycler.setAdapter(this);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_parent)
        LinearLayout itemParent;
        @BindView(R.id.parent)
        RelativeLayout parent;
        @BindView(R.id.check)
        ImageView check;
        @BindView(R.id.text)
        TextView text;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}

