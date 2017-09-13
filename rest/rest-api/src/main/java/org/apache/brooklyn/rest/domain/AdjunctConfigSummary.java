/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.brooklyn.rest.domain;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

import org.apache.brooklyn.config.ConfigKey;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

public class AdjunctConfigSummary extends ConfigSummary {

    private static final long serialVersionUID = 4339330833863794513L;

    @JsonInclude(Include.NON_NULL)
    private final Map<String, URI> links;

    // json deserialization
    AdjunctConfigSummary() {
        links = null;
    }

    public AdjunctConfigSummary(
            @JsonProperty("name") String name,
            @JsonProperty("type") String type,
            @JsonProperty("description") String description,
            @JsonProperty("defaultValue") Object defaultValue,
            @JsonProperty("reconfigurable") boolean reconfigurable,
            @JsonProperty("links") Map<String, URI> links) {
        super(name, type, description, defaultValue, reconfigurable, null, null, null);
        this.links = (links == null) ? ImmutableMap.<String, URI>of() : ImmutableMap.copyOf(links);
    }

    public AdjunctConfigSummary(ConfigKey<?> config, String label, Double priority, Map<String, URI> links) {
        super(config, label, priority);
        this.links = links != null ? ImmutableMap.copyOf(links) : null;
    }

    @Override
    public Map<String, URI> getLinks() {
        return links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdjunctConfigSummary)) return false;
        if (!super.equals(o)) return false;
        AdjunctConfigSummary that = (AdjunctConfigSummary) o;
        return Objects.equals(links, that.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), links);
    }

    @Override
    public String toString() {
        return "EnricherConfigSummary{" +
                "links=" + links +
                '}';
    }
}
