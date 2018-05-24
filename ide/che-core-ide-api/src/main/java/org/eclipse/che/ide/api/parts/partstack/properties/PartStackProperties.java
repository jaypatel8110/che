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

import org.eclipse.che.commons.annotation.Nullable;

/**
 * Holder for related to Part Stack properties.
 *
 * @author Roman Nikitenko
 */
public interface PartStackProperties {

  /**
   * Property ID for hidden state of Part Stack. It is expected string representation of boolean
   * value for the property: {@code true} - when current hidden state is caused by user action or
   * {@code false} otherwise
   */
  String HIDDEN_BY_USER = "HIDDEN_BY_USER";

  /**
   * Add related to Part Stack property
   *
   * @param propertyId ID of the property
   * @param value string representation of the value
   */
  void addProperty(String propertyId, String value);

  /** Removes related to Part Stack property by given ID */
  void removeProperty(String propertyId);

  /** Returns {@code true} when property exists or {@code false} otherwise */
  boolean hasProperty(String propertyId);

  /**
   * Get value of Part Stack property by given ID. Use {@link #hasProperty(String)} to check if
   * property exists
   *
   * @param propertyId ID of the property
   * @return string representation of the value when property is exist or {@code null} otherwise
   */
  @Nullable
  String getValue(String propertyId);
}
