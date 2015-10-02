package org.pql.test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import junit.framework.TestCase;

import org.pql.api.PQLAPI;
import org.pql.ini.PQLIniFile;
import org.pql.query.PQLQueryResult;

public class PQLTestAPQL extends TestCase {
	private static PQLAPI	pqlAPI	= null;
	private static boolean	flag	= false;
	
	public PQLTestAPQL() throws ClassNotFoundException, SQLException, IOException {
		if (PQLTestAPQL.flag) return;
		PQLTestAPQL.flag = true;
		
		PQLIniFile iniFile = new PQLIniFile();
		if (!iniFile.load()) { 
			System.out.println("ERROR: Cannot load PQL ini file.");
			return;
		}
		
		PQLTestAPQL.pqlAPI = new PQLAPI(iniFile.getMySQLURL(), iniFile.getMySQLUser(), iniFile.getMySQLPassword(),
				iniFile.getPostgreSQLHost(), iniFile.getPostgreSQLName(), iniFile.getPostgreSQLUser(), iniFile.getPostgreSQLPassword(),
				iniFile.getLoLA2Path(),
				iniFile.getLabelSimilaritySeacrhConfiguration(),
				iniFile.getThreeValuedLogicType(),  
				iniFile.getIndexType(),
				iniFile.getLabelManagerType(),
				iniFile.getDefaultLabelSimilarityThreshold(),
				iniFile.getIndexedLabelSimilarityThresholds(),
				iniFile.getNumberOfQueryThreads(),
				iniFile.getDefaultBotMaxIndexTime(),
				iniFile.getDefaultBotSleepTime());
		
		PQLTestAPQL.pqlAPI.reset();
		System.out.println("Reset done!");
		PQLTestAPQL.pqlAPI.storeModel(new File("./pnml/apql/1.pnml"),"1.pnml");
		System.out.println("1.pnml stored");
		PQLTestAPQL.pqlAPI.storeModel(new File("./pnml/apql/2.pnml"),"2.pnml");
		System.out.println("2.pnml stored");
		PQLTestAPQL.pqlAPI.storeModel(new File("./pnml/apql/3.pnml"),"3.pnml");
		System.out.println("3.pnml stored");
		PQLTestAPQL.pqlAPI.storeModel(new File("./pnml/apql/4.pnml"),"4.pnml");
		System.out.println("4.pnml stored");
		PQLTestAPQL.pqlAPI.storeModel(new File("./pnml/apql/5.pnml"),"5.pnml");
		System.out.println("5.pnml stored");
		PQLTestAPQL.pqlAPI.storeModel(new File("./pnml/apql/6.pnml"),"6.pnml");
		System.out.println("6.pnml stored");
		PQLTestAPQL.pqlAPI.storeModel(new File("./pnml/apql/7.pnml"),"7.pnml");
		System.out.println("7.pnml stored");
		PQLTestAPQL.pqlAPI.storeModel(new File("./pnml/apql/8.pnml"),"8.pnml");
		System.out.println("8.pnml stored");
		PQLTestAPQL.pqlAPI.storeModel(new File("./pnml/apql/9.pnml"),"9.pnml");
		System.out.println("9.pnml stored");
		PQLTestAPQL.pqlAPI.storeModel(new File("./pnml/apql/10.pnml"),"10.pnml");
		System.out.println("10.pnml stored");
		
		PQLTestAPQL.pqlAPI.index(PQLTestAPQL.pqlAPI.getInternalID("1.pnml"));
		System.out.println("1.pnml indexed");
		PQLTestAPQL.pqlAPI.index(PQLTestAPQL.pqlAPI.getInternalID("2.pnml"));
		System.out.println("2.pnml indexed");
		PQLTestAPQL.pqlAPI.index(PQLTestAPQL.pqlAPI.getInternalID("3.pnml"));
		System.out.println("3.pnml indexed");
		PQLTestAPQL.pqlAPI.index(PQLTestAPQL.pqlAPI.getInternalID("4.pnml"));
		System.out.println("4.pnml indexed");
		PQLTestAPQL.pqlAPI.index(PQLTestAPQL.pqlAPI.getInternalID("5.pnml"));
		System.out.println("5.pnml indexed");
		PQLTestAPQL.pqlAPI.index(PQLTestAPQL.pqlAPI.getInternalID("6.pnml"));
		System.out.println("6.pnml indexed");
		PQLTestAPQL.pqlAPI.index(PQLTestAPQL.pqlAPI.getInternalID("7.pnml"));
		System.out.println("7.pnml indexed");
		PQLTestAPQL.pqlAPI.index(PQLTestAPQL.pqlAPI.getInternalID("8.pnml"));
		System.out.println("8.pnml indexed");
		PQLTestAPQL.pqlAPI.index(PQLTestAPQL.pqlAPI.getInternalID("9.pnml"));
		System.out.println("9.pnml indexed");
		PQLTestAPQL.pqlAPI.index(PQLTestAPQL.pqlAPI.getInternalID("10.pnml"));
		System.out.println("10.pnml indexed");
	}

