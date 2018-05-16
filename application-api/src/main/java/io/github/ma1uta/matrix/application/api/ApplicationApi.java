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

package io.github.ma1uta.matrix.application.api;

import io.github.ma1uta.matrix.EmptyResponse;
import io.github.ma1uta.matrix.application.model.TransactionRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * This contains application service APIs which are used by the homeserver. All application services MUST implement these APIs.
 * These APIs are defined below.
 * <p/>
 * <a href="https://matrix.org/docs/spec/application_service/unstable.html#id7">Specification.</a>
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ApplicationApi {

    /**
     * This API is called by the HS when the HS wants to push an event (or batch of events) to the AS.
     *
     * @param txnId           Required. The transaction ID for this set of events. Homeservers generate these IDs and they are used to
     *                        ensure idempotency of requests.
     * @param request         JSON body request.
     * @param servletRequest  servlet request.
     * @param servletResponse servlet response.
     * @return Status code 200: The transaction was processed successfully.
     */
    @PUT
    @Path("/transactions/{txnId}")
    EmptyResponse transaction(@PathParam("txnId") String txnId, TransactionRequest request, @Context HttpServletRequest servletRequest,
                              @Context HttpServletResponse servletResponse);

    /**
     * This endpoint is invoked by the homeserver on an application service to query the existence of a given room alias.
     * The homeserver will only query room aliases inside the application service's aliases namespace. The homeserver will
     * send this request when it receives a request to join a room alias within the application service's namespace.
     *
     * @param roomAlias       Required. The room alias being queried.
     * @param servletRequest  servlet request.
     * @param servletResponse servlet response.
     * @return Status code 200: The application service indicates that this room alias exists. The application service MUST have
     *     created a room and associated it with the queried room alias using the client-server API. Additional information
     *     about the room such as its name and topic can be set before responding.
     *     <p/>
     *     Status code 401: The homeserver has not supplied credentials to the application service. Optional error information can
     *     be included in the body of this response.
     *     <p/>
     *     Status code 403: The credentials supplied by the homeserver were rejected.
     *     <p/>
     *     Status code 404: The application service indicates that this room alias does not exist. Optional error information can
     *     be included in the body of this response.
     */
    @GET
    @Path("/rooms/{roomAlias}")
    EmptyResponse rooms(@PathParam("roomAlias") String roomAlias, @Context HttpServletRequest servletRequest,
                        @Context HttpServletResponse servletResponse);

    /**
     * This endpoint is invoked by the homeserver on an application service to query the existence of a given user ID.
     * The homeserver will only query user IDs inside the application service's users namespace. The homeserver will
     * send this request when it receives an event for an unknown user ID in the application service's namespace.
     *
     * @param userId          Required. The user ID being queried.
     * @param servletRequest  servlet request.
     * @param servletResponse servlet response.
     * @return Status code 200: The application service indicates that this user exists. The application service MUST create
     *     the user using the client-server API.
     *     <p/>
     *     Status code 401: The homeserver has not supplied credentials to the application service. Optional error information
     *     can be included in the body of this response.
     *     <p/>
     *     Status code 403: The credentials supplied by the homeserver were rejected.
     *     <p/>
     *     Status code 404: The application service indicates that this user does not exist. Optional error information can be
     *     included in the body of this response.
     */
    @GET
    @Path("/users/{userId}")
    EmptyResponse users(@PathParam("userId") String userId, @Context HttpServletRequest servletRequest,
                        @Context HttpServletResponse servletResponse);
}
