import java.util.Arrays;

public class MediaArray implements Runnable {
    private int[] array;
    private int largura;
    private Double result;

    public MediaArray(int[] array, int largura) {
        this.array = array;
        this.largura = largura;
        this.result = null;
    }

    public Double getMedia() { return this.result; }

    @Override
    public void run() {
        if (this.largura <= 0) {
            this.result = (double)0;
            return;
        } 

        if (this.largura == 1) {
            System.out.print("\n\n\num\n\n\n");
            this.result = (double)this.array[0];
            return;
        }

        if (this.largura == 2) {
            System.out.print("\n\n\ndois\n\n\n");
            this.result = ((double)this.array[0] + (double)this.array[1]) / 2;
            return;
        }

        int halfSize = (this.largura - 1) / 2;

        MediaArray auxArray1 = new MediaArray(Arrays.copyOfRange(this.array, 0, halfSize), halfSize + 1);
        MediaArray auxArray2 = new MediaArray(Arrays.copyOfRange(this.array, halfSize + 1, this.largura - 1), largura - halfSize - 1);

        Thread t1 = new Thread(auxArray1);
        t1.start();

        Thread t2 = new Thread(auxArray2);
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {}
        
        this.result = (auxArray1.getMedia() + auxArray2.getMedia()) / 2;
    }

    public static void main(String[] args) {
        int size = 10;
        int[] array = new int[] {1, 3, 5, 7, 8, 9, 10, 2, 4, 6};

        MediaArray teste = new MediaArray(array, size);

        Thread t1 = new Thread(teste);
        t1.start();

        try {
            t1.join();
        } catch (Exception e) {}
        

        System.out.println(teste.getMedia());
    }
}