	public void test001() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM *");
		assertEquals(1,queryResult.getNumberOfParseErrors());
		assertEquals(0,queryResult.getSearchResults().size());
	}
	
	public void test002() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM *;");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		assertEquals(10,queryResult.getSearchResults().size());
	}
	
	public void test003() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE CanOccur(\"A\") AND AlwaysOccurs(\"B\");");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		assertEquals(7,queryResult.getSearchResults().size());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(true, res.contains("1.pnml"));
		assertEquals(true, res.contains("2.pnml"));
		assertEquals(true, res.contains("3.pnml"));
		assertEquals(true, res.contains("5.pnml"));
		assertEquals(true, res.contains("6.pnml"));
		assertEquals(true, res.contains("7.pnml"));
		assertEquals(true, res.contains("9.pnml"));
	}
	
	public void test004() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE Conflict(\"B\",\"C\");");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(2,res.size());
		
		assertEquals(true, res.contains("4.pnml"));
		assertEquals(true, res.contains("8.pnml"));
	}
	
	public void test005() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE CanConflict(\"B\",\"C\");");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(3,res.size());
		
		assertEquals(true, res.contains("4.pnml"));
		assertEquals(true, res.contains("8.pnml"));
		assertEquals(true, res.contains("10.pnml"));
	}
	
	public void test006() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE CanConflict(\"B\",\"C\") OR (CanOccur(\"B\") AND NOT \"C\" IN *);");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(5,res.size());
		
		assertEquals(true, res.contains("1.pnml"));
		assertEquals(true, res.contains("2.pnml"));
		assertEquals(true, res.contains("4.pnml"));
		assertEquals(true, res.contains("8.pnml"));
		assertEquals(true, res.contains("10.pnml"));
	}
	
	public void test007() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE CanConflict(\"B\",\"C\") OR CanConflict(\"B\",\"C\") IS UNKNOWN;");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(5,res.size());
		
		assertEquals(true, res.contains("1.pnml"));
		assertEquals(true, res.contains("2.pnml"));
		assertEquals(true, res.contains("4.pnml"));
		assertEquals(true, res.contains("8.pnml"));
		assertEquals(true, res.contains("10.pnml"));
	}
	
	public void test008() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE Cooccur(\"A\",\"B\");");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(7,res.size());
		
		assertEquals(true, res.contains("1.pnml"));
		assertEquals(true, res.contains("2.pnml"));
		assertEquals(true, res.contains("3.pnml"));
		assertEquals(true, res.contains("5.pnml"));
		assertEquals(true, res.contains("6.pnml"));
		assertEquals(true, res.contains("7.pnml"));
		assertEquals(true, res.contains("9.pnml"));
	}
	
	public void test009() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE CanCooccur(\"A\",\"B\");");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(10,res.size());
	}
	
	public void test010() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE CanCooccur(\"I\",\"H\");");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(4,res.size());
		
		assertEquals(true, res.contains("7.pnml"));
		assertEquals(true, res.contains("8.pnml"));
		assertEquals(true, res.contains("9.pnml"));
		assertEquals(true, res.contains("10.pnml"));
	}
	
	public void test011() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE TotalCausal(\"A\",\"B\");");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(6,res.size());
		
		assertEquals(true, res.contains("3.pnml"));
		assertEquals(true, res.contains("4.pnml"));
		assertEquals(true, res.contains("7.pnml"));
		assertEquals(true, res.contains("8.pnml"));
		assertEquals(true, res.contains("9.pnml"));
		assertEquals(true, res.contains("10.pnml"));
	}
	
	public void test012() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE TotalCausal(\"A\",\"E\");");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(3,res.size());
		
		assertEquals(true, res.contains("7.pnml"));
		assertEquals(true, res.contains("8.pnml"));
		assertEquals(true, res.contains("9.pnml"));
	}
	
	public void test013() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE TotalCausal({\"A\",\"B\",\"C\"},{\"K\"},ALL);");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(2,res.size());
		
		assertEquals(true, res.contains("6.pnml"));
		assertEquals(true, res.contains("9.pnml"));
	}
	
	public void test014() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE Cooccur(\"A\",{\"F\",\"H\"},ANY);");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(4,res.size());
		
		assertEquals(true, res.contains("5.pnml"));
		assertEquals(true, res.contains("6.pnml"));
		assertEquals(true, res.contains("7.pnml"));
		assertEquals(true, res.contains("9.pnml"));
	}
	
	public void test015() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE Cooccur(\"A\",{\"F\",\"H\"},ALL);");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(3,res.size());
		
		assertEquals(true, res.contains("6.pnml"));
		assertEquals(true, res.contains("7.pnml"));
		assertEquals(true, res.contains("9.pnml"));
	}
	
	public void test016() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE TotalConcurrent(\"B\",\"D\");");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(1,res.size());
		
		assertEquals(true, res.contains("5.pnml"));
	}
	
	public void test017() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE TotalConcurrent(\"B\",\"C\");");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(5,res.size());
		
		assertEquals(true, res.contains("3.pnml"));
		assertEquals(true, res.contains("4.pnml"));
		assertEquals(true, res.contains("5.pnml"));
		assertEquals(true, res.contains("8.pnml"));
		assertEquals(true, res.contains("9.pnml"));
	}
	
	public void test018() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE (((TotalConcurrent(\"B\",\"C\")))) AND CanCooccur(\"B\",\"C\");");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(3,res.size());
		
		assertEquals(true, res.contains("3.pnml"));
		assertEquals(true, res.contains("5.pnml"));
		assertEquals(true, res.contains("9.pnml"));
	}
	
	public void test019() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE TotalCausal(\"A\",\"C\") AND TotalConcurrent({\"C\"},{\"K\",\"D\"},ALL);");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(1,res.size());
		
		assertEquals(true, res.contains("7.pnml"));
	}
	
	public void test020() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE TotalCausal(\"A\",\"C\") OR TotalConcurrent({\"C\"},{\"K\",\"D\"},ALL);");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(4,res.size());
		
		assertEquals(true, res.contains("3.pnml"));
		assertEquals(true, res.contains("4.pnml"));
		assertEquals(true, res.contains("7.pnml"));
		assertEquals(true, res.contains("9.pnml"));
	}
	
	public void test021() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE TotalCausal(\"A\",\"C\") OR TotalConcurrent({\"C\"},{\"K\",\"D\"},ANY);");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(6,res.size());
		
		assertEquals(true, res.contains("3.pnml"));
		assertEquals(true, res.contains("4.pnml"));
		assertEquals(true, res.contains("7.pnml"));
		assertEquals(true, res.contains("8.pnml"));
		assertEquals(true, res.contains("9.pnml"));
		assertEquals(true, res.contains("10.pnml"));
	}
	
	public void test022() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE TotalCausal(\"A\",\"C\") OR (TotalConcurrent({\"C\"},{\"K\",\"D\"},ANY) AND CanCooccur({\"C\"},{\"K\",\"D\"},ALL));");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(4,res.size());
		
		assertEquals(true, res.contains("3.pnml"));
		assertEquals(true, res.contains("4.pnml"));
		assertEquals(true, res.contains("7.pnml"));
		assertEquals(true, res.contains("9.pnml"));
	}
	
	public void test023() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE {\"A\",\"C\"} IS SUBSET OF GetTasksAlwaysOccurs(*);");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(5,res.size());
		
		assertEquals(true, res.contains("3.pnml"));
		assertEquals(true, res.contains("5.pnml"));
		assertEquals(true, res.contains("6.pnml"));
		assertEquals(true, res.contains("7.pnml"));
		assertEquals(true, res.contains("9.pnml"));
	}
	
	public void test024() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("x = GetTasksAlwaysOccurs(*); SELECT * FROM * WHERE {\"A\",\"C\"} IS SUBSET OF x;");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(5,res.size());
		
		assertEquals(true, res.contains("3.pnml"));
		assertEquals(true, res.contains("5.pnml"));
		assertEquals(true, res.contains("6.pnml"));
		assertEquals(true, res.contains("7.pnml"));
		assertEquals(true, res.contains("9.pnml"));
	}
	
	public void test025() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("x = GetTasksAlwaysOccurs(*); SELECT * FROM * WHERE ({\"A\",\"C\"} IS SUBSET OF x) IS NOT TRUE;");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(5,res.size());
		
		assertEquals(true, res.contains("1.pnml"));
		assertEquals(true, res.contains("2.pnml"));
		assertEquals(true, res.contains("4.pnml"));
		assertEquals(true, res.contains("8.pnml"));
		assertEquals(true, res.contains("10.pnml"));
	}

	public void test026() throws ClassNotFoundException, SQLException {
		PQLQueryResult queryResult = PQLTestAPQL.pqlAPI.query("SELECT * FROM * WHERE TRUE;");
		assertEquals(0,queryResult.getNumberOfParseErrors());
		Set<String> res = queryResult.getSearchResults();
		assertEquals(10,res.size());
	}
}
