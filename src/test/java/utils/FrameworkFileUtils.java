package utils;

import java.io.File;
import java.io.FileFilter;
import java.net.URI;
import java.util.Arrays;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;

public class FrameworkFileUtils {

	public static URI getTheNewestFile(String filePath, String ext) {
	    File theNewestFile = null;
	    File dir = new File(filePath);
	    FileFilter fileFilter = new WildcardFileFilter("*." + ext);
	    File[] files = dir.listFiles(fileFilter);

	    if (files.length > 0) {	       
	        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
	        theNewestFile = files[0];
	    }

	    return theNewestFile.toURI();
	}
}
