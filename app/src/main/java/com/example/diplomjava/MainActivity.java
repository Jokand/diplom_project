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
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_field);

        textsLayout = findViewById(R.id.linearLayoutText);
        buttonsLayout = findViewById(R.id.linearLayoutButton);

        ArrayList<location> locations = new ArrayList<>();
        location living_spaces = new location("Жилые помещения", "a"), kitchen = new location("Кухня", "б"), hospital = new location("Больница", "в"), engine_room = new location("Машинное отделение", "г"), warehouse = new location("Склад", "д");
        locations.add(living_spaces);
        locations.add(kitchen);
        locations.add(hospital);
        locations.add(engine_room);
        locations.add(warehouse);
        avatar hero = new avatar(20, 30, 10, 10, 4, null, null, null, null);

        String logo = "Приветствие игрока, ввод в курс дела. После приветствия перед игроком описывается база и концепция вылазок глазами главного героя" + "\n";
        TextView someTextHelper = new TextView(MainActivity.this);
        someTextHelper.setText(logo);
        textsLayout.addView(someTextHelper);

        ButtonsHelper helper = new ButtonsHelper(MainActivity.this);

        buttonsLayout.addView(helper.getStartButton());

        System.out.println("Вы находитесь на своей базе, выберите куда вы пойдёте сейчас:");
//            for (int i = 0; i < locations.size(); i++) {
//                System.out.println((i + 1) + ". " + locations.get(i).name);
//            }
//            System.out.println(locations.size() + 1 + ". Выбор брони и оружия перед вылазкой" + "\n" + "0. Сидеть и ждать");

        for (int i = 0; i < locations.size(); i++) {
            Button b = new Button(MainActivity.this);
            b.setText((i + 1) + ". " + locations.get(i).name);
            int finalI = i;

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalI == -1) {
                        ritual_counter--;
                        hero.avatar_healing_xp(5);
                        hero.avatar_healing_mind(2);
                    } else if (locations.size() == finalI) {
                        hero.const_armor = 10 + hero.head_clothes.armor_class + hero.body_clothes.armor_class + hero.legs_clothes.armor_class;
                        hero.const_armor_mind = 10 + hero.head_clothes.mind_armor_class + hero.body_clothes.mind_armor_class + hero.legs_clothes.mind_armor_class;
                        hero.avatar_review();
                        while (true) {
                            for (int i = 0; i < hero.available_equipment.size(); i++) {
                                if (hero.available_equipment.get(i) instanceof weapon) {
                                    System.out.println((i + 1) + ". " + ((weapon) hero.available_equipment.get(i)).name);
                                } else {
                                    System.out.println((i + 1) + ". " + ((clothes) hero.available_equipment.get(i)).name);
                                }
                            }
                            System.out.println("0. Прекратить выбирать снаряжение");
                            int inventory_vote = -1;
                            if (inventory_vote == -1) {
                                break;
                            }
                            boolean category_clothes = hero.available_equipment.get(inventory_vote) instanceof clothes;
                            if (category_clothes) {
                                try {
                                    clothes choose = null;
                                    int vote = ((clothes) hero.available_equipment.get(inventory_vote)).place_of_protection;
                                    if (vote == 1) {
                                        clothes leg = hero.legs_clothes;
                                        hero.legs_clothes = (clothes) hero.available_equipment.get(inventory_vote);
                                        choose = (clothes) hero.available_equipment.get(inventory_vote);
                                        hero.available_equipment.remove(inventory_vote);
                                        hero.available_equipment.add(leg);
                                    } else if (vote == 2) {
                                        clothes body = hero.body_clothes;
                                        hero.body_clothes = (clothes) hero.available_equipment.get(inventory_vote);
                                        choose = (clothes) hero.available_equipment.get(inventory_vote);
                                        hero.available_equipment.remove(inventory_vote);
                                        hero.available_equipment.add(body);
                                    } else if (vote == 3) {
                                        clothes head = hero.head_clothes;
                                        hero.head_clothes = (clothes) hero.available_equipment.get(inventory_vote);
                                        choose = (clothes) hero.available_equipment.get(inventory_vote);
                                        hero.available_equipment.remove(inventory_vote);
                                        hero.available_equipment.add(head);
                                    }
                                    hero.const_armor = 10 + hero.head_clothes.armor_class + hero.body_clothes.armor_class + hero.legs_clothes.armor_class;
                                    hero.const_armor_mind = 10 + hero.head_clothes.mind_armor_class + hero.body_clothes.mind_armor_class + hero.legs_clothes.mind_armor_class;
                                    assert choose != null;
                                    System.out.println("Вы надели на себя " + choose.name + ". Теперь ваша физическая защита равна " + hero.const_armor + ", а ваша ментальная защита стала равна " + hero.const_armor_mind);
                                } catch (Exception ex) {
                                    System.out.println("Неккоректный ввод");
                                }
                            } else {
                                try {
                                    weapon chose;
                                    weapon Weapon = hero.weapon;
                                    hero.weapon = (weapon) hero.available_equipment.get(inventory_vote);
                                    chose = (weapon) hero.available_equipment.get(inventory_vote);
                                    hero.available_equipment.remove(inventory_vote);
                                    hero.available_equipment.add(Weapon);
                                    assert chose != null;
                                    System.out.println("Вы надели на себя " + chose.name + ". Теперь ваш модификатор урона равен " + hero.weapon.bonus_damage);
                                } catch (Exception ex) {
                                    System.out.println("Неккоректный ввод");
                                }
                            }
                        }
                    } else if (finalI < locations.size()) {
                        if (locations.get(finalI).exploration(hero)) {
                            return;
                        } else ritual_counter--;
                    }

                }
            });
            ((LinearLayout) findViewById(R.id.layout)).addView(b);
        }

        if (ritual_counter<=0) {
            System.out.println("Вы не успели сорвать планы культистов и весь мир поглотило безумие и тьма");

        }
    }
}