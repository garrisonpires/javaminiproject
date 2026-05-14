package com.virtuallab.controller;

import com.virtuallab.model.LabSession;
import com.virtuallab.repository.LabSessionRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.virtuallab.dto.MonthlyUsageDTO;

@Controller
public class LabSessionController {

    @Autowired
    private LabSessionRepository repo;

    // ── Auth Guard ────────────────────────────────────────────────────────────

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("loggedIn") != null;
    }

    // ── Dashboard ─────────────────────────────────────────────────────────────

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isLoggedIn(session))
            return "redirect:/login";

        long totalSessions = repo.count();
        long completed = repo.countByStatus("Completed");
        long ongoing = repo.countByStatus("Ongoing");
        Long totalStudents = repo.sumAllStudents();
        if (totalStudents == null)
            totalStudents = 0L;

        List<Object[]> yearSummary = repo.getYearWiseSummary();

        model.addAttribute("totalSessions", totalSessions);
        model.addAttribute("completed", completed);
        model.addAttribute("ongoing", ongoing);
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("yearSummary", yearSummary);
        model.addAttribute("username", session.getAttribute("username"));
        return "dashboard";
    }

    // ── List All Records (with search) ────────────────────────────────────────

    @GetMapping("/records")
    public String listRecords(@RequestParam(required = false) String academicYear,
            @RequestParam(required = false) String subjectName,
            @RequestParam(required = false) String faculty,
            HttpSession session, Model model) {
        if (!isLoggedIn(session))
            return "redirect:/login";

        boolean hasFilter = (academicYear != null && !academicYear.isBlank())
                || (subjectName != null && !subjectName.isBlank())
                || (faculty != null && !faculty.isBlank());

        List<LabSession> sessions;
        if (hasFilter) {
            sessions = repo.searchSessions(academicYear, subjectName, faculty);
        } else {
            sessions = repo.findAll();
        }

        model.addAttribute("sessions", sessions);
        model.addAttribute("academicYear", academicYear);
        model.addAttribute("subjectName", subjectName);
        model.addAttribute("faculty", faculty);
        model.addAttribute("academicYears", getAcademicYears());
        model.addAttribute("username", session.getAttribute("username"));
        return "records";
    }

    // ── Add Form ──────────────────────────────────────────────────────────────

    @GetMapping("/add")
    public String showAddForm(HttpSession session, Model model) {
        if (!isLoggedIn(session))
            return "redirect:/login";
        model.addAttribute("labSession", new LabSession());
        model.addAttribute("isEdit", false);
        model.addAttribute("academicYears", getAcademicYears());
        model.addAttribute("username", session.getAttribute("username"));
        return "addSession";
    }

    // ── Edit Form ─────────────────────────────────────────────────────────────

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, HttpSession session,
            Model model, RedirectAttributes ra) {
        if (!isLoggedIn(session))
            return "redirect:/login";

        Optional<LabSession> opt = repo.findById(id);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("error", "Record not found.");
            return "redirect:/records";
        }

        model.addAttribute("labSession", opt.get());
        model.addAttribute("isEdit", true);
        model.addAttribute("academicYears", getAcademicYears());
        model.addAttribute("username", session.getAttribute("username"));
        return "addSession";
    }

    // ── Save (Add or Update) ──────────────────────────────────────────────────

    @PostMapping("/save")
    public String saveSession(@RequestParam(required = false) Long id,
            @RequestParam String academicYear,
            @RequestParam String subjectName,
            @RequestParam String labName,
            @RequestParam String facultyInCharge,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfConduct,
            @RequestParam Integer numberOfStudents,
            @RequestParam String status,
            @RequestParam(required = false) String department,
            HttpSession session,
            RedirectAttributes ra) {
        if (!isLoggedIn(session))
            return "redirect:/login";

        LabSession labSession = (id != null) ? repo.findById(id).orElse(new LabSession()) : new LabSession();

        labSession.setAcademicYear(academicYear);
        labSession.setSubjectName(subjectName);
        labSession.setLabName(labName);
        labSession.setFacultyInCharge(facultyInCharge);
        labSession.setDateOfConduct(dateOfConduct);
        labSession.setNumberOfStudents(numberOfStudents);
        labSession.setStatus(status);
        if (department != null) {
            labSession.setDepartment(department);
        }

        repo.save(labSession);
        ra.addFlashAttribute("success", (id != null) ? "Record updated successfully!" : "Record added successfully!");
        return "redirect:/records";
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        if (!isLoggedIn(session))
            return "redirect:/login";
        repo.deleteById(id);
        ra.addFlashAttribute("success", "Record deleted successfully!");
        return "redirect:/records";
    }

    // ── View Details ──────────────────────────────────────────────────────────

    @GetMapping("/session/{id}")
    public String viewSessionDetails(@PathVariable Long id, HttpSession session, Model model, RedirectAttributes ra) {
        if (!isLoggedIn(session))
            return "redirect:/login";

        Optional<LabSession> opt = repo.findById(id);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("error", "Record not found.");
            return "redirect:/records";
        }

        model.addAttribute("labSession", opt.get());
        model.addAttribute("username", session.getAttribute("username"));
        return "sessionDetails";
    }

    // ── Year-wise Report ──────────────────────────────────────────────────────

    @GetMapping("/report")
    public String yearWiseReport(HttpSession session, Model model) {
        if (!isLoggedIn(session))
            return "redirect:/login";

        List<Object[]> report = repo.getYearWiseSummary();

        long totalSessions = 0, totalStudents = 0;
        for (Object[] row : report) {
            totalSessions += ((Long) row[1]);
            totalStudents += (row[2] != null ? ((Long) row[2]) : 0);
        }

        model.addAttribute("report", report);
        model.addAttribute("totalSessions", totalSessions);
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("username", session.getAttribute("username"));
        return "report";
    }

    // ── Month-wise Report Drilldown ───────────────────────────────────────────

    @GetMapping("/report/{academicYear}")
    public String monthlyReport(@PathVariable String academicYear, HttpSession session, Model model) {
        if (!isLoggedIn(session))
            return "redirect:/login";

        List<Object[]> monthlyData = repo.getMonthlyUsageByAcademicYear(academicYear);

        List<MonthlyUsageDTO> usageList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            String monthName = Month.of(i).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            usageList.add(new MonthlyUsageDTO(monthName, 0L));
        }

        long totalUsage = 0;
        for (Object[] row : monthlyData) {
            Integer monthInt = (Integer) row[0];
            Long usage = (row[1] != null) ? ((Number) row[1]).longValue() : 0L;
            if (monthInt != null && monthInt >= 1 && monthInt <= 12) {
                usageList.get(monthInt - 1).setUsage(usage);
            }
            totalUsage += usage;
        }

        model.addAttribute("academicYear", academicYear);
        model.addAttribute("usageList", usageList);
        model.addAttribute("totalUsage", totalUsage);
        model.addAttribute("username", session.getAttribute("username"));
        return "monthlyReport";
    }



    /** Returns academic years from 2010-2011 up to 2025-2026. */
    private List<String> getAcademicYears() {
        List<String> years = new ArrayList<>();
        for (int y = 2010; y <= 2025; y++) {
            years.add(y + "-" + (y + 1));
        }
        return years;
    }
}
