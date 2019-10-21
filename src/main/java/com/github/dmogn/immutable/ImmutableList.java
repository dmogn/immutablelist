package com.github.dmogn.immutable;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Immutable java.util.List implementation.
 * 
 * All methods are thread-safe.
 */
public class ImmutableList<E> implements List<E>  {
    
    final E[] array;
    
    public ImmutableList(E[] array) {
        Objects.requireNonNull(array);
        this.array = Arrays.copyOf(array, array.length);
    }
    
    public ImmutableList(E[] array, int fromIndex, int toIndex) {
        Objects.requireNonNull(array);
        this.array = Arrays.copyOfRange(array, fromIndex, toIndex);
    }
    
    public ImmutableList(Collection<E> collection) {
        Objects.requireNonNull(collection);
        this.array = collection.toArray((E[]) new Object[collection.size()]);
    }
    
    /**
     * Creates an empty list.
     */
    public ImmutableList() {
        this.array = (E[]) new Object[0];
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public boolean isEmpty() {
        return array.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public ListIterator<E> iterator() {
        return new ImmutableListIterator(this); 
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, array.length);
    }

    @Override
    public <E> E[] toArray(E[] array) {
        return (E[]) toArray();
    }

    @Deprecated
    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException("Not supported oprtation on immutable list."); 
    }

    @Deprecated
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported oprtation on immutable list."); 
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Objects.requireNonNull(c);
        for (final Object e : c) {
            if (indexOf(e) < 0)
                return false;
        }
        return true;
    }

    @Deprecated
    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Not supported oprtation on immutable list."); 
    }

    @Deprecated
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("Not supported oprtation on immutable list."); 
    }

    @Deprecated
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported oprtation on immutable list.");  
    }

    @Deprecated
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported oprtation on immutable list."); 
    }

    @Deprecated
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported oprtation on immutable list."); 
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= array.length)
            throw new IndexOutOfBoundsException();
        
        return array[index];
    }

    @Deprecated
    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException("Not supported oprtation on immutable list."); 
    }

    @Deprecated
    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException("Not supported oprtation on immutable list."); 
    }

    @Deprecated
    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("Not supported oprtation on immutable list."); 
    }

    @Override
    public int indexOf(Object o) {
        for (int i=0; i<array.length; i++) {
            // compare objects
            if (Objects.nonNull(array[i]) 
                    && array[i].equals(o))
                return i;
            
            // compare null to null
            if (Objects.isNull(array[i])
                    && Objects.isNull(o))
                return i;
        }
        // not found
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i=array.length-1; i>=0; i--) {
            // compare objects
            if (Objects.nonNull(array[i]) 
                    && array[i].equals(o))
                return i;
            
            // compare null to null
            if (Objects.isNull(array[i])
                    && Objects.isNull(o))
                return i;
        }
        // not found
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ImmutableListIterator(this); 
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ImmutableListIterator(this); 
    }

    @Override
    public ImmutableList<E> subList(int fromIndex, int toIndex) {
        return new ImmutableList<>(array, fromIndex, toIndex);
    }
    
    @Override
    public Stream<E> stream() {
        return Arrays.stream(array);
    }
    
    /**
     * Returns a ImmutableList consisting of the elements of this ImmutableList in reverse order.
     * 
     * @return the new ImmutableList
     */
    public ImmutableList<E> reverse() {
        final E[] a = (E[]) new Object[array.length];
        // copy in reverse older
        for (int i=0; i<a.length; i++) {
            a[i] = array[a.length-i-1];
        }
        return new ImmutableList<>(a);
    }
    
    /**
     * Returns a ImmutableList consisting of the elements of this ImmutableList that match
     * the given predicate.
     * 
     * @param predicate a non-interfering stateless predicate to apply to each element to determine if it
     *                  should be included
     * @return the new ImmutableList
     */
    public ImmutableList<E> filter(Predicate<? super E> predicate) {
        Objects.requireNonNull(predicate);
        
        final List<E> filteredList = new LinkedList<>();
        for (E entry : array) {
            if (predicate.test(entry)) {
                filteredList.add(entry);
            }
        }
        return new ImmutableList<>(filteredList);
    }
    
    /**
     * Returns a ImmutableList consisting of the results of applying the given
     * function to the elements of this ImmutableList.
     * 
     * @param <R> The element type of the new ImmutableList
     * @param mapper a non-interfering stateless function to apply to each element
     * @return the new ImmutableList
     */
    public <R> ImmutableList<R> map(Function<? super E, ? extends R> mapper) {
        Objects.requireNonNull(mapper);
        
        final R mappedArray[] = (R[]) new Object[array.length];
        for (int i=0; i<array.length; i++) {
            mappedArray[i] = mapper.apply(array[i]);
        }
        return new ImmutableList<>(mappedArray);
    }
    
    /**
     * The foldLeft function goes through the whole List, from head to tail, and passes each value to foldFunction. 
     * For the first list item, that first parameter is used as the first parameter to foldFunction. 
     * For the second list item is used as the second parameter to foldFunction.
     * For the third list item, the result of the first call to foldFunction is used as the first parameter.
     * 
     * @param foldFunction a non-interfering stateless function to apply to folding accumulator value and for each element.
     * @return the folding result
     */
    public E foldLeft(final BiFunction<? super E, ? super E, ? extends E> foldFunction) {
        Objects.requireNonNull(foldFunction);
        
        E accumulator = array[0];
        for (int i=1; i<array.length; i++) {
            accumulator = foldFunction.apply(accumulator, array[i]);
        }
        return accumulator;
    }
}
