/**
 * Search Engine Project
 */

package cms167;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class SearchEngine {
	
	// Declaration of the index structure
	HashMap<String, ArrayList<Location>> index;
	
	
	/**
	 * Constructor -- initialize the index
	 */
	public SearchEngine() {
		this.index = new HashMap<String, ArrayList<Location>>();
		
    //addFileToIndex("texts/macbeth.txt");
    //addFileToIndex("texts/a_midsummer_nights_dream.txt");
    addFileToIndex("texts/romeo_and_juliet.txt");
	}
	
	/**
	 * toString -- print the index
	 */
	public String toString() {
		return this.index.toString();
	}
	
	
	/**
	 * addFileToIndex -- Parse an input file and add its 
	 * words and lines to the index structure
	 * 
	 * @param fileName
	 */
	public void addFileToIndex(String fileName) {
		Scanner input = null;
		
		try {
			input = new Scanner(new File(fileName));
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Extract the first line to be the title of the play
		String play = input.nextLine();

		String actNumber = null;
		String sceneNumber = null;
		
		// Iterate over the lines of the file
		while (input.hasNext()) {
			String line = input.nextLine();
			
			// Create a second Scanner to break the line
			// into its component words
			//
			// By default, the Scanner will break the line
			// into whitespace-delimited chunks
			
			Scanner words = new Scanner(line);
			while (words.hasNext()) {
				String word = words.next();
				
				// If the line starts with "ACT" then we should
				// read the next token and treat that as the act
				// number
				if (word.equals("ACT")) {
					actNumber = words.next();
					continue;
				}
				
				// Same idea for SCENE
				if (word.equals("SCENE")) {
					sceneNumber = words.next();
					continue;
				}
				
				if (word.equals("PROLOGUE")) {
				    actNumber = "PROLOGUE";
                    sceneNumber = ""; // empty string
                    continue;
				}
				
				// Move the word to lowercase
				word = word.toLowerCase();
				
				// Remove any punctuation marks
				word = word.replaceAll("[.,;!?:]", "");
				
				// Insert a mapping into the index
				
				// Initialize an empty ArrayList if the word is
				// not in the HashMap
				if (!this.index.containsKey(word)) {
					this.index.put(word, new ArrayList<Location>());
				}
				
				// Add the word --> line pair into the index
				ArrayList<Location> locations = this.index.get(word);
				
				Location loc = new Location(play, actNumber, sceneNumber, line);
				locations.add(loc);
				this.index.put(word, locations);
				
			}
		}
	}
	
	/**
	 * lookup -- return the lines associated with a word
	 * @param    word
	 * @return   The set of lines as an ArrayList<String>
	 */
	public ArrayList<Location> lookup(String word) {
		return this.index.get(word.toLowerCase());
	}

}