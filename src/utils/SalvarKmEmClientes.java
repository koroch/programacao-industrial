/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import programacao.model.Cliente;
import programacao.model.Estado;
import view.GerenciamentoHorasEKms;

/**
 *
 * @author SGA
 */
public class SalvarKmEmClientes {
    
//    public static void main(String[] args) {
//        List<String> linhasArquivoCliente = new ArrayList<>();
//        List<Cliente> clientes = new ArrayList<>();
//    
//        try (BufferedReader br = new BufferedReader(new FileReader("clientes.txt"))) {
//            String linha;
//            while ((linha = br.readLine()) != null) {
//                linhasArquivoCliente.add(linha);
//            }
//
//            linhasArquivoCliente.forEach(elemento -> {
//                String[] dados = elemento.split("\\|");
//                clientes.add(new Cliente(
//                        Integer.parseInt(dados[0].trim()),
//                        dados[1].trim(),
//                        dados[2].trim(),
//                        Estado.valueOf(dados[3].trim()),
//                        0
//                ));
//            });
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(null, "Erro no arquivo clientes.txt, talvez ele ainda esteja vazio!");
//        }
//        
//        for (Cliente cliente : clientes) {
//            RoutesGoogleMaps routesGM = new RoutesGoogleMaps(
//                    GerenciamentoHorasEKms.EMPRESA,
//                    cliente.getBuscaEndereco()
//                ); 
//            
//            System.out.println(cliente.getBuscaEndereco()+" -> "+routesGM.getKms());
//            cliente.setDistanciaEmKm((Math.round(routesGM.getKms() * 100.0) / 100.0));
//        }
//        atualizaCliente(clientes);
//    }
    
    private static void atualizaCliente(List<Cliente> clientes){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("clientes.txt"))) {
            for (Cliente cliente: clientes) {
                writer.write(cliente.toString());
                writer.newLine();
            }
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
        }
    }
}
