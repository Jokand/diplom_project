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
    TextView someTextHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_field);
        someTextHelper = new TextView(getApplicationContext());
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
        //ButtonsHelper helper = new ButtonsHelper(getApplicationContext());
        TextView someTextHelper = new TextView(getApplicationContext());
        String logo = "Приветствие игрока, ввод в курс дела. После приветствия перед игроком описывается база и концепция вылазок глазами главного героя.";
        someTextHelper.setText(logo);
        textsLayout.addView(someTextHelper);
        buttonsLayout.addView(getStartButton(locations, hero));
    }

    public Button getStartButton(ArrayList<location> locations, avatar hero){
        Button btn = new Button(getApplicationContext());
        btn.setText("Начать игру");
        btn.setOnClickListener(v->{
            someTextHelper.setText("Вы находитесь на своей базе, выберите куда вы пойдёте сейчас:");
            textsLayout.addView(someTextHelper);
//            System.out.println(textsLayout);
//            System.out.println(someTextHelper);
            //textsLayout.addView(someTextHelper);
            //buttonsLayout.addView(getWaitingButton("Сидеть и ждать", hero));
            for (int i = 0; i < locations.size(); i++) {
                Button b = new Button(getApplicationContext());
                b.setText(locations.get(i).name);
                int finalI = i - 1;
                if(i + 1 == locations.size()){
                    btn.setText("Выбор брони и оружия перед вылазкой");
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (locations.size() == finalI) {
                                hero.const_armor = 10 + hero.head_clothes.armor_class + hero.body_clothes.armor_class + hero.legs_clothes.armor_class;
                                hero.const_armor_mind = 10 + hero.head_clothes.mind_armor_class + hero.body_clothes.mind_armor_class + hero.legs_clothes.mind_armor_class;
                                hero.avatar_review();

                                buttonsLayout.addView(getButton("Закончить выбор снаряжения"));
                                for (int i = 0; i < hero.available_equipment.size(); i++) {
                                    if (hero.available_equipment.get(i) instanceof weapon) {
                                        buttonsLayout.addView(getInventoryButton(((weapon) hero.available_equipment.get(i)).name, i, hero));
                                    } else {
                                        buttonsLayout.addView(getInventoryButton(((clothes) hero.available_equipment.get(i)).name, i, hero));
                                    }
                                }
                            }
                        }
                    });
                }
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (finalI < locations.size()) {
                            if (locations.get(finalI).exploration(hero)) {
                                btn.setText("Вы проиграли");
                            } else MainActivity.ritual_counter--;
                        }
                    }
                });
                //((LinearLayout) findViewById(R.id.layout)).addView(b);
                buttonsLayout.addView(b);
            }
        });
        return btn;
    }

    public Button getButton(String text){
        Button btn = new Button(getApplicationContext());
        btn.setText(text);
        return btn;
    }
    @SuppressLint("SetTextI18n")
    public Button getInventoryButton(String text, int i, avatar hero){
        Button btn = new Button(getApplicationContext());
        btn.setText(text);
        btn.setOnClickListener(v->{
            boolean category_clothes =  hero.available_equipment.get(i) instanceof clothes;
            if (category_clothes) {
                try {
                    clothes choose = null;
                    int vote = ((clothes) hero.available_equipment.get(i)).place_of_protection;
                    if (vote == 1) {
                        clothes leg = hero.legs_clothes;
                        hero.legs_clothes = (clothes) hero.available_equipment.get(i);
                        choose = (clothes) hero.available_equipment.get(i);
                        hero.available_equipment.remove(i);
                        hero.available_equipment.add(leg);
                    } else if (vote == 2) {
                        clothes body = hero.body_clothes;
                        hero.body_clothes = (clothes) hero.available_equipment.get(i);
                        choose = (clothes) hero.available_equipment.get(i);
                        hero.available_equipment.remove(i);
                        hero.available_equipment.add(body);
                    } else if (vote == 3) {
                        clothes head = hero.head_clothes;
                        hero.head_clothes = (clothes) hero.available_equipment.get(i);
                        choose = (clothes) hero.available_equipment.get(i);
                        hero.available_equipment.remove(i);
                        hero.available_equipment.add(head);
                    }
                    hero.const_armor = 10 + hero.head_clothes.armor_class + hero.body_clothes.armor_class + hero.legs_clothes.armor_class;
                    hero.const_armor_mind = 10 + hero.head_clothes.mind_armor_class + hero.body_clothes.mind_armor_class + hero.legs_clothes.mind_armor_class;
                    assert choose != null;
                    someTextHelper.setText("Вы надели на себя " + choose.name + ". Теперь ваша физическая защита равна "
                            + hero.const_armor + ", а ваша ментальная защита стала равна " + hero.const_armor_mind);
                    textsLayout.addView(someTextHelper);
                } catch (Exception ignored) {
                }
            } else {
                try {
                    weapon choose;
                    weapon Weapon = hero.weapon;
                    hero.weapon = (weapon) hero.available_equipment.get(i);
                    choose = (weapon) hero.available_equipment.get(i);
                    hero.available_equipment.remove(i);
                    hero.available_equipment.add(Weapon);
                    assert choose != null;
                    someTextHelper.setText("Вы надели на себя " + choose.name + ". Теперь ваша физическая защита равна "
                            + hero.const_armor + ", а ваша ментальная защита стала равна " + hero.const_armor_mind);
                    textsLayout.addView(someTextHelper);
                } catch (Exception ignored) {
                }
            }
        });
        return btn;
    }
}