package com.example.diplomjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

import static com.example.diplomjava.locationClass.exploration;
import static com.example.diplomjava.locationClass.giving_out_loot;

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
        event one = new event("Два", false_answer, "1 + 1"),
                two = new event("Четыре", false_answer, "2 + 2"),
                three = new event("3", false_answer, "2 + 1"),
                four = new event("Пятнадцать", false_answer, "3 * 5"),
                five = new event("Сорокчетыре", false_answer, "4 + 4 на js"),
                six = new event("Двадцать", false_answer, "5 * 4"),
                seven = new event("Восемнадцать", false_answer, "3 * 3 * 2");
        ArrayList<event> events = new ArrayList<event>();
        events.add(one);
        events.add(two);
        events.add(three);
        events.add(four);
        events.add(five);
        events.add(six);
        events.add(seven);

        locationClass
                living_spaces = new locationClass("Жилые помещения", "a", loots, events, enemies),
                kitchen = new locationClass("Кухня", "б", loots, events, enemies),
                hospital = new locationClass("Больница", "в", loots, events, enemies),
                engine_room = new locationClass("Машинное отделение", "г", loots, events, enemies),
                warehouse = new locationClass("Склад", "д", loots, events, enemies);

        ArrayList<locationClass> locations = new ArrayList<>();
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
    public Button getStartButton(ArrayList<locationClass> locations, avatar hero) {
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
    void lobby(avatar hero, ArrayList<locationClass> locations) {
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
            int finalI = i;
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
                        textsLayout.removeView(someTextHelper);
                        someTextHelper.setText("Вы пошли исследовать " + locations.get(finalI).name);
                        //exploration(); //1 - поражение 2 - победа 3 - побег
                        textsLayout.addView(someTextHelper);
                    }
                }
            });
            buttonsLayout.addView(b);
        }
        if(ritual_counter <= 0){
            textsLayout.removeAllViews();
            buttonsLayout.removeAllViews();
            someTextHelper.setText("ВЫ ПРОИГРАЛИ");
            btn.setText("Хотите попробовать снова?");
            btn.setOnClickListener(v1 -> {
                recreate();
            });
        }
    }

    public class event{
        String description;
        String true_answer;
        ArrayList<String> false_answer;
        public event(String true_answer, ArrayList<String> false_answer, String description) {
            this.true_answer = true_answer;
            this.false_answer = false_answer;
            this.description = description;
        }
        @SuppressLint("SetTextI18n")
        void issuing_an_event(avatar hero){
            int position_of_the_true_answer = new Random().nextInt(false_answer.size());
            false_answer.add(position_of_the_true_answer, true_answer);
            someTextHelper.setText(description);
            textsLayout.removeView(someTextHelper);
            textsLayout.addView(someTextHelper);
            for (int i = 0; i < false_answer.size()+1; i++) {
                Button btn = new Button(getApplicationContext());
                btn.setText(false_answer.get(i));
                int finalI = i;
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(position_of_the_true_answer==finalI){
                            false_answer.remove(position_of_the_true_answer);
                            someTextHelper.setText("Вы правильно решили задачу\n" + giving_out_loot(hero));
                            textsLayout.addView(someTextHelper);
                        }else{
                            false_answer.remove(position_of_the_true_answer);
                            someTextHelper.setText("Вы неправильно решили задачу и довольно сильно нашумели. Теперь вам нужно быстро убегать");
                            textsLayout.addView(someTextHelper);
                        }
                    }
                });
                buttonsLayout.addView(btn);
            }
        }
    }

    public Button reverse(String text, avatar hero, ArrayList<locationClass> location){
        Button btn = new Button(getApplicationContext());
        btn.setText(text);
        btn.setOnClickListener(v->{
            lobby(hero, location);
        });
        return btn;
    }

    @SuppressLint("SetTextI18n")
    public Button getInventoryButton(String text, int i, avatar hero, ArrayList<locationClass> locations){
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

    @SuppressLint("SetTextI18n")
    void enemyAttack(avatar hero, enemy Enemy){
        Enemy.armor = Enemy.const_armor;
        int enemyAttack = new Random().nextInt(101);
        if (enemyAttack < Enemy.bodyDamagePercent && enemyAttack > Enemy.mindDamagePercent) {
            int hit = Enemy.enemy_hit();
            textsLayout.removeView(someTextHelper);
            if ((hit > hero.armor) && (hero.xp > 0)) {
                hero.xp -= Enemy.attack();
                someTextHelper.setText("Вас ранил " + Enemy.name + ". Теперь у вас осталось " + hero.xp + "/" + hero.MAX_xp);
            } else{
                someTextHelper.setText("Меня ударили, но не пробили броню." + hero.xp + "/" + hero.MAX_xp);
            }
            textsLayout.addView(someTextHelper);
        } else if (enemyAttack < Enemy.bodyDamagePercent && enemyAttack > Enemy.defencePercent) {
            int hit = Enemy.enemy_hit();
            textsLayout.removeView(someTextHelper);
            if ((hit > hero.mind_armor) && (hero.mind > 0)) {
                hero.mind -= Enemy.attack_mind();
                someTextHelper.setText("Ваш разум повредил " + Enemy.name + ". Теперь у вас осталось " + hero.mind + "/" + hero.MAX_mind);
            } else{
                someTextHelper.setText("Меня ударили, но не пробили ментальную броню." + hero.mind + "/" + hero.MAX_mind);
            }
            textsLayout.addView(someTextHelper);
        } else {
            Enemy.defense();
            textsLayout.removeView(someTextHelper);
            someTextHelper.setText(Enemy.name + " усилил защиту " + Enemy.armor);
            textsLayout.addView(someTextHelper);
        }
    }

    @SuppressLint("SetTextI18n")
    public void fight(enemy Enemy, avatar hero, ArrayList locations){
        hero.armor = hero.const_armor;
        hero.mind_armor = hero.const_armor_mind;
        Button attack = new Button(getApplicationContext());
        attack.setText("Атаковать");
        attack.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                hero.stop_effect_defense();
                hero.stop_effect_defense_mind();
                textsLayout.removeView(someTextHelper);
                int hit = hero.avatar_hit();
                if ((hit > Enemy.armor) && (Enemy.xp > 0)) {
                    Enemy.xp -= hero.avatar_attack();
                    someTextHelper.setText("Вы ранили " + Enemy.name + ". Теперь у него осталось " + Enemy.xp + "/" + Enemy.MAX_xp);
                } else if (hit <= Enemy.armor) {
                    someTextHelper.setText("Вы не попали по " + Enemy.name + ". " + Enemy.xp + "/" + Enemy.MAX_xp);
                }
                textsLayout.addView(someTextHelper);
                enemyAttack(hero, Enemy);
            }
        });
        buttonsLayout.addView(attack);
        Button defence = new Button(getApplicationContext());
        defence.setText("Защищаться");
        defence.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                textsLayout.removeView(someTextHelper);
                someTextHelper.setText("Выберите что будете защищать:");
                textsLayout.addView(someTextHelper);
                Button defenceBody = new Button(getApplicationContext());
                defenceBody.setText("Повысить защиту тела");
                defenceBody.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hero.defense();
                        textsLayout.removeView(someTextHelper);
                        someTextHelper.setText("Вы встали в защитную стойку. Ваша защита была повышена на 2 еденицы. Ваша защита теперь равна " + hero.armor + ".");
                        textsLayout.addView(someTextHelper);
                        enemyAttack(hero, Enemy);
                    }
                });
                Button defenceMind = new Button(getApplicationContext());
                defenceMind.setText("Повысить защиту разума");
                defenceMind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hero.defense_mind();
                        textsLayout.removeView(someTextHelper);
                        someTextHelper.setText("Вы отчистили свой разум. Ваша ментальная защита была повышена на 2 еденицы. Ваша ментальная защита теперь равна " + hero.mind_armor + ".");
                        textsLayout.addView(someTextHelper);
                        enemyAttack(hero, Enemy);
                    }
                });
                buttonsLayout.addView(defenceBody);
                buttonsLayout.addView(defenceMind);
            }
        });
        buttonsLayout.addView(defence);

        Button useItems = new Button(getApplicationContext());
        useItems.setText("Использовать предмет");
        useItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hero.inventory.size() == 0) {
                    textsLayout.removeView(someTextHelper);
                    someTextHelper.setText("Инвентарь пуст");
                    textsLayout.addView(someTextHelper);
                }
                for (int i = 0; i < hero.inventory.size(); i++) {
                    Button btn = new Button(getApplicationContext());
                    int inventory_vote = i;
                    btn.setText(hero.inventory.get(i).name);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View v) {
                            textsLayout.removeView(someTextHelper);
                            if (hero.inventory.get(inventory_vote).what_heals == 1) {
                                hero.avatar_healing_xp(hero.inventory.get(inventory_vote).amount_of_treatment);
                                someTextHelper.setText("Вы использовали " + hero.inventory.get(inventory_vote + 1).name + " и восстановили " +
                                        hero.inventory.get(inventory_vote).amount_of_treatment + " xp");
                            } else if (hero.inventory.get(inventory_vote).what_heals == 2) {
                                hero.avatar_healing_mind(hero.inventory.get(inventory_vote).amount_of_treatment);
                                someTextHelper.setText("Вы использовали " + hero.inventory.get(inventory_vote + 1).name + " и восстановили " +
                                        hero.inventory.get(inventory_vote).amount_of_treatment + " очков ментального здоровья");
                            } else {
                                Enemy.xp -= hero.inventory.get(inventory_vote).amount_of_treatment;
                                someTextHelper.setText("Вы использовали " + hero.inventory.get(inventory_vote + 1).name + " ранили своего противника на  " +
                                        hero.inventory.get(inventory_vote).amount_of_treatment + " урона.");
                            }
                            textsLayout.addView(someTextHelper);
                            hero.inventory.remove(inventory_vote);
                            enemyAttack(hero, Enemy);
                        }
                    });
                    buttonsLayout.addView(btn);
                }

                Button goToBack = new Button(getApplicationContext());
                goToBack.setText("Назад");
                goToBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fight(Enemy, hero, locations);
                    }
                });
                buttonsLayout.addView(goToBack);
            }
        });
        buttonsLayout.addView(useItems);

        Button runningOnFight = new Button(getApplicationContext());
        runningOnFight.setText("Убежать");
        runningOnFight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textsLayout.removeView(someTextHelper);
                someTextHelper.setText("Вы сбежали с поля боя");
                textsLayout.addView(someTextHelper);
                lobby(hero, locations);
            }
        });
        buttonsLayout.addView(runningOnFight);
        Button reviewHero = new Button(getApplicationContext());
        reviewHero.setText("Убежать");
        reviewHero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textsLayout.removeView(someTextHelper);
                someTextHelper.setText(hero.avatar_review());
                textsLayout.addView(someTextHelper);
                fight(Enemy, hero, locations);
            }
        });
        buttonsLayout.addView(reviewHero);

//        if (Enemy.xp <= 0) {
//            textsLayout.removeView(someTextHelper);
//            someTextHelper.setText("Вы убили своего противника " + Enemy.name + ".");
//            textsLayout.addView(someTextHelper);
//            result_defeat = 2;
//        }
        //return result_defeat;//1 - поражение 2 - победа 3 - побег
    }
}