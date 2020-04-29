package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.kohsuke.args4j.Args4JUtilities.getUsageLines;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the default option value outputs in many ways.
 * @author Stephan Fuhrmann
 */
public class DefaultOptionTest {
    
    private CmdLineParser parser;

    private DefaultOption defaultOption;

    @BeforeEach
    public void getTestObject() {
        defaultOption = new DefaultOption();
        parser = new CmdLineParser(defaultOption);
    }
    
    @Test
    public void testParseArgumentWithEmptyArgs() throws IOException, CmdLineException {
        
        String usageMessage[] = getUsageLines(parser);
        
        String testMessageExpected[] = new String[] {
        " -byteVal N                             : my favorite byte (default: 0)",
        " -drink [BEER | WHISKEY | SCOTCH |      : my favorite drink (default: BEER)",
        " BOURBON | BRANDY]                         ",
        " -drinkArray [BEER | WHISKEY | SCOTCH   : my favorite drinks (default:",
        " | BOURBON | BRANDY]                      [BEER,BOURBON])",
        " -drinkList [BEER | WHISKEY | SCOTCH |  : my favorite drinks (default:",
        " BOURBON | BRANDY]                        [BEER,BRANDY])",
        " -req VAL                               : set a string",
        " -str VAL                               : set a string (default: pretty string)",
        " -strArray VAL                          : my favorite strarr (default:",
        "                                          [san,dra,chen])"
        };

        for (int i=0; i < usageMessage.length; i++) {
            assertEquals(testMessageExpected[i], usageMessage[i], "Line "+(i+1)+" wrong");
        }
    }
    private static class DefaultOption {
        @Option(name="-str",usage="set a string")
        public String str = "pretty string";
        
        @Option(name="-req",usage="set a string", required = true)
        public String req = "required";
        
        @Option(name="-noDefault")
        public String noDefault;
        
        @Option(name="-noDefaultReq", required = true)
        public String noDefaultReq;
        
        @Option(name="-byteVal", usage = "my favorite byte")
        public byte byteVal;
        
        @Option(name="-strArray", usage="my favorite strarr")
        public String strArray[] = new String[] { "san", "dra", "chen"};
        
        public enum DrinkName {
            BEER,
            WHISKEY,
            SCOTCH,
            BOURBON,
            BRANDY
        };
        
        @Option(name="-drinkArray", usage="my favorite drinks")
        public DrinkName drinkArray[] = new DrinkName[] { DrinkName.BEER, DrinkName.BOURBON };
        
        @Option(name="-drink", usage="my favorite drink")
        public DrinkName drink = DrinkName.BEER;
        
        @Option(name="-drinkList", usage="my favorite drinks")
        public List<DrinkName> drinkList = Arrays.asList(DrinkName.BEER, DrinkName.BRANDY);
        
        @Argument
        public String arguments[] = new String[] { "foo", "bar" };
    }
        
}
