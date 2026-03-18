package com.virtuallab.repository;

import com.virtuallab.model.LabSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabSessionRepository extends JpaRepository<LabSession, Long> {

       // ── Search / Filter ───────────────────────────────────────────────────────

       List<LabSession> findByAcademicYearContainingIgnoreCase(String academicYear);

       List<LabSession> findBySubjectNameContainingIgnoreCase(String subjectName);

       List<LabSession> findByFacultyInChargeContainingIgnoreCase(String faculty);

       @Query("SELECT s FROM LabSession s WHERE " +
                     "(:academicYear IS NULL OR :academicYear = '' OR LOWER(s.academicYear) LIKE LOWER(CONCAT('%', :academicYear, '%'))) AND " +
                     "(:subjectName IS NULL OR :subjectName = '' OR LOWER(s.subjectName) LIKE LOWER(CONCAT('%', :subjectName, '%'))) AND " +
                     "(:faculty IS NULL OR :faculty = '' OR LOWER(s.facultyInCharge) LIKE LOWER(CONCAT('%', :faculty, '%')))")
       List<LabSession> searchSessions(
                     @Param("academicYear") String academicYear,
                     @Param("subjectName") String subjectName,
                     @Param("faculty") String faculty);

       // ── Dashboard stats ───────────────────────────────────────────────────────

       long countByStatus(String status);

       @Query("SELECT SUM(s.numberOfStudents) FROM LabSession s")
       Long sumAllStudents();

       @Query("SELECT s.academicYear, COUNT(s), SUM(s.numberOfStudents) " +
                     "FROM LabSession s GROUP BY s.academicYear ORDER BY s.academicYear DESC")
       List<Object[]> getYearWiseSummary();

       // ── Month-wise report ─────────────────────────────────────────────────────

       @Query("SELECT MONTH(s.dateOfConduct), SUM(s.numberOfStudents) " +
                     "FROM LabSession s WHERE s.academicYear = :academicYear " +
                     "GROUP BY MONTH(s.dateOfConduct)")
       List<Object[]> getMonthlyUsageByAcademicYear(@Param("academicYear") String academicYear);
}
