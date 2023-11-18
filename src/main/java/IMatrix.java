public interface IMatrix {

    boolean equals(Object other); //перевизначений метод equals

    int getRows(); //повертає кількість рядки

    int getColumns(); //повертає кількість стовпців

    float[][] getMatrix(); //повертає матрицю

}
