package com.myKriClicker.kri_clicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.myKriClicker.kri_clicker.databinding.ActivityMainBinding;
import com.myKriClicker.kri_clicker.ui.Achievements.AchievementsFragment;
import com.myKriClicker.kri_clicker.ui.Home.HomeFragment;
import com.myKriClicker.kri_clicker.ui.Production.ProductionFragment;
import com.myKriClicker.kri_clicker.ui.Settings.SettingsFragment;
import com.myKriClicker.kri_clicker.ui.Upgrades.UpgradesFragment;

import java.text.DecimalFormat;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ImageButton settingsButton, upgradeButton, homeButton, productionButton, achievementsButton, rebornButton;
    private ActivityMainBinding binding;
    private ConstraintLayout container;
    private ImageView iconAnimatedView, animatedView, imagePopup;
    private TextView textAnimatedView;
    private View decorView, v;
    private PopupWindow popupWindow;
    private int[][] chosenRebornUpgrades = {
            {-1, -1, -1, -1},
            {-1, -1, -1, -1},
            {-1, -1, -1, -1},
            {-1, -1, -1, -1},
            {-1, -1, -1, -1},
            {-1, -1, -1, -1},
            {-1, -1, -1, -1},
            {-1, -1, -1, -1},
            {-1, -1, -1, -1},
            {-1, -1, -1, -1}
    };
    private short checkThousandDivides = 0;
    int[] backgroundIds = {
            R.drawable.backgroundid0,
            R.drawable.backgroundid1,
            R.drawable.backgroundid2,
            R.drawable.backgroundid3,
            R.drawable.backgroundid4,
            R.drawable.backgroundid5,
            R.drawable.backgroundid6,
            R.drawable.backgroundid7,
            R.drawable.backgroundid8,
            R.drawable.backgroundid9,
            R.drawable.backgroundid10,
            R.drawable.backgroundid11,
            R.drawable.backgroundid12,
            R.drawable.backgroundid13
    };
    private boolean[] targetAchieved = new boolean[22];
    boolean rebornUpgrade[] = {
            false, false, false,
            false, false, false,
            false, false, false
    };
    private boolean hasReborn = false;
    private boolean isNormalNotation = true;
    private boolean isFill = false;
    public boolean multiply5 = false;
    public boolean multiply100 = false;
    public boolean multiplymax = false;
    private double[] totalUpgradesMultiply = {1, 1, 1, 1, 1, 1, 1, 1, 1};
    public int[][] upgradesMultiply =
            {{1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1}};
    public int chosenBackground = 0;
    private int chosenKrikoin = 0;
    private int[] chosenIcon = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int SAVE_INTERVAL = 6000; // Интервал сохранения (1 минута)
    private static long MAX_AFK_TIME_IN_MILLIS = 2 * 60 * 60 * 1000; // 2 часа в миллисекундах
    private long lastExitTime;
    private long clickCount = 0;
    private long timeCount = 0;
    private final long[] clickCondition = {1500, 2500, 5000, 15000, 25000, 40000, 75000};
    private final long[] timeCondition = {3000, 18000, 54000, 108000, 432000, 1246000};
    public float[] amountUpgrade = {0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F};
    private float[] upgradeEarn = {0.04F, 0.5F, 2F, 10F, 55F, 250F, 1800F, 13150F, 49000F};
    private float[] clickAchievementMultiplyExample = {1.0025F, 1.00275F, 1.003F, 1.00315F, 1.00334F, 1.0035F, 1.004F};
    private float[] clickTimeAchievementMultiplyExample = {1.1F, 1.125F, 1.15F, 1.2F, 1.25F, 1.3F};
    private float[] productionTimeAchievementMupltiplyExample = {1.025F, 1.015F, 1.0125F, 1.0115F, 1.011F, 1.01F};
    private float[] clickAchievementMultiply = {1F, 1F, 1F, 1F, 1F, 1F, 1F};
    private float[] clickTimeAchievementMultiply = {1F, 1F, 1F, 1F, 1F, 1F};
    private float[] productionTimeAchievementMultiply = {1F, 1F, 1F, 1F, 1F, 1F};
    private float[] productionTimeAchievementMultiplyx10 = {1.28F, 1.16F, 3.46F, 1.12F, 1.116F, 1.105F};
    private float[] productionTimeAchievementMultiplyx100 = {11.8F, 4.4F, 3.46F, 3.14F, 2.98F, 2.7F};
    public double[][] upgradesCost = {{100.0, 500.0, 12_450.0, 76_500.0, 8_750_000.0, 75_000_000.0, 1_250_000_000.0, 5_000_000_000.0},
            {500.0, 10_000.0, 150_000.0, 7_500_000.0, 2_450_000_000.0, 125_000_000_000.0, 2_500_000_000_000.0, 75_000_000_000_000_000.0},
            {3_000.0, 65_000.0, 850_000.0, 60_000_000.0, 54_000_000_000.0, 750_000_000_000.0, 270_000_000_000_000.0, 450_000_000_000_000_000.0},
            {12_000.0, 144_000.0, 2_400_000.0, 480_000_000.0, 240_000_000_000.0, 12_000_000_000_000.0, 1_250_000_000_000_000.0, 3_600_000_000_000_000_000.0},
            {72_000.0, 1_200_000.0, 30_000_000.0, 1_500_000_000.0, 600_000_000_000.0, 36_000_000_000_000.0, 3_750_000_000_000_000.0, 90_000_000_000_000_000_000.0},
            {500_000.0, 12_000_000.0, 300_000_000.0, 24_000_000_000.0, 2_400_000_000_000.0, 375_000_000_000_000.0, 108_000_000_000_000_000.0, 225_000_000_000_000_000_000.0},
            {3_000_000.0, 60_000_000.0, 1_500_000_000.0, 120_000_000_000.0, 24_000_000_000_000.0, 1_875_000_000_000_000.0, 540_000_000_000_000_000.0, 1_080_000_000_000_000_000_000.0},
            {18_000_000.0, 240_000_000.0, 6_000_000_000.0, 600_000_000_000.0, 240_000_000_000_000.0, 22_500_000_000_000_000.0, 6_480_000_000_000_000.0, 14_400_000_000_000_000_000.0},
            {108_000_000.0, 2_400_000_000.0, 60_000_000_000.0, 7_200_000_000_000.0, 1_800_000_000_000_000.0, 337_500_000_000_000_000.0, 97_200_000_000_000_000_000.0, 194_400_000_000_000_000_000.0},
            {648_000_000.0, 14_400_000_000.0, 360_000_000_000.0, 43_200_000_000_000.0, 7_200_000_000_000_000.0, 1_687_500_000_000_000_000.0, 486_000_000_000_000_000.0, 972_000_000_000_000_000_000.0},
            {3_888_000_000.0, 72_000_000_000.0, 1_800_000_000_000.0, 216_000_000_000_000.0, 43_200_000_000_000_000.0, 8_437_500_000_000_000_000.0, 2_430_000_000_000_000_000.0, 4_860_000_000_000_000_000_000.0}};
    public double[] condition = {1.0, 5.0, 20.0, 40.0, 100.0, 150.0, 200.0, 250.0};
    private float increment = 1.125F;
    private double spaceCoin = 0;
    private double krikoin = 0;
    public double totalAmount = 0;
    private double[] powerOfAmount = new double[9];
    private double clickTotalMultiplier = 1;
    private double[] costUpgrade = {10, 150, 1750, 13000, 180000, 1250000, 24500000, 500000000, 1000000000};
    private double[] costPrimary = {10, 150, 1750, 13000, 180000, 1250000, 24500000, 500000000, 1000000000};

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

    private String[] formattedValueAmount = new String[9];
    private String coinsString;
    private String spaceCoinString;
    private String displayText;
    private String description1;
    private String description2;
    private String description3;
    private String description[] = new String[2];
    StringBuffer formattedValue = new StringBuffer();
    private String[] upgradeString = new String[9];

    private final String PREFS_NAME = "MyPrefs";
    private final String KEY_CLICKCOUNT = "clickCount";
    private final String KEY_TIMECOUNT = "timeCount";
    private final String KEY_COINS = "coins";
    private final String KEY_SPACE_COINS = "spaceCoins";
    private static final String LAST_EXIT_TIME_KEY = "lastExitTime";
    private final String KEY_CHOSENKOIN = "chosenKoin";
    private final String KEY_CHOSENBACKGROUND = "chosenBackground";
    private final String KEY_CHOSENNOTATION = "isNormalNotation";
    private final String KEY_ISFILL = "isFill";
    private final String KEY_REBORNED = "hasReborned";
    private final String[] KEY_CHOSENICON = {"chosenIcon1", "chosenIcon2", "chosenIcon3",
            "chosenIcon4", "chosenIcon5", "chosenIcon6",
            "chosenIcon7", "chosenIcon8", "chosenIcon9"};
    private final String[] KEY_UPGRADENUM = {
            "amountUpgrade1", "amountUpgrade2", "amountUpgrade3",
            "amountUpgrade4", "amountUpgrade5", "amountUpgrade6",
            "amountUpgrade7", "amountUpgrade8", "amountUpgrade9",
    };
    private final String[] KEY_TARGETACHIEVED = {
            "target1", "target2", "target3",
            "target4", "target5", "target6",
            "target7", "target8", "target9",
            "target10", "target11", "target12",
            "target13", "target14", "target15",
            "target16", "target17", "target18",
            "target19", "target20", "target21",
            "target22"
    };
    private final String KEY_REBORN_UPGRADE[] = {
            "reborn_upgrade1", "reborn_upgrade2", "reborn_upgrade3",
            "reborn_upgrade4", "reborn_upgrade5", "reborn_upgrade6",
            "reborn_upgrade7", "reborn_upgrade8", "reborn_upgrade9"
    };
    private final String[][] KEY_UPGRADESMULTIPLY = {
            {"clickUpgrade1", "clickUpgrade2", "clickUpgrade3", "clickUpgrade4",
                    "clickUpgrade5", "clickUpgrade6", "clickUpgrade7", "clickUpgrade8"},

            {"chiselUpgrade1", "chiselUpgrade2", "chiselUpgrade3", "chiselUpgrade4",
                    "chiselUpgrade5", "chiselUpgrade6", "chiselUpgrade7", "chiselUpgrade8"},

            {"stoneCutterUpgrade1", "stoneCutterUpgrade2", "stoneCutterUpgrade3", "stoneCutterUpgrade4",
                    "stoneCutterUpgrade5", "stoneCutterUpgrade6", "stoneCutterUpgrade7", "stoneCutterUpgrade8"},

            {"bronzeForgeUpgrade1", "bronzeForgeUpgrade2", "bronzeForgeUpgrade3", "bronzeForgeUpgrade4",
                    "bronzeForgeUpgrade5", "bronzeForgeUpgrade6", "bronzeForgeUpgrade7", "bronzeForgeUpgrade8"},

            {"ironForgeUpgrade1", "ironForgeUpgrade2", "ironForgeUpgrade3", "ironForgeUpgrade4",
                    "ironForgeUpgrade5", "ironForgeUpgrade6", "ironForgeUpgrade7", "ironForgeUpgrade8"},

            {"crucibleUpgrade1", "crucibleUpgrade2", "crucibleUpgrade3", "crucibleUpgrade4",
                    "crucibleUpgrade5", "crucibleUpgrade6", "crucibleUpgrade7", "crucibleUpgrade8"},

            {"VAFUpgrade1", "VAFUpgrade2", "VAFUpgrade3", "VAFUpgrade4",
                    "VAFUpgrade5", "VAFUpgrade6", "VAFUpgrade7", "VAFUpgrade8"},

            {"NPPUpgrade1", "NPPUpgrade2", "NPPUpgrade3", "NPPUpgrade4",
                    "NPPUpgrade5", "NPPUpgrade6", "NPPUpgrade7", "NPPUpgrade8"},

            {"diamondGeneratorUpgrade1", "diamondGenerator2", "diamondGeneratorUpgrade3", "diamondGeneratorUpgrade4",
                    "diamondGeneratorUpgrade5", "diamondGeneratorUpgrade6", "diamondGeneratorUpgrade7", "diamondGeneratorUpgrade8"},

            {"magicAltarUpgrade1", "magicAltarUpgrade2", "magicAltarUpgrade3", "magicAltarUpgrade4",
                    "magicAltarUpgrade5", "magicAltarUpgrade6", "magicAltarUpgrade7", "magicAltarUpgrade8"}
    };
    private final String KEY_CHOSENREBORNUPGRADE[][] = {

            {
                    "chosenrebornupgrade1", "chosenrebornupgrade2", "chosenrebornupgrade3", "chosenrebornupgrade4",
                    "chosenrebornupgrade5", "chosenrebornupgrade6", "chosenrebornupgrade7", "chosenrebornupgrade8"
            },
            {
                    "chosenrebornupgrade1a", "chosenrebornupgrade2a", "chosenrebornupgrade3a", "chosenrebornupgrade4a",
                    "chosenrebornupgrade5a", "chosenrebornupgrade6a", "chosenrebornupgrade7a", "chosenrebornupgrade8a"
            },
            {
                    "chosenrebornupgrade1b", "chosenrebornupgrade2b", "chosenrebornupgrade3b", "chosenrebornupgrade4b",
                    "chosenrebornupgrade5b", "chosenrebornupgrade6b", "chosenrebornupgrade7b", "chosenrebornupgrade8b"
            },
            {
                    "chosenrebornupgrade1c", "chosenrebornupgrade2c", "chosenrebornupgrade3c", "chosenrebornupgrade4c",
                    "chosenrebornupgrade5c", "chosenrebornupgrade6c", "chosenrebornupgrade7c", "chosenrebornupgrade8c"
            },
            {
                    "chosenrebornupgrade1d", "chosenrebornupgrade2d", "chosenrebornupgrade3d", "chosenrebornupgrade4d",
                    "chosenrebornupgrade5d", "chosenrebornupgrade6d", "chosenrebornupgrade7d", "chosenrebornupgrade8d"
            },
            {
                    "chosenrebornupgrade1e", "chosenrebornupgrade2e", "chosenrebornupgrade3e", "chosenrebornupgrade4e",
                    "chosenrebornupgrade5e", "chosenrebornupgrade6e", "chosenrebornupgrade7e", "chosenrebornupgrade8e"
            },
            {
                    "chosenrebornupgrade1f", "chosenrebornupgrade2f", "chosenrebornupgrade3f", "chosenrebornupgrade4f",
                    "chosenrebornupgrade5f", "chosenrebornupgrade6f", "chosenrebornupgrade7f", "chosenrebornupgrade8f"
            },
            {
                    "chosenrebornupgrade1g", "chosenrebornupgrade2g", "chosenrebornupgrade3g", "chosenrebornupgrade4g",
                    "chosenrebornupgrade5g", "chosenrebornupgrade6g", "chosenrebornupgrade7g", "chosenrebornupgrade8g"
            },
            {
                    "chosenrebornupgrade1h", "chosenrebornupgrade2h", "chosenrebornupgrade3h", "chosenrebornupgrade4h",
                    "chosenrebornupgrade5h", "chosenrebornupgrade6h", "chosenrebornupgrade7h", "chosenrebornupgrade8h"
            },
            {
                    "chosenrebornupgrade1j", "chosenrebornupgrade2j", "chosenrebornupgrade3j", "chosenrebornupgrade4j",
                    "chosenrebornupgrade5j", "chosenrebornupgrade6j", "chosenrebornupgrade7j", "chosenrebornupgrade8j"
            }
    };
    private final String[] KEY_UPGRADE =
            {
                    "upgrade1", "upgrade2", "upgrade3",
                    "upgrade4", "upgrade5", "upgrade6",
                    "upgrade7", "upgrade8", "upgrade9", };
    private final String[] KEY_CLICKACHIEVEMENT = {
            "clickAchievement1", "clickAchievement2", "clickAchievement3",
            "clickAchievement4", "clickAchievement5", "clickAchievement6",
            "clickAchievement7"
    };
    private final String[] KEY_CLICKTIMEACHIEVEMENT = {
            "clickTimeAchievement1", "clickTimeAchievement2", "clickTimeAchievement3",
            "clickTimeAchievement4", "clickTimeAchievement5", "clickTimeAchievement6"
    };
    private final String[] KEY_PRODUCTIONTIMEACHIEVEMENT = {
            "productionTimeAchievement1", "productionTimeAchievement2", "productionTimeAchievement3",
            "productionTimeAchievement4", "productionTimeAchievement5", "productionTimeAchievement6"
    };
    DecimalFormat decimalFormat = new DecimalFormat("#.#");
    private Runnable saveRunnable;
    private Handler handler, saveHandler;

    private Runnable update = new Runnable() {
        @Override
        public void run() {
            krikoin += totalAmount;
            timeCount++;
            // Увеличение валюты.
            updateCash(krikoin);
            // Установка значения для проверки
            handler.postDelayed(this, 200); // Запуск обновления каждую 0,2 секунду
        }
    };
    private Runnable Update = new Runnable() {
        @Override
        public void run() {
            checkIfAchieved();
            handler.postDelayed(this, 1000);
        }
    };
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplication().setTheme(R.style.AppTheme); // Используйте вашу тему здесь
        setTheme(R.style.AppTheme);
        // setContentView(R.layout.activity_main);
        // Скрываем заголовок активности
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        // Устанавливаем флаги для полноэкранного режима
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // Скрываем системные элементы, такие как панель навигации (если необходимо)
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        settingsButton = binding.SettingsButton;
        upgradeButton = binding.UpgradesButton;
        homeButton = binding.HomeButton;
        productionButton = binding.ProductionButton;
        achievementsButton = binding.AchievementsButton;
        rebornButton = binding.RebornButton;
        container = binding.container;
        animatedView = binding.animatedView;
        textAnimatedView = binding.textAnimatedView;
        iconAnimatedView = binding.iconAnimatedView;
        animatedView.setVisibility(View.GONE);
        textAnimatedView.setVisibility(View.GONE);
        iconAnimatedView.setVisibility(View.GONE);
        load();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Ваш код, который нужно выполнить с задержкой
                calculatePopupAFK();
            }
        }, 1000); // Задержка на 1 секунду

























        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new SettingsFragment());
                removeActiveButton();
                settingsButton.setBackground(getResources().getDrawable(R.drawable.tab_button_active));
            }
        });
        upgradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new UpgradesFragment());
                removeActiveButton();
                upgradeButton.setBackground(getResources().getDrawable(R.drawable.tab_button_active));
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new HomeFragment());
                removeActiveButton();
                homeButton.setBackground(getResources().getDrawable(R.drawable.tab_button_active));
            }
        });
        productionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ProductionFragment());
                removeActiveButton();
                productionButton.setBackground(getResources().getDrawable(R.drawable.tab_button_active));
            }
        });
        achievementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AchievementsFragment());
                removeActiveButton();
                achievementsButton.setBackground(getResources().getDrawable(R.drawable.tab_button_active));
            }
        });
        rebornButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                removeActiveButton();
                rebornButton.setBackground(getResources().getDrawable(R.drawable.tab_button_active));
                v = view;
                calculatePopup();
            }
        });
    }
    private void navigateToAnotherActivity() {
        Intent intent = new Intent(this, RebornActivity.class);
        startActivity(intent);
        finish();
    }















    //——————————————————————————————
    // on-like methods
    //——————————————————————————————
    @Override
    public void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacks(update);
            handler.removeCallbacks(Update);
        }
        saveData(); // Вызываем метод сохранения данных
    }
    @Override
    public void onResume() {
        super.onResume();
        // Возобновление обновления
        if (handler == null) {
            handler = new Handler();
            handler.postDelayed(update, 200);
            handler.postDelayed(Update, 1000);
        } else {
            handler.removeCallbacks(update);
            handler.removeCallbacks(Update);
            handler.postDelayed(update, 200);
            handler.postDelayed(Update, 1000);
        }
        if (saveHandler == null) {
            saveHandler = new Handler();
            saveHandler.postDelayed(saveRunnable, SAVE_INTERVAL);
        } else {
            saveHandler.removeCallbacks(saveRunnable);
            saveHandler.postDelayed(saveRunnable, SAVE_INTERVAL);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Сохранение данных при выходе из приложения
        saveData();
        // Остановка обновления при уничтожении активности
        if (handler != null) {
            handler.removeCallbacks(update);
            handler.removeCallbacks(Update);
        }
        // Остановка сохранения
        if (saveHandler != null) {
            saveHandler.removeCallbacks(saveRunnable);
        }
    }
    //——————————————————————————————
    //Data-like methods
    //——————————————————————————————
    private void saveData() {
        // Сохранение данных в SharedPreferences
        coinsString = Double.toString(krikoin); // Извлекаем значение в виде строки
        spaceCoinString = Double.toString(spaceCoin);
        for (byte i = 0; i < upgradeString.length; i++) {
            upgradeString[i] = Double.toString(costUpgrade[i]);
        }
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //—————————————————————————————————
        editor.putString(KEY_COINS, coinsString); // Преобразуем double в строку перед сохранением
        editor.putString(KEY_SPACE_COINS, spaceCoinString);
        editor.putLong(KEY_CLICKCOUNT, clickCount);
        editor.putLong(KEY_TIMECOUNT, timeCount);
        editor.putLong(LAST_EXIT_TIME_KEY, System.currentTimeMillis());
        //—————————————————————————————————

        //—————————————————————————————————
        editor.putInt(KEY_CHOSENBACKGROUND, chosenBackground);
        editor.putInt(KEY_CHOSENKOIN, chosenKrikoin);
        editor.putBoolean(KEY_CHOSENNOTATION, isNormalNotation);
        editor.putBoolean(KEY_REBORNED, hasReborn);
        //—————————————————————————————————

        //—————————————————————————————————
        for (byte i = 0; i < targetAchieved.length; i++) {
            editor.putBoolean(KEY_TARGETACHIEVED[i], targetAchieved[i]);
        }
        for (byte i = 0; i < KEY_REBORN_UPGRADE.length; i++) {
            editor.putBoolean(KEY_REBORN_UPGRADE[i], rebornUpgrade[i]);
        }
        for (byte i = 0; i < KEY_CHOSENREBORNUPGRADE.length; i++) {
            for (byte j = 0; i < KEY_CHOSENREBORNUPGRADE[i].length; i++) {
                editor.putInt(KEY_CHOSENREBORNUPGRADE[i][j], chosenRebornUpgrades[i][j]);
            }
        }
        //—————————————————————————————————

        //—————————————————————————————————
        for (byte i = 0; i < chosenIcon.length; i++) {
            editor.putInt(KEY_CHOSENICON[i], chosenIcon[i]);
        }
        //—————————————————————————————————

        //—————————————————————————————————
        for (byte i = 0; i < KEY_UPGRADESMULTIPLY.length; i++) {
            for (byte j = 0; j < KEY_UPGRADESMULTIPLY[i].length; j++) {
                editor.putInt(KEY_UPGRADESMULTIPLY[i][j], upgradesMultiply[i][j]);
            }
        }
        for (byte i = 0; i < upgradeString.length; i++) {
            editor.putString(KEY_UPGRADE[i], upgradeString[i]);
            editor.putFloat(KEY_UPGRADENUM[i], amountUpgrade[i]);
        }
        //—————————————————————————————————

        //—————————————————————————————————
        for (byte i = 0; i < clickAchievementMultiply.length; i++) {
            editor.putFloat(KEY_CLICKACHIEVEMENT[i], clickAchievementMultiply[i]);
        }
        for (byte i = 0; i < productionTimeAchievementMultiply.length; i++) {
            editor.putFloat(KEY_CLICKTIMEACHIEVEMENT[i], clickTimeAchievementMultiply[i]);
            editor.putFloat(KEY_PRODUCTIONTIMEACHIEVEMENT[i], productionTimeAchievementMultiply[i]);
        }
        //—————————————————————————————————

        editor.apply();
    }

    private void loadSave() {
        // Инициализация SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        clickCount = sharedPreferences.getLong(KEY_CLICKCOUNT, 0);
        timeCount = sharedPreferences.getLong(KEY_TIMECOUNT, 0);
        lastExitTime = sharedPreferences.getLong(LAST_EXIT_TIME_KEY, System.currentTimeMillis());
        coinsString = sharedPreferences.getString(KEY_COINS, "0.0");
        spaceCoinString = sharedPreferences.getString(KEY_SPACE_COINS, "0.0");
        chosenBackground = sharedPreferences.getInt(KEY_CHOSENBACKGROUND, 0);
        chosenKrikoin = sharedPreferences.getInt(KEY_CHOSENKOIN, 0);
        isNormalNotation = sharedPreferences.getBoolean(KEY_CHOSENNOTATION, true);
        isFill = sharedPreferences.getBoolean(KEY_ISFILL, false);
        hasReborn = sharedPreferences.getBoolean(KEY_REBORNED, false);

        spaceCoin = Double.parseDouble(spaceCoinString);
        krikoin = Double.parseDouble(coinsString); // Преобразуем строку в double
        for (byte i = 0; i < chosenIcon.length; i++) {
            chosenIcon[i] = sharedPreferences.getInt(KEY_CHOSENICON[i], 0);
        }
        for (byte i = 0; i < KEY_UPGRADESMULTIPLY.length; i++) {
            for (byte j = 0; j < KEY_UPGRADESMULTIPLY[i].length; j++) {
                upgradesMultiply[i][j] = sharedPreferences.getInt(KEY_UPGRADESMULTIPLY[i][j], 1);
            }
        }
        for (byte i = 0; i < KEY_CHOSENREBORNUPGRADE.length; i++) {
            for (byte j = 0; i < KEY_CHOSENREBORNUPGRADE[i].length; i++) {
                chosenRebornUpgrades[i][j] = sharedPreferences.getInt(KEY_CHOSENREBORNUPGRADE[i][j], 1);
            }
        }
        for (byte i = 0; i < upgradeString.length; i++) {
            upgradeString[i] = sharedPreferences.getString(KEY_UPGRADE[i], "0");
            amountUpgrade[i] = sharedPreferences.getFloat(KEY_UPGRADENUM[i], 0);
            costUpgrade[i] = Double.parseDouble(upgradeString[i]);
            powerOfAmount[i] = Math.pow(increment, amountUpgrade[i]);
        }
        for (byte i = 0; i < clickTimeAchievementMultiply.length; i++) {
            clickTimeAchievementMultiply[i] = sharedPreferences.getFloat(KEY_CLICKTIMEACHIEVEMENT[i], 1);
            productionTimeAchievementMultiply[i] = sharedPreferences.getFloat(KEY_PRODUCTIONTIMEACHIEVEMENT[i], 1);
        }
        for (byte i = 0; i < clickAchievementMultiply.length; i++) {
            clickAchievementMultiply[i] = sharedPreferences.getFloat(KEY_CLICKACHIEVEMENT[i], 1);
        }
        for (byte i = 0; i < targetAchieved.length; i++) {
            targetAchieved[i] = sharedPreferences.getBoolean(KEY_TARGETACHIEVED[i], false);
        }
        for (byte i = 0; i < KEY_REBORN_UPGRADE.length; i++) {
            rebornUpgrade[i] = sharedPreferences.getBoolean(KEY_REBORN_UPGRADE[i], false);
        }
    }
    private void increaseCurrencyBasedOnTime() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastExitTime;
        setAfk();
        // Ограничиваем время до MAX_AFK_TIME_IN_MILLIS
        if (elapsedTime > 0) {
            elapsedTime = Math.min(elapsedTime, MAX_AFK_TIME_IN_MILLIS);
        } else {
            elapsedTime = 0;
        }
        if (rebornUpgrade[3]) {
            elapsedTime *= 0.75;
        } else if (rebornUpgrade[1]) {
            elapsedTime *= 0.5;
        } else {
            elapsedTime *= 0.25;
        }
        krikoin += (totalAmount * 5) * (elapsedTime / 1000);
    }

    //——————————————————————————————
    //Update data-like methods
    //——————————————————————————————
    private String updateCash(double valueToFormat) {
        checkThousandDivides = 0;

        // Используем StringBuffer для эффективной конкатенации строк
        formattedValue.setLength(0);
        // Пока valueToFormat больше 1000 и kriKoins больше 9999, делим valueToFormat на 1000
        if (isNormalNotation) {
            while (valueToFormat > 1000) {
                valueToFormat /= 1000;
                checkThousandDivides++;
            }
        } else {
            while (valueToFormat > 10) {
                valueToFormat /= 10;
                checkThousandDivides++;
            }
        }

        // Форматируем значение kriKoins в строку с заданным числом знаков после запятой
        formattedValue.append(decimalFormat.format(valueToFormat));

        // Формируем строку для отображения
        displayText = (!isNormalNotation) ?
                formattedValue.append(numEnds[103]).append(checkThousandDivides).toString() :
                formattedValue.append(numEnds[checkThousandDivides]).toString();

        // Устанавливаем текст в соответствующий элемент

        return displayText;
    }
    private String KPS(double valueToFormat) {
        StringBuffer formattedValue = new StringBuffer();
        checkThousandDivides = 0;
        valueToFormat *= 5;
        if (isNormalNotation) {
            while (valueToFormat > 1000) {
                valueToFormat /= 1000;
                checkThousandDivides++;
            }
        } else {
            while (valueToFormat > 10) {
                valueToFormat /= 10;
                checkThousandDivides++;
            }
        }
        formattedValue.append(decimalFormat.format(valueToFormat));
        displayText = (!isNormalNotation) ?
                formattedValue.append(numEnds[103]).append(checkThousandDivides).toString() :
                formattedValue.append(numEnds[checkThousandDivides]).toString();
        displayText = displayText + "/s";
        return displayText;
    }
    //——————————————————————————————
    //Set-like methods
    //——————————————————————————————
    public void setBackground() {
        Bitmap bitmap;
        if (chosenBackground < backgroundIds.length) {
            bitmap = BitmapFactory.decodeResource(getResources(), backgroundIds[chosenBackground]);
        } else {
            chosenBackground = 0;
            bitmap = BitmapFactory.decodeResource(getResources(), backgroundIds[chosenBackground]);
        }
        // Создаем BitmapDrawable с использованием нашего изображения
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);

        // Устанавливаем TileMode
        if (!isFill) {
            bitmapDrawable.setTileModeXY(android.graphics.Shader.TileMode.REPEAT, android.graphics.Shader.TileMode.REPEAT);
        } else {
            bitmapDrawable.setGravity(Gravity.FILL);
        }

        // Устанавливаем фон в настройках активности
        container.setBackground(bitmapDrawable);
    }

    public String[] amountUpgradeSetText() {
        for (byte i = 0; i < formattedValueAmount.length; i++) {
            formattedValueAmount[i] = decimalFormat.format(amountUpgrade[i]);
        }
        return formattedValueAmount;
    }
    public String[] UpgradeCostSetText() {
        String[] formattedValuePrice = new String[costUpgrade.length];
        checkThousandDivides = 0;
        for (byte i = 0; i < costUpgrade.length; i++) {
            powerOfAmount[i] = Math.pow(increment, amountUpgrade[i]);
            costUpgrade[i] = costPrimary[i] * powerOfAmount[i];
            double Tool = costUpgrade[i];
            checkThousandDivides = 0;
            if (isNormalNotation) {
                while (Tool > 1000) {
                    Tool /= 1000;
                    checkThousandDivides++;
                }
            } else {
                while (Tool > 10) {
                    Tool /= 10;
                    checkThousandDivides++;
                }
            }
            if (isNormalNotation) {
                formattedValuePrice[i] = decimalFormat.format(Tool) + numEnds[checkThousandDivides];
            } else {
                formattedValuePrice[i] = decimalFormat.format(Tool) + numEnds[103] + checkThousandDivides;
            }
        }
        return formattedValuePrice;
    }
    public String[] DescriptionSetText() {
        double descriptionTool;
        checkThousandDivides = 0;
        String[] formattedValueDescription = new String[upgradeEarn.length];
        for (byte i = 0; i < upgradeEarn.length; i++) {
            descriptionTool = upgradeEarn[i] * totalUpgradesMultiply[i] * 5;
            checkThousandDivides = 0;
            if (isNormalNotation) {
                while (descriptionTool > 1000) {
                    descriptionTool /= 1000;
                    checkThousandDivides++;
                }
                formattedValueDescription[i] = decimalFormat.format(descriptionTool) + numEnds[checkThousandDivides];
            } else {
                while (descriptionTool > 10) {
                    descriptionTool /= 10;
                    checkThousandDivides++;
                }
                formattedValueDescription[i] = decimalFormat.format(descriptionTool) + numEnds[103] + checkThousandDivides;
            }
        }
        return formattedValueDescription;
    }
    private void setAfk() {
        MAX_AFK_TIME_IN_MILLIS = 2 * 60 * 60 * 1000;
        if (rebornUpgrade[2]) {
            MAX_AFK_TIME_IN_MILLIS = 4 * 60 * 60 * 1000;
        }
        if (rebornUpgrade[4]) {
            MAX_AFK_TIME_IN_MILLIS = 6 * 60 * 60 * 1000;
        }
    }
    //——————————————————————————————
    //Arithmetic-like methods
    //——————————————————————————————
    public void calculate() {
        totalAmount = 0;
        int Tool;
        Arrays.fill(totalUpgradesMultiply, (byte) 1);

        for (byte i = 0; i < productionTimeAchievementMultiply.length; i++) {
            if (productionTimeAchievementMultiply[i] != 1) {
                if (i < 3) {
                    Tool = (int) amountUpgrade[i + 1];
                    while (Tool > 0) {
                        if (Tool >= 100) {
                            totalUpgradesMultiply[i] *= productionTimeAchievementMultiplyx100[i];
                            Tool -= 100;
                        } else if (Tool >= 10) {
                            totalUpgradesMultiply[i] *= productionTimeAchievementMultiplyx10[i];
                            Tool -= 10;
                        } else {
                            totalUpgradesMultiply[i] *= productionTimeAchievementMultiply[i];
                            Tool--;
                        }

                    }
                } else if (i == 3) {
                    Tool = (int) amountUpgrade[i + 2];

                    while (Tool > 0) {
                        if (Tool >= 100) {
                            totalUpgradesMultiply[i] *= productionTimeAchievementMultiplyx100[i];
                            totalUpgradesMultiply[i + 1] *= productionTimeAchievementMultiplyx100[i];
                            Tool -= 100;
                        } else if (Tool >= 10) {
                            totalUpgradesMultiply[i] *= productionTimeAchievementMultiplyx10[i];
                            totalUpgradesMultiply[i + 1] *= productionTimeAchievementMultiplyx10[i];
                            Tool -= 10;
                        } else {
                            totalUpgradesMultiply[i] *= productionTimeAchievementMultiply[i];
                            totalUpgradesMultiply[i + 1] *= productionTimeAchievementMultiply[i];
                            Tool--;
                        }

                    }
                } else if (i == 4) {
                    Tool = (int) amountUpgrade[i + 3];

                    while (Tool > 0) {
                        if (Tool >= 100) {
                            totalUpgradesMultiply[i + 1] *= productionTimeAchievementMultiplyx100[i];
                            totalUpgradesMultiply[i + 2] *= productionTimeAchievementMultiplyx100[i];
                            Tool -= 100;
                        } else if (Tool >= 10) {
                            totalUpgradesMultiply[i + 1] *= productionTimeAchievementMultiplyx10[i];
                            totalUpgradesMultiply[i + 2] *= productionTimeAchievementMultiplyx10[i];
                            Tool -= 10;
                        } else {
                            totalUpgradesMultiply[i + 1] *= productionTimeAchievementMultiply[i];
                            totalUpgradesMultiply[i + 2] *= productionTimeAchievementMultiply[i];
                            Tool--;
                        }

                    }
                } else if (i == 5) {
                    Tool = (int) amountUpgrade[0];

                    while (Tool > 0) {
                        if (Tool >= 100) {
                            totalUpgradesMultiply[i + 2] *= productionTimeAchievementMultiplyx100[i];
                            totalUpgradesMultiply[i + 3] *= productionTimeAchievementMultiplyx100[i];
                            Tool -= 100;
                        } else if (Tool >= 10) {
                            totalUpgradesMultiply[i + 2] *= productionTimeAchievementMultiplyx10[i];
                            totalUpgradesMultiply[i + 3] *= productionTimeAchievementMultiplyx10[i];
                            Tool -= 10;
                        } else {
                            totalUpgradesMultiply[i + 2] *= productionTimeAchievementMultiply[i];
                            totalUpgradesMultiply[i + 3] *= productionTimeAchievementMultiply[i];

                            Tool--;
                        }

                    }
                }
            }
        }
        for (byte i = 0; i < totalUpgradesMultiply.length; i++) {
            for (byte j = 0; j < upgradesMultiply[i].length; j++) {
                if (totalUpgradesMultiply[i] < 1) {
                    totalUpgradesMultiply[i] = 1;
                }
                totalUpgradesMultiply[i] *= upgradesMultiply[i + 1][j];
            }
        }
        for (byte i = 0; i < totalUpgradesMultiply.length; i++) {
            totalAmount += amountUpgrade[i] * totalUpgradesMultiply[i] * upgradeEarn[i];
        }
        KPS(totalAmount);
        checkThousandDivides = 0;
    }
    public void calculateTotalMultipliers() {
        clickTotalMultiplier = 1;
        float Tool = 1;
        for (byte i = 0; i < clickAchievementMultiply.length; i++) {
            if (i < 5) {
                for (int j = 0; j < amountUpgrade[i]; j++) {
                    Tool *= clickAchievementMultiply[i];
                }
                clickTotalMultiplier *= Tool;
                Tool = 1;
            } else if (i == 5) {
                for (int j = 0; j < amountUpgrade[5]; j++) {
                    Tool *= clickAchievementMultiply[i];
                }
                for (int j = 0; j < amountUpgrade[6]; j++) {
                    Tool *= clickAchievementMultiply[i];
                }
                clickTotalMultiplier *= Tool;
                Tool = 1;
            } else {
                for (int j = 0; j < amountUpgrade[7]; j++) {
                    Tool *= clickAchievementMultiply[i];
                }
                for (int j = 0; j < amountUpgrade[8]; j++) {
                    Tool *= clickAchievementMultiply[i];
                }
                clickTotalMultiplier *= Tool;
            }
        }
        for (byte i = 0; i < clickTimeAchievementMultiply.length; i++) {
            clickTotalMultiplier *= clickTimeAchievementMultiply[i];
        }
        for (byte i = 0; i < upgradesMultiply[0].length; i++) {
            clickTotalMultiplier *= upgradesMultiply[0][i];
        }
        if (rebornUpgrade[0]) {
            clickTotalMultiplier += (totalAmount / 200.0);
        }
    }
    private void calculatePopupAFK() {
        if (popupWindow != null && popupWindow.isShowing()) {
            return; // Если открыто, ничего не делаем
        }
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastExitTime;
        loadString();
        setAfk();
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.popup_afk, null);
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
        TextView afkDescription = popupView.findViewById(R.id.afkDescription);
        TextView amountAfkPopup = popupView.findViewById(R.id.amountAfkPopup);
        TextView amountAfkCash = popupView.findViewById(R.id.amountAfkCash);
        TextView amountPopup = popupView.findViewById(R.id.amountPopup);
        Button purchasePopup = popupView.findViewById(R.id.purchasePopup);
        afkDescription.setText(description[0]);
        amountAfkCash.setText(description[1]);
        if (elapsedTime < 0) {
            amountAfkPopup.setText((formatDuration(elapsedTime / 1000)));
            amountPopup.setText(R.string.timetraveller);
        } else if (elapsedTime < MAX_AFK_TIME_IN_MILLIS) {
            amountAfkPopup.setText((formatDuration(elapsedTime / 1000)));
            if (rebornUpgrade[3]) {
                elapsedTime *= 0.75;
            } else if (rebornUpgrade[1]) {
                elapsedTime *= 0.5;
            } else {
                elapsedTime *= 0.25;
            }
            amountPopup.setText(updateCash(((totalAmount * 5) * (elapsedTime / 1000))));
        } else {
            elapsedTime = MAX_AFK_TIME_IN_MILLIS;
            if (rebornUpgrade[3]) {
                elapsedTime *= 0.75;
            } else if (rebornUpgrade[1]) {
                elapsedTime *= 0.5;
            } else {
                elapsedTime *= 0.25;
            }
            amountAfkPopup.setText(formatDuration(MAX_AFK_TIME_IN_MILLIS / 1000) + " *max");
            amountPopup.setText(updateCash(((totalAmount * 5) * elapsedTime / 1000)) + " *max");
        }
        increaseCurrencyBasedOnTime();
        purchasePopup.setOnClickListener(view -> popupWindow.dismiss());
    }
    private static String formatDuration(long seconds) {
        return DateUtils.formatElapsedTime(seconds);
    }
    private void calculatePopup() {
        if (popupWindow != null && popupWindow.isShowing()) {
            return; // Если открыто, ничего не делаем
        }
        loadString();
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.popup_reborn, null);
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
        TextView questionPopup = popupView.findViewById(R.id.questionPopup);
        TextView questionPopupTwo = popupView.findViewById(R.id.questionPopupTwo);
        TextView transmutatePopup = popupView.findViewById(R.id.transmutatePopup);
        TextView amountPopup = popupView.findViewById(R.id.amountPopup);
        Button closePopup = popupView.findViewById(R.id.closePopup);
        Button purchasePopup = popupView.findViewById(R.id.purchasePopup);
        questionPopup.setText(description1);
        questionPopupTwo.setText(description2);
        transmutatePopup.setText(description3);
        amountPopup.setText(updateCash(Math.sqrt(Math.sqrt(krikoin + 1))) + "");

        closePopup.setOnClickListener(view -> navigateToAnotherActivity());
        purchasePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Math.sqrt(Math.sqrt(krikoin)) >= 100) {
                    spaceCoin += Math.sqrt(Math.sqrt(krikoin));
                    krikoin = 0;
                    Arrays.fill(amountUpgrade, 0);
                    for (int i = 0; i < upgradesMultiply.length; i++) {
                        for (int j = 0; j < upgradesMultiply[i].length; j++) {
                            upgradesMultiply[i][j] = 1;
                        }
                    }
                    if (rebornUpgrade[5]) {
                        for (byte i = 0; i < chosenRebornUpgrades.length; i++) {
                            if (chosenRebornUpgrades[i][0] > 0) {
                                setUpgradesMultiply(i, (byte)(chosenRebornUpgrades[i][0] - 1));
                                break;
                            }
                        }
                    }
                    if (rebornUpgrade[6]) {
                        for (byte i = 0; i < chosenRebornUpgrades.length; i++) {
                            if (chosenRebornUpgrades[i][1] > 0) {
                                setUpgradesMultiply(i, (byte)(chosenRebornUpgrades[i][1] - 1));
                                break;
                            }
                        }
                    }
                    if (rebornUpgrade[7]) {
                        for (byte i = 0; i < chosenRebornUpgrades.length; i++) {
                            if (chosenRebornUpgrades[i][2] > 0) {
                                setUpgradesMultiply(i, (byte)(chosenRebornUpgrades[i][2] - 1));
                                break;
                            }
                        }
                    }
                    if (rebornUpgrade[8]) {
                        for (byte i = 0; i < chosenRebornUpgrades.length; i++) {
                            if (chosenRebornUpgrades[i][3] > 0) {
                                setUpgradesMultiply(i, (byte)(chosenRebornUpgrades[i][3] - 1));
                                break;
                            }
                        }
                    }
                    saveData();
                    navigateToAnotherActivity();
                } else if (Math.sqrt(Math.sqrt(krikoin)) < 100) {
                    purchasePopup.setText(R.string.insufficientlyFunds);
                    purchasePopup.setTextColor(Color.parseColor("#EA0000"));
                }
            }
        });
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
    }
    private PopupWindow createPopupWindow(View popupView) {
        return new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );
    }
    private void loadString () {
        description1 = getString(R.string.reborndescription1);
        description2 = getString(R.string.reborndescription2);
        description3 = getString(R.string.reborndescription3);
        description[0] = getString(R.string.afktime);
        description[1] = getString(R.string.afkgain);
    }
    public void checkClickedButton(byte i) {
        if (krikoin >= costUpgrade[i]) {
            multipliers(i, krikoin);
        }
        KPS(totalAmount);
    }

    private void multipliers(byte j, double valueToFormat) {
        if (multiplymax) {
            for (; ; ) {
                amountUpgrade[j] = amountUpgrade[j] + 1;
                valueToFormat -= costUpgrade[j];
                powerOfAmount[j] = Math.pow(increment, amountUpgrade[j]);
                costUpgrade[j] = costPrimary[j] * powerOfAmount[j];
                if (valueToFormat < 0) {
                    amountUpgrade[j] = amountUpgrade[j] - 1;
                    valueToFormat += costUpgrade[j];
                    powerOfAmount[j] = Math.pow(increment, amountUpgrade[j]);
                    costUpgrade[j] = costPrimary[j] * powerOfAmount[j];
                    krikoin = valueToFormat;
                    calculate();
                    break;
                }
            }
        } else if (multiply100) {
            for (byte i = 0; i < 100; i++) {
                amountUpgrade[j] = amountUpgrade[j] + 1;
                valueToFormat -= costUpgrade[j];
                powerOfAmount[j] = Math.pow(increment, amountUpgrade[j]);
                costUpgrade[j] = costPrimary[j] * powerOfAmount[j];
                krikoin = valueToFormat;
                if (valueToFormat < 0) {
                    amountUpgrade[j] = amountUpgrade[j] - 1;
                    valueToFormat += costUpgrade[j];
                    powerOfAmount[j] = Math.pow(increment, amountUpgrade[j]);
                    costUpgrade[j] = costPrimary[j] * powerOfAmount[j];
                    krikoin = valueToFormat;
                    calculate();
                    break;
                }
            }
        } else if (multiply5) {
            for (byte i = 0; i < 5; i++) {
                amountUpgrade[j] = amountUpgrade[j] + 1;
                valueToFormat -= costUpgrade[j];
                powerOfAmount[j] = Math.pow(increment, amountUpgrade[j]);
                costUpgrade[j] = costPrimary[j] * powerOfAmount[j];
                krikoin = valueToFormat;
                if (valueToFormat < 0) {
                    amountUpgrade[j] = amountUpgrade[j] - 1;
                    valueToFormat += costUpgrade[j];
                    powerOfAmount[j] = Math.pow(increment, amountUpgrade[j]);
                    costUpgrade[j] = costPrimary[j] * powerOfAmount[j];
                    krikoin = valueToFormat;
                    calculate();
                    break;
                }
            }
        } else {
            // Действие при улучшении первой кнопки
            amountUpgrade[j] = amountUpgrade[j] + 1;
            krikoin -= costUpgrade[j];
            powerOfAmount[j] = Math.pow(increment, amountUpgrade[j]);
            costUpgrade[j] = costPrimary[j] * powerOfAmount[j];
            calculate();
        }
        saveData();
    }
    public String updatePriceUpgrades(double valueToFormat, byte i, byte j) {
        checkThousandDivides = 0;
        // Пока значение больше 1000 и стоимость clickUpgradex больше 9999, делим значение на 1000
        if (isNormalNotation) {
            while (valueToFormat > 1000) {
                valueToFormat /= 1000;
                checkThousandDivides++;
            }
        } else {
            while (valueToFormat > 10) {
                valueToFormat /= 10;
                checkThousandDivides++;
            }
        }
        String formattedValueUpgrades;
        // Форматируем стоимость clickUpgradex в строку с заданным числом знаков после запятой
        formattedValueUpgrades = (upgradesCost[i][j] <= 9999) ? String.format("%.0f", valueToFormat) : String.format("%.1f", valueToFormat);

        // Определяем индекс для выбора окончания

        // Формируем строку для отображения
        String displayTextUpgrades = (!isNormalNotation) ?
                formattedValueUpgrades + numEnds[103] + (checkThousandDivides) :
                formattedValueUpgrades + numEnds[checkThousandDivides];

        return displayTextUpgrades;
        // Устанавливаем текст в соответствующий элемент
    }
    //——————————————————————————————
    //Animation-like methods
    //——————————————————————————————
    private void launchAnimation(byte j, byte i, byte k) {
        int resourceId;
        switch(j) {
            case 0:
                resourceId = getResources().getIdentifier("coinid" + (k + 1), "drawable", getPackageName());
                break;
            case 1:
                resourceId = getResources().getIdentifier("id1a_" + (k + 1), "drawable", getPackageName());
                break;
            case 2:
                resourceId = getResources().getIdentifier("id2a_" + (k + 1), "drawable", getPackageName());
                break;
            default:
                resourceId = getResources().getIdentifier("coinid0", "drawable", getPackageName());
                break;
        }

        iconAnimatedView.setImageResource(resourceId);
        targetAchieved[i] = true;
        animatedView.setAlpha(0f);
        animatedView.setVisibility(View.VISIBLE);
        iconAnimatedView.setVisibility(View.VISIBLE);
        textAnimatedView.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity and clear any animation
        // listener set on the view.
        animatedView.animate()
                .alpha(1f)
                .setDuration(1000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animatedView.animate()
                                .alpha(0f)
                                .setDuration(1000)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        animatedView.animate()
                                                .alpha(1f)
                                                .setDuration(1000)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        animatedView.animate()
                                                                .alpha(1f)
                                                                .setDuration(2500)
                                                                .setListener(new AnimatorListenerAdapter() {
                                                                    @Override
                                                                    public void onAnimationEnd(Animator animation) {
                                                                        animatedView.setVisibility(View.GONE);
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });

        textAnimatedView.animate()
                .alpha(1f)
                .setDuration(1000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        textAnimatedView.animate()
                                .alpha(0f)
                                .setDuration(1000)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        textAnimatedView.animate()
                                                .alpha(1f)
                                                .setDuration(1000)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        textAnimatedView.animate()
                                                                .alpha(1f)
                                                                .setDuration(2500)
                                                                .setListener(new AnimatorListenerAdapter() {
                                                                    @Override
                                                                    public void onAnimationEnd(Animator animation) {
                                                                        textAnimatedView.setVisibility(View.GONE);
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });

        iconAnimatedView.animate()
                .alpha(1f)
                .setDuration(1000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        iconAnimatedView.animate()
                                .alpha(0f)
                                .setDuration(1000)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        iconAnimatedView.animate()
                                                .alpha(1f)
                                                .setDuration(1000)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        iconAnimatedView.animate()
                                                                .alpha(1f)
                                                                .setDuration(2500)
                                                                .setListener(new AnimatorListenerAdapter() {
                                                                    @Override
                                                                    public void onAnimationEnd(Animator animation) {
                                                                        iconAnimatedView.setVisibility(View.GONE);
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }
    private void checkIfAchieved() {
        if (!targetAchieved[8]) {
            coinsCheck();
        }
        if (!targetAchieved[15]) {
            clicksCheck();
        }
        if (!targetAchieved[21]) {
            timeCheck();
        }
        if (targetAchieved[8] && targetAchieved[15] && targetAchieved[21] && hasReborn) {
            handler.removeCallbacks(Update);
        }
        if (krikoin > 1_000_000_000.0) {
            hasReborn = true;
            saveData();
            rebornButton.setVisibility(View.VISIBLE);
        }
    }
    private void coinsCheck() {
        for (byte i = 0; i < 9; i++) {
            if (!targetAchieved[i] && amountUpgrade[i] >= 1) {
                launchAnimation((byte)0, i, i);
            }
        }
    }
    private void clicksCheck() {
        for (byte i = 0; i < clickCondition.length; i++) {
            if (!targetAchieved[i + 9] && clickCount >= clickCondition[i]) {
                launchAnimation((byte) 1, (byte)(i + 9), i);
            }
        }
    }
    private void timeCheck() {
        for (byte i = 0; i < timeCondition.length; i++) {
            if (!targetAchieved[i + 16] && timeCount >= timeCondition[i]) {
                launchAnimation((byte) 2, (byte)(i + 16), i);
            }
        }
    }
    public void onClickUpgradesPurchase(byte i, byte j) {
        krikoin -= upgradesCost[i][j];
    }
    public void onClickUpgradesPurchaseAll() {
        for (byte i = 0; i < 10; i++) {
            for (byte j = 0; j < 8; j++) {
                if (i == 0) {
                    if (upgradesMultiply[0][j] == 1 && krikoin >= upgradesCost[0][j]) {
                        krikoin -= upgradesCost[i][j];
                        updateCash(krikoin);
                        setUpgradesMultiply(i, j);
                    }
                } else {
                    if (amountUpgrade[i - 1] >= condition[j] && upgradesMultiply[i][j] == 1 && krikoin > upgradesCost[i][j]) {
                        krikoin -= upgradesCost[i][j];
                        updateCash(krikoin);
                        setUpgradesMultiply(i, j);
                    }
                }
            }
        }
        calculate();
    }
    public boolean isUpgradesCondition(byte i, byte j) {
        return (upgradesMultiply[i][j] > 1 || (amountUpgrade[i - 1] >= condition[j] && krikoin > (upgradesCost[i][j] * 0.75F)));
    }
    public boolean isFill() {
        return isFill;
    }
    public long[] getClickCondition() {
        return clickCondition;
    }
    public long[] getTimeCondition() {
        return timeCondition;
    }
    public long getClickCount() {
        return clickCount;
    }
    public long getTimeCount() {
        return timeCount;
    }
    public String getUpdateCashDisplayText() {
        return updateCash(krikoin);
    }
    public String getKPSDisplayText () {
        return KPS(totalAmount);
    }
    public double getKrikoin() {
        return krikoin;
    }
    public int getChosenKrikoin() {
        return chosenKrikoin;
    }
    public int getChosenKrikoinIcon(byte i) {
        return chosenIcon[i];
    }
    public int getChosenRebornUpgrade(byte i, byte j) {
        return chosenRebornUpgrades[i][j];
    }
    public boolean getRebornUpgrade(byte i) {
        return rebornUpgrade[i];
    }
    public void setNotation(boolean statement) {
        isNormalNotation = statement;
        updateCash(krikoin);
        KPS(krikoin);
    }
    public void setMultiply1() {
        multiply5 = false;
        multiply100 = false;
        multiplymax = false;
    }
    public void setMultiply5() {
        multiply5 = true;
        multiply100 = false;
        multiplymax = false;
    }
    public void setMultiply100() {
        multiply5 = false;
        multiply100 = true;
        multiplymax = false;
    }
    public void setMultiplymax() {
        multiply5 = false;
        multiply100 = false;
        multiplymax = true;
    }
    public void setClickAchievementMultiply(byte i) {
        clickAchievementMultiply[i] = clickAchievementMultiplyExample[i];
    }
    public void setTimeAchievementMultiply(byte i) {
        clickTimeAchievementMultiply[i] = clickTimeAchievementMultiplyExample[i];
        productionTimeAchievementMultiply[i] = productionTimeAchievementMupltiplyExample[i];
    }
    public void setIsFill(boolean statement) {
        isFill = statement;
    }
    public void setChosenBackground(byte i) {
        chosenBackground = i;
    }
    public void setChosenKrikoin(byte i) {
        chosenKrikoin = i;
    }
    public void setChosenKrikoinIcon(byte i, byte j) {
        chosenIcon[i] = j;
    }
    public void setChosenRebornUpgrade(byte i, byte j, byte statement) {
        for (byte one = 0; one < chosenRebornUpgrades.length; one++) {
                chosenRebornUpgrades[one][j] = -1;
        }
        chosenRebornUpgrades[i][j] = statement;
    }
    public void setUpgradesMultiply(byte i, byte j) {
        if (j == 1 || j == 4 || j == 6 || j == 7) {
            upgradesMultiply[i][j] = 5;
        } else {
            upgradesMultiply[i][j] = 2;
        }
    }
    public void onClickHome() {
        // Действие при нажатии кнопки
        krikoin += clickTotalMultiplier;
        clickCount++;
        // Установка значения для проверки
        updateCash(krikoin);
    }

    //——————————————————————————————

    private void load() {
        loadSave();
        if (!hasReborn) {
            rebornButton.setVisibility(View.GONE);
        } else {
            rebornButton.setVisibility(View.VISIBLE);
        }
        updateCash(krikoin);
        if (handler == null) {
            handler = new Handler();
            handler.postDelayed(update, 200);
            handler.postDelayed(Update, 1000);
        } else {
            handler.removeCallbacks(update);
            handler.removeCallbacks(Update);
            handler.postDelayed(update, 200);
            handler.postDelayed(Update, 1000);
        }
        if (saveHandler == null) {
            saveHandler = new Handler();
            saveHandler.postDelayed(saveRunnable, SAVE_INTERVAL);
        } else {
            saveHandler.removeCallbacks(saveRunnable);
            saveHandler.postDelayed(saveRunnable, SAVE_INTERVAL);
        }
        setBackground();
        calculate();
        calculateTotalMultipliers();
        KPS(totalAmount);
        saveData();
    }
    public void replaceFragment(Fragment fragment) {
        saveData();
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main);
        if (currentFragment.getClass() != fragment.getClass()) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.nav_host_fragment_activity_main, fragment);
            transaction.commit();
        }
    }
    private void removeActiveButton() {
        settingsButton.setBackground(getResources().getDrawable(R.drawable.tab_button));
        upgradeButton.setBackground(getResources().getDrawable(R.drawable.tab_button));
        homeButton.setBackground(getResources().getDrawable(R.drawable.tab_button));
        productionButton.setBackground(getResources().getDrawable(R.drawable.tab_button));
        achievementsButton.setBackground(getResources().getDrawable(R.drawable.tab_button));
        rebornButton.setBackground(getResources().getDrawable(R.drawable.tab_button));

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
