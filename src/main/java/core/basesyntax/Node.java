package core.basesyntax;

public class Node <K,V> {
    final K key;
    V value;
    Node <K,V> next;

    public Node(K key, V value, Node <K,V> next) {
        this.key = key;
        this.next = next;
        this.value = value;
    }
}
