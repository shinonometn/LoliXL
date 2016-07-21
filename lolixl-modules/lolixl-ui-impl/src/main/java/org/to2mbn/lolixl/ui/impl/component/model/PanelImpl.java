package org.to2mbn.lolixl.ui.impl.component.model;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import org.to2mbn.lolixl.ui.Panel;

import java.util.function.Consumer;

public class PanelImpl implements Panel {
	private Image icon;
	private String title;
	private Runnable hideOperation = () -> {
	};
	private Parent content;

	private final Consumer<Panel> onShow;
	private final Runnable onClose;

	public PanelImpl(Consumer<Panel> _onShow, Runnable _onClose) {
		onShow = _onShow;
		onClose = _onClose;
	}

	@Override
	public Image getIcon() {
		return icon;
	}

	@Override
	public void setIcon(Image _icon) {
		icon = _icon;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String _title) {
		title = _title;
	}

	@Override
	public Runnable getHideOperation() {
		return hideOperation;
	}

	@Override
	public void setHideOperation(Runnable _hideOperation) {
		hideOperation = _hideOperation;
	}

	@Override
	public Parent getContent() {
		return content;
	}

	@Override
	public void setContent(Parent _content) {
		content = _content;
	}

	@Override
	public void show() {
		onShow.accept(this);
	}

	@Override
	public void hide() {
		hideOperation.run();
		onClose.run();
	}
}