/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabIRWeb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author felipe
 */
public class MapReviews {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File("Gourmet_Foods.txt");
        Scanner sc = new Scanner(file);
        HashMap<String, Product> map = new HashMap<>();

        while (sc.hasNextLine()) {
            String a = sc.nextLine();
            a = a.substring(a.indexOf(": ") + 2);
            String pid = a;
            a = sc.nextLine();
            a = a.substring(a.indexOf(": ") + 2);
            String title = a;
            a = sc.nextLine();
            a = a.substring(a.indexOf(": ") + 2);
            String price = a;
            a = sc.nextLine();
            a = a.substring(a.indexOf(": ") + 2);
            String uid = a;
            a = sc.nextLine();
            a = a.substring(a.indexOf(": ") + 2);
            String pname = a;
            a = sc.nextLine();
            a = a.substring(a.indexOf(": ") + 2);
            String hness = a;
            a = sc.nextLine();
            a = a.substring(a.indexOf(": ") + 2);
            String score = a;
            a = sc.nextLine();
            a = a.substring(a.indexOf(": ") + 2);
            String time = a;
            a = sc.nextLine();
            a = a.substring(a.indexOf(": ") + 2);
            String summ = a;
            a = sc.nextLine();
            a = a.substring(a.indexOf(": ") + 2);
            String text = a;
            a = sc.nextLine();

            if (map.containsKey(pid)) {
                Review newReview = new Review(summ, text, score, hness);
                map.get(pid).addReview(newReview);

            } else { //no existe el producto en el diccionario
                Review firstReview = new Review(summ, text, score, hness);
                Product p = new Product(title, price, firstReview);
                map.put(pid, p);

            }
            //System.out.println(p++);
            //System.out.println(pid + titulo + uid + pname + hness + score + summ + text);

        }
        sc.close();
        System.out.println("Terminé de leer reviews!");
        System.out.println("Tenemos " + map.size() + " productos registrados.");

        //en map ya tenemos todos los productos atados a sus reviews 1:n
        //ahora generamos map2 que contiene el id del producto => su info nutricional
        BufferedReader bf = new BufferedReader(new FileReader("sr27.csv"));
        HashMap<String, NutriInfo> map2 = new HashMap<>();
        for (CSVRecord record : CSVFormat.DEFAULT.withHeader().parse(bf)) {
            NutriInfo table = new NutriInfo();
            table.NDB_No = record.get("NDB_No");
            table.Shrt_Desc = record.get("Shrt_Desc");
            table.Water_g = record.get("Water_(g)");
            table.Energ_Kcal = record.get("Energ_Kcal");
            table.Protein_g = record.get("Protein_(g)");
            table.Lipid_Tot_g = record.get("Lipid_Tot_(g)");
            table.Ash_g = record.get("Ash_(g)");
            table.Carbohydrt_g = record.get("Carbohydrt_(g)");
            table.Fiber_TD_g = record.get("Fiber_TD_(g)");
            table.Sugar_Tot_g = record.get("Sugar_Tot_(g)");
            table.Calcium_mg = record.get("Calcium_(mg)");
            table.Iron_mg = record.get("Iron_(mg)");
            table.Magnesium_mg = record.get("Magnesium_(mg)");
            table.Phosphorus_mg = record.get("Phosphorus_(mg)");
            table.Potassium_mg = record.get("Potassium_(mg)");
            table.Sodium_mg = record.get("Sodium_(mg)");
            table.Zinc_mg = record.get("Zinc_(mg)");
            table.Copper_mg = record.get("Copper_mg)");
            table.Manganese_mg = record.get("Manganese_(mg)");
            table.Selenium_µg = record.get("Selenium_(µg)");
            table.Vit_C_mg = record.get("Vit_C_(mg)");
            table.Thiamin_mg = record.get("Thiamin_(mg)");
            table.Riboflavin_mg = record.get("Riboflavin_(mg)");
            table.Niacin_mg = record.get("Niacin_(mg)");
            table.Panto_Acid_mg = record.get("Panto_Acid_mg)");
            table.Vit_B6_mg = record.get("Vit_B6_(mg)");
            table.Folate_Tot_µg = record.get("Folate_Tot_(µg)");
            table.Folic_Acid_µg = record.get("Folic_Acid_(µg)");
            table.Food_Folate_µg = record.get("Food_Folate_(µg)");
            table.Folate_DFE_µg = record.get("Folate_DFE_(µg)");
            table.Choline_Tot_mg = record.get("Choline_Tot_ (mg)");
            table.Vit_B12_µg = record.get("Vit_B12_(µg)");
            table.Vit_A_IU = record.get("Vit_A_IU");
            table.Vit_A_RAE = record.get("Vit_A_RAE");
            table.Retinol_µg = record.get("Retinol_(µg)");
            table.Alpha_Carot_µg = record.get("Alpha_Carot_(µg)");
            table.Beta_Carot_µg = record.get("Beta_Carot_(µg)");
            table.Beta_Crypt_µg = record.get("Beta_Crypt_(µg)");
            table.Lycopene_µg = record.get("Lycopene_(µg)");
            table.LutZea_µg = record.get("Lut+Zea_ (µg)");
            table.Vit_E_mg = record.get("Vit_E_(mg)");
            table.Vit_D_µg = record.get("Vit_D_µg");
            table.Vit_D_IU = record.get("Vit_D_IU");
            table.Vit_K_µg = record.get("Vit_K_(µg)");
            table.FA_Sat_g = record.get("FA_Sat_(g)");
            table.FA_Mono_g = record.get("FA_Mono_(g)");
            table.FA_Poly_g = record.get("FA_Poly_(g)");
            table.Cholestrl_mg = record.get("Cholestrl_(mg)");
            table.GmWt_1 = record.get("GmWt_1");
            table.GmWt_Desc1 = record.get("GmWt_Desc1");
            table.GmWt_2 = record.get("GmWt_2");
            table.GmWt_Desc2 = record.get("GmWt_Desc2");
            table.Refuse_Pct = record.get("Refuse_Pct");
            map2.put(record.get("NDB_No"), table);
        }
        bf.close();
        System.out.println("Terminé de leer información nutricional!");
        System.out.println("Tenemos " + map2.size() + " tablas nutricionales registradas.");

