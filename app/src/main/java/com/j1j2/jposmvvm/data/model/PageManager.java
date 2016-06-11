package com.j1j2.jposmvvm.data.model;


public class PageManager<T> {


    /**
     * List : []
     * PageLength : 0
     * PageIndex : 1
     * Offset : 0
     * PageSize : 20
     * TotalCount : 0
     * PageCount : 0
     */

    private int PageLength;
    private int PageIndex;
    private int Offset;
    private int PageSize;
    private int TotalCount;
    private int PageCount;
    private T List;

    public void setPageLength(int PageLength) {
        this.PageLength = PageLength;
    }

    public void setPageIndex(int PageIndex) {
        this.PageIndex = PageIndex;
    }

    public void setOffset(int Offset) {
        this.Offset = Offset;
    }

    public void setPageSize(int PageSize) {
        this.PageSize = PageSize;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public void setPageCount(int PageCount) {
        this.PageCount = PageCount;
    }

    public void setList(T List) {
        this.List = List;
    }

    public int getPageLength() {
        return PageLength;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public int getOffset() {
        return Offset;
    }

    public int getPageSize() {
        return PageSize;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public int getPageCount() {
        return PageCount;
    }

    public T getList() {
        return List;
    }

    @Override
    public String toString() {
        return "PageManager{" +
                "PageLength=" + PageLength +
                ", PageIndex=" + PageIndex +
                ", Offset=" + Offset +
                ", PageSize=" + PageSize +
                ", TotalCount=" + TotalCount +
                ", PageCount=" + PageCount +
                ", List=" + List +
                '}';
    }
}
