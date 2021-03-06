package co.earcos.filesync.view;


import co.earcos.filesync.model.SyncFile;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.CheckBoxTree;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.tree.TreeSelectionModel;

public class FileTreeDemo {

  public static void main(String[] args) {
    LookAndFeelFactory.installJideExtension(LookAndFeelFactory.EXTENSION_STYLE_XERTO);
//    JFrame.setDefaultLookAndFeelDecorated(true);
//    LookAndFeelFactory.installDefaultLookAndFeelAndExtension();

    // Figure out where in the filesystem to start displaying
    File root;
    if (args.length > 0) {
      root = new File(args[0]);
    } else {
      root = new File("D:/ErikArcos/Musica");
    }

    // Create a TreeModel object to represent our tree of files
    SyncFile syncFileRoot = new SyncFile(root);
    SyncFileTreeModel model = new SyncFileTreeModel(syncFileRoot);
    CheckBoxTree checkBoxTree = new CheckBoxTree(model);
    checkBoxTree.getCheckBoxTreeSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
    checkBoxTree.getCheckBoxTreeSelectionModel().setDigIn(true);
    checkBoxTree.setSelectPartialOnToggling(true);

    // The JTree can get big, so allow it to scroll
    JScrollPane scrollpane = new JScrollPane(checkBoxTree);

    // Display it all in a window and make the window appear
    JFrame frame = new JFrame("FileTreeDemo");
    frame.getContentPane().add(scrollpane, "Center");
    frame.setSize(400, 600);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}