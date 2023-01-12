package com.spring.demo.dto;

import java.util.List;

public class FootballMatches {
	
	private Long page;
	private Long per_page;
	private Long total;
	private Long total_pages;
	
	private List<MatchResult> data;

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public Long getPer_page() {
		return per_page;
	}

	public void setPer_page(Long per_page) {
		this.per_page = per_page;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getTotal_pages() {
		return total_pages;
	}

	public void setTotal_pages(Long total_pages) {
		this.total_pages = total_pages;
	}

	public List<MatchResult> getData() {
		return data;
	}

	public void setData(List<MatchResult> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "FootballMatches [page=" + page + ", per_page=" + per_page + ", total=" + total + ", total_pages="
				+ total_pages + ", matchResults=" + data + "]";
	}
	
	
	 
}
