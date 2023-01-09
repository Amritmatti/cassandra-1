/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.cassandra.simulator.logging;

import ch.qos.logback.core.PropertyDefinerBase;

public class SeedDefiner extends PropertyDefinerBase
{
    private static final String PROPERTY = "cassandra.simulator.seed";

    public static void setSeed(long seed)
    {
        System.setProperty(PROPERTY, "0x" + Long.toHexString(seed));
    }

    @Override
    public String getPropertyValue()
    {
        if (System.getProperty(PROPERTY) == null)
        {
            System.err.println("SeedDefiner is being called before the seed has been set, check static init order");
            System.setProperty(PROPERTY, "<undefined>");
        }
        return System.getProperty(PROPERTY);
    }
}