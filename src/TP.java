import java.util.concurrent.BrokenBarrierException;

public class TP extends Thread{
    private Monitor monitor;
    private int start;
    private int end;
    private int numberOfThread;

    private int xP;
    private int yP;
    private int[][] MUP=new int[Data.N][Data.N];
    private int[][] MAH = new int[Data.N][Data.N];
    private int[] ZP=new int[Data.N];
    public TP(Monitor monitor, int start, int end, int numberOfThread){
        this.monitor = monitor;
        this.start = start;
        this.end=end;
        this.numberOfThread = numberOfThread;
    }

    @Override
    public void run(){
        System.out.println("Thread "+numberOfThread+" started");
        Data.fill_vector(monitor.getZ());
        Data.fill_matrix(monitor.getMU());
        // Бар'єр 1
        try {
            Data.barrier1.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        MUP = monitor.getMU();
        MAH = Data.multiplyMatrixByMatrix(Data.MX, MUP, start, end);
        Data.insertMatrixPartResult(MAH, Data.MA, start, end);

        ZP = monitor.getZ();

        xP = Data.getMaxValueInVector(ZP, start, end);
        monitor.calculateMaxValueX(xP);

        yP =Data.getMinValueInVector(ZP, start, end);
        monitor.calculateMinValueY(yP);
        // Бар'єр 2
        try {
            Data.barrier2.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        xP = monitor.getX();
        yP = monitor.getY();

        Data.GetFinalResult(xP, yP, ZP, start, end);
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

