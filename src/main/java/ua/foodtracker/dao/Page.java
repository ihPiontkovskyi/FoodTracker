package ua.foodtracker.dao;

/**
 * Class contains information about page see {@link PageableDao}
 */
public class Page {
    private final Integer pageNumber;
    private final Long recordNumber;

    public Page(Integer pageNumber, Long recordNumber) {
        this.pageNumber = pageNumber;
        this.recordNumber = recordNumber;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Long getRecordNumber() {
        return recordNumber;
    }
}
