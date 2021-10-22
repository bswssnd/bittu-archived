package bittu.jni;

public class AudioPlayerJNI {
	static {
		System.loadLibrary("bittuplayer");
	}
	
	public static final int AUDIO_BUFFER_MAX_SIZE = 3840;
	
	public static final int NO_ERROR = 0;
	public static final int MEDIA_INAUDIBLE = -1;
	public static final int MEDIA_INVALID = -2;
	public static final int MEDIA_DECODE_ERROR = -3;
	public static final int MEDIA_INTERNAL_ERROR = -4;
	public static final int MEDIA_EOF = -5;
	
	private int ptr;
	
	public native void init();
	public native int stream(String url);
	public native int get20ms(byte[] buf);
	public native void uninit();
}
