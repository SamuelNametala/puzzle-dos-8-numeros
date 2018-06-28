package codigo;

import java.util.ArrayList;

/**
 *
 * @author Samuel Nametala
 */
public class BuscaAEstrela {

    public int nos_explorados;
    /*Contem os valores do objetivo final.*/
    public int[][] tabuleiro_objetivo;
    /*Guarda todos os nós criados.*/
    public ArrayList<No> lista_nos;
    /*Guarda todos os nós da borda que ainda não foram verificados se são o nó objetivo.*/
    public ArrayList<No> lista_nos_borda;

    /*Construtor.*/
    public BuscaAEstrela() {

        nos_explorados=0;
        lista_nos = new ArrayList();
        lista_nos_borda = new ArrayList();
        tabuleiro_objetivo = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        //tabuleiro_objetivo = new int[][]{{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};
    }

    /*Inicia o processo de busca A Estrela.*/
    public No iniciarBusca(int[][] tabuleiro, int id_heuristica) {

        /*Variável para guardar o nó objetivo assim que ele for encontrado. Seu valor é iniciado com Null, 
        para caso não encontre um nó, seja retornado esse valor.*/
        No no_objetivo = null;
        nos_explorados=0;
        /*Variável para guardar o id_no do nó com menor custo.*/
        int menor_no;

        /*Cria o primeiro nó e o adiciona na lista_nos.*/
        lista_nos.add(new No(tabuleiro, (lista_nos.size())));
        /*Incrementa o valor da heurística total deste nó.*/
        lista_nos.get(lista_nos.size() - 1).custo_total = calcularCusto(lista_nos.get(lista_nos.size() - 1), id_heuristica);
        /*Adiciona o primeiro nó na lista_nos_borda.*/
        lista_nos_borda.add(lista_nos.get(0));

        /*Enquanto a lista_nos_borda não estiver vazia faça.*/
        while (!lista_nos_borda.isEmpty()) {
            
           /*Atualiza o valor do menor no.*/
            menor_no = menorCusto();
            
            System.out.println("Explorando: "+lista_nos_borda.get(menor_no).id_no);
            
            /*Se o nó atual é o objetivo faça.*/
            if (eObjetivo(lista_nos_borda.get(menor_no))) {
                
                /*Atualiza o valor do no_objetivo.*/
                no_objetivo = lista_nos_borda.get(menor_no);
                /*Limpa a lista_nos_borda para encerrar a busca.*/
                lista_nos_borda.clear();
            } else {

                /*Se custo_total é menor ou igual au limite faça.*/
                nos_explorados++;
                /*Cria os possíveis filhos do nó atual.*/
                gerarNosFilhos(lista_nos_borda.get(menor_no), id_heuristica);
                /*Remove o nó atual da lista menor_nos.*/
                lista_nos_borda.remove(menor_no);
            }
        }

        /*Retorna o nó objetivo.*/
        return no_objetivo;
    }

    /*Explora toda a lista_nos_borda, procurando qual deles tem o menor custo, para continuar com a busca a partir dele. 
    Caso está função seja chamada, quer dizer que existe pelo menor um nó na lista_nos_borda*/
    public int menorCusto() {

        /*Guarda o custo do menor nó encontrado. Esse valor é iniciado com o custo do primeiro no da lista_nos_borda.*/
        int menor_custo = lista_nos_borda.get(0).custo_total;
        /*Guarda a posição da lista_nos_borda onde está o nó com menor custo. Esse valor é iniciado com 0 para pegar o primeiro no da lista_nos_borda.*/
        int menor_no = 0;

        /*Explora toda a lista_nos_borda.*/
        for (int i = 0; i < lista_nos_borda.size(); i++) {

            /*Verifica se o custo do nó explorado neste momento é menor que o custo do nó anterior.*/
            if (menor_custo > lista_nos_borda.get(i).custo_total) {

                /*Atualiza o valor do menor custo.*/
                menor_custo = lista_nos_borda.get(i).custo_total;
                /*Atualiza o valor da posição onde está o no com menor curto.*/
                menor_no = i;
            }
        }

        /*Retorna a posição em que se encontra o nó com menor custo.*/
        return menor_no;
    }

