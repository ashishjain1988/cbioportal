package org.mskcc.cbio.oncotator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO for Oncotator Cache Table.
 *
 */
public class DaoOncotatorCache
{
    private static DaoOncotatorCache daoOncotatorCache;

    private DaoOncotatorCache() {
    }

    /**
     * Gets Singleton Instance.
     * @return DaoOncotator Object.
     */
    public static DaoOncotatorCache getInstance()
    {
        if (daoOncotatorCache == null) {
            daoOncotatorCache = new DaoOncotatorCache();
        }
        
        return daoOncotatorCache;
    }

    /*
     * Adds a new Cache Record to the Database.
     *
     * @param mutSig Mut Sig Object.
     * @return number of records successfully added.
     * @throws DaoException Database Error.
     */

    public int put(OncotatorRecord record) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DatabaseUtil.getDbConnection();
            pstmt = con.prepareStatement
                    ("INSERT INTO oncotator_cache (`CACHE_KEY`,`GENE_SYMBOL`, `GENOME_CHANGE`, `PROTEIN_CHANGE`," +
                            " `VARIANT_CLASSIFICATION`," +
                            " `EXON_AFFECTED`, `COSMIC_OVERLAP`, `DB_SNP_RS`)" +
                            " VALUES (?,?,?,?,?,?,?,?)");
            pstmt.setString(1, record.getKey());
            pstmt.setString(2, record.getGene());
            pstmt.setString(3, record.getGenomeChange());
            pstmt.setString(4, record.getProteinChange());
            pstmt.setString(5, record.getVariantClassification());
            pstmt.setInt(6, record.getExonAffected());
            pstmt.setString(7, record.getCosmicOverlappingMutations());
            pstmt.setString(8, record.getDbSnpRs());
            int rows = pstmt.executeUpdate();
            return rows;
        } catch (SQLException e) {
            throw e;
        } finally {
            DatabaseUtil.closeAll(con, pstmt, rs);
        }
    }

    //getMutSig from a hugoGeneSymbol

    public OncotatorRecord get(String key) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DatabaseUtil.getDbConnection();
            pstmt = con.prepareStatement
                    ("SELECT * FROM oncotator_cache WHERE CACHE_KEY = ?");
            pstmt.setString(1, key);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                OncotatorRecord record = new OncotatorRecord(rs.getString("CACHE_KEY"));
                record.setGene(rs.getString("GENE_SYMBOL"));
                record.setGenomeChange(rs.getString("GENOME_CHANGE"));
                record.setProteinChange(rs.getString("PROTEIN_CHANGE"));
                record.setVariantClassification(rs.getString("VARIANT_CLASSIFICATION"));
                record.setCosmicOverlappingMutations(rs.getString("COSMIC_OVERLAP"));
                record.setExonAffected(rs.getInt("EXON_AFFECTED"));
                record.setDbSnpRs(rs.getString("DB_SNP_RS"));
                return record;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            DatabaseUtil.closeAll(con, pstmt, rs);
        }
    }

    public void deleteAllRecords() throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = DatabaseUtil.getDbConnection();
            pstmt = con.prepareStatement("TRUNCATE TABLE oncotator_cache");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            DatabaseUtil.closeAll(con, pstmt, rs);
        }
    }
}