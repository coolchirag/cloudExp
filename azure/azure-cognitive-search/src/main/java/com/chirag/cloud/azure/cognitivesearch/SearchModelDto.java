package com.chirag.cloud.azure.cognitivesearch;

import java.util.List;

public class SearchModelDto {

	private String id;
	private Fields fields;
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Fields getFields() {
		return fields;
	}

	public void setFields(Fields fields) {
		this.fields = fields;
	}

	public class Fields {
		private String label;
		private String booktype;
		private List<String> codes;
		
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
		@Override
		public String toString() {
			return "Fields [label=" + label + ", booktype=" + booktype + ", codes=" + codes + "]";
		}
		
		
	}

	@Override
	public String toString() {
		return "SearchModelDto [id=" + id + ", fields=" + fields + "]";
	}
	
	
	
}
