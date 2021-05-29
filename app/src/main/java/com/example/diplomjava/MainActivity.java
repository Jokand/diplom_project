package com.example.diplomjava;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static int ritual_counter = 15;
    LinearLayout textsLayout, buttonsLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_field);
        textsLayout = findViewById(R.id.linearLayoutText);
        buttonsLayout = findViewById(R.id.linearLayoutButton);
        location living_spaces = new location("Жилые помещения", "a"), kitchen = new location("Кухня", "б"),
                hospital = new location("Больница", "в"), engine_room = new location("Машинное отделение", "г"),
                warehouse = new location("Склад", "д");
        ArrayList<location> locations = new ArrayList<>();
        locations.add(living_spaces);
        locations.add(kitchen);
        locations.add(hospital);
        locations.add(engine_room);
        locations.add(warehouse);
        avatar hero = new avatar(20, 30, 10, 10, 4, null, null, null, null);
        ButtonsHelper helper = new ButtonsHelper(MainActivity.this);
        TextView someTextHelper = new TextView(MainActivity.this);
        String logo = "Приветствие игрока, ввод в курс дела. После приветствия перед игроком описывается база и концепция вылазок глазами главного героя.";
        someTextHelper.setText(logo);
        someTextHelper.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textsLayout.addView(someTextHelper);
        buttonsLayout.addView(helper.getStartButton(locations, hero));
    }
}