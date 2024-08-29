package com.myKriClicker.kri_clicker.ui.Production;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import com.myKriClicker.kri_clicker.databinding.ActivityProductionBinding;
import com.myKriClicker.kri_clicker.MainActivity;

public class ProductionFragment extends Fragment {
    private ActivityProductionBinding binding;
    private TextView CurrentKrikoinProduction, KrikoinPerSecondProduction;
    private TextView[] UpgradeCost = new TextView[9];
    private TextView[] amountUpgrades = new TextView[9];
    private TextView[] Description = new TextView[9];
    private ConstraintLayout productionActivity;
    private ImageView ProductionKrikoin, productionKrikoinPerSecond;
    private ImageView[] icons = new ImageView[9];
    public Button multiply1x, multiply5x, multiply100x, multiplymaxx;
    public Button[] Upgrade = new Button[9];
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
    GradientDrawable border = new GradientDrawable();
    GradientDrawable bordeR = new GradientDrawable();

    private String perSecond;

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
        binding = ActivityProductionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        CurrentKrikoinProduction = binding.CurrentKrikoinProduction;
        KrikoinPerSecondProduction = binding.KrikoinPerSecondProduction;
        productionKrikoinPerSecond = binding.ProductionKrikoinPerSecond;
        Upgrade[0] = binding.Upgrade1;
        Upgrade[1] = binding.Upgrade2;
        Upgrade[2] = binding.Upgrade3;
        Upgrade[3] = binding.Upgrade4;
        Upgrade[4] = binding.Upgrade5;
        Upgrade[5] = binding.Upgrade6;
        Upgrade[6] = binding.Upgrade7;
        Upgrade[7] = binding.Upgrade8;
        Upgrade[8] = binding.Upgrade9;

        UpgradeCost[0] = binding.UpgradeCost1;
        UpgradeCost[1] = binding.UpgradeCost2;
        UpgradeCost[2] = binding.UpgradeCost3;
        UpgradeCost[3] = binding.UpgradeCost4;
        UpgradeCost[4] = binding.UpgradeCost5;
        UpgradeCost[5] = binding.UpgradeCost6;
        UpgradeCost[6] = binding.UpgradeCost7;
        UpgradeCost[7] = binding.UpgradeCost8;
        UpgradeCost[8] = binding.UpgradeCost9;

        amountUpgrades[0] = binding.amountUpgrades1;
        amountUpgrades[1] = binding.amountUpgrades2;
        amountUpgrades[2] = binding.amountUpgrades3;
        amountUpgrades[3] = binding.amountUpgrades4;
        amountUpgrades[4] = binding.amountUpgrades5;
        amountUpgrades[5] = binding.amountUpgrades6;
        amountUpgrades[6] = binding.amountUpgrades7;
        amountUpgrades[7] = binding.amountUpgrades8;
        amountUpgrades[8] = binding.amountUpgrades9;

        Description[0] = binding.description1;
        Description[1] = binding.description2;
        Description[2] = binding.description3;
        Description[3] = binding.description4;
        Description[4] = binding.description5;
        Description[5] = binding.description6;
        Description[6] = binding.description7;
        Description[7] = binding.description8;
        Description[8] = binding.description9;

        icons[0] = binding.icon1;
        icons[1] = binding.icon2;
        icons[2] = binding.icon3;
        icons[3] = binding.icon4;
        icons[4] = binding.icon5;
        icons[5] = binding.icon6;
        icons[6] = binding.icon7;
        icons[7] = binding.icon8;
        icons[8] = binding.icon9;

        multiply1x = binding.multiply1x;
        multiply5x = binding.multiply5x;
        multiply100x = binding.multiply100x;
        multiplymaxx = binding.multiplymax;

        // Инициализирование функий из xml
        perSecond = getString(R.string.perSecond);

        productionActivity = binding.productionActivity;
        ProductionKrikoin = binding.ProductionKrikoin;

        load();

