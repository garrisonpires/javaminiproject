package com.virtuallab.util;

import com.virtuallab.model.LabSession;
import com.virtuallab.model.Student;
import com.virtuallab.repository.LabSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private LabSessionRepository repo;

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void run(String... args) throws Exception {
        // Always clear past seeded workshops to ensure latest 7 are injected
        List<LabSession> existing = repo.findBySubjectNameContainingIgnoreCase("Workshop");
        if (!existing.isEmpty()) {
            System.out.println("Cleaning up old workshop data to inject full 7 student records...");
            repo.deleteAll(existing);
        }

        System.out.println("Seeding the 7 workshops with full student details...");

        // Workshop 1
        LabSession ws1 = new LabSession(
            "2021-2022", "Workshop 1", "Computer Engineering Lab",
            "In-house Faculty", LocalDate.parse("2021-07-07"), 34, "Completed", "Computer Engineering"
        );
        ws1.setStudents(parseStudents(
            "1814054 Kriti Shrivastav, 1814014 Nidhi Raviraj Desai, 1924012 Malik Rihan Hoscop, 1814015 Priti Devdas, " +
            "1814035 Swapnil Sanjay Nagvenkar, 1814006 Sanket Mangesh Bandekar, 1614046 Glenna Z. Pinto, " +
            "1814004 Shravani Amshekar, 1814029 Manesh Borkar, 1614026 Mahale Aishwarya Dinesh, 1814026 Anusha Komarpant, " +
            "1814042 Shaun Noronha, 1814028 Krupashri Koli, 1814034 Saurabh Mulgaonkar, 1814030 Mahendra Markande, " +
            "1814049 Tejaswini Vijay Sail, 1814045 Aryan Prabhu Sawkar, 1814003 Amatya Katyayan, 1924005 Saurav Kamat, " +
            "1924008 Sweeney Mascarenhas, 1814016 Naschwin Domnic Dias, 1814020 Russel Fitzval Fernandes, " +
            "1924009 Ruchika Nagekar, 1814017 Veliston Dias, 1814055 Somdutt Vernekar, 1814019 Nikhil Fernandes, " +
            "1814024 Aarti Katke, 1814050 Sohil Kishor Salgaonkar, 1814013 Clenzila De Sousa, 1924004 Megha K. M, " +
            "1814018 Elridge Fernandes, 1814052 Sheena Mashelkar, 1814033 Leon Menezes, 1924006 Mansi Dattaram Kerkar"
        ));

        // Workshop 2
        LabSession ws2 = new LabSession(
            "2021-2022", "Workshop 2", "Computer Engineering Lab",
            "In-house Faculty", LocalDate.parse("2021-07-10"), 27, "Completed", "Computer Engineering"
        );
        ws2.setStudents(parseStudents(
            "1924008 Sweeney Mascarenhas, 1814003 Amatya Katyayan, 1924000 Mansi Dattaram Kerkar, 1814024 Aarti Katke, " +
            "1924009 Ruchika Nagekar, 1814035 Swapnil Nagvenkar, 1814026 Anusha Komarpant, 1814014 Priti Devdas, " +
            "1814015 Shravani Amshekar, 1814055 Somdutt Vernekar, 1814043 Leon Menezes, 1924004 Megha K. M, " +
            "1924011 Meltoy Travasso, 1814016 Naschwin Domnic Dias, 1814050 Sohil Kishor Salgaonkar, 1814042 Shaun Noronha, " +
            "1924005 Saurav Kamat, 1814013 Clenzila De Sousa, 1814052 Sheena Mashelkar, 1814034 Saurabh Mulgaonkar, " +
            "1814029 Manesh Borkar, 1814047 Aishwarya Mahale, 1814026 Asis Rodrigues, 1614046 Glenna Z. Pinto, " +
            "1814056 Ashwini Virdkar, 1814030 Mahendra Markande, 1814018 Elridge Fernandes"
        ));

        // Workshop 3
        LabSession ws3 = new LabSession(
            "2021-2022", "Workshop 3", "Computer Engineering Lab",
            "In-house Faculty", LocalDate.parse("2021-07-17"), 26, "Completed", "Computer Engineering"
        );
        ws3.setStudents(parseStudents(
            "1914010 Carson Conception Rodrigues, 1914049 Anjali Kushwaha, 1914004 Sidhlaxm Shenoy, 1914046 Sanresh Mapsekar, " +
            "1914032 Prajakta Maheshwar Chodankar, 1914011 Cheryl Fernandes, 1914050 Srishi Virdker, 1914015 Enid Fernandes, " +
            "1914005 Anushka Shetty, 1914035 Surayya Akiwat, 1914034 Rea D'souza, 1914024 Nihal Nilesh Kamat, " +
            "1914048 Sidham Desai, 1914019 Joshua John Fernandes, 2024003 Ajinkya Maik, 1914007 Ashish Mishra, " +
            "1914042 Oystum Yas, 1914028 Vibhuti Telekar, 1914056 Deepanjali Rawat, 1914013 Ruthvi Rajendradatt Keny, " +
            "1914046 Samiksha Chari, 1914043 Savi Shet Shirodkar, 1914043 Sejal Naik, 1914047 Anjan Lad, " +
            "1914002 Anan Lad, 1914014 Druvi Tendulkar"
        ));

        // Workshop 4
        LabSession ws4 = new LabSession(
            "2021-2022", "Workshop 4", "Computer Engineering Lab",
            "In-house Faculty", LocalDate.parse("2021-05-20"), 19, "Completed", "Computer Engineering"
        );
        ws4.setStudents(parseStudents(
            "2014025 Leo Dinar Barros, 2014038 Pratham Deepak Shah, 2014039 Rafath Umaimath, 2014044 Rutvik Vaze, " +
            "2014049 Sejal Sanjay Karapurkar, 2014050 Shannon Samuel Barreto D'Silva, 2014019 Joshua Coutinho, " +
            "2014005 Aryan Kotru, 2014006 Chinmay Joshi, 2014023 Kayne Roque Marion Martins, 2014008 Duval Franco Gomes, " +
            "2014064 Yadnya Phadke, 2014037 Nitya Pai, 2014012 Hitendra Eknath Naik, 2014042 Rudra Kande, " +
            "2014036 Nishil Malikarjun Hoogar, 2014011 Gauravi Kamat, 2014061 Varad Milind Kelkar, 2014015 Isha Phadte"
        ));

        // Workshop 5
        LabSession ws5 = new LabSession(
            "2021-2022", "Workshop 5", "Computer Engineering Lab",
            "In-house Faculty", LocalDate.parse("2021-08-30"), 24, "Completed", "Computer Engineering"
        );
        ws5.setStudents(parseStudents(
            "2014040 Rawdan Arvino Johnis Noronha, 2014057 Syed Tabib Ashraf, 2014025 Leo Dinar Barros, 2014029 Mariza Rodrigues, " +
            "2014037 Niya Pai, 2014005 Aryan Kortu, 2014042 Rudra Kande, 2014017 Jayden Anthony Ferrao, 2014053 Skyla Benisha Barreto, " +
            "2014041 Ronan Chris Mendes, 2014050 Shannon Samuel Barreto D'Silva, 2014023 Kayne Roque Marion Martins, " +
            "2014030 Jaden Mascarenhas, 2014045 Saish Ugaonkar, 2014014 Isaiah D'Costa, 2014010 Flancy Fernandes, " +
            "2014002 Aayush Sandeep Parab, 2014038 Pratham Deepak Shah, 2014031 Merc Jhos Gonsalves, 2014051 Shubham Faheem Thakur, " +
            "2014032 Mohammad Faheem Thakur, 2014036 Nishil Malikarjun Hoogar, 2014047 Sanketh Shenoy, 2014035 Nimisha Namdev Naik Gaonkar"
        ));

        // Workshop 6
        LabSession ws6 = new LabSession(
            "2019-2020", "Workshop 6", "Computer Engineering Lab",
            "In-house Faculty", LocalDate.parse("2020-02-03"), 17, "Completed", "Computer Engineering"
        );
        ws6.setStudents(parseStudents(
            "1814003 Amatya Katyayan, 1814005 Anchila Maity, 1814007 Anjal Barad, 1814009 Anjali Batule, 1814010 Bevan Fernandes, " +
            "1814014 Shivam Naik, 1814047 Asis Rodrigues, 1814019 Nidhi Desai, 1814039 Nikhil Fernandes, 1814044 Poonam Pai, " +
            "1814043 Pratibha Patil, 1814019 Nikhil Fernandes, 1814014 Priti Devdas, 1814015 Sheena Mashelkar, " +
            "1814052 Shravani Amshekar, 1814004 Vedangi Barve, 1814008 Vedangi Barve"
        ));

        // Workshop 7
        LabSession ws7 = new LabSession(
            "2020-2021", "Workshop 7", "Mechanical Engineering Lab",
            "In-house Faculty", LocalDate.parse("2020-07-01"), 20, "Completed", "Mechanical Engineering"
        );
        ws7.setStudents(parseStudents(
            "1912067 Abigail Barreto, 1912070 Amston Sanches, 1912074 Ashtang Mandrekar, 1912075 Ayisha Shaikh, 1912076 Ayush Tari, " +
            "1912078 Chirag Naik, 1912081 Dhruv Bhende, 1912102 Shawn Pacheco, 1912085 Josten D'Souza, 1912089 Lemuel De Cunha, " +
            "1912091 Mahi Nair, 1912111 Yash Mandrekar, 1912093 Nathan Mazarello, 1912084 Jes Menezes, 1912066 Abdul Aziz Salman Mohammed, " +
            "1912109 Vellington Bazilio Monteiro, 1912095 Onkar Yedvi, 1912104 Simron Rodrigues, 1912100 Schuler Fernandes, " +
            "1912107 Suryakant Phadte"
        ));

        repo.saveAll(List.of(ws1, ws2, ws3, ws4, ws5, ws6, ws7));
        System.out.println("Seeded all 7 workshops successfully with all students!");
    }

    private List<Student> parseStudents(String data) {
        List<Student> list = new ArrayList<>();
        String[] entries = data.split(",");
        for (String entry : entries) {
            entry = entry.trim();
            if (entry.isEmpty()) continue;
            // Roll No is the first continuous block of digits. E.g "1814054 Kriti Shrivastav"
            int firstSpace = entry.indexOf(' ');
            if (firstSpace != -1) {
                String rollNo = entry.substring(0, firstSpace);
                String name = entry.substring(firstSpace + 1);
                list.add(new Student(rollNo, name));
            }
        }
        return list;
    }
}
