package edu.neu.coe.huskySort.sort.radix;

import java.lang.reflect.Array;
import java.text.Collator;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Caspar
 * @date 2021/12/5 00:05
 */
public class UnicodeMSDSort {

    public UnicodeMSDSort(final Collator collator) {
        this.collator = collator;
    }

    public UnicodeMSDSort(final Collator collator, final int keyLengthCutoff) {
        this.collator = collator;
        customKeyLength = true;
        keyLength = keyLengthCutoff;
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
        final int n = a.length;
        preSort(a);
        sort(a,  0, n, 0);
        postSort(a);
    }

    private void preSort(final String[] a) {
        final int n = a.length;
        aux = new String[n];
        byte[][] mKeys = Arrays.stream(a).map(e -> collator.getCollationKey(e).toByteArray()).toArray(byte[][]::new);
        getKeyLength(mKeys);
        keysAux = new byte[n][keyLength];
        keys = new byte[n][];
        for (int i = 0; i < mKeys.length; ++i) {
            if (mKeys[i].length < keyLength) {
                keys[i] = mKeys[i];
            } else {
                keys[i] = Arrays.copyOfRange(mKeys[i], 0, keyLength);
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

    public static void setCutoff(final int cutoff) {
        UnicodeMSDSort.cutoff = cutoff;
    }

    /**
     * Sort from a[lo] to a[hi] (exclusive), ignoring the first d characters of each String.
     * This method is recursive.
     *
     * @param a  the array to be sorted.
     * @param lo the low index.
     * @param hi the high index (one above the highest actually processed).
     * @param d  the number of characters in each String to be skipped.
     */
    private void sort(final String[] a, final int lo, final int hi, final int d) {
        assert lo >= 0 : "lo " + lo + " is negative";
        assert hi <= a.length : "hi " + hi + " is out of bounds: " + a.length;
        if (hi < lo + cutoff) insertionSort(a, lo, hi);
        else {
            final int[] count = new int[R + 2];
            for (int i = lo; i < hi; i++) {
                final int x = charAt(i, d);
                count[x + 2]++;
            }
            for (int r = 0; r < R + 1; r++)      // Transform counts to indices.
                count[r + 1] += count[r];
            for (int i = lo; i < hi; i++) {
                aux[count[charAt(i, d) + 1]] = a[i];
                keysAux[count[charAt(i, d) + 1]++] = keys[i];
            }

            // Copy back.
            if (hi - lo >= 0) {
                System.arraycopy(aux, 0, a, lo, hi - lo);
                System.arraycopy(keysAux, 0, keys, lo, hi - lo);
            }

            // Recursively sort for each character value.
            for (int r = 0; r < R; r++)
                sort(a, lo + count[r], lo + count[r + 1], d + 1);
        }
    }

    private void insertionSort(final String[] a, final int lo, final int hi) {
        for (int i = lo; i < hi; i++)
            for (int j = i; j > lo && less(a[j], a[j - 1]); j--)
                swap(a, j, j - 1);
    }

    private boolean less(final int i, final int j, final int d) {
        return charAt(i, d) < charAt(j, d);
    }

    private boolean less(final String str1, final String str2) {
        return getCollator().compare(str1, str2) < 0;
    }

    private void swap(final Object[] a, final int j, final int i) {
        final Object temp = a[j];
        a[j] = a[i];
        a[i] = temp;
        swapKeys(j, i);
    }

    private  void swapKeys(final int j, final int i) {
        byte[] tmp = keys[i];
        keys[i] = keys[j];
        keys[j] = tmp;
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

    private static int cutoff = 15;
    private static String[] aux;       // auxiliary array for distribution
    private static int R = 256;
    private static byte[][] keys;
    private static byte[][] keysAux;
    private final Collator collator;

    private static int keyLength = 20;
    private static boolean customKeyLength = false;
    private static boolean secondSort = false;

}
