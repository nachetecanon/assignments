package org.icanon.assignement1;

import org.icanon.assignement1.service.StringStatistics;
import org.icanon.assignements.model.StatService;

import static java.lang.System.exit;

public class Application1 {
    String s1;
    String s2;

    public static void main(String[] args) {
        Application1 application1 = new Application1();
        if (args.length != 2) {
            application1.help("assignement1-<version>.jar");
            exit(-1);
        }
        application1.s1 = args[0];
        application1.s2 = args[1];
        application1.run();
    }

    private void run() {
        StringStatistics st = new StringStatistics(new StatService());
        System.out.println("Result: " + st.mix(s1, s2));
    }

    private void help(final String app) {
        System.out.println("launch like this: java -jar " + app + " <string1> <string2>");
    }
}
