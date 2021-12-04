//package edu.neu.coe.huskySort.sort.keySort.stringSortUtils;
//
//import edu.neu.coe.huskySort.sort.Helper;
//
//import java.util.Random;
//import java.util.function.Function;
//
//import static java.util.Arrays.binarySearch;
//
///**
// * @author Caspar
// * @date 2021/12/3 22:58
// */
//
///**
// * KeyHelper interface.
// * <p>
// * A KeyHelper provides all utilities that are needed by key sort methods. The difference between KeyHelper and Helper
// * is that we compare the elements according to the keys of elements, and we have to move the keys when moving the
// * elements.
// * The keys and elements have to be stored in the Helper instance. When we call the functions, we don't need the
// * array as the input param.
// * <p>
// *
// *
// * @param <X>
// */
//public interface KeyHelper<X extends Comparable<X>> {
//
//    /**
//     * @return true if this is an instrumented Helper.
//     */
//    boolean instrumented();
//
//    /**
//     * Compare elements i and j of xs within the subarray lo..hi
//     *
//     * @param i  one of the indices.
//     * @param j  the other index.
//     * @return the result of comparing xs[i] to xs[j]
//     */
//    int compare(int i, int j);
//
//    /**
//     * Compare values v and w and return true if v is less than w.
//     *
//     * @param i the index of the first value.
//     * @param j the second value.
//     * @return true if v is less than w.
//     */
//    boolean less(int i, int j);
//
//
//    /**
//     * Method to perform a general swap, i.e. between xs[i] and xs[j]
//     *
//     * @param i  the index of the lower of the elements to be swapped.
//     * @param j  the index of the higher of the elements to be swapped.
//     */
//    void swap(int i, int j);
//
//    /**
//     * Method to perform a stable swap, i.e. between xs[i] and xs[i-1]
//     *
//     * @param i  the index of the higher of the adjacent elements to be swapped.
//     */
//    default void swapStable(int i) {
//        swap(i - 1, i);
//    }
//
//    /**
//     * Method to perform a stable swap, but only if xs[i] is less than xs[i-1], i.e. out of order.
//     *
//     * @param i  the index of the lower element.
//     * @param j  the index of the upper element.
//     * @return true if there was an inversion (i.e. the order was wrong and had to be be fixed).
//     */
//    default boolean swapConditional(int i, int j) {
//        boolean result = less(i, j);
//        if (result) {
//            swap(i, j);
//        }
//        return result;
//    }
//
//    /**
//     * Method to perform a stable swap, but only if xs[i] is less than xs[i-1], i.e. out of order.
//     *
//     * @param i  the index of the upper element.
//     * @return true if there was an inversion (i.e. the order was wrong and had to be be fixed).
//     */
//    default boolean swapStableConditional(int i) {
//        boolean result = less(i, i - 1);
//        if (result) {
//            swap(i, i - 1);
//        }
//        return result;
//    }
//
//    /**
//     * Method to perform a stable swap using half-exchanges,
//     * i.e. between xs[i] and xs[j] such that xs[j] is moved to index i,
//     * and xs[i] thru xs[j-1] are all moved up one.
//     * This type of swap is used by insertion sort.
//     * <p>
//     * TODO this method does not seem to work.
//     *
//     * @param i  the index of the destination of xs[j].
//     * @param j  the index of the right-most element to be involved in the swap.
//     */
//    void swapInto(int i, int j);
//
//    /**
//     * Method to perform a stable swap using half-exchanges, and binary search.
//     * i.e. [i] is moved leftwards to its proper place and all elements from
//     * the destination of x[i] thru x[i-1] are moved up one place.
//     * This type of swap is used by insertion sort.
//     *
//     * @param keys the array of elements' keys, keys from 0 to i - 1 MUST be sorted.
//     * @param i  the index of the element to be swapped into the ordered array xs[0..i-1].
//     */
//    default void swapIntoSorted(X[] keys, int i) {
//        int j = binarySearch(keys, 0, i, keys[i]);
//        if (j < 0) j = -j - 1;
//        if (j < i) swapInto(j, i);
//    }
//
//    /**
//     * Copy the element at source[j] into target[i]
//     *
//     * @param source the source array.
//     * @param i      the target index.
//     * @param target the target array.
//     * @param j      the source index.
//     */
//    void copy(X[] target, int i, int j, );
//
//    /**
//     * TODO eliminate this method as it has been superseded by swapConditional. However, maybe the latter is a better name.
//     * Method to fix a potentially unstable inversion.
//     *
//     * @param xs the array of X elements.
//     * @param i  the index of the lower of the elements to be swapped.
//     * @param j  the index of the higher of the elements to be swapped.
//     */
//    default void fixInversion(X[] xs, int i, int j) {
//        swapConditional(xs, i, j);
//    }
//
//    /**
//     * TODO eliminate this method as it has been superseded by swapStableConditional. However, maybe the latter is a better name.
//     * Method to fix a stable inversion.
//     *
//     * @param xs the array of X elements.
//     * @param i  the index of the higher of the adjacent elements to be swapped.
//     */
//    default void fixInversion(X[] xs, int i) {
//        swapStableConditional(xs, i);
//    }
//
//    /**
//     * Return true if xs is sorted, i.e. has no inversions.
//     *
//     * @param xs an array of Xs.
//     * @return true if there are no inversions, else false.
//     */
//    boolean sorted(X[] xs);
//
//    /**
//     * Count the number of inversions of this array.
//     *
//     * @param xs an array of Xs.
//     * @return the number of inversions.
//     */
//    int inversions(X[] xs);
//
//    /**
//     * Method to post-process the array xs after sorting.
//     *
//     * @param xs the array that has been sorted.
//     */
//    void postProcess(X[] xs);
//
//    /**
//     * Method to generate an array of randomly chosen X elements.
//     *
//     * @param clazz the class of X.
//     * @param f     a function which takes a Random and generates a random value of X.
//     * @return an array of X of length determined by the current value according to setN.
//     */
//    X[] random(Class<X> clazz, Function<Random, X> f);
//
//    /**
//     * @return the description of this Helper.
//     */
//    String getDescription();
//
//    default int cutoff() {
//        return 7;
//    }
//
//    /**
//     * Initialize this Helper with the size of the array to be managed.
//     *
//     * @param n the size to be managed.
//     */
//    void init(int n);
//
//    /**
//     * Get the current value of N.
//     *
//     * @return the value of N.
//     */
//    int getN();
//
//    /**
//     * Close this Helper, freeing up any resources used.
//     */
//    void close();
//
//    /**
//     * If instrumenting, increment the number of copies by n.
//     *
//     * @param n the number of copies made.
//     */
//    default void incrementCopies(int n) {
//        // do nothing.
//    }
//
//    /**
//     * If instrumenting, increment the number of fixes by n.
//     *
//     * @param n the number of copies made.
//     */
//    default void incrementFixes(int n) {
//        // do nothing.
//    }
//
//    /**
//     * Method to do any required preProcessing.
//     *
//     * @param xs the array to be sorted.
//     * @return the array after any pre-processing.
//     */
//    default X[] preProcess(X[] xs) {
//        // CONSIDER invoking init from here.
//        return xs;
//    }
//
//    default void registerDepth(int depth) {
//    }
//
//    default int maxDepth() {
//        return 0;
//    }
//
//}
