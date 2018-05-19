/*
 * Copyright sablintolya@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ma1uta.matrix.identity.api;

import io.github.ma1uta.matrix.identity.model.validation.PublishResponse;
import io.github.ma1uta.matrix.identity.model.validation.ValidationResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Checking non-published 3pid ownership.
 *
 * @author ma1uta.
 */
@Path("/_matrix/identity/api/v1/3pid")
public interface ValidationApi {

    /**
     * Check whether ownership of a 3pid was validated.
     *
     * @param sid             session id.
     * @param clientSecret    client secret from the requestToken call.
     * @param servletRequest  servlet request.
     * @param servletResponse servlet response.
     * @return validation data.
     */
    @GET
    @Path("/getValidated3pid")
    @Produces(MediaType.APPLICATION_JSON)
    ValidationResponse validate(@QueryParam("sid") String sid, @QueryParam("client_secret") String clientSecret,
                                @Context HttpServletRequest servletRequest, @Context HttpServletResponse servletResponse);

    /**
     * Publishing a validated association.
     *
     * @param sid             session id.
     * @param clientSecret    client secret from the requestToken call.
     * @param mxid            matrix id.
     * @param servletRequest  servlet request.
     * @param servletResponse servlet response.
     * @return publish result.
     */
    @POST
    @Path("/bind")
    @Produces(MediaType.APPLICATION_JSON)
    PublishResponse publish(@FormParam("sid") String sid, @FormParam("client_secret") String clientSecret, @FormParam("mxid") String mxid,
                            @Context HttpServletRequest servletRequest, @Context HttpServletResponse servletResponse);
}
