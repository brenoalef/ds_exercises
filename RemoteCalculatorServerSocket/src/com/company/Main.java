package com.company;

import lipermi.exception.LipeRMIException;
import lipermi.handler.CallHandler;
import lipermi.net.IServerListener;
import lipermi.net.Server;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        System.out.println("Inicializando...");

        // CallHandler é responsável por manter o controle de todos os objetos locais exportados e intâncias remotas
        CallHandler callHandler = new CallHandler();

        // Interface e implementação da calculadora
        CalculatorInterface calculatorInterface;
        calculatorInterface = new CalculatorImpl();
        try {
            // Exportando o objeto da calculadora
            callHandler.registerGlobal(CalculatorInterface.class,
                    calculatorInterface);
            System.out.println("Calculadora registrada.");
        } catch (LipeRMIException e) {
            e.printStackTrace();
        }

        // Inicializando o server
        Server server = new Server();
        try {
            server.addServerListener(new IServerListener() {
                @Override
                public void clientConnected(Socket socket) {
                    System.out.println("Cliente conectado: " + socket.getInetAddress());
                }

                @Override
                public void clientDisconnected(Socket socket) {
                    System.out.println("Cliente desconectado: " + socket.getInetAddress());
                }
            });
            // Relacionando o objeto exportado a porta 9090 para acesso remoto
            server.bind(9090, callHandler);
            System.out.println("Calculadora pronta para aceitar chamadas remotas.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
