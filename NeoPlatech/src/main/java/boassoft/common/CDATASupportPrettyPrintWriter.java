package boassoft.common;

import java.io.Writer;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

public class CDATASupportPrettyPrintWriter extends PrettyPrintWriter {

	 public CDATASupportPrettyPrintWriter(Writer writer, XmlFriendlyReplacer replacer) {
	        super(writer, replacer);
	    }

	    protected void writeText(QuickWriter writer, String text) {
	        writer.write("<![CDATA[");
			writer.write(text);
			writer.write("]]>");
	    }
}
