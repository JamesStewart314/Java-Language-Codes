public class Drone {
    
    private int identificador, nivel_bateria;
    private String marca, modelo, cor;
    private float[] dimensoes;
    private float peso;
    private float vel_maxima, vel_atual;
    private float altitude_max, altitude_atual;
    private float[] coordenada_inicial, coordenada_atual;

    public Drone(int identificador, String marca, String modelo, String cor, 
                 float[] dimensoes, float peso, float vel_maxima, float vel_atual, 
                 float altitude_max, float altitude_atual, float[] coordenada_inicial,
                 float[] coordenada_atual, int nivel_bateria) {

        this.identificador = identificador;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.vel_maxima = vel_maxima;
        this.vel_atual = vel_atual;
        this.altitude_max = altitude_max;
        this.altitude_atual = altitude_atual;
        this.nivel_bateria = nivel_bateria;
        this.peso = peso;
        
        this.dimensoes = new float[3];
        this.coordenada_inicial = new float[3];
        this.coordenada_atual = new float[3];

        System.arraycopy(coordenada_inicial, 0, this.coordenada_inicial, 
                 0, coordenada_inicial.length);
        
        System.arraycopy(dimensoes, 0, this.dimensoes, 0, 3);

        System.arraycopy(coordenada_atual, 0, this.coordenada_atual, 
                 0, coordenada_atual.length);

    }


    public int getIdentificador() { return this.identificador; }

    public String getMarca() { return this.marca; }

    public String getModelo() { return this.modelo; }

    public String getCor() { return this.cor; }

    public float[] getDimensoes() { return this.dimensoes; }

    public float getPeso() { return this.peso; }

    public float getVelMaxima() { return this.vel_maxima; }

    public float getVelAtual() { return this.vel_maxima; }

    public float getAltMax() { return this.altitude_max; }

    public float getAltAtual() { return this.altitude_atual; }

    public float[] getCoordInicial() { return this.coordenada_inicial; }

    public float[] getCoordAtual() { return this.coordenada_atual; }

    public int getNivelBateria() { return this.nivel_bateria; }


    public void imprimir() {
        System.out.println("\n/-------------------- INFOS --------------------\\");
        
        System.out.printf("- Identificador: %d;\n- Marca: %s;\n- Modelo: %s;\n- Cor: %s;\n", 
                          this.identificador, this.marca, this.modelo, this.cor);

        System.out.println("\n- Dimensoes:");

        System.out.printf(" └> Comprimento: %.2f ;\n └> Largura: %.2f ;\n └> Altura: %.2f ;\n\n",
                this.dimensoes[0], this.dimensoes[1], this.dimensoes[2]);

        System.out.printf("- Peso: %.2f\n", this.peso);
        System.out.printf("- Velocidade Máxima: %.2f\n", this.vel_maxima);
        System.out.printf("- Velocidade Atual: %.2f\n", this.vel_atual);
        System.out.println("- Altitude Máxima: " + this.altitude_max);
        System.out.println("- Altitude Atual: " + this.altitude_atual);

        System.out.printf("- Coordenadas Iniciais: x = %.2f, y = %.2f, z = %.2f\n",
                this.coordenada_inicial[0], this.coordenada_inicial[1], this.coordenada_inicial[2]);

        System.out.printf("- Coordenadas Atuais: x = %.2f, y = %.2f, z = %.2f\n",
                this.coordenada_atual[0], this.coordenada_atual[1], this.coordenada_atual[2]);

        System.out.println("- Nível de Bateria: " + this.nivel_bateria);
        System.out.println("\\-----------------------------------------------/\n");
    }

    public void acelerar(float aceleracao) {
        if (aceleracao <= 0) return;

        this.vel_atual += aceleracao;
    }

    public void desacelerar(float desaceleracao) {
        if (desaceleracao <= 0) return;

        this.vel_atual = Math.max(this.vel_atual - desaceleracao, 0);
    }

    public void subir(float centimetros) {
        if (centimetros <= 0) return;

        float nova_altitude = this.altitude_atual + centimetros / 100;

        if (nova_altitude <= this.altitude_max) {
            this.altitude_atual = nova_altitude;
            this.coordenada_atual[2] = this.altitude_atual;

        } else {
            this.altitude_atual = this.altitude_max;
            this.coordenada_atual[2] = this.altitude_atual;
        }
    }

    public void descer(float centimetros) {
        if (centimetros <= 0) return;

        float nova_altitude = this.altitude_atual - centimetros;

        if (nova_altitude >= 0) {
            this.altitude_atual = nova_altitude;
            this.coordenada_atual[2] = this.altitude_atual;

        } else {
            this.altitude_atual = 0;
            this.coordenada_atual[2] = this.altitude_atual;
        }
    }

    public void moverDireita(float metros) {
        if (metros <= 0) return;
        this.coordenada_atual[0] += metros;
    }

    public void moverEsquerda(float metros) {
        if (metros <= 0) return;
        this.coordenada_atual[0] -= metros;
    }

    public void moverFrente(float metros) {
        if (metros <= 0) return;
        this.coordenada_atual[1] -= metros;
    }

    public void moverTras(float metros) {
        if (metros <= 0) return;
        this.coordenada_atual[1] += metros;
    }

    public void moverParaCoordenada(float[] coordenadas) {
        if (coordenadas.length != 3) {
            throw new IllegalArgumentException("Array \"coordenadas\" possui tamanho diferente de 3");
        }

        for (int i = 0; i < 2; i++) {
            this.coordenada_atual[i] = coordenadas[i];
        }

        this.coordenada_atual[2] = coordenadas[2] > 0 ? coordenadas[2] : 0;
        
        return;
    }

    public double calcularDistancia(float[] coordenadas) {
        if (coordenadas.length != 3) {
            throw new IllegalArgumentException("Array \"coordenadas\" possui tamanho diferente de 3");
        }

        return Math.sqrt(Math.pow((double) (this.coordenada_atual[0] - coordenadas[0]), 2) +
                Math.pow((double) (this.coordenada_atual[1] - coordenadas[1]), 2) +
                Math.pow((double) (this.coordenada_atual[2] - coordenadas[2]), 2));
    }

    public double calcularDistanciaDrone(Drone novo_drone) {

        return Math.sqrt(Math.pow((double) (this.coordenada_atual[0] - novo_drone.coordenada_atual[0]), 2) +
                Math.pow((double) (this.coordenada_atual[1] - novo_drone.coordenada_atual[1]), 2) +
                Math.pow((double) (this.coordenada_atual[2] - novo_drone.coordenada_atual[2]), 2));
    }

    public void retornarPontoPartida() {
        System.arraycopy(this.coordenada_inicial, 0, this.coordenada_atual, 0, this.coordenada_atual.length);
    }

    @Override
    public String toString() {
        String result = this.identificador + " " + this.marca + " " + this.modelo + " " +
        this.cor + " " + Float.toString(this.dimensoes[0]) + " " + Float.toString(this.dimensoes[1]) + " " + 
        Float.toString(this.dimensoes[2]) + " " + Float.toString(peso) + " " +
        Float.toString(this.vel_maxima) + " " + Float.toString(this.vel_atual) + " " +
        Float.toString(this.altitude_max) + " " + Float.toString(this.altitude_atual) + " " + 
        Float.toString(this.coordenada_inicial[0]) + " " + Float.toString(this.coordenada_inicial[1]) + " " + 
        Float.toString(this.coordenada_inicial[2]) + " " + Float.toString(this.coordenada_atual[0]) + " " + 
        Float.toString(this.coordenada_atual[1]) + " " + Float.toString(this.coordenada_atual[2]) + " " +
        Integer.toString(this.nivel_bateria);

        return result;
    }

    public static void main(String[] args) {

        Drone n1 = new Drone(20, "Xiaomi", "REDMI98", "amarelo", 
                     new float[] { 10.1f, 20.2f, 30.3f }, 10f, 200f, 0f, 70f, 0f,
                     new float[] { 0f, 0f, 0f }, new float[] { 0f, 0f, 0f }, 63);
        Drone n2 = new Drone(40, "LGTV", "LG-31", "azul", 
                      new float[] { 12.1f, 24.2f, 36.3f }, 10f, 200f, 0f, 70f, 0f,
                      new float[] { 0f, 0f, 0f }, new float[] { 0f, 0f, 0f }, 63);

        n1.imprimir();
        n1.subir(170f);
        n1.imprimir();
        System.out.println(n1.calcularDistanciaDrone(n2));
        n1.retornarPontoPartida();
        n1.imprimir();

        for(float a: n1.getDimensoes()) {
            System.out.println(a);
        }

        DroneDAO persistenciaDrone = new DroneDAO("drones.txt");
        persistenciaDrone.add(n1);
        persistenciaDrone.add(n2);

        System.out.println("Salvando Drones:");
        persistenciaDrone.save();
        System.out.println("Carregando Drones:");
        persistenciaDrone.load();

        for (int i = 0 ; i < persistenciaDrone.getNumDrones() ; i++) {
            System.out.println(persistenciaDrone.getDrone(i));
        }
    }
}
