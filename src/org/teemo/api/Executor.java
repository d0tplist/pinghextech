package org.teemo.api;

import org.teemo.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class Executor {

    public static void execute(Extractor extractor, Server server, Consumer<Double> consumer) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(extractor.getCommand(server.getIp()));

            InputStream in = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;

            while((line = reader.readLine()) != null){
                Double result = extractor.extract(line);
                if(result != null) {
                    consumer.accept(result);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
