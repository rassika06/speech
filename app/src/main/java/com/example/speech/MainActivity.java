package com.example.speech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText etText;
    Button btnSpeak;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etText = findViewById(R.id.etText);
        btnSpeak = findViewById(R.id.btnSpeak);

        textToSpeech = new TextToSpeech(this, status -> {

            if (status == TextToSpeech.SUCCESS) {

                int result = textToSpeech.setLanguage(Locale.US);

                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {

                    Toast.makeText(MainActivity.this,
                            "Language not supported",
                            Toast.LENGTH_SHORT).show();
                }

            } else {

                Toast.makeText(MainActivity.this,
                        "TextToSpeech Initialization Failed",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnSpeak.setOnClickListener(v -> {

            String text = etText.getText().toString().trim();

            if (text.isEmpty()) {
                Toast.makeText(MainActivity.this,
                        "Please enter text",
                        Toast.LENGTH_SHORT).show();
            } else {
                textToSpeech.speak(text,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        null);
            }

        });
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}