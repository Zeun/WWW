/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aux;

import cc.mallet.classify.Classifier;
import cc.mallet.pipe.iterator.CsvIterator;
import cc.mallet.types.Labeling;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 *
 * @author felipe
 */
public class AuxClassifier {
    public Classifier loadClassifier()
        throws FileNotFoundException, IOException, ClassNotFoundException {

        // The standard way to save classifiers and Mallet data                                            
        //  for repeated use is through Java serialization.                                                
        // Here we load a serialized classifier from a file.                                               

        Classifier classifier;

        ObjectInputStream ois =
            new ObjectInputStream (new FileInputStream (new File("nuestro.clasificador")));
        classifier = (Classifier) ois.readObject();
        ois.close();

        return classifier;
    }
        
    public String printLabelings(Classifier classifier, String texto) throws IOException {

        // Create a new iterator that will read raw instance data from                                     
        //  the lines of a file.                                                                           
        // Lines should be formatted as:                                                                   
        //                                                                                                 
        //   [name] [label] [data ... ]                                                                    
        //                                                                                                 
        //  in this case, "label" is ignored.                                                              

        CsvIterator reader =
            new CsvIterator(new StringReader("asd asd " + texto),
                            "(\\w+)\\s+(\\w+)\\s+(.*)",
                            3, 2, 1);  // (data, label, name) field indices               

        // Create an iterator that will pass each instance through                                         
        //  the same pipe that was used to create the training data                                        
        //  for the classifier.                                                                            
        Iterator instances =
            classifier.getInstancePipe().newIteratorFrom(reader);

        // Classifier.classify() returns a Classification object                                           
        //  that includes the instance, the classifier, and the                                            
        //  classification results (the labeling). Here we only                                            
        //  care about the Labeling.                                                                       
        while (instances.hasNext()) {
            Labeling labeling = classifier.classify(instances.next()).getLabeling();
            String result = "";
            HashMap<String,Double> m = new HashMap();
            // print the labels with their weights in descending order (ie best first)                     
            for (int rank = 0; rank < 3; rank++){
                m.put(labeling.getLabelAtRank(rank).toString(), labeling.getValueAtRank(rank));
            }
            result = m.get("positivo") + ";" + m.get("neutro") + ";" + m.get("negativo");
            return result;
        }
    return "no";
    }
    

    public double getScore(String review_summ, String review_score, String review_positiveVotes, String review_totalVotes, String emotion) {
        String[] summaries = review_summ.split(Pattern.quote("*^*"));
        String[] scores = review_score.split(Pattern.quote("*^*"));
        String[] positiveVotes = review_positiveVotes.split(Pattern.quote("*^*"));
        String[] totalVotes = review_totalVotes.split(Pattern.quote("*^*"));
        String[] emotions = emotion.split(Pattern.quote("*^*"));
        
        int m = summaries.length;
        int totalTotalVotes = 0;
        for(String s : totalVotes){
            totalTotalVotes += Integer.parseInt(s);
            totalTotalVotes++;
        }
        
        double ranking = 0;
        Double P,n,N;
        for(int i = 0; i<m; i++){
            String[] emotions_separado = emotions[i].split(Pattern.quote(";"));
            if(emotions_separado[0].equals("null")){
                P = 0.0;
            }else{
                P = new Double(emotions_separado[0]);
            }
            if(emotions_separado[1].equals("null")){
                n = 0.0;
            }else{
                n = new Double(emotions_separado[1]);
            }
            if(emotions_separado[2].equals("null")){
                N = 0.0;
            }else{
                N = new Double(emotions_separado[2]);
            }
            int viPlus = Integer.parseInt(positiveVotes[i]) + 1;
            double ponderedStars = ponderStars(P, n, N, viPlus);
            ranking += ((P + 1 - N + n/2)/2)*(viPlus/totalTotalVotes)+ponderedStars;
        }
        ranking /= 2*m;
        
        return ranking;
    
    }
    
    public double ponderStars(double pos, double neu, double neg, int stars){
        double result = 0;
        double star = stars;
        if(pos >= neu && pos >= neg){
            result = star/5;
        }
        else if(neg >= pos && neg >= neu){
            result = 1 - ((star - 1)/5);
        } else{
            if(stars == 3) result = 1;
            if(stars == 2 || stars == 4) result = 0.6;
            if(stars == 1 || stars == 5) result = 0.2;
        }
        return result;
    }
}
