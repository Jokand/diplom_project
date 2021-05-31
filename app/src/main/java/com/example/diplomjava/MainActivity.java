package com.example.diplomjava;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static int ritual_counter = 15;
    LinearLayout textsLayout, buttonsLayout;
    TextView someTextHelper, WaitingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_field);
        someTextHelper = new TextView(getApplicationContext());
        WaitingText = new TextView(getApplicationContext());
        textsLayout = findViewById(R.id.linearLayoutText);
        buttonsLayout = findViewById(R.id.linearLayoutButton);

        TextView WaitingText = new TextView(getApplicationContext());
        TextView someTextHelper = new TextView(getApplicationContext());

        item heal_body = new item("Аптечка", 5, 1);
        item heal_mind = new item("Книга", 5, 2);
        item damage_enemy = new item("Обрез", 10, 3);

        weapon arms = new weapon("Кулаки", 1, "Голые кулаки. Практически не наносят урона");
        weapon knife = new weapon("Нож", 3, "Большой острый нож с следами ржавчины. Этот нож отнял очень много жизней");
        clothes cap = new clothes("Кепка", "Морская кепка", 0, 1, 3);
        clothes T_shirt = new clothes("Футболка", "Простая тряпичная футболка", 0, 1, 2);
        clothes shorts = new clothes("Шортики", "Синие шорты по колено", 1, 0, 1);
        clothes helmet = new clothes("Немецкая каска", "Старая немецкая каска, покрытая оккультными символами.", 1, 2, 3);
        clothes bulletproof_vest = new clothes("Бронежелет", "Хороший новый бронежелет, который лежал где то на складах", 4, 1, 2);
        clothes trousers = new clothes("Армейские штаны", "Армейские штаны из крепкой ткани, которые защищают своего носителя от многого", 2, 1, 1);
        enemy Enemy_1 = new enemy("Культист", 15, 12, 4, 6, 100, 60, 10, 5);
        enemy Enemy_2 = new enemy("Фанатик", 10, 13, 5, 4, 100, 50, 10, 5);
        enemy Enemy_3 = new enemy("Зомби", 20, 10, 3, 4, 100, 70, 2, 1);
        ArrayList<enemy> enemies = new ArrayList<>();
        enemies.add(Enemy_1);
        enemies.add(Enemy_2);
        enemies.add(Enemy_3);

        ArrayList<Object> loots = new ArrayList<>();
        loots.add(heal_body);
        loots.add(heal_mind);
        loots.add(damage_enemy);
        loots.add(knife);
        loots.add(helmet);
        loots.add(bulletproof_vest);
        loots.add(trousers);

        ArrayList<String> false_answer = new ArrayList<>();
        false_answer.add("Десять");
        false_answer.add("Пять");
        false_answer.add("Восемь");
        false_answer.add("Двенадцать");
        false_answer.add("Сороктри");
        location.event one = new location.event("Два", false_answer, "1 + 1"),
                two = new location.event("Четыре", false_answer, "2 + 2"),
                three = new location.event("3", false_answer, "2 + 1"),
                four = new location.event("Пятнадцать", false_answer, "3 * 5"),
                five = new location.event("Сорокчетыре", false_answer, "4 + 4 на js"),
                six = new location.event("Двадцать", false_answer, "5 * 4"),
                seven = new location.event("Восемнадцать", false_answer, "3 * 3 * 2");
        ArrayList<location.event> events = new ArrayList<location.event>();
        events.add(one);
        events.add(two);
        events.add(three);
        events.add(four);
        events.add(five);
        events.add(six);
        events.add(seven);

        location
                living_spaces = new location("Жилые помещения", "a", loots, events, enemies),
                kitchen = new location("Кухня", "б", loots, events, enemies),
                hospital = new location("Больница", "в", loots, events, enemies),
                engine_room = new location("Машинное отделение", "г", loots, events, enemies),
                warehouse = new location("Склад", "д", loots, events, enemies);

        ArrayList<location> locations = new ArrayList<>();
        locations.add(living_spaces);
        locations.add(kitchen);
        locations.add(hospital);
        locations.add(engine_room);
        locations.add(warehouse);
        avatar hero = new avatar(20, 30, 10, 10, 4, arms, cap, T_shirt, shorts);

        //тестовый кусок начало
        hero.available_equipment.add(knife);
        hero.available_equipment.add(helmet);
        hero.available_equipment.add(bulletproof_vest);
        hero.available_equipment.add(trousers);
        hero.inventory.add(heal_body);
        hero.inventory.add(heal_mind);
        hero.inventory.add(damage_enemy);
        //тестовый кусок конец

        String logo = "Приветствие игрока, ввод в курс дела. После приветствия перед игроком описывается база и концепция вылазок глазами главного героя.";
        someTextHelper.setText(logo);
        textsLayout.addView(someTextHelper);
        buttonsLayout.addView(getStartButton(locations, hero));
    }

    public Button getStartButton(ArrayList<location> locations, avatar hero) {
        Button btn = new Button(getApplicationContext());
        btn.setText("Начать игру");
        btn.setOnClickListener(v -> {
            lobby(hero, locations);
        });
        return btn;
    }

    public void selectAvailableEquipment(avatar hero, ArrayList location) {
        buttonsLayout.removeAllViews();
        hero.const_armor = 10 + hero.head_clothes.armor_class + hero.body_clothes.armor_class + hero.legs_clothes.armor_class;
        hero.const_armor_mind = 10 + hero.head_clothes.mind_armor_class + hero.body_clothes.mind_armor_class + hero.legs_clothes.mind_armor_class;
        someTextHelper.setText(hero.avatar_review());
        textsLayout.removeView(someTextHelper);
        textsLayout.addView(someTextHelper);
        buttonsLayout.addView(reverse("Закончить выбор снаряжения", hero, location));
        for (int i1 = 0; i1 < hero.available_equipment.size(); i1++) {
            if (hero.available_equipment.get(i1) instanceof weapon) {
                buttonsLayout.addView(getInventoryButton(((weapon) hero.available_equipment.get(i1)).name, i1, hero, location));
            } else {
                buttonsLayout.addView(getInventoryButton(((clothes) hero.available_equipment.get(i1)).name, i1, hero, location));
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void lobby(avatar hero, ArrayList<location> locations) {
        buttonsLayout.removeAllViews();
        someTextHelper.setText("Вы находитесь на своей базе, выберите куда вы пойдёте сейчас: \n" +" До окончания призыва осталось: " + ritual_counter);
        textsLayout.removeView(someTextHelper);
        textsLayout.addView(someTextHelper);
        Button WaitingButton = new Button(getApplicationContext());
        WaitingButton.setText("Сидеть и отдыхать");
        WaitingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ritual_counter--;
                WaitingText.setText("Вы хорошо отдохнули и полечились. Вам стало в разы лучше \n" + (hero.avatar_healing_xp(5) + "\n" + hero.avatar_healing_mind(2)));
                textsLayout.removeView(WaitingText);
                textsLayout.addView(WaitingText);
            }
        });
        buttonsLayout.addView(WaitingButton);
        Button btn = new Button(getApplicationContext());
        for (int i = 0; i < locations.size(); i++) {
            Button b = new Button(getApplicationContext());
            b.setText(locations.get(i).name);
            int finalI = i - 1;
            if (i == 0) {
                btn.setText("Выбор брони и оружия перед вылазкой");
                btn.setOnClickListener(v1 -> {
                    selectAvailableEquipment(hero, locations);
                });
                buttonsLayout.addView(btn);
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
//        if(ritual_counter <= 0){
//            textsLayout.removeAllViews();
//            buttonsLayout.removeAllViews();
//            someTextHelper.setText("ВЫ ПРОИГРАЛИ");
//            btn.setText("Хотите попробовать снова?");
//            btn.setOnClickListener(v1 -> {
//                recreate();
//            });
//        }
    }


    public Button reverse(String text, avatar hero, ArrayList location){
        Button btn = new Button(getApplicationContext());
        btn.setText(text);
        btn.setOnClickListener(v->{
            lobby(hero, location);
        });
        return btn;
    }
    @SuppressLint("SetTextI18n")
    public Button getInventoryButton(String text, int i, avatar hero, ArrayList locations){
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
            selectAvailableEquipment(hero, locations);
        });
        return btn;
    }
}