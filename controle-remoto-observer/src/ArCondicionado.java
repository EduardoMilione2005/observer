package remote;

import java.util.ArrayList;
import java.util.List;


public class ArCondicionado implements Observer {

    private boolean ligado = false;
    private final List<String> historico = new ArrayList<>();

    @Override
    public void update(String evento, Object dados) {
        String msg;
        switch (evento) {
            case "LIGAR":
                ligado = true;
                msg = "[Ar] Ar-condicionado LIGADO";
                break;
            case "DESLIGAR":
                ligado = false;
                msg = "[Ar] Ar-condicionado DESLIGADO";
                break;
            default:
                msg = "[Ar] Comando '" + evento + "' ignorado";
        }
        historico.add(msg);
        System.out.println(msg);
    }

    public boolean isLigado()          { return ligado;    }
    public List<String> getHistorico() { return historico; }
}