        //ahora buscamos productos similares y al detectar la similaridad,
        //los agregamos al índice
        // map field-name to analyzer
        Map<String, Analyzer> analyzerPerField = new HashMap<>();
        analyzerPerField.put("amazonProductId", new KeywordAnalyzer());
        analyzerPerField.put("price", new KeywordAnalyzer());
        analyzerPerField.put("NDB_No", new KeywordAnalyzer());
        analyzerPerField.put("Shrt_Desc", new SimpleAnalyzer());
        analyzerPerField.put("Water_g", new KeywordAnalyzer());
        analyzerPerField.put("Energ_Kcal", new KeywordAnalyzer());
        analyzerPerField.put("Protein_g", new KeywordAnalyzer());
        analyzerPerField.put("Lipid_Tot_g", new KeywordAnalyzer());
        analyzerPerField.put("Ash_g", new KeywordAnalyzer());
        analyzerPerField.put("Carbohydrt_g", new KeywordAnalyzer());
        analyzerPerField.put("Fiber_TD_g", new KeywordAnalyzer());
        analyzerPerField.put("Sugar_Tot_g", new KeywordAnalyzer());
        analyzerPerField.put("Calcium_mg", new KeywordAnalyzer());
        analyzerPerField.put("Iron_mg", new KeywordAnalyzer());
        analyzerPerField.put("Magnesium_mg", new KeywordAnalyzer());
        analyzerPerField.put("Phosphorus_mg", new KeywordAnalyzer());
        analyzerPerField.put("Potassium_mg", new KeywordAnalyzer());
        analyzerPerField.put("Sodium_mg", new KeywordAnalyzer());
        analyzerPerField.put("Zinc_mg", new KeywordAnalyzer());
        analyzerPerField.put("Copper_mg", new KeywordAnalyzer());
        analyzerPerField.put("Manganese_mg", new KeywordAnalyzer());
        analyzerPerField.put("Selenium_µg", new KeywordAnalyzer());
        analyzerPerField.put("Vit_C_mg", new KeywordAnalyzer());
        analyzerPerField.put("Thiamin_mg", new KeywordAnalyzer());
        analyzerPerField.put("Riboflavin_mg", new KeywordAnalyzer());
        analyzerPerField.put("Niacin_mg", new KeywordAnalyzer());
        analyzerPerField.put("Panto_Acid_mg", new KeywordAnalyzer());
        analyzerPerField.put("Vit_B6_mg", new KeywordAnalyzer());
        analyzerPerField.put("Folate_Tot_µg", new KeywordAnalyzer());
        analyzerPerField.put("Folic_Acid_µg", new KeywordAnalyzer());
        analyzerPerField.put("Food_Folate_µg", new KeywordAnalyzer());
        analyzerPerField.put("Folate_DFE_µg", new KeywordAnalyzer());
        analyzerPerField.put("Choline_Tot_mg", new KeywordAnalyzer());
        analyzerPerField.put("Vit_B12_µg", new KeywordAnalyzer());
        analyzerPerField.put("Vit_A_IU", new KeywordAnalyzer());
        analyzerPerField.put("Vit_A_RAE", new KeywordAnalyzer());
        analyzerPerField.put("Retinol_µg", new KeywordAnalyzer());
        analyzerPerField.put("Alpha_Carot_µg", new KeywordAnalyzer());
        analyzerPerField.put("Beta_Carot_µg", new KeywordAnalyzer());
        analyzerPerField.put("Beta_Crypt_µg", new KeywordAnalyzer());
        analyzerPerField.put("Lycopene_µg", new KeywordAnalyzer());
        analyzerPerField.put("LutZea_µg", new KeywordAnalyzer());
        analyzerPerField.put("Vit_E_mg", new KeywordAnalyzer());
        analyzerPerField.put("Vit_D_µg", new KeywordAnalyzer());
        analyzerPerField.put("Vit_D_IU", new KeywordAnalyzer());
        analyzerPerField.put("Vit_K_µg", new KeywordAnalyzer());
        analyzerPerField.put("FA_Sat_g", new KeywordAnalyzer());
        analyzerPerField.put("FA_Mono_g", new KeywordAnalyzer());
        analyzerPerField.put("FA_Poly_g", new KeywordAnalyzer());
        analyzerPerField.put("Cholestrl_mg", new KeywordAnalyzer());
        analyzerPerField.put("GmWt_1", new KeywordAnalyzer());
        analyzerPerField.put("GmWt_Desc1", new KeywordAnalyzer());
        analyzerPerField.put("GmWt_2", new KeywordAnalyzer());
        analyzerPerField.put("GmWt_Desc2", new KeywordAnalyzer());
        analyzerPerField.put("Refuse_Pct", new KeywordAnalyzer());

// create a per-field analyzer wrapper using the StandardAnalyzer as .. standard analyzer ;)
        PerFieldAnalyzerWrapper analyzer = new PerFieldAnalyzerWrapper(new StopAnalyzer(), analyzerPerField);

