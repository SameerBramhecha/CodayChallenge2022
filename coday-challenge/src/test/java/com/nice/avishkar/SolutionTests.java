package com.nice.avishkar;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * Basic test cases for candidates
 * */
public class SolutionTests {

	private static ElectionResult resultData;

	// Run once, execute solution and load resultData
	@BeforeClass
	public static void initAndExecute() {
		Instant start = Instant.now();
		Path candidateFilePath = Paths.get("src/main/resources/candidateFile.csv");
		Path votingFilePath = Paths.get("src/main/resources/votingFile.csv");
		Solution solution = new Solution();

		try {
			resultData = solution.execute(candidateFilePath, votingFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();
		System.err.println("Execution took " + timeElapsed + " millis");
	}

	@Test
	public void test1() {

		Assert.assertTrue(resultData.getResultData().get("Ulhasnagar").getWinnerName().equals("AnamikaPilla"));
		Assert.assertTrue(resultData.getResultData().get("Ulhasnagar").getCandidateList().get(0).getCandidateName()
				.equals("AnamikaPilla"));
		Assert.assertTrue(resultData.getResultData().get("Ulhasnagar").getCandidateList().get(0).getVotes() == 155);

		Assert.assertTrue(resultData.getResultData().get("Ulhasnagar").getCandidateList().get(1).getCandidateName()
				.equals("BhavesChaturvedi"));
		Assert.assertTrue(resultData.getResultData().get("Ulhasnagar").getCandidateList().get(1).getVotes() == 149);

		Assert.assertTrue(resultData.getResultData().get("Ulhasnagar").getCandidateList().get(2).getCandidateName()
				.equals("SwaraIyer"));
		Assert.assertTrue(resultData.getResultData().get("Ulhasnagar").getCandidateList().get(2).getVotes() == 145);

	}

	@Test
	public void test2() {
		Assert.assertTrue(resultData.getResultData().get("Niphad").getWinnerName().equals("NO_WINNER"));
		Assert.assertTrue(resultData.getResultData().get("Niphad").getCandidateList().get(0).getCandidateName()
				.equals("KamaVarma"));
		Assert.assertTrue(resultData.getResultData().get("Niphad").getCandidateList().get(0).getVotes() == 270);
	}

	@Test
	public void test3() {
		Assert.assertTrue(resultData.getResultData().get("Hingna").getWinnerName().equals("HiranyaKhatri"));
		Assert.assertTrue(resultData.getResultData().get("Hingna").getCandidateList().get(0).getCandidateName()
				.equals("HiranyaKhatri"));
		Assert.assertTrue(resultData.getResultData().get("Hingna").getCandidateList().get(0).getVotes() == 256);
	}

}
