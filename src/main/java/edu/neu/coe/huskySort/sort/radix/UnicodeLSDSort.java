package edu.neu.coe.huskySort.sort.radix;

import java.lang.reflect.Array;
import java.text.Collator;
import java.util.Arrays;

/**
 * @author Caspar
 * @date 2021/12/5 03:28
 */
public class UnicodeLSDSort {

    public UnicodeLSDSort(final Collator collator, final int keyLengthCutoff) {
        this.collator = collator;
        setKeyLength(keyLengthCutoff);
        customKeyLength = true;
    }

    public UnicodeLSDSort(final Collator collator) {
        this.collator = collator;
    }

    public void reset() {
        aux = null;
        keysAux = null;
        keys = null;
        keyLength = 20;
        customKeyLength = false;
        secondSort = false;
    }
    /**
     * Sort an array of Strings using MSDStringSort.
     *
     * @param a the array to be sorted.
     */
    public void sort(final String[] a) {
        preSort(a);
        sort(a,  keyLength - 1);
        postSort(a);
    }

    private void preSort(final String[] a) {
        final int n = a.length;
        aux = new String[n];
        byte[][] mKeys = Arrays.stream(a).map(e -> collator.getCollationKey(e).toByteArray()).toArray(byte[][]::new);
        getKeyLength(mKeys);
        keysAux = new byte[n][keyLength];
        keys = new byte[n][keyLength];
        for (int i = 0; i < mKeys.length; ++i) {
            keys[i] = new byte[keyLength];
            for (int j = 0; j < keyLength && j < mKeys[i].length; ++j) {
                keys[i][j] = mKeys[i][j];
            }
        }
    }

    private void postSort(final String[] a) {
        if (secondSort) {
            Arrays.sort(a, collator::compare);
        }
    }

    private void getKeyLength(byte[][] keys) {
        Integer[] lengths = Arrays.stream(keys).map(Array::getLength).sorted().toArray(Integer[]::new);
        if (!customKeyLength) {
            keyLength = lengths[lengths.length / 2];
        }
        secondSort = keyLength < lengths[lengths.length - 1];
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
            keysAux[count[charAt(i, d)]++] = keys[i];
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
    private static boolean customKeyLength = false;
    private static boolean secondSort = false;
}
