package com.nacoda.selectrecycler;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
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
    private OnItemClickListener listener;

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

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {
            SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.selection), MODE_PRIVATE);
            int selectedPosition = prefs.getInt(context.getString(R.string.selection), 0);
            if (position == selectedPosition) {
                holder.check.setVisibility(View.VISIBLE);
                holder.itemParent.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.text.setText(data[position]);

        holder.click(listener, holder.itemParent, holder.check, position);
    }

    public interface OnItemClickListener {
        void onClick(int position, View itemParent, View check);
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

        void click(final OnItemClickListener listener, View itemParent, View check, final int position) {
            itemParent.setOnClickListener(view1 -> {
                listener.onClick(position, itemParent, check);
            });
        }

    }
}

