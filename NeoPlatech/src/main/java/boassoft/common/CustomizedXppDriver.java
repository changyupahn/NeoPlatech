package boassoft.common;

import java.io.Writer;

import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class CustomizedXppDriver extends XppDriver {

	XmlFriendlyReplacer replacer = null;
    public CustomizedXppDriver(XmlFriendlyReplacer replacer){
        super();
        this.replacer = replacer;
    }
    public HierarchicalStreamWriter createWriter(Writer out) {
        return new CDATASupportPrettyPrintWriter(out, replacer);
    }
}
