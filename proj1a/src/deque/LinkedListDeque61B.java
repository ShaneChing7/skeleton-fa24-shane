package deque;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class LinkedListDeque61B<T> implements Deque61B<T> {

    private final Node sentinel;
    private int size;


    private class Node {
        Node pre;
        Node next;
        T item;

        Node(Node pre, Node next, T item) {
            this.pre = pre;
            this.next = next;
            this.item = item;
        }

    }


    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.pre = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }


    @Override
    public void addFirst(T x) {
        Node firstNode = new Node(sentinel, sentinel.next, x);
        sentinel.next.pre = firstNode;
        sentinel.next = firstNode;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        Node lastNode = new Node(sentinel.pre, sentinel, x);
        sentinel.pre.next = lastNode;
        sentinel.pre = lastNode;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        Node pointer = sentinel.next;
        for (int i = 0; i < size; i++) {
            list.add(pointer.item);
            pointer = pointer.next;
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from an empty deque");
        }
        T x = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.pre = sentinel;
        size--;
        return x;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from an empty deque");
        }
        T x = sentinel.pre.item;
        Node pointer = sentinel.pre;
        pointer.pre.next = sentinel;
        sentinel.pre = pointer.pre;
        size--;
        return x;
    }

    @Override
    public T get(int index) {//index == 0 --> sentinel.next.item
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from an empty deque");
        }
        if(index >= size || index < 0){
            throw new IndexOutOfBoundsException("The index is out of bounds");
        }
        Node pointer = sentinel;
        int count = 0;
        while (count <= index) {
            pointer = pointer.next;
            count++;
        }
        return pointer.item;
    }

    @Override
    public T getRecursive(int index) {//index == 1 --> sentinel.next.item
        // 检查索引是否越界
        if(isEmpty()){
            return null;
        }
        if (index <= 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return getNodeRecursive(sentinel, index).item;
    }

    // 辅助递归函数，用于查找指定索引位置的节点
    private Node getNodeRecursive(Node currentNode, int remainingIndex) {
        if (remainingIndex == 0) {
            return currentNode;
        } else {
            return getNodeRecursive(currentNode.next, remainingIndex - 1);
        }
    }
}
