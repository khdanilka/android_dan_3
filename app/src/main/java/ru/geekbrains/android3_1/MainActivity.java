package ru.geekbrains.android3_1;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MvpAppCompatActivity implements MainViewInterface
{
    @BindView(R.id.btn_one) Button buttonOne;
    @BindView(R.id.btn_two) Button buttonTwo;
    @BindView(R.id.btn_three) Button buttonThree;
    List<Button> buttonList = new ArrayList<>();
    @BindView(R.id.rv) RecyclerView recyclerView;

    @InjectPresenter MainPresenter presenter;

    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        buttonList.add(buttonOne);
        buttonList.add(buttonTwo);
        buttonList.add(buttonThree);
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter()
    {
        String string = "hello";
        return new MainPresenter(string);
    }

    @OnClick({R.id.btn_one, R.id.btn_two, R.id.btn_three})
    public void onButtonClick(Button button)
    {
        presenter.onButtonClick(button.getId(),buttonList);
    }

    @Override
    public void init()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new Adapter(presenter.getListPrestenter());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setButtonValue(int val, int id)
    {
//        switch (id)
//        {
//            case R.id.btn_one:
//                buttonOne.setText(String.format(getString(R.string.count_format), val));
//                break;
//            case R.id.btn_two:
//                buttonTwo.setText(String.format(getString(R.string.count_format), val));
//                break;
//            case R.id.btn_three:
//                buttonThree.setText(String.format(getString(R.string.count_format), val));
//                break;
//        }
        for(int i = 0; i < buttonList.size(); i++){
            int count = -1;
            if (id == buttonList.get(i).getId()) count = val;
            else count = presenter.getValueForButton(i);
            buttonList.get(i).setText(String.format(getString(R.string.count_format), count));
        }
    }

    @Override
    public void updateList()
    {
        adapter.notifyDataSetChanged();
    }
}
