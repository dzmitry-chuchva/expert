package by.bsuir.iit.kp.expert;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import by.bsuir.iit.kp.expert.exceptions.LoaderException;

public class Bootstrap {
	
	private static final String UITYPE = "ui";
	private static final String DEBUG = "d";
	private static final String[] noParamArgumentsOrder = new String[] { Loader.FILENAME_PROP };
	
	private static final String OPT_DESCR_INDENT = "\t\t\t\t\t\t";
	private static final String OPT_INDENT = "\t\t";
	
	public static void main(String[] args) {
		try {
			Properties props = parseCmdLine(args);
			Loader.setProperties(props);
			
			Loader.startSystem();
		} catch (LoaderException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Properties parseCmdLine(String[] args) {
		Properties props = new Properties();
		int i = 0;
		int noParamArgNo = 0;
		
		try {
			while (i < args.length) {
				String option = args[i];
				if (option.charAt(0) == '-') {
					String title = args[0].substring(1);
					if (UITYPE.equals(title)) {
						String[] arguments = getArgumentsForOption(args,i);
						if (arguments.length > 0) {
							props.setProperty(Loader.UITYPE_PROP, arguments[0]);
						} else {
							throw new Exception("Not enough parameters for " + option);
						}
						i++;
					} else if (DEBUG.equals(title)) {
						props.setProperty(Loader.DEBUGMODE_PROP, Loader.VALUE_TRUE);
					} else {
						throw new Exception("Unknown option: " + option);
					}
				} else {
					if (noParamArgNo == noParamArgumentsOrder.length) {
						throw new Exception("Unexpected: " + option);
					} else {
						props.setProperty(noParamArgumentsOrder[noParamArgNo++], option);
					}
				}
				i++;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println();
			usage(System.err);
		}
		return props;
	}
	
	private static String[] getArgumentsForOption(String[] cmdLine, int start) throws Exception {
		List args = new ArrayList();
		
		int index = 1;
		if (start + index == cmdLine.length) {
			throw new Exception("Not enough parameters for " + cmdLine[start]);
		} else {
			String next = cmdLine[start + index++];
			String[] splitted = next.split(",");
			args.addAll(Arrays.asList(splitted));
		}
		
		return (String[])args.toArray();
	}
	
	private static void usage(PrintStream out) {
		out.println("usage: " + Bootstrap.class.getName() + " [-options] file");
		out.println("where options include:");
		
		showOption(out,UITYPE,"type of user interface, which will be used on runtime");
		showOption(out,"filename","name of file with model description, which will be executed");
	}

	private static void showOption(PrintStream out, String option, String description) {
		StringBuffer buffer = new StringBuffer(OPT_INDENT);
		buffer.append(option);
		if (option.length() + 1 >= OPT_DESCR_INDENT.length() * 4) {
			buffer.append('\n');
		}
		buffer.append(OPT_DESCR_INDENT);
		buffer.append(description);
		out.println(buffer.toString());
	}

}
