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
package org.eclipse.che.plugin.pullrequest.client.preference;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.Boolean.parseBoolean;
import static java.lang.String.valueOf;
import static org.eclipse.che.ide.api.parts.PartStackType.TOOLING;
import static org.eclipse.che.ide.api.parts.partstack.properties.PartStackProperties.HIDDEN_BY_USER;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import org.eclipse.che.ide.api.parts.PartStack;
import org.eclipse.che.ide.api.parts.WorkspaceAgent;
import org.eclipse.che.ide.api.parts.partstack.properties.PartStackProperties;
import org.eclipse.che.ide.api.parts.partstack.properties.PartStackPropertyChangedEvent;
import org.eclipse.che.ide.api.preferences.AbstractPreferencePagePresenter;
import org.eclipse.che.ide.api.preferences.PreferencesManager;
import org.eclipse.che.ide.bootstrap.BasicIDEInitializedEvent;
import org.eclipse.che.plugin.pullrequest.client.ContributeMessages;

/**
 * Preference page presenter for the Contribute Part.
 *
 * @author Roman Nikitenko
 */
@Singleton
public class ContributePreferencePresenter extends AbstractPreferencePagePresenter
    implements ContributePreferenceView.ActionDelegate, PartStackPropertyChangedEvent.Handler {
  public static final String ACTIVATE_BY_PROJECT_SELECTION =
      "git.contribute.activate.projectSelection";

  private ContributePreferenceView view;
  private PreferencesManager preferencesManager;

  private PartStack toolingPartStack;
  private boolean isActivateByProjectSelection;

  @Inject
  public ContributePreferencePresenter(
      EventBus eventBus,
      WorkspaceAgent workspaceAgent,
      ContributePreferenceView view,
      ContributeMessages localizationConstants,
      PreferencesManager preferencesManager) {
    super(
        localizationConstants.contributePreferencesTitle(),
        localizationConstants.contributePreferencesCategory());

    this.view = view;
    this.preferencesManager = preferencesManager;
    this.toolingPartStack = workspaceAgent.getPartStack(TOOLING);

    view.setDelegate(this);
    eventBus.addHandler(PartStackPropertyChangedEvent.TYPE, this);
    eventBus.addHandler(BasicIDEInitializedEvent.TYPE, e -> init());
  }

  private void init() {
    String preference = preferencesManager.getValue(ACTIVATE_BY_PROJECT_SELECTION);
    if (isNullOrEmpty(preference)) {
      preference = "true";
      preferencesManager.setValue(ACTIVATE_BY_PROJECT_SELECTION, preference);
    }

    isActivateByProjectSelection = parseBoolean(preference);
  }

  @Override
  public boolean isDirty() {
    String preference = preferencesManager.getValue(ACTIVATE_BY_PROJECT_SELECTION);
    boolean storedValue = isNullOrEmpty(preference) || parseBoolean(preference);

    return isActivateByProjectSelection != storedValue;
  }

  @Override
  public void go(AcceptsOneWidget container) {
    container.setWidget(view);

    view.setActivateByProjectSelection(isActivateByProjectSelection);
  }

  @Override
  public void storeChanges() {
    preferencesManager.setValue(
        ACTIVATE_BY_PROJECT_SELECTION, valueOf(isActivateByProjectSelection));
  }

  @Override
  public void revertChanges() {
    boolean storedValue = parseBoolean(preferencesManager.getValue(ACTIVATE_BY_PROJECT_SELECTION));

    isActivateByProjectSelection = storedValue;
    view.setActivateByProjectSelection(storedValue);
  }

  @Override
  public void onActivateByProjectSelectionChanged(boolean isActivated) {
    isActivateByProjectSelection = isActivated;
    delegate.onDirtyChanged();
  }

  @Override
  public void onPartStackPropertyChanged(PartStackPropertyChangedEvent event) {
    if (!HIDDEN_BY_USER.equals(event.getPropertyId())
        || toolingPartStack == null
        || !toolingPartStack.equals(event.getPartStack())) {
      return;
    }

    PartStackProperties partStackProperties = toolingPartStack.getProperties();
    if (!partStackProperties.hasProperty(HIDDEN_BY_USER)) {
      return;
    }

    boolean isToolingHiddenByUser = parseBoolean(partStackProperties.getValue(HIDDEN_BY_USER));
    if (isToolingHiddenByUser && isActivateByProjectSelection) {
      isActivateByProjectSelection = false;
      view.setActivateByProjectSelection(false);

      preferencesManager.setValue(ACTIVATE_BY_PROJECT_SELECTION, "false");
      preferencesManager.flushPreferences();
    }
  }
}
