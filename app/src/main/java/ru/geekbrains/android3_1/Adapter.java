package ru.geekbrains.android3_1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>
{
    IListPresenter presenter;

    public Adapter(IListPresenter presenter)
    {
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.pos = position;
        presenter.bindView(holder);
    }

    @Override
    public int getItemCount()
    {
        return presenter.getViewCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements IListRowView
    {
        int pos = -1;
        @BindView(R.id.tv_title) TextView titleTextView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public int getPos()
        {
            return pos;
        }

        @Override
        public void setText(String text)
        {
            titleTextView.setText(text);
        }
    }
}
