package org.andarted.hnefatafl.common;

public class QLog {
    
	public static void log(String realm, String speaker, String messege) {
		System.out.printf("%-12s %-24s:              %s%n", realm, speaker, messege);
	}

}
