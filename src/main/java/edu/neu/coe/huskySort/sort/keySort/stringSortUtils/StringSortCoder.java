package edu.neu.coe.huskySort.sort.keySort.stringSortUtils;

import edu.neu.coe.huskySort.sort.huskySortUtils.Coding;
import edu.neu.coe.huskySort.sort.huskySortUtils.HuskyCoder;

/**
 * @author Caspar
 * @date 2021/12/3 15:29
 */
public class StringSortCoder<X> implements HuskyCoder {

    @Override
    public String name() {
        return HuskyCoder.super.name();
    }

    @Override
    public long huskyEncode(Object o) {
        return 0;
    }

    @Override
    public Coding huskyEncode(Object[] xs) {
        return HuskyCoder.super.huskyEncode(xs);
    }

    @Override
    public boolean perfect() {
        return HuskyCoder.super.perfect();
    }
}
