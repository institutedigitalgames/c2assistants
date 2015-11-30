package utils;

public class InitParameters {
	static public void saveDefaultParameters(){
		ParameterCollection pc = new ParameterCollection();
		pc.setParameter("4scribes_decks", "decks/");
		pc.setParameter("iconoscope_typical", "icons/");
		//---
		pc.setParameter("mutateAddShapeChance", "-0.1");
		pc.setParameter("mutateCloneShapeChance", "0.1");
		pc.setParameter("mutateRemoveShapeChance", "0.1");
		pc.setParameter("mutateChangeShapeAnyChance", "0.1");
		pc.setParameter("mutateChangeColorAnyChance", "0.1");
		pc.setParameter("mutateTranslateAnyChance", "0.05");
		pc.setParameter("mutateScaleAnyChance", "0.1");
		pc.setParameter("mutateRotateAnyChance", "0.1");
		pc.setParameter("mutateTranslateMod", "0.1");
		pc.setParameter("mutateMinScale", "0.5");

		pc.saveToXML(ParameterCollection.parFileLoc);
	}
	
	public static void main(String[] args) {
		saveDefaultParameters();
	}
}
