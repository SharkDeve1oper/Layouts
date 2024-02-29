package sharkdeve1oper.apps.arabic.ui.rim;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.regex.Pattern;

import sharkdeve1oper.apps.arabic.R;
import sharkdeve1oper.apps.arabic.databinding.FragmentRimBinding;

public class RimFragment extends Fragment {

    private FragmentRimBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Инициализация активности
        RimViewModel rimViewModel =
                new ViewModelProvider(this).get(RimViewModel.class);

        binding = FragmentRimBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Событие на кнопку: перевести
        Button translateButton = root.findViewById(R.id.translateNumberButtonRim);
        translateButton.setOnClickListener(v -> {
            EditText numberEditText = root.findViewById(R.id.editTextNumberRim);
            if (!isNumber(numberEditText.getText().toString().toUpperCase().replace(" ", "").replace(".", ""))) {
                Toast.makeText(getActivity(), "Вы неправильно ввели число в арабской системе!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (numberEditText.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Вы ничего не ввели!", Toast.LENGTH_SHORT).show();
                return;
            }

            int number = Integer.parseInt(numberEditText.getText().toString().toUpperCase().replace(" ", "").replace(".", ""));

            if ((number <= 0) || (number > 3999)) {
                Toast.makeText(getActivity(), "Нарушен диапазон (1-3999)!", Toast.LENGTH_SHORT).show();
                return;
            }

            EditText answerEditText = root.findViewById(R.id.editTextAnswerRim);
            answerEditText.setText(String.format("%s", intToRoman(number)));
        });

        // Событие на кнопку: скопировать
        Button copyButton = root.findViewById(R.id.copyAnswerButtonRim);
        copyButton.setOnClickListener(v -> {

            EditText answerEditText = root.findViewById(R.id.editTextAnswerRim);

            if (answerEditText.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Нет ответа!", Toast.LENGTH_SHORT).show();
                return;
            }

            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", answerEditText.getText().toString());
            clipboard.setPrimaryClip(clip);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public boolean isNumber(String s) {
        String regex = "^\\d+$";
        return Pattern.matches(regex, s);
    }

    public String intToRoman(int num) {
        String[] thousands = {"", "M", "MM", "MMM", "IV̅", "V̅", "VI̅", "VII̅", "VIII̅", "IX̅"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return thousands[num / 1000] +
                hundreds[(num % 1000) / 100] +
                tens[(num % 100) / 10] +
                ones[num % 10];
    }
}