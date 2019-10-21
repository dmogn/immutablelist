package com.github.dmogn.immutable;

import java.util.ListIterator;

/**
 * Immutable java.util.ListIterator implementation.
 * 
 * All methods are thread-safe.
 */
public class ImmutableListIterator<E> implements ListIterator<E> {
    
    private final ImmutableList<E> list;
    
    private int currentIndex = 0;
    
    protected ImmutableListIterator(ImmutableList<E> list) {
        this.list = list;
    }
    
    protected ImmutableListIterator(ImmutableList<E> list, int currentIndex) {
        this.list = list;
        this.currentIndex = currentIndex;
    }

    @Override
    public boolean hasNext() {
        return currentIndex+1 < list.size();
    }

    @Override
    public E next() {
        currentIndex++;
        return list.get(currentIndex);
    }

    @Override
    public boolean hasPrevious() {
        return currentIndex >  0;
    }

    @Override
    public E previous() {
        currentIndex--;
        return list.get(currentIndex); 
    }

    @Override
    public int nextIndex() {
        return currentIndex+1;
    }

    @Override
    public int previousIndex() {
        return currentIndex-1;
    }

    @Deprecated
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported oprtation on immutable list."); 
    }

    @Deprecated
    @Override
    public void set(E e) {
        throw new UnsupportedOperationException("Not supported oprtation on immutable list."); 
    }

    @Deprecated
    @Override
    public void add(E e) {
        throw new UnsupportedOperationException("Not supported oprtation on immutable list."); 
    }
    
}
