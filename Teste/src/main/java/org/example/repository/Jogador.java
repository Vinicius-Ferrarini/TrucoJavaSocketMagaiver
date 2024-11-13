public class TrucoGame {
    private List<Jogador> jogadores;
    private Baralho baralho;
    private int turnoAtual;
    private int valorRodada;

    public TrucoGame() {
        this.jogadores = new ArrayList<>();
        this.baralho = new Baralho();  // Usa a classe Baralho que você forneceu
        this.turnoAtual = 0;  // Começa com o jogador 1
        this.valorRodada = 1;  // Valor inicial da rodada
        baralho.embaralhar();  // Embaralha as cartas ao iniciar
    }

    // Adiciona um jogador ao jogo
    public void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
    }

    // Inicializa e distribui as cartas aos jogadores
    public void distribuirCartas() {
        baralho.embaralhar();  // Embaralha novamente antes de distribuir
        for (Jogador jogador : jogadores) {
            List<Cartas> mao = baralho.distribuirCartas(3);  // Distribui 3 cartas por jogador
            jogador.receberCartas(mao);  // Atualiza a mão do jogador
        }
    }

    // Alterna o turno entre os jogadores
    public void alternarTurno() {
        turnoAtual = (turnoAtual + 1) % jogadores.size();  // Alterna entre 0 e 1 para dois jogadores
    }

    // Exibe informações do jogo
    public void exibirInformacoes() {
        System.out.println("Jogo iniciado. Turno atual: Jogador " + (turnoAtual + 1));
        System.out.println("Valor da rodada: " + valorRodada);
    }

    // Método para rodar uma rodada
    public void rodarRodada() {
        // Distribuir as cartas no início da rodada
        distribuirCartas();
        exibirInformacoes();

        // A lógica para cada jogador jogar uma carta
        Jogador jogadorAtual = jogadores.get(turnoAtual);
        // Aqui vamos supor que o jogador escolhe a carta de forma automatizada por enquanto
        System.out.println("Jogador " + jogadorAtual.getId() + " está jogando.");

        // Simulando uma jogada onde o jogador escolhe a primeira carta da mão
        Cartas cartaJogada = jogadorAtual.getMao().get(0);
        System.out.println("Jogador " + jogadorAtual.getId() + " jogou: " + cartaJogada);

        // Alternar o turno após a jogada
        alternarTurno();
    }

    public static void main(String[] args) {
        TrucoGame jogo = new TrucoGame();

        // Criando jogadores
        Jogador jogador1 = new Jogador(1);
        Jogador jogador2 = new Jogador(2);

        // Adicionando os jogadores
        jogo.adicionarJogador(jogador1);
        jogo.adicionarJogador(jogador2);

        // Iniciando a primeira rodada
        jogo.rodarRodada();
    }
}
