package com.chirag.cloud.azure.cognitivesearch;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchModelDto {

	private String doc_id;
	private String id;

	private String label;
	private String booktype;
	private List<String> codes;

	@JsonProperty("name_cm")
	private String nameCm;
	private Integer parent_id;
	private Date start_date;
	private String end_date;
	
	
	public String getDoc_id() {
		return doc_id;
	}
	public void setDoc_id(String doc_id) {
		this.doc_id = doc_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getBooktype() {
		return booktype;
	}
	public void setBooktype(String booktype) {
		this.booktype = booktype;
	}
	public List<String> getCodes() {
		return codes;
	}
	public void setCodes(List<String> codes) {
		this.codes = codes;
	}
	public String getNameCm() {
		return nameCm;
	}
	public void setNameCm(String nameCm) {
		this.nameCm = nameCm;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	@Override
	public String toString() {
		return "SearchModelDto [doc_id=" + doc_id + ", id=" + id + ", label=" + label + ", booktype=" + booktype
				+ ", codes=" + codes + ", nameCm=" + nameCm + ", parent_id=" + parent_id + ", start_date=" + start_date
				+ ", end_date=" + end_date + "]";
	}
	
	
	

}
