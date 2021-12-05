package edu.neu.coe.huskySort.sort.radix;

import java.lang.reflect.Array;
import java.text.Collator;
import java.util.Arrays;
import java.util.OptionalInt;

/**
 * @author Caspar
 * @date 2021/12/5 03:28
 */
public class UnicodeLSDSort {

    public UnicodeLSDSort(final Collator collator) {
        this.collator = collator;
    }

    /**
     * Sort an array of Strings using MSDStringSort.
     *
     * @param a the array to be sorted.
     */
    public void sort(final String[] a) {
        final int n = a.length;
        aux = new String[n];
        byte[][] mKeys = Arrays.stream(a).map(e -> collator.getCollationKey(e).toByteArray()).toArray(byte[][]::new);
        Integer[] length = Arrays.stream(mKeys).map(Array::getLength).sorted().toArray(Integer[]::new);
        int maxKeyLength =  getMaxLength(length);
        int mKeyLength = Math.min(keyLength, maxKeyLength);
        keysAux = new byte[n][mKeyLength];
        keys = new byte[n][mKeyLength];
        //
        sort(a,  mKeyLength - 1);
        if (mKeyLength < maxKeyLength) {
            Arrays.sort(a, collator::compare);
        }
    }

    private int getMaxLength(Integer[] length) {
        return Arrays.stream(length).max(Integer::compareTo).orElse(0);
    }

    public Collator getCollator() {
        return collator;
    }

    public static void setKeyLength(final int keyLength) {
        UnicodeLSDSort.keyLength = keyLength;
    }

    private void sort(String[] a, int d) {

        int[] count = new int[R + 1];
        for (int i = 0; i < a.length; ++i) {
            count[charAt(i, d) + 1]++;
        }
        for (int r = 0; r < R; ++r) {
            count[r + 1] += count[r];
        }
        for (int i = 0; i < a.length; ++i) {
            aux[count[charAt(i, d)]] = a[i];
            keys[count[charAt(i, d)]++] = keysAux[i];
        }
        for (int i = 0; i < a.length; ++i) {
            a[i] = aux[i];
            keys[i] = keysAux[i];
        }

        if (d > 0) {
            sort(a, d - 1);
        }
    }

    private int charAt(int i, int d) {
        if (d > keys[i].length) {
            return 0;
        } else if (keys[i][d] < 0) {
            return 256 + keys[i][d];
        } else {
            return keys[i][d];
        }
    }

    private static String[] aux;       // auxiliary array for distribution
    private static int R = 256;
    private static byte[][] keys;
    private static byte[][] keysAux;
    private final Collator collator;
    private static int keyLength = 20;
}
