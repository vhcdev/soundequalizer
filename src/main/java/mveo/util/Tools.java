package mveo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

public class Tools {

	public static String calculateChecksum(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		String md5 = DigestUtils.md5Hex(fis);
		fis.close();
		return md5;
	}

}
