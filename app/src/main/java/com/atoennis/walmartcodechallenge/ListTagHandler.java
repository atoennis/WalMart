package com.atoennis.walmartcodechallenge;

import android.text.Editable;
import android.text.Html.TagHandler;

import org.xml.sax.XMLReader;

public class ListTagHandler implements TagHandler {
    private final String replaceValue;

    public ListTagHandler(String replaceValue) {
        this.replaceValue = replaceValue;
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (tag.equalsIgnoreCase("li") && opening) {
            output.append(replaceValue);
        }
    }
}
