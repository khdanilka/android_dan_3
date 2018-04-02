package ru.geekbrains.android3_1;

public interface IListPresenter
{
    int pos = -1;

    void bindView(IListRowView view);
    int getViewCount();
}
