import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class MyArrayList<T> implements List <T>{

    public static Object myList = new Array[];

    public static void main(String[] args) {
        myList.add("Orange");
        myList.add("Apple");
        myList.add("Pear");
        myList.add("Strawberry");

        System.out.println("List size is: " + myList.checkSize());
        System.out.println(myList.isEmpty());
        System.out.println(myList.contains("P"));
        System.out.println(myList.remove(1));
    }
    public int checkSize() {
        int amount = 0;
        for (String s : myList) {
            amount++;
        }
        return amount;
    }

    @Override
    public int size() {
        int amount = 0;
        for (String s : myList) {
            amount++;
        }
        return amount;
    }

    @Override
    public boolean isEmpty() {
        int amount = myList.size();
        if (amount == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        String main = null;
        String Substring = null;
        boolean flag=false;
        assert false;
        if(main.trim().equals("")) {
            return flag;
        }
        char[] fullstring =main.toCharArray();
        char[] sub =Substring.toCharArray();
        int counter=0;
        if(sub.length==0) {
            flag=true;
            return flag;
        }
        for (char c : fullstring) {

            if (c == sub[counter]) {
                counter++;
            } else {
                counter = 0;
            }

            if (counter == sub.length) {
                flag = true;
                return flag;
            }

        }
        return flag;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer action) {
        List.super.forEach(action);
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection collection) {
        return false;
    }

    @Override
    public boolean addAll(int i, Collection collection) {
        return false;
    }

    @Override
    public void replaceAll(UnaryOperator operator) {
        List.super.replaceAll(operator);
    }

    @Override
    public void sort(Comparator c) {
        List.super.sort(c);
    }

    @Override
    public void clear() {

    }

    @Override
    public Object get(int i) {
        return null;
    }

    @Override
    public Object set(int i, Object o) {
        return null;
    }

    @Override
    public void add(int i, Object o) {

    }

    @Override
    public Object remove(int i) {

        return myList.size();
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator listIterator() {
        return null;
    }

    @Override
    public ListIterator listIterator(int i) {
        return null;
    }

    @Override
    public List subList(int i, int i1) {
        return null;
    }

    @Override
    public boolean retainAll(Collection collection) {
        return false;
    }

    @Override
    public boolean removeAll(Collection collection) {
        return false;
    }

    @Override
    public boolean containsAll(Collection collection) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] objects) {
        return new Object[0];
    }
}

