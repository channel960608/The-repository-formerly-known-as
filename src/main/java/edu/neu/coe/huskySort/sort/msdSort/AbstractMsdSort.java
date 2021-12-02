package edu.neu.coe.huskySort.sort.msdSort;

import edu.neu.coe.huskySort.sort.SortWithHelper;
import edu.neu.coe.huskySort.util.Config;

/**
 * @author Caspar
 * @date 2021/11/27 15:09
 */
public abstract class AbstractMsdSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Constructor for AbstractHuskySort
     *
     * @param name       name of sorter.
     */
    protected AbstractMsdSort(String name, int n, Config config) {
        super(name, n, config);
        this.name = name;
    }

    protected final String name;
}
