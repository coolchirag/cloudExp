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
		private String name_cm;
		private Integer parent_id;
		
		
		
		public String getName_cm() {
			return name_cm;
		}
		public void setName_cm(String name_cm) {
			this.name_cm = name_cm;
		}
		public Integer getParent_id() {
			return parent_id;
		}
		public void setParent_id(Integer parent_id) {
			this.parent_id = parent_id;
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
		@Override
		public String toString() {
			return "Fields [label=" + label + ", booktype=" + booktype + ", codes=" + codes + ", name_cm=" + name_cm
					+ ", parent_id=" + parent_id + "]";
		}
		
		
	}

	@Override
	public String toString() {
		return "SearchModelDto [id=" + id + ", fields=" + fields + "]";
	}
	
	
	
}
