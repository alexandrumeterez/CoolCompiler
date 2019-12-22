// Version 1.1.
package cool.tester;

import java.io.*;
import java.util.HashSet;

public class Tester2 {
    // java -cp "bin;lib/antlr-4.7.1-complete.jar;%CLASSPATH%" coolc.tester.Tester1
    public static void main(String[] args) throws IOException, InterruptedException {
        final String TEST_DIR_NAME = "tests/tema2";
        var testDir = new File(TEST_DIR_NAME);
        
        var filenameFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".cl") && ! name.endsWith("main.cl");
            }
        };
        
        var total = 0;
        
        for (var file : testDir.listFiles(filenameFilter)) {
            var inPath = file.getPath();
            var outPath = inPath.replace(".cl", ".out");
            ProcessBuilder builder = new ProcessBuilder("make", "-s", "ARGS=" + inPath , "run");
            builder.redirectOutput(new File(outPath));
            builder.redirectError(new File(outPath));
            Process p = builder.start();
            p.waitFor();
            
            System.out.printf("%-30s -> ", file.getName());
            var result = compare(outPath, inPath.replace(".cl", ".ref"), System.out);
            if (result)
                total += 5;
            
            System.out.println("-----");
        }
        
        System.out.println("Total: " + total);
    }
    
    public static boolean compare(String outName, String refName, PrintStream oldOut)
            throws IOException {
        try (var outReader = new BufferedReader(new FileReader(outName));
             var refReader = new BufferedReader(new FileReader(refName));
        ) {
            String line = null;
            
            var outSet = new HashSet<String>();
            var refSet = new HashSet<String>();
            
            while ((line = outReader.readLine()) != null)
                outSet.add(line);
            
            while ((line = refReader.readLine()) != null)
                refSet.add(line);
            
            if (outSet.equals(refSet)) {
                oldOut.println("OK");
                return true;
            }
            
            oldOut.println("Failed");
            
            // Copy set since removeAll would mutate it.
            var missingSet = new HashSet<String>(refSet);
            missingSet.removeAll(outSet);
            
            var extraneousSet = new HashSet<String>(outSet);
            extraneousSet.removeAll(refSet);
            
            if (! missingSet.isEmpty()) {
                oldOut.println("* Missing errors:");
                missingSet.stream().forEach(oldOut::println);
            }
            
            if (! extraneousSet.isEmpty()) {
                oldOut.println("* Extraneous errors:");
                extraneousSet.stream().forEach(oldOut::println);
            }
            
            return false;
        }
    }

}
