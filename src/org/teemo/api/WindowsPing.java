package org.teemo.api;

public class WindowsPing implements Extractor {

    @Override
    public Double extract(String input) {

        int indexA = input.indexOf("ms");

        if (indexA != -1) {
            input = input.substring(0, indexA);

            indexA = input.indexOf("=");

            if (indexA != -1) {
                input = input.substring(indexA + 1, input.length());
                if (input.matches("[0-9.]+")) {
                    return Double.valueOf(input);
                }
            }
        }

        return null;
    }

    @Override
    public String getCommand(String ip) {
        return "ping -n 10 " + ip;
    }
}
