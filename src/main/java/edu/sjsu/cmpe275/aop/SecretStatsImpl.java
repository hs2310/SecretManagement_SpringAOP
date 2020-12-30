package edu.sjsu.cmpe275.aop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

public class SecretStatsImpl implements SecretStats {
	/***
	 * This is a dummy implementation only. You are expected to provide an actual
	 * implementation based on the requirements.
	 */

	public Map<UUID, String> secrets = new HashMap<UUID, String>();

	public HashMap<UUID, String> creator = new HashMap<UUID, String>();

	public HashMap<UUID, HashMap<String, HashSet<String>>> lookup = new HashMap<UUID, HashMap<String, HashSet<String>>>();

	public HashMap<UUID, HashMap<String, HashSet<String>>> lookup1 = new HashMap<UUID, HashMap<String, HashSet<String>>>();

	public HashMap<UUID, HashSet<String>> readDS = new HashMap<>();

	public void printLooUp() {

		// lookup.forEach((K, V) -> {
		// 	System.out.println(K + " : ");
		// 	V.forEach((k, v) -> System.out.println("\t" + k + " : " + v));
		// });
	}

	@Override
	public void resetStatsAndSystem() {
		this.secrets.clear();
		this.creator.clear();
		this.lookup.clear();
		this.lookup1.clear();
		this.readDS.clear();
	}

	@Override
	public int getLengthOfLongestSecret() {
		int maxSize = 0;

		for (UUID key : secrets.keySet()) {
			maxSize = Math.max(maxSize, secrets.get(key).length());
		}

		return maxSize;
	}

	@Override
	public String getMostTrustedUser() {
		if(lookup1.isEmpty())
			return null;
		HashMap<String, Integer> count = new HashMap<>();
				
		lookup1.forEach((K, V) -> {
			V.forEach((k, v) -> {
				v.forEach((val) -> {
					count.put(val, count.getOrDefault(val, 0) + 1);
				});
			});
		});


		
		
		HashMap<Integer, ArrayList<String>> reversedCount = new HashMap<>();

		for (Entry<String, Integer> set : count.entrySet()) {
			ArrayList<String> l = new ArrayList<String>();

			// if (reversedCount.get(set.getValue()))

			ArrayList<String> li = reversedCount.getOrDefault(set.getValue(), l );
			
			li.add(set.getKey());
			Collections.sort(li);
			reversedCount.put(set.getValue(), li);	

			// ArrayList<String> 
		}
		
		
		return count.entrySet().stream().max((entry1, entry2) -> entry1.getValue() >= entry2.getValue() ? 1 : -1).get().getKey();
	}

	@Override
	public String getWorstSecretKeeper() {
		HashMap<String, Integer> wasSharedcount = new HashMap<>();
		HashSet<String> users = new HashSet<>();
		lookup1.forEach((K, V) -> {
			V.forEach((k, v) -> {
				v.forEach((val) -> {
					users.add(val);
					wasSharedcount.put(val, wasSharedcount.getOrDefault(val, 0) + 1);
				});
			});
		});
		//System.out.println(wasSharedcount +"\n==============================\n" );
		HashMap<String, Integer> didSharecount = new HashMap<>();
		lookup1.forEach((K, V) -> {
			V.forEach((k, v) -> {
				users.add(k);				
				didSharecount.put(k, didSharecount.getOrDefault(k, 0) + v.size());
			});
		});

		//System.out.println(didSharecount +"\n==============================\n" + users);

		HashMap<Integer , ArrayList<String>> netShare = new HashMap<>();

		for(String user : users){
			int was = wasSharedcount.getOrDefault(user, 0);
			int did = didSharecount.getOrDefault(user, 0);
			ArrayList<String> l = netShare.get(was - did);
			if (l == null)
				 l= new ArrayList<String>();
			l.add(user);
			Collections.sort(l);
			netShare.put(was-did , l);
		}
		//System.out.println("**"+netShare);
		if (netShare.isEmpty()) return null;
		int min = Collections.min(netShare.keySet());
		return netShare.get(min).get(0);
	}

	@Override
	public String getBestKnownSecret() {

		if (readDS.isEmpty()) 
			return null;
		//System.out.println("+++++++\n" + readDS + "+++++++") ;
		readDS.forEach((k,v) -> {
			//System.out.println(secrets.get(k) + " :: " + v);
		});

		HashMap<UUID, Integer> count = new HashMap<>();

		readDS.forEach((K, V) -> {
			count.put(K,V.size());
		});


		// System.out.println(count);
		
		HashMap<Integer, ArrayList<String>> reversedCount = new HashMap<>();

		for (Entry<UUID, Integer> set : count.entrySet()) {
			ArrayList<String> l = new ArrayList<String>();

			// if (reversedCount.get(set.getValue()))

			ArrayList<String> li = reversedCount.getOrDefault(set.getValue(), l );
			
			li.add(secrets.get(set.getKey()));
			Collections.sort(li);
			reversedCount.put(
				set.getValue(), 
				li);	

			// ArrayList<String> 
		}
		// System.out.println(reversedCount);
		

		return secrets.get(count.entrySet().stream().max((entry1, entry2) -> entry1.getValue() >= entry2.getValue() ? 1 : -1).get().getKey());

		// return null;
	}
    
}



