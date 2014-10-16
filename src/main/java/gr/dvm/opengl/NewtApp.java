package gr.dvm.opengl;

import javax.media.opengl.*;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;

public class NewtApp {

    public static void main(String[] args) {

        GLProfile glp       = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);

        GLWindow window = GLWindow.create(caps);
        window.setSize(300, 300);
        window.setVisible(true);
        window.setTitle("NEWT Window Test");

        window.addWindowListener(new WindowAdapter() {
            public void windowDestroyNotify(WindowEvent e) {
                System.exit(0);
            };
        });
    }
}
