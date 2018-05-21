package org.teemo.api;

public class MacPing implements Extractor {


    @Override
    public String getCommand(String ip) {
        return "ping -c 10 "+ip;
    }

    /**
     * Example:
     * 64 bytes from 104.160.136.3: icmp_seq=51 ttl=47 time=152.489 ms
     *
     * @param input
     * @return
     */
    @Override
    public Double extract(String input) {

        if (input.endsWith("ms")) {
            int indexA = input.lastIndexOf("=");
            int indexB = input.lastIndexOf(" ");

            if(indexA == -1 || indexB == -1){
                return null;
            }

            input = input.substring(indexA + 1, indexB);

            if(input.matches("[0-9.]+")) {
                return Double.valueOf(input.trim());
            }
        }

        return null;
    }
}
