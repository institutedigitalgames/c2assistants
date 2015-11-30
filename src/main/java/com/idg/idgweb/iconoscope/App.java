package com.idg.idgweb.iconoscope;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

import org.json.JSONArray;
import org.json.JSONObject;

public class App {
	//"App": { "Scale":1.3333333333333333, "Points":123456, "AppHeight":900, "AllWords":["Friendship", "Tomato juice", "Rumpelstiltskin"], "AppWidth":1200, "SelectedWord":"Rumpelstiltskin", "DeviceHeight":1200, "Theme":"Default", "DeviceWidth":1920 }
	@XmlElement(name = "Scale")
    public double Scale;
	@XmlElement(name = "Points")
	public double Points;
	@XmlElement(name = "AppHeight")
	public int AppHeight;
	@XmlElement(name = "AppWidth")
	public int AppWidth;
	@XmlElement(name = "DeviceHeight")
	public int DeviceHeight;
	@XmlElement(name = "DeviceWidth")
	public int DeviceWidth;
	@XmlElement(name = "AllWords")
	public ArrayList<String> AllWords;
	@XmlElement(name = "SelectedWord")
	public String SelectedWord;
	@XmlElement(name = "ChallengeText")
	public String ChallengeText;
	@XmlElement(name = "QuestID")
	public int QuestID;
	@XmlElement(name = "MissionID")
	public int MissionID;
	
	public App(double Scale, double Points,int AppHeight,int AppWidth,int DeviceWidth, int DeviceHeight,ArrayList<String> AllWords,String SelectedWord){
		this.Scale = Scale;
		this.Points = Points;
		this.AppHeight = AppHeight;
		this.AppWidth = AppWidth;
		this.DeviceWidth = DeviceWidth;
		this.DeviceHeight = DeviceHeight;
		this.AllWords = new ArrayList<String>();
		this.AllWords.addAll(AllWords);
		this.SelectedWord = SelectedWord;
		//this.ChallengeText = ChallengeText;
	}
	public App(	double Scale, double Points,int AppHeight,int AppWidth,int DeviceWidth, int DeviceHeight,ArrayList<String> AllWords,
				String SelectedWord,String ChallengeText,int QuestID,int MissionID){
		this(Scale, Points, AppHeight, AppWidth, DeviceWidth, DeviceHeight, AllWords, SelectedWord);
		this.ChallengeText = ChallengeText;
		this.QuestID = QuestID;
		this.MissionID = MissionID;
	}
	
	public App createCopy(){
		return new App(this.Scale, this.Points, this.AppHeight, this.AppWidth, this.DeviceWidth , this.DeviceHeight, this.AllWords, this.SelectedWord, this.ChallengeText,this.QuestID,this.MissionID);
	}
	public static App fromJSON(JSONObject obj){
		double scale = obj.has("Scale") ? obj.getDouble("Scale") : 1.3333333333333333;
		double points = obj.has("Points") ? obj.getDouble("Points") : 0;
		int appHeight = obj.has("AppHeight") ? obj.getInt("AppHeight") : 900;
		int appWidth = obj.has("AppWidth") ? obj.getInt("AppWidth") : 1200;
		int deviceHeight = obj.has("DeviceHeight") ? obj.getInt("DeviceHeight") : 1200;
		int deviceWidth = obj.has("DeviceWidth") ? obj.getInt("DeviceWidth") : 1920;
		ArrayList<String> allWords = new ArrayList<String>();
		if(obj.has("AllWords")){
			JSONArray json_wordList = obj.getJSONArray("AllWords");
			for(int i=0;i<json_wordList.length();i++){
				allWords.add(json_wordList.getString(i));
			}
		}
		String selectedWord = obj.has("SelectedWord") ?  obj.getString("SelectedWord") : "Empty";
		String challengeText = obj.has("ChallengeText") ? obj.getString("ChallengeText") : "";
		int questID = obj.has("QuestID") ? obj.getInt("QuestID") : 0;
		int missionID = obj.has("MissionID") ? obj.getInt("MissionID") : 0;
		
		return new App(scale, points, appHeight, appWidth, deviceWidth , deviceHeight, allWords, selectedWord, challengeText, questID, missionID);
	}
}