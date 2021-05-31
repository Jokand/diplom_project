package com.example.diplomjava;

import java.util.ArrayList;
import java.util.Random;

public class avatar {
    int xp, mind, armor, mind_armor, damage, MAX_xp, MAX_mind, const_armor, const_armor_mind;
    ArrayList<item> inventory;
    ArrayList<Object> available_equipment;
    weapon weapon;
    clothes head_clothes, body_clothes, legs_clothes;

    TextView numeralInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        numeralInput = (TextView) findViewById(R.id.textView);

        convert.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        String intValue = numeralInput.getText().toString();
                        try{
                            int integer = Integer.parseInt(intValue);
                            if (integer > 0 && integer <= 4999){
                                translator(integer);

                            }else{
                                numeralInput.setText("Please enter an integer between 0 and 4,999.");
                            }

                        }catch(NumberFormatException e){
                            numeralInput.setText("Invalid input try again.");
                        }
                    }
                }
        );

    public avatar(int xp, int mind, int armor, int mind_armor, int damage, weapon weapon, clothes head_clothes, clothes body_clothes, clothes legs_clothes) {
        this.xp = xp;
        this.mind = mind;
        this.armor = armor;
        this.const_armor = armor;
        this.mind_armor = mind_armor;
        this.const_armor_mind = mind_armor;
        this.damage = damage;
        this.MAX_xp = xp;
        this.MAX_mind = mind;
        this.inventory = new ArrayList<>();
        this.available_equipment = new ArrayList<>();
        this.weapon = weapon;
        this.head_clothes = head_clothes;
        this.body_clothes = body_clothes;
        this.legs_clothes = legs_clothes;
    }

    int avatar_attack() {
        return (int) (damage * Math.random() + 1 + weapon.bonus_damage);
    }

    void defense() {
        armor = armor + 2;
    }

    void stop_effect_defense() {
        if (const_armor != armor) {
            armor = const_armor;
        }
    }

    void defense_mind() {
        mind_armor = mind_armor + 2;
    }

    void stop_effect_defense_mind() {
        if (const_armor_mind != mind_armor) {
            mind_armor = const_armor_mind;
        }
    }

    int avatar_hit() {
        return new Random().nextInt(21)+ weapon.bonus_damage;
    }

    void avatar_review(){
        System.out.println("Физическое здоровье: " + xp + ", Ментальное здоровье: " + mind + "\n" + "Защита тела равна: " + const_armor + ", Защита разума равна: " + const_armor_mind + "\n" + "Экипированное снаряжение: " + "\n" + "Экипированное оружие:");
        weapon.weapon_review();
        System.out.println("Экипированная броня:");
        head_clothes.clothes_review();
        body_clothes.clothes_review();
        legs_clothes.clothes_review();
    }

    void avatar_healing_xp(int heal) {
        int difference = MAX_xp - xp;
        if (difference > heal) {
            xp = xp + heal;
            someTextHelper.setText("Вы находитесь на своей базе, выберите куда вы пойдёте сейчас:");
            textsLayout.addView(someTextHelper);
            System.out.println("Вы восстановили " + heal + " едениц здоровья " + xp + "/" + MAX_xp);
        } else {
            xp = MAX_xp;
            System.out.println("Вы полностью восстановили здоровье " + xp + "/" + MAX_xp);
        }
    }

    void avatar_healing_mind(int mind_heal) {
        int difference = MAX_mind - mind;
        if (difference > mind_heal) {
            mind = mind + mind_heal;
            System.out.println("Вы восстановили " + mind_heal + " едениц ментального здоровья " + mind + "/" + MAX_mind);
        } else {
            mind = MAX_mind;
            System.out.println("Вы полностью восстановили ментальное здоровье " + mind + "/" + MAX_mind);
        }
    }
}
