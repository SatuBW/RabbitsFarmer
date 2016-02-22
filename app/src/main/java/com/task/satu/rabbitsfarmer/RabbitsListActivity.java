package com.task.satu.rabbitsfarmer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.task.satu.rabbitsfarmer.Data.Rabbit;
import com.task.satu.rabbitsfarmer.Data.RabbitsAdapter;

import java.util.ArrayList;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RabbitsListActivity extends AppCompatActivity {

    @Bind(R.id.listView_rabbits_list)
    ListView listView;

    @Bind(R.id.spinner_sort_method)
    Spinner spinner_sort;

    RabbitsAdapter rabbitsAdapter;
    ArrayList<Rabbit> rabbits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rabbits_list);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        rabbits = (ArrayList<Rabbit>) extras.getSerializable(RabbitManagerActivity.LIST_EXTRA);

        rabbitsAdapter = new RabbitsAdapter(getApplicationContext(),R.layout.item_rabbit);

        if (rabbits.size() != 0)
        for (Rabbit rabbit: rabbits)
            rabbitsAdapter.add(rabbit);

        listView.setAdapter(rabbitsAdapter);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_methods_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sort.setAdapter(adapter);

        spinner_sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        rabbitsAdapter.sort(new Comparator<Rabbit>() {
                            @Override
                            public int compare(Rabbit lhs, Rabbit rhs) {
                                return lhs.getName().compareTo(rhs.getName());
                            }
                        });
                        break;
                    case 2:
                        rabbitsAdapter.sort(new Comparator<Rabbit>() {
                            @Override
                            public int compare(Rabbit lhs, Rabbit rhs) {
                                return lhs.getBirthday().compareTo(rhs.getBirthday());
                            }
                        });
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
