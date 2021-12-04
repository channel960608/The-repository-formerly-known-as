package edu.neu.coe.huskySort.sort.keySort;

/**
 * @author Caspar
 * @date 2021/12/3 16:46
 */
public interface BaseCoder<X, K extends Comparable<K>> {

    /**
     * @return the name of this coder.
     */
    default String name() {
        return "BaseCoder";
    }

    /**
     * Encode x as a long.
     * As much as possible, if x > y, huskyEncode(x) > huskyEncode(y).
     * If this cannot be guaranteed, then the result of imperfect(z) will be true.
     *
     * @param x the X value to encode.
     * @return a long which is, as closely as possible, monotonically increasing with the domain of X values.
     */
    K encode(X x);

    /**
     * Encode an array of Xs.
     *
     * @param xs an array of X elements.
     * @return an array of longs corresponding to the the Husky codes of the X elements.
     */
    K[] arrayEncode(X[] xs);
}
