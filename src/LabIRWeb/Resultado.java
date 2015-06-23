/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabIRWeb;

import org.apache.lucene.document.Document;

/**
 *
 * @author felipe
 */
public class Resultado implements Comparable<Resultado>{
    Document d;
    double puntaje;

    Resultado(Document doc) {
        this.d = doc;
    }

    @Override
    public int compareTo(Resultado o) {
        double estePuntaje = new Double(d.get("ranking"));
        double elOtroPuntaje = new Double(o.d.get("ranking"));
        if (estePuntaje < elOtroPuntaje)
            return 1;
        else if (elOtroPuntaje == estePuntaje)
            return 0;
        else 
            return -1;
    }
    
    
}
