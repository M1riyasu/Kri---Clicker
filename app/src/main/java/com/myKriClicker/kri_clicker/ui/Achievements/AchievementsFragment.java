package com.myKriClicker.kri_clicker.ui.Achievements;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.myKriClicker.kri_clicker.R;
import com.myKriClicker.kri_clicker.databinding.ActivityAchievementsBinding;
import com.myKriClicker.kri_clicker.MainActivity;

public class AchievementsFragment extends Fragment {
    private ActivityAchievementsBinding binding;
    ImageButton[] firstLineButtons = new ImageButton[7];
    ImageButton[] secondLineButtons = new ImageButton[6];
    private TextView CurrentKrikoinAchievements, KrikoinPerSecondAchievements;
    private ConstraintLayout achievementActivity;
    private ImageView AchievementsKrikoin, achievementsKrikoinPerSecond;

    private ImageView achievementImagePopup;
    private View v;
    private PopupWindow popupWindow;


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
    int[][] achievementImageIds = {
            {
                    R.drawable.id1a_1, R.drawable.id1a_2, R.drawable.id1a_3, R.drawable.id1a_4,
                    R.drawable.id1a_5, R.drawable.id1a_6, R.drawable.id1a_7
            },
            {
                    R.drawable.id2a_1, R.drawable.id2a_2, R.drawable.id2a_3, R.drawable.id2a_4,
                    R.drawable.id2a_5, R.drawable.id2a_6, R.drawable.id2a_1, R.drawable.id2a_1
            },
            {
                    R.drawable.id2a_2, R.drawable.id2a_3, R.drawable.id2a_4, R.drawable.id2a_5,
                    R.drawable.id2a_6, R.drawable.id2a_1, R.drawable.id2a_1, R.drawable.id2a_1
            },
            {
                    R.drawable.id2a_2, R.drawable.id2a_3, R.drawable.id2a_4, R.drawable.id2a_5,
                    R.drawable.id2a_6, R.drawable.id2a_1, R.drawable.id2a_1, R.drawable.id2a_1
            },
            {
                    R.drawable.id2a_2, R.drawable.id2a_3, R.drawable.id2a_4, R.drawable.id2a_5,
                    R.drawable.id2a_6, R.drawable.id2a_1, R.drawable.id2a_1, R.drawable.id2a_1
            },
            {
                    R.drawable.id2a_2, R.drawable.id2a_3, R.drawable.id2a_4, R.drawable.id2a_5,
                    R.drawable.id2a_6, R.drawable.id2a_1, R.drawable.id2a_1, R.drawable.id2a_1
            }
    };

    private final String[][] achievementDescription = new String[7][7];
    private final String[][] achievementRequirement = new String[6][7];

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
                             ViewGroup container, Bundle savedInstanceState) {


        binding = ActivityAchievementsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        CurrentKrikoinAchievements = binding.CurrentKrikoinAchievements;
        KrikoinPerSecondAchievements = binding.KrikoinPerSecondAchievements;
        achievementActivity = binding.achievementActivity;
        AchievementsKrikoin = binding.AchievementsKrikoin;
        achievementsKrikoinPerSecond = binding.AchievementsKrikoinPerSecond;

        load();

        for (int i = 0; i < firstLineButtons.length; i++) {
            final int finalI = i;
            firstLineButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    v = view;
                    calculate((byte)0, (byte)finalI);
                }
            });
        }

        for (int i = 0; i < secondLineButtons.length; i++) {
            final int finalI = i;
            secondLineButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    v = view;
                    calculate((byte)1, (byte)finalI);
                }
            });
        }


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
    private void calculate(byte i, byte j) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return; // Если открыто, ничего не делаем
        }
        LayoutInflater inflater = getLayoutInflater();
        View achievementPopupView = inflater.inflate(R.layout.popup_achievement, null);
        popupWindow = createPopupWindow(achievementPopupView);
        // Получение размеров экрана
        DisplayMetrics displayMetrics = new DisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

// Создание объекта <link>PopupWindow</link>
        PopupWindow popupWindow = createPopupWindow(achievementPopupView);

// Установка ширины и высоты всплывающего окна
        int popupWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        int popupHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        popupWindow.setWidth(popupWidth);
        popupWindow.setHeight(popupHeight);

// Установка фокуса на всплывающем окне
        popupWindow.setFocusable(true);

