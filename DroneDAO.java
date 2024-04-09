import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DroneDAO {
    public String fileName;
    public final static int MAX_DRONES = 10;
    private Drone[] drones;
    private int numDrones;
    private FileReader fr;
    private BufferedReader br;
    private FileWriter fw;
    private BufferedWriter bw;
   
    public DroneDAO(String fileName){
        this.fileName = fileName;
        this.drones = new Drone[DroneDAO.MAX_DRONES];
        this.numDrones = 0;
    };
   
    public void add(Drone drone){
       if (this.numDrones >= DroneDAO.MAX_DRONES) return;
       this.drones[this.numDrones++] = drone;

       return;
    }
   
    public Drone get(int id){
       try {
            if (this.numDrones == 0) throw new Exception("No Drones to Get.");
       } catch (Exception error) {
            System.out.println(error.getMessage());
       }

       return this.drones[id];
    }
   
    public void save(){

        if (this.numDrones <= 0) return;

        try {
            this.fw = new FileWriter(this.fileName);
            this.bw  = new BufferedWriter(this.fw);

            for (int i = 0 ; i < this.numDrones ; ++i) {
                this.bw.write(this.drones[i].toString());
            }

            this.bw.close();
            this.fw.close();

        } catch (IOException error) {
            System.out.println("Erro ao Lidar com o Arquivo: " + error.getMessage());
        }
    }
   

    public void load(){
        return;
    }
}
