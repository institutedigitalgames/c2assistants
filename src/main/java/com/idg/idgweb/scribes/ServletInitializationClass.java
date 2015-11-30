/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.idg.idgweb.scribes;

import com.idg.idgweb.scribes.datamanagement.SemanticDifferenceTable;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import utils.InitParameters;

/**
 *
 * @author c2learn
 */
public class ServletInitializationClass implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Initialization scripts removed");
		//InitParameters.saveDefaultParameters();
		/*
		ElementList.initInstance();
		SemanticDifferenceTable.initInstance();
		*/
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Destruction scripts removed");
		/*
		SemanticDifferenceTable.getInstance().locallySaveTable();
        ElementList.destroyInstance();
        SemanticDifferenceTable.destroyInstance();
		*/
    }
    
}
