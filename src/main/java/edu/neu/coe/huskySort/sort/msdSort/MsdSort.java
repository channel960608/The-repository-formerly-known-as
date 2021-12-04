package edu.neu.coe.huskySort.sort.msdSort;

import edu.neu.coe.huskySort.sort.huskySortUtils.HuskySortHelper;
import edu.neu.coe.huskySort.util.LazyLogger;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

import static java.util.Arrays.binarySearch;

/**
 * This class represents the purest form of Husky Sort based on IntroSort for pass 1 and the System sort for pass 2.
 * <p>
 * CONSIDER redefining all of the "to" parameters to be consistent with our other Sort utilities.
 *
 * @param <I> the type of the elements to be sorted.
 */
public class MsdSort<I extends CharSequence, O extends CharSequence & Comparable<O>> {

    public static void main(final String[] args) {

        final int N = 50000;
        final int m = 1;
        logger.info("MsdSort.main: sorting " + N + " random alphabetic ASCII words " + m + " times");
        // Just for test purpose: this should take about 3 minutes
        final MsdSort<String, String> sorter = new MsdSort<>(TranslatorFactory.CHINESE_TRANSLATOR, false, false);
        for (int i = 0; i < m; i++) {
            final String[] alphaBetaArray = new String[]{"陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑"};
            sorter.sort(alphaBetaArray);
            Arrays.stream(alphaBetaArray).forEach(System.out::println);
        }
        logger.info("PureHuskySort.main: finished");
    }

    /**
     * The main sort method.
     *
     * @param xs the array to be sorted.
     */
    public void sort(final I[] xs) {
        // NOTE: we start with a random shuffle
        // This is necessary if we might be sorting a pre-sorted array. Otherwise, we usually don't need it.
        if (mayBeSorted) Collections.shuffle(Arrays.asList(xs));
        // NOTE: First pass where we code to pinyin and sort according to those.
        final O[] pinyins = msdTranslator.batchDecode(xs);
//        introSort(xs, longs, 0, longs.length, 2 * floor_lg(xs.length));
        final O[] oaux = (O[]) Array.newInstance(pinyins[0].getClass(), xs.length);
        final I[] iaux = (I[]) Array.newInstance(xs[0].getClass(), xs.length);
        radixSort(pinyins, oaux, xs, iaux, 0, xs.length, 0, 6);
    }

    /**
     * Primary constructor.
     *
     * @param msdTranslator    the translator to be used to  translate ASCII as alphabet.
     * @param mayBeSorted      if this is true, then we should perform a random shuffle to prevent an O(N*N) performance.
     *                         NOTE: that even though we are using IntroSort, the random shuffle precaution is necessary when
     * @param useInsertionSort if true, then insertion sort will be used to mop up remaining inversions instead of system sort.
     */
    public MsdSort(final UnicodeTranslator<I, O> msdTranslator, final boolean mayBeSorted, final boolean useInsertionSort) {
        this.msdTranslator = msdTranslator;
        this.mayBeSorted = mayBeSorted;
        this.useInsertionSort = useInsertionSort;
    }

    // CONSIDER invoke method in IntroSort
    private static int floor_lg(final int a) {
        return (int) (Math.floor(Math.log(a) / Math.log(2)));
    }

    private static final int sizeThreshold = 16;

    // TEST
    @SuppressWarnings({"UnnecessaryLocalVariable"})
//    private void introSort(final I[] objects, final long[] longs, final int from, final int to, final int depthThreshold) {
//        // CONSIDER merge with IntroHuskySort
//        if (to - from <= sizeThreshold + 1) {
//            insertionSort(objects, longs, from, to);
//            return;
//        }
//        if (depthThreshold == 0) {
//            heapSort(objects, longs, from, to);
//            return;
//        }
//
//        final int lo = from;
//        final int hi = to - 1;
//
//        if (longs[hi] < longs[lo]) swap(objects, longs, lo, hi);
//
//        int lt = lo + 1, gt = hi - 1;
//        int i = lo + 1;
//        while (i <= gt) {
//            if (longs[i] < longs[lo]) swap(objects, longs, lt++, i++);
//            else if (longs[hi] < longs[i]) swap(objects, longs, i, gt--);
//            else i++;
//        }
//        swap(objects, longs, lo, --lt);
//        swap(objects, longs, hi, ++gt);
//        introSort(objects, longs, lo, lt, depthThreshold - 1);
//        if (longs[lt] < longs[gt]) introSort(objects, longs, lt + 1, gt, depthThreshold - 1);
//        introSort(objects, longs, gt + 1, hi + 1, depthThreshold - 1);
//    }

    // TEST
//    private void heapSort(final X[] objects, final long[] longs, final int from, final int to) {
//        // CONSIDER removing these size checks. They haven't really been tested.
//        if (to - from <= sizeThreshold + 1) {
//            insertionSort(objects, longs, from, to);
//            return;
//        }
//        final int n = to - from;
//        for (int i = n / 2; i >= 1; i = i - 1) {
//            downHeap(objects, longs, i, n, from);
//        }
//        for (int i = n; i > 1; i = i - 1) {
//            swap(objects, longs, from, from + i - 1);
//            downHeap(objects, longs, 1, i - 1, from);
//        }
//    }

