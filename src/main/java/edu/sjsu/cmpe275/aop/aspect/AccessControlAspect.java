package edu.sjsu.cmpe275.aop.aspect;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.NotAuthorizedException;
import edu.sjsu.cmpe275.aop.SecretStatsImpl;

@Aspect
@Order(1)
public class AccessControlAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */
	
	
	@Autowired SecretStatsImpl stats;
	
	public boolean checkPath(UUID secretId, String userId) {
		String owner = stats.creator.get(secretId);
		HashMap<String, HashSet<String>> network = stats.lookup.get(secretId);

		for(int i = 0; i < network.size(); i++) {
			for(String key : network.keySet()) {
				for(String s : network.get(key)) {
					if(s.equals(userId)) {
						userId = key;
						if(userId.equals(owner))
							return true;
					}
						
				}
			}
		}
		
		if(userId.equals(owner))
			return true;
		return false;
		
	}
	
	
	
	@Before("execution(public * edu.sjsu.cmpe275.aop.SecretService.readSecret(..)) &&args(userId,secretId)")
	public void readAdvice(JoinPoint joinPoint, String userId, UUID secretId ) {
		
			if(!stats.lookup.containsKey(secretId))
				throw new NotAuthorizedException("SECRET DOES NOT EXIST");
			if (stats.creator.get(secretId) != userId)
				if (!checkPath(secretId, userId))
					throw new NotAuthorizedException("SECRET IS NOT SHARED WITH YOU");
			
			// System.out.println("@READ SECRET " + userId + " " + secretId);
			
		// System.out.printf("- Access control prior to the executuion of the method %s\n", joinPoint.getSignature().getName());
		
	}
	
	@Before("execution(public * edu.sjsu.cmpe275.aop.SecretService.shareSecret(..)) &&args(userId,secretId,targetUserId)")
	public void shareAdvice(JoinPoint joinPoint, String userId , UUID secretId, String targetUserId) {
			//System.out.println(userId +" "+ targetUserId + " "+ stats.lookup+ " checkPath : "+ checkPath(secretId,userId));
			
			if(!stats.lookup.containsKey(secretId) || !checkPath(secretId, userId))
				throw new NotAuthorizedException("Not Authorized to Share");
			
			HashMap<String, HashSet<String>> network = stats.lookup.get(secretId);
			HashMap<String, HashSet<String>> network1 = stats.lookup1.get(secretId);
			HashSet<String> set = network.getOrDefault(userId, new HashSet<String>());
			HashSet<String> set1 = network1.getOrDefault(userId, new HashSet<String>());
			set.add(targetUserId);
			set1.add(targetUserId);
			network.put(userId, set);
			network1.put(userId, set1);
			stats.lookup.put(secretId , network);
			stats.lookup1.put(secretId , network1);
			// System.out.println("\n");
			// stats.printLooUp();
			// System.out.println("\n");
			
		// System.out.printf("- Access control prior to the executuion of the method %s\n", joinPoint.getSignature().getName());
		
	}
	@Before("execution(public * edu.sjsu.cmpe275.aop.SecretService.unshareSecret(..)) && args(userId,secretId,targetUserId)")
	public void unshareSecretdAdvice(JoinPoint joinPoint,String userId, UUID secretId, String targetUserId) {
		
		
			if(!stats.lookup.containsKey(secretId))
				throw new NotAuthorizedException();
			
			
			
			if(stats.lookup.get(secretId).containsKey(userId)) {
				HashSet<String> set = stats.lookup.get(secretId).get(userId);
				

				if(!set.remove(targetUserId))
					throw new NotAuthorizedException("Secret is never shared with target or was already unshared");
				
				
				
				// System.out.println("\n");
				// stats.printLooUp();
				// System.out.println("\n");
			} else 
				throw new NotAuthorizedException("You don't have right's");
			
			
			
		// System.out.printf("- Access control prior to the executuion of the method %s\n", joinPoint.getSignature().getName());
		
	}

	@After("execution(public * edu.sjsu.cmpe275.aop.SecretService.readSecret(..)) &&args(userId,secretId)")
	public void AfterreadAdvice(JoinPoint joinPoint, String userId, UUID secretId ) {
		
			if (!stats.creator.get(secretId).equals(userId)){
				HashSet<String> init = new HashSet<>();
				HashSet<String> output = stats.readDS.getOrDefault(secretId, init);
				output.add(userId);
				stats.readDS.put(secretId, output);
			}
		
	}

}


