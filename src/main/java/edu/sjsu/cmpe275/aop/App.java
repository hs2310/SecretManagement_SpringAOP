package edu.sjsu.cmpe275.aop;

import java.util.UUID;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        /***
         * Following is a dummy implementation of App to demonstrate bean creation with Application context.
         * You may make changes to suit your need, but this file is NOT part of your submission.
         */

    	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        SecretService secretService = (SecretService) ctx.getBean("secretService");
        SecretStats stats = (SecretStats) ctx.getBean("secretStats");

        try {
        	
            /**
             * TEST 1 : PASSED
             */
            UUID secret = secretService.createSecret("Alice", "My little secret");
            secretService.shareSecret("Alice", secret, "Bob");
            secretService.shareSecret("Bob", secret, "Cat");
            secretService.unshareSecret("Alice", secret, "Cat");

            /**
             * TEST 2 : PASSED 
             */
            // UUID secret = secretService.createSecret("Alice", "My little secret");
            // secretService.shareSecret("Alice", secret, "Bob");
            // secretService.shareSecret("Bob", secret, "Alice");
            
            /**
             * TEST 3 : PASSED
             */
            // UUID secret = secretService.createSecret("Alice", "My little secret");
            // secretService.shareSecret("Alice", secret, "Bob");
            // secretService.shareSecret("Bob", secret, "Alice");
            // secretService.unshareSecret("Bob", secret, "Alice");
            // secretService.readSecret("Alice", secret);

            /**
             * TEST 4 : PASSED
             */
            // UUID secret = secretService.createSecret("Alice", "My little secret");
            // secretService.shareSecret("Alice", secret, "Bob");
            // secretService.shareSecret("Bob", secret, "Charlie");
            // secretService.shareSecret("Alice", secret, "Charlie");
            // secretService.unshareSecret("Bob", secret, "Charlie");
            // secretService.readSecret("Charlie", secret);

            
            /**
             * TEST 5 : PASSED
             */
            // UUID secret = secretService.createSecret("Alice", "My little secret");
            // UUID secret1 = secretService.createSecret("Bob", "My little secret");
            // secretService.shareSecret("Alice", secret, "Bob");
            // secretService.shareSecret("Alice", secret1, "Charlie");            
            
            /**
             * TEST 6 : PASSED
             */
            // UUID secret = secretService.createSecret("Alice", "My little secret");
            // secretService.readSecret("Bob", secret);

            /**
             * TEST 7 : PASSED
             */
            // UUID secret = secretService.createSecret("Alice", "My little secret");
            // secretService.shareSecret("Alice", secret, "Bob");
            // secretService.readSecret("Bob", secret);

            /**
             * TEST 8 : PASSED
             */
            // UUID secret = secretService.createSecret("Alice", "My little secret");
            // secretService.shareSecret("Alice", secret, "Bob");
            // secretService.shareSecret("Bob", secret, "Charlie");            
            // secretService.readSecret("Charlie", secret);

            /**
             * TEST 9 : PASSED
             */
            // UUID secret = secretService.createSecret("Alice", "My little secret");
            // secretService.shareSecret("Alice", secret, "Bob");
            // secretService.shareSecret("Bob", secret, "Charlie");            
            // secretService.unshareSecret("Alice", secret, "Bob");
            // secretService.readSecret("Charlie", secret);
            
            /**
             * TEST 10 : PASSED
             */
            // UUID secret = secretService.createSecret("Alice", "My little secret11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
            
            
            // secretService.shareSecret("Bob", secret, "Dog");
            // secretService.shareSecret("Alice", secret, "Dog");
            // // secretService.shareSecret("Bob", secret, "Dog");
            // secretService.readSecret("Alice", secret);
            // secretService.readSecret("Bob", secret);
            // secretService.readSecret("Cat", secret);
            // secretService.readSecret("Bob", secret);
        	
            // UUID secret2 = secretService.createSecret("Alice", "secret");
            // secretService.shareSecret("Alice", secret2, "Bob");
        	// secretService.shareSecret("Bob", secret2, "Cat");
            // secretService.shareSecret("Bob", secret2, "Dog");
            // secretService.shareSecret("Alice", secret2, "Dog");
            // secretService.readSecret("Alice", secret2);
            // secretService.readSecret("Alice", secret2);
            // secretService.readSecret("Bob", secret2);
            // secretService.readSecret("Cat", secret2);
            // secretService.readSecret("Dog", secret2);
            // secretService.readSecret("Bob", secret2);
       	// stats.resetStatsAndSystem();
//        	secretService.shareSecret("Alice", secret2, "Bob");
//        	secretService.readSecret("Alice", secret2);
//        	secretService.unshareSecret("Alice", secret2, "Bob");
//        	secretService.unshareSecret("Bob", secret2, "Alice");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // stats.resetStatsAndSystem();
        System.out.println("longest Secret length: " + stats.getLengthOfLongestSecret());
        System.out.println("The best known secret: " + stats.getBestKnownSecret());
        System.out.println("The worst secret keeper: " + stats.getWorstSecretKeeper());
        System.out.println("The most trusted user: " + stats.getMostTrustedUser());
        ctx.close();
    }
}