    // TEST
//    private void downHeap(final X[] objects, final long[] longs, int i, final int n, final int lo) {
//        final long d = longs[lo + i - 1];
//        final X od = objects[lo + i - 1];
//        int child;
//        while (i <= n / 2) {
//            child = 2 * i;
//            if (child < n && longs[lo + child - 1] < longs[lo + child]) child++;
//            if (d >= longs[lo + child - 1]) break;
//            longs[lo + i - 1] = longs[lo + child - 1];
//            objects[lo + i - 1] = objects[lo + child - 1];
//            i = child;
//        }
//        longs[lo + i - 1] = d;
//        objects[lo + i - 1] = od;
//    }

    void insertionSort(final I[] is, final O[] os, final int from, final int to) {
        for (int i = from + 1; i < to; i++)
            if (OPTIMIZED)
                swapIntoSorted(is, os, i);
            else
                for (int j = i; j > from && os[j].compareTo(os[j - 1]) < 0; j--)
                    swap(is, os, j, j - 1);
    }

    void radixSort(final O[] os, final O[] oaux ,final I[] is, final I[] iaux, final int from, final int to, int d) {
        radixSort(os, oaux, is, iaux, from, to, d, 1);
    }

    void radixSort(final O[] os, final O[] oaux ,final I[] is, final I[] iaux, final int from, final int to, int d, int cutoff) {
        if (to <= from + 1) {
            return;
        }
        if (cutoff > 0 && to <= from + cutoff) {
            insertionSort(is, os, from, to);
            return;
        }
        int[] count = new int[Radix + 1];
        for (int i = from; i < to; i++) {
            count[charAt(os[i], d) + 1]++;
        }
        for (int r = 0; r < Radix; r++) {
            count[r + 1] += count[r];
        }
        for (int i = from; i < to; i++) {
            oaux[count[charAt(os[i], d)]] = os[i];
            iaux[count[charAt(os[i], d)]++] = is[i];
        }
        for (int i = from; i < to; i++) {
            os[i] = oaux[i];
            is[i] = iaux[i];
        }
        if (to - from == count[1]) {
            return;
        }
        for (int r = 1; r < Radix && count[r] < os.length; r++) {
            radixSort(os, oaux, is, iaux, from + count[r], from + count[r + 1], d + 1);
        }
    }

    private int charAt(O o, int d) {
        if (null == o || d > o.length()) {
            return 0;
        }
        return o.charAt(d) - 'a' + 1;
    }

    /**
     * Regular swap of elements at indexes i and j, not necessarily adjacent.
     * However, for insertion sort, they will always be adjacent.
     *
     * @param is    the I array.
     * @param os    the O array.
     * @param i     the index of one element to be swapped.
     * @param j     the index of the other element to be swapped.
     */
    private void swap(final I[] is, final O[] os, final int i, final int j) {
        // Swap Os
        final O tempO = os[i];
        os[i] = os[j];
        os[j] = tempO;
        // Swap Is
        final I tempI = is[i];
        is[i] = is[j];
        is[j] = tempI;
    }

    /**
     * Swap method for insertion sort which takes advantage of the known fact that the elements of the array
     * at indices less than i are in order.
     *
     * @param is    the I array.
     * @param os the O array.
     * @param i     the index of the element to be moved.
     */
    private void swapIntoSorted(final I[] is, final O[] os, final int i) {
        int j = binarySearch(os, 0, i, os[i]);
        if (j < 0) j = -j - 1;
        if (j < i) swapInto(is, os, j, i);
    }

    /**
     * Swap method which uses half-swaps.
     *
     * @param is    the I array.
     * @param os    the O array.
     * @param i     the index of the element to be moved.
     * @param j     the index of the destination of that element.
     */
    void swapInto(final I[] is, final O[] os, final int i, final int j) {
        if (j > i) {
            final I x = is[j];
            System.arraycopy(is, i, is, i + 1, j - i);
            is[i] = x;
            final O l = os[j];
            System.arraycopy(os, i, os, i + 1, j - i);
            os[i] = l;
        }
    }

    private UnicodeTranslator<I, O> getMsdTranslator() {
        return msdTranslator;
    }

    // NOTE that we keep this false because, for the size of arrays that we need to sort via insertion sort,
    // This optimization doesn't really help.
    // That might be because (a) arrays are short and (b) the binary search will likely take quite a bit longer than
    // necessary when the array is already close to being in order (since binary search starts in the middle).
    // It would be like looking up aardvark in the dictionary using strict binary search.
    private static final boolean OPTIMIZED = false;

    private final UnicodeTranslator<I, O> msdTranslator;
    private final boolean mayBeSorted;
    private final boolean useInsertionSort;
    private final int Radix = 27;
    private final static LazyLogger logger = new LazyLogger(MsdSort.class);
}
