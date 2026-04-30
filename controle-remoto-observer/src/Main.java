package remote;

public class Main {
    public static void main(String[] args) {

        ControleRemoto controle = new ControleRemoto();

        Televisao     tv  = new Televisao();
        SomAmbiente   som = new SomAmbiente();
        ArCondicionado ar  = new ArCondicionado();
        
        controle.adicionarObserver(tv);
        controle.adicionarObserver(som);
        controle.adicionarObserver(ar);

        System.out.println("=== Ligando todos os dispositivos ===");
        controle.ligar();

        System.out.println("\n=== Aumentando volume ===");
        controle.aumentarVolume();
        controle.aumentarVolume();
        controle.aumentarVolume();

        System.out.println("\n=== Mudando canal da TV ===");
        controle.mudarCanal(5);

        System.out.println("\n=== Removendo Som Ambiente do controle ===");
        controle.removerObserver(som);
        controle.diminuirVolume();

        System.out.println("\n=== Desligando todos os dispositivos ===");
        controle.desligar();
    }
}
