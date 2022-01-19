package cms167;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.ArrayList;

@RestController
public class Controller {
    private SearchEngine engine;
    
    public Controller() {
    this.engine = new SearchEngine();
    
    // Print the engine to verify that it's being initialized correctly.
    System.out.println(this.engine);
    }

    @RequestMapping("/hello")
    public String hello(@RequestParam(value="name", defaultValue="World") String name) {
    return "Hello, " + name + "!";
    }  
    @RequestMapping("/goodbye")
    public String goodbye() {
        return "Goodbye, Friend!";
    }
    @RequestMapping("")
    public String index() {
    String indexHtml = null;

    try {
        byte[] bytes = Files.readAllBytes(Paths.get("html/index.html"));
    	indexHtml = new String(bytes);
    } catch(Exception e) {
        e.printStackTrace();
    }

    return indexHtml;
    }
    @RequestMapping("/search")
    public String search(@RequestParam(value="word", defaultValue="") String word) {
        ArrayList<Location> results = this.engine.lookup(word);
        
        String response = "";
        
        String nothing = "It looks like Shakespeare is not fond of this word. Try another one.";
        
        if (results == null) {
            return nothing; 
        }
        
        for (Location loc : results) {
            response += "<p>";
            response += loc.toString();
            response += "</p>";
        }
        
        return response;
    }
}