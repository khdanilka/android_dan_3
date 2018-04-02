package ru.geekbrains.android3_1;

import java.util.ArrayList;
import java.util.List;

public class StringsRepository
{
    List<String> strings;

    public StringsRepository()
    {
        strings = new ArrayList<>();
        strings.add("string1");
        strings.add("string2");
        strings.add("string3");
        strings.add("string4");
        strings.add("string5");
        strings.add("string6");
        strings.add("string7");
        strings.add("string8");
        strings.add("string9");
        strings.add("string10");
    }

    public List<String> getStrings()
    {
        return strings;
    }

    public int getCount()
    {
        return strings.size();
    }
}
