import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
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
   
    public Drone getDrone(int idx){
       try {
            if (this.numDrones == 0) throw new Exception("No Drones to Get.");
       } catch (Exception error) {
            System.out.println(error.getMessage());
       }

       return this.drones[idx];
    }

    public Drone[] getArrayDrones() { return this.drones; }

    public int getNumDrones() { return this.numDrones; }
   
    public void save(){

        if (this.numDrones <= 0) return;

        try {
            this.fw = new FileWriter(this.fileName);
            this.bw  = new BufferedWriter(this.fw);

            for (int i = 0 ; i < this.numDrones ; ++i) {
                this.bw.write(this.drones[i].toString());
                this.bw.write("\n");
            }

            this.bw.close();
            this.fw.close();

        } catch (IOException ex) {
            System.out.println("Erro ao Lidar com o Arquivo: " + ex.getMessage());
        }
    }
   

    public void load(){
        // Limpando o Array de Drones:
        this.numDrones = 0;

        try {
            this.fr = new FileReader(this.fileName);
            this.br = new BufferedReader(this.fr);
            
            String inputString;

            while ((inputString = this.br.readLine()) != null) {
                String[] droneData = inputString.split(" ");

                Drone tempDrone = new Drone(Integer.parseInt(droneData[0]), droneData[1], droneData[2], droneData[3], 
                             new float[] {Float.parseFloat(droneData[4]), Float.parseFloat(droneData[5]), Float.parseFloat(droneData[6])}, 
                             Float.parseFloat(droneData[7]), Float.parseFloat(droneData[8]), Float.parseFloat(droneData[9]), Float.parseFloat(droneData[10]), 
                             Float.parseFloat(droneData[11]), new float[] {Float.parseFloat(droneData[12]), Float.parseFloat(droneData[13]), Float.parseFloat(droneData[14])}, 
                             new float[] {Float.parseFloat(droneData[15]), Float.parseFloat(droneData[16]), Float.parseFloat(droneData[17])}, Integer.parseInt(droneData[18]));
                
                this.add(tempDrone);

            }

            this.fr.close();
            this.br.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
