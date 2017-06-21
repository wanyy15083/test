package com.test.schedule;

import junit.framework.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

/**
 * Created by songyigui on 2017/6/21.
 */
public class TestScheduleExecutor {
    private static final Logger LOGGER = LogManager.getLogger(RemoteFileFetcher.class);


    public static void main(String[] args) throws Exception {
        String input = "12test32";
        ForbiddenWordUtils.setForbiddenWordFetchURL("http://localhost:8089/forbidden-test.txt");
        ForbiddenWordUtils.setReloadInterval(1000);
        ForbiddenWordUtils.initRemoteFetch();

        Server server = new Server(8089);
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setBaseResource(Resource.newClassPathResource("."));
        server.setHandler(resourceHandler);
        server.start();

        Thread.sleep(1500);

        Assert.assertTrue(ForbiddenWordUtils.containsForbiddenWord(input));

        server.stop();

    }

}
