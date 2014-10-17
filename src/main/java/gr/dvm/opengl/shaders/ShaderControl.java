package gr.dvm.opengl.shaders;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;

import javax.media.opengl.GL2;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/*
 * Taken from: http://www.guyford.co.uk/showpage.php?id=50&page=How_to_setup_and_load_GLSL_Shaders_in_JOGL
 */
public class ShaderControl {

    private static final Logger LOG = LogManager.getLogger(ShaderControl.class.getName());

    private int vertexShaderProgram;
    private int fragmentShaderProgram;
    private int shaderprogram;
    public String[] vsrc;
    public String[] fsrc;

    public void init(GL2 gl) {
        try {
            attachShaders(gl);
        } catch (Exception e) {
            LOG.error("Error", e);
        }
    }

    public String[] loadShader(String name) {
        StringBuilder sb = new StringBuilder();
        
        try {
            InputStream is = this.getClass().getResourceAsStream(name);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            is.close();
        } catch (Exception e) {
            LOG.error("Error", e);
        }

        LOG.info("Shader is: " + sb.toString());
        return new String[] { sb.toString() };
    }

    private void attachShaders(GL2 gl) throws Exception {
        vertexShaderProgram = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
        fragmentShaderProgram = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);
        gl.glShaderSource(vertexShaderProgram, 1, vsrc, null, 0);
        gl.glCompileShader(vertexShaderProgram);
        gl.glShaderSource(fragmentShaderProgram, 1, fsrc, null, 0);
        gl.glCompileShader(fragmentShaderProgram);
        shaderprogram = gl.glCreateProgram();

        gl.glAttachShader(shaderprogram, vertexShaderProgram);
        gl.glAttachShader(shaderprogram, fragmentShaderProgram);
        gl.glLinkProgram(shaderprogram);
        gl.glValidateProgram(shaderprogram);
        IntBuffer intBuffer = IntBuffer.allocate(1);
        gl.glGetProgramiv(shaderprogram, GL2.GL_LINK_STATUS, intBuffer);

        if (intBuffer.get(0) != 1) {
            gl.glGetProgramiv(shaderprogram, GL2.GL_INFO_LOG_LENGTH, intBuffer);
            int size = intBuffer.get(0);
            LOG.error("Program link error: ");
            if (size > 0) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(size);
                gl.glGetProgramInfoLog(shaderprogram, size, intBuffer, byteBuffer);

                LOG.error(new String(byteBuffer.array()));
            } else {
                LOG.error("Unknown");
            }
            System.exit(1);
        }
    }

    public int useShader(GL2 gl) {
        gl.glUseProgram(shaderprogram);
        return shaderprogram;
    }

    public void dontUseShader(GL2 gl) {
        gl.glUseProgram(0);
    }
}
