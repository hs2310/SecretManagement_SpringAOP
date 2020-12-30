package edu.sjsu.cmpe275.aop.aspect;

import java.util.HashMap;
import java.util.HashSet;

import java.util.UUID;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.SecretStatsImpl;

@Aspect
@Order(1)
public class ValidationAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */

	@Autowired SecretStatsImpl stats;
	
	@Before("execution(public * edu.sjsu.cmpe275.aop.SecretService.*(..))")
	public void validAdvice(JoinPoint joinPoint){
		for(Object i : joinPoint.getArgs()) {
			if(i == null || i.equals("")) {
				throw new IllegalArgumentException("Argument Cannot be null");
			}	
		}
		
		 switch (joinPoint.getSignature().getName()) {
				case "createSecret" : {	
					String l2 = (String) joinPoint.getArgs()[1];						
					if (l2.length() > 100) 
						throw new IllegalArgumentException("Length of Secret content can't be greater than 100");
					break;
				}
				
				case "shareSecret": {
					String userId = (String) joinPoint.getArgs()[0];
					
					String targetId = (String) joinPoint.getArgs()[2];
					
					if (userId.equals(targetId))
						throw new IllegalArgumentException("Secret cannot be shared with self");
					break;
				}
				
			}
			
		//System.out.printf("Doing validation prior to the executuion of the method %s\n", joinPoint.getSignature().getName());

	}
	
	@AfterReturning(pointcut= "execution( * edu.sjsu.cmpe275.aop.SecretService.createSecret(..))", returning = "retVal")
	public void createAdvice(JoinPoint jp, Object retVal) throws Throwable  {
		
		String sender = (String) jp.getArgs()[0];
		String content = (String) jp.getArgs()[1];
		//System.out.println(sender);

		HashMap<String , HashSet<String>> initial = new HashMap<String , HashSet<String>>();
		HashMap<String , HashSet<String>> initial1 = new HashMap<String , HashSet<String>>();
		initial.put(sender, new HashSet<String>());
		stats.creator.put((UUID)retVal , sender);
		stats.lookup.put((UUID) retVal, initial);
		stats.lookup1.put((UUID) retVal, initial1);
		stats.secrets.put((UUID)retVal, content);
		
		
	}

}
