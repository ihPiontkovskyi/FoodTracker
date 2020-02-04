package ua.foodtracker.dao;

public class Page {
    private final Integer pageNumber;
    private final Long recordNumber;

    public Page(Integer pageNumber, Long recordNumber) {
        this.pageNumber = pageNumber;
        this.recordNumber = recordNumber;
    }

    public long getOffset() {
        return (pageNumber - 1) * recordNumber;
    }

    public Long getRecordNumber() {
        return recordNumber;
    }
}
