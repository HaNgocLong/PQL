package org.pql.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.pql.api.PQLAPI;
import org.pql.ini.PQLIniFile;
import org.pql.query.PQLQueryResult;

/**
 * This test is part of the core test package.
 * 
 * This test must be successful for every PQL commit!
 * 
 * @author Artem Polyvyanyy
 */
public class Test_Cases_1 {
	private static PQLAPI	pqlAPI	= null;
	
	@BeforeClass
	public static void beforeClass() throws ClassNotFoundException, SQLException, IOException {
		PQLIniFile iniFile = new PQLIniFile();
		if (!iniFile.load()) { 
			System.out.println("ERROR: Cannot load PQL ini file.");
			return;
		}
	
		Test_Cases_1.pqlAPI = new PQLAPI(iniFile.getMySQLURL(), iniFile.getMySQLUser(), iniFile.getMySQLPassword(),
				iniFile.getPostgreSQLHost(), iniFile.getPostgreSQLName(), iniFile.getPostgreSQLUser(), iniFile.getPostgreSQLPassword(),
				iniFile.getLoLA2Path(),
				iniFile.getLabelSimilaritySeacrhConfiguration(),
				iniFile.getIndexType(),
				iniFile.getLabelManagerType(),
				iniFile.getDefaultLabelSimilarityThreshold(),
				iniFile.getIndexedLabelSimilarityThresholds(),
				iniFile.getNumberOfQueryThreads(),
				iniFile.getDefaultBotMaxIndexTime(),
				iniFile.getDefaultBotSleepTime());
		
		// TODO check that we are connected to the right version of PQL
		
		Test_Cases_1.pqlAPI.reset();

		System.out.println("Reset done!");
		Test_Cases_1.pqlAPI.storeModel(new File("./pnml/pql/1.pnml"),"1.pnml");
		System.out.println("1.pnml stored");
		Test_Cases_1.pqlAPI.storeModel(new File("./pnml/pql/2.pnml"),"2.pnml");
		System.out.println("2.pnml stored");
		Test_Cases_1.pqlAPI.storeModel(new File("./pnml/pql/3.pnml"),"3.pnml");
		System.out.println("3.pnml stored");
		Test_Cases_1.pqlAPI.storeModel(new File("./pnml/pql/4.pnml"),"4.pnml");
		System.out.println("4.pnml stored");
		Test_Cases_1.pqlAPI.storeModel(new File("./pnml/pql/5.pnml"),"5.pnml");
		System.out.println("5.pnml stored");
		Test_Cases_1.pqlAPI.storeModel(new File("./pnml/pql/6.pnml"),"6.pnml");
		System.out.println("6.pnml stored");
		Test_Cases_1.pqlAPI.storeModel(new File("./pnml/pql/7.pnml"),"7.pnml");
		System.out.println("7.pnml stored");
		Test_Cases_1.pqlAPI.storeModel(new File("./pnml/pql/8.pnml"),"8.pnml");
		System.out.println("8.pnml stored");
		Test_Cases_1.pqlAPI.storeModel(new File("./pnml/pql/9.pnml"),"9.pnml");
		System.out.println("9.pnml stored");
		Test_Cases_1.pqlAPI.storeModel(new File("./pnml/pql/10.pnml"),"10.pnml");
		System.out.println("10.pnml stored");
		
		Test_Cases_1.pqlAPI.index(Test_Cases_1.pqlAPI.getInternalID("1.pnml"));
		System.out.println("1.pnml indexed");
		Test_Cases_1.pqlAPI.index(Test_Cases_1.pqlAPI.getInternalID("2.pnml"));
		System.out.println("2.pnml indexed");
		Test_Cases_1.pqlAPI.index(Test_Cases_1.pqlAPI.getInternalID("3.pnml"));
		System.out.println("3.pnml indexed");
		Test_Cases_1.pqlAPI.index(Test_Cases_1.pqlAPI.getInternalID("4.pnml"));
		System.out.println("4.pnml indexed");
		Test_Cases_1.pqlAPI.index(Test_Cases_1.pqlAPI.getInternalID("5.pnml"));
		System.out.println("5.pnml indexed");
		Test_Cases_1.pqlAPI.index(Test_Cases_1.pqlAPI.getInternalID("6.pnml"));
		System.out.println("6.pnml indexed");
		Test_Cases_1.pqlAPI.index(Test_Cases_1.pqlAPI.getInternalID("7.pnml"));
		System.out.println("7.pnml indexed");
		Test_Cases_1.pqlAPI.index(Test_Cases_1.pqlAPI.getInternalID("8.pnml"));
		System.out.println("8.pnml indexed");
		Test_Cases_1.pqlAPI.index(Test_Cases_1.pqlAPI.getInternalID("9.pnml"));
		System.out.println("9.pnml indexed");
		Test_Cases_1.pqlAPI.index(Test_Cases_1.pqlAPI.getInternalID("10.pnml"));
		System.out.println("10.pnml indexed");
		
	}

