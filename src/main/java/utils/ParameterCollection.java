package utils;

import java.util.HashMap;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ParameterCollection {
	HashMap<String,String> parameters;
	
	//public static final String parFileLoc = "/var/www/idg_files/idgWebParameters.xml";
	public static final String parFileLoc = "idgWebParameters.xml";
	static HashMap<String,String> parameterInstance = null; 
	
	public ParameterCollection(){
		parameters = new HashMap<String,String>();
	}
	public ParameterCollection(ParameterCollection other){
		parameters = new HashMap<String,String>();
		for(Entry<String,String> entry : other.parameters.entrySet()){
			parameters.put(entry.getKey(),entry.getValue());
		}
	}

	public boolean contains(String name){
		String temp = parameters.get(name);
		return (temp!=null);
	}

	public void setParameter(String name, String value){ parameters.put(name, value); }
	public void setParameter(String name, double value){ setParameter(name,Double.toString(value)); }
	public void setParameter(String name, float value){ setParameter(name,Float.toString(value)); }
	public void setParameter(String name, int value){ setParameter(name,Integer.toString(value)); }
	public void setParameter(String name, boolean value){
		if(value){
			setParameter(name,Integer.toString(1));
		} else {
			setParameter(name,Integer.toString(0));
		}
	}

	public int getInteger(String name){
		String temp = parameters.get(name);
		if(temp==null){ 
			//System.out.println("ParameterCollection: parameter "+name+" does not exist."); 
			return 0;
		}
		return Integer.parseInt(temp);
	}
	public boolean getBoolean(String name){
		int temp = getInteger(name);
		if(temp==0){
			return false;
		} else if (temp==1) {
			return true;
		} else {
			//System.out.println("ParameterCollection: parameter "+name+" is not boolean.");
			return true;
		}
	}
	public float getFloat(String name){
		String temp = parameters.get(name);
		if(temp==null){
			//System.out.println("ParameterCollection: parameter "+name+" does not exist.");
			return 0;
		}
		return Float.parseFloat(temp);
	}
	public double getDouble(String name){
		String temp = parameters.get(name);
		if(temp==null){
			//System.out.println("ParameterCollection: parameter "+name+" does not exist.");
			return 0;
		}
		return Double.parseDouble(temp);
	}
	public String getString(String name){
		String temp = parameters.get(name);
		if(temp==null){
			//System.out.println("ParameterCollection: parameter "+name+" does not exist.");
			return "";
		}
		return temp;
	}

	public String print(){
		String result = "--- BEGIN parameters --- \n";
		Iterator iterator = parameters.entrySet().iterator();
		while(iterator.hasNext()){
			Entry e = (Entry)iterator.next();
			String name = (String)e.getKey();
			String value = (String)e.getValue();
			result += "\t" + name + ": " + value + "\n";
		}
		result += "--- END parameters --- \n";
		return result;
	}

	// ----------------------------------
	// XML INTEGRATION
	// ----------------------------------
	public ParameterCollection(String filename){
		//System.out.println("Loading parameters from "+filename);
		if(filename.equals(parFileLoc) && parameterInstance!=null && !parameterInstance.isEmpty()){ 
			//System.out.println("Parameter instance found:\n"+parameterInstance.toString());
			parameters = parameterInstance; 
			return;
		}
		//System.out.println("Parameter instance not found, loading file...");
		parameters = new HashMap<String,String>();
		File file = new File(filename);
		if (!file.exists()) {
			//System.out.println("ParameterCollection: File "+filename+" does not exist.");
			if(filename.equals(parFileLoc)){
				parameterInstance.put("4scribes_decks","decks/");
				parameterInstance.put("iconoscope_typical","icons/");
			}
			return;
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		Document doc = null;
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(file);
		} catch(Exception e) {
			//System.out.println("ParameterCollection: Error parsing file "+filename+".");
			return;
		}
		doc.getDocumentElement().normalize();
		if(!doc.getDocumentElement().getNodeName().endsWith("parameters")){
			//System.out.println("ParameterCollection: Invalid XML root");
			return;
		}
		NodeList nodeLst = doc.getElementsByTagName("parameter");
		//System.out.println("Information of all employees");
		for (int i = 0; i < nodeLst.getLength(); i++) {
			if (nodeLst.item(i).getNodeType() != Node.ELEMENT_NODE) {
				//System.out.println("ParameterCollection: Invalid XML node");
				break;
			}
			Element el = (Element)nodeLst.item(i);
			parameters.put(el.getAttribute("name"),el.getAttribute("value"));
		}
		//System.out.println("Parameters loaded: \n"+this.toXML());
		if(filename.equals(parFileLoc)){
			parameterInstance = (HashMap<String,String>)parameters.clone(); 
			//System.out.println("Instance initialized: \n"+this.toXML());
		}
	}
	
	public String toXML(){
		// --- create string
		String result = "<parameters>\n";
		Iterator iterator = parameters.entrySet().iterator();
		while(iterator.hasNext()){
			Entry e = (Entry)iterator.next();
			String name = (String)e.getKey();
			String value = (String)e.getValue();
			result += "\t<parameter name=\"" + name + "\" value=\"" + value + "\"/>\n";
		}
		result += "</parameters>\n";
		return result;
	}
	
	public void saveToXML(String filename){
		// --- save string to file
		File file = new File(filename);
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			fw.write(this.toXML());
			fw.close();
		} catch(Exception e) {
			//System.out.println("ParameterCollection: Error writing to file "+filename+".");
			return;
		}
	}
};