package com.myKriClicker.kri_clicker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.myKriClicker.kri_clicker.databinding.ActivityRebornBinding;

import java.text.DecimalFormat;

public class RebornActivity  extends AppCompatActivity {
    private ImageButton[] rebornUpgrades = new ImageButton[9];
    private ImageButton exit;
    private ImageView line[] = new ImageView[8];
    private ImageView imagePopup;
    private TextView CurrentSpaceCoin;
    private View decorView, v;
    private ActivityRebornBinding binding;
    private PopupWindow popupWindow;
    private short checkThousandDivides = 0;
    int[] rebornUpgradeIds = {
            R.drawable.rebornid1,
            R.drawable.rebornida1,
            R.drawable.rebornida2,
            R.drawable.rebornida3,
            R.drawable.rebornida4,
            R.drawable.rebornidb1,
            R.drawable.rebornidb2,
            R.drawable.rebornidb3,
            R.drawable.rebornidb4
    };
    private int[] rebornPrice = {100, 350, 512, 950, 2048, 150, 250, 900, 1200};
    double spaceCoin = 0;
    boolean rebornUpgrade[] = {
            false, false, false,
            false, false, false,
            false, false, false
    };
    private final String[] description = new String[9];

    private final String[] numEnds = {"", "K", "M", "B", "T", "Qa", "Qi", "Sx", "Sp", "Oc", "No",
            "Dc", "UD", "DD", "TD", "QaD", "QiD", "SxD", "SpD", "OcD", "NoD",
            "Vg", "AVg", "DVg", "TVg", "QaVg", "QiVg", "SxVg", "SpVg", "OcVg", "NoVg",
            "Ti", "UTi", "DTI", "TTi", "QaTi", "QiTi", "SxTi", "SpTi", "OcTi", "NoTi",
            "Qag", "UQag", "DQag", "TQag", "QaQag", "QiQag", "SxQag", "SpQag", "OcQag", "NoQag",
            "Qig", "UQig", "DQig", "TQig", "QaQig", "QiQig", "SxQig", "SpQig", "OcQig", "NoQig",
            "Sxg", "USxg", "DSxg", "TSxg", "QaSxg", "QiSxg", "SxSxg", "SpSxg", "OcSxg", "NoSxg",
            "Spg", "USpg", "DSpg", "TSpg", "QaSpg", "QiSpg", "SxSpg", "SpSpg", "OcSpg", "NoSpg",
            "Ocg", "UOcg", "DOcg", "TOcg", "QaOcg", "QiOcg", "SxOcg", "SpOcg", "OcOcg", "NoOcg",
            "Nog", "UNog", "DNog", "TNog", "QaNog", "QiNog", "SxNog", "SpNog", "OcNog", "NoNog",
            "Centillion", "Centunillion", "e"};
    DecimalFormat decimalFormat = new DecimalFormat("#.#");
    private String spaceCoinString;
    private String displayText;
    StringBuffer formattedValue = new StringBuffer();
    private final String PREFS_NAME = "MyPrefs";
    private final String KEY_SPACE_COINS = "spaceCoins";
    private final String KEY_REBORN_UPGRADE[] = {
            "reborn_upgrade1", "reborn_upgrade2", "reborn_upgrade3",
            "reborn_upgrade4", "reborn_upgrade5", "reborn_upgrade6",
            "reborn_upgrade7", "reborn_upgrade8", "reborn_upgrade9"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplication().setTheme(R.style.AppTheme); // Используйте вашу тему здесь
        setTheme(R.style.AppTheme);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        // Устанавливаем флаги для полноэкранного режима
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });
        binding = ActivityRebornBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CurrentSpaceCoin = binding.CurrentSpaceCoin;
        exit = binding.exit;
        rebornUpgrades[0] = binding.rebornUpgrade1;
        rebornUpgrades[1] = binding.rebornUpgrade2;
        rebornUpgrades[2] = binding.rebornUpgrade3;
        rebornUpgrades[3] = binding.rebornUpgrade4;
        rebornUpgrades[4] = binding.rebornUpgrade5;
        rebornUpgrades[5] = binding.rebornUpgrade6;
        rebornUpgrades[6] = binding.rebornUpgrade7;
        rebornUpgrades[7] = binding.rebornUpgrade8;
        rebornUpgrades[8] = binding.rebornUpgrade9;

        line[0] = binding.line1;
        line[1] = binding.line2;
        line[2] = binding.line3;
        line[3] = binding.line4;
        line[4] = binding.line5;
        line[5] = binding.line6;
        line[6] = binding.line7;
        line[7] = binding.line8;

        load();

        rebornUpgrades[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v = view;
                calculate((byte)0);
            }
        });
        rebornUpgrades[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v = view;
                calculate((byte)1);
            }
        });
        rebornUpgrades[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rebornUpgrade[0]) {
                    v = view;
                    calculate((byte) 2);
                }
            }
        });
        rebornUpgrades[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rebornUpgrade[1]) {
                    v = view;
                    calculate((byte) 3);
                }
            }
        });
        rebornUpgrades[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rebornUpgrade[2]) {
                    v = view;
                    calculate((byte) 4);
                }
            }
        });
        rebornUpgrades[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rebornUpgrade[0]) {
                    v = view;
                    calculate((byte) 5);
                }
            }
        });
        rebornUpgrades[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rebornUpgrade[0]) {
                    v = view;
                    calculate((byte) 6);
                }
            }
        });
        rebornUpgrades[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rebornUpgrade[5]) {
                    v = view;
                    calculate((byte) 7);
                }
            }
        });
        rebornUpgrades[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rebornUpgrade[6]) {
                    v = view;
                    calculate((byte) 8);
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                navigateToAnotherActivity();
            }
        });
    }
    private void calculate(byte i) {
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
        imagePopup.setImageResource(rebornUpgradeIds[i]);
        descriptionPopup.setText(description[i]);
//        storyPopup.setText(story[i][j]);
        upgradePopup.setText(getString(R.string.price) + rebornPrice[i]);
        purchasePopup.setVisibility((!rebornUpgrade[i]) ? View.VISIBLE : View.GONE);

        closePopup.setOnClickListener(view -> popupWindow.dismiss());
        purchasePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spaceCoin >= rebornPrice[i] && !rebornUpgrade[i]) {
                    spaceCoin -= rebornPrice[i];
                    rebornUpgrade[i] = true;
                    CurrentSpaceCoin.setText(updateCash(spaceCoin));
                    removeRebornShadow();
                    saveData();
                    setPopupTextAndColor(upgradePopup, getString(R.string.purchased), "#19BE00");
                } else if (!rebornUpgrade[i]) {
                    upgradePopup.setText(getString(R.string.insufficientlyFunds));
                    upgradePopup.setTextColor(Color.parseColor("#EA0000"));
                } else {
                    upgradePopup.setText(getString(R.string.alreadyPurhaced));
                    upgradePopup.setTextColor(Color.parseColor("#EA0000"));
                }
            }
        });
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
    }

    private String updateCash(double valueToFormat) {
        checkThousandDivides = 0;

        formattedValue.setLength(16);

        while (valueToFormat > 1000) {
            valueToFormat /= 1000;
            checkThousandDivides++;
        }
        formattedValue.append(decimalFormat.format(valueToFormat));

        displayText = formattedValue.append(numEnds[checkThousandDivides]).toString();


        return displayText;
    }
    private void loadString () {
        description[0] = getString(R.string.reborn1);
        description[1] = getString(R.string.reborn2);
        description[2] = getString(R.string.reborn3);
        description[3] = getString(R.string.reborn4);
        description[4] = getString(R.string.reborn5);
        description[5] = getString(R.string.reborn6);
        description[6] = getString(R.string.reborn7);
        description[7] = getString(R.string.reborn8);
        description[8] = getString(R.string.reborn9);
    }
    private void loadSave() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        spaceCoinString = sharedPreferences.getString(KEY_SPACE_COINS, "0.0");
        spaceCoin = Double.parseDouble(spaceCoinString);
        for (byte i = 0; i < KEY_REBORN_UPGRADE.length; i++) {
            rebornUpgrade[i] = sharedPreferences.getBoolean(KEY_REBORN_UPGRADE[i], false);
        }
    }
    private void navigateToAnotherActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void saveData() {
        spaceCoinString = Double.toString(spaceCoin);
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SPACE_COINS, spaceCoinString);
        for (byte i = 0; i < KEY_REBORN_UPGRADE.length; i++) {
            editor.putBoolean(KEY_REBORN_UPGRADE[i], rebornUpgrade[i]);
        }
        editor.apply();
    }
    private void load() {
        loadSave();
        loadString();
        removeRebornShadow();
        CurrentSpaceCoin.setText(updateCash(spaceCoin));
    }
    private void removeRebornShadow() {
        for (byte i = 0; i < rebornUpgrades.length; i++) {
            if (rebornUpgrade[i]) {
                rebornUpgrades[i].setImageResource(0);
            }
        }
        if (rebornUpgrade[0]) {
            line[0].setImageResource(0);
            line[1].setImageResource(0);
            line[2].setImageResource(0);
            line[3].setImageResource(0);
        }
        if (rebornUpgrade[1]) {
            line[4].setImageResource(0);
        }
        if (rebornUpgrade[2]) {
            line[5].setImageResource(0);
        }
        if (rebornUpgrade[5]) {
            line[6].setImageResource(0);
        }
        if (rebornUpgrade[6]) {
            line[7].setImageResource(0);
        }
    }
    private void setPopupTextAndColor(TextView textView, String text, String color) {
        textView.setText(text);
        textView.setTextColor(Color.parseColor(color));
    }
    private PopupWindow createPopupWindow(View popupView) {
        return new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );
    }
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }
    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    }
}
