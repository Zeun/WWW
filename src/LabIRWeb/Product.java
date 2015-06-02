/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabIRWeb;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author felipe
 */
public class Product {
    public String title;
    public String price;
    public String review_summ;
    public String review_text;
    public String review_score;
    public String review_positiveVotes;
    public String review_totalVotes;
    public int cantidadReviews;
                    
    public Product(String a, String b, String c, String d, String e, String f, int g){
        title = a;
        price = b;
        review_summ = c;
        review_text = d;
        review_score = e;
        review_positiveVotes = f.split("/")[0];
        review_totalVotes = f.split("/")[1];
        cantidadReviews = g;

    }
   
    
}
