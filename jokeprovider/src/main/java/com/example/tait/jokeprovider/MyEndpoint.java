/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.tait.jokeprovider;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "jokeprovider.tait.example.com",
                ownerName = "jokeprovider.tait.example.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    //gets a joke and tells it to you.
    @ApiMethod(name = "tellJoke")
    public MyBean tellJoke(@Named("joke") String joke) {
        MyBean response = new MyBean();
        response.setData(joke);
        return response;
    }
}
