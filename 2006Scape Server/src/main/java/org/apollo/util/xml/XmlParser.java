package org.apollo.util.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * A simple XML parser that uses the internal {@link org.xml.sax} API to create a tree of {@link XmlNode} objects.
 *
 * @author Graham
 */
public final class XmlParser {

	/**
	 * A class which handles SAX events.
	 *
	 * @author Graham
	 */
	private final class XmlHandler extends DefaultHandler {

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			currentNode.setValue(new String(ch, start, length));
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (!nodeStack.isEmpty()) {
				currentNode = nodeStack.pop();
			}
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			XmlNode next = new XmlNode(localName);

			if (rootNode == null) {
				rootNode = currentNode = next;
			} else {
				currentNode.addChild(next);
				nodeStack.add(currentNode);
				currentNode = next;
			}

			if (attributes != null) {
				int attributeCount = attributes.getLength();
				for (int i = 0; i < attributeCount; i++) {
					String attribLocalName = attributes.getLocalName(i);
					currentNode.setAttribute(attribLocalName, attributes.getValue(i));
				}
			}
		}

	}

	/**
	 * The current node.
	 */
	private XmlNode currentNode;

	/**
	 * The SAX event handler.
	 */
	private final XmlHandler eventHandler;

	/**
	 * The stack of nodes, used when traversing the document and going through child nodes.
	 */
	private final Stack<XmlNode> nodeStack = new Stack<>();

	/**
	 * The current root node.
	 */
	private XmlNode rootNode;

	/**
	 * The {@link XMLReader} backing this {@link XmlParser}.
	 */
	private final XMLReader xmlReader;

	/**
	 * Creates the XML parser.
	 *
	 * @throws SAXException If a SAX error occurs.
	 */
	public XmlParser() throws SAXException {
		xmlReader = XMLReaderFactory.createXMLReader();
		eventHandler = this.new XmlHandler();
		init();
	}

	/**
	 * Initialises this parser.
	 */
	private void init() {
		xmlReader.setContentHandler(eventHandler);
		xmlReader.setDTDHandler(eventHandler);
		xmlReader.setEntityResolver(eventHandler);
		xmlReader.setErrorHandler(eventHandler);
	}

	/**
	 * Parses XML data from the {@link InputSource}.
	 *
	 * @param source The {@link InputSource}.
	 * @return The root {@link XmlNode}.
	 * @throws IOException If an I/O error occurs.
	 * @throws SAXException If a SAX error occurs.
	 */
	private XmlNode parse(InputSource source) throws IOException, SAXException {
		rootNode = null;
		xmlReader.parse(source);
		if (rootNode == null) {
			throw new SAXException("No root element.");
		}
		return rootNode;
	}

	/**
	 * Parses XML data from the given {@link InputStream}.
	 *
	 * @param is The {@link InputStream}.
	 * @return The root {@link XmlNode}.
	 * @throws IOException If an I/O error occurs.
	 * @throws SAXException If a SAX error occurs.
	 */
	public XmlNode parse(InputStream is) throws IOException, SAXException {
		synchronized (this) {
			return parse(new InputSource(is));
		}
	}

	/**
	 * Parses XML data from the given {@link Reader}.
	 *
	 * @param reader The {@link Reader}.
	 * @return The root {@link XmlNode}.
	 * @throws IOException If an I/O error occurs.
	 * @throws SAXException If a SAX error occurs.
	 */
	public XmlNode parse(Reader reader) throws IOException, SAXException {
		synchronized (this) {
			return parse(new InputSource(reader));
		}
	}

}