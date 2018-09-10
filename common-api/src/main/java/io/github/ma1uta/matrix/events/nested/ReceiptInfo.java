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

package io.github.ma1uta.matrix.events.nested;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * Receipts.
 */
@ApiModel(description = "Receipts.")
public class ReceiptInfo {

    /**
     * A collection of users who have sent m.read receipts for this event.
     */
    @ApiModelProperty(
        name = "m.read",
        value = "A collection of users who have sent m.read receipts for this event."
    )
    @JsonProperty("m.read")
    private Map<String, ReceiptTs> read;

    public Map<String, ReceiptTs> getRead() {
        return read;
    }

    public void setRead(Map<String, ReceiptTs> read) {
        this.read = read;
    }
}