import java.util.ArrayList;
import java.util.Collection;

public class DronesArquivoBinario {
    public static void main(String[] args) {
        Collection<Drone> drones = new ArrayList<Drone>();

        drones.add(new Drone(2, "teste", "teste1", "teste2", new float[] { 1.1f, 2.2f, 3.3f }, 10f, 200f, 0f, 70f, 0f,
                new float[] { 0f, 0f, 0f }, new float[] { 0f, 0f, 0f }, 63));
        drones.add(new Drone(2, "teste", "teste1", "teste2", new float[] { 1.1f, 2.2f, 3.3f }, 10f, 200f, 0f, 70f, 0f,
                new float[] { 0f, 0f, 0f }, new float[] { 0f, 0f, 0f }, 63));

        System.out.println("Salvando arquivo binario " + "drones.bin");
        PersistenciaDrone.salvar_drones(drones, "drones.bin");
    }
}
