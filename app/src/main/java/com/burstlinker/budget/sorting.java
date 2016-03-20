package com.burstlinker.budget;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Alan Solitar on 2016/03/18.
 */

public class sorting {

    public enum SORT_BY {NAME, DATE, PRICE};
    public static void SortByParameter(ArrayList<Purchase> list, SORT_BY sort) {


    }

    public class PriceCompare implements Comparator<Purchase> {
        @Override
        public int compare(Purchase lhs, Purchase rhs) {
            Float first = lhs.getPrice();
            Float second = rhs.getPrice();
            return first.compareTo(second);
        }
    }

    public class NameCompare implements Comparator<Purchase> {
        @Override
        public int compare(Purchase lhs, Purchase rhs) {
            return lhs.getName().compareTo(rhs.getName());
        }
    }


    public class DateCompare implements Comparator<Purchase> {
        @Override
        public int compare(Purchase lhs, Purchase rhs) {
            Long first  = lhs.getDate().;
            Long second = rhs.getDate();
            return first.compareTo(second);
        }
    }
}
