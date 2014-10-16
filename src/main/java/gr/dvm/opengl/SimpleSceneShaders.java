package gr.dvm.opengl;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import gr.dvm.opengl.util.LogGL;
import gr.dvm.opengl.shaders.ShaderControl;

public class SimpleSceneShaders implements GLEventListener {

    private static final Logger LOG = LogManager.getLogger(SimpleSceneShaders.class.getName());

    private double theta = 0;
    private double s     = 0;
    private double c     = 0;
    private ShaderControl shader;

    public static void main(String[] args) {
        LOG.info("Shader Main");
        GLProfile glp       = GLProfile.get(GLProfile.GL4);
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas     = new GLCanvas(caps);

        LOG.info("Is GL4 Capable? " + glp.isGL4());
        LogGL.logProfile(glp);


        Frame frame = new Frame("AWT Window Test");
        frame.setSize(300, 300);
        frame.add(canvas);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        canvas.addGLEventListener(new SimpleSceneShaders());

        FPSAnimator animator = new FPSAnimator(canvas, 60);
        //animator.add(canvas);
        animator.start();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        update();
        render(drawable);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        LOG.info("Running Shader Class");
        LogGL.logVendor(drawable);
        GL4 gl = drawable.getGL().getGL4();
        shader = new ShaderControl();
        shader.fsrc = shader.loadShader("/shaders/fragment_shader.txt");
        shader.vsrc = shader.loadShader("/shaders/vertex_shader.txt");
        shader.init(gl);
    }

    @Override 
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    private void update() {
        theta += 0.01;
        s = Math.sin(theta);
        c = Math.cos(theta);
    }

    private void render(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        GL4 gl4 = drawable.getGL().getGL4();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        shader.useShader(gl4);

        gl.glBegin(GL.GL_TRIANGLES);
        gl.glColor3f(1, 0, 0);
        gl.glVertex2d(-c, -c);
        gl.glColor3f(0, 1, 0);
        gl.glVertex2d(0, c);
        gl.glColor3f(0, 0, 1);
        gl.glVertex2d(s, -s);

        shader.dontUseShader(gl4);

        gl.glEnd();
    }
}
