import analisadores.AnalisadorUm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TesteComDuasThreads {
    public static void main(String[] args){
        long tempoParaIniciar = System.currentTimeMillis();
        File pastaParaAnalise = new File("src/cpfs");
        Runnable analisadorUm = new AnalisadorUm(pastaParaAnalise,2 );

        Thread t1 = new Thread(analisadorUm);
        Thread t2 = new Thread(analisadorUm);


        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException g) {
            g.printStackTrace();
        }
        long tempoParaFinalizar = System.currentTimeMillis();
        long tempoDeExecucao = tempoParaFinalizar - tempoParaIniciar;
        try(FileWriter fW = new FileWriter("tempo_que_levou_para_fim_da_exec_utlizando2Threads.txt")){
            fW.write("Tempo total de execução = " + tempoDeExecucao + "milissegundos utilizando 2 threads");
            System.out.println("Foi salvo no arquivo o tempo de execução " + tempoDeExecucao + "milissegundos utilizando 2 thread");
        }catch (IOException g){
            System.out.println("Ocorreu um erro " + g.getMessage());
        }
    }
}
