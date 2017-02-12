package multi_thread;

/**
 * 多线程打印数字
 * Created by lisongyang on 17-2-12.
 */
public class ThreadMulti {

    public static Object obj = new Object();

    public static Integer i = 0;

    public ThreadMulti(int i) {
        this.i = i;
    }

    private static class PrintThread extends Thread {
        public int id;
        public PrintThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while(i < 100) {
                synchronized (obj) {
                    while(i % 5 != 0 || (i % 5 == 0 && (i / 5) % 3 != id)) {
                        try {
                            i.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (i < 100) {
                        System.out.println("Thread-" + id + " : " + i + " " + (i + 1)
                                + " " + (i + 2) + " " + (i + 3) + " " + (i + 4)
                                + "\n");
                        i += 5;
                    }

                    i.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new PrintThread(0) ;
        Thread thread2 = new PrintThread(1) ;
        Thread thread3 = new PrintThread(2) ;

        thread1.start();
        thread2.start();
        thread3.start();

    }
}
