package in.ac.iitb.cfilt.cpost;

import in.ac.iitb.cfilt.prop.AppProperties;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
/**
 * This class is used to read the config file. 
 * The format of the config file is as follows.<br/>
 * <br/>
 * [Context1]<br/>
 * var1 = value1<br/>
 * var2 = value2<br/>
 * <br/>
 * [Context2]<br/>
 * var1 = value1<br/>
 * var2 = value2<br/>
 * <br/>
 * 
 * @author Dinesh Gadge
 *
 */
public class ConfigReader {

	static HashMap<String, String> config;

	public static String get(String key){
		return config.get(key);
	}

	/**
	 * Prints the values in the HashMap <code>config</code>
	 */
	public static void printValues(){
		Set s = config.entrySet();
		Iterator si = s.iterator();
		Map.Entry me;
		while(si.hasNext()){
			me = ((Map.Entry) (si.next()));
			System.out.println(me.getKey() + " " + me.getValue());
		}

	}

	/**
	 * Reads the config file and stores the values in a HashMap.
	 * Finally the HashMap contains the values as :<br/>
	 * config.get("Context1.var1") will give value1 in Context1<br/>
	 * config.get("Context2.var2") will give value2 in Context2 etc.<br/>
	 * @param configFile A String giving the path/name of the config File 
	 */
	public static void read(String configFile){
		try {
			if(configFile.startsWith("$setu")){
				
				String path=AppProperties.getProperty("setu");
				if((path.substring(path.length()-1)).equals("/")){
					path=path.substring(0,path.length()-1);
				}
				configFile=path.concat(configFile.substring(5));
			}

			BufferedReader bcfr = new BufferedReader(new InputStreamReader(new FileInputStream(configFile), "UTF8"));
			String varPrefix = "";
			String varName;
			String value;
			int indexOfEqual;
			String line = null;
			config = new HashMap<String,String>();
			while((line = bcfr.readLine()) != null){
				if(line.equals(""))
					continue;
				if(line.startsWith("[") && line.trim().endsWith("]")){
					varPrefix = line.substring(1, line.indexOf("]"))+".";
				}
				else{
					indexOfEqual = line.indexOf("=");
					varName = line.substring(0, indexOfEqual).trim();
					value = line.substring(indexOfEqual + 1).trim();
					config.put(varPrefix + varName, value);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * main function to check the working of ConfigReader class.
	 * @param args args[0] gives the path/name of the config file
	 */
	public static void main(String[] args){
		ConfigReader.read(args[0]);
		ConfigReader.printValues();
	}
}