// Показ всплывающего окна в центре экрана
        int x = (screenWidth - popupWidth) / 2;
        int y = (screenHeight - popupHeight) / 2 - 200;
        popupWindow.showAtLocation(achievementPopupView, Gravity.CENTER, x, y);
        achievementImagePopup = achievementPopupView.findViewById(R.id.achievementImagePopup);
        TextView achievementDescriptionPopup = achievementPopupView.findViewById(R.id.achievementDescriptionPopup);
        TextView achievementRequirementPopup = achievementPopupView.findViewById(R.id.achievementRequirementPopup);

        achievementImagePopup.setImageResource(achievementImageIds[i][j]);
        achievementDescriptionPopup.setText(achievementDescription[i][j]);
        achievementRequirementPopup.setText(achievementRequirement[i][j]);
    }
    private PopupWindow createPopupWindow(View aсhievementPopupView) {
        return new PopupWindow(
                aсhievementPopupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );
    }

    private void loadString () {
        achievementDescription[0][0] = getString(R.string.achievementClicks1);
        achievementDescription[0][1] = getString(R.string.achievementClicks2);
        achievementDescription[0][2] = getString(R.string.achievementClicks3);
        achievementDescription[0][3] = getString(R.string.achievementClicks4);
        achievementDescription[0][4] = getString(R.string.achievementClicks5);
        achievementDescription[0][5] = getString(R.string.achievementClicks6);
        achievementDescription[0][6] = getString(R.string.achievementClicks7);

        achievementDescription[1][0] = getString(R.string.achievementTime1) + "\n" + getString(R.string.achievementTime10);
        achievementDescription[1][1] = getString(R.string.achievementTime2) + "\n" + getString(R.string.achievementTime20);
        achievementDescription[1][2] = getString(R.string.achievementTime3) + "\n" + getString(R.string.achievementTime30);
        achievementDescription[1][3] = getString(R.string.achievementTime4) + "\n" + getString(R.string.achievementTime40);
        achievementDescription[1][4] = getString(R.string.achievementTime5) + "\n" + getString(R.string.achievementTime50);
        achievementDescription[1][5] = getString(R.string.achievementTime6) + "\n" + getString(R.string.achievementTime60);



        achievementRequirement[0][0] = getString(R.string.achievementClicksRequire1);
        achievementRequirement[0][1] = getString(R.string.achievementClicksRequire2);
        achievementRequirement[0][2] = getString(R.string.achievementClicksRequire3);
        achievementRequirement[0][3] = getString(R.string.achievementClicksRequire4);
        achievementRequirement[0][4] = getString(R.string.achievementClicksRequire5);
        achievementRequirement[0][5] = getString(R.string.achievementClicksRequire6);
        achievementRequirement[0][6] = getString(R.string.achievementClicksRequire7);

        achievementRequirement[1][0] = getString(R.string.achievementTimeRequire1);
        achievementRequirement[1][1] = getString(R.string.achievementTimeRequire2);
        achievementRequirement[1][2] = getString(R.string.achievementTimeRequire3);
        achievementRequirement[1][3] = getString(R.string.achievementTimeRequire4);
        achievementRequirement[1][4] = getString(R.string.achievementTimeRequire5);
        achievementRequirement[1][5] = getString(R.string.achievementTimeRequire6);
    }
    private void loadButtons() {
        firstLineButtons[0] = binding.id1A1;
        firstLineButtons[1] = binding.id1A2;
        firstLineButtons[2] = binding.id1A3;
        firstLineButtons[3] = binding.id1A4;
        firstLineButtons[4] = binding.id1A5;
        firstLineButtons[5] = binding.id1A6;
        firstLineButtons[6] = binding.id1A7;


        secondLineButtons[0] = binding.id2A1;
        secondLineButtons[1] = binding.id2A2;
        secondLineButtons[2] = binding.id2A3;
        secondLineButtons[3] = binding.id2A4;
        secondLineButtons[4] = binding.id2A5;
        secondLineButtons[5] = binding.id2A6;



    }
    private void loadAchievements() {
        int redCol = Color.parseColor("#AF1919");
        ColorDrawable redColor = new ColorDrawable(redCol);
        int greenCol = Color.parseColor("#199619");
        ColorDrawable greenColor = new ColorDrawable(greenCol);
        long[] timeCondition = ((MainActivity) requireActivity()).getTimeCondition();
        long[] clickCondition = ((MainActivity) requireActivity()).getClickCondition();
        for (byte i = 0; i < firstLineButtons.length; i++) {
            if (((MainActivity) requireActivity()).getClickCount() < clickCondition[i]) {
                firstLineButtons[i].setBackground(redColor);
            } else {
                firstLineButtons[i].setBackground(greenColor);
                ((MainActivity) requireActivity()).setClickAchievementMultiply(i);
            }
        }
        for (byte i = 0; i < secondLineButtons.length; i++) {
            if (((MainActivity) requireActivity()).getTimeCount() < timeCondition[i]) {
                secondLineButtons[i].setBackground(redColor);
            } else {
                secondLineButtons[i].setBackground(greenColor);
                ((MainActivity) requireActivity()).setTimeAchievementMultiply(i);
            }
        }
    }

    private void setKriKoins() {
        AchievementsKrikoin.setImageResource(krikoinIds[((MainActivity) requireActivity()).getChosenKrikoin()]);
        achievementsKrikoinPerSecond.setImageResource(krikoinIds[((MainActivity) requireActivity()).getChosenKrikoin()]);
    }
    private void updateCash() {
        CurrentKrikoinAchievements.setText(((MainActivity) requireActivity()).getUpdateCashDisplayText());
    }

    private void KPS() {
        KrikoinPerSecondAchievements.setText(((MainActivity) requireActivity()).getKPSDisplayText());
    }
    private void load() {
        updateCash();
        loadString();
        loadButtons();
        loadAchievements();
        setKriKoins();
        KPS();
        if (handler == null) {
            handler = new Handler();
            handler.postDelayed(update, 200);
        } else {
            handler.removeCallbacks(update);
            handler.postDelayed(update, 200);
        }
    }
}