    /*Realiza o calculo do custo total do nó de acordo com a heurística escolhida.*/
    public int calcularCusto(No no, int id_heuristica) {

        /*Variável que guarda o calculo da heurística.*/
        int heuristica_total = 0;

        switch (id_heuristica) {
            
            /*Realiza o calculo da heurística 1 que é a soma do número de peças fora do lugar.*/
            case 1:
                
                /*Explora cada posição do tabuleiro deste nó.*/
                for (int i = 0; i <= 2; i++) {
                    for (int j = 0; j <= 2; j++) {
                        
                        /*Se o valor da posição atual do tabuleiro for diferente de 0 e for diferente do valor da mesma posição do tabuleiro_objetivo, faça.*/
                        if (no.tabuleiro[i][j] != 0 && no.tabuleiro[i][j] != tabuleiro_objetivo[i][j]) {
                            
                            /*Incrementa o valor da heurística total em + 1.*/
                            heuristica_total++;
                        }
                    }
                }
            break;
            
            /*Realiza o calculo da heurística 2 que é a soma das distancias entre as peças e suas respectivas posições.*/
            case 2:
                
                /*Explora cada posição do tabuleiro deste nó.*/
                for (int i = 0; i <= 2; i++) {
                    for (int j = 0; j <= 2; j++) {

                        /*Para cada posição do tabuleiro explora todo o tabuleiro_objetivo até encontrar um valor que seja igual a atual posição do tabuleiro.*/
                        for (int k = 0; k <= 2; k++) {
                            for (int l = 0; l <= 2; l++) {

                                /*Se o valor da posição atual do tabuleiro for diferente de 0 e for igual a posição atual do tabuleiro_objetivo, faça.*/
                                if (no.tabuleiro[i][j] != 0 && no.tabuleiro[i][j] == tabuleiro_objetivo[k][l]) {
                                    
                                    /*Incrementa o valor da heurística total com a distancia da posição atual em relação a sua respectiva posição original.*/
                                    heuristica_total = heuristica_total + (Math.abs(i - k) + Math.abs(j - l));
                                }
                            }
                        }
                    }
                }
            break;
        }

        /*Retorna o custo total da heurística.*/
        return heuristica_total;
    }

