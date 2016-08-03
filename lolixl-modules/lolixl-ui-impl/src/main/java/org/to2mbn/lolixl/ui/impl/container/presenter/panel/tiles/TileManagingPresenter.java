package org.to2mbn.lolixl.ui.impl.container.presenter.panel.tiles;

import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.service.component.ComponentContext;
import org.to2mbn.lolixl.i18n.I18N;
import org.to2mbn.lolixl.ui.Panel;
import org.to2mbn.lolixl.ui.PanelDisplayService;
import org.to2mbn.lolixl.ui.SideBarTileService;
import org.to2mbn.lolixl.ui.SideBarTileService.StackingStatus;
import org.to2mbn.lolixl.ui.component.Tile;
import org.to2mbn.lolixl.ui.container.presenter.Presenter;
import org.to2mbn.lolixl.ui.impl.container.presenter.HomeContentPresenter;
import org.to2mbn.lolixl.ui.impl.container.view.panel.tils.TileManagingView;
import org.to2mbn.lolixl.ui.model.DisplayableTile;
import org.to2mbn.lolixl.ui.model.SidebarTileElement;
import org.to2mbn.lolixl.utils.MappedObservableList;

@Component(immediate = true)
public class TileManagingPresenter extends Presenter<TileManagingView> implements DisplayableTile {

	private static final String FXML_LOCATION = "/ui/fxml/panel/tile_managing_panel.fxml";

	@Reference
	private HomeContentPresenter homeContentPresenter;

	@Reference
	private PanelDisplayService panelDisplayService;

	@Reference
	private SideBarTileService tileService;

	private MappedObservableList<SidebarTileElement, Tile> tilesMapping;

	@Activate
	public void active(ComponentContext compCtx) {
		super.active();
	}

	@Override
	protected void initializePresenter() {
		tilesMapping = new MappedObservableList<>(tileService.getTiles(StackingStatus.SHOWN, StackingStatus.HIDDEN),
				SidebarTileElement::createTile);

		view.upButton.setOnAction(this::onUpButtonClicked);
		view.downButton.setOnAction(this::onDownButtonClicked);

		view.listView.setItems(tilesMapping);

		bindManagementTile();
	}

	@Override
	protected String getFxmlLocation() {
		return FXML_LOCATION;
	}

	private void onUpButtonClicked(ActionEvent event) {
		Tile selectedTile = view.listView.getSelectionModel().getSelectedItem();
		moveTile(selectedTile, -1);
	}

	private void onDownButtonClicked(ActionEvent event) {
		Tile selectedTile = view.listView.getSelectionModel().getSelectedItem();
		moveTile(selectedTile, 1);
	}

	private void moveTile(Tile tile, int offset) {
		SidebarTileElement entry = tilesMapping.inverseMapping().get(tile);
		if (entry != null) {
			tileService.moveTile(entry, offset);
		}
	}

	@Override
	public ObservableStringValue getLocalizedName() {
		return I18N.localize("org.to2mbn.lolixl.ui.impl.container.tiles.management.title");
	}

	private void bindManagementTile() {
		Tile tile = DisplayableTile.super.createTile();
		Panel panel = panelDisplayService.newPanel();
		panel.bindButton(tile);
		panel.bindItem(this);
		homeContentPresenter.setManagementTile(tile);
	}

}
