package com.example.rest;

import org.apache.http.client.methods.HttpGet;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

import static com.example.rest.RestClient.execute;
import static com.example.rest.RestClient.normalize;

/**
 * @version $Revision$ $Date$
 */
@RunWith(Arquillian.class)
public class RestTest {

    @ArquillianResource
    private URL webappUrl;

    @Deployment
    public static WebArchive archive() {
        final WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "com.example");

        // Recursively add all the src/main/webapp content
        war.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                .importDirectory("src/main/webapp").as(GenericArchive.class),
                "/", Filters.includeAll());

        // Print so we can see what we have in the war
        System.out.println(war.toString(true));
        return war;
    }

    @Test
    public void testGetJSON() throws Exception {

        final HttpGet get = new HttpGet(normalize(webappUrl.toURI(), "rest"));
        get.setHeader("Accept", "application/json");

        final String content = execute(get);

        Assert.assertEquals("{\"movie\":[]}", content);
    }

    @Test
    public void testGetXml() throws Exception {

        final HttpGet get = new HttpGet(normalize(webappUrl.toURI(), "rest"));
        get.setHeader("Accept", "text/xml");

        final String content = execute(get);

        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><movies></movies>", content);
    }
}
