/*
Курсова робота з "Архітектури комп'ютерів. Частина 2"
Виконав: Михайлов Андрій
Варіант: 18
Завдання: A= max(Z)*C + Z*(MX*MU)*min(Z)
ПВВ1: MX, C
ПВВ2: Z, A, MU
*/
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        var start  = System.nanoTime();
        System.out.println("Main thread started");
        System.out.println("N = " + Data.N);
        System.out.println("P = " + Data.P);
        Monitor monitor = new Monitor();
        List<Thread> threads = new ArrayList<>();


        for (int i = 1; i < Data.P + 1; i++) {
            Thread thread = switch (i) {
                case 1 -> new T1(monitor, 0, Data.H, 1);

                case Data.P -> new TP(monitor, Data.H * (Data.P - 1), Data.N, Data.P);

                default -> new Ti(monitor, Data.H * (i - 1), i * Data.H, i + 1);

            };
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        var finish = System.nanoTime();
        System.out.println(finish - start);
        System.out.println("Main thread ended");
    }
}