	@Test
	public void test001() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = Test_Cases_1.pqlAPI.query("SELECT * FROM * WHERE CanOccur(\"D\") AND Conflict(\"D\", \"E\");");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		assertEquals(2,queryResult.getSearchResults().size());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(true, res.contains("1.pnml"));
		assertEquals(true, res.contains("5.pnml"));
		
	}
	
	@Test
	public void test002() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = Test_Cases_1.pqlAPI.query("SELECT * FROM * WHERE AlwaysOccurs(\"C\") OR Cooccur(\"B\", \"C\");");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		assertEquals(6,queryResult.getSearchResults().size());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(true, res.contains("1.pnml"));
		assertEquals(true, res.contains("4.pnml"));
		assertEquals(true, res.contains("6.pnml"));
		assertEquals(true, res.contains("7.pnml"));
		assertEquals(true, res.contains("9.pnml"));
		assertEquals(true, res.contains("10.pnml"));
		
	}
	
	@Test
	public void test003() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = Test_Cases_1.pqlAPI.query("SELECT * FROM * WHERE (CanOccur(\"G\") AND (NOT Conflict(\"E\", \"G\"))) OR (TotalConcurrent(\"C\", \"D\") AND AlwaysOccurs(\"D\"));");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		assertEquals(5,queryResult.getSearchResults().size());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(true, res.contains("4.pnml"));
		assertEquals(true, res.contains("6.pnml"));
		assertEquals(true, res.contains("7.pnml"));
		assertEquals(true, res.contains("9.pnml"));
		assertEquals(true, res.contains("10.pnml"));
		
	}
	
	@Test
	public void test004() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = Test_Cases_1.pqlAPI.query("SELECT * FROM * WHERE CanOccur({\"F\", \"G\"}, ALL) AND AlwaysOccurs({\"F\", \"G\"}, ANY);");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		assertEquals(3,queryResult.getSearchResults().size());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(true, res.contains("6.pnml"));
		assertEquals(true, res.contains("9.pnml"));
		assertEquals(true, res.contains("10.pnml"));
		
	}
	
	@Test
	public void test005() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = Test_Cases_1.pqlAPI.query("SELECT * FROM * WHERE Cooccur(\"B\", {\"C\", \"D\"}, ALL) AND TotalConcurrent(\"B\", {\"C\", \"D\"}, ANY);");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		assertEquals(3,queryResult.getSearchResults().size());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(true, res.contains("4.pnml"));
		assertEquals(true, res.contains("9.pnml"));
		assertEquals(true, res.contains("10.pnml"));
		
	}

	@Test
	public void test006() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = Test_Cases_1.pqlAPI.query("SELECT * FROM * WHERE Conflict({\"A\", \"B\"}, {\"E\", \"F\"}, ANY) OR (Cooccur({\"A\", \"B\"}, {\"E\", \"F\"}, EACH) AND TotalCausal({\"A\", \"B\"}, {\"E\", \"F\"}, ALL));");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		assertEquals(3,queryResult.getSearchResults().size());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(true, res.contains("1.pnml"));
		assertEquals(true, res.contains("2.pnml"));
		assertEquals(true, res.contains("3.pnml"));
		
	}
	
	@Test
	public void test007() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = Test_Cases_1.pqlAPI.query("SELECT * FROM * WHERE \"C\" IN (GetTasksAlwaysOccurs({\"C\"}) UNION GetTasksTotalCausal({\"C\"}, {\"B\", \"D\"}, ALL));");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		assertEquals(7,queryResult.getSearchResults().size());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(true, res.contains("1.pnml"));
		assertEquals(true, res.contains("2.pnml"));
		assertEquals(true, res.contains("3.pnml"));
		assertEquals(true, res.contains("4.pnml"));
		assertEquals(true, res.contains("7.pnml"));
		assertEquals(true, res.contains("9.pnml"));
		assertEquals(true, res.contains("10.pnml"));
	
		
	}
	
	@Test
	public void test008() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = Test_Cases_1.pqlAPI.query("SELECT * FROM * WHERE \"G\" IN (GetTasksCanOccur({\"G\"}) INTERSECT GetTasksConflict({\"G\"}, {\"D\", \"E\", \"F\"}, ANY));");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		assertEquals(1,queryResult.getSearchResults().size());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(true, res.contains("8.pnml"));
		
	}


	@Test
	public void test009() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = Test_Cases_1.pqlAPI.query("SELECT * FROM * WHERE GetTasksCooccur({\"A\", \"B\", \"C\"}, {\"D\", \"E\"}, ANY) NOT EQUALS GetTasksTotalConcurrent({\"A\", \"B\", \"C\"}, {\"D\", \"E\"}, ANY);");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		assertEquals(7,queryResult.getSearchResults().size());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(true, res.contains("3.pnml"));
		assertEquals(true, res.contains("4.pnml"));
		assertEquals(true, res.contains("5.pnml"));
		assertEquals(true, res.contains("6.pnml"));
		assertEquals(true, res.contains("7.pnml"));
		assertEquals(true, res.contains("9.pnml"));
		assertEquals(true, res.contains("10.pnml"));
	
		
	}


	@Test
	public void test010() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = Test_Cases_1.pqlAPI.query("SELECT * FROM * WHERE ({\"A\", \"B\", \"E\", \"F\"} EXCEPT GetTasksCooccur({\"A\", \"B\", \"E\", \"F\"}, {\"C\", \"D\"}, ALL)) OVERLAPS WITH GetTasksConflict({\"A\", \"B\", \"E\", \"F\"}, {\"C\", \"D\"}, ANY);");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		assertEquals(5,queryResult.getSearchResults().size());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(true, res.contains("1.pnml"));
		assertEquals(true, res.contains("2.pnml"));
		assertEquals(true, res.contains("3.pnml"));
		assertEquals(true, res.contains("5.pnml"));
		assertEquals(true, res.contains("6.pnml"));
		
	}

	
	
}