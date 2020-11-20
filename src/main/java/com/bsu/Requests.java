package com.bsu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Requests {
    List<Company> companies;

    public Requests(List<Company> companies) {
        this.companies = companies;
    }

    public List<Company> chooseByAbbreviation(String request) {
        List<Company> result = new ArrayList<>();

        for (Company company : companies) {
            if (company.getAbbreviation().equalsIgnoreCase(request)) {
                result.add(company);
            }
        }

        return result;
    }

    public List<Company> chooseByTypeOfBusiness(String request) {
        List<Company> result = new ArrayList<>();

        for (Company company : companies) {
            if (company.getType().equalsIgnoreCase(request)) {
                result.add(company);
            }
        }

        return result;
    }

    List<Company> chooseByCountOfEmployees(int start, int end){
        List<Company> result = new ArrayList<>();

        for (Company company : companies) {
            if (start <= company.getEmployee() && company.getEmployee() <= end) {
                result.add(company);
            }
        }

        return result;
    }
}
