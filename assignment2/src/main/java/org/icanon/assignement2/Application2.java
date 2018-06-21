package org.icanon.assignement2;

import org.icanon.assignement2.service.StringStatistics;
import org.icanon.assignements.model.StatService;

import java.util.Arrays;

import static java.lang.System.exit;

public class Application2 {
    String[] args;

    public static void main(String[] args) {
        Application2 application2 = new Application2();
        if (args.length < 2) {
            application2.help("assignement1-<version>.jar");
            exit(-1);
        }
        application2.args = args;
        application2.run();
    }

    private void run() {
        StringStatistics st = new StringStatistics(new StatService());
        System.out.println("Result: " + st.mix(Arrays.asList(args), ","));
    }

    private void help(final String app) {
        System.out.println("launch like this: java -jar " + app + " <string1> <string2> ...");
    }
}
