import java.util.concurrent.BrokenBarrierException;

public class T1 extends Thread{
    private Monitor monitor;
    private int start;
    private int end;
    private int numberOfThread;

    private int x1;
    private int y1;
    private int[][] MU1 = new int[Data.N][Data.N];
    private int[][] MAH = new int[Data.N][Data.N];
    private int[] Z1 = new int[Data.N];


    public T1(Monitor monitor, int start, int end, int numberOfThread){
        this.monitor = monitor;
        this.start = start;
        this.end=end;
        this.numberOfThread = numberOfThread;
    }

    @Override
    public void run(){

        System.out.println("Thread "+numberOfThread+" started");
        Data.fill_vector(Data.C);
        Data.fill_matrix(Data.MX);
        // Бар'єр 1
        try {
            Data.barrier1.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        MU1 = monitor.getMU();
        MAH = Data.multiplyMatrixByMatrix(Data.MX, MU1, start, end);
        Data.insertMatrixPartResult(MAH, Data.MA, start, end);

        Z1 = monitor.getZ();

        x1 = Data.getMaxValueInVector(Z1, start, end);
        monitor.calculateMaxValueX(x1);

        y1=Data.getMinValueInVector(Z1, start, end);
        monitor.calculateMinValueY(y1);
        // Бар'єр 2
        try {
            Data.barrier2.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        x1 = monitor.getX();
        y1= monitor.getY();

        Data.GetFinalResult(x1, y1, Z1, start, end);
        System.out.println("Thread "+numberOfThread+" ended");
        // Бар'єр 3
        try {
            Data.barrier3.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}
