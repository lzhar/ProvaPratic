package analisadores;

import java.io.*;
import java.util.Arrays;
import java.io.File;

public class AnalisadorUm implements Runnable {

    private File pastaParaAnalise;
    private final int tamanhoDoBatchParaThread1 = 30;
    private final int tamanhoDoBatchParaDuasThread = 15;
    private final int tamanhoDoBatchParaTresThread = 10;
    private final int tamanhoDoBatchParaQuatroThread = 6;
    private final int tamanhoDoBatchParaCincoThread = 5;
    private final int tamanhoDoBatchParaSeisThread = 3;
    private final int tamanhoDoBatchParaSeteThread = 2;
    private final int tamanhoDoBatchParaOitoThread = 1;


    private int numeroDeThreadsUsadas = 1;
    private int tamanhoAtualDoBatch;


    public AnalisadorUm(File pastaParaAnalise, int numeroDeThreadsUsadas) {
        this.pastaParaAnalise = pastaParaAnalise;
        this.numeroDeThreadsUsadas = numeroDeThreadsUsadas;
        definirTamanhoDoBatch();
    }

    private void definirTamanhoDoBatch() {
        switch (numeroDeThreadsUsadas) {
            case 1:
                tamanhoAtualDoBatch = tamanhoDoBatchParaThread1;
                break;
            case 2:
                tamanhoAtualDoBatch = tamanhoDoBatchParaDuasThread;
                break;
            case 3:
                tamanhoAtualDoBatch = tamanhoDoBatchParaTresThread;
                break;
            case 4:
                tamanhoAtualDoBatch = tamanhoDoBatchParaQuatroThread;
                break;
            case 5:
                tamanhoAtualDoBatch = tamanhoDoBatchParaCincoThread;
                break;
            case 6:
                tamanhoAtualDoBatch = tamanhoDoBatchParaSeisThread;
                break;
            case 7:
                tamanhoAtualDoBatch = tamanhoDoBatchParaSeteThread;
                break;
            case 8:
                tamanhoAtualDoBatch = tamanhoDoBatchParaOitoThread;
                break;
            default:
                tamanhoAtualDoBatch = tamanhoDoBatchParaThread1;
                System.out.println("O numero que você informou de threads não é valido vamos usar o padrão " + tamanhoDoBatchParaThread1);
                break;
        }
    }

    public void run() {

        File[] arquivos = pastaParaAnalise.listFiles();
        if (arquivos != null) {
            Arrays.sort(arquivos);
            for (File arquivo : arquivos) {
                if (arquivo.isFile()) {
                    try (BufferedReader leitorArquivo = new BufferedReader(new FileReader(arquivo))) {
                        String linha;
                        int linhasLidasNoBatchAtual = 0;
                        while ((linha = leitorArquivo.readLine()) != null) {
                            linha = linha.trim();
                            boolean validoOuNao = CPFValidator.validaCPF(linha);
                            System.out.println("[analisador " + Thread.currentThread().getName() + "] CPF lido: " + linha + " | " + (validoOuNao ? "VALIDO" : "INVALIDO") + " (arquivo: " + arquivo.getName() + ")");
                            linhasLidasNoBatchAtual++;

                            if (linhasLidasNoBatchAtual % tamanhoAtualDoBatch == 0) {
                                System.out.println("[analisador " + Thread.currentThread().getName() + "] Fim do batch de " + tamanhoAtualDoBatch + " linhas.");

                            }
                        }
                        if (linhasLidasNoBatchAtual % tamanhoAtualDoBatch != 0) {
                            System.out.println("[analisador " + Thread.currentThread().getName() + "] Fim do arquivo " + arquivo.getName() + " com " + (linhasLidasNoBatchAtual % tamanhoAtualDoBatch) + " linhas restantes.");
                        }
                    } catch (IOException f) {
                        System.out.println("Erro no analisador " + Thread.currentThread().getName() + " ao ler o arquivo: " + arquivo.getName());
                        f.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("A pasta que o programa tentou acessar não existe!.");
        }
    }
}