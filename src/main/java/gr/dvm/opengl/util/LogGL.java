package gr.dvm.opengl.util;

import javax.media.opengl.GLProfile;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.nativewindow.AbstractGraphicsDevice;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class LogGL {

    private static final Logger LOG = LogManager.getLogger(LogGL.class.getName());

    public static void logProfile(GLProfile glp) {
        LOG.info("Available OpenGL Profiles");

        for (String profile : GLProfile.GL_PROFILE_LIST_ALL) {
            LOG.info("Profile: " + profile);
        }

        AbstractGraphicsDevice dev = GLProfile.getDefaultDevice();
        LOG.info("Device Type:     " + dev.getType());
        LOG.info("Device UniqueID: " + dev.getUniqueID());
        LOG.info("Device UnitID:   " + dev.getUnitID());
    }

    public static void logVendor(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        LOG.info("Chosen GLCaps:    " + drawable.getChosenGLCapabilities());
        LOG.info("INIT GL IS:       " + gl.getClass().getName());
        LOG.info("GL_VENDOR:        " + gl.glGetString(GL.GL_VENDOR));
        LOG.info("GL_RENDERER:      " + gl.glGetString(GL.GL_RENDERER));
        LOG.info("GL_VERSION:       " + gl.glGetString(GL.GL_VERSION));
    }
}
