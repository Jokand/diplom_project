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

        item heal_body = new item("Аптечка", 5, 1);
        item heal_mind = new item("Книга", 5, 2);
        item damage_enemy= new item("Обрез", 10, 3);

        weapon arms = new weapon("кулаки", 1, "Тупа кулаки");
        weapon knife = new weapon("Нож", 3, "Ножик делает вжик вжик");
        clothes cap = new clothes("Кепка", "Морская кепка", 0, 1,3);
        clothes T_shirt = new clothes("Футболка", "Простая тряпичная фу", 0, 1,2);
        clothes shorts = new clothes("Шортики", "Короткие сексуальные шортики", 1, 0,1);
        clothes helmet = new clothes("щлем", "железный шлем", 1, 2,3);
        clothes bulletproof_vest = new clothes("Броник", "Непробиваемая штука", 4, 1,2);
        clothes trousers = new clothes("Штаны", "Штаны из берёзовой коры", 2, 1,1);
        enemy Enemy_1 = new enemy("Культист", 15, 12, 4, 6, 100, 60, 10, 5);
        enemy Enemy_2 = new enemy("Фанатик", 10, 13, 5, 4, 100, 50, 10, 5);
        enemy Enemy_3 = new enemy("Зомби", 20, 10, 3, 4, 100, 70, 2, 1);
        ArrayList<enemy> enemis = new ArrayList<>();
        enemis.add(Enemy_1); enemis.add(Enemy_2); enemis.add(Enemy_3);

        ArrayList<Object> loots = new ArrayList<>();
        loots.add(heal_body); loots.add(heal_mind); loots.add(damage_enemy); loots.add(knife); loots.add(helmet); loots.add(bulletproof_vest);  loots.add(trousers);

        ArrayList<String> false_answer = new ArrayList<>();
        false_answer.add("Десять");
        false_answer.add("Пять");
        false_answer.add("Восемь");
        false_answer.add("Двенадцать");
        false_answer.add("Сороктри");
        location.event one = new location.event("Два", false_answer, "1 + 1");
        location.event two = new location.event("Четыре", false_answer, "2 + 2");
        location.event three = new location.event("3", false_answer, "2 + 1");
        location.event four = new location.event("Пятнадцать", false_answer, "3 * 5");
        location.event five = new location.event("Сорокчетыре", false_answer, "4 + 4 на js");
        location.event six = new location.event("Двадцать", false_answer, "5 * 4");
        location.event seven = new location.event("Восемнадцать", false_answer, "3 * 3 * 2");

        ArrayList<location.event> events = new ArrayList<location.event>();
        events.add(one); events.add(two); events.add(three); events.add(four); events.add(five); events.add(six);  events.add(seven);

        location
                living_spaces = new location("Жилые помещения", "a", loots, events, enemis),
                kitchen = new location("Кухня", "б", loots, events, enemis),
                hospital = new location("Больница", "в", loots, events, enemis),
                engine_room = new location("Машинное отделение", "г", loots, events, enemis),
                warehouse = new location("Склад", "д", loots, events, enemis);

        ArrayList<location> locations = new ArrayList<>();
        locations.add(living_spaces);
        locations.add(kitchen);
        locations.add(hospital);
        locations.add(engine_room);
        locations.add(warehouse);
        avatar hero = new avatar(20, 30, 10, 10, 4, arms, cap, T_shirt, shorts);

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
//            buttonsLayout.addView(getWaitingButton("Сидеть и ждать", hero));
            Button WaitingButton = new Button(getApplicationContext());
            WaitingButton.setText("Сидеть и ждать");
            WaitingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ritual_counter--;
                    hero.avatar_healing_xp(5);
                    hero.avatar_healing_mind(2);
                }
            });
            buttonsLayout.addView(WaitingButton);
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
                                textsLayout.addView(someTextHelper);
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