package edu.neu.coe.huskySort.sort.msdSort;

/**
 * @author Caspar
 * @date 2021/12/2 03:15
 */
public class Pair<X extends Comparable<X>, Y> implements Comparable<Pair>{

    X key;
    Y value;

    public Pair(X key, Y value) {
        setKey(key);
        setValue(value);
    }

    public X getKey() {
        return key;
    }

    public Y getValue() {
        return value;
    }

    public void setKey(X key) {
        this.key = key;
    }

    public void setValue(Y value) {
        this.value = value;
    }

    @Override
    public int compareTo(Pair p) {
        return this.key.compareTo((X) p.key);
    }
}
