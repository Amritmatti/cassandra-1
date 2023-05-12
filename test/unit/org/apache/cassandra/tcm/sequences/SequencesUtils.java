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

package org.apache.cassandra.tcm.sequences;

import java.util.Random;

import org.apache.cassandra.tcm.Epoch;
import org.apache.cassandra.tcm.ownership.DataPlacements;

public class SequencesUtils
{
    public static LockedRanges lockedRanges(DataPlacements placements, Random random)
    {
        LockedRanges locked = LockedRanges.EMPTY;
        for (int i = 0; i < random.nextInt(10) + 1; i++)
            locked = locked.lock(LockedRanges.keyFor(epoch(random)), affectedRanges(placements, random));
        return locked;
    }

    public static LockedRanges.AffectedRanges affectedRanges(DataPlacements placements, Random random)
    {
        LockedRanges.AffectedRangesBuilder affected = LockedRanges.AffectedRanges.builder();
        placements.asMap().forEach((params, placement) -> {
            placement.reads.replicaGroups().keySet().forEach((range) -> {
                if (random.nextDouble() >= 0.6)
                    affected.add(params, range);
            });
        });
        return affected.build();
    }

    public static Epoch epoch(Random random)
    {
        return Epoch.create(Math.abs(random.nextLong()));
    }

}