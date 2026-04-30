package remote;

import java.util.ArrayList;
import java.util.List;


public class SomAmbiente implements Observer {

    private boolean ligado = false;
    private int     volume = 15;
    private final List<String> historico = new ArrayList<>();

    @Override
    public void update(String evento, Object dados) {
        if (!(dados instanceof ControleRemoto.ComandoRemoto)) return;
        ControleRemoto.ComandoRemoto cmd = (ControleRemoto.ComandoRemoto) dados;

        String msg;
        switch (evento) {
            case "LIGAR":
                ligado = true;
                msg = "[Som] Sistema de som LIGADO";
                break;
            case "DESLIGAR":
                ligado = false;
                msg = "[Som] Sistema de som DESLIGADO";
                break;
            case "VOL+":
                if (ligado) { volume = cmd.volume; msg = "[Som] Volume aumentado → " + volume; }
                else msg = "[Som] Desligado, ignorando VOL+";
                break;
            case "VOL-":
                if (ligado) { volume = cmd.volume; msg = "[Som] Volume diminuído → " + volume; }
                else msg = "[Som] Desligado, ignorando VOL-";
                break;
            case "CANAL":
                msg = "[Som] Comando CANAL ignorado (não aplicável)";
                break;
            default:
                msg = "[Som] Comando desconhecido: " + evento;
        }
        historico.add(msg);
        System.out.println(msg);
    }

    public boolean isLigado()          { return ligado;    }
    public int     getVolume()         { return volume;    }
    public List<String> getHistorico() { return historico; }
}
