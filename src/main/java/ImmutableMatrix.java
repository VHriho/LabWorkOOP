import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class ImmutableMatrix implements IMatrix{

    private final float[][] elements;

    //конструктор, що створює пусту матрицю
    public ImmutableMatrix() {
        elements = new float[0][0];
    }

    //конструктор, що створює матрицю заданого розміру
    public ImmutableMatrix(int m, int n) {
        if (m < 0 || n < 0)
            throw new NegativeArraySizeException("Row or column value is negative");
        else
            elements = new float[m][n];
    }

    //конструктор, що створює копію іншої матриці
    public ImmutableMatrix(IMatrix copiedMatrix) {
        elements = new float[copiedMatrix.getRows()][copiedMatrix.getColumns()];
        for (int i = 0; i < copiedMatrix.getRows(); i++)
            elements[i] = Arrays.copyOf(copiedMatrix.getMatrix()[i], copiedMatrix.getMatrix()[i].length);
    }

    //заповнює матрицю значеннями
    @Override
    public void setElem(int m, int n, float elem) {
        throw new UnsupportedOperationException("Immutable Matrix cannot be changed");
    }

    //заповнює матрицю випадковими значеннями
    @Override
    public void setRandomElem() {
        throw new UnsupportedOperationException("Immutable Matrix cannot be changed");
    }

    //заповнює матрицю заданим набором значень
    @Override
    public void fillElem(float[][] matrix){
        throw new UnsupportedOperationException("Immutable Matrix cannot be changed");
    }

    //повертає елемент на претині рядка і стовпчика
    @Override
    public float getElem(int m, int n) {
        if (m < 0 || n < 0)
            throw new NegativeArraySizeException("Row or column value is negative");
        if (m >= elements.length || n >= elements[0].length)
            throw new ArrayIndexOutOfBoundsException("Row or column value out of matrix dimension");
        else
            return elements[m][n];
    }

    //повертає заданий рядок
    @Override
    public float[] getRow(int m) {
        if (m < 0)
            throw new NegativeArraySizeException("Row value is negative");
        if (m >= elements.length)
            throw new ArrayIndexOutOfBoundsException("Row value out of matrix dimension");
        else
            return elements[m];
    }

    //повертає кількість рядків
    @Override
    public int getRows() {
        return elements.length;
    }

    //повертає заданий стовпчик
    @Override
    public float[] getColumn(int n) {
        if (n < 0)
            throw new NegativeArraySizeException("Column value is negative");
        if (n >= elements[0].length)
            throw new ArrayIndexOutOfBoundsException("Column value out of matrix dimension");
        else {
            float[] column = new float[elements[0].length];
            for (int i = 0; i < elements.length; i++) {
                for (int j = 0; j < elements[0].length; j++) {
                    if (j == n)
                        column[i] = elements[i][j];
                }
            }
            return column;
        }
    }

    //повертає кількість стовпців
    @Override
    public int getColumns() {
        return elements[0].length;
    }

    //повертає розмірність матриці
    @Override
    public HashMap<String, Integer> getDimension() {
        HashMap<String, Integer> map = new HashMap<>();
        if (elements.length == 0 || elements[0].length == 0) {
            map.put("Rows", 0);
            map.put("Columns", 0);
        }
        else {
            map.put("Rows", elements.length);
            map.put("Columns", elements[0].length);
        }
        return map;
    }

    //перевизначений метод equals
    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(getClass() != other.getClass())
            return false;
        ImmutableMatrix newMatrix = (ImmutableMatrix) other;
        return (elements.length == newMatrix.elements.length && elements[0].length == newMatrix.elements[0].length && Arrays.deepEquals(elements, newMatrix.elements));
    }

    //перевизначений метод hashCode
    @Override
    public int hashCode() {
        return Objects.hash(elements.length, elements[0].length, Arrays.deepHashCode(elements), 31);
    }

    //Повертає матрицю
    @Override
    public float[][] getMatrix() {
        return elements;
    }
}
