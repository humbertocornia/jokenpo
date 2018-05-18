package jokenpo;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {

  /**
   * Porta utilizada para a conexão via socket.
   */
  private static Integer port = 5000;

  public static void main(String args[]) throws Exception {
    String outputClient1 = "";
    String outputClient2 = "";
    String inputClient1;
    String inputClient2;

    //Exibe mensagem de boas-vindas
    System.out.println("--- Bem-Vindo ao Jokenpo (Servidor) ---");

    //Definir Porta
    Server.port = Server.getPort();

    //Criar novo servidor socket
    ServerSocket welcomeSocket = new ServerSocket(Server.port);

    System.out.println("Ok, estamos trabalhando na porta " + welcomeSocket.
        getLocalPort() + "...");

    while (!welcomeSocket.isClosed()) {
      //Jogador 1
      Socket client1 = welcomeSocket.accept();
      if (client1.isConnected()) {
        System.out.println("Jogador 1 ("
            + (client1.getLocalAddress().toString()).substring(1) + ":"
            + client1.getLocalPort() + ") entrou... Aguarde pelo Jogador 2...");
      }
      DataOutputStream outToClient1 = new DataOutputStream(
          client1.getOutputStream());
      BufferedReader inFromClient1 = new BufferedReader(
          new InputStreamReader(client1.getInputStream()));

      //Jogador 2
      Socket client2 = welcomeSocket.accept();
      if (client2.isConnected()) {
        System.out.println("Jogador 2 ("
            + (client2.getLocalAddress().toString()).substring(1) + ":"
            + client1.getLocalPort() + ") entrou... Vamos começar!");
      }
      DataOutputStream outToClient2 = new DataOutputStream(
          client2.getOutputStream());
      BufferedReader inFromClient2 = new BufferedReader(
          new InputStreamReader(client2.getInputStream()));

      //Recebendo entrada dos Clientes
      inputClient1 = inFromClient1.readLine();
      inputClient2 = inFromClient2.readLine();

      //Empate, mesma opção
      if (inputClient1.equals(inputClient2)) {
        outputClient1 = "Empate";
        outputClient2 = "Empate";
        System.out.println("Empatou.");
      }
      //C1 = R e C2 = T
      //C1 vence e C2 perde
      else if (inputClient1.equals("R") && inputClient2.equals("T")) {
        outputClient1 = "Parabéns, você venceu! :) ";
        outputClient2 = "Que pena, você perdeu! :( ";
        System.out.println("Jogador 1 venceu - Rocha vence Tesoura");
      }
      //C1 = T e C2 = R
      //C1 perde e C2 vence
      else if (inputClient1.equals("T") && inputClient2.equals("R")) {
        outputClient1 = "Que pena, você perdeu! :( ";
        outputClient2 = "Parabéns, você venceu! :) ";
        System.out.println("Jogador 2 venceu - Rocha vence Tesoura");
      }
      //C1 = R e C2 = P
      //C1 perde e C2 vence
      else if (inputClient1.equals("R") && inputClient2.equals("P")) {
        outputClient1 = "Que pena, você perdeu! :( ";
        outputClient2 = "Parabéns, você venceu! :) ";
        System.out.println("Jogador 2 venceu - Papel vence Rocha");
      }
      //C1 = P e C2 = R
      //C1 vence e C2 perde
      else if (inputClient1.equals("P") && inputClient2.equals("R")) {
        outputClient1 = "Parabéns, você venceu! :) ";
        outputClient2 = "Que pena, você perdeu! :( ";
        System.out.println("Jogador 1 venceu - Papel vence Rocha");
      }
      //C1 = T e C2 = P
      //C1 vence e C2 perde
      else if (inputClient1.equals("T") && inputClient2.equals("P")) {
        outputClient1 = "Parabéns, você venceu! :) ";
        outputClient2 = "Que pena, você perdeu! :( ";
        System.out.println("Jogador 1 venceu - Tesoura vence Papel");
      }
      //C1 = P e C2 = T
      //C1 perde e C2 vence
      else if (inputClient1.equals("P") && inputClient2.equals("T")) {
        outputClient1 = "Que pena, você perdeu! :( ";
        outputClient2 = "Parabéns, você venceu! :) ";
        System.out.println("Jogador 2 venceu - Tesoura vence Papel");
      }

      // Envia resposta e fecha o socket
      outToClient1.writeBytes(outputClient1.toUpperCase());
      outToClient2.writeBytes(outputClient2.toUpperCase());
      client1.close();
      client2.close();

      System.out.println("Esperando um novo jogo...");
    }
  }

  /**
   * Verifica se a porta inserida pelo usuário é válida (maior ou igual a 1 E
   * menor ou igual a 65535).
   *
   * @param port Porta inserida pelo Client.
   *
   * @return True se a porta.
   */
  public static boolean portaValida(Integer port) {
    return port >= 1 && port <= 65535;
  }

  /**
   * Pega a porta que a ser iniciado o socket, sendo a padrão (5000) caso o
   * usuário digite '0', ou a porta desejada.
   *
   * @return A porta para iniciar o servidor socket.
   */
  private static int getPort() {
    Integer input;

    try (Scanner sc = new Scanner(System.in)) {
      do {
        System.out.println("Por favor digite o número da porta, que esteja "
            + "entre 1 e 65535 ou \ndigite \"0\" para continuar com a porta "
            + "padrão(" + Server.port + "): ");

        input = sc.nextInt();
      }
      while (input != 0 && !Server.portaValida(input));
    }

    return input == 0 ? Server.port : input;
  }
}
