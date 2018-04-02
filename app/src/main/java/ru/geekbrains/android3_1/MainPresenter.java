package ru.geekbrains.android3_1;

import android.util.Log;
import android.widget.Button;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainViewInterface>
{
    private static final String TAG = "MainPresenter";

    class ListPrestenter implements IListPresenter
    {
        List<String> strings = new ArrayList<>();
        @Override
        public void bindView(IListRowView view)
        {
            view.setText(strings.get(view.getPos()));
        }

        @Override
        public int getViewCount()
        {
            return strings.size();
        }
    }

    CounterModel counterModel = new CounterModel();
    StringsRepository stringsRepository = new StringsRepository();
    ListPrestenter listPrestenter = new ListPrestenter();

    public MainPresenter(String arg)
    {
        Log.d(TAG, arg);
    }

    @Override
    protected void onFirstViewAttach()
    {
        super.onFirstViewAttach();
        getViewState().init();
        listPrestenter.strings = stringsRepository.getStrings();
        getViewState().updateList();
    }

    public void onButtonClick(int id, List<Button> buttonList)
    {
        int val = -1;
        for(int i = 0; i < buttonList.size(); i++){
            if (id == buttonList.get(i).getId()) {
                val = counterModel.calculate(i);
                break;
            }
        }
//        switch (id)
//        {
//            case R.id.btn_one:
//                val = counterModel.calculate(0);
//                break;
//            case R.id.btn_two:
//                val = counterModel.calculate(1);
//                break;
//            case R.id.btn_three:
//                val = counterModel.calculate(2);
//                break;
//        }

        getViewState().setButtonValue(val, id);
    }

    public int getValueForButton(int index){
        return counterModel.valueByIndex(index);
    }

    public ListPrestenter getListPrestenter()
    {
        return listPrestenter;
    }
}
