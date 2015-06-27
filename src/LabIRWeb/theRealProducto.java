/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabIRWeb;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author felipe
 */
public class theRealProducto {
    public String title;
    public String price;
    public NutriInfo info;
    public List<Review> reviews;

    public theRealProducto(String t, String p, NutriInfo n, String r_summ, String r_text, String r_score, String r_positiveVotes, String r_totalVotes, String r_emotion){
        this.title = t;
        this.price = p;
        this.info = n;
        reviews = new ArrayList<Review>();
        String[] summaries = r_summ.split(Pattern.quote("*^*"));
        String[] texts = r_text.split(Pattern.quote("*^*"));
        String[] scores = r_score.split(Pattern.quote("*^*"));
        String[] positiveVotes = r_positiveVotes.split(Pattern.quote("*^*"));
        String[] totalVotes = r_totalVotes.split(Pattern.quote("*^*"));
        String[] emotions = r_emotion.split(Pattern.quote("*^*"));
        int cantidadDeReviews = summaries.length;
        for(int i = 0; i < cantidadDeReviews; i++){
            reviews.add(new Review(summaries[i], texts[i], scores[i], positiveVotes[i], totalVotes[i], emotions[i]));
        }
    }
}
