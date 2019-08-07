/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viii28stw.pensiltikfrontend.util.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author herudi-pc
 */
public class Config {

    private ApplicationContext applicationContext;
    private static Config provider;

    public Config() throws ExceptionInInitializerError {
        try {
            this.applicationContext = new ClassPathXmlApplicationContext("applicationContex.xml");
        } catch (BeansException ex) {
            System.err.print("error " + ex);
        }
    }

    public synchronized static Config getInstance() throws ExceptionInInitializerError {
        Config tempProvider;
        if (provider == null) {
            provider = new Config();
            tempProvider = provider;
        } else if (provider.getApplicationContext() == null) {
            provider = new Config();
            tempProvider = provider;
        } else {
            tempProvider = provider;
        }

        return tempProvider;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
