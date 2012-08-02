package org.mskcc.cbio.cgds.scripts;

import org.mskcc.cbio.cgds.dao.DaoCaseList;
import org.mskcc.cbio.cgds.model.CaseList;
import org.mskcc.cbio.cgds.util.ProgressMonitor;

import java.util.ArrayList;

/**
 * Command Line Tool to Export All Case Lists to the Console.
 */
public class GetAllCaseLists {

    public static void main(String[] args) throws Exception {
        ProgressMonitor pMonitor = new ProgressMonitor();
        pMonitor.setConsoleMode(true);
        DaoCaseList daoCaseList = new DaoCaseList();
        ArrayList <CaseList> caseListMaster = daoCaseList.getAllCaseLists();
        for (CaseList caseList:  caseListMaster) {
            System.out.println (caseList.getCaseListId() + ": "
                    + caseList.getStableId() + ": " + caseList.getName());
        }
    }
}