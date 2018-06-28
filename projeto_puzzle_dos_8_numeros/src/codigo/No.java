package codigo;

/**
 *
 * @author Samuel Nametala
 */
public class No {

    public String aux;
    public int codigo;
    /*Guarda o id do nó.*/
    public int id_no;
    /*Guarda o id do pai deste nó.*/
    public int id_no_pai;
    /*Guarda a profundidade do nó.*/
    public int profundiade;
    /*Guarda o custo total do nó.*/
    public int custo_total;
    /*Guarda os valores das posições das peças do tabuleiro para este nó.*/
    public int[][] tabuleiro;

    /*Construtor da classe para o primeiro nó que for criado.*/
    public No(int[][] atual, int id) {

        this.id_no = id;
        id_no_pai = 0;
        custo_total = 0;
        profundiade = 0;

        tabuleiro = new int[3][3];

        /*Copia os valores de cada posição do tabuleiro do nó pai para o tabuleiro deste nó.*/
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                tabuleiro[i][j] = atual[i][j];
            }
        }
        
        atualizaCodigo();
    }

    /*Construtor da classe.*/
    public No(No no, int id, int i, int j, int k, int l) {

        this.id_no = id;
        id_no_pai = no.id_no;
        custo_total = 0;
        profundiade = no.profundiade + 1;

        tabuleiro = new int[3][3];

        tabuleiro[0][0] = no.tabuleiro[0][0];
        tabuleiro[0][1] = no.tabuleiro[0][1];
        tabuleiro[0][2] = no.tabuleiro[0][2];
        tabuleiro[1][0] = no.tabuleiro[1][0];
        tabuleiro[1][1] = no.tabuleiro[1][1];
        tabuleiro[1][2] = no.tabuleiro[1][2];
        tabuleiro[2][0] = no.tabuleiro[2][0];
        tabuleiro[2][1] = no.tabuleiro[2][1];
        tabuleiro[2][2] = no.tabuleiro[2][2];
        
        tabuleiro[i][j]=tabuleiro[k][l];
        tabuleiro[k][l]=0;
        
        atualizaCodigo();
    }
    
    public void atualizaCodigo() {
        aux = ""+tabuleiro[0][0]+tabuleiro[0][1]+tabuleiro[0][2]+tabuleiro[1][0]+tabuleiro[1][1]+tabuleiro[1][2]+tabuleiro[2][0]+tabuleiro[2][1];
        codigo = Integer.parseInt(aux);
    }
}
