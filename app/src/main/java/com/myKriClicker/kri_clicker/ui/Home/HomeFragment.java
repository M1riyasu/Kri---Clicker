package com.myKriClicker.kri_clicker.ui.Home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.myKriClicker.kri_clicker.R;
import com.myKriClicker.kri_clicker.databinding.ActivityHomeBinding;
import com.myKriClicker.kri_clicker.MainActivity;

public class HomeFragment extends Fragment {

    private ActivityHomeBinding binding;
    private TextView CurrentKrikoin, KrikoinPerSecond;
    private Button clickButton;
    private ConstraintLayout homeActivity;
    private ImageView HomeKrikoin, homeKrikoinPerSecond;
    int[] krikoinIds = {
            R.drawable.default_krikoin,
            R.drawable.coinid0,
            R.drawable.coinid1,
            R.drawable.coinid2,
            R.drawable.coinid3,
            R.drawable.coinid4,
            R.drawable.coinid5,
            R.drawable.coinid6,
            R.drawable.coinid7,
            R.drawable.coinid8,
            R.drawable.coinid9
    };
    private boolean isNormalNotation = true;

    private Handler handler;
    private Runnable update = new Runnable() {
        @Override
        public void run() {
            updateCash();
            // Установка значения для проверки
            handler.postDelayed(this, 200); // Запуск обновления каждую 0,1 секунду

        }

    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup home, Bundle savedInstanceState) {
        binding = ActivityHomeBinding.inflate(inflater, home, false);

        View root = binding.getRoot();

        // Инициализирование функий из xml

        CurrentKrikoin = binding.CurrentKrikoin;
        KrikoinPerSecond = binding.KrikoinPerSecond;
        clickButton = binding.clickButton;
        homeActivity = binding.homeActivity;
        HomeKrikoin = binding.HomeKrikoin;
        homeKrikoinPerSecond = binding.HomeKrikoinPerSecond;

        load();
        clickButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).onClickHome();
                updateCash();
            }
        });
        return root;
    }
    @Override
    public void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacks(update);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Возобновление обновления
        if (handler == null) {
            handler = new Handler();
            handler.postDelayed(update, 200);
        } else {
            handler.removeCallbacks(update);
            handler.postDelayed(update, 200);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Остановка обновления при уничтожении активности
        if (handler != null) {
            handler.removeCallbacks(update);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    //——————————————————————————————
    //Update data-like methods
    //——————————————————————————————
    private void updateCash() {
        CurrentKrikoin.setText(((MainActivity) requireActivity()).getUpdateCashDisplayText());
    }
    private void KPS() {
        KrikoinPerSecond.setText(((MainActivity) requireActivity()).getKPSDisplayText());
    }
    //——————————————————————————————
    //Set-like methods
    //——————————————————————————————
    private void setKriKoins() {
        HomeKrikoin.setImageResource(krikoinIds[(((MainActivity) requireActivity()).getChosenKrikoin())]);
        homeKrikoinPerSecond.setImageResource(krikoinIds[((MainActivity) requireActivity()).getChosenKrikoin()]);
    }
    //——————————————————————————————
    //Arithmetic-like methods
    //——————————————————————————————
    private void calculate() {
        ((MainActivity) requireActivity()).calculate();
        KPS();
    }
    private void calculateTotalMultipliers() {
        ((MainActivity) requireActivity()).calculateTotalMultipliers();
    }

    //——————————————————————————————

    private void load() {
        updateCash();
        setKriKoins();
        calculate();
        calculateTotalMultipliers();
        if (handler == null) {
            handler = new Handler();
            handler.postDelayed(update, 200);
        } else {
            handler.removeCallbacks(update);
            handler.postDelayed(update, 200);
        }
        KPS();
    }
}