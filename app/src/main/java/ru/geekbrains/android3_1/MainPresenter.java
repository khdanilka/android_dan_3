package ru.geekbrains.android3_1;

import android.util.Log;
import android.widget.Button;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
        //listPrestenter.strings = stringsRepository.getStrings();


        Observable<String> observable =
                Observable.fromIterable(stringsRepository.getStrings())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        final Observer<String> listPrestenterObserver = new Observer<String>() {
            List<String> bufList;
            @Override
            public void onSubscribe(Disposable d) {
                bufList = new ArrayList<>();
            }

            @Override
            public void onNext(String s) {
                bufList.add(s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("ERROR","что-то пошло не так");
            }

            @Override
            public void onComplete() {
                 listPrestenter.strings = bufList;
            }
        };
        observable.subscribe(listPrestenterObserver);

        getViewState().updateList();
    }

    public void onButtonClick(int id, List<Button> buttonList)
    {
        int val = -1;
        for(int i = 0; i < buttonList.size(); i++){
            if (id == buttonList.get(i).getId()) {
//                val = counterModel.calculate(i);
                val = modelCalc(i);
                break;
            }
        }
        getViewState().setButtonValue(val, id);
    }
    private Observer observer;

    public Integer modelCalc(final Integer index){

        final Integer[] val = new Integer[1];
//        final Integer[] val = new Integer[1];
//        Observable.just(counterModel.calculate(index))
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        val[0] = integer;
//                    }
//                });
//        return val[0];

        Observable<Integer> justObservable = Observable
                .just(counterModel.calculate(index));
                //.subscribeOn(Schedulers.computation())
                //.observeOn(AndroidSchedulers.mainThread());

        justObservable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer s) throws Exception {
                //Log.d(TAG, "onNext: " + s + " 2");
                val[0] = s;
            }
        });

        return val[0];
    }



    public int getValueForButton(int index){
        return counterModel.valueByIndex(index);
    }

    public ListPrestenter getListPrestenter()
    {
        return listPrestenter;
    }
}
