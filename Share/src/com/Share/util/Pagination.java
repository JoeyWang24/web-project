package com.Share.util;

import java.util.List;


public class Pagination<T> {
	
	
	//-----从后台获取的数据------
	//每一页的数据
	private List<T> data;
	
	//总记录数
	private int count;
	
	//-----从前台传入的数据------
	
	//当前页号
	private int page;
	
	//每页显示行数
	private int rows;
	
	//计算总页数
	public int getPageCount() {
		return count%rows==0?count/rows:count/rows+1;
	}
	
	//是否首页
	public boolean isFirst(){
		return page==1;
	}
	
	//是否尾页
	public boolean isLast(){
		return page==getPageCount();
	}
	
	//上一页
	public int getPrePage(){
		return	page==1?page:page-1;
	}
	
	//下一页
	public int getNextPage(){
		return page==getPageCount()?page:page+1;
	}
	

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}


	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
	
}
