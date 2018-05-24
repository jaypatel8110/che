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

import org.eclipse.che.ide.api.parts.PartStack;

/**
 * The factory which creates instances of {@link PartStackProperties}.
 *
 * @author Roman Nikitenko
 */
public interface PartStackPropertiesFactory {

  /**
   * Creates implementation of {@link PartStackProperties}.
   *
   * @param partStack related Part Stack
   * @return an instance of {@link PartStackProperties}
   */
  PartStackProperties create(PartStack partStack);
}
