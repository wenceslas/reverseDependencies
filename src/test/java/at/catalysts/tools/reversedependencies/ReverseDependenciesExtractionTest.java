package at.catalysts.tools.reversedependencies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import at.catalysts.tools.reversedependencies.data.DependencyTree;
import at.catalysts.tools.reversedependencies.data.Setting;
import at.catalysts.tools.reversedependencies.io.DependencyFileReader;
import at.catalysts.tools.reversedependencies.test.IntegrationTest;

public class ReverseDependenciesExtractionTest {

    @Category(IntegrationTest.class)
    @Test
    public void testMainWithCache() {
        ReverseDependencies.main(new String[] {"-q", "src/test/resources/query.txt", "-m", "src/test/resources/inuse.csv",
                "-r", "http://repo.appli.dgfip/nexus/content/repositories/releases/", "-o", "target/resultats_extraction.csv",
                "-s", "fr/gouv/finances/tpi", "-c", "target/cache.csv"});
       
        
        /*
        DependencyFileReader reader = new DependencyFileReader("src/test/resources/results.csv");
        reader.setReversedWithUsedByDependencies(true);
        List<DependencyTree> resultTrees = reader.readDependencyTreeFile();
        assertNotNull(resultTrees);
        assertEquals(2, resultTrees.size());
        assertEquals("queryArtifactId", resultTrees.get(0).getDependency().getArtifactId());
        assertEquals("junit", resultTrees.get(1).getDependency().getArtifactId());
        assertEquals("4.12", resultTrees.get(1).getDependency().getVersion());
        assertEquals(1, resultTrees.get(1).getUsedBy().size());
        assertEquals("maven-compiler-plugin", resultTrees.get(1).getUsedBy().get(0).getDependency().getArtifactId());
        assertEquals("3.6.0", resultTrees.get(1).getUsedBy().get(0).getDependency().getVersion());
        */
    }

 
}