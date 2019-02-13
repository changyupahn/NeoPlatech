package boassoft.common;

public class StaticPush {

    private static int sw = 0;
	
	public static int get(){
		return sw;
	}
	
	public static synchronized void set(){
		sw = 1;
	}
	
	public static synchronized void end(){
		sw = 0;
	}
}
