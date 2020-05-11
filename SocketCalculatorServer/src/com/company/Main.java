package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Main {
    public static void main(String[] args) {
        ServerSocket welcomeSocket;
        DataOutputStream socketOutput;
        DataInputStream socketInput;

        try {
            // json || xml
            String type = "xml";
            welcomeSocket = new ServerSocket(9090);
            int i = 0;

            System.out.println("Servidor no ar");
            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                i++;
                System.out.println("Nova conexão: " + i);

                // Recebendo dados
                socketInput = new DataInputStream(connectionSocket.getInputStream());
                String payload = socketInput.readUTF();

                // Montando árvore
                BinaryExpressionTree tree = null;
                if (type.equals("json")) {
                    // Transformando payload json em um objeto
                    tree = new Gson().fromJson(payload, BinaryExpressionTree.class);
                    System.out.println(i + ": " + tree);
                } else {
                    JAXBContext jaxbContext;
                    try {
                        // Classe do objeto a ser lido
                        jaxbContext = JAXBContext.newInstance(BinaryExpressionTree.class);
                        // Unmarshaller do xml
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                        // Leitura do xml
                        StringReader sr = new StringReader(payload);
                        tree = (BinaryExpressionTree) jaxbUnmarshaller.unmarshal(sr);
                        System.out.println(i + ": " + tree);
                    } catch (JAXBException e) {
                        e.printStackTrace();
                    }
                }

                String result = "Erro!";
                if (tree != null) {
                    // Resolvendo árvore
                    result = String.valueOf(tree.evaluate());
                }

                System.out.println(i + ": " + result);

                // Enviando resposta para o cliente
                socketOutput = new DataOutputStream(connectionSocket.getOutputStream());
                socketOutput.writeBytes(result + '\n');
                socketOutput.flush();
                socketOutput.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}