package me.zt.illusion.egl;

import android.view.Surface;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

import static android.opengl.EGL14.EGL_CONTEXT_CLIENT_VERSION;
import static javax.microedition.khronos.egl.EGL10.EGL_WIDTH;
import static javax.microedition.khronos.egl.EGL10.EGL_HEIGHT;
import static javax.microedition.khronos.egl.EGL10.EGL_NONE;

public class EglHelper {
    private EGL10 mEgl;
    private EGLDisplay mEglDisplay;
    private EGLContext mEglContext;
    private EGLSurface mEglSurface;

    public void initEgl(EGLContext eglContext, int width, int height) {
        mEgl = (EGL10) EGLContext.getEGL();

        mEglDisplay = mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if (mEglDisplay == EGL10.EGL_NO_DISPLAY){
            throw new RuntimeException("eglGetDisplay failed");
        }

        int[] version = new int[2];
        if (!mEgl.eglInitialize(mEglDisplay,version)){
            throw new RuntimeException("eglInitialize failed");
        }

        int[] attrbutes = new int[]{
                EGL10.EGL_RED_SIZE,8,
                EGL10.EGL_GREEN_SIZE,8,
                EGL10.EGL_BLUE_SIZE,8,
                EGL10.EGL_ALPHA_SIZE,8,
                EGL10.EGL_DEPTH_SIZE,16,
                EGL10.EGL_RENDERABLE_TYPE,4,
                EGL10.EGL_NONE
        };
        int[] num_config = new int[1];
        if (!mEgl.eglChooseConfig(mEglDisplay,attrbutes,null,0,num_config)){
            throw new IllegalArgumentException("eglChooseConfig failes");
        }
        int numConfigs = num_config[0];
        if (numConfigs <= 0){
            throw new IllegalArgumentException("No configs match configSpec");
        }

        EGLConfig[] configs = new EGLConfig[numConfigs];
        if (!mEgl.eglChooseConfig(mEglDisplay,attrbutes,configs,numConfigs,num_config)){
            throw new IllegalArgumentException("eglChooseConfig get failed");
        }

        int[] surfaceAttr = {
                EGL_WIDTH, width,
                EGL_HEIGHT, height,
                EGL_NONE
        };
        mEglSurface = mEgl.eglCreatePbufferSurface(mEglDisplay, configs[0], surfaceAttr);
//        mEglSurface = mEgl.eglCreateWindowSurface(mEglDisplay,configs[0],surface,null);

        int[] contextAttr=new int[]{
                EGL_CONTEXT_CLIENT_VERSION, 2,
                EGL10.EGL_NONE
        };

        if (eglContext != null){
            mEglContext = mEgl.eglCreateContext(mEglDisplay,configs[0], eglContext, contextAttr);
        }else {
            mEglContext = mEgl.eglCreateContext(mEglDisplay,configs[0],EGL10.EGL_NO_CONTEXT, contextAttr);
        }

        if (!mEgl.eglMakeCurrent(mEglDisplay,mEglSurface,mEglSurface,mEglContext)){
            throw new RuntimeException("eglMakeCurrent fail");
        }
    }

    public EGLContext getmEglContext(){
        return mEglContext;
    }

    public boolean swapBuffers(){
        if (mEgl != null){
            return mEgl.eglSwapBuffers(mEglDisplay,mEglSurface);
        }else {
            throw new RuntimeException("egl is null");
        }
    }

    public void destroyEgl(){
        if (mEgl != null){
            mEgl.eglMakeCurrent(mEglDisplay,
                    EGL10.EGL_NO_SURFACE,
                    EGL10.EGL_NO_SURFACE,
                    EGL10.EGL_NO_CONTEXT
            );
            mEgl.eglDestroySurface(mEglDisplay,mEglSurface);
            mEglSurface = null;
            mEgl.eglDestroyContext(mEglDisplay,mEglContext);
            mEglContext = null;
            mEgl.eglTerminate(mEglDisplay);
            mEglDisplay = null;
            mEgl = null;
        }
    }

}
