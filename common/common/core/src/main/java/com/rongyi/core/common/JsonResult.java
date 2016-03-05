package com.rongyi.core.common;

/**
 * 封装的json数据对象
 * 
 * @author jiejie 2014年6月19日 下午1:44:44
 *
 */
@Deprecated
public class JsonResult<T> {

    private T        result;
    private MetaData meta;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public MetaData getMeta() {
        return meta;
    }

    public void setMeta(MetaData meta) {
        this.meta = meta;
    }

    public static <T> JsonResult<T> successResult(T t, String msg, Integer total_record) {
        MetaData meta = new MetaData(0, msg, total_record);
        JsonResult<T> result = new JsonResult<T>();
        result.setMeta(meta);
        result.setResult(t);
        return result;
    }

    public static <T> JsonResult<T> failResult(T t, String msg, Integer total_record) {
        MetaData meta = new MetaData(1, msg, total_record);
        JsonResult<T> result = new JsonResult<T>();
        result.setMeta(meta);
        result.setResult(t);
        return result;
    }

    public static class MetaData {

        private Integer status;
        private String  msg;
        private Integer total_record;

        public MetaData(Integer status, String msg, Integer total_record) {
            this.status = status;
            this.msg = msg;
            this.total_record = total_record;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

		public Integer getTotal_record() {
			return total_record;
		}

		public void setTotal_record(Integer total_record) {
			this.total_record = total_record;
		}

    }
}
