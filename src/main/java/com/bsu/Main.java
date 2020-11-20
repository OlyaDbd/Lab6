package com.bsu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Company> companies = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("Companies.txt"))) {
            while (scanner.hasNext()) {
                Company company = new Company(scanner.nextLine());
                companies.add(company);
            }
            searchingByRequest(companies);
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    
    static void searchingByRequest(List<Company> companies) {
        int requestIndex = 1;
        try (Scanner scanner = new Scanner(new File("requests.txt"));
             FileWriter logFile =
                     new FileWriter(new File("Logfile.txt"), true)) {
            List<Company> result = new ArrayList<>();
            Requests manager = new Requests(companies);
            while (scanner.hasNext()) {
                String request = scanner.nextLine().toLowerCase();
                if (request.contains((SQLRequests.ABBREVIATION).toLowerCase())) {
                    result = manager.chooseByAbbreviation(
                            request.substring(request.indexOf("= ") + 2));
                } else if (request.contains((SQLRequests.TYPE_OF_BUSINESS).toLowerCase())) {
                    result = manager.chooseByTypeOfBusiness(
                            request.substring(request.indexOf("= ") + 2));
                } else if (request.contains((SQLRequests.AMOUNT_OF_EMPLOYEES).toLowerCase())) {
                    result = manager.chooseByCountOfEmployees(
                            Integer.parseInt(request.substring(
                                    (request.indexOf("from: ") + 6), request.indexOf("to:") - 1)),
                            Integer.parseInt(request.substring(request.indexOf("to: ") + 4)));
                }
                logWriting(logFile, request);
                FileWriter outputFile =
                        new FileWriter(new File("Request" + requestIndex + ".csv"));
                fileWriting(outputFile, result);
                outputFile.close();
                requestIndex++;
            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    static void logWriting(FileWriter logFile, String request) throws IOException {
        SimpleDateFormat formatNow = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date now = new Date();
        logFile.write("Date and time of running: " + formatNow.format(now) + "\n");
        logFile.write("Request: " + request + "\n");
    }


    static void fileWriting(FileWriter outputFile,
                            List<Company> inf) throws IOException {
        for (Company company : inf) {
            outputFile.append(company.toString());
            outputFile.append(",");
            outputFile.append(System.lineSeparator());
        }
    }

}