    /*Verifica se o nó é o nó tabuleiro_objetivo.*/
    public boolean eObjetivo(No no) {
        
        /*Variável que contem a resposta se o nó que será verificado é o nó objetivo ou não. 
        Casa seu valor seja True é o nó objetivo, caso contrario seu valor é False. 
        Ela é iniciado com True e ao constatar que não é o objetivo recebe False.*/
        boolean e_no_objetivo = true;

        /*Explora cada posição do tabuleiro deste nó.*/
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                
                /*Se o valor da posição atual do tabuleiro for diferente do valor da mesma posição do tabuleiro_objetivo, faça.*/
                if (no.tabuleiro[i][j] != tabuleiro_objetivo[i][j]) {
                    
                    /*Nó não é o objetivo, então muda o valor da e_no_objetivo*/
                    e_no_objetivo = false;
                    /*Incrementa i e j para finalizar a exploração.*/
                    i = 3;
                    j = 3;
                }
            }
        }

        /*Retorna resposta.*/
        return e_no_objetivo;
    }

    public boolean jaVisitado(No no) {
        boolean ja_visitado = false;
        
        for (int i = 0; i < lista_nos.size()-1; i++) {
            if (no.codigo == lista_nos.get(i).codigo) {
                ja_visitado = true;
                i = lista_nos.size();
            }
        }

        return ja_visitado;
    }
    
    public void gerarNosFilhos(No no_pai, int id_heuristica) {

        /*Explora cada posição do tabuleiro deste nó.*/
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {

                /*Se a posição atual for igual a 0 faça.*/
                if (no_pai.tabuleiro[i][j] == 0) {

                    /*Se existir uma posição acima da posição atual e se esta posição acima do pai deste nó pai for diferente de 0 faça.*/
                    if ((i - 1) >= 0) {

                        /*Cria um nó filho e o adiciona na lista_nos.*/
                        lista_nos.add(new No(no_pai, lista_nos.size(), i, j, i-1, j));
                        if (!jaVisitado(lista_nos.get(lista_nos.size() - 1))) {
                            /*Incrementa o valor da heurística total deste nó.*/
                            lista_nos.get(lista_nos.size() - 1).custo_total = calcularCusto(lista_nos.get(lista_nos.size() - 1), id_heuristica) + lista_nos.get(lista_nos.size() - 1).profundiade;
                            /*Adiciona o novo nó filho na lista_nos_borda.*/
                            lista_nos_borda.add(lista_nos.get(lista_nos.size() - 1));
                        } else {
                            lista_nos.remove(lista_nos.get(lista_nos.size() - 1));
                        }
                    }

                    /*Se existir uma posição a direita da posição atual e se esta posição a direita do pai deste nó pai for diferente de 0 faça.*/
                    if ((j + 1) <= 2) {
                        lista_nos.add(new No(no_pai, lista_nos.size(), i, j, i, j+1));
                        if (!jaVisitado(lista_nos.get(lista_nos.size() - 1))) {
                            /*Incrementa o valor da heurística total deste nó.*/
                            lista_nos.get(lista_nos.size() - 1).custo_total = calcularCusto(lista_nos.get(lista_nos.size() - 1), id_heuristica) + lista_nos.get(lista_nos.size() - 1).profundiade;
                            /*Adiciona o novo nó filho na lista_nos_borda.*/
                            lista_nos_borda.add(lista_nos.get(lista_nos.size() - 1));
                        } else {
                            lista_nos.remove(lista_nos.get(lista_nos.size() - 1));
                        }
                    }

                    /*Se existir uma posição abaixo da posição atual e se esta posição abaixo do pai deste nó pai for diferente de 0 faça.*/
                    if ((i + 1) <= 2) {
                        lista_nos.add(new No(no_pai, lista_nos.size(), i, j, i+1, j));
                        if (!jaVisitado(lista_nos.get(lista_nos.size() - 1))) {
                            /*Incrementa o valor da heurística total deste nó.*/
                            lista_nos.get(lista_nos.size() - 1).custo_total = calcularCusto(lista_nos.get(lista_nos.size() - 1), id_heuristica) + lista_nos.get(lista_nos.size() - 1).profundiade;
                            /*Adiciona o novo nó filho na lista_nos_borda.*/
                            lista_nos_borda.add(lista_nos.get(lista_nos.size() - 1));
                        } else {
                            lista_nos.remove(lista_nos.get(lista_nos.size() - 1));
                        }
                    }

                    /*Se existir uma posição a esquerda da posição atual e se esta posição a esquerda do pai deste nó pai for diferente de 0 faça.*/
                    if ((j - 1) >= 0) {
                        lista_nos.add(new No(no_pai, lista_nos.size(), i, j, i, j-1));
                        if (!jaVisitado(lista_nos.get(lista_nos.size() - 1))) {
                            /*Incrementa o valor da heurística total deste nó.*/
                            lista_nos.get(lista_nos.size() - 1).custo_total = calcularCusto(lista_nos.get(lista_nos.size() - 1), id_heuristica) + lista_nos.get(lista_nos.size() - 1).profundiade;
                            /*Adiciona o novo nó filho na lista_nos_borda.*/
                            lista_nos_borda.add(lista_nos.get(lista_nos.size() - 1));
                        } else {
                            lista_nos.remove(lista_nos.get(lista_nos.size() - 1));
                        }
                    }

                    /*Incrementa i e j para finalizar a exploração.*/
                    i = 3;
                    j = 3;
                }
            }
        }
    }
}
