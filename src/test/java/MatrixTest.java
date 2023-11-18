import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatrixTest {

    //конструктори, що створюють пусту матрицю, матрицю заданого розміру та копію іншої матриці
    @Test
    void step2() {
        IMatrix emptyMatrix = new Matrix();
        IMatrix selfDimensioned = new Matrix(3, 3);
        IMatrix copiedMatrix = new Matrix(selfDimensioned);

        Assertions.assertEquals(3, selfDimensioned.getRows());
        Assertions.assertEquals(3, selfDimensioned.getColumns());
        Assertions.assertEquals(true, copiedMatrix.equals(selfDimensioned));
        Assertions.assertEquals(0, emptyMatrix.getMatrix().length);
    }

    //методи, що дозволяють заповнити матрицю значеннями
    @Test
    void step3() {
        IMatrix matrix = new Matrix(2,2);
        matrix.setElem(0,0,1);
        matrix.setElem(0,1,2);
        matrix.setElem(1,0,3);
        matrix.setElem(1,1,4);

        IMatrix matrix1 = new Matrix(3,3);
        matrix1.setRandomElem();

        IMatrix matrix2 = new Matrix();

        IMatrix matrix3 = new Matrix(3,3);
        float[][] filledMatrix = {{1,2,3},{4,5,6},{7,8,9}};
        matrix3.fillElem(filledMatrix);

        Assertions.assertEquals(1,matrix3.getMatrix()[0][0]);
        Assertions.assertEquals(2,matrix3.getMatrix()[0][1]);
        Assertions.assertEquals(3,matrix3.getMatrix()[0][2]);
        Assertions.assertEquals(4,matrix3.getMatrix()[1][0]);
        Assertions.assertEquals(5, matrix3.getMatrix()[1][1]);

        //виключення при від'ємному значені рядка або стовпчика
        Throwable thrown = Assertions.assertThrows(NegativeArraySizeException.class, () -> matrix.setElem(-1,0,1));
        Assertions.assertEquals("Row or column value is negative", thrown.getMessage());

        //виключення при порушені вказання розмірності
        Throwable thrown1 = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> matrix.setElem(1,5,1));
        Assertions.assertEquals("Row or column value out of matrix dimension", thrown1.getMessage());

        //виключення при заповнені матриці розмірності 0
        Throwable thrown2 = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> matrix2.setRandomElem());
        Assertions.assertEquals("Dimension of matrix is 0", thrown2.getMessage());
    }
}
