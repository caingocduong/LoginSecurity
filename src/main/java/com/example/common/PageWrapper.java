package com.example.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageWrapper<T> {
	public static final int MAX_PAGE_POST_DISPLAY = 3;
	private Page<T> page;
	private List<PageItem> items;
	private int currentPage;
	private String url;
	
	public PageWrapper(Page<T> page, String url){
		this.page =page;
		this.url = url;
		
		items = new ArrayList<PageItem>();
		
		currentPage = page.getNumber() + 1;
		int start, size;
		if(page.getTotalPages() <= MAX_PAGE_POST_DISPLAY){
			start = 1;
			size = page.getTotalPages();
		} else {
			if(currentPage <= MAX_PAGE_POST_DISPLAY - MAX_PAGE_POST_DISPLAY/2){
				start = 1;
				size = MAX_PAGE_POST_DISPLAY;
			} else {
				if(currentPage >= page.getTotalPages() - MAX_PAGE_POST_DISPLAY/2){
					start = page.getTotalPages() - MAX_PAGE_POST_DISPLAY +1;
					size = MAX_PAGE_POST_DISPLAY;
				} else {
					start = currentPage - MAX_PAGE_POST_DISPLAY/2;
					size = MAX_PAGE_POST_DISPLAY;
				}
			}
		}
		
		for(int i = 0; i < size; i++){
			items.add(new PageItem(start+i, (start+i)==currentPage));
		}
	}
	
	public String getUrl(){
		 return url;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public List<PageItem> getItems(){
		
		return this.items;
	}
	
	public int getNumber(){
		
		return this.currentPage;
	}
	
	public List<T> getContent(){
		
		return page.getContent();
	}
	
	public int getSize(){
		
		return page.getSize();
	}
	
	public int getTotalPages(){
		
		return page.getTotalPages();
	}
	
	public boolean isFirstPage(){
		
		return page.isFirst();
	}
	
	public boolean isLastPage(){
		
		return page.isLast();
	}
	
	public boolean isHasPreviousPage(){
		
		return page.hasPrevious();
	}
	
	public boolean isHasNextPage(){
		
		return page.hasNext();
	}
	
	public class PageItem {
		private int number;
		private boolean current;
		
		public PageItem(int number, boolean current){
			this.number = number;
			this.current = current;
		}
		
		public int getNumber(){
			
			return this.number;
		}
		
		public boolean isCurrent(){
			
			return this.current;
		}
	}

}
