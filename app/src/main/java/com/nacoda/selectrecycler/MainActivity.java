package com.nacoda.selectrecycler;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        recycler.setAdapter(new SelectionAdapter(data,this, recycler));

    }

    @OnClick(R.id.rate)
    public void onClick() {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.selection), MODE_PRIVATE);
        int selectedPosition = prefs.getInt(getString(R.string.selection),0);
        Toast.makeText(this, "" + data[selectedPosition], Toast.LENGTH_SHORT).show();
    }
}
