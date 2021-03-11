package com.rs2.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.rs2.game.npcs.NPCDefinition;
import com.thoughtworks.xstream.XStream;

public class XStreamUtil {
	
	private static XStreamUtil instance = new XStreamUtil();
	private static XStream xStream = new XStream();
	
	public static XStreamUtil getInstance() {
		return instance;
	}
        
    static {
		xStream.alias("npcDefinition", NPCDefinition.class);
	}

	public static XStream getXStream() {
		return xStream;
	}
	
    public static void writeXML(Object object, File file) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        try {
            xStream.toXML(object, out);
            out.flush();
        } finally {
            out.close();
        }
    }

}
