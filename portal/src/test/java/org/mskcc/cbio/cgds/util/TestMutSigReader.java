package org.mskcc.cbio.cgds.util;

import junit.framework.TestCase;
import org.mskcc.cbio.cgds.dao.DaoCancerStudy;
import org.mskcc.cbio.cgds.dao.DaoGeneOptimized;
import org.mskcc.cbio.cgds.dao.DaoMutSig;
import org.mskcc.cbio.cgds.model.CancerStudy;
import org.mskcc.cbio.cgds.model.CanonicalGene;
import org.mskcc.cbio.cgds.model.MutSig;
import org.mskcc.cbio.cgds.scripts.ImportTypesOfCancers;
import org.mskcc.cbio.cgds.scripts.ResetDatabase;
import org.mskcc.cbio.cgds.util.MutSigReader;
import org.mskcc.cbio.cgds.util.ProgressMonitor;

import java.io.File;

public class TestMutSigReader extends TestCase {

	// TBD: change these to use getResourceAsStream()
    File properties = new File("target/test-classes/testCancerStudy.txt");
    File mutSigFile = new File("target/test-classes/test_mut_sig_data.txt");

    ProgressMonitor pm = new ProgressMonitor();
    // TBD: change this to use getResourceAsStream()
    File cancers =  new File("target/test-classes/cancers.txt");
    
    public void testloadMutSig() throws Exception {

        ProgressMonitor pMonitor = new ProgressMonitor();
        pMonitor.setConsoleMode(false);

        ResetDatabase.resetDatabase();
        ImportTypesOfCancers.load(new ProgressMonitor(), cancers);

        // Add cancers to a fresh database
        // Add a cancer study whose standardId is "tcga_gbm"
        // In accordance with /testCancerStudy.txt
        CancerStudy cancerStudy = new CancerStudy("Glioblastoma TCGA", "GBM Description", "tcga_gbm", "GBM", false);
        DaoCancerStudy.addCancerStudy(cancerStudy);
        assertEquals(1, cancerStudy.getInternalId());

        // Add some genes to the fresh database
        DaoGeneOptimized daoGeneOptimized = DaoGeneOptimized.getInstance();
        CanonicalGene gene = new CanonicalGene(1956, "EGFR");
        CanonicalGene gene2 = new CanonicalGene(4921, "DDR2");
        daoGeneOptimized.addGene(gene);
        daoGeneOptimized.addGene(gene2);

        int cancerStudyId = MutSigReader.getInternalId(properties);
        assertTrue(CancerStudy.NO_SUCH_STUDY != cancerStudyId);
        MutSigReader.loadMutSig(cancerStudyId, mutSigFile, pMonitor);
        
        // Is the data in the database?
        MutSig mutSig = DaoMutSig.getMutSig("EGFR", 1);
        assertTrue(mutSig != null);
        CanonicalGene testGene = mutSig.getCanonicalGene();
        assertTrue(testGene != null);

        assertTrue("EGFR".equals(testGene.getHugoGeneSymbolAllCaps()));
        assertEquals(mutSig.getNumMutations(), 20);
        assertEquals(mutSig.getNumBasesCovered(), 502500);
        assertTrue(1E-11f == mutSig.getpValue());
        assertTrue(1E-8f == mutSig.getqValue());
    }
}