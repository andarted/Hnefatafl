package org.andarted.hnefatafl.common;

public class TraceLogger {
	//ANSI Farb Codes
	private static final String RESET  = "\u001B[0m";
    private static final String GREEN  = "\u001B[32m";
    private static final String RED    = "\u001B[31m";
    
    
	public static void log(String realm, String speaker, boolean check, String target) {
		String checkMark = check ? GREEN + "✔" + RESET : RED + "x" +RESET;
		System.out.printf("%-30s %-36s %-12s ->          %s%n", realm, speaker, check, target);
	}

}
