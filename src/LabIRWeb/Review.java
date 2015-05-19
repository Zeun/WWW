/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabIRWeb;

/**
 *
 * @author felipe
 */
public class Review {
    public String summary;
    public String text;
    public String score;
    public String positiveVotes;
    public String totalVotes;
    
    public Review(String a, String b, String c, String d){
        summary = a;
        text = b;
        score = c.substring(0, 1);
        positiveVotes = d.split("/")[0];
        totalVotes = d.split("/")[1];
    }
}
