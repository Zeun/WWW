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
    public int score;
    public int positiveVotes;
    public int totalVotes;
    
    public Review(String a, String b, String c, String d){
        summary = a;
        text = b;
        score = Integer.parseInt(c.substring(0, 1));
        positiveVotes = Integer.parseInt(d.split("/")[0]);
        totalVotes = Integer.parseInt(d.split("/")[1]);
    }
}
