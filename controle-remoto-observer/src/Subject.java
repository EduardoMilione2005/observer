package remote;

public interface Subject {
    void adicionarObserver(Observer observer);
    void removerObserver(Observer observer);
    void notificarObservers();
}
