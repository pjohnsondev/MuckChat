package muck.client;

import muck.client.controllers.SignInController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignInTest {

        private static final Logger logger = LogManager.getLogger(SignInTest.class);

        @BeforeAll
        public static void beforeAll(){
            logger.info("Run this code 'before all' setup");
        }

        @BeforeEach
        public void beforeEach(){
            logger.info("Run this code 'before each' setup");
        }

        @Test
        // TODO: check if Sign in was valid
        public void testSignInValidity(){
            SignInController user = new SignInController();
            assertEquals(true, user.validateSignIn("username", "password"));
        }

        @Test
        // TODO: check if Fields are empty
        public void testIsNotEmpty(){
            SignInController user = new SignInController();
            assertEquals(true, user.isNotEmpty("username", "password"));
        }




    }

