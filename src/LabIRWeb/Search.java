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
import java.util.List;
import java.util.Scanner;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
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
<<<<<<< Updated upstream
        int hitsPerPage = 10;
=======
        int hitsPerPage = 20;
>>>>>>> Stashed changes
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
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println(d.get("amazonTitle"));
<<<<<<< Updated upstream
            System.out.println(hits[i].score);
=======
            System.out.println("**");
            System.out.println(d.get("summary"));
            System.out.println("**");
            System.out.println(d.get("text"));            
            System.out.println("--------------------------------------------------------------");
>>>>>>> Stashed changes
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
