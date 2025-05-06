import analisadores.AnalisadorUm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TesteComOito {
    public static void main(String[] args){
        long tempoParaIniciar = System.currentTimeMillis();
        File pastaParaAnalise = new File("src/cpfs");
        Runnable analisadorUm = new AnalisadorUm(pastaParaAnalise,8 );

        Thread t1 = new Thread(analisadorUm);
        Thread t2 = new Thread(analisadorUm);
        Thread t3 = new Thread(analisadorUm);
        Thread t4 = new Thread(analisadorUm);
        Thread t5 = new Thread(analisadorUm);
        Thread t6 = new Thread(analisadorUm);
        Thread t7 = new Thread(analisadorUm);
        Thread t8 = new Thread(analisadorUm);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            t6.join();
            t7.join();
            t8.join();

        } catch (InterruptedException g) {
            g.printStackTrace();
        }
        long tempoParaFinalizar = System.currentTimeMillis();
        long tempoDeExecucao = tempoParaFinalizar - tempoParaIniciar;
        try(FileWriter fW = new FileWriter("tempo_que_levou_para_fim_da_exec_utlizando8Threads.txt")){
            fW.write("Tempo total de execução = " + tempoDeExecucao + "milissegundos utilizando 8 threads");
            System.out.println("Foi salvo no arquivo o tempo de execução " + tempoDeExecucao + "milissegundos utilizando 8 thread");
        }catch (IOException g){
            System.out.println("Ocorreu um erro " + g.getMessage());
        }
    }
}


