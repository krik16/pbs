package com.shouyingbao.pbs.core.bean;


/**
 * 统一返回对象(和前端统一)
 * 
 * @author 
 */
public class ResponseResult2 implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 返回对象类型数据 */
	private Result result=new Result();

	private Meta meta=new Meta();
	
	public void setErrno(int errno) {
		this.meta.setErrno(errno);
	}
	public void setMsg(String msg) {
		this.meta.setMsg(msg);
	}
	public void setData(Object data) {
		this.result.setData(data);
	}
	public void setTotal_record(int total_record) {
		this.result.setTotal_record(total_record);
	}
	public void setPagesize(int pagesize) {
		this.result.setPagesize(pagesize);
	}
	public void setCurrentpage(int currentpage) {
		this.result.setCurrentpage(currentpage);
	}
	public Result getResult() {
		return result;
	}
	public Meta getMeta() {
		return meta;
	}
	class Result implements java.io.Serializable {

		private static final long serialVersionUID = 1L;
		
		private Object data;
		
		private Page page=new Page();
		
		private class Page  implements java.io.Serializable {

			private static final long serialVersionUID = 1L;
			
			private int total_record;
			private int pagesize;
			private int currentpage;
			public int getTotal_record() {
				return total_record;
			}
			public void setTotal_record(int total_record) {
				this.total_record = total_record;
			}
			public int getPagesize() {
				return pagesize;
			}
			public void setPagesize(int pagesize) {
				this.pagesize = pagesize;
			}
			public int getCurrentpage() {
				return currentpage;
			}
			public void setCurrentpage(int currentpage) {
				this.currentpage = currentpage;
			}
		}

		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}
		public void setTotal_record(int total_record) {
			this.page.setTotal_record(total_record);
		}
		public void setPagesize(int pagesize) {
			this.page.setPagesize(pagesize);
		}
		public void setCurrentpage(int currentpage) {
			this.page.setCurrentpage(currentpage);
		}
		public Page getPage() {
			return page;
		}
		
	}

	 class Meta implements java.io.Serializable {

		private static final long serialVersionUID = 1L;
		/** 信息编码 */
		private int errno;
		
		/** 提升信息 */
		private String msg;
		
		public int getErrno() {
			return errno;
		}

		public void setErrno(int errno) {
			this.errno = errno;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}


