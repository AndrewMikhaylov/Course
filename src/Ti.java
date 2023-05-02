import java.util.concurrent.BrokenBarrierException;

public class Ti extends Thread{
    private Monitor monitor;
    private int start;
    private int end;
    private int numberOfThread;

    private int xi;
    private int yi;
    private int[][] MUi=new int[Data.N][Data.N];
    private int[][]MAH = new int[Data.N][Data.N];
    private int[] Zi=new int[Data.N];
    public Ti(Monitor monitor, int start, int end, int numberOfThread){
        this.monitor = monitor;
        this.start = start;
        this.end=end;
        this.numberOfThread = numberOfThread;
    }

    @Override
    public void run(){
        System.out.println("Thread "+numberOfThread+" started");
        // Бар'єр 1
        try {
            Data.barrier1.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        MUi = monitor.getMU();
        MAH = Data.multiplyMatrixByMatrix(Data.MX, MUi, start, end);
        Data.insertMatrixPartResult(MAH, Data.MA, start, end);

        Zi = monitor.getZ();

        xi = Data.getMaxValueInVector(Zi, start, end);
        monitor.calculateMaxValueX(xi);

        yi =Data.getMinValueInVector(Zi, start, end);
        monitor.calculateMinValueY(yi);
        // Бар'єр 2
        try {
            Data.barrier2.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        xi = monitor.getX();
        yi = monitor.getY();

        Data.GetFinalResult(xi, yi, Zi, start, end);
        System.out.println("Thread "+numberOfThread+" started");
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
