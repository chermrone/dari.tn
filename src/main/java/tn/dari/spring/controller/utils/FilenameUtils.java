package tn.dari.spring.controller.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FilenameUtils {
	private static final Logger log = LoggerFactory.getLogger(FilenameUtils.class);
	static public String getExtension(String name) {
		log.info(name);
		log.info("nom de longueur"+name.length());
		String[] n = name.split("\\.");
		log.info("nom de longueur"+n.length);
		log.info("nom de longueur"+n[n.length-1]);

		return n[n.length-1];
	}
}
