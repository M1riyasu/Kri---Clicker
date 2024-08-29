package com.myKriClicker.kri_clicker.ui.Upgrades;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;

import com.myKriClicker.kri_clicker.MainActivity;
import com.myKriClicker.kri_clicker.R;
import com.myKriClicker.kri_clicker.databinding.ActivityUpgradesBinding;

public class UpgradesFragment extends Fragment {
    private ActivityUpgradesBinding binding;
    private Context mContext;
    private ConstraintLayout upgradesActivity;
    private ImageButton[][] UpgradeArray = {
    new ImageButton[8],
    new ImageButton[8],
    new ImageButton[8],
    new ImageButton[8],
    new ImageButton[8],
    new ImageButton[8],
    new ImageButton[8],
    new ImageButton[8],
    new ImageButton[8],
    new ImageButton[8]};
    private AppCompatButton purchaseAllButton;
    private ImageView imagePopup, UpgradesKrikoin, upgradesKrikoinPerSecond;

    private TextView CurrentKrikoinUpgrades, KrikoinPerSecondUpgrades;
    private GridLayout purchaseableGrid, purchasedGrid;
    private View v;
    ViewGroup parentLinearLayout;
    private Runnable saveRunnable;
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
    int[][] imageIds = {
            {
            R.drawable.id1_1, R.drawable.id1_2, R.drawable.id1_3, R.drawable.id1_4,
            R.drawable.id1_5, R.drawable.id1_6, R.drawable.id1_7, R.drawable.id1_8
            },
            {
            R.drawable.id2_1, R.drawable.id2_2, R.drawable.id2_3, R.drawable.id2_4,
            R.drawable.id2_5, R.drawable.id2_6, R.drawable.id2_7, R.drawable.id2_8
            },
            {
            R.drawable.id3_1, R.drawable.id3_2, R.drawable.id3_3, R.drawable.id3_4,
            R.drawable.id3_5, R.drawable.id3_6, R.drawable.id3_7, R.drawable.id3_8
            },
            {
            R.drawable.id4_1, R.drawable.id4_2, R.drawable.id4_3, R.drawable.id4_4,
            R.drawable.id4_5, R.drawable.id4_6, R.drawable.id4_7, R.drawable.id4_8
            },
            {
            R.drawable.id5_1, R.drawable.id5_2, R.drawable.id5_3, R.drawable.id5_4,
            R.drawable.id5_5, R.drawable.id5_6, R.drawable.id5_7, R.drawable.id5_8
            },
            {
            R.drawable.id6_1, R.drawable.id6_2, R.drawable.id6_3, R.drawable.id6_4,
            R.drawable.id6_5, R.drawable.id6_6, R.drawable.id6_7, R.drawable.id6_8
            },
            {
            R.drawable.id7_1, R.drawable.id7_2, R.drawable.id7_3, R.drawable.id7_4,
            R.drawable.id7_5, R.drawable.id7_6, R.drawable.id7_7, R.drawable.id7_8
            },
            {
            R.drawable.id8_1, R.drawable.id8_2, R.drawable.id8_3, R.drawable.id8_4,
            R.drawable.id8_5, R.drawable.id8_6, R.drawable.id8_7, R.drawable.id8_8
            },
            {
            R.drawable.id9_1, R.drawable.id9_2, R.drawable.id9_3, R.drawable.id9_4,
            R.drawable.id9_5, R.drawable.id9_6, R.drawable.id9_7, R.drawable.id9_8
            },
            {
            R.drawable.id10_1, R.drawable.id10_2, R.drawable.id10_3, R.drawable.id10_4,
            R.drawable.id10_5, R.drawable.id10_6, R.drawable.id10_7, R.drawable.id10_8
            },
    };
    private final String[][] description = new String[10][8];
//    private final String[][] story = new String[10][8];

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
    private Handler handler;