        bordeR.setStroke(15, Color.rgb(70, 70, 70));
        bordeR.setColor(Color.rgb(11, 11, 11));
        border.setStroke(15, Color.rgb(45, 45, 45));
        border.setColor(Color.rgb(5, 5, 5));
        border.setCornerRadius(3);
        bordeR.setCornerRadius(3);
        multiply1x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiply1x.setBackground(border);
                multiply5x.setBackground(bordeR);
                multiply100x.setBackground(bordeR);
                multiplymaxx.setBackground(bordeR);
                ((MainActivity) requireActivity()).setMultiply1();

            }
        });
        multiply5x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiply1x.setBackground(bordeR);
                multiply5x.setBackground(border);
                multiply100x.setBackground(bordeR);
                multiplymaxx.setBackground(bordeR);
                ((MainActivity) requireActivity()).setMultiply5();
            }
        });
        multiply100x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiply1x.setBackground(bordeR);
                multiply5x.setBackground(bordeR);
                multiply100x.setBackground(border);
                multiplymaxx.setBackground(bordeR);
                ((MainActivity) requireActivity()).setMultiply100();
            }
        });
        multiplymaxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiply1x.setBackground(bordeR);
                multiply5x.setBackground(bordeR);
                multiply100x.setBackground(bordeR);
                multiplymaxx.setBackground(border);

                ((MainActivity) requireActivity()).setMultiplymax();
            }
        });
        Upgrade[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkClickedButton((byte) 0);
            }
        });
        Upgrade[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkClickedButton((byte) 1);
            }
        });
        Upgrade[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkClickedButton((byte) 2);
            }
        });
        Upgrade[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkClickedButton((byte) 3);
            }
        });
        Upgrade[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkClickedButton((byte) 4);
            }
        });
        Upgrade[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkClickedButton((byte) 5);
            }
        });
        Upgrade[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkClickedButton((byte) 6);
            }
        });
        Upgrade[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkClickedButton((byte) 7);
            }
        });
        Upgrade[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkClickedButton((byte) 8);
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
        CurrentKrikoinProduction.setText(((MainActivity) requireActivity()).getUpdateCashDisplayText());
    }

    private void KPS() {
        KrikoinPerSecondProduction.setText(((MainActivity) requireActivity()).getKPSDisplayText());
    }

    private void updateUpgradeButton() {
        UpgradeCostSetText();
        descriptionSetText();
        amountUpgradeSetText();
    }

    private void amountUpgradeSetText() {
        String amountUpgradeMain[] = ((MainActivity) requireActivity()).amountUpgradeSetText();
        for (byte i = 0; i < amountUpgrades.length; i++) {
            amountUpgrades[i].setText(amountUpgradeMain[i] + "x");
        }
    }

    private void UpgradeCostSetText() {
        String[] UpgradeCostTool = ((MainActivity) requireActivity()).UpgradeCostSetText();
        for (byte i = 0; i < UpgradeCost.length; i++) {
            UpgradeCost[i].setText(UpgradeCostTool[i]);
        }
    }
    private void descriptionSetText() {
            String[] DescriptionTool = ((MainActivity) requireActivity()).DescriptionSetText();
            for (byte i = 0; i < Description.length; i++) {
                Description[i].setText(DescriptionTool[i] + " " + perSecond);
            }
    }

    private void setKriKoins() {
        ProductionKrikoin.setImageResource(krikoinIds[((MainActivity) requireActivity()).getChosenKrikoin()]);
        productionKrikoinPerSecond.setImageResource(krikoinIds[((MainActivity) requireActivity()).getChosenKrikoin()]);
    }

    private void setIcons() {
        for (byte i = 0; i < icons.length; i++) {
            icons[i].setImageResource(imageIds[i][((MainActivity) requireActivity()).getChosenKrikoinIcon(i)]);
        }
    }

    //——————————————————————————————
    //Arithmetic-like methods
    //——————————————————————————————


    //——————————————————————————————
    //Other methods
    //——————————————————————————————
    private void checkClickedButton(byte i) {
        ((MainActivity) requireActivity()).checkClickedButton(i);
        updateUpgradeButton();
        KPS();
        updateCash();
    }

    //——————————————————————————————

    private void load() {
        updateUpgradeButton();
        updateCash();
        setKriKoins();
        setIcons();
        if (((MainActivity) requireActivity()).multiplymax) {
            multiplymaxx.setBackground(border);
        } else if (((MainActivity) requireActivity()).multiply100) {
            multiply100x.setBackground(border);
        } else if (((MainActivity) requireActivity()).multiply5) {
            multiply5x.setBackground(border);
        } else {
            multiply1x.setBackground(border);
        }
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