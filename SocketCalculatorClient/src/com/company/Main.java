package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Main {
    public static void main(String[] args) {
        try {
            // json || xml
            String type = "xml";
            //Conexão com o Servidor
            Socket clientSocket = new Socket("192.168.15.10", 9090);
            DataOutputStream socketSaidaServer = new DataOutputStream(clientSocket.getOutputStream());

            // (50-30)/((3+2)*4)
            BinaryExpressionTree tree = new BinaryExpressionTree('/',
                    new BinaryExpressionTree('-',
                            new BinaryExpressionTree(50),
                            new BinaryExpressionTree(30)),
                    new BinaryExpressionTree('*',
                            new BinaryExpressionTree('+',
                                    new BinaryExpressionTree(3),
                                    new BinaryExpressionTree(2)),
                            new BinaryExpressionTree(4)));

            String payload = "";
            if (type.equals("json")) {
                // Transformando objeto em um json
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                payload = gson.toJson(tree);
                System.out.println(payload);
            } else {
                try {
                    // Classe do objeto a ser transformado em um xml
                    JAXBContext contextObj = JAXBContext.newInstance(BinaryExpressionTree.class);
                    // Marshaller para transformação
                    Marshaller marshallerObj = contextObj.createMarshaller();
                    marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    // Transformando em um xml
                    StringWriter sw = new StringWriter();
                    marshallerObj.marshal(tree, sw);
                    payload = sw.toString();
                    System.out.println(payload);
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }

            // Enviando os dados
            socketSaidaServer.writeUTF(payload);
            socketSaidaServer.flush();

            // Recebendo a resposta
            BufferedReader messageFromServer = new BufferedReader
                    (new InputStreamReader(clientSocket.getInputStream()));
            String result = messageFromServer.readLine();

            System.out.println("resultado = " + result);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}