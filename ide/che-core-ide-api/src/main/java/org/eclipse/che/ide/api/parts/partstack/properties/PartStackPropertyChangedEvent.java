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

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import org.eclipse.che.ide.api.parts.PartStack;

/**
 * Is fired when a Part Stack property is changed.
 *
 * @author Roman Nikitenko
 */
public class PartStackPropertyChangedEvent extends GwtEvent<PartStackPropertyChangedEvent.Handler> {

  /** Implement to handle changing a Part Stack property. */
  public interface Handler extends EventHandler {
    void onPartStackPropertyChanged(PartStackPropertyChangedEvent event);
  }

  public static final Type<Handler> TYPE = new Type<Handler>();

  private final PartStack partStack;
  private final String propertyId;

  public PartStackPropertyChangedEvent(PartStack partStack, String propertyId) {
    this.partStack = partStack;
    this.propertyId = propertyId;
  }

  public PartStack getPartStack() {
    return partStack;
  }

  public String getPropertyId() {
    return propertyId;
  }

  @Override
  public Type<Handler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(Handler handler) {
    handler.onPartStackPropertyChanged(this);
  }
}
