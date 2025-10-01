package org.andarted.hnefatafl.common;

public class QLog {
    
	public static void log(String realm, String speaker, String messege) {
		System.out.printf("%-24s %-30s              %s%n", realm, speaker, messege);
	}

}
