/*
 * Copyright 2023 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.reliability.infinispan.proto;

import org.drools.reliability.core.BaseStoredObject;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.types.protobuf.AnySchema;

/**
 * This class is used to store objects in Infinispan using ProtoStream.
 * This class inherits Serializable from BaseStoredObject, but it uses ProtoStream instead of Java serialization.
 */
public class ProtoStreamStoredObject extends BaseStoredObject {

    private final transient Object object;

    private final transient AnySchema.Any protoObject;

    public ProtoStreamStoredObject(Object object, boolean propagated) {
        super(propagated);

        this.object = object;
        this.protoObject = ProtoStreamUtils.toAnySchema(object);
    }

    @ProtoFactory
    public ProtoStreamStoredObject(AnySchema.Any protoObject, boolean propagated) {
        super(propagated);

        this.protoObject = protoObject;
        this.object = ProtoStreamUtils.fromAnySchema(protoObject);
    }

    @ProtoField(value = 1, required = true)
    public AnySchema.Any getProtoObject() {
        return protoObject;
    }

    @Override
    @ProtoField(value = 2, required = true)
    public boolean isPropagated() {
        return propagated;
    }

    @Override
    public Object getObject() {
        return object;
    }

    @Override
    public String toString() {
        return "ProtoStreamStoredObject{" +
                "object=" + object +
                ", propagated=" + propagated +
                '}';
    }
}