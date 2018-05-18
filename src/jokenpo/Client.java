package jokenpo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

  /**
   * IP do Servidor.
   */
  private static String host = "localhost";

  /**
   * Porta do Servidor.
   */
  private static Integer port = 5000;

  /**
   * Regras do jogo.
   */
  private static String regrasJogo = "\nRegras:\n - (R)ocha vence (T)esoura\n"
      + " - (T)esoura vence (P)apel\n - (P)apel vence (R)ocha\n";

  public static void main(String args[]) throws Exception {

    String input = "";
    String response;

    System.out.println("--- Bem-Vindo ao Jokenpo (Cliente) ---");

    BufferedReader inFromUser = new BufferedReader(
        new InputStreamReader(System.in));
    Socket clientSocket = new Socket(Client.host, Client.port);
    DataOutputStream outToServer = new DataOutputStream(
        clientSocket.getOutputStream());
    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
        clientSocket.getInputStream()));

    do {
      if (input.equals("-regras")) {
        System.out.println(Client.regrasJogo);
      }

      // Mensagem ao usuário para ele selecionar pedra, papel ou tesoura
      System.out.println("Começe o jogo selecinando (R)ocha (P)apel, (T)esoura "
          + "ou escreva \"-regras\" para ler as regas: ");
      input = inFromUser.readLine();
    }
    while (!input.equals("R") && !input.equals("P") && !input.equals("T"));

    // Transmite a mensagem ao servidor e informa ao usuário que a resposta dele
    // foi enviada
    outToServer.writeBytes(input + "\n");
    System.out.println("Sua resposta (" + input + ") foi transmitida para o "
        + "servidor. Aguarde pelo resultado...");

    // Pega a resposta do Servidor
    response = inFromServer.readLine();

    // Mostra a mensagem ao Cliente
    System.out.println("Resposta do Servidor: " + response);

    // Fecha a conexão socket
    clientSocket.close();
  }
}
