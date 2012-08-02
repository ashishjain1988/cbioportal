package org.mskcc.cbio.cgds.scripts;

import junit.framework.TestCase;
import org.mskcc.cbio.cgds.dao.DaoGeneOptimized;
import org.mskcc.cbio.cgds.model.CanonicalGene;
import org.mskcc.cbio.cgds.scripts.ImportGeneData;
import org.mskcc.cbio.cgds.scripts.ResetDatabase;
import org.mskcc.cbio.cgds.util.ProgressMonitor;

import java.io.File;

/**
 * JUnit tests for ImportGeneData class.
 */
public class TestImportGeneData extends TestCase {

    public void testImportGeneData() throws Exception {
        ResetDatabase.resetDatabase();
        DaoGeneOptimized daoGene = DaoGeneOptimized.getInstance();
        ProgressMonitor pMonitor = new ProgressMonitor();
        pMonitor.setConsoleMode(false);
		// TBD: change this to use getResourceAsStream()
        File file = new File("target/test-classes/genes_test.txt");
        ImportGeneData parser = new ImportGeneData(file, pMonitor);
        parser.importData();

        CanonicalGene gene = daoGene.getGene(35);
        assertEquals("ACADS", gene.getHugoGeneSymbolAllCaps());
        gene = daoGene.getGene(112);
        assertEquals("ADCY6", gene.getHugoGeneSymbolAllCaps());

        gene = daoGene.getGene("ACYP1");
        assertEquals(97, gene.getEntrezGeneId());
    }
}