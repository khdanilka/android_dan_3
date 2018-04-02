package ru.geekbrains.android3_1;

import android.widget.Button;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface MainViewInterface extends MvpView
{
    void init();
    void setButtonValue(int val, int id);
    void updateList();
}
