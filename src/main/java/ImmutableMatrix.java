import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

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

    //сума матриць
    @Override
    public IMatrix sumMatrix(IMatrix matrix) {
        if (this.getRows() != matrix.getRows() || this.getColumns() != matrix.getColumns())
            throw new IllegalArgumentException("Matrices must have an equal number of columns and rows");
        ImmutableMatrix sumOfMatrix = new ImmutableMatrix(matrix.getRows(), matrix.getColumns());
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                sumOfMatrix.elements[i][j] = this.getElem(i, j) + matrix.getElem(i, j);
            }
        }
        return sumOfMatrix;
    }

    //повертає добуток матриці на скаляр
    @Override
    public IMatrix multMatrix(float mult) {
        ImmutableMatrix resMult = new ImmutableMatrix(this.elements.length, this.elements[0].length);
        for (int i = 0; i < this.elements.length; i++) {
            for (int j = 0; j < this.elements[0].length; j++) {
                resMult.elements[i][j] = elements[i][j] * mult;
            }
        }
        return resMult;
    }

    //множення матриць
    @Override
    public IMatrix multiplication(IMatrix matrix) {
        if (this.getColumns() != matrix.getColumns())
            throw new IllegalArgumentException("Matrices must have an equal number of columns and rows");
        ImmutableMatrix multiplct = new ImmutableMatrix(this.getRows(), matrix.getColumns());
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                multiplct.elements[i][j] = 0;
                for (int a = 0; a < this.getColumns(); a++){
                    multiplct.elements[i][j] += this.elements[i][a] * matrix.getElem(a,j);
                }
            }
        }
        return multiplct;
    }

    //транспонування матриці
    @Override
    public IMatrix transposeMatrix() {
        ImmutableMatrix transpose = new ImmutableMatrix(this.elements.length, this.elements[0].length);
        for (int i = 0; i < this.elements.length; i++)  {
            for (int j = 0; j < this.elements[0].length; j++) {
                transpose.elements[i][j] = this.elements[j][i];
            }
        }
        return transpose;
    }

    //повертає діагональну матрицю за вказаним вектором
    @Override
    public IMatrix diagonal(float[] vector) {
        ImmutableMatrix matrix = new ImmutableMatrix(vector.length, vector.length);
        for (int i = 0; i < vector.length; i++) {
            for (int j = 0; j < vector.length; j++) {
                if (i == j) {
                    matrix.elements[i][j] = vector[i];
                }
                else
                    matrix.elements[i][j] = 0;
            }
        }
        return matrix;
    }

    //одинична матриця
    @Override
    public IMatrix singleMatrix(int m, int n) {
        if (m <= 0 || n <= 0)
            throw new NegativeArraySizeException("Rows and columns value must be equal and more than 0");
        if (m != n)
            throw new IllegalArgumentException("Rows and columns value must be equal");
        ImmutableMatrix single = new ImmutableMatrix(m, n);
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                if (i == j )
                    single.elements[i][j] = 1;
                else
                    single.elements[i][j] = 0;
            }
        }
        return single;
    }

    //матриця рядок
    @Override
    public IMatrix rowMatrix(int m) {
        if(m <= 0 )
            throw new NegativeArraySizeException("Row value must be not negative or equal 0");
        Random setRandom = new Random();
        ImmutableMatrix rowMat = new ImmutableMatrix(m, 1);
        for (int i = 0; i < m; i++) {
            rowMat.elements[i][0] = setRandom.nextFloat(10);
        }
        return rowMat;
    }

    //матриця-стовпчик
    @Override
    public IMatrix columnMatrix(int n) {
        if(n <= 0 )
            throw new NegativeArraySizeException("Column value must be not negative or equal 0");
        Random setRandom = new Random();
        ImmutableMatrix columnMat = new ImmutableMatrix(1, n);
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < n; j++) {
                columnMat.elements[0][j] = setRandom.nextFloat(10);
            }
        }
        return columnMat;
    }

    //Повертає матрицю
    @Override
    public float[][] getMatrix() {
        return elements;
    }
}
