package org.to2mbn.lolixl.ui.impl.container.presenter.panel.settings;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.felix.scr.annotations.Activate;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.to2mbn.lolixl.core.config.ConfigurationCategory;
import org.to2mbn.lolixl.ui.ConfigurationCategoryViewManager;
import org.to2mbn.lolixl.ui.model.ConfigurationCategoryViewProvider;
import org.to2mbn.lolixl.utils.LambdaServiceTracker;
import org.to2mbn.lolixl.utils.ObservableServiceTracker;
import org.to2mbn.lolixl.utils.ServiceUtils;
import javafx.collections.ObservableList;

public class ConfigurationCategoryViewManagerImpl implements ConfigurationCategoryViewManager {

	private Set<String> categoriesTagNames = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private ObservableServiceTracker<ConfigurationCategoryViewProvider> viewProvidersTracker;
	@SuppressWarnings("rawtypes")
	private LambdaServiceTracker<ConfigurationCategory> categoriesTracker;
	private BundleContext bundleContext;

	@Activate
	public void active(ComponentContext compCtx) {
		bundleContext = compCtx.getBundleContext();

		categoriesTracker = new LambdaServiceTracker<>(bundleContext, ConfigurationCategory.class)
				.whenAdding((ref, service) -> {
					categoriesTagNames.add(ServiceUtils.getIdProperty(ConfigurationCategory.PROPERTY_CATEGORY, ref, service));
					viewProvidersTracker.updateTrackedList();
				})
				.whenRemoving((ref, service) -> {
					categoriesTagNames.remove(ServiceUtils.getIdProperty(ConfigurationCategory.PROPERTY_CATEGORY, ref, service));
					viewProvidersTracker.updateTrackedList();
				});

		viewProvidersTracker = new ObservableServiceTracker<>(bundleContext, ConfigurationCategoryViewProvider.class,
				stream -> stream
						.map(ref -> {
							ConfigurationCategoryViewProvider service = viewProvidersTracker.getService(ref);
							return categoriesTagNames.contains(ServiceUtils.getIdProperty(ConfigurationCategoryViewProvider.PROPERTY_CATEGORY, ref, service))
									? service : null;
						})
						.filter(Objects::nonNull));

		categoriesTracker.open(true);
		viewProvidersTracker.open(true);
	}

	@Override
	public ObservableList<ConfigurationCategoryViewProvider> getProviders() {
		return viewProvidersTracker.getServiceList();
	}

}