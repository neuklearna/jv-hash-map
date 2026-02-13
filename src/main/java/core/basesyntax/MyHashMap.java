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

        int result = getBucketIndex(key);
        if (tablica[result] == null) {
            tablica[result] = new Node<>(key, value, null);
            size++;
        } else {
            Node<K, V> currentnode = tablica[result];
            while (currentnode != null) {
                if (Objects.equals(currentnode.key, key)) {
                    currentnode.value = value;
                    return;
                }
                currentnode = currentnode.next;
            }
            tablica[result] = new Node<>(key, value, tablica[result]);
            size++;
        }
    }

    @Override
    public V getValue(K key) {
        int currentNodeIndex = getBucketIndex(key);
        Node<K, V> current = tablica[currentNodeIndex];
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
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
                put(node.key, node.value);
                node = node.next;
            }
        }
    }

    public V remove(K key) {
        int result = getBucketIndex(key);
        Node<K, V> current = tablica[result];
        Node<K, V> prev = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                if (prev == null) {
                    tablica[result] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value; // Dodano, żeby wyjść z metody z sukcesem
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    private int getBucketIndex(K key) {
        return (key == null) ? 0 : Math.abs(key.hashCode() % tablica.length);
    }

    // Klasa Node, której brakowało w Twoim pliku:
    private static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;

        private Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
