/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.ide.api.parts.partstack.properties;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.google.web.bindery.event.shared.EventBus;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.che.ide.api.parts.PartStack;

/**
 * @see PartStackProperties
 * @author Roman Nikitenko
 */
public class PartStackPropertiesImpl implements PartStackProperties {
  private Map<String, String> properties = new HashMap<>();

  private final EventBus eventBus;
  private final PartStack partStack;

  @AssistedInject
  public PartStackPropertiesImpl(@Assisted PartStack partStack, EventBus eventBus) {
    this.partStack = partStack;
    this.eventBus = eventBus;
  }

  @Override
  public void addProperty(String propertyId, String value) {
    properties.put(propertyId, value);

    eventBus.fireEvent(new PartStackPropertyChangedEvent(partStack, propertyId));
  }

  @Override
  public void removeProperty(String propertyId) {
    properties.remove(propertyId);

    eventBus.fireEvent(new PartStackPropertyChangedEvent(partStack, propertyId));
  }

  @Override
  public boolean hasProperty(String propertyId) {
    return properties.containsKey(propertyId);
  }

  @Override
  public String getValue(String propertyId) {
    return properties.get(propertyId);
  }
}
