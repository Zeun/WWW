/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabIRWeb;

import java.util.regex.Pattern;

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
    public Double emotionPositive;
    public Double emotionNeutral;
    public Double emotionNegative;
    public String emotion;

    public Review(String a, String b, String c, String d) {
        summary = a;
        text = b;
        score = c.substring(0, 1);
        positiveVotes = d.split("/")[0];
        totalVotes = d.split("/")[1];
    }

    public Review(String a, String b, String c, String d, String e, String f) {
        summary = a;
        text = b;
        score = c;
        positiveVotes = d;
        totalVotes = e;
        String[] emo = f.split(Pattern.quote(";"));
        if (emo[0].equals("null")) {
            emotionPositive = 0.0;
        } else {
            emotionPositive = new Double(emo[0]);
        }
        if (emo[1].equals("null")) {
            emotionNeutral = 0.0;
        } else {
            emotionNeutral = new Double(emo[1]);
        }
        if (emo[2].equals("null")) {
            emotionNegative = 0.0;
        } else {
            emotionNegative = new Double(emo[2]);
        }
        if (emotionPositive >= emotionNeutral && emotionPositive >= emotionNegative) {
            emotion = "positive";
        } else if (emotionNegative >= emotionPositive && emotionNegative >= emotionNeutral) {
            emotion = "negative";
        } else {
            emotion = "neutral";
        }
    }
}
