package com.task.satu.rabbitsfarmer.Data;

import android.content.Context;
import android.os.AsyncTask;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Satu on 2016-01-05.
 */
@Setter
@Getter
public class DataLoader {
    public static final String FILE_NAME_RABBITS = "rabbits";
    public static final int RABIT_DATA = 1;
    private ArrayList<Rabbit> rabbits;
    private String filenameToSave;
    private Context context;

    public DataLoader(Context context) {
        this.context = context;
    }

    public void saveFile(String file){
        filenameToSave = file;
        new SaveDataTask(context).execute(rabbits);
    }
    public void loadFile(Context context, int data, String owner){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(FILE_NAME_RABBITS+owner);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            switch (data){
                case RABIT_DATA:
                    rabbits = (ArrayList<Rabbit>) objectInputStream.readObject();
                break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class SaveDataTask extends AsyncTask<Object,Void,Void> {

        Context context;

        private SaveDataTask(Context context) {
            this.context = context;
        }


        @Override
        protected Void doInBackground(Object... params) {
            try {
                FileOutputStream outputStream = context.openFileOutput(filenameToSave, context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(params[0]);
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