        //Analyzer analyzer = new StandardAnalyzer();
        Path indexPath = Paths.get("data/");
        Directory directory = FSDirectory.open(indexPath);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iwriter = new IndexWriter(directory, config);
        Document d;
        int prodAnalizados = 1;
        int prodAAnalizar = map.size();
        for (String idProductoComent : map.keySet()) {
            float percent = (prodAnalizados/prodAAnalizar);
            System.out.println(String.format("[%d] Analizando producto %d de %d (%.2f %%)", System.currentTimeMillis(), prodAnalizados++, prodAAnalizar, percent));
            String titulo = map.get(idProductoComent).title;
            double mayorSimilitud = 0;
            String tituloCandidato = "";
            String idCandidato = null;
            for (String idProductoInfo : map2.keySet()) {
                String tituloInfo = map2.get(idProductoInfo).Shrt_Desc;
                //esta función da un valor entre 0 y 1 respecto a la similaridad
                //de los nombres
                double similarity = StringSimilarity.similarity(titulo, tituloInfo);
                if (similarity > mayorSimilitud) {
                    mayorSimilitud = similarity;
                    tituloCandidato = tituloInfo;
                    idCandidato = idProductoInfo;
                }
            }
            
                //creamos el documento con los datos de amazon
                d = new Document();
                //TODO revisar el 
                d.add(new StringField("amazonProductId", idProductoComent, Field.Store.YES));
                d.add(new TextField("amazonTitle", map.get(idProductoComent).title, Field.Store.YES));
                d.add(new StringField("price", map.get(idProductoComent).price, Field.Store.YES));

                //agregamos las reviews
                int cantidadDeReviews = 0;
                for (Review r : map.get(idProductoComent).reviews) {
                    d.add(new TextField("review_" + String.valueOf(cantidadDeReviews) + "_summary", r.summary, Field.Store.YES));
                    d.add(new TextField("review_" + String.valueOf(cantidadDeReviews) + "_title", r.text, Field.Store.YES));
                    d.add(new IntField("review_" + String.valueOf(cantidadDeReviews) + "_score", r.score, Field.Store.YES));
                    d.add(new IntField("review_" + String.valueOf(cantidadDeReviews) + "_positiveVotes", r.positiveVotes, Field.Store.YES));
                    d.add(new IntField("review_" + String.valueOf(cantidadDeReviews) + "_totalVotes", r.totalVotes, Field.Store.YES));
                    //considerar agregar el cociente entre positiveVotes y totalVotes para ordenar por helpfulness si es necesario
                    cantidadDeReviews = cantidadDeReviews + 1;
                }
                d.add(new IntField("qtyOfReviews", cantidadDeReviews, Field.Store.YES));

            //acá se define el umbral de similitud entre el nombre del producto de cada base de datos
            if (mayorSimilitud >= 0.65) {
                System.out.println(String.format("Encontré similitud entre: %s y %s, con un valor de %f", titulo, tituloCandidato, mayorSimilitud));

                //agregación de datos de la tabla de nutrientes
                if (idCandidato != null) {//si encontramos un candidato
                    NutriInfo n = map2.get(idCandidato);
                    d.add(new IntField("NDB_No", Integer.parseInt(n.NDB_No), Field.Store.YES));
                    d.add(new TextField("Shrt_Desc", n.Shrt_Desc, Field.Store.YES));
                    if (!"".equals(n.Water_g)) {
                        d.add(new FloatField("Water", Float.parseFloat(n.Water_g), Field.Store.YES));
                    }
                    if (!"".equals(n.Energ_Kcal)) {
                        d.add(new IntField("Energ_Kcal", Integer.parseInt(n.Energ_Kcal), Field.Store.YES));
                    }
                    if (!"".equals(n.Protein_g)) {
                        d.add(new FloatField("Protein", Float.parseFloat(n.Protein_g), Field.Store.YES));
                    }
                    if (!"".equals(n.Lipid_Tot_g)) {
                        d.add(new FloatField("Lipid_Tot", Float.parseFloat(n.Lipid_Tot_g), Field.Store.YES));
                    }
                    if (!"".equals(n.Ash_g)) {
                        d.add(new FloatField("Ash", Float.parseFloat(n.Ash_g), Field.Store.YES));
                    }
                    if (!"".equals(n.Carbohydrt_g)) {
                        d.add(new FloatField("Carbohydrt", Float.parseFloat(n.Carbohydrt_g), Field.Store.YES));
                    }
                    if (!"".equals(n.Fiber_TD_g)) {
                        d.add(new FloatField("Fiber_TD", Float.parseFloat(n.Fiber_TD_g), Field.Store.YES));
                    }
                    if (!"".equals(n.Sugar_Tot_g)) {
                        d.add(new FloatField("Sugar_Tot", Float.parseFloat(n.Sugar_Tot_g), Field.Store.YES));
                    }
                    if (!"".equals(n.Calcium_mg)) {
                        d.add(new IntField("Calcium", Integer.parseInt(n.Calcium_mg), Field.Store.YES));
                    }
                    if (!"".equals(n.Iron_mg)) {
                        d.add(new FloatField("Iron", Float.parseFloat(n.Iron_mg), Field.Store.YES));
                    }
                    if (!"".equals(n.Magnesium_mg)) {
                        d.add(new IntField("Magnesium", Integer.parseInt(n.Magnesium_mg), Field.Store.YES));
                    }
                    if (!"".equals(n.Phosphorus_mg)) {
                        d.add(new IntField("Phosphorus", Integer.parseInt(n.Phosphorus_mg), Field.Store.YES));
                    }
                    if (!"".equals(n.Potassium_mg)) {
                        d.add(new IntField("Potassium", Integer.parseInt(n.Potassium_mg), Field.Store.YES));
                    }
                    if (!"".equals(n.Sodium_mg)) {
                        try {
                            d.add(new IntField("Sodium", Integer.parseInt(n.Potassium_mg), Field.Store.YES));
                        } catch (Exception e) {
                            int i = 0;
                        }
                    }
                    if (!"".equals(n.Zinc_mg)) {
                        d.add(new FloatField("Zinc", Float.parseFloat(n.Zinc_mg), Field.Store.YES));
                    }
                    if (!"".equals(n.Copper_mg)) {
                        d.add(new FloatField("Copper", Float.parseFloat(n.Copper_mg), Field.Store.YES));
                    }
                    if (!"".equals(n.Manganese_mg)) {
                        d.add(new FloatField("Manganese", Float.parseFloat(n.Manganese_mg), Field.Store.YES));
                    }
                    if (!"".equals(n.Selenium_µg)) {
                        d.add(new FloatField("Selenium", Float.parseFloat(n.Selenium_µg), Field.Store.YES));
                    }
                    if (!"".equals(n.Vit_C_mg)) {
                        d.add(new FloatField("Vit_C", Float.parseFloat(n.Vit_C_mg), Field.Store.YES));
                    }
                    if (!"".equals(n.Thiamin_mg)) {
                        d.add(new FloatField("Thiamin", Float.parseFloat(n.Thiamin_mg), Field.Store.YES));
                    }
                    if (!"".equals(n.Riboflavin_mg)) {
                        d.add(new FloatField("Riboflavin", Float.parseFloat(n.Riboflavin_mg), Field.Store.YES));
                    }
                    if (!"".equals(n.Niacin_mg)) {
                        d.add(new FloatField("Niacin", Float.parseFloat(n.Niacin_mg), Field.Store.YES));
                    }
                    if (!"".equals(n.Panto_Acid_mg)) {
                        d.add(new FloatField("Panto_acid", Float.parseFloat(n.Panto_Acid_mg), Field.Store.YES));
                    }
                    if (!"".equals(n.Vit_B6_mg)) {
                        d.add(new FloatField("Vit_B6", Float.parseFloat(n.Vit_B6_mg), Field.Store.YES));
                    }
                    if (!"".equals(n.Folate_Tot_µg)) {
                        d.add(new IntField("Folate_Tot", Integer.parseInt(n.Folate_Tot_µg), Field.Store.YES));
                    }
                    if (!"".equals(n.Folic_Acid_µg)) {
                        d.add(new IntField("Folic_acid", Integer.parseInt(n.Folic_Acid_µg), Field.Store.YES));
                    }
                    if (!"".equals(n.Food_Folate_µg)) {
                        d.add(new IntField("Food_Folate", Integer.parseInt(n.Food_Folate_µg), Field.Store.YES));
                    }
                    if (!"".equals(n.Folate_DFE_µg)) {
                        d.add(new IntField("Folate_DFE", Integer.parseInt(n.Folate_DFE_µg), Field.Store.YES));
                    }
                    if (!"".equals(n.Choline_Tot_mg)) {
                        d.add(new FloatField("Choline_Tot", Float.parseFloat(n.Choline_Tot_mg), Field.Store.YES));
                    }
                    if (!"".equals(n.Vit_B12_µg)) {
                        d.add(new FloatField("Vit_B12", Float.parseFloat(n.Vit_B12_µg), Field.Store.YES));
                    }
                    if (!"".equals(n.Vit_A_IU)) {
                        d.add(new IntField("Vit_A_IU", Integer.parseInt(n.Vit_A_IU), Field.Store.YES));
                    }
                    if (!"".equals(n.Vit_A_RAE)) {
                        d.add(new IntField("Vit_A_RAE", Integer.parseInt(n.Vit_A_RAE), Field.Store.YES));
                    }
                    if (!"".equals(n.Retinol_µg)) {
                        d.add(new IntField("Retinol", Integer.parseInt(n.Retinol_µg), Field.Store.YES));
                    }
                    if (!"".equals(n.Alpha_Carot_µg)) {
                        d.add(new IntField("Alpha_Carot", Integer.parseInt(n.Alpha_Carot_µg), Field.Store.YES));
                    }
                    if (!"".equals(n.Beta_Carot_µg)) {
                        d.add(new IntField("Beta_Carot", Integer.parseInt(n.Beta_Carot_µg), Field.Store.YES));
                    }
                    if (!"".equals(n.Beta_Crypt_µg)) {
                        d.add(new IntField("Beta_Crypt", Integer.parseInt(n.Beta_Crypt_µg), Field.Store.YES));
                    }
                    if (!"".equals(n.Lycopene_µg)) {
                        d.add(new IntField("Lycopene", Integer.parseInt(n.Lycopene_µg), Field.Store.YES));
                    }
                    if (!"".equals(n.LutZea_µg)) {
                        d.add(new IntField("Lut+Zea", Integer.parseInt(n.LutZea_µg), Field.Store.YES));
                    }
                    if (!"".equals(n.Vit_E_mg)) {
                        d.add(new FloatField("Vit_E", Float.parseFloat(n.Vit_E_mg), Field.Store.YES));
                    }
                    if (!"".equals(n.Vit_D_µg)) {
                        d.add(new FloatField("Vit_D_mcg", Float.parseFloat(n.Vit_D_µg), Field.Store.YES));
                    }
                    if (!"".equals(n.Vit_D_IU)) {
                        d.add(new IntField("Vit_D_IU", Integer.parseInt(n.Vit_D_IU), Field.Store.YES));
                    }
                    if (!"".equals(n.Vit_K_µg)) {
                        d.add(new FloatField("Vit_K", Float.parseFloat(n.Vit_K_µg), Field.Store.YES));
                    }
                    if (!"".equals(n.FA_Sat_g)) {
                        d.add(new FloatField("FA_Sat", Float.parseFloat(n.FA_Sat_g), Field.Store.YES));
                    }
                    if (!"".equals(n.FA_Mono_g)) {
                        d.add(new FloatField("FA_Mono", Float.parseFloat(n.FA_Mono_g), Field.Store.YES));
                    }
                    if (!"".equals(n.FA_Poly_g)) {
                        d.add(new FloatField("FA_Poly", Float.parseFloat(n.FA_Poly_g), Field.Store.YES));
                    }
                    if (!"".equals(n.Cholestrl_mg)) {
                        d.add(new IntField("Cholestrl", Integer.parseInt(n.Cholestrl_mg), Field.Store.YES));
                    }
                    if (!"".equals(n.GmWt_1)) {
                        d.add(new IntField("GmWt_1", Integer.parseInt(n.GmWt_1), Field.Store.YES));
                    }
                    if (!"".equals(n.GmWt_Desc1)) {
                        d.add(new StringField("GmWt_Desc1", n.GmWt_Desc1, Field.Store.YES));
                    }
                    if (!"".equals(n.GmWt_2)) {
                        d.add(new FloatField("GmWt_2", Float.parseFloat(n.GmWt_2), Field.Store.YES));
                    }
                    if (!"".equals(n.GmWt_Desc2)) {
                        d.add(new StringField("GmWt_Desc2", n.GmWt_Desc2, Field.Store.YES));
                    }
                    if (!"".equals(n.Refuse_Pct)) {
                        d.add(new IntField("Refuse_Pct", Integer.parseInt(n.Refuse_Pct), Field.Store.YES));
                    }
                }

            }

                iwriter.addDocument(d);
                System.out.println("Documento agregado! número de documentos almacenados:" + iwriter.numDocs());

        }

        //finaliza el análisis de similitud, cerramos el índice
        iwriter.close();

    }

}