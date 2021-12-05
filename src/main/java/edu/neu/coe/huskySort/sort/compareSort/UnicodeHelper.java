package edu.neu.coe.huskySort.sort.compareSort;

import edu.neu.coe.huskySort.sort.BaseHelper;

import java.text.Collator;



/**
 * @author Caspar
 * @date 2021/12/5 01:25
 */
public class UnicodeHelper extends BaseHelper<String> {

    private final Collator collator;

    public UnicodeHelper(final String description, final Collator collator) {
        super(description);
        this.collator = collator;
    }

    @Override
    public boolean instrumented() {
        return true;
    }

    @Override
    public int compare(String[] xs, int i, int j) {
        return compare(xs[i], xs[j]);
    }

    @Override
    public boolean less(String v, String w) {
        return compare(v, w) < 0;
    }

    @Override
    public int compare(String v, String w) {
        return collator.compare(v, w);
    }

    @Override
    public boolean sorted(String[] xs) {
        for (int i = 0; i < xs.length - 1; ++i) {
            if (less(xs[i + 1], xs[i])) {
                return false;
            }
        }
        return true;
    }
}
