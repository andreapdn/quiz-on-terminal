package util;

public class ArraySet<T> {
    protected Object[] v;
    protected int vSize;

    public ArraySet() {
        v = new Object[1];
        makeEmpty();
    }

    public void makeEmpty() {
        vSize = 0;
    }
    public boolean isEmpty() {
        return vSize == 0;
    }
    public int size() {
        return vSize;
    }

    public Object get(int index) {
        if (index < 0 || index >= vSize) {
            throw new IndexOutOfBoundsException();
        }
        return v[index];
    }
    public void add(T obj) {
        if (vSize >= v.length) resize();

        v[vSize++] = obj;
    }
    public Object remove(int index) {
        if (index < 0 || index >= vSize) {
            throw new IndexOutOfBoundsException();
        }

        Object element = v[index];
        swap(index, --vSize);
        return element;
    }
    public boolean contains(T obj) {
        int pos = search(obj);
        if (pos < 0) return false;
        return true;
    }
    public Object[] toArray() {
        Object[] v2 = new Object[vSize];
        System.arraycopy(v, 0, v2, 0, vSize);
        return v2;
    }

    // Linear Search
    private int search(T obj) {
        for (int i = 0; i < vSize; i++) {
            if (v[i].equals(obj)) return i;
        }
        return -1;
    }
    private void swap(int i, int j) {
        Object k = v[i];
        v[i] = v[j];
        v[j] = k;
    }
    private void resize() {
        Object[] v2 = new Object[v.length * 2];
        System.arraycopy(v, 0, v2, 0, v.length);
        v = v2;
    }
}
