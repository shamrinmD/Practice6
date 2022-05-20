package ru.mirea.shamrin.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText enterNameFile;
    private EditText enterText;
    private SharedPreferences preferences;
    final String FILE_NAME = "file_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterNameFile = findViewById(R.id.editTextName);
        enterText = findViewById(R.id.editText);
        preferences = getPreferences(MODE_PRIVATE);
        loadFile();
    }

    public void saveNameText() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FILE_NAME, enterNameFile.getText().toString());
        editor.apply();
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(enterNameFile.getText().toString(), MODE_PRIVATE);
            outputStream.write(enterText.getText().toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFile() {
        FileInputStream fileInputStream;
        if (preferences.getString(FILE_NAME, null) != null) {
            try {
                fileInputStream = openFileInput(preferences.getString(FILE_NAME, null));
                byte[] bytes = new byte[fileInputStream.available()];
                fileInputStream.read(bytes);
                String text = new String(bytes);
                enterNameFile.setText(preferences.getString(FILE_NAME, null));
                enterText.setText(text);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveNameText();
    }


}