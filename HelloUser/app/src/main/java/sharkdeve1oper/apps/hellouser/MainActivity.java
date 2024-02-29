package sharkdeve1oper.apps.hellouser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateFieldName();

        // Событие на нажатие на кнопку
        Button button = (Button) findViewById(R.id.changeNameButton);
        button.setOnClickListener(v -> {

            // Обновление настроек и поля имени
            EditText nameEditText = (EditText) findViewById(R.id.newNameUser);

            // Проверка поля
            if (nameEditText.getText().toString().equals("")) {
                Toast.makeText(MainActivity.this, "Поле не может быть пустым", Toast.LENGTH_SHORT).show();
                return;
            }

            preferences.edit().putString("name", String.valueOf(nameEditText.getText())).apply();
            updateFieldName();

            // Очищение поля с именем
            nameEditText.setText("");
        });
    }

    protected void updateFieldName(){
        // Инициализация текстового поля
        TextView nameTextView = (TextView) findViewById(R.id.nameUser);


        // Инициализация настроек
        this.preferences = this.getPreferences(Context.MODE_PRIVATE);
        String name = preferences.getString("name", "Мы ещё не знакомы!");

        // Изменение надписи
        nameTextView.setText(String.format("Привет, %s!", name));
    }
}