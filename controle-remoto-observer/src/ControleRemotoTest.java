package remote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Padrão Observer — Controle Remoto")
class ControleRemotoTest {

    private ControleRemoto controle;
    private Televisao      tv;
    private SomAmbiente    som;
    private ArCondicionado ar;

    @BeforeEach
    void setUp() {
        controle = new ControleRemoto();
        tv       = new Televisao();
        som      = new SomAmbiente();
        ar       = new ArCondicionado();
    }
    
    @Nested
    @DisplayName("Registro de Dispositivos")
    class RegistroDispositivos {

        @Test
        @DisplayName("Deve registrar um dispositivo corretamente")
        void deveRegistrarUmDispositivo() {
            controle.adicionarObserver(tv);
            assertEquals(1, controle.getObserverCount());
        }

        @Test
        @DisplayName("Deve registrar múltiplos dispositivos")
        void deveRegistrarMultiplosDispositivos() {
            controle.adicionarObserver(tv);
            controle.adicionarObserver(som);
            controle.adicionarObserver(ar);
            assertEquals(3, controle.getObserverCount());
        }

        @Test
        @DisplayName("Não deve registrar o mesmo dispositivo duas vezes")
        void naoDeveRegistrarDuplicatas() {
            controle.adicionarObserver(tv);
            controle.adicionarObserver(tv);
            assertEquals(1, controle.getObserverCount());
        }

        @Test
        @DisplayName("Deve lançar exceção ao adicionar observer nulo")
        void deveLancarExcecaoComObserverNulo() {
            assertThrows(IllegalArgumentException.class,
                    () -> controle.adicionarObserver(null));
        }
    }
    
    @Nested
    @DisplayName("Remoção de Dispositivos")
    class RemocaoDispositivos {

        @Test
        @DisplayName("Deve remover um dispositivo corretamente")
        void deveRemoverDispositivo() {
            controle.adicionarObserver(tv);
            controle.adicionarObserver(som);
            controle.removerObserver(tv);
            assertEquals(1, controle.getObserverCount());
        }

        @Test
        @DisplayName("Remover dispositivo não registrado não lança exceção")
        void removerInexistenteNaoLancaExcecao() {
            assertDoesNotThrow(() -> controle.removerObserver(tv));
        }

        @Test
        @DisplayName("Dispositivo removido não deve receber notificações")
        void dispositivoRemovidoNaoRecebeNotificacao() {
            controle.adicionarObserver(tv);
            controle.ligar();
            controle.removerObserver(tv);
            controle.desligar();
            assertTrue(tv.isLigada());
        }
    }
    
    @Nested
    @DisplayName("Televisão")
    class TestesTelevisao {

        @Test
        @DisplayName("TV deve ligar ao receber comando LIGAR")
        void tvDeveLigar() {
            controle.adicionarObserver(tv);
            controle.ligar();
            assertTrue(tv.isLigada());
        }

        @Test
        @DisplayName("TV deve desligar ao receber comando DESLIGAR")
        void tvDeveDesligar() {
            controle.adicionarObserver(tv);
            controle.ligar();
            controle.desligar();
            assertFalse(tv.isLigada());
        }

        @Test
        @DisplayName("TV deve mudar de canal quando ligada")
        void tvDeveMudarCanal() {
            controle.adicionarObserver(tv);
            controle.ligar();
            controle.mudarCanal(7);
            assertEquals(7, tv.getCanal());
        }

        @Test
        @DisplayName("TV não deve mudar de canal quando desligada")
        void tvNaoDeveMudarCanalDesligada() {
            controle.adicionarObserver(tv);
            controle.mudarCanal(7);
            assertEquals(1, tv.getCanal());
        }

        @Test
        @DisplayName("TV deve aumentar volume quando ligada")
        void tvDeveAumentarVolume() {
            controle.adicionarObserver(tv);
            controle.ligar();
            controle.aumentarVolume();
            controle.aumentarVolume();
            assertEquals(2, tv.getVolume());
        }

        @Test
        @DisplayName("TV não deve alterar volume quando desligada")
        void tvNaoDeveAlterarVolumeDesligada() {
            controle.adicionarObserver(tv);
            int volumeAntes = tv.getVolume();
            controle.aumentarVolume();
            assertEquals(volumeAntes, tv.getVolume());
        }
    }
    
    @Nested
    @DisplayName("Som Ambiente")
    class TestesSomAmbiente {

        @Test
        @DisplayName("Som deve ligar ao receber comando LIGAR")
        void somDeveLigar() {
            controle.adicionarObserver(som);
            controle.ligar();
            assertTrue(som.isLigado());
        }

        @Test
        @DisplayName("Som deve desligar ao receber comando DESLIGAR")
        void somDeveDesligar() {
            controle.adicionarObserver(som);
            controle.ligar();
            controle.desligar();
            assertFalse(som.isLigado());
        }

        @Test
        @DisplayName("Som deve ignorar comando de canal")
        void somDeveIgnorarCanal() {
            controle.adicionarObserver(som);
            controle.ligar();
            controle.mudarCanal(3);
            assertTrue(som.getHistorico().stream()
                    .anyMatch(h -> h.contains("ignorado")));
        }

        @Test
        @DisplayName("Som deve aumentar volume quando ligado")
        void somDeveAumentarVolume() {
            controle.adicionarObserver(som);
            controle.ligar();
            controle.aumentarVolume();
            assertEquals(1, som.getVolume());
        }
    }
    
    @Nested
    @DisplayName("Ar-Condicionado")
    class TestesArCondicionado {

        @Test
        @DisplayName("Ar deve ligar ao receber comando LIGAR")
        void arDeveLigar() {
            controle.adicionarObserver(ar);
            controle.ligar();
            assertTrue(ar.isLigado());
        }

        @Test
        @DisplayName("Ar deve desligar ao receber comando DESLIGAR")
        void arDeveDesligar() {
            controle.adicionarObserver(ar);
            controle.ligar();
            controle.desligar();
            assertFalse(ar.isLigado());
        }

        @Test
        @DisplayName("Ar deve ignorar comandos de volume e canal")
        void arDeveIgnorarOutrosComandos() {
            controle.adicionarObserver(ar);
            controle.ligar();
            controle.aumentarVolume();
            controle.mudarCanal(4);
            assertTrue(ar.getHistorico().stream()
                    .filter(h -> h.contains("ignorado"))
                    .count() >= 2);
        }
    }
    
    @Nested
    @DisplayName("Notificação Múltipla")
    class NotificacaoMultipla {

        @Test
        @DisplayName("Todos os dispositivos devem ser notificados ao ligar")
        void todosNotificadosAoLigar() {
            controle.adicionarObserver(tv);
            controle.adicionarObserver(som);
            controle.adicionarObserver(ar);
            controle.ligar();

            assertAll("todos ligados",
                    () -> assertTrue(tv.isLigada()),
                    () -> assertTrue(som.isLigado()),
                    () -> assertTrue(ar.isLigado())
            );
        }

        @Test
        @DisplayName("Não deve lançar exceção ao notificar sem dispositivos")
        void semDispositivosNaoLancaExcecao() {
            assertDoesNotThrow(() -> controle.ligar());
        }
    }
}
