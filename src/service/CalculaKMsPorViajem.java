package service;


import utils.GeocodingGoogleMaps;
import utils.RoutesGoogleMaps;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Koroch
 */
public class CalculaKMsPorViajem {
    private double kmsRota;
    public CalculaKMsPorViajem(String origem, String destino) {
        GeocodingGoogleMaps sgmOrigem = new GeocodingGoogleMaps(origem);
        GeocodingGoogleMaps sgmDestino = new GeocodingGoogleMaps(destino);
        
        try {
            
            RoutesGoogleMaps rgm = new RoutesGoogleMaps(origem, destino);
            
            this.kmsRota = rgm.getKms();
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public double getKmsRota() {
        return kmsRota;
    }
    
}
