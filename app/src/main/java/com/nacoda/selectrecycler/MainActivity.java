package com.nacoda.selectrecycler;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    String[] data = {"Excellent", "Good", "Okay", "Bad", "Terrible"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.selection), MODE_PRIVATE).edit();
        editor.apply();

        SelectionAdapter adapter;

        adapter = new SelectionAdapter(data,this,recycler);

        adapter.setOnClickListener((position, itemParent, check) -> {
            int color = Color.TRANSPARENT;
            Drawable background = itemParent.getBackground();
            if (background instanceof ColorDrawable)
                color = ((ColorDrawable) background).getColor();

            if (color == getResources().getColor(R.color.colorPrimary)) {
                editor.putInt(getString(R.string.selection), position);
                editor.apply();
                recycler.setAdapter(adapter);
            } else {
                check.setVisibility(View.INVISIBLE);
                itemParent.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                recycler.setAdapter(adapter);
            }
        });

        recycler.setAdapter(adapter);
    }

    @OnClick(R.id.rate)
    public void onClick() {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.selection), MODE_PRIVATE);
        int selectedPosition = prefs.getInt(getString(R.string.selection), 0);
        Toast.makeText(this, "" + data[selectedPosition], Toast.LENGTH_SHORT).show();
    }
}
