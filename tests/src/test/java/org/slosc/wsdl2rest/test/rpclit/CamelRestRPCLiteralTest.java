/*
 * #%L
 * Wildfly Camel :: Testsuite
 * %%
 * Copyright (C) 2013 - 2014 RedHat
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package org.slosc.wsdl2rest.test.rpclit;

import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.apache.camel.CamelContext;
import org.apache.camel.ServiceStatus;
import org.junit.Assert;
import org.junit.Test;
import org.slosc.wsdl2rest.util.SpringCamelContextFactory;

public class CamelRestRPCLiteralTest {

    @Test
    public void testSpringContextFromURL() throws Exception {
        URL resourceUrl = getClass().getResource("/rpclit/rpclit-camel-context.xml");
        CamelContext camelctx = SpringCamelContextFactory.createSingleCamelContext(resourceUrl, null);
        camelctx.start();
        try {
            Assert.assertEquals(ServiceStatus.Started, camelctx.getStatus());
            
            Client client = ClientBuilder.newClient();
            Object result = client.target("http://localhost:8080/rpclit/addressservice/address/Kermit").request(MediaType.APPLICATION_JSON).get(String.class);
            Assert.assertEquals("Hello Kermit", result);
        } finally {
            camelctx.stop();
        }
    }

}
