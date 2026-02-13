package core.basesyntax;

import java.util.Objects;

public class MyHashMap<K, V> implements MyMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private Node<K, V>[] tablica;
    private int size;

    public MyHashMap() {
        tablica = new Node[DEFAULT_INITIAL_CAPACITY];
    }

    @Override
    public void put(K key, V value) {
        if (size >= tablica.length * DEFAULT_LOAD_FACTOR) {
            resize();
        }

        int index = getBucketIndex(key);
        Node<K, V> current = tablica[index];

        while (current != null) {
            if (Objects.equals(current.getKey(), key)) {
                current.setValue(value);
                return;
            }
            current = current.getNext();
        }

        tablica[index] = new Node<>(key, value, tablica[index]);
        size++;
    }

    @Override
    public V getValue(K key) {
        int index = getBucketIndex(key);
        Node<K, V> current = tablica[index];
        while (current != null) {
            if (Objects.equals(current.getKey(), key)) {
                return current.getValue();
            }
            current = current.getNext();
        }
        return null;
    }

    @Override
    public int getSize() {
        return size;
    }

    private void resize() {
        Node<K, V>[] oldTablica = tablica;
        tablica = new Node[oldTablica.length * 2];
        size = 0;

        for (Node<K, V> node : oldTablica) {
            while (node != null) {
                put(node.getKey(), node.getValue());
                node = node.getNext();
            }
        }
    }

    private int getBucketIndex(K key) {
        return (key == null) ? 0 : Math.abs(key.hashCode() % tablica.length);
    }
}
