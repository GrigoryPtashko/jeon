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

package io.github.ma1uta.mxtoot.matrix.command;

import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException;
import com.sys1yagi.mastodon4j.api.method.Statuses;
import io.github.ma1uta.matrix.Event;
import io.github.ma1uta.matrix.bot.BotHolder;
import io.github.ma1uta.matrix.client.EventMethods;
import io.github.ma1uta.mxtoot.mastodon.MxMastodonClient;
import io.github.ma1uta.mxtoot.matrix.MxTootConfig;
import io.github.ma1uta.mxtoot.matrix.MxTootDao;
import io.github.ma1uta.mxtoot.matrix.MxTootPersistentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Boost.
 */
public class Boost extends AbstractStatusCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(Boost.class);

    @Override
    public String name() {
        return "boost";
    }

    @Override
    public void invoke(BotHolder<MxTootConfig, MxTootDao, MxTootPersistentService<MxTootDao>, MxMastodonClient> holder, Event event,
                       String arguments) {
        MxTootConfig config = holder.getConfig();
        EventMethods eventMethods = holder.getMatrixClient().event();

        if (!initMastodonClient(holder)) {
            return;
        }

        if (arguments == null || arguments.trim().isEmpty()) {
            eventMethods.sendNotice(config.getRoomId(), "Usage: " + usage());
            return;
        }

        String trimmed = arguments.trim();

        Long statusId;
        try {
            statusId = Long.parseLong(trimmed);
        } catch (NumberFormatException e) {
            LOGGER.error("Wrong status id", e);
            eventMethods.sendNotice(config.getRoomId(), "Status id is not a number.\nUsage: " + usage());
            return;
        }

        try {
            new Statuses(holder.getData().getMastodonClient()).postReblog(statusId).execute();
        } catch (Mastodon4jRequestException e) {
            LOGGER.error("Cannot toot", e);
            eventMethods.sendNotice(config.getRoomId(), "Cannot boost: " + e.getMessage());
        }
    }

    @Override
    public String help() {
        return "boost a status.";
    }

    @Override
    public String usage() {
        return "boost <status_id>";
    }
}
