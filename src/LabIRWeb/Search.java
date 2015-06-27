/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabIRWeb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author daniel
 */
public class Search {

    String dirIndexES = "data/";
    String dirIndexNONES = "data/";

    ArrayList<List> listaInfo = new ArrayList();

    public Search(String directorioIndexES, String directorioIndexNONES) {
        this.dirIndexES = directorioIndexES;
        this.dirIndexNONES = directorioIndexNONES;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        Scanner s = new Scanner(System.in);
        System.out.println("Ingrese términos de búsqueda:");
        String busqueda = s.nextLine();
        Search search = new Search();
        search.buscarContenido(busqueda);
    }

    private Search() {
    }

    public void buscarContenido(String busqueda) throws IOException, ParseException {
        StandardAnalyzer analyzer = new StandardAnalyzer();

        //File indexDirES = new File(dirIndexES);
        Path indexDirES = Paths.get(dirIndexES);
        Directory indexES = FSDirectory.open(indexDirES);

        Path indexDirNONES = Paths.get(dirIndexNONES);
        Directory indexNONES = FSDirectory.open(indexDirNONES);

        // 2. Query
        String querystr = busqueda;

//        Query q = new QueryParser("amazonTitle", analyzer).parse(querystr);
        //Query qNONES = new QueryParser(Version.LUCENE_43, "contenido", analyzer).parse(querystr);
        String[] fields = {"amazonTitle", "text", "summary"};
        //QueryParser parser = new QueryParser(field, analyzerWrapper);
        Query q = new MultiFieldQueryParser(fields, analyzer).parse(querystr);

        // 3. Search
        int hitsPerPage = 20;
        IndexReader reader = DirectoryReader.open(indexES);
        IndexSearcher searcher = new IndexSearcher(reader);

        IndexReader readerNONES = DirectoryReader.open(indexNONES);
        IndexSearcher searcherNONES = new IndexSearcher(readerNONES);

        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
        TopScoreDocCollector collectorNONES = TopScoreDocCollector.create(hitsPerPage);

        searcher.search(q, collector);
        searcherNONES.search(q, collectorNONES);

        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        // ScoreDoc[] hitsNONES = collectorNONES.topDocs().scoreDocs;
        // 4. Display results
        System.out.println("................................................................");
        System.out.println("Iniciando Búsqueda: ");
        System.out.println("................................................................");

        ArrayList<Resultado> resultados = new ArrayList<>();
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            resultados.add(new Resultado(d));
//            System.out.println(d.get("amazonTitle"));
//            System.out.println(hits[i].score);
//            System.out.println("**");
//            System.out.println(d.get("summary"));
//            System.out.println("**");
//            System.out.println(d.get("text"));            
//            System.out.println("--------------------------------------------------------------");
        }

        Collections.sort(resultados);

        System.out.println("Mostrando resultados");
        for (Resultado r : resultados) {
            System.out.println(r.d.get("amazonTitle"));
            System.out.println("**");
            System.out.println(r.d.get("summary"));
            System.out.println("**");
            System.out.println(String.format("Ranking %s ", r.d.get("ranking")));
            //System.out.println(r.d.get("text"));            
            System.out.println("--------------------------------------------------------------");
        }

