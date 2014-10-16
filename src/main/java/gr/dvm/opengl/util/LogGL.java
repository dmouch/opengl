package gr.dvm.opengl.util;

import javax.media.opengl.GLProfile;
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
}
