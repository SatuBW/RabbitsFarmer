package com.task.satu.rabbitsfarmer;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.task.satu.rabbitsfarmer.Data.DataLoader;
import com.task.satu.rabbitsfarmer.Data.Rabbit;
import com.task.satu.rabbitsfarmer.Interfaces.OnRabbitPut;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RabbitManagerActivity extends AppCompatActivity implements OnRabbitPut {

    public static final String LIST_EXTRA = "RABBITS_EXTRA";
    public static final String RESULT_EXTRA = "RESULT_EXTRA";
    private ArrayList<Rabbit> rabbits;
    public static final int REQUEST_CODE = 1;
    DataLoader dataLoader;
    private Menu actionMenu;
    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rabbit_manager);
        ButterKnife.bind(this);
        user_name = ParseUser.getCurrentUser().getObjectId();
        dataLoader = new DataLoader(getApplicationContext());
        dataLoader.loadFile(getApplicationContext(),DataLoader.RABIT_DATA,ParseUser.getCurrentUser().getObjectId());
        rabbits = dataLoader.getRabbits();
        if (rabbits == null)
            rabbits = new ArrayList<Rabbit>();
        if (isOnline()) {
            getRabbitsFromTheCloud();
            for (int i = 0 ; i < rabbits.size(); i++)
                if (rabbits.get(i).getId() == null)
                    sendRabbitToTheCloud(rabbits.get(i),i);
        }

    }
    @OnClick(R.id.button_add_rabbit)
    public void on_add_rabbit_buttonClick(){
        PutRabbitDialog putRabbitDialog = new PutRabbitDialog();
        FragmentManager fm = getFragmentManager();
        putRabbitDialog.show(fm, "test");
    }
    @OnClick(R.id.button_rabbits_list)
    public void on_rabbitList_buttonClick(){
        Intent intent = new Intent(getApplicationContext(),RabbitsListActivity.class);
        intent.putExtra(LIST_EXTRA,rabbits);
        startActivity(intent);
    }

    @Override
    public void onMedicinePut(final Rabbit rabbit) {
        rabbits.add(rabbit);
        sendRabbitToTheCloud(rabbit, rabbits.size()-1);
    }

    @Override
    protected void onStop() {
        if (rabbits.size() != 0) {
            dataLoader.setRabbits(rabbits);
            dataLoader.saveFile(DataLoader.FILE_NAME_RABBITS+user_name);
        }
        super.onStop();
    }

    private void getRabbitsFromTheCloud(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Rebbit");
        //ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Rabbit");
        query.whereEqualTo("Id_user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for (ParseObject parseObject : list) {
                    Rabbit rabbit = new Rabbit(parseObject.getObjectId(),parseObject.getString("Name"),parseObject.getDate("Birthday"),parseObject.getString("Color"));
                    boolean exsistFlag = false;
                    for (int i = 0 ; i <rabbits.size(); i++) {
                        if (rabbits.get(i).getId().compareTo(rabbit.getId()) == 0 ) {
                            exsistFlag = true;
                            break;
                        }
                    }
                    if (!exsistFlag)
                        rabbits.add(rabbit);
                }
            }
        });
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void sendRabbitToTheCloud(final Rabbit rabbit, final int i){
        final ParseObject parse_rabbit = new ParseObject("Rebbit");
        parse_rabbit.put("Id_user", ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));
        parse_rabbit.put("Name", rabbit.getName());
        parse_rabbit.put("Color", rabbit.getColor());
        parse_rabbit.put("Birthday", rabbit.getBirthday());
        parse_rabbit.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                String id = parse_rabbit.getObjectId();
                rabbit.setId(id);
                rabbits.set(i,rabbit);
                Toast.makeText(getApplicationContext(),"poszlo",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        actionMenu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case R.id.action_logout:
                ParseUser.logOut();
                finish();
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