    private Runnable update = new Runnable() {
        @Override
        public void run() {
            updateCash();
            checkPositionClicks();
            // Установка значения для проверки
            handler.postDelayed(this, 200); // Запуск обновления каждую 0,1 секунду
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = ActivityUpgradesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        purchaseableGrid = binding.purchaseableGrid;
        purchasedGrid = binding.purchasedGrid;
        CurrentKrikoinUpgrades = binding.CurrentKrikoinUpgrades;
        KrikoinPerSecondUpgrades = binding.KrikoinPerSecondUpgrades;
        upgradesActivity = binding.upgradesActivity;
        UpgradesKrikoin = binding.UpgradesKriKoin;
        upgradesKrikoinPerSecond = binding.UpgradesKrikoinPerSecond;
        purchaseAllButton = binding.purchaseAllButton;

        load();

        purchaseAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).onClickUpgradesPurchaseAll();
                checkPositionClicks();
            }
        });
        for (byte i = 0; i < 10; i++) {
            for (byte j = 0; j < 8; j++) {
                byte finalI = i;
                byte finalJ = j;
                UpgradeArray[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        v = view;
                        calculate(finalI, finalJ);
                        KPS();
                    }
                });
            }
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
        View popupView = inflater.inflate(R.layout.popup, null);
        popupWindow = createPopupWindow(popupView);
        // Получение размеров экрана
        DisplayMetrics displayMetrics = new DisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

// Создание объекта <link>PopupWindow</link>
        PopupWindow popupWindow = createPopupWindow(popupView);

// Установка ширины и высоты всплывающего окна
        int popupWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        int popupHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        popupWindow.setWidth(popupWidth);
        popupWindow.setHeight(popupHeight);

// Установка фокуса на всплывающем окне
        popupWindow.setFocusable(true);

// Показ всплывающего окна в центре экрана
        int x = (screenWidth - popupWidth) / 2;
        int y = (screenHeight - popupHeight) / 2;
        popupWindow.showAtLocation(popupView, Gravity.CENTER, x, y);
        TextView descriptionPopup = popupView.findViewById(R.id.descriptionPopup);
        TextView upgradePopup = popupView.findViewById(R.id.upgradePopup);
//        TextView storyPopup = popupView.findViewById(R.id.storyPopup);
        Button closePopup = popupView.findViewById(R.id.closePopup);
        Button purchasePopup = popupView.findViewById(R.id.purchasePopup);
        imagePopup = popupView.findViewById(R.id.imagePopup);
        imagePopup.setImageResource(imageIds[i][j]);
        descriptionPopup.setText(description[i][j]);
