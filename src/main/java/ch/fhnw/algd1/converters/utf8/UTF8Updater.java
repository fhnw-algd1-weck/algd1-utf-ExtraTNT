package ch.fhnw.algd1.converters.utf8;

import ch.fhnw.algd1.converters.base.Updater;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/*
 * Created on 05.09.2014
 */

/**
 * @author Wolfgang Weck
 */
public class UTF8Updater implements Updater<Integer> {
private final Document doc;

public UTF8Updater(Document doc) {
    this.doc = doc;
}

@Override
public Document getDocument() {
    return doc;
}

@Override
public void update(Integer val) {
    try {
        ((PlainDocument) doc).replace(0, doc.getLength(), hexBytes(
            UTF8Converter.codePointToUTF(val)), doc.getDefaultRootElement()
                                          .getAttributes());
    } catch (BadLocationException e) {
        e.printStackTrace();
    }
}

private String hexBytes(byte[] b) {
    if (b == null) return "";
    StringBuilder s = new StringBuilder();

    for (int x : b) {
        if (x < 0) x = x + 256;
        s.append(hexDigit(x / 16));
        s.append(hexDigit(x % 16));
    }
    return s.toString();
}

private char hexDigit(int x) {
    if (x < 10) return (char) ('0' + x);
    return (char) ('A' + x - 10);
}
}
