package remote;

import java.util.ArrayList;
import java.util.List;

public class ControleRemoto implements Subject {

    private final List<Observer> observers = new ArrayList<>();

    private String botaoPressionado;
    private int    canal;
    private int    volume;
    private boolean ligado;

    @Override
    public void adicionarObserver(Observer observer) {
        if (observer == null) throw new IllegalArgumentException("Observer não pode ser nulo.");
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removerObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notificarObservers() {
        ComandoRemoto comando = new ComandoRemoto(botaoPressionado, canal, volume, ligado);
        for (Observer observer : new ArrayList<>(observers)) {
            observer.update(botaoPressionado, comando);
        }
    }

    public void pressionarBotao(String botao) {
        this.botaoPressionado = botao;
        System.out.println("\n[Controle] Botão pressionado: " + botao);
        notificarObservers();
    }

    public void ligar() {
        this.ligado = true;
        this.botaoPressionado = "LIGAR";
        System.out.println("\n[Controle] Botão pressionado: LIGAR");
        notificarObservers();
    }

    public void desligar() {
        this.ligado = false;
        this.botaoPressionado = "DESLIGAR";
        System.out.println("\n[Controle] Botão pressionado: DESLIGAR");
        notificarObservers();
    }

    public void aumentarVolume() {
        this.volume++;
        this.botaoPressionado = "VOL+";
        System.out.println("\n[Controle] Botão pressionado: VOL+ (volume: " + volume + ")");
        notificarObservers();
    }

    public void diminuirVolume() {
        if (this.volume > 0) this.volume--;
        this.botaoPressionado = "VOL-";
        System.out.println("\n[Controle] Botão pressionado: VOL- (volume: " + volume + ")");
        notificarObservers();
    }

    public void mudarCanal(int canal) {
        this.canal = canal;
        this.botaoPressionado = "CANAL";
        System.out.println("\n[Controle] Botão pressionado: CANAL " + canal);
        notificarObservers();
    }

    public String getBotaoPressionado() { return botaoPressionado; }
    public int    getCanal()            { return canal; }
    public int    getVolume()           { return volume; }
    public boolean isLigado()           { return ligado; }
    public int    getObserverCount()    { return observers.size(); }

    public static class ComandoRemoto {
        public final String  botao;
        public final int     canal;
        public final int     volume;
        public final boolean ligado;

        public ComandoRemoto(String botao, int canal, int volume, boolean ligado) {
            this.botao  = botao;
            this.canal  = canal;
            this.volume = volume;
            this.ligado = ligado;
        }
    }
}