//        storyPopup.setText(story[i][j]);
        upgradePopup.setText(getString(R.string.price) + ((MainActivity) requireActivity()).updatePriceUpgrades(((MainActivity) requireActivity()).upgradesCost[i][j], i, j));
        purchasePopup.setVisibility(((MainActivity) requireActivity()).upgradesMultiply[i][j] == 1 ? View.VISIBLE : View.GONE);

        closePopup.setOnClickListener(view -> popupWindow.dismiss());
        purchasePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((MainActivity) requireActivity()).getKrikoin() >= ((MainActivity) requireActivity()).upgradesCost[i][j]
                && ((MainActivity) requireActivity()).upgradesMultiply[i][j] == 1) {
                    ((MainActivity) requireActivity()).onClickUpgradesPurchase(i, j);
                    updateCash();
                    ((MainActivity) requireActivity()).setUpgradesMultiply(i, j);
                    setPopupTextAndColor(upgradePopup, getString(R.string.purchased), "#19BE00");
                    parentLinearLayout = (ViewGroup) UpgradeArray[i][j].getParent(); // Получите родительский LinearLayout
                    if (parentLinearLayout != null) {
                        parentLinearLayout.removeView(UpgradeArray[i][j]); // Удалите ImageButton из текущего LinearLayout
                    }
                    purchasedGrid.addView(UpgradeArray[i][j]); // Добавьте ImageButton во второй LinearLayout
                    parentLinearLayout = null;
                    checkPositionClicks();
                    KPS();
                } else if (((MainActivity) requireActivity()).upgradesMultiply[i][j] == 1) {
                    upgradePopup.setText(getString(R.string.insufficientlyFunds));
                    upgradePopup.setTextColor(Color.parseColor("#EA0000"));
                } else {
                    upgradePopup.setText(getString(R.string.alreadyPurhaced));
                    upgradePopup.setTextColor(Color.parseColor("#EA0000"));
                }
            }
        });
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        updateCash();
    }


    private void checkPositionClicks() {
        for (byte i = 0; i < UpgradeArray.length; i++) {
            for (byte j = 0; j < UpgradeArray[i].length; j++) {
                parentLinearLayout = (ViewGroup) UpgradeArray[i][j].getParent();
                if (parentLinearLayout == purchaseableGrid && ((MainActivity) requireActivity()).upgradesMultiply[i][j] != 1) {
                    parentLinearLayout.removeView(UpgradeArray[i][j]);
                    purchasedGrid.addView(UpgradeArray[i][j]);
                }
            }
        }
    }
    private PopupWindow createPopupWindow(View popupView) {
        return new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );
    }
    private void setPopupTextAndColor(TextView textView, String text, String color) {
        textView.setText(text);
        textView.setTextColor(Color.parseColor(color));
    }

    private void loadString () {
        description[0][0] = getString(R.string.multiply2);
        description[0][1] = getString(R.string.multiply5);
        description[0][2] = getString(R.string.multiply2);
        description[0][3] = getString(R.string.multiply2);
        description[0][4] = getString(R.string.multiply5);
        description[0][5] = getString(R.string.multiply2);
        description[0][6] = getString(R.string.multiply5);
        description[0][7] = getString(R.string.multiply5);

        description[1][0] = getString(R.string.multiplyChisel2);
        description[1][1] = getString(R.string.multiplyChisel5);
        description[1][2] = getString(R.string.multiplyChisel2);
        description[1][3] = getString(R.string.multiplyChisel2);
        description[1][4] = getString(R.string.multiplyChisel5);
        description[1][5] = getString(R.string.multiplyChisel2);
        description[1][6] = getString(R.string.multiplyChisel5);
        description[1][7] = getString(R.string.multiplyChisel5);

        description[2][0] = getString(R.string.multiplyStoneCutter2);
        description[2][1] = getString(R.string.multiplyStoneCutter5);
        description[2][2] = getString(R.string.multiplyStoneCutter2);
        description[2][3] = getString(R.string.multiplyStoneCutter2);
        description[2][4] = getString(R.string.multiplyStoneCutter5);
        description[2][5] = getString(R.string.multiplyStoneCutter2);
        description[2][6] = getString(R.string.multiplyStoneCutter5);
        description[2][7] = getString(R.string.multiplyStoneCutter5);

        description[3][0] = getString(R.string.multiplyBronzeForge2);
        description[3][1] = getString(R.string.multiplyBronzeForge5);
        description[3][2] = getString(R.string.multiplyBronzeForge2);
        description[3][3] = getString(R.string.multiplyBronzeForge2);
        description[3][4] = getString(R.string.multiplyBronzeForge5);
        description[3][5] = getString(R.string.multiplyBronzeForge2);
        description[3][6] = getString(R.string.multiplyBronzeForge5);
        description[3][7] = getString(R.string.multiplyBronzeForge5);

        description[4][0] = getString(R.string.multiplyIronForge2);
        description[4][1] = getString(R.string.multiplyIronForge5);
        description[4][2] = getString(R.string.multiplyIronForge2);
        description[4][3] = getString(R.string.multiplyIronForge2);
        description[4][4] = getString(R.string.multiplyIronForge5);
        description[4][5] = getString(R.string.multiplyIronForge2);
        description[4][6] = getString(R.string.multiplyIronForge5);
        description[4][7] = getString(R.string.multiplyIronForge5);

        description[5][0] = getString(R.string.multiplyCrucible2);
        description[5][1] = getString(R.string.multiplyCrucible5);
        description[5][2] = getString(R.string.multiplyCrucible2);
        description[5][3] = getString(R.string.multiplyCrucible2);
        description[5][4] = getString(R.string.multiplyCrucible5);
        description[5][5] = getString(R.string.multiplyCrucible2);
        description[5][6] = getString(R.string.multiplyCrucible5);
        description[5][7] = getString(R.string.multiplyCrucible5);

        description[6][0] = getString(R.string.multiplyVAF2);
        description[6][1] = getString(R.string.multiplyVAF5);
        description[6][2] = getString(R.string.multiplyVAF2);
        description[6][3] = getString(R.string.multiplyVAF2);
        description[6][4] = getString(R.string.multiplyVAF5);
        description[6][5] = getString(R.string.multiplyVAF2);
        description[6][6] = getString(R.string.multiplyVAF5);
        description[6][7] = getString(R.string.multiplyVAF5);

        description[7][0] = getString(R.string.multiplyNPP2);
        description[7][1] = getString(R.string.multiplyNPP5);
        description[7][2] = getString(R.string.multiplyNPP2);
        description[7][3] = getString(R.string.multiplyNPP2);
        description[7][4] = getString(R.string.multiplyNPP5);
        description[7][5] = getString(R.string.multiplyNPP2);
        description[7][6] = getString(R.string.multiplyNPP5);
        description[7][7] = getString(R.string.multiplyNPP5);


        description[8][0] = getString(R.string.multiplyDiamondGenerator2);
        description[8][1] = getString(R.string.multiplyDiamondGenerator5);
        description[8][2] = getString(R.string.multiplyDiamondGenerator2);
        description[8][3] = getString(R.string.multiplyDiamondGenerator2);
        description[8][4] = getString(R.string.multiplyDiamondGenerator5);
        description[8][5] = getString(R.string.multiplyDiamondGenerator2);
        description[8][6] = getString(R.string.multiplyDiamondGenerator5);
        description[8][7] = getString(R.string.multiplyDiamondGenerator5);

        description[9][0] = getString(R.string.multiplyMagicAltar2);
        description[9][1] = getString(R.string.multiplyMagicAltar5);
        description[9][2] = getString(R.string.multiplyMagicAltar2);
        description[9][3] = getString(R.string.multiplyMagicAltar2);
        description[9][4] = getString(R.string.multiplyMagicAltar5);
        description[9][5] = getString(R.string.multiplyMagicAltar2);
        description[9][6] = getString(R.string.multiplyMagicAltar5);
        description[9][7] = getString(R.string.multiplyMagicAltar5);
/*
        story[0][0] = getString(R.string.id1_1);
        story[0][1] = getString(R.string.NaN);
        story[0][2] = getString(R.string.NaN);
        story[0][3] = getString(R.string.NaN);
        story[0][4] = getString(R.string.NaN);
        story[0][5] = getString(R.string.NaN);
        story[0][6] = getString(R.string.NaN);
        story[0][7] = getString(R.string.NaN);

        story[1][0] = getString(R.string.NaN);
        story[1][1] = getString(R.string.NaN);
        story[1][2] = getString(R.string.NaN);
        story[1][3] = getString(R.string.NaN);
        story[1][4] = getString(R.string.NaN);
        story[1][5] = getString(R.string.NaN);
        story[1][6] = getString(R.string.NaN);
        story[1][7] = getString(R.string.NaN);

        story[2][0] = getString(R.string.NaN);
        story[2][1] = getString(R.string.NaN);
        story[2][2] = getString(R.string.NaN);
        story[2][3] = getString(R.string.NaN);
        story[2][4] = getString(R.string.NaN);
        story[2][5] = getString(R.string.NaN);
        story[2][6] = getString(R.string.NaN);
        story[2][7] = getString(R.string.NaN);

        story[3][0] = getString(R.string.NaN);
        story[3][1] = getString(R.string.NaN);
        story[3][2] = getString(R.string.NaN);
        story[3][3] = getString(R.string.NaN);
        story[3][4] = getString(R.string.NaN);
        story[3][5] = getString(R.string.NaN);
        story[3][6] = getString(R.string.NaN);
        story[3][7] = getString(R.string.NaN);

        story[4][0] = getString(R.string.NaN);
        story[4][1] = getString(R.string.NaN);
        story[4][2] = getString(R.string.NaN);
        story[4][3] = getString(R.string.NaN);
        story[4][4] = getString(R.string.NaN);
        story[4][5] = getString(R.string.NaN);
        story[4][6] = getString(R.string.NaN);
        story[4][7] = getString(R.string.NaN);

        story[5][0] = getString(R.string.NaN);
        story[5][1] = getString(R.string.NaN);
        story[5][2] = getString(R.string.NaN);
        story[5][3] = getString(R.string.NaN);
        story[5][4] = getString(R.string.NaN);
        story[5][5] = getString(R.string.NaN);
        story[5][6] = getString(R.string.NaN);
        story[5][7] = getString(R.string.NaN);

        story[6][0] = getString(R.string.NaN);
        story[6][1] = getString(R.string.NaN);
        story[6][2] = getString(R.string.NaN);
        story[6][3] = getString(R.string.NaN);
        story[6][4] = getString(R.string.NaN);
        story[6][5] = getString(R.string.NaN);
        story[6][6] = getString(R.string.NaN);
        story[6][7] = getString(R.string.NaN);

        story[7][0] = getString(R.string.NaN);
        story[7][1] = getString(R.string.NaN);
        story[7][2] = getString(R.string.NaN);
        story[7][3] = getString(R.string.NaN);
        story[7][4] = getString(R.string.NaN);
        story[7][5] = getString(R.string.NaN);
        story[7][6] = getString(R.string.NaN);
        story[7][7] = getString(R.string.NaN);

        story[8][0] = getString(R.string.NaN);
        story[8][1] = getString(R.string.NaN);
        story[8][2] = getString(R.string.NaN);
        story[8][3] = getString(R.string.NaN);
        story[8][4] = getString(R.string.NaN);
        story[8][5] = getString(R.string.NaN);
        story[8][6] = getString(R.string.NaN);
        story[8][7] = getString(R.string.NaN);

*/
    }
    private void loadButtons() {
        GridLayout purchaseableGrid = binding.purchaseableGrid;
        float dpValue = 60; // Размер в dp
        float scale = getResources().getDisplayMetrics().density;
        int pixelSize = (int) (dpValue * scale + 0.5f); // Преобразование в пиксели
        for (byte i = 0; i < imageIds.length; i++) {
            for (byte j = 0; j < imageIds[i].length; j++) {
                UpgradeArray[i][j] = new ImageButton(mContext); // Инициализируйте элемент
                UpgradeArray[i][j].setLayoutParams(new ViewGroup.LayoutParams(pixelSize, pixelSize));
                UpgradeArray[i][j].setPadding(8, 2, 0, 0);
                UpgradeArray[i][j].setBackground(getResources().getDrawable(R.drawable.imagebutton_shape));
                UpgradeArray[i][j].setScaleType(ImageView.ScaleType.FIT_CENTER);
                UpgradeArray[i][j].setImageResource(imageIds[i][j]);
                if (i == 0) {
                    if (((MainActivity) requireActivity()).upgradesMultiply[0][j] > 1 || ((MainActivity) requireActivity()).getKrikoin() > (((MainActivity) requireActivity()).upgradesCost[0][j] * 0.75F)) {
                        purchaseableGrid.addView(UpgradeArray[i][j]);
                    }
                } else {
                    if (((MainActivity) requireActivity()).isUpgradesCondition(i, j)) {
                        purchaseableGrid.addView(UpgradeArray[i][j]);
                    }
                }
            }
        }
    }

    //——————————————————————————————
    //Update data-like methods
    //——————————————————————————————
    private void updateCash() {
        CurrentKrikoinUpgrades.setText(((MainActivity) requireActivity()).getUpdateCashDisplayText());
    }
    private void KPS() {
        KrikoinPerSecondUpgrades.setText(((MainActivity) requireActivity()).getKPSDisplayText());
    }
    //——————————————————————————————
    //Set-like methods
    //——————————————————————————————
    private void setKriKoins() {
        UpgradesKrikoin.setImageResource(krikoinIds[(((MainActivity) requireActivity()).getChosenKrikoin())]);
        upgradesKrikoinPerSecond.setImageResource(krikoinIds[((MainActivity) requireActivity()).getChosenKrikoin()]);
    }
    //——————————————————————————————
    //Arithmetic-like methods
    //——————————————————————————————
    private void calculate() {
        ((MainActivity) requireActivity()).calculate();
        KPS();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Проверяем, изменилась ли ориентация экрана
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Если горизонтальная ориентация, здесь можно выполнить нужные действия
            ((MainActivity) requireActivity()).replaceFragment(new UpgradesFragment()); // Пересоздаем активность
        } else {
            ((MainActivity) requireActivity()).replaceFragment(new UpgradesFragment());
        }
    }
    private void load() {
        loadButtons();
        updateCash();
        if (handler == null) {
            handler = new Handler();
            handler.postDelayed(update, 200);
        } else {
            handler.removeCallbacks(update);
            handler.postDelayed(update, 200);
        }

        calculate();
        setKriKoins();
        checkPositionClicks();
        loadString();
        KPS();
    }
}

