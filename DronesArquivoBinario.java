import java.util.ArrayList;
import java.util.Collection;

public class DronesArquivoBinario {
    public static void main(String[] args) {
        String nomeArq = "drones.bin";
        Collection<Drone> drones = new ArrayList<Drone>();

        drones.add(new Drone(20, "Xiaomi", "REDMI98", "amarelo", new float[] { 10.1f, 20.2f, 30.3f }, 10f, 200f, 0f, 70f, 0f,
                new float[] { 0f, 0f, 0f }, new float[] { 0f, 0f, 0f }, 63));
        drones.add(new Drone(40, "LGTV", "LG-31", "azul", new float[] { 12.1f, 24.2f, 36.3f }, 10f, 200f, 0f, 70f, 0f,
                new float[] { 0f, 0f, 0f }, new float[] { 0f, 0f, 0f }, 63));

        System.out.println("Salvando arquivo binario " + nomeArq);
        PersistenciaDrone.salvar_drones(drones, nomeArq);

        for (Drone dr: PersistenciaDrone.lerDrones(nomeArq)) {
            System.out.println(dr);
        }

        System.out.println(PersistenciaDrone.lerRegistroPos(nomeArq, 2));
        PersistenciaDrone.escreverRegistroPos(nomeArq, 2, new Drone(20, "Xiaomi", "REDMI98", "amarelo", new float[] { 10.1f, 20.2f, 30.3f }, 10f, 200f, 0f, 70f, 0f,
                new float[] { 0f, 0f, 0f }, new float[] { 0f, 0f, 0f }, 63));

        System.out.println("\nMudanças Finais:");
        for (Drone dr: PersistenciaDrone.lerDrones(nomeArq)) {
            System.out.println(dr);
        }
    }
}
