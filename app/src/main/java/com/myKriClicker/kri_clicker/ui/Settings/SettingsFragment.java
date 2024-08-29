package com.myKriClicker.kri_clicker.ui.Settings;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.myKriClicker.kri_clicker.MainActivity;
import com.myKriClicker.kri_clicker.R;
import com.myKriClicker.kri_clicker.databinding.ActivitySettingsBinding;

public class SettingsFragment extends Fragment {
    private ActivitySettingsBinding binding;
    private Context mContext;
    private ImageButton rebornButton[] = new ImageButton[4];
    private AppCompatButton changeBackground, changeKriKoin, changeIcon,
            loadBackground,
            normalNotation, scienceNotation,
            isBackgroundFill,
            emptyButton;

    private TextView CurrentKrikoinSettings, KrikoinPerSecondSettings, clickCountAmount, timeSpentAmount;
    private ConstraintLayout settingsActivity;
    private ImageView SettingsKrikoin, settingsKrikoinPerSecond;
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

    private PopupWindow popupWindow;
    private AppCompatImageButton[] backgroundButton = new AppCompatImageButton[backgroundIds.length];
    int[] buttonBackgroundIds = {
            R.drawable.normalnotationbackground,
            R.drawable.sciencenotationbackground
    };
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
                    R.drawable.id1_1, R.drawable.id1_1, R.drawable.id1_2, R.drawable.id1_3, R.drawable.id1_4,
                    R.drawable.id1_5, R.drawable.id1_6, R.drawable.id1_7, R.drawable.id1_8
            },
            {
                    R.drawable.upgrade_one, R.drawable.id2_1, R.drawable.id2_2, R.drawable.id2_3, R.drawable.id2_4,
                    R.drawable.id2_5, R.drawable.id2_6, R.drawable.id2_7, R.drawable.id2_8
            },
            {
                    R.drawable.upgrade_two, R.drawable.id3_1, R.drawable.id3_2, R.drawable.id3_3, R.drawable.id3_4,
                    R.drawable.id3_5, R.drawable.id3_6, R.drawable.id3_7, R.drawable.id3_8
            },
            {
                    R.drawable.upgrade_three, R.drawable.id4_1, R.drawable.id4_2, R.drawable.id4_3, R.drawable.id4_4,
                    R.drawable.id4_5, R.drawable.id4_6, R.drawable.id4_7, R.drawable.id4_8
            },
            {
                    R.drawable.upgrade_fourth, R.drawable.id5_1, R.drawable.id5_2, R.drawable.id5_3, R.drawable.id5_4,
                    R.drawable.id5_5, R.drawable.id5_6, R.drawable.id5_7, R.drawable.id5_8
            },
            {
                    R.drawable.upgrade_fifth, R.drawable.id6_1, R.drawable.id6_2, R.drawable.id6_3, R.drawable.id6_4,
                    R.drawable.id6_5, R.drawable.id6_6, R.drawable.id6_7, R.drawable.id6_8
            },
            {
                    R.drawable.upgrade_sixth, R.drawable.id7_1, R.drawable.id7_2, R.drawable.id7_3, R.drawable.id7_4,
                    R.drawable.id7_5, R.drawable.id7_6, R.drawable.id7_7, R.drawable.id7_8
            },
            {
                    R.drawable.upgrade_seventh, R.drawable.id8_1, R.drawable.id8_2, R.drawable.id8_3, R.drawable.id8_4,
                    R.drawable.id8_5, R.drawable.id8_6, R.drawable.id8_7, R.drawable.id8_8
            },
            {
                    R.drawable.upgrade_eigth, R.drawable.id9_1, R.drawable.id9_2, R.drawable.id9_3, R.drawable.id9_4,
                    R.drawable.id9_5, R.drawable.id9_6, R.drawable.id9_7, R.drawable.id9_8
            },
            {
                    R.drawable.upgrade_ninth, R.drawable.id10_1, R.drawable.id10_2, R.drawable.id10_3, R.drawable.id10_4,
                    R.drawable.id10_5, R.drawable.id10_6, R.drawable.id10_7, R.drawable.id10_8
            },
    };
    private AppCompatImageButton[] kriKoinButton = new AppCompatImageButton[krikoinIds.length];
    private AppCompatImageButton[][] productionButton = new AppCompatImageButton[imageIds.length - 1][imageIds[0].length];
    private AppCompatImageButton[][] stoneButton = new AppCompatImageButton[imageIds.length][imageIds[0].length];
    private String kriKoinText;
    private String backgroundText;
    private String iconText;
    private String stoneDescription;
    private String isFillText;
    private String isStretchText;
    private Handler statisticHandler, handler;

    private Runnable statistic = new Runnable() {
        @Override
        public void run() {
            calculateData();
            // Установка значения для проверки
            statisticHandler.postDelayed(this, 1000); // Запуск обновления каждую 0,1 секунду
        }
    };
    private Runnable update = new Runnable() {
        @Override
        public void run() {
            updateCash();
            // Установка значения для проверки
            handler.postDelayed(this, 200); // Запуск обновления каждую 0,1 секунду

        }

    };
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = ActivitySettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        CurrentKrikoinSettings = binding.CurrentKrikoinSettings;
        KrikoinPerSecondSettings = binding.KrikoinPerSecondSettings;
        settingsActivity = binding.settingsActivity;
        SettingsKrikoin = binding.SettingsKriKoin;
        changeBackground = binding.changeBackground;
        changeKriKoin = binding.changeKriKoin;
        changeIcon = binding.changeIcon;
        normalNotation = binding.normalNotation;
        scienceNotation = binding.scienceNotation;
        clickCountAmount = binding.clickCountAmountResult;
        timeSpentAmount = binding.timeSpentAmountResult;
        isBackgroundFill = binding.isBackgroundFill;
        settingsKrikoinPerSecond = binding.SettingsKriKoinPerSecond;

        rebornButton[0] = binding.rebornButton1;
        rebornButton[1] = binding.rebornButton2;
        rebornButton[2] = binding.rebornButton3;
        rebornButton[3] = binding.rebornButton4;

        load();

        if (((MainActivity) requireActivity()).isFill()) {
            isBackgroundFill.setText(isStretchText);
        } else {
            isBackgroundFill.setText(isFillText);
        }
        changeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopup((byte) 0);
            }
        });
        changeKriKoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopup((byte) 1);
            }
        });
        changeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopup((byte) 2);
            }
        });

        normalNotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).setNotation(true);
                KPS();
            }
        });
        scienceNotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).setNotation(false);
                KPS();
            }
        });
        isBackgroundFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((MainActivity) requireActivity()).isFill()) {
                    isBackgroundFill.setText(isFillText);
                    ((MainActivity) requireActivity()).setIsFill(false);
                } else {
                    isBackgroundFill.setText(isStretchText);
                    ((MainActivity) requireActivity()).setIsFill(true);
                }
                ((MainActivity) requireActivity()).setBackground();
            }
        });

        for (byte i = 0; i < backgroundIds.length; i++) {
            byte finalI = i;
            if (backgroundButton[i] != null) {
                backgroundButton[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity) requireActivity()).setChosenBackground(finalI);
                        ((MainActivity) requireActivity()).setBackground();
                    }
                });
            }
        }
        for (byte i = 0; i < krikoinIds.length; i++) {
            byte finalI = i;
            if (kriKoinButton[i] != null) {
                kriKoinButton[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity) requireActivity()).setChosenKrikoin(finalI);
                        setKriKoins();
                    }
                });
            }
        }
        for (byte i = 0; i < rebornButton.length; i++) {
            byte finalI = i;
            if (rebornButton[i] != null) {
                rebornButton[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (((MainActivity) requireActivity()).getRebornUpgrade((byte)(finalI + 5))) {
                            calculate(finalI);
                        }
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
        if (statisticHandler != null) {
            statisticHandler.removeCallbacks(statistic);
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
        if (statisticHandler == null) {
            statisticHandler = new Handler();
            statisticHandler.postDelayed(statistic, 200);
        } else {
            statisticHandler.removeCallbacks(statistic);
            statisticHandler.postDelayed(statistic, 200);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Остановка обновления при уничтожении активности
        if (handler != null) {
            handler.removeCallbacks(update);
        }
        if (statisticHandler != null) {
            statisticHandler.removeCallbacks(statistic);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setKriKoins() {
        SettingsKrikoin.setImageResource(krikoinIds[((MainActivity) requireActivity()).getChosenKrikoin()]);
        settingsKrikoinPerSecond.setImageResource(krikoinIds[((MainActivity) requireActivity()).getChosenKrikoin()]);
    }
    private void createPopup(byte chosen) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return; // Если открыто, ничего не делаем
        }
        LayoutInflater inflater = getLayoutInflater();

        View popupView = inflater.inflate(R.layout.popup_settings, null);

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
        int y = (screenHeight - popupHeight) / 2 - 200;
        float dpValue = 60; // Размер в dp
        float scale = getResources().getDisplayMetrics().density;
        int pixelSize = (int) (dpValue * scale + 0.5f); // Преобразование в пиксели
        GridLayout gridLayout = new GridLayout(mContext);
        gridLayout.setLayoutParams(new GridLayout.LayoutParams());
        gridLayout.setLayoutParams(new GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
        ));
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        gridLayout.setColumnOrderPreserved(false);
        gridLayout.setPadding(16, 16, 16, 16);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, x, y);
        ((ViewGroup) popupView).addView(gridLayout);

        TextView askText = popupView.findViewById(R.id.askText);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) gridLayout.getLayoutParams();
        layoutParams.topToBottom = askText.getId(); // Устанавливаем связь с askText
        switch (chosen) {
            case 0:
                askText.setText(backgroundText);
                break;
            case 1:
                askText.setText(kriKoinText);
                break;
            case 2:
                askText.setText(iconText);
                break;
        }
        layoutParams.topToBottom = askText.getId(); // Устанавливаем связь с askText
        switch (chosen) {
            case 0:
                gridLayout.setColumnCount(3);
                gridLayout.setRowCount(5);
                for (byte i = 0; i < backgroundIds.length; i++) {

                    backgroundButton[i] = new AppCompatImageButton(mContext); // Инициализация элемента
                    backgroundButton[i].setLayoutParams(new GridLayout.LayoutParams(
                            GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                            GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
                    ));
                    backgroundButton[i].setLayoutParams(new ViewGroup.LayoutParams(pixelSize, pixelSize));
                    backgroundButton[i].setPadding(0, 0, 0, 0);
                    backgroundButton[i].setBackground(getResources().getDrawable(R.drawable.imagebutton_shape));
                    backgroundButton[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
                    backgroundButton[i].setImageResource(backgroundIds[i]);
                    gridLayout.addView(backgroundButton[i]);

                }
                for (byte i = 0; i < backgroundIds.length; i++) {
                    byte finalI = i;
                    if (backgroundButton[i] != null) {
                        backgroundButton[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ((MainActivity) requireActivity()).setChosenBackground(finalI);
                                ((MainActivity) requireActivity()).setBackground();
                            }
                        });
                    }
                }
                break;
            case 1:
                gridLayout.setColumnCount(3);
                gridLayout.setRowCount(4);

                for (byte i = 0; i < krikoinIds.length; i++) {
                    kriKoinButton[i] = new AppCompatImageButton(mContext); // Инициализация элемента
                    kriKoinButton[i].setLayoutParams(new GridLayout.LayoutParams(
                            GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                            GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
                    ));
                    kriKoinButton[i].setLayoutParams(new ViewGroup.LayoutParams(pixelSize, pixelSize));
                    kriKoinButton[i].setPadding(0, 0, 0, 0);
                    kriKoinButton[i].setBackground(getResources().getDrawable(R.drawable.imagebutton_shape));
                    kriKoinButton[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
                    kriKoinButton[i].setImageResource(krikoinIds[i]);

                }
                byte isEnoughForFunction = 0;
                gridLayout.addView(kriKoinButton[0]);
                if (((MainActivity) requireActivity()).amountUpgrade[8] > 9) {
                    gridLayout.addView(kriKoinButton[1]);
                    isEnoughForFunction++;
                }
                for (byte i = 0; i < ((MainActivity) requireActivity()).amountUpgrade.length; i++) {
                    if (((MainActivity) requireActivity()).amountUpgrade[i] > 0) {
                        gridLayout.addView(kriKoinButton[i + 2]);
                        isEnoughForFunction++;
                    }
                }
                if (isEnoughForFunction < 4) {
                    gridLayout.setColumnCount(GridLayout.UNDEFINED);
                }
                for (byte i = 0; i < krikoinIds.length; i++) {
                    byte finalI = i;
                    if (kriKoinButton[i] != null) {
                        kriKoinButton[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ((MainActivity) requireActivity()).setChosenKrikoin(finalI);
                                setKriKoins();
                            }
                        });
                    }
                }
                break;
            case 2:

                gridLayout.setColumnCount(9);
                gridLayout.setRowCount(9);
                pixelSize = (int) ((dpValue * scale + 0.5f) / 2); // Преобразование в пиксели


                for (byte i = 0; i < imageIds.length - 1; i++) {
                    for (byte j = 0; j < imageIds[i].length; j++) {
                        if (j == 0 || ((MainActivity) requireActivity()).upgradesMultiply[i + 1][j - 1] > 1) {
                            productionButton[i][j] = new AppCompatImageButton(mContext); // Инициализация элемента
                            productionButton[i][j].setLayoutParams(new GridLayout.LayoutParams(
                                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
                            ));
                            productionButton[i][j].setLayoutParams(new ViewGroup.LayoutParams(pixelSize, pixelSize));
                            productionButton[i][j].setPadding(0, 0, 0, 0);
                            productionButton[i][j].setImageResource(R.drawable.imagebutton_shape);
                            if (((MainActivity) requireActivity()).getChosenKrikoinIcon(i) == j) {
                                productionButton[i][j].setBackground(getResources().getDrawable(R.drawable.chosenicon));
                            } else {
                                productionButton[i][j].setBackground(getResources().getDrawable(R.drawable.transparentbutton));
                            }
                            productionButton[i][j].setScaleType(ImageView.ScaleType.FIT_CENTER);
                            productionButton[i][j].setImageResource(imageIds[i + 1][j]);
                            gridLayout.addView(productionButton[i][j]);
                        } else {
                            emptyButton = new AppCompatButton(mContext);
                            emptyButton.setBackground(getResources().getDrawable(R.drawable.transparentbutton));
                            emptyButton.setLayoutParams(new ViewGroup.LayoutParams(pixelSize, pixelSize));

                            gridLayout.addView(emptyButton);
                        }
                    }
                }
                for (byte i = 0; i < imageIds.length - 1; i++) {
                    for (byte j = 0; j < imageIds[i].length; j++) {
                        byte finalI = i;
                        byte finalJ = j;
                        if (productionButton[i][j] != null) {
                            productionButton[i][j].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ((MainActivity) requireActivity()).setChosenKrikoinIcon(finalI, finalJ);
                                    for (byte k = 0; k < productionButton.length; k++) {
                                        if (productionButton[finalI][k] != null) {
                                            productionButton[finalI][k].setBackground(getResources().getDrawable(R.drawable.transparentbutton));
                                        }
                                    }
                                    productionButton[finalI][finalJ].setBackground(getResources().getDrawable(R.drawable.chosenicon));
                                }
                            });
                        }
                    }
                }
                break;
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


    private void calculateData() {
        clickCountAmount.setText(((MainActivity) requireActivity()).getClickCount() + "");
        timeSpentAmount.setText(formatDuration(((MainActivity) requireActivity()).getTimeCount() / 5));
    }
    private void calculate(byte k) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return; // Если открыто, ничего не делаем
        }
        LayoutInflater inflater = getLayoutInflater();

        View popupView = inflater.inflate(R.layout.popup_settings, null);

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
        int y = (screenHeight - popupHeight) / 2 - 200;
        float dpValue = 60; // Размер в dp
        float scale = getResources().getDisplayMetrics().density;
        int pixelSize = (int) ((dpValue * scale + 0.5f) / 2);// Преобразование в пиксели
        GridLayout gridLayout = new GridLayout(mContext);
        gridLayout.setLayoutParams(new GridLayout.LayoutParams());
        gridLayout.setLayoutParams(new GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
        ));
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        gridLayout.setColumnOrderPreserved(false);
        gridLayout.setPadding(16, 16, 16, 16);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, x, y);
        ((ViewGroup) popupView).addView(gridLayout);

        TextView askText = popupView.findViewById(R.id.askText);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) gridLayout.getLayoutParams();
        layoutParams.topToBottom = askText.getId(); // Устанавливаем связь с askText
        gridLayout.setColumnCount(8);
        gridLayout.setRowCount(9);
        askText.setText(stoneDescription);

        for (byte i = 0; i < imageIds.length; i++) {
            for (byte j = 0; j < imageIds[i].length - 1; j++) {
                if (((MainActivity) requireActivity()).upgradesMultiply[i][j] > 1) {
                    stoneButton[i][j] = new AppCompatImageButton(mContext); // Инициализация элемента
                    stoneButton[i][j].setLayoutParams(new GridLayout.LayoutParams(
                            GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                            GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
                    ));
                    stoneButton[i][j].setLayoutParams(new ViewGroup.LayoutParams(pixelSize, pixelSize));
                    stoneButton[i][j].setPadding(0, 0, 0, 0);
                    stoneButton[i][j].setImageResource(R.drawable.imagebutton_shape);
                    if (((MainActivity) requireActivity()).getChosenRebornUpgrade(i, k) == j) {
                        ((MainActivity) requireActivity()).setChosenRebornUpgrade(i, k, j);
                        stoneButton[i][j].setBackground(getResources().getDrawable(R.drawable.chosenicon));
                    } else {
                        stoneButton[i][j].setBackground(getResources().getDrawable(R.drawable.transparentbutton));
                    }
                    stoneButton[i][j].setScaleType(ImageView.ScaleType.FIT_CENTER);
                    stoneButton[i][j].setImageResource(imageIds[i][j + 1]);
                    gridLayout.addView(stoneButton[i][j]);
                } else {
                    emptyButton = new AppCompatButton(mContext);
                    emptyButton.setBackground(getResources().getDrawable(R.drawable.transparentbutton));
                    emptyButton.setLayoutParams(new ViewGroup.LayoutParams(pixelSize, pixelSize));

                    gridLayout.addView(emptyButton);
                }
            }
        }
        for (byte i = 0; i < imageIds.length; i++) {
            for (byte j = 0; j < imageIds[i].length - 1; j++) {
                byte finalI = i;
                byte finalJ = j;
                if (stoneButton[i][j] != null) {
                    stoneButton[i][j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((MainActivity) requireActivity()).setChosenRebornUpgrade(finalI, k, finalJ);
                            for (byte k = 0; k < stoneButton.length; k++) {
                                for (byte l = 0; l < stoneButton[k].length; l++) {
                                    if (stoneButton[k][l] != null) {
                                        stoneButton[k][l].setBackground(getResources().getDrawable(R.drawable.transparentbutton));
                                    }
                                }
                            }
                            stoneButton[finalI][finalJ].setBackground(getResources().getDrawable(R.drawable.chosenicon));
                        }
                    });
                }
            }
        }
    }
    private static String formatDuration(long seconds) {
        return DateUtils.formatElapsedTime(seconds);
    }
    private void loadString() {
        backgroundText = getString(R.string.chooseBackground);
        kriKoinText = getString(R.string.chooseKriKoin);
        iconText = getString(R.string.chooseIcon);
        stoneDescription = getString(R.string.rebornstonedescription);
        isFillText = getString(R.string.isFillBackground);
        isStretchText = getString(R.string.isStretchBackground);
    }

    private void updateCash() {
        CurrentKrikoinSettings.setText(((MainActivity) requireActivity()).getUpdateCashDisplayText());
    }

    private void KPS() {
        KrikoinPerSecondSettings.setText(((MainActivity) requireActivity()).getKPSDisplayText());
    }
    private void setNotationBackground() {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeResource(getResources(), buttonBackgroundIds[0]);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        bitmapDrawable.setTileModeXY(android.graphics.Shader.TileMode.REPEAT, android.graphics.Shader.TileMode.REPEAT);
        normalNotation.setBackground(bitmapDrawable);

        bitmap = BitmapFactory.decodeResource(getResources(), buttonBackgroundIds[1]);
        bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        bitmapDrawable.setTileModeXY(android.graphics.Shader.TileMode.REPEAT, android.graphics.Shader.TileMode.REPEAT);
        scienceNotation.setBackground(bitmapDrawable);
    }
    private void showRebornButtons() {
        if (((MainActivity) requireActivity()).getRebornUpgrade((byte)5)) {
            rebornButton[0].setVisibility(View.VISIBLE);
        }
        if (((MainActivity) requireActivity()).getRebornUpgrade((byte)6)) {
            rebornButton[1].setVisibility(View.VISIBLE);
        }
        if (((MainActivity) requireActivity()).getRebornUpgrade((byte)7)) {
            rebornButton[2].setVisibility(View.VISIBLE);
        }
        if (((MainActivity) requireActivity()).getRebornUpgrade((byte)8)) {
            rebornButton[3].setVisibility(View.VISIBLE);
        }
    }
    private void load() {
        loadString();
        setKriKoins();
        updateCash();
        calculateData();
        KPS();
        showRebornButtons();
        setNotationBackground();
        if (statisticHandler == null) {
            statisticHandler = new Handler();
            statisticHandler.postDelayed(statistic, 1000);
        } else {
            statisticHandler.removeCallbacks(statistic);
            statisticHandler.postDelayed(statistic, 1000);
        }
        if (handler == null) {
            handler = new Handler();
            handler.postDelayed(update, 1000);
        } else {
            handler.removeCallbacks(update);
            handler.postDelayed(update, 1000);
        }
    }
}