        /*System.out.println("No ES Found " + hitsNONES.length + " hits.");
         for(int i=0;i<hitsNONES.length;++i) {
         int docId = hitsNONES[i].doc;
         Document d = searcherNONES.doc(docId);
         System.out.println((i + 1) + ". " + d.get("es") + "\t" + d.get("contenido"));
         }*/
        reader.close();
        readerNONES.close();

    }

    public List<theRealProducto> buscarParaJavaEE() throws IOException, ParseException {
        String busqueda = "";
        List<theRealProducto> result;
        StandardAnalyzer analyzer = new StandardAnalyzer();

        //File indexDirES = new File(dirIndexES);
        Path indexDirES = Paths.get(dirIndexES);
        Directory indexES = FSDirectory.open(indexDirES);

        Path indexDirNONES = Paths.get(dirIndexNONES);
        Directory indexNONES = FSDirectory.open(indexDirNONES);

        // 2. Query
        String querystr = busqueda;

//        Query q = new QueryParser("amazonTitle", analyzer).parse(querystr);
        //Query qNONES = new QueryParser(Version.LUCENE_43, "contenido", analyzer).parse(querystr);
        String[] fields = {"amazonTitle", "text", "summary"};
        //QueryParser parser = new QueryParser(field, analyzerWrapper);
        Query q = new MultiFieldQueryParser(fields, analyzer).parse(querystr);

        // 3. Search
        int hitsPerPage = 20;
        IndexReader reader = DirectoryReader.open(indexES);
        IndexSearcher searcher = new IndexSearcher(reader);

        IndexReader readerNONES = DirectoryReader.open(indexNONES);
        IndexSearcher searcherNONES = new IndexSearcher(readerNONES);

        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
        TopScoreDocCollector collectorNONES = TopScoreDocCollector.create(hitsPerPage);

        searcher.search(q, collector);
        searcherNONES.search(q, collectorNONES);

        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        // ScoreDoc[] hitsNONES = collectorNONES.topDocs().scoreDocs;
        ArrayList<Resultado> resultados = new ArrayList<>();
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            resultados.add(new Resultado(d));
        }

        Collections.sort(resultados);

        System.out.println("Mostrando resultados");
        result = new ArrayList<>();
        for (Resultado r : resultados) {
            System.out.println(r.d.get("amazonTitle"));
            System.out.println("**");
            System.out.println(r.d.get("summary"));
            System.out.println("**");
            System.out.println(String.format("Ranking %s ", r.d.get("ranking")));
            //System.out.println(r.d.get("text"));            
            System.out.println("--------------------------------------------------------------");
            NutriInfo ninfo = new NutriInfo();
            boolean hasInfo = !(r.d.get("NDB_No") == null);
            if (hasInfo) {
                ninfo.NDB_No = r.d.get("NDB_No");
                ninfo.Shrt_Desc = r.d.get("Shrt_Desc");
                ninfo.Water_g = r.d.get("Water");
                ninfo.Energ_Kcal = r.d.get("Energ_Kcal");
                ninfo.Protein_g = r.d.get("Protein");
                ninfo.Lipid_Tot_g = r.d.get("Lipid_Tot");
                ninfo.Ash_g = r.d.get("Ash");
                ninfo.Carbohydrt_g = r.d.get("Carbohydrt");
                ninfo.Fiber_TD_g = r.d.get("Fiber_TD");
                ninfo.Sugar_Tot_g = r.d.get("Sugar_Tot");
                ninfo.Calcium_mg = r.d.get("Calcium");
                ninfo.Iron_mg = r.d.get("Iron");
                ninfo.Magnesium_mg = r.d.get("Magnesium");
                ninfo.Phosphorus_mg = r.d.get("Phosphorus");
                ninfo.Potassium_mg = r.d.get("Potassium");
                ninfo.Sodium_mg = r.d.get("Sodium");
                ninfo.Zinc_mg = r.d.get("Zinc");
                ninfo.Copper_mg = r.d.get("Copper");
                ninfo.Manganese_mg = r.d.get("Manganese");
                ninfo.Selenium_µg = r.d.get("Selenium");
                ninfo.Vit_C_mg = r.d.get("Vit_C");
                ninfo.Thiamin_mg = r.d.get("Thiamin");
                ninfo.Riboflavin_mg = r.d.get("Riboflavin");
                ninfo.Niacin_mg = r.d.get("Niacin");
                ninfo.Panto_Acid_mg = r.d.get("Panto_acid");
                ninfo.Vit_B6_mg = r.d.get("Vit_B6");
                ninfo.Folate_Tot_µg = r.d.get("Folate_Tot");
                ninfo.Folic_Acid_µg = r.d.get("Folic_acid");
                ninfo.Food_Folate_µg = r.d.get("Food_Folate");
                ninfo.Folate_DFE_µg = r.d.get("Folate_DFE");
                ninfo.Choline_Tot_mg = r.d.get("Choline_Tot");
                ninfo.Vit_B12_µg = r.d.get("Vit_B12");
                ninfo.Vit_A_IU = r.d.get("Vit_A_IU");
                ninfo.Vit_A_RAE = r.d.get("Vit_A_RAE");
                ninfo.Retinol_µg = r.d.get("Retinol");
                ninfo.Alpha_Carot_µg = r.d.get("Alpha_Carot");
                ninfo.Beta_Carot_µg = r.d.get("Beta_Carot");
                ninfo.Beta_Crypt_µg = r.d.get("Beta_Crypt");
                ninfo.Lycopene_µg = r.d.get("Lycopene");
                ninfo.LutZea_µg = r.d.get("Lut+Zea");
                ninfo.Vit_E_mg = r.d.get("Vit_E");
                ninfo.Vit_D_µg = r.d.get("Vit_D_mcg");
                ninfo.Vit_D_IU = r.d.get("Vit_D_IU");
                ninfo.Vit_K_µg = r.d.get("Vit_K");
                ninfo.FA_Sat_g = r.d.get("FA_Sat");
                ninfo.FA_Mono_g = r.d.get("FA_Mono");
                ninfo.FA_Poly_g = r.d.get("FA_Poly");
                ninfo.Cholestrl_mg = r.d.get("Cholestrl");
                ninfo.GmWt_1 = r.d.get("GmWt_1");
                ninfo.GmWt_Desc1 = r.d.get("GmWt_Desc1");
                ninfo.GmWt_2 = r.d.get("GmWt_2");
                ninfo.GmWt_Desc2 = r.d.get("GmWt_Desc2");
                ninfo.Refuse_Pct = r.d.get("Refuse_Pct");
            }
            if (hasInfo) {
                result.add(new theRealProducto(r.d.get("amazonTitle"),
                        r.d.get("price"), ninfo, r.d.get("summary"), r.d.get("text"), r.d.get("score"), r.d.get("positive"), r.d.get("total"), r.d.get("emotion")));
            } else {
                result.add(new theRealProducto(r.d.get("amazonTitle"),
                        r.d.get("price"), null, r.d.get("summary"), r.d.get("text"), r.d.get("score"), r.d.get("positive"), r.d.get("total"), r.d.get("emotion")));
            }
        }

        /*System.out.println("No ES Found " + hitsNONES.length + " hits.");
         for(int i=0;i<hitsNONES.length;++i) {
         int docId = hitsNONES[i].doc;
         Document d = searcherNONES.doc(docId);
         System.out.println((i + 1) + ". " + d.get("es") + "\t" + d.get("contenido"));
         }*/
        reader.close();

        readerNONES.close();

        return result;
    }

    public Document buscarNutriInfos(String busqueda) throws IOException, ParseException {
        StandardAnalyzer analyzer = new StandardAnalyzer();

        //File indexDirES = new File(dirIndexES);
        Path indexDirES = Paths.get("dataSR27/");
        Directory indexES = FSDirectory.open(indexDirES);

        Path indexDirNONES = Paths.get("dataSR27/");
        Directory indexNONES = FSDirectory.open(indexDirNONES);

        // 2. Query
        String querystr = busqueda;

//        Query q = new QueryParser("amazonTitle", analyzer).parse(querystr);
        //Query qNONES = new QueryParser(Version.LUCENE_43, "contenido", analyzer).parse(querystr);
        String[] fields = {"Shrt_Desc"};
        //QueryParser parser = new QueryParser(field, analyzerWrapper);
        Query q = new MultiFieldQueryParser(fields, analyzer).parse(querystr);

        // 3. Search
        int hitsPerPage = 20;
        IndexReader reader = DirectoryReader.open(indexES);
        IndexSearcher searcher = new IndexSearcher(reader);

        IndexReader readerNONES = DirectoryReader.open(indexNONES);
        IndexSearcher searcherNONES = new IndexSearcher(readerNONES);

        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
        TopScoreDocCollector collectorNONES = TopScoreDocCollector.create(hitsPerPage);

        searcher.search(q, collector);
        searcherNONES.search(q, collectorNONES);

        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        // ScoreDoc[] hitsNONES = collectorNONES.topDocs().scoreDocs;
        if (hits.length == 0) {
            return null;
        }
        if (hits[0].score < 1) {
            return null;
        }
        int docId = hits[0].doc;
        Document d = searcher.doc(docId);
        /*System.out.println("No ES Found " + hitsNONES.length + " hits.");
         for(int i=0;i<hitsNONES.length;++i) {
         int docId = hitsNONES[i].doc;
         Document d = searcherNONES.doc(docId);
         System.out.println((i + 1) + ". " + d.get("es") + "\t" + d.get("contenido"));
         }*/
        reader.close();
        readerNONES.close();
        return d;
    }

}
