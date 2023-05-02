public class Monitor {
    private int x = Integer.MIN_VALUE;
    private int y = Integer.MAX_VALUE;

    private int[] Z = new int[Data.N];
    private int[][] MU = new int[Data.N][Data.N];

    public synchronized void calculateMaxValueX(int xi){
        if (xi>x){
            x = xi;
        }
    }

    public synchronized void calculateMinValueY(int yi){
        if (yi<y){
            y = yi;
        }
    }

    public synchronized int getX(){
        return x;
    }

    public synchronized int getY(){
        return y;
    }

    public int[] getZ() {
        return Z;
    }


    public int[][] getMU() {
        return MU;
    }

}
