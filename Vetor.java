public class Vetor<T> {

    T[] A;
    int capacity;
    int size;

    @SuppressWarnings ("unchecked")
    public Vetor(int capacity) {
        A = (T[]) new Object[capacity];
        this.size = 0;
        this.capacity = capacity;
    }

    public boolean isEmpty() {
        if (size == 0)
            return true;
        else
            return false;
    }
    public boolean isFull() {
    return size == capacity;
}

    public int size() {
        return size;
    }

    public T get(int i) throws Exception {
        if (i < 0 || i >= size)
            throw new Exception("Posição inexistente");

        return A[i];
    }

    public void set(int i, T n) throws Exception {
        if (i < 0 || i >= size)
            throw new Exception("Posição inexistente");

        A[i] = n;
    }

    public void add(int i, T n) throws Exception {

        if (size == A.length)
            throw new Exception("Lista cheia");

        if (i < 0 || i > size)
            throw new Exception("Posição invalida para inserir");

        for (int x = size; x > i; x--) {
            A[x] = A[x - 1];
        }

        A[i] = n;
        size++;
    }

    public void remove(int i) throws Exception {

        if (isEmpty())
            throw new Exception("Lista Vazia");

        if (i < 0 || i >= size)
            throw new Exception("Posição inexistente");

        for (int x = i; x < size - 1; x++) {
            A[x] = A[x + 1];
        }

        size--;
    }

    public int search(T n) {

        for (int i = 0; i < size; i++) {

            if (A[i].equals(n))
                return i;
        }

        return -1;
    }

    public void mostraLista() throws Exception {

        for (int i = 0; i < size; i++) {
            System.out.println(A[i]);
        }
    }
}