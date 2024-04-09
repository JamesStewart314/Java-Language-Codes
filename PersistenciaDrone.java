import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenciaDrone {
    
    /*
    // Tamanho do Conjunto de Bytes Referente ao Registro do Drone :
    // 4(int identificador) + 20(str marca) + 20(str modelo) + 16(str cor) + 
    // 3*4(float[3] dimensoes) + 4(float peso) + 4(float vel_maxima) + 
    // 4(float vel_atual) + 4(float altitude_max) + 4(float altitude_atual) +
    // 3*4(float[3] coordenada_inicial) + 3*4(float[3] coordenada_atual) +
    // 4(int nivel_bateria) = 120 bytes no total.
    */

    private static final int TAM_REG_DRONE = 120;
    private static final int TAM_MARCA_DRONE = 20;
    private static final int TAM_MODELO_DRONE = 20;
    private static final int TAM_COR_DRONE = 16;

    private static final int POS_ID_DRONE = 0;
    private static final int POS_MARCA_DRONE = 4;
    private static final int POS_MODELO_DRONE = 24;
    private static final int POS_COR_DRONE = 44;
    private static final int POS_DIMENSOES_DRONE = 60;
    private static final int POS_PESO_DRONE = 72;
    private static final int POS_VEL_MAXIMA_DRONE = 76;
    private static final int POS_VEL_ATUAL_DRONE = 80;
    private static final int POS_ALTITUDE_MAX_DRONE = 84;
    private static final int POS_ALTITUDE_ATUAL_DRONE = 88;
    private static final int POS_COORD_INICIAL_DRONE = 92;
    private static final int POS_COORD_ATUAL_DRONE = 104;
    private static final int POS_NIVEL_BAT_DRONE = 116;
    
    /*
    // A implementação de um construtor para a classe em questão é 
    // considerada dispensável, dada a sua natureza primordialmente 
    // focada no tratamento - entrada e saída - do fluxo de dados.
    */

    public PersistenciaDrone() {}

    public static void salvar_drones(Collection<Drone> drones, String nomeArquivo) {
        
        try {
            
            FileOutputStream fwDrone = new FileOutputStream(nomeArquivo);
            DataOutputStream dwDrone = new DataOutputStream(fwDrone);
            
            /*
            // Para armazenar um registro em arquivos, precisamos 
            // inicialmente estruturar seu conteúdo ordenadamente em um
            // array de bytes.
            */

            byte[] conteudo = new byte[PersistenciaDrone.TAM_REG_DRONE];

            for(Drone auxDrone: drones) {
                // Inicializando o conjunto de Bytes :
                Arrays.fill(conteudo, (byte)0);

                // Criando o Objeto Buffer para Efetuar a Conversão de
                // Dados eficientemente.
                ByteBuffer byteBufferDrone = ByteBuffer.wrap(conteudo);

                byteBufferDrone.put(PersistenciaDrone.POS_ID_DRONE, (byte)auxDrone.getIdentificador());
                byteBufferDrone.put(PersistenciaDrone.POS_MARCA_DRONE, auxDrone.getMarca().getBytes());
                byteBufferDrone.put(PersistenciaDrone.POS_MODELO_DRONE, auxDrone.getModelo().getBytes());
                byteBufferDrone.put(PersistenciaDrone.POS_COR_DRONE, auxDrone.getCor().getBytes());
                
                for(int i = 0 ; i < 3 ; ++i) {
                    byteBufferDrone.put(PersistenciaDrone.POS_DIMENSOES_DRONE + 4 * i, 
                                        (byte)auxDrone.getDimensoes()[i]);
                }

                byteBufferDrone.put(PersistenciaDrone.POS_PESO_DRONE, (byte)auxDrone.getPeso());
                byteBufferDrone.put(PersistenciaDrone.POS_VEL_MAXIMA_DRONE, (byte)auxDrone.getVelMaxima());
                byteBufferDrone.put(PersistenciaDrone.POS_VEL_ATUAL_DRONE, (byte)auxDrone.getVelAtual());
                byteBufferDrone.put(PersistenciaDrone.POS_ALTITUDE_MAX_DRONE, (byte)auxDrone.getAltMax());
                byteBufferDrone.put(PersistenciaDrone.POS_ALTITUDE_ATUAL_DRONE, (byte)auxDrone.getAltAtual());

                for(int i = 0 ; i < 3 ; ++i) {
                    byteBufferDrone.put(PersistenciaDrone.POS_COORD_INICIAL_DRONE + 4 * i, 
                                        (byte)auxDrone.getCoordInicial()[i]);
                }

                for(int i = 0 ; i < 3 ; ++i) {
                    byteBufferDrone.put(PersistenciaDrone.POS_COORD_ATUAL_DRONE + 4 * i, 
                                        (byte)auxDrone.getCoordAtual()[i]);
                }

                byteBufferDrone.put(PersistenciaDrone.POS_NIVEL_BAT_DRONE, (byte)auxDrone.getNivelBateria());

                dwDrone.write(byteBufferDrone.array());

            }

            fwDrone.close();
            dwDrone.close();

        } catch (IOException error) {
            Logger.getLogger(PersistenciaDrone.class.getName()).log(Level.SEVERE, null, error);
        }
    }


}
