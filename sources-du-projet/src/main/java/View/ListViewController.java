package View;


import javafx.scene.control.ListView;
import utils.Observer;
import utils.Subject;

public class ListViewController{
	Directory dir = new Directory("../rsc");		
	ListView <String > list;

	ListViewController(){
		list = new ListView<String>();
		list.getItems().addAll(this.dir.getListOfFiles());
	}
}
