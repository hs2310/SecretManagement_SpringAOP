package edu.sjsu.cmpe275.aop.aspect;

import java.io.IOException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.core.annotation.Order;



//import edu.sjsu.cmpe275.aop.NotAuthorizedException;

import org.aspectj.lang.annotation.Around;

@Aspect
@Order(1)
public class RetryAspect {
	/***
	 * Following is a dummy implementation of this aspect. You are expected to
	 * provide an actual implementation based on the requirements, including
	 * adding/removing advices as needed.
	 * 
	 * @throws ,
	 */

	
	
	@Around("execution(public * edu.sjsu.cmpe275.aop.SecretService.*(..))")
	public Object retryAdvice(ProceedingJoinPoint joinPoint)
			throws Throwable {
		// System.out.printf("+ Retry aspect prior to the executuion of the method %s\n",joinPoint.getSignature().getName());
		Object result = null;
		try {
			result = joinPoint.proceed();
			// System.out.printf("+ Finished the executuion of the method at 1st try %s with result %s\n",joinPoint.getSignature().getName(), result);
		} catch (IOException e) {
			//System.out.printf("+ 1st time exceution failed retrying again");
			try {
				result = joinPoint.proceed();
				//System.out.printf("+ Finished the executuion of the method at 2nd try %s with result %s\n",joinPoint.getSignature().getName(), result);
			} catch (IOException e1) {
				//System.out.printf("+ 2st time exceution failed retrying again");
				try {
					result = joinPoint.proceed();
					//System.out.printf("+ Finished the executuion of the method at 3rd try %s with result %s\n",joinPoint.getSignature().getName(), result);

				} catch (IOException e2) {
					//System.out.printf("+ Aborted the executuion of the method at 3rd try %s\n ", joinPoint.getSignature().getName());
					throw new IOException("Network has failed system tried for 3 times");

				}
			}
		} 
		return result;
	}

}
