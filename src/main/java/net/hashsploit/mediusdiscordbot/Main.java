package net.hashsploit.mediusdiscordbot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException, JSONException {
		
		if (args.length != 1) {
			System.err.println("Error: The path to the configuration file must be passed in as a single argument.");
			return;
		}
		
		final File file = new File(args[0]);
		
		if (!file.isFile()) {
			System.err.println("Error: File not found.");
			return;
		}
			
		final JSONTokener tokener;
		final JSONObject json;
		
		try {
			tokener = new JSONTokener(new FileReader(file));
			json = new JSONObject(tokener);
		} catch (FileNotFoundException e) {
			System.err.println("Error: File not found.");
			return;
		} catch (JSONException e) {
			System.err.println("Error: Invalid configuration file.");
			return;
		}
		
		new MediusBot(json);
	}
}
