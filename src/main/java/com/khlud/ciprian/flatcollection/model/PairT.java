package com.khlud.ciprian.flatcollection.model;

/**
 * Created by Ciprian on 2/27/2016.
 */
public class PairT<K, V> {
    public K _key;
    public V _value;

    public K getKey() {
        return _key;
    }

    public V getValue() {
        return _value;
    }

    public PairT(K key, V value) {
        this._key = key;
        this._value = value;
    }

    @Override
    public String toString() {
        return _key.toString() + "->" + _value.toString();
    }
}
