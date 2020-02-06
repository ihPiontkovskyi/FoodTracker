package ua.foodtracker.service.utility;

public class ServiceUtility {

    private ServiceUtility() {
    }

    public static long getNumberOfPage(long countOfRecords, long itemsPerPage) {
        return countOfRecords % itemsPerPage == 0 ? countOfRecords / itemsPerPage : countOfRecords / itemsPerPage + 1;
    }
}
