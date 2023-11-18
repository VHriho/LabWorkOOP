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

}
