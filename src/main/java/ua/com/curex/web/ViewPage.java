package ua.com.curex.web;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Tomasz Nurkiewicz
 * @since 13.06.11, 16:55
 */
public class ViewPage<T> {

	private List<T> data;

	private int curPage;
	private int rpp;
	private long totalRecords;

	public ViewPage() {
	}

	public ViewPage(Page<T> dbPage, int page, int size, long totalRecords) {
		this.data = dbPage.getContent();
		this.curPage = page;
		this.rpp = size;
		this.totalRecords = totalRecords;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	
	public int getRpp() {
		return rpp;
	}

	public void setRpp(int rpp) {
		this.rpp = rpp;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}
}
