package edu.neu.coe.huskySort.sort.compareSort;

import edu.neu.coe.huskySort.sort.simple.TimSort;

import java.util.Arrays;

/**
 * @author Caspar
 * @date 2021/12/5 03:05
 */
public class UnicodeTimSort extends TimSort<String> {

    @Override
    public void sort(final String[] xs, final int from, final int to) {
        Arrays.sort(xs, from, to, (e1, e2) -> getHelper().compare(e1, e2));
    }

    @Override
    public String[] sort(final String[] xs) {
        preSort(xs, false);
        sort(xs, 0, xs.length);
        return xs;
    }


    public static final String DESCRIPTION = "UnicodeTimsort";

    /**
     * Constructor for TimSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public UnicodeTimSort(final UnicodeHelper helper) {
        super(helper);
    }

}
