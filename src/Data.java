import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;


public class Data {
    public final static int N = 2400;
    public final static  int P = 4;
    public static int H = N/P;

    public static CyclicBarrier barrier1 = new CyclicBarrier(Data.P);
    public static CyclicBarrier barrier2 = new CyclicBarrier(Data.P);
    public static CyclicBarrier barrier3 = new CyclicBarrier(Data.P);


    public static int[] C = new int[N];
    public static  int[] A = new int[N];
    public static int[][] MX = new int[N][N];
    public static int[][] MA = new int[N][N];

    public static void fill_matrix(int[][] matrix)
    {
        for (int[] ints : matrix) Arrays.fill(ints, 1);
    }
    public static void fill_vector(int[] vector)
    {
        Arrays.fill(vector, 1);
    }

    public static int getMinValueInVector(int[] vector, int start, int end)
    {
        int min = start;
        for (int i = start; i<end; i++) {
            if (vector[i]<min){
                min = vector[i];
            }
        }
        return min;
    }

    public static int getMaxValueInVector(int[] vector, int start, int end)
    {
        int max = start;
        for (int i = start; i<end; i++) {
            if (vector[i]>max){
                max = vector[i];
            }
        }
        return max;
    }

    public static int[][] multiplyMatrixByMatrix(int[][] matrix1, int[][] matrix2, int start, int end)
    {
        int[][] result = new int[matrix1.length][end - start];
        int s;
        for (int i = 0; i < matrix1.length; i++) {
            s = 0;
            for (int j = start; j < end; j++) {
                for (int k = 0; k < matrix2.length; k++) {
                    result[i][s] += matrix1[i][k] * matrix2[k][j];
                }
                s++;
            }
        }
        return result;
    }

    public static int[] multiplyVectorByMatrix(int[] vector, int[][] matrix, int start, int end)
    {
        int[] result = new int[end-start];

        int index =0;
        for (int i = 0; i < vector.length; i++) {
            for (int j = start; j < end; j++) {
                result[index++] += matrix[i][j] * vector[i];
            }
            index=0;
        }
        return result;
    }

    public static int[] multiplyVectorByNumber(int[] vector, int number, int start, int end)
    {
        int[] result = new int[end-start];

        for(int i = 0; i< result.length; i++)
        {
            result[i] = vector[i]*number;
        }

        return result;
    }

    public static int[] addVectors(int[] vector1, int[] vector2)
    {
        int[] result = new int[vector1.length];

        for (int i = 0; i<vector2.length; i++)
        {
            result[i] = vector1[i]+vector2[i];
        }
        return result;
    }

    public static void GetFinalResult(int xi, int yi, int[] Zi, int start, int end)
    {
        int[] maxZxC = multiplyVectorByNumber(C, xi, start, end);
        int[] ZxMA = multiplyVectorByMatrix(Zi, MA, start, end);
        int[] ZxMAxminZ = multiplyVectorByNumber(ZxMA, yi, start, end);
        int[] resultA = addVectors(maxZxC, ZxMAxminZ);
        int j = 0;
        for (int i = start; i<end; i++) {
            A[i] = resultA[j];
            j++;
        }
    }

    public static void ShowResult()
    {
        for (int i: A) {
            System.out.println(i+" ");
        }

    }

    public static void insertMatrixPartResult(int[][] newMatrix, int[][] originalMatrix, int start, int end)
    {
        for (int i = 0; i < newMatrix.length; i++) {
            int k = 0;
            for (int j = start; j < end; j++) {
                originalMatrix[i][j] = newMatrix[i][k];
                k++;
            }
        }
    }
}
