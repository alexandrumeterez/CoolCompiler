// Version 1.1.
package cool.tester;

import java.io.*;

public class Tester1 {
    // java -cp "bin;antlr-4.7.2-complete.jar;%CLASSPATH%" coolc.tester.Tester1
    public static void main(String[] args) throws IOException, InterruptedException {
        final String TEST_DIR_NAME = "tests/tema1";
        var testDir = new File(TEST_DIR_NAME);
        
        var filenameFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".cl");
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
            var result = compare(outPath, inPath.replace(".cl", ".ref"));
            if (result == 0) {
                System.out.println("OK");
                total += 5;
            }
            else
                System.out.println("Failed at line " + result);
        }
        
        System.out.println("Total: " + total);
    }
    
    public static int compare(String fileName1, String fileName2)
            throws IOException {
        try (LineNumberReader reader1 = new LineNumberReader(new FileReader(fileName1));
             LineNumberReader reader2 = new LineNumberReader(new FileReader(fileName2));
        ) {
            String line1 = null, line2 = null;
            
            while ((line1 = reader1.readLine()) != null
                    & (line2 = reader2.readLine()) != null) {
                
                if (! line1.equals(line2))
                    return reader1.getLineNumber();
            }
            
            if (line1 != null || line2 != null)
                return reader1.getLineNumber() + 1;
            
            return 0;
        }
    }

}
