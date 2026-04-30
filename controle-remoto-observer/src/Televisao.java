package remote;

import java.util.ArrayList;
import java.util.List;

public class Televisao implements Observer {

    private boolean ligada = false;
    private int     canal  = 1;
    private int     volume = 10;
    private final List<String> historico = new ArrayList<>();

    @Override
    public void update(String evento, Object dados) {
        if (!(dados instanceof ControleRemoto.ComandoRemoto)) return;
        ControleRemoto.ComandoRemoto cmd = (ControleRemoto.ComandoRemoto) dados;

        String msg;
        switch (evento) {
            case "LIGAR":
                ligada = true;
                msg = "[TV] Televisão LIGADA";
                break;
            case "DESLIGAR":
                ligada = false;
                msg = "[TV] Televisão DESLIGADA";
                break;
            case "VOL+":
                if (ligada) { volume = cmd.volume; msg = "[TV] Volume aumentado → " + volume; }
                else msg = "[TV] Desligada, ignorando VOL+";
                break;
            case "VOL-":
                if (ligada) { volume = cmd.volume; msg = "[TV] Volume diminuído → " + volume; }
                else msg = "[TV] Desligada, ignorando VOL-";
                break;
            case "CANAL":
                if (ligada) { canal = cmd.canal; msg = "[TV] Canal alterado → " + canal; }
                else msg = "[TV] Desligada, ignorando CANAL";
                break;
            default:
                msg = "[TV] Comando desconhecido: " + evento;
        }
        historico.add(msg);
        System.out.println(msg);
    }

    public boolean isLigada()            { return ligada;    }
    public int     getCanal()            { return canal;     }
    public int     getVolume()           { return volume;    }
    public List<String> getHistorico()   { return historico; }
}
