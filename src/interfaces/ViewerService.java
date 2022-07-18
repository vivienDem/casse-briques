package interfaces;

import javafx.scene.Parent;

public interface ViewerService {
	public void init();
	public Parent getPanel();
	public void setMainWindowWidth(double w);
	public void setMainWindowHeight(double h);
}
