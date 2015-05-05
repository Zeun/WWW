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
    public List<Review> reviews;
    
    public Product(String a, String b, Review c){
        title = a;
        price = b;
        List l = new ArrayList<Review>();
        l.add(c);
        reviews = l;

    }
    
    public void addReview(Review a){
        List l = reviews;
        l.add(a);
        reviews = l;
    }
}
