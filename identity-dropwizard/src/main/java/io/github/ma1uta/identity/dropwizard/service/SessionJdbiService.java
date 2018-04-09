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

package io.github.ma1uta.identity.dropwizard.service;

import io.github.ma1uta.identity.configuration.SessionServiceConfiguration;
import io.github.ma1uta.identity.dao.SessionDao;
import io.github.ma1uta.identity.service.AssociationService;
import io.github.ma1uta.identity.service.EmailService;
import io.github.ma1uta.identity.service.InvitationService;
import io.github.ma1uta.identity.service.impl.AbstractSessionService;
import io.github.ma1uta.matrix.identity.model.validation.ValidationResponse;
import org.jdbi.v3.core.Jdbi;

public class SessionJdbiService extends AbstractSessionService {

    private final Jdbi jdbi;

    public SessionJdbiService(EmailService emailService,
                              AssociationService associationService,
                              InvitationService invitationService,
                              SessionServiceConfiguration configuration, Jdbi jdbi) {
        super(emailService, associationService, invitationService, configuration);
        this.jdbi = jdbi;
    }

    public Jdbi getJdbi() {
        return jdbi;
    }

    @Override
    public String create(String clientSecret, String email, Long sendAttempt, String nextLink) {
        return getJdbi().inTransaction(h -> super.createInternal(clientSecret, email, sendAttempt, nextLink, h.attach(SessionDao.class)));
    }

    @Override
    public String validate(String token, String clientSecret, String sid) {
        return getJdbi().inTransaction(h -> super.validateInternal(token, clientSecret, sid, h.attach(SessionDao.class)));
    }

    @Override
    public ValidationResponse getSession(String sid, String clientSecret) {
        return getJdbi().withHandle(handle -> {
            handle.setReadOnly(true);
            return handle.inTransaction(h -> super.getSessionInternal(sid, clientSecret, h.attach(SessionDao.class)));
        });
    }

    @Override
    public boolean publish(String sid, String clientSecret, String mxid) {
        return getJdbi().inTransaction(h -> super.publishInternal(sid, clientSecret, mxid, h.attach(SessionDao.class)));
    }

    @Override
    public void cleanup() {
        getJdbi().useTransaction(h -> super.cleanupInternal(h.attach(SessionDao.class)));
    }
}