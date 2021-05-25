package com.example.diplomjava;

import android.content.Context;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ButtonsHelper extends AppCompatActivity {

    Context context;
    Button btn;

    public ButtonsHelper(Context context){
        this.context = context;
    }

    public Button getStartButton(){
        btn = new Button(context);
        btn.setText("Далее");
        btn.setOnClickListener(v->{
            btn.setText("какой то другой текст");
        });
        return btn;
    }
}
