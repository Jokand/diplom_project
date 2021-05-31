package com.example.diplomjava;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ButtonsHelper extends AppCompatActivity {

    LinearLayout textsLayout, buttonsLayout;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.game_field);
//        textsLayout = findViewById(R.id.linearLayoutText);
//        buttonsLayout = findViewById(R.id.linearLayoutButton);
//    }
    Context context;
    Button btn;
    TextView someTextHelper;


    public ButtonsHelper(Context context){
        this.context = context;
        this.someTextHelper = new TextView(context);
    }

    public Button getStartButton(ArrayList<location> locations, avatar hero){
        btn = new Button(context);
        btn.setText("Начать игру");
        btn.setOnClickListener(v->{
            someTextHelper.setText("\n Вы находитесь на своей базе, выберите куда вы пойдёте сейчас:");
            buttonsLayout.addView(getWaitingButton("Сидеть и ждать", hero));
            for (int i = 0; i < locations.size(); i++) {
                Button b = new Button(context);
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
                                        buttonsLayout.addView(getInventoryButton( ((weapon) hero.available_equipment.get(i)).name, i, hero));
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
                                return;
                            } else MainActivity.ritual_counter--;
                        }

                    }
                });
//            ((LinearLayout) findViewById(R.id.layout)).addView(b);
            }
        });
        return btn;
    }

    public Button getButton(String text){
        btn = new Button(context);
        btn.setText(text);
        return btn;
    }

    public Button getWaitingButton(String text, avatar hero){
        btn.setText(text);
        btn.setOnClickListener(v->{
            MainActivity.ritual_counter--;
            hero.avatar_healing_xp(5);
            hero.avatar_healing_mind(2);
        });
        return btn;
    }

    @SuppressLint("SetTextI18n")
    public Button getInventoryButton(String text, int i, avatar hero){
        btn = new Button(context);
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
