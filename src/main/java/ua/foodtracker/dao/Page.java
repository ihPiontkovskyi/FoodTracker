package ua.foodtracker.dao;

/**
 * Class contains information about page see {@link PageableDao}
 */
public class Page {
    private final Integer pageNumber;
    private final Integer recordNumber;

    public Page(Integer pageNumber, Integer recordNumber) {
        this.pageNumber = pageNumber;
        this.recordNumber = recordNumber;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getRecordNumber() {
        return recordNumber;
    }
}
