import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
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

                byteBufferDrone.putInt(PersistenciaDrone.POS_ID_DRONE, auxDrone.getIdentificador());
                byteBufferDrone.put(PersistenciaDrone.POS_MARCA_DRONE, auxDrone.getMarca().getBytes());
                byteBufferDrone.put(PersistenciaDrone.POS_MODELO_DRONE, auxDrone.getModelo().getBytes());
                byteBufferDrone.put(PersistenciaDrone.POS_COR_DRONE, auxDrone.getCor().getBytes());
                
                for(int i = 0 ; i < 3 ; ++i) {
                    byteBufferDrone.putFloat(PersistenciaDrone.POS_DIMENSOES_DRONE + 4 * i, auxDrone.getDimensoes()[i]);
                }

                byteBufferDrone.putFloat(PersistenciaDrone.POS_PESO_DRONE, auxDrone.getPeso());
                byteBufferDrone.putFloat(PersistenciaDrone.POS_VEL_MAXIMA_DRONE, auxDrone.getVelMaxima());
                byteBufferDrone.putFloat(PersistenciaDrone.POS_VEL_ATUAL_DRONE, auxDrone.getVelAtual());
                byteBufferDrone.putFloat(PersistenciaDrone.POS_ALTITUDE_MAX_DRONE, auxDrone.getAltMax());
                byteBufferDrone.putFloat(PersistenciaDrone.POS_ALTITUDE_ATUAL_DRONE, auxDrone.getAltAtual());

                for(int i = 0 ; i < 3 ; ++i) {
                    byteBufferDrone.putFloat(PersistenciaDrone.POS_COORD_INICIAL_DRONE + 4 * i, auxDrone.getCoordInicial()[i]);
                }

                for(int i = 0 ; i < 3 ; ++i) {
                    byteBufferDrone.putFloat(PersistenciaDrone.POS_COORD_ATUAL_DRONE + 4 * i, auxDrone.getCoordAtual()[i]);
                }

                byteBufferDrone.putInt(PersistenciaDrone.POS_NIVEL_BAT_DRONE, auxDrone.getNivelBateria());

                dwDrone.write(byteBufferDrone.array());
            }

            fwDrone.close();
            dwDrone.close();

        } catch (IOException error) {
            Logger.getLogger(PersistenciaDrone.class.getName()).log(Level.SEVERE, null, error);
        }
    }

    public static Collection<Drone> lerDrones(String nomeArquivo) {

        Collection<Drone> leitura_drones = new ArrayList<Drone>();

        try {
            FileInputStream fw = new FileInputStream(nomeArquivo);
            DataInputStream dw = new DataInputStream(fw);
            byte[] databytes = new byte[PersistenciaDrone.TAM_REG_DRONE];
            boolean eof = false;

            do {
                try {
                    if (dw.read(databytes, 0, PersistenciaDrone.TAM_REG_DRONE) == (-1)) {
                        eof = true;
                    } else {
                        ByteBuffer result = ByteBuffer.wrap(databytes);

                        int identificador = result.getInt(PersistenciaDrone.POS_ID_DRONE);
                        String marca = new String(databytes, PersistenciaDrone.POS_MARCA_DRONE, PersistenciaDrone.TAM_MARCA_DRONE);
                        String modelo = new String(databytes, PersistenciaDrone.POS_MODELO_DRONE, PersistenciaDrone.TAM_MODELO_DRONE);
                        String cor = new String(databytes, PersistenciaDrone.POS_COR_DRONE, PersistenciaDrone.TAM_COR_DRONE);
                        
                        float[] dimensoes = new float[3];
                        for(int i = 0 ; i < 3 ; i++) {
                            dimensoes[i] = result.getFloat(PersistenciaDrone.POS_DIMENSOES_DRONE + 4 * i);
                        }

                        float peso = result.getFloat(PersistenciaDrone.POS_PESO_DRONE);
                        float vel_maxima = result.getFloat(PersistenciaDrone.POS_VEL_MAXIMA_DRONE);
                        float vel_atual = result.getFloat(PersistenciaDrone.POS_VEL_ATUAL_DRONE);
                        float altitude_max = result.getFloat(PersistenciaDrone.POS_ALTITUDE_MAX_DRONE);
                        float altitude_atual = result.getFloat(PersistenciaDrone.POS_ALTITUDE_ATUAL_DRONE);

                        float[] coordenada_inicial = new float[3];
                        for (int i = 0; i < 3; i++) {
                            coordenada_inicial[i] = result.getFloat(PersistenciaDrone.POS_COORD_INICIAL_DRONE + 4 * i);
                        }

                        float[] coordenada_atual = new float[3];
                        for (int i = 0; i < 3; i++) {
                            coordenada_atual[i] = result.getFloat(PersistenciaDrone.POS_COORD_ATUAL_DRONE + 4 * i);
                        }
                        
                        int nivel_bateria = result.getInt(PersistenciaDrone.POS_NIVEL_BAT_DRONE);

                        Drone tempDrone = new Drone(identificador, marca, modelo, cor, dimensoes, 
                                                    peso, vel_maxima, vel_atual, altitude_max, altitude_atual, 
                                                    coordenada_inicial, coordenada_atual, nivel_bateria);
                        
                        leitura_drones.add(tempDrone);
                        
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PersistenciaDrone.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (!eof);

            try {
                fw.close();
                dw.close();
            } catch (IOException ex) {
                Logger.getLogger(PersistenciaDrone.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PersistenciaDrone.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return leitura_drones;
    }

    public static Drone lerRegistroPos(String nomeArquivo, int posicao) {

        try {
            if (posicao <= 0) throw new Exception("Posição deve ser um valor poritivo maior ou igual a um.");
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }

        RandomAccessFile ra;
        try {
            // Cria um arquivo de acesso direto
            ra = new RandomAccessFile(nomeArquivo, "r");
            // Coloca o cursor no terceiro no quarto registro (salta os 3 registros anteriores)
            // Posicao medida ordinalmente: 1(primeiro), 2(segundo), 3(terceiro), ...
            ra.seek((posicao - 1) * PersistenciaDrone.TAM_REG_DRONE);
            
            byte[] registroUnitario = new byte[PersistenciaDrone.TAM_REG_DRONE];
            Arrays.fill(registroUnitario, (byte)0);
            // Efetuando a leitura dos Bytes:
            ra.read(registroUnitario, 0, PersistenciaDrone.TAM_REG_DRONE);
            ByteBuffer bbRegistroUnitario = ByteBuffer.wrap(registroUnitario);

            int identificador = bbRegistroUnitario.getInt(PersistenciaDrone.POS_ID_DRONE);
            String marca = new String(registroUnitario, PersistenciaDrone.POS_MARCA_DRONE, PersistenciaDrone.TAM_MARCA_DRONE);
            String modelo = new String(registroUnitario, PersistenciaDrone.POS_MODELO_DRONE, PersistenciaDrone.TAM_MODELO_DRONE);
            String cor = new String(registroUnitario, PersistenciaDrone.POS_COR_DRONE, PersistenciaDrone.TAM_COR_DRONE);
            
            float[] dimensoes = new float[3];
            for(int i = 0 ; i < 3 ; i++) {
                dimensoes[i] = bbRegistroUnitario.getFloat(PersistenciaDrone.POS_DIMENSOES_DRONE + 4 * i);
            }

            float peso = bbRegistroUnitario.getFloat(PersistenciaDrone.POS_PESO_DRONE);
            float vel_maxima = bbRegistroUnitario.getFloat(PersistenciaDrone.POS_VEL_MAXIMA_DRONE);
            float vel_atual = bbRegistroUnitario.getFloat(PersistenciaDrone.POS_VEL_ATUAL_DRONE);
            float altitude_max = bbRegistroUnitario.getFloat(PersistenciaDrone.POS_ALTITUDE_MAX_DRONE);
            float altitude_atual = bbRegistroUnitario.getFloat(PersistenciaDrone.POS_ALTITUDE_ATUAL_DRONE);

            float[] coordenada_inicial = new float[3];
            for (int i = 0; i < 3; i++) {
                coordenada_inicial[i] = bbRegistroUnitario.getFloat(PersistenciaDrone.POS_COORD_INICIAL_DRONE + 4 * i);
            }

            float[] coordenada_atual = new float[3];
            for (int i = 0; i < 3; i++) {
                coordenada_atual[i] = bbRegistroUnitario.getFloat(PersistenciaDrone.POS_COORD_ATUAL_DRONE + 4 * i);
            }
            
            int nivel_bateria = bbRegistroUnitario.getInt(PersistenciaDrone.POS_NIVEL_BAT_DRONE);

            return new Drone(identificador, marca, modelo, cor, dimensoes, 
                             peso, vel_maxima, vel_atual, altitude_max, altitude_atual, 
                             coordenada_inicial, coordenada_atual, nivel_bateria);

        } catch (IOException ex) {
            Logger.getLogger(PersistenciaDrone.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static void escreverRegistroPos(String nomeArq, int posicao, Drone novoInfoDrone) {
        try {
            if (posicao <= 0) throw new Exception("Posição deve ser um valor poritivo maior ou igual a um.");
        } catch (Exception ex) {
            System.out.println(ex);
            return;
        }

        RandomAccessFile ra;
        try {
            ra = new RandomAccessFile(nomeArq, "rw");
            // Posicionando o Cursor:
            ra.seek((posicao - 1) * PersistenciaDrone.TAM_REG_DRONE);
            
            byte[] infoDrone = new byte[PersistenciaDrone.TAM_REG_DRONE];
            Arrays.fill(infoDrone, (byte)0);
            ByteBuffer byteBufferDrone = ByteBuffer.wrap(infoDrone);

            byteBufferDrone.putInt(PersistenciaDrone.POS_ID_DRONE, novoInfoDrone.getIdentificador());
            byteBufferDrone.put(PersistenciaDrone.POS_MARCA_DRONE, novoInfoDrone.getMarca().getBytes());
            byteBufferDrone.put(PersistenciaDrone.POS_MODELO_DRONE, novoInfoDrone.getModelo().getBytes());
            byteBufferDrone.put(PersistenciaDrone.POS_COR_DRONE, novoInfoDrone.getCor().getBytes());

            for (int i = 0; i < 3; ++i) {
                byteBufferDrone.putFloat(PersistenciaDrone.POS_DIMENSOES_DRONE + 4 * i, novoInfoDrone.getDimensoes()[i]);
            }

            byteBufferDrone.putFloat(PersistenciaDrone.POS_PESO_DRONE, novoInfoDrone.getPeso());
            byteBufferDrone.putFloat(PersistenciaDrone.POS_VEL_MAXIMA_DRONE, novoInfoDrone.getVelMaxima());
            byteBufferDrone.putFloat(PersistenciaDrone.POS_VEL_ATUAL_DRONE, novoInfoDrone.getVelAtual());
            byteBufferDrone.putFloat(PersistenciaDrone.POS_ALTITUDE_MAX_DRONE, novoInfoDrone.getAltMax());
            byteBufferDrone.putFloat(PersistenciaDrone.POS_ALTITUDE_ATUAL_DRONE, novoInfoDrone.getAltAtual());

            for (int i = 0; i < 3; ++i) {
                byteBufferDrone.putFloat(PersistenciaDrone.POS_COORD_INICIAL_DRONE + 4 * i, novoInfoDrone.getCoordInicial()[i]);
            }

            for (int i = 0; i < 3; ++i) {
                byteBufferDrone.putFloat(PersistenciaDrone.POS_COORD_ATUAL_DRONE + 4 * i, novoInfoDrone.getCoordAtual()[i]);
            }

            byteBufferDrone.putInt(PersistenciaDrone.POS_NIVEL_BAT_DRONE, novoInfoDrone.getNivelBateria());

            ra.write(byteBufferDrone.array());


        } catch (IOException ex) {
            Logger.getLogger(PersistenciaDrone.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

