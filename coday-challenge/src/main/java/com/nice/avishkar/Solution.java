package com.nice.avishkar;

import java.io.*;
import java.util.*;
import java.nio.file.Path;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Solution {

	public static Map<String, List<CandidateVotes>> constituencycandMap;
	public static Map<String, ConstituencyResult> constituenciesresultMap;
	public static Map<String, JSONArray> constituencycandJSArray;
	public static Map<String, JSONObject> constituencyresultJSObject;

	@SuppressWarnings("unchecked")
	static void processoutputjson() throws Exception {
		// Constituency-Candidate JSON Array
		constituencycandJSArray = new HashMap<>();
		constituencyresultJSObject = new HashMap<>();
		for (Map.Entry<String, List<CandidateVotes>> entry : constituencycandMap.entrySet()) {
			JSONArray temparr = new JSONArray();
			List<CandidateVotes> lst = entry.getValue();
			for (CandidateVotes e : lst) {
				JSONObject jobj = new JSONObject();
				jobj.put("candidateName", e.getCandidateName());
				jobj.put("votes", e.getVotes());
				temparr.add(jobj);
			}
			constituencycandJSArray.put(entry.getKey(), temparr);
		}
		// Constituency - Result JSON Object
		for (Map.Entry<String, ConstituencyResult> entry : constituenciesresultMap.entrySet()) {
			JSONObject tempobj = new JSONObject();
			tempobj.put("winnerName", entry.getValue().getWinnerName());
			tempobj.put("candidiateList", constituencycandJSArray.get(entry.getKey()));
			constituencyresultJSObject.put(entry.getKey(), tempobj);
		}
		// Constituency-wise Result JSON Object
		JSONObject tempobj = new JSONObject();
		for (Map.Entry<String, JSONObject> entry : constituencyresultJSObject.entrySet()) {
			tempobj.put(entry.getKey(), entry.getValue());
		}
		JSONObject resultobject = new JSONObject();
		resultobject.put("resultData", tempobj);

		try {
			File f = new File("output.json");
			FileWriter fr = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fr);
			bw.write(resultobject.toJSONString());
			bw.flush();
			bw.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static void candprocess() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("src/main/resources/candidateFile.csv"));
		String line = "";
		String splitBy = ",";
		line = br.readLine();
		constituencycandMap = new LinkedHashMap<>();
		while ((line = br.readLine()) != null) {
			String det[] = line.split(splitBy);
			if (!constituencycandMap.containsKey(det[0])) {
				constituencycandMap.put(det[0], new LinkedList<CandidateVotes>());
				constituencycandMap.get(det[0]).add(new CandidateVotes("NOTA", 0));
			}
			constituencycandMap.get(det[0]).add(new CandidateVotes(det[1], 0));
		}

		br.close();
	}

	static void votecount() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("src/main/resources/votingFile.csv"));//
		String line = "";
		String splitBy = ",";
		line = br.readLine();
		Map<String, String> votercandMap = new LinkedHashMap<>();
		while ((line = br.readLine()) != null) {
			String det[] = line.split(splitBy);
			if (votercandMap.containsKey(det[0])) {
				// duplicate vote
				if (!votercandMap.get(det[0]).equals("true")) {
					for (CandidateVotes e : constituencycandMap.get(det[1])) {
						if (e.getCandidateName().equals(det[3])) {
							e.decvotes();
							votercandMap.put(det[0], "true");
						}
					}
				}
			} else {
				// valid vote
				for (CandidateVotes e : constituencycandMap.get(det[1])) {
					if (e.getCandidateName().equals(det[3])) {
						e.incvotes();
						votercandMap.put(det[0], det[3]);
					}
				}
			}
		}
		
		br.close();
	}

	static void voteprocess() {
		constituenciesresultMap = new LinkedHashMap<>();
		for (Map.Entry<String, List<CandidateVotes>> entry : constituencycandMap.entrySet()) {
			List<CandidateVotes> lst = entry.getValue();
			CandidateVotes temp = lst.get(0);
			lst.remove(0);
			Collections.sort(lst);
			lst.add(temp);
			String winner = "";
			if (lst.get(0).getVotes() == lst.get(1).getVotes()) {
				winner = "NO_WINNER";
			} else {
				winner = lst.get(0).getCandidateName();
			}

			ConstituencyResult cr = new ConstituencyResult(winner, lst);
			constituenciesresultMap.put(entry.getKey(), cr);
		}
	}

	public ElectionResult execute(Path candidateFile, Path votingFile) throws Exception {
		candprocess();
		votecount();
		voteprocess();
		ElectionResult resultData = new ElectionResult(constituenciesresultMap);
		// Write code here to read CSV files and process them
		processoutputjson();
		return resultData;
	}
}