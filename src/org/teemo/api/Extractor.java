package org.teemo.api;

public interface Extractor {

    Double extract(String input);

    String getCommand(String ip);
}
