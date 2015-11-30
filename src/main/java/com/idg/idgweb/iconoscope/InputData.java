package com.idg.idgweb.iconoscope;

import org.json.JSONObject;

public class InputData {
	Canvas canvas;
	App app;
	
	public InputData(Canvas canvas, App app){ 
		this.canvas = canvas;
		this.app = app;
	}
	
	public static InputData fromJSON(JSONObject obj){
		JSONObject canvas_obj = obj.getJSONObject("Canvas");
		JSONObject app_obj = obj.getJSONObject("App");
		Canvas canvas = Canvas.fromJSON(canvas_obj);
		App app = App.fromJSON(app_obj);
		return new InputData(canvas,app);
	}
	
	public App getApp(){ return app; }
	public Canvas getCanvas(){ return canvas; }
}
