package sharkdeve1oper.apps.arabic.ui.arabic;

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

import sharkdeve1oper.apps.arabic.R;
import sharkdeve1oper.apps.arabic.databinding.FragmentArabicBinding;

public class ArabicFragment extends Fragment {

    private FragmentArabicBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Инициализация активности
        ArabicViewModel arabicViewModel =
                new ViewModelProvider(this).get(ArabicViewModel.class);

        binding = FragmentArabicBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Событие на кнопку: перевести
        Button translateButton = root.findViewById(R.id.translateNumberButton);
        translateButton.setOnClickListener(v -> {
            EditText numberEditText = root.findViewById(R.id.editTextNumber);
            if (!isRomanNumeral(numberEditText.getText().toString().toUpperCase().replace(" ", ""))) {
                Toast.makeText(getActivity(), "Вы неправильно ввели число в римской системе!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (numberEditText.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Вы ничего не ввели!", Toast.LENGTH_SHORT).show();
                return;
            }

            int number = romanToInt(numberEditText.getText().toString().toUpperCase().replace(" ", ""));

            EditText answerEditText = root.findViewById(R.id.editTextAnswer);
            answerEditText.setText(String.format("%s", number));
        });

        // Событие на кнопку: скопировать
        Button copyButton = root.findViewById(R.id.copyAnswerButton);
        copyButton.setOnClickListener(v -> {

            EditText answerEditText = root.findViewById(R.id.editTextAnswer);

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
    public int romanToInt(String s) {
        int[] nums = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case 'M':
                    nums[i] = 1000;
                    break;
                case 'D':
                    nums[i] = 500;
                    break;
                case 'C':
                    nums[i] = 100;
                    break;
                case 'L':
                    nums[i] = 50;
                    break;
                case 'X':
                    nums[i] = 10;
                    break;
                case 'V':
                    nums[i] = 5;
                    break;
                case 'I':
                    nums[i] = 1;
                    break;
            }
        }
        int sum = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] < nums[i + 1])
                sum -= nums[i];
            else
                sum += nums[i];
        }
        return sum + nums[nums.length - 1];
    }

    public boolean isRomanNumeral(String str) {
        String romanNumeralPattern = "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
        return str.matches(romanNumeralPattern);
    }

}